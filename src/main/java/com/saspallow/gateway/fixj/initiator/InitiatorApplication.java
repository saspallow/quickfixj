package com.saspallow.gateway.fixj.initiator;

import com.saspallow.gateway.fixj.acceptor.AcceptorServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import quickfix.*;
import quickfix.field.*;
import quickfix.fix40.NewOrderSingle;

import java.io.*;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

/**
 * Created by dt213142 on 10/5/2017.
 */
@Component
public class InitiatorApplication {

    private final static Logger logger = LoggerFactory.getLogger(InitiatorApplication.class);

    private static CountDownLatch shutdownLatch = new CountDownLatch(1);
    private boolean initiatorStarted = false;

    private Initiator initiator;
    private InitiatorServer initiatorServer;

    public InitiatorApplication() throws RuntimeError, ConfigError, FileNotFoundException {
        initiatorServer = new InitiatorServer();
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream is = new FileInputStream( classLoader.getResource("client.cfg").getFile() );
        SessionSettings settings = new SessionSettings(is);
        MessageStoreFactory messageStoreFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new FileLogFactory(settings);
        MessageFactory messageFactory = new DefaultMessageFactory();
        initiator = new SocketInitiator(initiatorServer, messageStoreFactory, settings, logFactory, messageFactory);
    }


    public void testNewOrderSingle () {
        quickfix.fix42.NewOrderSingle order = new quickfix.fix42.NewOrderSingle(new ClOrdID("MISYS1001"),
                new HandlInst(HandlInst.MANUAL_ORDER),
                new Symbol("MISYS"),
                new Side(Side.BUY),
                new TransactTime(new Date()),
                new OrdType(OrdType.LIMIT));

        System.out.println("Outside: " + initiator.getSessions());
        initiatorServer.send(order);
    }

    public void menu(){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println("\nPlease enter a choice" +
                        "\n1) Send Order From Fix To ETS Message: " +
                        "\n    1.1) New Order Single (D)" +
                        "\n    1.2) Order Cancel Request (F)" +
                        "\n2) Send Order From EST To ETS Message: " +
                        "\n    2.1) New Order Single (1I)" +
                        "\n3) --: " +
                        "\n    3.1) Renew" +
                        "\n    3.2) Logon" +
                        "\n    3.2) Logout" +
                        "\n9) Exit" +
                        "\n=>");
                String line = br.readLine();
                if (line.equals("9")) {
                    break;
                }else if (line.equals("1.1")) {
                    testNewOrderSingle();
                }else if (line.equals("1.2")) {
                }else if (line.equals("2.1")) {
                }else if (line.equals("3.1")) {
                    newLatch();
                }else if (line.equals("3.2")) {
                    logon();
                }else if (line.equals("3.3")) {
                    logout();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void stop() {
        shutdownLatch.countDown();
//        initiator.stop();
//        shutdownLatch.await();
    }

    protected synchronized CountDownLatch newLatch() {
        return this.shutdownLatch = new CountDownLatch(1);
    }

    public void logon() throws RuntimeError, ConfigError, InterruptedException {
        if (!initiatorStarted) {
            try {
                initiator.start();
                initiatorStarted = true;
                menu();
            } catch (Exception e) {
                logger.error("Start Filed", e);
            }
        }
        else {
            for (SessionID sessionID : initiator.getSessions()) {
                Session.lookupSession(sessionID).logon();
            }
        }
    }

    public void logout() {
        for (SessionID sessionID : initiator.getSessions()) {
            Session.lookupSession(sessionID).logout("user requested");
        }
    }
}
