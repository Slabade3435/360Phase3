package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class StaffPortal {
	public Scene staffLogin(Scene previousScene, Stage ownerStage) {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 900, 600);
		// Staff Label
		Label staffLabel = new Label("Staff Portal");
		staffLabel.setFont(Font.font("Arial", 40));
		staffLabel.setTextFill(Color.web("#0844a4"));

		// Back Button
		Button backToMain = new Button("Back");
		backToMain.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white; -fx-font-size: 20px;");
		backToMain.setOnAction(e -> {
			Stage stage = (Stage) backToMain.getScene().getWindow();
			stage.setScene(previousScene);
		});

		// Top Menu
		HBox topMenu = new HBox();
		topMenu.getChildren().addAll(backToMain, staffLabel);
		topMenu.setAlignment(Pos.TOP_LEFT);
		topMenu.setSpacing(270.0);
		topMenu.setPadding(new Insets(10));

		// Set the menu at the top
		root.setTop(topMenu);
		root.setAlignment(topMenu, Pos.TOP_CENTER);

		// Username Label and input field
		Label staffUserName = new Label("Username");
		staffUserName.setFont(Font.font("Arial", 20));
		staffUserName.setTextFill(Color.BLACK);

		TextField staffUserNameField = new TextField();

		HBox userNameHolder = new HBox();
		userNameHolder.getChildren().addAll(staffUserName, staffUserNameField);
		userNameHolder.setAlignment(Pos.CENTER);
		userNameHolder.setSpacing(10);

		// password Label and input field
		Label passwordLabel = new Label("Password");
		passwordLabel.setFont(Font.font("Arial", 20));
		passwordLabel.setTextFill(Color.BLACK);

		TextField passwordField = new TextField();

		HBox passwordHolder = new HBox();
		passwordHolder.getChildren().addAll(passwordLabel, passwordField);
		passwordHolder.setAlignment(Pos.CENTER);
		passwordHolder.setSpacing(10);


		// Login button
		Button staffLoginButton = new Button("Login");
		staffLoginButton.setStyle("-fx-background-color: #0844a4; -fx-text-fill: white; -fx-font-size: 20px;");
		staffLoginButton.setOnAction(e -> {
			String userNameInput = staffUserNameField.getText();
			String passwordInput = passwordField.getText();

			if (userNameInput.equals("") && passwordInput.equals("")) {

				createPopup("Please input the Username and Password", true);

			} else if (userNameInput.equals("")) {

				createPopup("Please input the Username", true);

			} else if (passwordInput.equals("")) {

				createPopup("Please input the Password", true);
			} else {

				if (checkLogin(userNameInput, passwordInput)) {
					System.out.println("User exists within the DB");
//					ownerStage.setScene(new staffPatientLookUp().staffPatientLookUp(scene, ownerStage, userNameInput));
				} else {
					System.out.println("User DOES NOT exist");
					// creates a popUp at the specified location
					createPopup("The Staff member does not Exist within the Database", true);

				}
			}

		});

		// adding everything to a VBox
		VBox staffPortalVbox = new VBox();
		staffPortalVbox.getChildren().addAll(userNameHolder, passwordHolder, staffLoginButton);
		staffPortalVbox.setAlignment(Pos.CENTER);
		staffPortalVbox.setSpacing(50);
		staffPortalVbox.setPadding(new Insets(-100, 0, 0, 0));

		// setting the vbox to the center
		root.setCenter(staffPortalVbox);

		return scene;

	}

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

	private boolean checkLogin(String userName, String password) {

		boolean foundUser = false;

		try (BufferedReader reader = new BufferedReader(new FileReader("staffLogins"))) {
			String userLogin = userName + "," + password;
			String line;
			boolean isFirstLine = true;
			while ((line = reader.readLine()) != null) {
				if (isFirstLine == true) {
					isFirstLine = false;
				} else {
					if (line.equals(userLogin)) {
						foundUser = true;
						break;
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return foundUser;
	}
}
