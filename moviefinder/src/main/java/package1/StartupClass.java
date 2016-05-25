package package1;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartupClass extends Application{


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("package1/MainView.fxml"));
        primaryStage.setTitle("Movie DB");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}