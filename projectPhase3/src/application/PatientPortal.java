package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
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

		HBox dobHolder = new HBox();
		dobHolder.getChildren().addAll(dobLabel, dobPicker);
		dobHolder.setAlignment(Pos.CENTER);
		dobHolder.setSpacing(10);

		// login button for the patient
		Button patientLoginButton = new Button("Login");
		patientLoginButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white; -fx-font-size: 20px;");
		patientLoginButton.setOnAction(e -> {
			String firstNameInput = firstNameField.getText();
			String lastNameInput = lastNameField.getText();

			
			if(firstNameInput.equals("") || lastNameInput.equals("") || dobPicker.getValue() == null) {
				createPopup("Please provide input for all the fields!", true);
			} else {
				String dobInput = dobPicker.getValue().toString();
				System.out.println(dobPicker.getValue());
				
				if(checkLogin(firstNameInput, lastNameInput, dobInput)) {
					//TODO connect patient view here and remove the print statement
					System.out.println("The Patient is in the DB");
				} else {
					createPopup("The Patient does not Exist within the Database", true);
				}
				
			}

			
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
	
	//generates popups: needs error string and if the error is an actual error or a suggestion
	private void createPopup(String errorString, boolean actualError) {
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL); // Makes the popup modal

		if (actualError) {
			popupStage.setTitle("Error");
		}

		VBox errorHandle = new VBox();

		Label errorLabel = new Label(errorString);
		errorLabel.setFont(Font.font("Arial", 25));

		if (actualError) {
			Button ackErrorButton = new Button("Acknowledge");
			ackErrorButton.setStyle("-fx-background-color: #c93828; -fx-text-fill: white; -fx-font-size: 15px;");
			ackErrorButton.setOnAction(e -> popupStage.close());

			errorHandle.getChildren().add(errorLabel);
			errorHandle.getChildren().add(ackErrorButton);
			errorHandle.setAlignment(Pos.CENTER);
			errorHandle.setSpacing(30);

		} else {
			Button closeButton = new Button("Close");
			closeButton.setOnAction(e -> popupStage.close());
			closeButton.setStyle("-fx-background-color: #27d121; -fx-text-fill: white; -fx-font-size: 15px;");

			errorHandle.getChildren().add(errorLabel);
			errorHandle.getChildren().add(closeButton);
			errorHandle.setAlignment(Pos.CENTER);
			errorHandle.setSpacing(30);
		}

		StackPane popupRoot = new StackPane();
		popupRoot.getChildren().add(errorHandle);

		Scene popupScene = new Scene(popupRoot);
		popupStage.setScene(popupScene);
		popupStage.show();
	}
	
	
	//checks login info of the patient within the patientLogins file
	private boolean checkLogin(String firstName, String lastName, String dob) {

		boolean foundPatient = false;

		try (BufferedReader reader = new BufferedReader(new FileReader("patientLogins"))) {
			String patientLogin = firstName + "," + lastName + "," + dob;
			System.out.println(patientLogin);
			String line;
			boolean isFirstLine = true;
			while ((line = reader.readLine()) != null) {
				if (isFirstLine == true) {
					isFirstLine = false;
				} else {
					if (line.equals(patientLogin)) {
						foundPatient = true;
						break;
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return foundPatient;
	}

	

}
