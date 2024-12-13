package model;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Creating UI components
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label surnameLabel = new Label("Surname:");
        TextField surnameField = new TextField();

        Label creditCardLabel = new Label("Credit Card:");
        TextField creditCardField = new TextField();

        Button submitButton = new Button("Submit");

        // Layout setup
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(surnameLabel, 0, 1);
        gridPane.add(surnameField, 1, 1);
        gridPane.add(creditCardLabel, 0, 2);
        gridPane.add(creditCardField, 1, 2);
        gridPane.add(submitButton, 1, 3);

        // Event handling
        submitButton.setOnAction(event -> {
            String name = nameField.getText();
            String surname = surnameField.getText();
            String creditCard = creditCardField.getText();

            System.out.println("Reservation Details:");
            System.out.println("Name: " + name);
            System.out.println("Surname: " + surname);
            System.out.println("Credit Card: " + creditCard);
        });

        // Scene setup
        Scene scene = new Scene(gridPane, 400, 200);
        primaryStage.setTitle("Hotel Reservation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
