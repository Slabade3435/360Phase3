package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NursePatientLookUp {
	public Scene patientLookUp(Scene previousScene, Stage ownerStage, String userName) {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 900, 600);

		HBox topMenu = new HBox();

		// Nurse label
		Label nurseLabel = new Label("Hello Nurse " + userName);
		nurseLabel.setFont(Font.font("Arial", 32));
		nurseLabel.setTextFill(Color.web("#0844a4"));

		// Logout Button
		Button logoutButton = new Button("Logout");
		logoutButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white; -fx-font-size: 20px;");
		logoutButton.setOnAction(e -> {
			Stage stage = (Stage) logoutButton.getScene().getWindow();
			stage.setScene(previousScene);
		});

		topMenu.getChildren().add(nurseLabel);
		topMenu.getChildren().add(logoutButton);
		topMenu.setSpacing(230);
		topMenu.setAlignment(Pos.TOP_RIGHT);

		// Set topMenu at the top
		root.setTop(topMenu);
		root.setAlignment(topMenu, Pos.TOP_CENTER);

		VBox searchPatient = new VBox();

		Label searchPatientLabel = new Label("Search Patient: ");
		searchPatientLabel.setFont(Font.font("Arial", 25));

		GridPane patientInfo = new GridPane();
		patientInfo.setPadding(new Insets(40));
		patientInfo.setHgap(60);
		patientInfo.setVgap(30);

		ColumnConstraints patientLabels = new ColumnConstraints();
		ColumnConstraints patientFields = new ColumnConstraints();
		patientInfo.getColumnConstraints().addAll(patientLabels, patientFields);

		// first name
		Label firstNameLabel = new Label("First Name: ");
		firstNameLabel.setFont(Font.font("Arial", 20));

		TextField firstNameTextField = new TextField();
		firstNameTextField.setPrefSize(200, 40);

		// last name
		Label lastNameLabel = new Label("Last Name: ");
		lastNameLabel.setFont(Font.font("Arial", 20));

		TextField lastNameTextField = new TextField();
		lastNameTextField.setPrefSize(200, 40);

		// DOB
		Label dobLabel = new Label("Date of Birth: ");
		dobLabel.setFont(Font.font("Arial", 20));

		DatePicker dobPicker = new DatePicker();
		dobPicker.setPrefSize(200, 40);

		patientInfo.addRow(0, firstNameLabel, firstNameTextField);
		patientInfo.addRow(1, lastNameLabel, lastNameTextField);
		patientInfo.addRow(2, dobLabel, dobPicker);

		searchPatient.getChildren().add(searchPatientLabel);
		searchPatient.getChildren().add(patientInfo);
		searchPatient.setAlignment(Pos.CENTER);
		searchPatient.setSpacing(30);

		root.setLeft(searchPatient);
		root.setAlignment(searchPatient, Pos.CENTER);

		Button searchButton = new Button("Search");
		searchButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white; -fx-font-size: 20px;");
		searchButton.setOnAction(e -> {
			String firstNameInput = firstNameTextField.getText();
			String lastNameInput = lastNameTextField.getText();

			// check if all the fields are filled
			if (firstNameInput.equals("") || lastNameInput.equals("") || dobPicker.getValue() == null) {
				createPopup("Please provide input for all the fields!", true);
			} else {
				String dobInput = dobPicker.getValue().toString();
//				System.out.println(dobPicker.getValue());
				
				File patientFile = checkRecords(firstNameInput, lastNameInput, dobInput);
				if (patientFile != null) {
					// TODO connect to Doctor Tab of the patient, should g
					System.out.println("The Patient is in the DB");
					ownerStage.setScene(new NurseView().patientTabs(scene, ownerStage, patientFile));

				} else {
					createPopup("The Patient does not Exist within the Database", true);
				}

			}

		});

		HBox searchButtonHolder = new HBox();
		searchButtonHolder.getChildren().add(searchButton);
		searchButtonHolder.setAlignment(Pos.CENTER);

		root.setCenter(searchButtonHolder);
		root.setAlignment(searchButtonHolder, Pos.CENTER);

		return scene;

	}

	//check patient record within the patientInfo folder
	private File checkRecords(String firstName, String lastName, String dob) {
			
			String folderPth = "patientInfo";
			//TODO add DOB to filename
			String filenm = firstName + "_" + lastName + "_" + dob + "_" + "patientinfo.txt";

			File folder = new File(folderPth);

			// check if the file exists within the folder

			if (folder.exists() && folder.isDirectory()) {
				File[] files = folder.listFiles();

				for (File file : files) {
					if (file.isFile() && file.getName().equals(filenm)) {
						return file;
					}
				}

			} 
			
			File patientFile = new File(folderPth, filenm);
			try {
				patientFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return patientFile;
		}

	//create Popups
	private void createPopup(String errorString, boolean actualError) {
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL); // Makes the popup modal
		// popupStage.setTitle("Error");

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

}