package com.saspallow.gateway.app;

import com.saspallow.gateway.fixj.acceptor.AcceptorApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import quickfix.ConfigError;

import java.io.IOException;

/**
 * Created by dt213142 on 10/3/2017.
 */
@Configuration
@ComponentScan
public class TestAcceptor {

    @Bean
    public AcceptorApplication acceptorApplication() throws IOException, ConfigError {
        return new AcceptorApplication();
    }

    public static void main(String[] args) throws IOException, ConfigError {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestAcceptor.class);
        AcceptorApplication acceptorApplication = context.getBean(AcceptorApplication.class);
        try {
            acceptorApplication.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
