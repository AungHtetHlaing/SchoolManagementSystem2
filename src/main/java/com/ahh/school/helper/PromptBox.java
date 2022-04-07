package main.java.com.ahh.school.helper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PromptBox {

    private static String userInput;

    public static String show(String title, String txt) {

        Stage stage = new Stage();
        VBox root = new VBox();

        Label label = new Label(txt);
        label.setStyle("-fx-font-family: Cambria; -fx-font-size: 16;");

        TextField textField = new TextField();

        HBox hBox1 = new HBox();
        hBox1.setSpacing(10);
        hBox1.getChildren().addAll(label, textField);

        HBox hBox2 = new HBox();
        hBox2.setSpacing(5);
        Button okBtn = new Button("OK");
        okBtn.setId("okBtn");
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setId("cancelBtn");
        hBox2.getChildren().addAll(okBtn, cancelBtn);


        okBtn.setOnAction(e -> {

            userInput = textField.getText();

            if (userInput.isEmpty()) {
                AlertDialog.show("Error", "User Input is empty");
            } else {
              textField.setText("");
              stage.close();
            }
        });

        cancelBtn.setOnAction( e -> stage.close());

        root.getChildren().addAll(hBox1, hBox2);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getStylesheets().add("/main/resources/css/style.css");
        root.setSpacing(10);

        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.initStyle(StageStyle.UTILITY);
        stage.setMinWidth(350);
        stage.setMinHeight(150);
        stage.setResizable(false);
        stage.setScene(new Scene(root,350,150));
        stage.setTitle(title);
        stage.showAndWait(); // waiting return value; show is not waiting return value;

        return userInput;
    }
}
