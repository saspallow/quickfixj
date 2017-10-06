package com.saspallow.gateway.fixj.initiator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import quickfix.*;
import quickfix.field.*;
import quickfix.fix42.NewOrderSingle;
import quickfix.fix42.OrderCancelRequest;

import java.util.Date;
import java.util.HashSet;

public class InitiatorServer implements Application {

    private final DefaultMessageFactory messageFactory = new DefaultMessageFactory();

    private Session session;
    private final HashSet<SessionID> set = new HashSet<>();

    private final static Logger logger = LoggerFactory.getLogger(InitiatorServer.class);

    @Override
    public void onCreate(SessionID sessionID) {
        logger.info("** Init On Create **" + sessionID);
        session = Session.lookupSession(sessionID);
        set.add(sessionID);
    }

    @Override
    public void onLogon(SessionID sessionID) {
        logger.info("** Init On Logon **");
//        NewOrderSingle order = new NewOrderSingle(new ClOrdID("MISYS1001"),
//                new HandlInst(HandlInst.MANUAL_ORDER),
//                new Symbol("MISYS"),
//                new   Side(Side.BUY),
//                new TransactTime(new Date()),
//                new OrdType(OrdType.LIMIT));
//
//        try {
//            Session.sendToTarget(order, sessionID);
//        } catch (SessionNotFound sessionNotFound) {
//            sessionNotFound.printStackTrace();
//        }
    }

    @Override
    public void onLogout(SessionID sessionID) {
//        logger.info("** Init On Logout **");
    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {
//        logger.info("** Init To Admin**");
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
//        logger.info("** Init From Admin **");
    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        logger.info("** Init Send new Message **");
    }

    @Override
    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        logger.info("** Init received new message.. **");
    }

    public void send(Message message) {
        System.out.println("Inside: " + this.session.getSessionID());
        System.out.println("Inside Map: "+set);
        try {
            Session.sendToTarget(message, new SessionID("FIX.4.2:103/002->999"));
        } catch (SessionNotFound sessionNotFound) {
            sessionNotFound.printStackTrace();
        }
    }
}
