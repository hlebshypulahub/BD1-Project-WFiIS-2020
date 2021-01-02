package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
//        URL url = new File("C:\\Users\\hlebs\\Desktop\\SEMESTR 5\\BD1\\Project_v1\\Application\\src\\application.login\\Login.fxml").toURI().toURL();
//        Parent root = FXMLLoader.load(url);
        Parent root = FXMLLoader.load(getClass().getResource("login/Login.fxml"));
        primaryStage.setTitle("Hleb Shypula - Projekt - Bazy Danych 1");
        primaryStage.setScene(new Scene(root, 1200, 750));
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
