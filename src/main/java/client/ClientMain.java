package client;

import client.controller.MainControllerGUI;
import common.CalendarService;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Anuwat Angkuldee 5810401066
 */

public class ClientMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader root = new FXMLLoader(getClass().getResource("/MainView.fxml"));
        primaryStage.setScene(new Scene((Parent) root.load(), 600, 400));
        primaryStage.setTitle("Calendar");
        primaryStage.setResizable(false);

        ApplicationContext buffer = new ClassPathXmlApplicationContext("calendar-client.xml");
        CalendarService service = (CalendarService) buffer.getBean("service");

        MainControllerGUI mainControllerGUI = root.getController();
        mainControllerGUI.setService(service);
        mainControllerGUI.setAppointmentsDetails();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
