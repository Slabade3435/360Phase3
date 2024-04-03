package application;

import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PatientPortal {

	public Scene patientLogin(Scene previousScene, Stage ownerStage) {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 900, 600);

		// Patient Label
		Label patientLabel = new Label("Patient Portal");
		patientLabel.setFont(Font.font("Arial", 40));
		patientLabel.setTextFill(Color.web("#0844a4"));

		// Back Button
		Button backToMain = new Button("Back");
		backToMain.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white; -fx-font-size: 20px;");
		backToMain.setOnAction(e -> {
			Stage stage = (Stage) backToMain.getScene().getWindow();
			stage.setScene(previousScene);
		});

		// Top Menu
		HBox topMenu = new HBox();
		topMenu.getChildren().addAll(backToMain, patientLabel);
		topMenu.setAlignment(Pos.TOP_LEFT);
		topMenu.setSpacing(270.0);
		topMenu.setPadding(new Insets(10));

		// Set the menu at the top
		root.setTop(topMenu);
		root.setAlignment(topMenu, Pos.TOP_CENTER);

		// First Name Label and input field
		Label firstNameLabel = new Label("First Name");
		firstNameLabel.setFont(Font.font("Arial", 20));
		firstNameLabel.setTextFill(Color.BLACK);

		TextField firstNameField = new TextField();

		HBox firstNameHolder = new HBox();
		firstNameHolder.getChildren().addAll(firstNameLabel, firstNameField);
		firstNameHolder.setAlignment(Pos.CENTER);
		firstNameHolder.setSpacing(10);

		// Last Name Label and input field
		Label lastNameLabel = new Label("Last Name");
		lastNameLabel.setFont(Font.font("Arial", 20));
		lastNameLabel.setTextFill(Color.BLACK);

		TextField lastNameField = new TextField();

		HBox lastNameHolder = new HBox();
		lastNameHolder.getChildren().addAll(lastNameLabel, lastNameField);
		lastNameHolder.setAlignment(Pos.CENTER);
		lastNameHolder.setSpacing(10);

		// Date of birth and date picker
		Label dobLabel = new Label("Date of Birth");
		dobLabel.setFont(Font.font("Arial", 20));
		dobLabel.setTextFill(Color.BLACK);

		DatePicker dobPicker = new DatePicker();
		dobPicker.setValue(LocalDate.now());

		HBox dobHolder = new HBox();
		dobHolder.getChildren().addAll(dobLabel, dobPicker);
		dobHolder.setAlignment(Pos.CENTER);
		dobHolder.setSpacing(10);

		// login button for the patient
		Button patientLoginButton = new Button("Login");
		patientLoginButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white; -fx-font-size: 20px;");
		patientLoginButton.setOnAction(e -> {
			System.out.println(dobPicker.getValue());
		});

		// adding everything to a VBox
		VBox patientPortalVbox = new VBox();
		patientPortalVbox.getChildren().addAll(firstNameHolder, lastNameHolder, dobHolder, patientLoginButton);
		patientPortalVbox.setAlignment(Pos.CENTER);
		patientPortalVbox.setSpacing(50);
		patientPortalVbox.setPadding(new Insets(-100, 0, 0, 0));

		// setting the vbox to the center
		root.setCenter(patientPortalVbox);

		return scene;

	}

}
