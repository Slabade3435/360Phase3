package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DoctorView {
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
		Tab PatientHisTab = new Tab("History");
		PatientHisTab.setContent(createHistoryContent());
		
		Tab PatientVitalTab = new Tab("Vital History");
		PatientVitalTab.setContent(createVitalHistory());

		Tab PhysicalTestTab = new Tab("Physical Test");
		PhysicalTestTab.setContent(physicalTest());

		Tab DiagnosisTab = new Tab("Diagnosis");
		DiagnosisTab.setContent(diagnosis());

		Tab PrescriptionTab = new Tab("Prescription");
		PrescriptionTab.setContent(prescription());

		Tab NotesTab = new Tab("Notes");
		NotesTab.setContent(notes());

		Messages doctorMessaging = new Messages();
		
		Messages doctorMessaging = new Messages();
		
		Tab logOutTab = new Tab("Back to Patient Look up");
		logOutTab.setOnSelectionChanged(e -> {
			if(logOutTab.isSelected()) {
				ownerStage.setScene(previousScene);
			}
		});
		
		// Add tabs to the tab pane
    
		tabPane.getTabs().addAll(PatientHisTab, PhysicalTestTab, DiagnosisTab, PrescriptionTab, NotesTab, doctorMessaging.createTab(), logOutTab);


		// Set the TabPane to the top of the BorderPane
		root.setTop(tabPane);

		return scene;

	}
	// reads the files
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

	//displays the layout and contents in the history tab
	private VBox createHistoryContent() {
		VBox historyContent = new VBox();
		historyContent.setAlignment(Pos.TOP_CENTER);
		//history title
		Label historyLabel = new Label("History");
		historyLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #0844a4;"); // Set font size and color
		historyContent.getChildren().add(historyLabel);

		GridPane gridPane = new GridPane();
		gridPane.setVgap(10); // Reduced vertical gap
		gridPane.setHgap(10);
		gridPane.setPadding(new Insets(20));
		//Labels
		Label firstNameLabel = new Label("First Name:");
		Label lastNameLabel = new Label("Last Name:");
		Label dobLabel = new Label("Date of Birth:");
		Label ageLabel = new Label("Age:");

		// Non-editable text fields
		TextField firstNameField = new TextField();
		firstNameField.setEditable(false);
		firstNameField.setText(fileField("First Name"));

		TextField lastNameField = new TextField();
		lastNameField.setEditable(false);
		lastNameField.setText(fileField("Last Name"));

		DatePicker dobField = new DatePicker();
		String dateOnFile = fileField("Date of Birth");
		if (dateOnFile.equals("")) {
			dobField.setValue(null);
		} else {
			dobField.setValue(LocalDate.parse(dateOnFile));
		}

		TextField ageField = new TextField();
		ageField.setEditable(false);
		ageField.setText(fileField("Age"));

		gridPane.addRow(0, firstNameLabel, firstNameField);
		gridPane.addRow(1, lastNameLabel, lastNameField);
		gridPane.addRow(2, dobLabel, dobField);
		gridPane.addRow(3, ageLabel, ageField);

		// Create a VBox for buttons
		VBox buttonBox = new VBox(10);
		buttonBox.setAlignment(Pos.BOTTOM_LEFT);
		buttonBox.setPadding(new Insets(20, 0, 0, 20)); // Set padding from top and left

		Button immunizationButton = new Button("Immunization Records");
		immunizationButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white;");
		immunizationButton.setPrefSize(200, 50); // Set button size

		Button medicalHistoryButton = new Button("Previous Medical History");
		medicalHistoryButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white;");
		medicalHistoryButton.setPrefSize(200, 50); // Set button size

		Button medicationButton = new Button("Previous Medication");
		medicationButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white;");
		medicationButton.setPrefSize(200, 50); // Set button size

		// Create a TextArea
		TextArea textArea = new TextArea();
		textArea.setEditable(false);
		double height = 250; // making a variable called height with a value 150
		double width = 400; // making a variable called height with a value 300
		textArea.setPrefHeight(height);
		textArea.setPrefWidth(width);

		HBox totalBox = new HBox(10);
		totalBox.getChildren().addAll(buttonBox, textArea);

		// Add event handlers to update text area
		immunizationButton.setOnAction(e -> textArea.setText(fileField("History of Immunization")));
		medicalHistoryButton.setOnAction(e -> textArea.setText(fileField("Previous Health Issues")));
		medicationButton.setOnAction(e -> textArea.setText(fileField("Previous Medications")));

		// Add buttons to the button VBox
		buttonBox.getChildren().addAll(immunizationButton, medicalHistoryButton, medicationButton);

		// Add a vertical gap between the grid pane and the button VBox
		VBox.setMargin(buttonBox, new Insets(20, 0, 0, 0));

		// Add the button VBox and text area to the historyContent VBox

		historyContent.getChildren().addAll(gridPane, totalBox);

		return historyContent;
	}

	
	private BorderPane createVitalHistory() {
		
		BorderPane root = new BorderPane();
		
		Label vitalLabel = new Label("Patient Vitals");
		vitalLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #0844a4;");
		root.setAlignment(vitalLabel, Pos.TOP_CENTER);
		root.setTop(vitalLabel);

		// Labels for fields
		Label firstNameLabel = new Label("First Name:");
		Label lastNameLabel = new Label("Last Name:");
		Label dobLabel = new Label("Date of Birth:");
		Label ageLabel = new Label("Age:");
		Label weightLabel = new Label("Weight:");
		Label heightLabel = new Label("Height:");
		Label bodyTemperatureLabel = new Label("Body Temperature:");
		Label bloodPressureLabel = new Label("Blood Pressure:");

		// Text fields for editable information
		TextField firstNameField = new TextField();
		firstNameField.setText(fileField("First Name"));
		firstNameField.setEditable(false);

		TextField lastNameField = new TextField();
		lastNameField.setText(fileField("Last Name"));
		lastNameField.setEditable(false);

		DatePicker dobField = new DatePicker();
		String dateOnFile = fileField("Date of Birth");
		if (dateOnFile.equals("")) {
			dobField.setValue(null);
		} else {
			dobField.setValue(LocalDate.parse(dateOnFile));
		}
		dobField.setEditable(false);
		
		TextField ageField = new TextField();
		ageField.setText(fileField("Age"));
		ageField.setEditable(false);
		
		TextField weightField = new TextField();
		weightField.setText(fileField("Weight"));
		weightField.setEditable(false);
		
		TextField heightField = new TextField();
		heightField.setText(fileField("Height"));
		heightField.setEditable(false);
		
		TextField bodyTemperatureField = new TextField();
		bodyTemperatureField.setText(fileField("Body Temperature"));
		bodyTemperatureField.setEditable(false);
		
		TextField bloodPressureField = new TextField();
		bloodPressureField.setText(fileField("Blood Pressure"));
		bloodPressureField.setEditable(false);

		// Layout setup
		GridPane gridPane = new GridPane();
		gridPane.addRow(0, firstNameLabel, firstNameField);
		gridPane.addRow(1, lastNameLabel, lastNameField);
		gridPane.addRow(2, dobLabel, dobField);
		gridPane.addRow(3, ageLabel, ageField);
		gridPane.addRow(4, heightLabel, heightField);
		gridPane.addRow(5, weightLabel, weightField);
		gridPane.addRow(6, bodyTemperatureLabel, bodyTemperatureField);
		gridPane.addRow(7, bloodPressureLabel, bloodPressureField);
		
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);
	    gridPane.setAlignment(Pos.CENTER);

	    VBox vbox = new VBox(20, gridPane);
	    vbox.setAlignment(Pos.CENTER);
	    vbox.setPadding(new Insets(50));

	    root.setCenter(vbox);

	    return root;
	}


	//displays layout and contents of the physical test tab

	private VBox physicalTest() {
		VBox physicalTest = new VBox();
		physicalTest.setAlignment(Pos.TOP_CENTER);

		Label physicalTestL = new Label("Physical");
		physicalTestL.setStyle("-fx-font-size: 24px; -fx-text-fill: #0844a4;"); // Set font size
		physicalTest.getChildren().add(physicalTestL);

		GridPane gridPane = new GridPane();
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		gridPane.setPadding(new Insets(20));

		Label firstNameLabel = new Label("First Name:");
		Label lastNameLabel = new Label("Last Name:");
		Label dobLabel = new Label("Date of Birth:");
		Label ageLable = new Label("Age:");

		Label allergieLabel = new Label("Allergies:");

		// Non-editable text fields
		TextField firstNameField = new TextField();
		firstNameField.setEditable(false);
		firstNameField.setText(fileField("First Name"));

		TextField lastNameField = new TextField();
		lastNameField.setEditable(false);
		lastNameField.setText(fileField("Last Name"));

		DatePicker dobField = new DatePicker();
		String dateOnFile = fileField("Date of Birth");
		if (dateOnFile.equals("")) {
			dobField.setValue(null);
		} else {
			dobField.setValue(LocalDate.parse(dateOnFile));
		}

		TextField ageField = new TextField();
		ageField.setEditable(false);
		ageField.setText(fileField("Age"));

		TextArea allergyNote = new TextArea(retrieveTextArea("Allergies"));

		VBox textBox = new VBox(10);
		textBox.getChildren().add(allergyNote);
		textBox.setAlignment(Pos.BOTTOM_LEFT);
		HBox totalBox = new HBox(10);
		totalBox.getChildren().add(textBox);

		gridPane.addRow(0, firstNameLabel, firstNameField);
		gridPane.addRow(1, lastNameLabel, lastNameField);
		gridPane.addRow(2, dobLabel, dobField);
		gridPane.addRow(3, ageLable, ageField);
		gridPane.addRow(4, allergieLabel, allergyNote);
		physicalTest.getChildren().addAll(gridPane, totalBox);

		Button saveButton = new Button("Save");
		saveButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white; -fx-font-size: 20px;");
		saveButton.setOnAction(e -> {
			saveChangesForTextArea(allergyNote, "Allergies:");
		});
//		Button saveButton = createSaveButtonForTextArea(allergyNote, "Allergies");
		physicalTest.getChildren().add(saveButton);

		return physicalTest;

	}

	private void saveChangesForTextArea(TextArea notesTextArea, String fieldName) {

		try (BufferedReader reader = new BufferedReader(new FileReader(patientFile))) {
			StringBuilder fileContent = new StringBuilder();
			String line;
			boolean foundField = false;
			boolean addField = false;

			while ((line = reader.readLine()) != null) {

				if (line.startsWith(fieldName)) {
					foundField = true;
					addField = true;
				}

				if (foundField) {
//					System.out.println("Not ")
					if (!line.startsWith(fieldName)) {
						if (line.contains(":")) {
							fileContent.append(fieldName).append(" ").append(notesTextArea.getText()).append("\n");
							fileContent.append(line).append("\n");
							foundField = false;
						}
					}

				} else {
					fileContent.append(line).append("\n");
				}

			}

			if (!addField) {
				fileContent.append(fieldName).append(" ").append(notesTextArea.getText()).append("\n");
			} else if (addField && foundField) {
				fileContent.append(fieldName).append(" ").append(notesTextArea.getText()).append("\n");
			}

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

	private VBox diagnosis() {
		VBox diagnosis = new VBox();
		diagnosis.setAlignment(Pos.TOP_CENTER);

		Label diagnosisL = new Label("Diagnosis");
		diagnosisL.setStyle("-fx-font-size: 24px; -fx-text-fill: #0844a4;"); // Set font size
		diagnosis.getChildren().add(diagnosisL);

		TextArea diagnosisT = new TextArea(retrieveTextArea("Diagnosis"));
		diagnosisT.setPrefWidth(300);
		diagnosisT.setPrefHeight(300);

		Button saveButton = new Button("Save");
		saveButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white; -fx-font-size: 20px;");
		saveButton.setOnAction(e -> {
			saveChangesForTextArea(diagnosisT, "Diagnosis:");
		});

		HBox totalBox = new HBox(10);
		totalBox.getChildren().add(diagnosisT); // Add text area and
		totalBox.getChildren().add(saveButton);
		// button
		totalBox.setAlignment(Pos.CENTER);

		diagnosis.getChildren().add(totalBox);
		return diagnosis;
	}

	private VBox prescription() {
		VBox prescription = new VBox();
		prescription.setAlignment(Pos.TOP_CENTER);

		Label preSLabel = new Label("Prescription");
		preSLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #0844a4;"); // Set font size

		GridPane gridPane = new GridPane();
		gridPane.setVgap(5); // Reduced vertical gap
		gridPane.setPadding(new Insets(20));
		gridPane.setAlignment(Pos.CENTER);

		Label mediactionLabel = new Label("Medication: ");
		Label doseLabel = new Label("Dose (mg): ");
		Label frequencyLabel = new Label("Frequency :");

		// Non-editable text fields
		TextField medicationField = new TextField();
		TextField doseField = new TextField();
		TextField frequencyField = new TextField();

		gridPane.addRow(0, mediactionLabel, medicationField);
		gridPane.addRow(1, doseLabel, doseField);
		gridPane.addRow(2, frequencyLabel, frequencyField);

		// Create TableView for prescriptions
		// Create TableView for prescriptions
		TableView<PrescriptionEntry> prescriptionTable = new TableView<>();
		TableColumn<PrescriptionEntry, String> medicationColumn = new TableColumn<>("Medication");
		medicationColumn.setCellValueFactory(new PropertyValueFactory<>("medication"));
		medicationColumn.setMinWidth(200); // Set minimum width for medication column

		TableColumn<PrescriptionEntry, String> doseColumn = new TableColumn<>("Dose (mg)");
		doseColumn.setCellValueFactory(new PropertyValueFactory<>("dose"));
		doseColumn.setMinWidth(200); // Set minimum width for dose column

		TableColumn<PrescriptionEntry, String> frequencyColumn = new TableColumn<>("Frequency");
		frequencyColumn.setCellValueFactory(new PropertyValueFactory<>("frequency"));
		frequencyColumn.setMinWidth(200); // Set minimum width for frequency column

		prescriptionTable.getColumns().addAll(medicationColumn, doseColumn, frequencyColumn); // IMPORTANT DO NOT
																								// DELETE: DISPLAYS
		// TABLE COLUMNS

		Button saveButton = new Button("Save");
		saveButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white; -fx-font-size: 20px;");
		saveButton.setOnAction(event -> {
			String medication = medicationField.getText();
			String dose = doseField.getText();
			String frequency = frequencyField.getText();

			try (FileWriter writer = new FileWriter(patientFile, true)) {
				writer.write("Medication: " + medication + "\n");
				writer.write("Dose (mg): " + dose + "\n");
				writer.write("Frequency: " + frequency + "\n\n");
				writer.flush();
				medicationField.clear();
				doseField.clear();
				frequencyField.clear();
				updatePrescriptionTable(prescriptionTable);
			} catch (IOException e) {
				System.err.println("Error writing to file: " + e.getMessage());
			}

		});

		HBox buttonBox = new HBox(10);
		buttonBox.getChildren().add(saveButton);
		buttonBox.setAlignment(Pos.CENTER);

		updatePrescriptionTable(prescriptionTable);

		prescription.getChildren().addAll(preSLabel, gridPane, buttonBox, prescriptionTable);

		return prescription;
	}

	private void updatePrescriptionTable(TableView<PrescriptionEntry> prescriptionTable) {

		try (BufferedReader reader = new BufferedReader(new FileReader(patientFile))) {
			ObservableList<PrescriptionEntry> entries = FXCollections.observableArrayList();
			String line;
			String medication = null;
			String dose = null;
			String frequency = null;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("Medication: ")) {
					medication = line.substring("Medication: ".length());
				} else if (line.startsWith("Dose (mg): ")) {
					dose = line.substring("Dose (mg): ".length());
				} else if (line.startsWith("Frequency: ")) {
					frequency = line.substring("Frequency: ".length());
					entries.add(new PrescriptionEntry(medication, dose, frequency));
					medication = null;
					dose = null;
					frequency = null;
				}
			}
			prescriptionTable.setItems(entries);
		} catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}
	}

	// Define a class to represent a prescription entry
	public class PrescriptionEntry {
		private String medication;
		private String dose;
		private String frequency;

		public PrescriptionEntry(String medication, String dose, String frequency) {
			this.medication = medication;
			this.dose = dose;
			this.frequency = frequency;
		}

		public String getMedication() {
			return medication;
		}

		public String getDose() {
			return dose;
		}

		public String getFrequency() {
			return frequency;
		}
	}

	private VBox notes() {
		VBox notes = new VBox();
		notes.setAlignment(Pos.TOP_CENTER);

		Label noteLabel = new Label("Notes");
		noteLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #0844a4;"); // Set font size
		notes.getChildren().add(noteLabel);

		GridPane gridPane = new GridPane();
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(20));

		Label notesLabel = new Label("Additional Notes:");
		gridPane.addRow(0, notesLabel);

		TextArea notesTextArea = new TextArea(retrieveTextArea("Additional Notes"));
		notesTextArea.setPrefWidth(400);
		notesTextArea.setPrefHeight(400);

		HBox totalBox = new HBox(10);
		Button saveButton = new Button("Save");
		saveButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white; -fx-font-size: 20px;");
		saveButton.setOnAction(e -> {
			saveChangesForTextArea(notesTextArea, "Additional Notes:");
		});

		totalBox.getChildren().addAll(notesTextArea, saveButton);
		totalBox.setAlignment(Pos.CENTER);

		notes.getChildren().addAll(gridPane, totalBox);
		return notes;
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

}
