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
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DoctorView {
	public Scene patientTabs(Scene previousScene, Stage ownerStage, File patientFile) {
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: white;");
		Scene scene = new Scene(root, 900, 600);

		// Create the TabPane
		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

		// Create and add tabs to the TabPane
		Tab PatientHisTab = new Tab("History");
		PatientHisTab.setContent(createHistoryContent(patientFile));

		Tab PhysicalTestTab = new Tab("Physical Test");
		PhysicalTestTab.setContent(physicalTest());

		Tab DiagnosisTab = new Tab("Diagnosis");
		DiagnosisTab.setContent(diagnosis());

		Tab PrescriptionTab = new Tab("Prescription");

		Tab NotesTab = new Tab("Notes");

		// Add tabs to the tab pane
		tabPane.getTabs().addAll(PatientHisTab, PhysicalTestTab, DiagnosisTab, PrescriptionTab, NotesTab);

		// Set the TabPane to the top of the BorderPane
		root.setTop(tabPane);

		return scene;

	}

	private String fileField(String field, File patientFile) {
		String resultString = "";

		try (BufferedReader br = new BufferedReader(new FileReader(patientFile))) {
			String line;

			while ((line = br.readLine()) != null) {
				String[] fields = line.split(":");

				if (fields.length == 2 && fields[0].trim().equals(field)) {
					resultString = fields[1].trim();
					
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultString;

	}

	private VBox createHistoryContent(File patientFile) {
		VBox historyContent = new VBox();
		historyContent.setAlignment(Pos.CENTER);

		Label historyLabel = new Label("Patient History:");
		historyLabel.setStyle("-fx-font-size: 24px;"); // Set font size
		historyContent.getChildren().add(historyLabel);

		GridPane gridPane = new GridPane();
		gridPane.setVgap(30);
		gridPane.setHgap(60);
		gridPane.setPadding(new Insets(20));
		

		Label firstNameLabel = new Label("First Name:");
		Label lastNameLabel = new Label("Last Name:");
		Label dobLabel = new Label("Date of Birth:");
		Label ageLabel = new Label("Age:");

		// Non-editable text fields
		TextField firstNameField = new TextField();
		firstNameField.setEditable(false);
		firstNameField.setText(fileField("First Name", patientFile));
		
		TextField lastNameField = new TextField();
		lastNameField.setEditable(false);
		lastNameField.setText(fileField("Last Name", patientFile));
		
		
		TextField dobField = new TextField();
		dobField.setEditable(false);
		dobField.setText(fileField("Date of Birth", patientFile));
		
		TextField ageField = new TextField();
		ageField.setEditable(false);
		ageField.setText(fileField("Age", patientFile));

		gridPane.addRow(0, firstNameLabel, firstNameField);
		gridPane.addRow(1, lastNameLabel, lastNameField);
		gridPane.addRow(2, dobLabel, dobField);
		gridPane.addRow(3, ageLabel, ageField);

		historyContent.getChildren().add(gridPane);

//		// Add buttons and text fields
//		TextArea immunizationText = new TextArea();
//		immunizationText.setEditable(false);
//		immunizationText.setPrefWidth(50);
//		immunizationText.setPrefHeight(100); // Set height
//		immunizationText.setVisible(false);
//
//		TextArea medicalHistoryText = new TextArea();
//		medicalHistoryText.setEditable(false);
//		medicalHistoryText.setPrefWidth(50);
//		medicalHistoryText.setPrefHeight(100);
//		medicalHistoryText.setVisible(false);
//
//		TextArea medicationText = new TextArea();
//		medicationText.setEditable(false);
//		medicationText.setPrefWidth(50);
//		medicationText.setPrefHeight(100);
//		medicationText.setVisible(false);
//
//		Button immunizationButton = new Button("Immunization Records");
//		Button medicalHistoryButton = new Button("Previous Medical History");
//		Button medicationButton = new Button("Previous Medication");
//
//		immunizationButton.setOnAction(e -> {
//			toggleTextFieldVisibility(immunizationText);
//			medicalHistoryText.setVisible(false);
//			medicationText.setVisible(false);
//		});
//		medicalHistoryButton.setOnAction(e -> {
//			toggleTextFieldVisibility(medicalHistoryText);
//			immunizationText.setVisible(false);
//			medicationText.setVisible(false);
//		});
//		medicationButton.setOnAction(e -> {
//			toggleTextFieldVisibility(medicationText);
//			immunizationText.setVisible(false);
//			medicalHistoryText.setVisible(false);
//		});
//
//		// Set button size
//		immunizationButton.setPrefSize(200, 50);
//		medicalHistoryButton.setPrefSize(200, 50);
//		medicationButton.setPrefSize(200, 50);
//
//		VBox buttonsBox = new VBox(20); // Increased spacing between buttons
//		buttonsBox.setAlignment(Pos.CENTER);
//		buttonsBox.getChildren().addAll(immunizationButton, medicalHistoryButton, medicationButton);
//
//		VBox textFieldsBox = new VBox(10);
//		textFieldsBox.setAlignment(Pos.CENTER);
//		textFieldsBox.getChildren().addAll(immunizationText, medicalHistoryText, medicationText);
//
//		historyContent.getChildren().addAll(buttonsBox, textFieldsBox);

		return historyContent;
	}

	private void toggleTextFieldVisibility(TextArea immunizationText) {
		immunizationText.setVisible(!immunizationText.isVisible());
	}

	private VBox physicalTest() {
		VBox physicalTest = new VBox();
		physicalTest.setAlignment(Pos.TOP_CENTER);

		Label physicalTestL = new Label("Physical");
		physicalTestL.setStyle("-fx-font-size: 24px;"); // Set font size
		physicalTest.getChildren().add(physicalTestL);

		GridPane gridPane = new GridPane();
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(20));

		Label firstNameLabel = new Label("First Name:");
		Label lastNameLabel = new Label("Last Name:");
		Label dobLabel = new Label("Date of Birth:");
		Label ageLable = new Label("Age:");

		Label allergieLabel = new Label("Allergies:");
		Label ShellFishLabel = new Label("Shellfish:");
		Label BeestingLabel = new Label("Beestings");
		Label foodsLabel = new Label("Foods:");

//		RadioButton yesButton1 = new RadioButton("yes");
//		RadioButton noButton1 = new RadioButton("no");
//
//		RadioButton yesButton2 = new RadioButton("yes");
//		RadioButton noButton2 = new RadioButton("no");
//
//		RadioButton yesButton3 = new RadioButton("yes");
//		RadioButton noButton3 = new RadioButton("no");
//
//		// Set ToggleGroup for each pair of yes and no buttons if necessary
//		ToggleGroup toggleGroup1 = new ToggleGroup();
//		yesButton1.setToggleGroup(toggleGroup1);
//		noButton1.setToggleGroup(toggleGroup1);
//
//		ToggleGroup toggleGroup2 = new ToggleGroup();
//		yesButton2.setToggleGroup(toggleGroup2);
//		noButton2.setToggleGroup(toggleGroup2);
//
//		ToggleGroup toggleGroup3 = new ToggleGroup();
//		yesButton3.setToggleGroup(toggleGroup3);
//		noButton3.setToggleGroup(toggleGroup3);

		// Non-editable text fields
		TextField firstNameField = new TextField();
		firstNameField.setEditable(false);
		TextField lastNameField = new TextField();
		lastNameField.setEditable(false);
		TextField dobField = new TextField();
		dobField.setEditable(false);
		TextField ageField = new TextField();
		ageField.setEditable(false);

		TextArea note = new TextArea("Please List");
		note.setPrefWidth(100);
		note.setPrefHeight(100);
		// physicalTest.getChildren().add(note);

		gridPane.addRow(0, firstNameLabel, firstNameField);
		gridPane.addRow(1, lastNameLabel, lastNameField);
		gridPane.addRow(2, dobLabel, dobField);
		gridPane.addRow(3, ageLable, ageField);
		gridPane.addRow(5, allergieLabel);
//		gridPane.addRow(6, ShellFishLabel, yesButton1, noButton1);
//		gridPane.addRow(7, BeestingLabel, yesButton2, noButton2);
//		gridPane.addRow(8, foodsLabel, yesButton3, noButton3);
		physicalTest.getChildren().add(gridPane);

		return physicalTest;

	}

	private VBox diagnosis() {
		VBox diagnosis = new VBox();
		diagnosis.setAlignment(Pos.TOP_CENTER);

		Label diagnosisL = new Label("Diagnosis");
		diagnosisL.setStyle("-fx-font-size: 24px;"); // Set font size
		diagnosis.getChildren().add(diagnosisL);

		return diagnosis;
	}
}
