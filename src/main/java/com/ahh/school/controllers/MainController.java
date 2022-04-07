package main.java.com.ahh.school.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import main.java.com.ahh.school.helper.ConfirmBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button nav_home;

    @FXML
    private Button nav_teachers;

    @FXML
    private Button nav_students;

    @FXML
    private Button nav_classes;

    @FXML
    private Button nav_subjects;

    @FXML
    private Button nav_pay_amount;

    @FXML
    private Button nav_exit;

    private Parent layout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nav_home.setGraphic(getImage("/main/resources/img/home.png"));
        nav_teachers.setGraphic(getImage("/main/resources/img/home.png"));
        nav_students.setGraphic(getImage("/main/resources/img/student.png"));
        nav_classes.setGraphic(getImage("/main/resources/img/home.png"));
        nav_subjects.setGraphic(getImage("/main/resources/img/subject.png"));
        nav_pay_amount.setGraphic(getImage("/main/resources/img/pay.png"));
        nav_exit.setGraphic(getImage("/main/resources/img/sad.png"));
    }

    private ImageView getImage(String path) {
        ImageView img = new ImageView(path);
        img.setFitWidth(20);
        img.setFitHeight(20);
        return img;
    }

    private void showLayout(Parent layout) {
        HBox hBox = LoginController.gethBox();
        if (hBox.getChildren().size() > 1) {
            hBox.getChildren().remove( 1);
        }
        LoginController.gethBox().getChildren().add(layout);
    }

    @FXML
    void showTeachers() {
        try {
            layout = FXMLLoader.load(getClass().getResource("/main/resources/views/teacher.fxml"));
            showLayout(layout);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void showClasses() {
        try {
            layout = FXMLLoader.load(getClass().getResource("/main/resources/views/class.fxml"));
            showLayout(layout);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void showHome() {
        try {
            layout = FXMLLoader.load(getClass().getResource("/main/resources/views/home.fxml"));
            showLayout(layout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exit() {
        boolean flag = ConfirmBox.show("Exit", "Are you sure to exit!");
        if (flag) {
            Platform.exit();
        }
    }

    @FXML
    void showPayAmount() {
        try {
            layout = FXMLLoader.load(getClass().getResource("/main/resources/views/pay_amount.fxml"));
            showLayout(layout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showStudents() {

        try {
            layout = FXMLLoader.load(getClass().getResource("/main/resources/views/student.fxml"));
            showLayout(layout);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void showSubjects() {
        try {
            layout = FXMLLoader.load(getClass().getResource("/main/resources/views/subject.fxml"));
            showLayout(layout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
