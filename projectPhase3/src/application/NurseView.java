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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NurseView {

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
		Tab vitalsTab = new Tab("Vitals");
		vitalsTab.setContent(createVitalsTab());

		Tab allergiesTab = new Tab("Allergies");
		allergiesTab.setContent(createAllergiesTab());

		Tab healthConcernsTab = new Tab("Health Concerns");
		healthConcernsTab.setContent(createHealthConcernsTab());
		
		Tab patientHistoryTab = new Tab("Patient History");
		patientHistoryTab.setContent(createpatientHistoryTab());

		Messages patientMessaging = new Messages();
		
		Tab logOutTab = new Tab("Back to Patient Look Up");
		logOutTab.setOnSelectionChanged(e -> {
			if(logOutTab.isSelected()) {
				ownerStage.setScene(previousScene);
			}
		});
		
		// Add tabs to the tab pane
		tabPane.getTabs().addAll(vitalsTab, allergiesTab, healthConcernsTab, patientHistoryTab, patientMessaging.createTab(), logOutTab );

		// Set the TabPane to the top of the BorderPane
		root.setTop(tabPane);

		return scene;
	}

	private BorderPane createpatientHistoryTab() {

		BorderPane root = new BorderPane();

		// Labels for fields
		Label prevHealthIssuesLabel = new Label("Previous Health Issues:");
		Label prevMedsLabel = new Label("Previously Prescribed Medication:");
		Label immunizationLabel = new Label("History of Immunization:");

		// Text fields for editable information
		TextField prevHealthIssuesField = new TextField();
		prevHealthIssuesField.setEditable(false);
		prevHealthIssuesField.setText(fileField("Previous Health Issues"));

		TextField prevMedsField = new TextField();
		prevMedsField.setEditable(false);
		prevMedsField.setText(fileField("Previous Medications"));

		TextField immunizationField = new TextField();
		immunizationField.setEditable(false);
		immunizationField.setText(fileField("History of Immunization"));
		
		// Layout setup
		GridPane gridPane = new GridPane();
		gridPane.addRow(0, prevHealthIssuesLabel, prevHealthIssuesField);
		gridPane.addRow(1, prevMedsLabel, prevMedsField);
		gridPane.addRow(2, immunizationLabel, immunizationField);
		
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setAlignment(Pos.CENTER);

		VBox vbox = new VBox(20, gridPane);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(50));

		root.setCenter(vbox);

		return root;

	}
	
	private BorderPane createAllergiesTab() {
	    BorderPane root = new BorderPane();

	    Label allergiesLabel = new Label("Allergies:");
	    allergiesLabel.setFont(Font.font("Arial", 30));

	    // TextArea to display known allergies
	    TextArea allergiesTextArea = new TextArea(retrieveTextArea("Allergies"));
	    allergiesTextArea.setPrefRowCount(10);
	    allergiesTextArea.setEditable(true);

	    // Button to save changes
	    Button saveButton = new Button("Save");
		saveButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white; -fx-font-size: 20px;");
		saveButton.setOnAction(e -> {
	       
	        saveChangesForTextArea(allergiesTextArea, "Allergies:"); 
	    });

	    // Layout setup
	    VBox vbox = new VBox(20, allergiesLabel, allergiesTextArea, saveButton);
	    vbox.setAlignment(Pos.CENTER);
	    vbox.setPadding(new Insets(50));

	    root.setCenter(vbox);

	    return root;
	}
	
	private BorderPane createHealthConcernsTab() {
	    BorderPane root = new BorderPane();

	    Label healthConcernsLabel = new Label("Health Concerns:");
	    healthConcernsLabel.setFont(Font.font("Arial", 30));

	    // TextArea to display health concerns
	    TextArea healthConcersnTextArea = new TextArea(retrieveTextArea("Health Concerns"));
	    healthConcersnTextArea.setPrefRowCount(10);
	    healthConcersnTextArea.setEditable(true);

	    // Button to save changes
	    Button saveButton = new Button("Save");
		saveButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white; -fx-font-size: 20px;");
		saveButton.setOnAction(e -> {
	       
	        saveChangesForTextArea(healthConcersnTextArea, "Health Concerns:"); 
	    });

	    // Layout setup
	    VBox vbox = new VBox(20, healthConcernsLabel, healthConcersnTextArea, saveButton);
	    vbox.setAlignment(Pos.CENTER);
	    vbox.setPadding(new Insets(50));

	    root.setCenter(vbox);

	    return root;
	}

	private BorderPane createVitalsTab() {

		BorderPane root = new BorderPane();

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

		TextField lastNameField = new TextField();
		lastNameField.setText(fileField("Last Name"));

		DatePicker dobField = new DatePicker();
		String dateOnFile = fileField("Date of Birth");
		if (dateOnFile.equals("")) {
			dobField.setValue(null);
		} else {
			dobField.setValue(LocalDate.parse(dateOnFile));
		}
		
		TextField ageField = new TextField();
		ageField.setText(fileField("Age"));
		
		TextField weightField = new TextField();
		weightField.setText(fileField("Weight"));
		
		TextField heightField = new TextField();
		heightField.setText(fileField("Height"));
		
		TextField bodyTemperatureField = new TextField();
		bodyTemperatureField.setText(fileField("Body Temperature"));
		
		TextField bloodPressureField = new TextField();
		bloodPressureField.setText(fileField("Blood Pressure"));
		
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
			
			saveChanges("Age:", ageField.getText());
			saveChanges("Weight:", weightField.getText());
			saveChanges("Height:", heightField.getText());
			saveChanges("Body Temperature:", bodyTemperatureField.getText());
			saveChanges("Blood Pressure:", bloodPressureField.getText());

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
		gridPane.addRow(3, ageLabel, ageField);
		gridPane.addRow(4, heightLabel, heightField);
		gridPane.addRow(5, weightLabel, weightField);
		gridPane.addRow(6, bodyTemperatureLabel, bodyTemperatureField);
		gridPane.addRow(7, bloodPressureLabel, bloodPressureField);
		
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
//                    System.out.println("Not ")
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
	
}
