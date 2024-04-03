package application;
	
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,900,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.setFill(Color.WHITE);
			
			ImageView bearImageView = new ImageView();
			Image bearImage = new Image(getClass().getResourceAsStream("DoomsDay_Doctor.png"));
			bearImageView.setImage(bearImage);
			
			//ADD BEAR AND SET ITS ALIGNMENT
			root.setTop(bearImageView);
			root.setAlignment(bearImageView, Pos.TOP_CENTER);
			
			Label appleLabel = new Label("An Apple a Day Keeps the Doctor Away");
			appleLabel.setFont(Font.font("Arial", 32));
			appleLabel.setTextFill(Color.web("#0844a4"));
			
			
			Label welcomeLabel = new Label("Welcome!");
			welcomeLabel.setFont(Font.font("Arial", 20));
			welcomeLabel.setTextFill(Color.BLACK);
			
			
			//Create buttons and Set style
			Button patientButton = new Button("Patient");
			Button staffButton = new Button("Staff");
			patientButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white; -fx-font-size: 20px;");
			staffButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white; -fx-font-size: 20px;");
			
			//Create HBox and add buttons
			HBox buttonHolder = new HBox();
			buttonHolder.getChildren().addAll(patientButton, staffButton);
			buttonHolder.setAlignment(Pos.CENTER);
			buttonHolder.setSpacing(15);
			
			//Create VBox for labels
			VBox vboxCenter = new VBox();
			vboxCenter.getChildren().addAll(appleLabel, welcomeLabel, buttonHolder);
			vboxCenter.setAlignment(Pos.CENTER);
			vboxCenter.setSpacing(15);
			

			//ADD LABELS AND SETS ITS ALIGNMENT
			root.setCenter(vboxCenter);
			root.setAlignment(vboxCenter, Pos.CENTER);
			
			
			primaryStage.setTitle("DoomsDay Doctor");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			patientButton.setOnAction(e->{
				primaryStage.setScene(new PatientPortal().patientLogin(scene, primaryStage));
			});
			
			staffButton.setOnAction(e->{
				primaryStage.setScene(new StaffPortal().staffLogin(scene, primaryStage));
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
