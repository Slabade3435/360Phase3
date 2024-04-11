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

public class Messages2 {
	
	//needs to fix positioning and add functionality to read the nurses from the file and put that into the combobox
	public static Tab createTab() {
	
		GridPane grid = new GridPane();
		ComboBox staff = new ComboBox();
		TextArea write = new TextArea();
		Button send = new Button("Send");
		Label recieved = new Label("Recieved Messages:");
		Label blank = new Label();
	
		//formating
		staff.setMaxWidth(250);
		grid.setPadding(new Insets(40));
		
		//drop box input (staff names)
		int count = lineCount();
		for (int i = 0; i < count; i++) {
			staff.getItems().add(getStaff(i));
		}
		
		//add to grid
		grid.add(staff, 0, 1);
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
	
	private static String getStaff(int i) {
		String staffMember = "";
		try (BufferedReader reader = new BufferedReader(new FileReader("staffLogins"))) {
			String line;
			int j = 0;
			while ((line = reader.readLine()) != null) {
				if (j == i) {
					String[] fields = line.split(",");
					staffMember = fields[0] + " " + fields[1] + " , " + fields[2];
                    break;
                }
                j = j + 1;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return staffMember;
	}
	
	private static int lineCount() {
		int lines = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader("staffLogins"))) {
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