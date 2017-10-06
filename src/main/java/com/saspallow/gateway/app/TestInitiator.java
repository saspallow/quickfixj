package com.saspallow.gateway.app;

import com.saspallow.gateway.fixj.initiator.InitiatorApplication;
import com.saspallow.gateway.fixj.initiator.InitiatorApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import quickfix.ConfigError;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by dt213142 on 10/5/2017.
 */
@Configuration
@ComponentScan
public class TestInitiator {


    @Bean
    public InitiatorApplication initiatorApplication() throws IOException, ConfigError {
        return new InitiatorApplication();
    }

    public static void main(String[] args) throws IOException, ConfigError, InterruptedException {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestAcceptor.class);
        InitiatorApplication clientApplication = context.getBean(InitiatorApplication.class);
        try {
            clientApplication.logon();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
