package com.saspallow.gateway.fixj.acceptor;

import org.springframework.stereotype.Component;
import quickfix.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by dt213142 on 10/3/2017.
 */
@Component
public class AcceptorApplication {

    Application application;
    Acceptor acceptor;

    public AcceptorApplication() throws ConfigError, RuntimeError, IOException {
        application = new AcceptorServer();
        ClassLoader classLoader = getClass().getClassLoader();

        InputStream is = new FileInputStream( classLoader.getResource("server.cfg").getFile() );
        SessionSettings sessionSettings = new SessionSettings(is);
        MessageStoreFactory messageStoreFactory = new FileStoreFactory(sessionSettings);
        LogFactory logFactory = new FileLogFactory(sessionSettings);
        MessageFactory messageFactory = new DefaultMessageFactory();
        acceptor = new SocketAcceptor(application, messageStoreFactory, sessionSettings, logFactory, messageFactory);
    }

    public void promptEnterKey(){
        System.out.println("Press \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public void run() throws RuntimeError, ConfigError {
        acceptor.start();
        promptEnterKey();
        acceptor.stop();
    }
}
