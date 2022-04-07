package main.java.com.ahh.school;

import main.java.com.ahh.school.dao.UserDaoImpl;
import main.java.com.ahh.school.dto.UserDto;
import main.java.com.ahh.school.entity.User;
import main.java.com.ahh.school.helper.ConfirmBox;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.com.ahh.school.service.UserService;
import main.java.com.ahh.school.service.UserServiceImpl;

public class StartMain extends Application {

    private static Stage stage;
    private static User user;

    @Override
    public void start(Stage primaryStage) throws Exception{

        UserService userService = new UserServiceImpl(new UserDaoImpl());

        UserDto userDto = userService.findOne(1L);
        user = userDto.getEntity();

        if (user.getName() == null) {
            User insertUser = new User();
            insertUser.setName("admin");
            insertUser.setPassword("12345");
            userService.save(new UserDto(insertUser));
            user = userService.findOne(1L).getEntity();
        }


        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/views/login.fxml"));
        root.getStylesheets().add("/main/resources/css/style.css");
        stage.setTitle("School Management System");
        stage.getIcons().add(new Image("/main/resources/img/school.png"));
        stage.setResizable(false);
        stage.setScene(new Scene(root, 600, 346));
        stage.setOnCloseRequest(e -> {
            e.consume();
            boolean flag = ConfirmBox.show("Exit", "Are you sure to exit!");
            if (flag) {
                Platform.exit();
            }
        });
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }
    public static User getCurrentUser() {
        return user;
    }
    public static void main(String[] args) {
        launch(args);
    }
}

