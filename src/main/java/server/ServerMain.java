package server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Anuwat Angkuldee 5810401066
 */

public class ServerMain {

    public static void main(String[] args) {

        ApplicationContext buffer = new ClassPathXmlApplicationContext("calendar-server.xml");

        System.out.println("Server has started.");

    }

}
