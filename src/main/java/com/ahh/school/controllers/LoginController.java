package main.java.com.ahh.school.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.java.com.ahh.school.StartMain;
import main.java.com.ahh.school.dao.UserDaoImpl;
import main.java.com.ahh.school.dto.UserDto;
import main.java.com.ahh.school.entity.User;
import main.java.com.ahh.school.helper.AlertDialog;
import main.java.com.ahh.school.helper.ConfirmBox;
import main.java.com.ahh.school.service.UserService;
import main.java.com.ahh.school.service.UserServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    private static HBox hBox;

    UserService userService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userService = new UserServiceImpl(new UserDaoImpl());
    }

    @FXML
    void cancel() {
        if (ConfirmBox.show("Exit", "Are you sure to exit without login!")){
            Platform.exit();
        }
    }

    @FXML
    void login() throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();

        if (username.isEmpty() || password.isEmpty()) {
            AlertDialog.show("Error", "Email or Password is empty");
        } else {
            UserDto userDto = userService.findOne(1L);
            User user = userDto.getEntity();
            boolean result = user.getName().equals(username) && user.getPassword().equals(password);
            if (result) {
                hBox = FXMLLoader.load(getClass().getResource("/main/resources/views/main.fxml"));
                Parent home = FXMLLoader.load(getClass().getResource("/main/resources/views/home.fxml"));
                hBox.getChildren().add(home);
                Stage stage = StartMain.getStage();
                stage.setMaximized(true);
                stage.setResizable(true);

                stage.setScene(new Scene(hBox, 1600, 1080));

            } else {
                AlertDialog.show("Error", "Email or Password is incorrect");
                this.username.setText("");
                this.password.setText("");
            }
        }
    }

    public static HBox gethBox() {
        return hBox;
    }
}
