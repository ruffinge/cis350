package package1;


import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class StartupClass extends Application{

	//private String userInput;
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("package1/MainView.fxml"));
		primaryStage.setTitle("Movie DB");
		primaryStage.getIcons().add(new Image("bird.png"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	public static void main(String[] args){
		launch(args);
		//MovieDBcl mvdb = new MovieDBcl();
		//System.out.println(mvdb.Searching("Here"));
	}
}