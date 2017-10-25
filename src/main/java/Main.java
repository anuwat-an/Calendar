/**
 * Anuwat Angkuldee 5810401066
 */

import controller.MainControllerGUI;
import dataSource.DataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader root = new FXMLLoader(getClass().getResource("/MainView.fxml"));
        primaryStage.setScene(new Scene((Parent) root.load(), 600, 400));
        primaryStage.setTitle("Calendar");
        primaryStage.setResizable(false);

        ApplicationContext buffer = new ClassPathXmlApplicationContext("main.xml");
        DataSource dataSource = (DataSource) buffer.getBean("dataSource");

        MainControllerGUI mainControllerGUI = root.getController();
        mainControllerGUI.setDataSource(dataSource);
        mainControllerGUI.prepareCalendar();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
