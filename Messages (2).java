package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Messages {
	
	//needs to fix positioning and add functionality to read the nurses from the file and put that into the combobox
	public static Tab createTab() {
	
		GridPane grid = new GridPane();
		ComboBox patient = new ComboBox();
		TextArea write = new TextArea();
		Button send = new Button("Send");
		Label recieved = new Label("Recieved Messages:");
		Label blank = new Label();
	
		//formating
		patient.setMaxWidth(250);
		grid.setPadding(new Insets(40));
		
		//drop box input (patient names)
		int count = lineCount();
		for (int i = 0; i < count; i++) {
			patient.getItems().add(getpatient(i));
		}
		
		//add to grid
		grid.add(patient, 0, 1);
		grid.add(write, 0, 2);
		grid.add(send, 0, 3);
		grid.add(recieved, 1, 0);
		grid.add(blank, 0, 0, 1, 5);
	
        // Creating the tab with the grid as its content
        Tab messagesTab = new Tab("Messages");
        messagesTab.setContent(grid);
        messagesTab.setClosable(false);
        return messagesTab;
    }
	
	private static String getpatient(int i) {
		String patient = "";
		try (BufferedReader reader = new BufferedReader(new FileReader("patientLogins"))) {
			String line;
			int j = 0;
			while ((line = reader.readLine()) != null) {
				if (j == i) {
					String[] fields = line.split(",");
					patient = fields[0] + " " + fields[1];
                    break;
                }
                j = j + 1;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return patient;
	}
	
	private static int lineCount() {
		int lines = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader("patientLogins"))) {
            while (reader.readLine() != null) {
                lines++;
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

    // Utility method to show alerts
    private static void showAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
