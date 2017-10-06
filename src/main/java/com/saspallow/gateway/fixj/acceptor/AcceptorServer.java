package com.saspallow.gateway.fixj.acceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.*;

/**
 * Created by dt213142 on 10/3/2017.
 */
public class AcceptorServer extends MessageCracker implements Application {

    private final static Logger logger = LoggerFactory.getLogger(AcceptorServer.class);

    public void onCreate(SessionID sessionID) {
//        logger.info("** Acc On Create **");
    }

    public void onLogon(SessionID sessionID) {
        logger.info("** Acc On Logon **");
    }

    public void onLogout(SessionID sessionID) {
//        logger.info("** Acc On Logout **");
    }

    public void toAdmin(Message message, SessionID sessionID) {
//        logger.info("** Acc To Admin**");
    }

    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
//        logger.info("** Acc From Admin **");
    }

    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        logger.info("** Acc Send new Message **");
    }

    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        logger.info("** Acc received new message.. **");
    }
}
