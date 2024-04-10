package application;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PatientDataView {

	File patientFile;

	public Scene patientTabs(Scene previousScene, Stage ownerStage, File pFile) {

		this.patientFile = pFile;

		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: white;");
		Scene scene = new Scene(root, 900, 600);

		// Create the TabPane
		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

		// Create and add tabs to the TabPane
		Tab personalInfoTab = new Tab("Personal Information");
		personalInfoTab.setContent(createPersonalInfoTab());

		Tab patientHistoryTab = new Tab("Patient History");
		patientHistoryTab.setContent(createPatientHistoryTab());

		Tab appointmentSummaryTab = new Tab("Appointment Summary");
		appointmentSummaryTab.setContent(createAppointmentSummaryTab());

		Tab insuranceInfoTab = new Tab("Insurance Information");
		insuranceInfoTab.setContent(createInsuranceInfoTab());

		Tab pharmacyInfoTab = new Tab("Pharmacy Information");
		pharmacyInfoTab.setContent(createPharmacyInfoTab());

		Tab healthResourcesTab = new Tab("Health and Wellness Resources");
		healthResourcesTab.setContent(createHealthResourcesTab());
		
		Tab logOutTab = new Tab("Log Out");
		logOutTab.setOnSelectionChanged(e -> {
			if(logOutTab.isSelected()) {
				ownerStage.setScene(previousScene);
			}
		});

		// Add tabs to the tab pane
		tabPane.getTabs().addAll(personalInfoTab, patientHistoryTab, appointmentSummaryTab, insuranceInfoTab, pharmacyInfoTab, healthResourcesTab, logOutTab);

		// Set the TabPane to the top of the BorderPane
		root.setTop(tabPane);

		return scene;
	}

	private BorderPane createHealthResourcesTab() {
		BorderPane root = new BorderPane();
		// Implement the scene content for Health & Wellness Resources
		VBox content = new VBox();
		content.setAlignment(Pos.CENTER);
		content.setSpacing(20);

		// Hyperlinks to various health and wellness resources
		Hyperlink counselingLink = new Hyperlink("Counseling Services");
		counselingLink.setOnAction(e -> openURL("https://eoss.asu.edu/counseling"));

		Hyperlink healthServicesLink = new Hyperlink("Health Services");
		healthServicesLink.setOnAction(e -> openURL("https://eoss.asu.edu/health"));

		Hyperlink fitnessWellnessLink = new Hyperlink("Sun Devil Fitness and Wellness");
		fitnessWellnessLink.setOnAction(e -> openURL("https://fitness.asu.edu/"));

		// Add hyperlinks to the content VBox
		content.getChildren().addAll(counselingLink, healthServicesLink, fitnessWellnessLink);

		// Set content VBox in the center of the BorderPane
		root.setCenter(content);

		// Set padding for the root BorderPane
		root.setPadding(new Insets(20));

		return root;
	}

	private void openURL(String url) {
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private BorderPane createPharmacyInfoTab() {
		BorderPane root = new BorderPane();

		// Labels for fields
		Label pharmacyLabel = new Label("Pharmacy:");
		Label addressLabel = new Label("Address:");

		// Text fields for editable information
		TextField pharmacyField = new TextField();
		pharmacyField.setText(fileField("Pharmacy"));

		TextField addressField = new TextField();
		addressField.setText(fileField("Address"));

		// Button to save changes
		Button saveButton = new Button("Save");
		saveButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white; -fx-font-size: 20px;");
		saveButton.setOnAction(e -> {
			// Save changes to the file
			saveChanges("Pharmacy:", pharmacyField.getText());
			saveChanges("Address:", addressField.getText());
		});

		// Layout setup
		GridPane gridPane = new GridPane();
		gridPane.addRow(0, pharmacyLabel, pharmacyField);
		gridPane.addRow(1, addressLabel, addressField);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setAlignment(Pos.CENTER);

		VBox vbox = new VBox(20, gridPane, saveButton);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(50));

		root.setCenter(vbox);

		return root;
	}

	private BorderPane createInsuranceInfoTab() {
		BorderPane root = new BorderPane();

		// Labels for fields
		Label insuranceIdLabel = new Label("Insurance ID:");
		Label phoneNumberLabel = new Label("Phone number:");

		// Text fields for editable information
		TextField insuranceIdField = new TextField();
		insuranceIdField.setText(fileField("Insurance ID"));

		TextField phoneNumberField = new TextField();
		phoneNumberField.setText(fileField("Phone Number"));

		// Button to save changes
		Button saveButton = new Button("Save");
		saveButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white; -fx-font-size: 20px;");
		saveButton.setOnAction(e -> {

			// Save changes to the file
			saveChanges("Insurance ID:", insuranceIdField.getText());
			saveChanges("Phone Number:", phoneNumberField.getText());
		});

		// Layout setup
		GridPane gridPane = new GridPane();
		gridPane.addRow(0, insuranceIdLabel, insuranceIdField);
		gridPane.addRow(1, phoneNumberLabel, phoneNumberField);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setAlignment(Pos.CENTER);

		VBox vbox = new VBox(20, gridPane, saveButton);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(50));

		root.setCenter(vbox);

		return root;
	}

	private BorderPane createAppointmentSummaryTab() {
		BorderPane root = new BorderPane();

		Label summaryLabel = new Label("Diagnosis by Doctor:");
		summaryLabel.setFont(Font.font("Arial", 30));

		// TextArea to display appointment summary
		TextArea summaryTextArea = new TextArea();
		summaryTextArea.setPrefRowCount(10);
		summaryTextArea.setEditable(false);
		summaryTextArea.setText(retrieveTextArea("Diagnosis"));

		// Layout setup
		VBox vbox = new VBox(20, summaryLabel, summaryTextArea);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(50));

		root.setCenter(vbox);

		return root;
	}
	
	private String retrieveTextArea(String fieldName) {
		StringBuilder notesBuilder = new StringBuilder();

		try (BufferedReader br = new BufferedReader(new FileReader(patientFile))) {
			String line;
			boolean startNotes = false;

			while ((line = br.readLine()) != null) {
				if (startNotes) {

					if (line.contains(":")) {
						break;
					}

					notesBuilder.append(line).append("\n");
				} else {
					String[] fields = line.split(":");

					if (fields[0].trim().equals(fieldName)) {
						notesBuilder.append(fields[1].trim()).append("\n");
						startNotes = true;

					}

				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return notesBuilder.toString();

	}

	private BorderPane createPatientHistoryTab() {
		BorderPane root = new BorderPane();

		// Labels for fields
		Label prevHealthLabel = new Label("Previous Health Issues:");
		Label prevMedicationsLabel = new Label("Previous Medications:");
		Label historyOfImmunLabel = new Label("History of Immunization:");

		// Text fields for editable information
		TextField prevHealthField = new TextField();
		prevHealthField.setText(fileField("Previous Health Issues"));

		TextField prevMedicationsField = new TextField();
		prevMedicationsField.setText(fileField("Previous Medications"));

		TextField historyOfImmunField = new TextField();
		historyOfImmunField.setText(fileField("History of Immunization"));

		// Button to save changes
		Button saveButton = new Button("Save");
		saveButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white; -fx-font-size: 20px;");
		saveButton.setOnAction(e -> {
			// Save changes to the file
//            saveChanges(insuranceIdField.getText(), phoneNumberField.getText());
			saveChanges("Previous Health Issues:", prevHealthField.getText());
			saveChanges("Previous Medications:", prevMedicationsField.getText());
			saveChanges("History of Immunization:", historyOfImmunField.getText());
		});

		// Layout setup
		GridPane gridPane = new GridPane();
		gridPane.addRow(0, prevHealthLabel, prevHealthField);
		gridPane.addRow(1, prevMedicationsLabel, prevMedicationsField);
		gridPane.addRow(2, historyOfImmunLabel, historyOfImmunField);
		
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setAlignment(Pos.CENTER);

		VBox vbox = new VBox(20, gridPane, saveButton);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(50));

		root.setCenter(vbox);

		return root;
	}

	private BorderPane createPersonalInfoTab() {

		BorderPane root = new BorderPane();

		// Labels for fields
		Label firstNameLabel = new Label("First Name:");
		Label lastNameLabel = new Label("Last Name:");
		Label dobLabel = new Label("Date of Birth:");

		// Text fields for editable information
		TextField firstNameField = new TextField();
		firstNameField.setText(fileField("First Name"));

		TextField lastNameField = new TextField();
		lastNameField.setText(fileField("Last Name"));

		DatePicker dobField = new DatePicker();
		String dateOnFile = fileField("Date of Birth");
		if (dateOnFile.equals("")) {
			dobField.setValue(null);
		} else {
			dobField.setValue(LocalDate.parse(dateOnFile));
		}

		// Button to save changes
		Button saveButton = new Button("Save");
		saveButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white; -fx-font-size: 20px;");
		saveButton.setOnAction(e -> {

			// Save changes to the file
			saveChanges("First Name:", firstNameField.getText());
			saveChanges("Last Name:", lastNameField.getText());
			String dob = "";
			if (dobField.getValue() == null) {
				saveChanges("Date of Birth:", "");
			} else {
				dob = dobField.getValue().toString();
				saveChanges("Date of Birth:", dob);
			}

			File newPatientFile = new File("patientInfo",
					firstNameField.getText() + "_" + lastNameField.getText() + "_" + dob + "_" + "patientinfo.txt");
			patientFile.renameTo(newPatientFile);
			patientFile = newPatientFile;

		});

		// Layout setup
		GridPane gridPane = new GridPane();
		gridPane.addRow(0, firstNameLabel, firstNameField);
		gridPane.addRow(1, lastNameLabel, lastNameField);
		gridPane.addRow(2, dobLabel, dobField);

		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setAlignment(Pos.CENTER);

		VBox vbox = new VBox(20, gridPane, saveButton);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(50));

		root.setCenter(vbox);

		return root;

	}

	private void saveChanges(String fieldName, String newFieldString) {
		System.out.println("This is hte fieldname: " + fieldName);
		System.out.println("This is hte newFieldString: " + newFieldString);
		try (BufferedReader reader = new BufferedReader(new FileReader(patientFile))) {
			StringBuilder fileContent = new StringBuilder();
			String line;

			if (patientFile.length() == 0) {
				fileContent.append(fieldName).append(" ").append(newFieldString).append("\n");
			} else {
				boolean addField = false;
				while ((line = reader.readLine()) != null) {
					if (line.startsWith(fieldName)) {
						line = fieldName + " " + newFieldString;
						addField = true;
					}
					fileContent.append(line).append("\n");

				}

				if (!addField) {
					fileContent.append(fieldName).append(" ").append(newFieldString).append("\n");
				}
			}

			System.out.println("This is the fileContent: " + fileContent.toString());
			// Write the updated content back to the file
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(patientFile))) {
				writer.write(fileContent.toString());
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String mutipleFileFields(String field1, String field2) {
		String resultString = "";
		StringBuilder fileContent = new StringBuilder();
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(patientFile))) {
			String line;
			

			while ((line = br.readLine()) != null) {
				String[] fields = line.split(":");

				if (fields[0].trim().equals(field1) || fields[0].trim().equals(field2)) {
					fileContent.append(fields[1].trim()).append("\n");

				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		resultString = fileContent.toString();
		return resultString;
		
		
		
	}

	private String fileField(String field) {
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

}
