package main.java.com.ahh.school.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import main.java.com.ahh.school.StartMain;
import main.java.com.ahh.school.dao.ClassDaoImpl;
import main.java.com.ahh.school.dao.StudentDaoImpl;
import main.java.com.ahh.school.dao.TeacherDaoImpl;
import main.java.com.ahh.school.dao.UserDaoImpl;
import main.java.com.ahh.school.dto.UserDto;
import main.java.com.ahh.school.entity.User;
import main.java.com.ahh.school.helper.AlertDialog;
import main.java.com.ahh.school.helper.PromptBox;
import main.java.com.ahh.school.service.*;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private Text teacher_count;

    @FXML
    private Text student_count;

    @FXML
    private Text class_count;

    private UserService userService;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TeacherService teacherService = new TeacherServiceImpl(new TeacherDaoImpl());
        StudentService studentService = new StudentServiceImpl(new StudentDaoImpl());
        ClassService classService = new ClassServiceImpl(new ClassDaoImpl());
        userService = new UserServiceImpl(new UserDaoImpl());

        teacher_count.setText(String.valueOf(teacherService.fetchAll().size()));
        student_count.setText(String.valueOf(studentService.fetchAll().size()));
        class_count.setText(String.valueOf(classService.fetchAll().size()));
    }


    @FXML
    void changePassword() {
        User user = StartMain.getCurrentUser();

        String currentPsw = PromptBox.show("Write Your Current Password", "Current Password");
        if(currentPsw.equals(user.getPassword())) {
            String newPsw = PromptBox.show("Write Your New Password", "New Password");
            if (newPsw != null) {
                user.setPassword(newPsw);
                if (userService.update(new UserDto(user)) != null) {
                    AlertDialog.show("Success", "Changing Password is successful");
                }
            }
        } else {
            AlertDialog.show("Error", "Password is incorrect");
        }


    }
    
}
