package main.java.com.ahh.school.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import main.java.com.ahh.school.dao.ClassDaoImpl;
import main.java.com.ahh.school.dao.TeacherDaoImpl;
import main.java.com.ahh.school.dto.ClassDto;
import main.java.com.ahh.school.dto.TeacherDto;
import main.java.com.ahh.school.entity.SchoolClass;
import main.java.com.ahh.school.entity.Teacher;
import main.java.com.ahh.school.helper.AlertDialog;
import main.java.com.ahh.school.service.ClassService;
import main.java.com.ahh.school.service.ClassServiceImpl;
import main.java.com.ahh.school.service.TeacherService;
import main.java.com.ahh.school.service.TeacherServiceImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TeacherController implements Initializable {
    @FXML
    private TextField t_name;

    @FXML
    private TextField t_age;

    @FXML
    private TextField t_phone;

    @FXML
    private ChoiceBox<String> t_gender;


    @FXML
    private ChoiceBox<SchoolClass> t_class;

    @FXML
    private TextArea t_address;

    @FXML
    private Button editBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private Label count;

    @FXML
    private TextField search_box;

    @FXML
    private TableView<Teacher> t_table;
    @FXML
    private TableColumn<Teacher, Integer> col_t_id;

    @FXML
    private TableColumn<Teacher, String> col_t_name;

    @FXML
    private TableColumn<Teacher, Integer> col_t_age;

    @FXML
    private TableColumn<Teacher, String> col_t_phone;

    @FXML
    private TableColumn<Teacher, String> col_t_gender;

    @FXML
    private TableColumn<Teacher, SchoolClass> col_t_class;

    @FXML
    private TableColumn<Teacher, String> col_t_address;

    @FXML
    private TableColumn<Teacher, Void> actionColumn;


    private ObservableList<Teacher> teachers;
    private TeacherService teacherService;

    private ObservableList<SchoolClass> schoolClasses;

    private Long teacher_id;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ImageView searchImg = new ImageView("/main/resources/img/search.png");
        searchImg.setFitWidth(20);
        searchImg.setFitHeight(20);
        searchBtn.setGraphic(searchImg);

        teacherService = new TeacherServiceImpl(new TeacherDaoImpl());

        ObservableList<String> genders = FXCollections.observableArrayList("male", "female");
        t_gender.setItems(genders);

        ClassService classService = new ClassServiceImpl(new ClassDaoImpl());
        List<ClassDto> classDtos = classService.fetchAll();
        List<SchoolClass> resultSchoolClasses = new ArrayList<>();
        for (ClassDto out : classDtos) {
            resultSchoolClasses.add(out.getEntity());
        }
        schoolClasses = FXCollections.observableArrayList(resultSchoolClasses);
        t_class.setItems(schoolClasses);

        t_class.setConverter(new StringConverter<>() {
            @Override
            public String toString(SchoolClass schoolClass) {
                return schoolClass.getName();
            }

            @Override
            public SchoolClass fromString(String s) {
                return null;
            }
        });

        col_t_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_t_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_t_age.setCellValueFactory(new PropertyValueFactory<>("age"));
        col_t_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_t_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_t_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        col_t_class.setCellValueFactory(new PropertyValueFactory<>("schoolClass"));
        col_t_class.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Teacher, SchoolClass> call(TableColumn<Teacher, SchoolClass> payAmountSchoolClassTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(SchoolClass schoolClass, boolean b) {
                        super.updateItem(schoolClass, b);
                        if (b) {
                            setGraphic(null);
                        } else {
                            setGraphic(new Text(schoolClass.getName()));
                        }
                    }
                };
            }
        });


        addDataToTable();
        customizeActionTableColumn();
    }


    @FXML
    void addTeacher() {
        try {
            String name = t_name.getText();
            int age = Integer.parseInt(t_age.getText());
            String phone = t_phone.getText();
            String address = t_address.getText();

            if (age < 0  || phone.isEmpty() || name.isEmpty() || address.isEmpty() || t_gender.getSelectionModel().isEmpty() || t_class.getSelectionModel().isEmpty()) {
                AlertDialog.show("Error", "Something is Wrong");
            } else {
                if (phone.length() > 11) {
                    throw new Exception("Phone No must be 11 length");
                }
                String gender = t_gender.getSelectionModel().getSelectedItem();
                SchoolClass schoolClass = t_class.getSelectionModel().getSelectedItem();
                Teacher teacher = new Teacher(name,age,phone,gender,address,schoolClass);
                if (teacherService.save(new TeacherDto(teacher))) {

                    t_name.setText("");
                    t_age.setText("");
                    t_phone.setText("");
                    t_address.setText("");
                    t_gender.getSelectionModel().clearSelection();
                    t_class.getSelectionModel().clearSelection();


                    addDataToTable();

                }
            }

        } catch (NumberFormatException e) {
            AlertDialog.show("Error", "Write Number");
        } catch (Exception e) {
            AlertDialog.show("Error", e.getMessage());
        }


    }

    public void addDataToTable() {
        List<TeacherDto> classDtos = teacherService.fetchAll();
        List<Teacher> results = new ArrayList<>();
        for (TeacherDto out : classDtos) {
            results.add(out.getEntity());
        }
        teachers = FXCollections.observableArrayList(results);
        count.setText(String.valueOf(teachers.size()));
        t_table.setItems(teachers);
    }

    @FXML
    void cancelTeacher() {
        t_name.setText("");
        t_age.setText("");
        t_phone.setText("");
        t_address.setText("");
        search_box.setText("");
        t_class.getSelectionModel().clearSelection();
        t_gender.getSelectionModel().clearSelection();

        addBtn.setDisable(false);
        addBtn.setVisible(true);

        editBtn.setDisable(true);
        editBtn.setVisible(false);
    }

    @FXML
    void editTeacher() {

        try {
            String name = t_name.getText();
            int age = Integer.parseInt(t_age.getText());
            String phone = t_phone.getText();
            String address = t_address.getText();

            if (age < 0  || phone.isEmpty() || name.isEmpty() || address.isEmpty() || t_gender.getSelectionModel().isEmpty() || t_class.getSelectionModel().isEmpty()) {
                AlertDialog.show("Error", "Something is Wrong");
            } else {
                if (phone.length() > 11) {
                    throw new Exception("Phone No must be 11 length");
                }
                String gender = t_gender.getSelectionModel().getSelectedItem();
                SchoolClass schoolClass = t_class.getSelectionModel().getSelectedItem();
                Teacher teacher = new Teacher(name,age,phone,gender,address, schoolClass);
                teacher.setId(teacher_id);
                if (teacherService.update(new TeacherDto(teacher)) != null) {

                    t_name.setText("");
                    t_age.setText("");
                    t_phone.setText("");
                    t_address.setText("");
                    t_gender.getSelectionModel().clearSelection();
                    t_class.getSelectionModel().clearSelection();

                    addBtn.setDisable(false);
                    addBtn.setVisible(true);

                    editBtn.setDisable(true);
                    editBtn.setVisible(false);

                    addDataToTable();

                }
            }

        } catch (NumberFormatException e) {
            AlertDialog.show("Error", "Write Number");
        } catch (Exception e) {
            AlertDialog.show("Error", e.getMessage());
        }
    }

    @FXML
    void search() {
        String searchTxt = search_box.getText();

        List<TeacherDto> teacherDtos = teacherService.fetchAllByName(searchTxt);
        List<Teacher> teacherResults= new ArrayList<>();
        for (TeacherDto out : teacherDtos) {
            teacherResults.add(out.getEntity());
        }
        teachers = FXCollections.observableArrayList(teacherResults);
        count.setText(String.valueOf(teachers.size()));
        t_table.setItems(teachers);
    }



    public void customizeActionTableColumn() {
        Callback<TableColumn<Teacher, Void>, TableCell<Teacher, Void>> factory = new Callback<>() {
            @Override
            public TableCell<Teacher, Void> call(TableColumn<Teacher, Void> classSectionVoidTableColumn) {
                return new TableCell<>() {
                    final HBox hBox = new HBox();
                    final Button editButton = new Button("Edit");
                    final Button deleteButton = new Button("Delete");


                    {
                        hBox.setSpacing(10);
                        hBox.setPadding(new Insets(0, 10, 0, 10));
                        hBox.getChildren().addAll(editButton, deleteButton);
                        editButton.setId("editBtn");
                        editButton.setOnAction(e -> {
                            int ind = t_table.getSelectionModel().getFocusedIndex();
                            if (ind != -1) {
                                Teacher teacher = teachers.get(ind);
                                t_name.setText(teacher.getName());
                                t_age.setText(String.valueOf(teacher.getAge()));
                                t_phone.setText(teacher.getPhone());
                                t_address.setText(teacher.getAddress());
                                t_gender.getSelectionModel().select(teacher.getGender());
                                teacher_id = teacher.getId();

                                for (SchoolClass ce : schoolClasses) {
                                    if (ce.getId().equals(teacher.getSchoolClass().getId())) {
                                        t_class.getSelectionModel().select(ce);
                                        break;
                                    }
                                }


                                addBtn.setDisable(true);
                                addBtn.setVisible(false);

                                editBtn.setDisable(false);
                                editBtn.setVisible(true);

                            }
                        });

                        deleteButton.setId("cancelBtn");
                        deleteButton.setOnAction(e -> {
                            int ind = t_table.getSelectionModel().getFocusedIndex();

                            if (ind != -1) {
                                Teacher teacher = teachers.get(ind);
                                if (teacherService.delete(new TeacherDto(teacher))) {
                                    addDataToTable();
                                }
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void unused, boolean b) {
                        super.updateItem(unused, b);

                        if (b) {
                            setGraphic(null);
                        } else {
                            setGraphic(hBox);
                        }
                    }
                };
            }

        };
        actionColumn.setCellFactory(factory);

    }
}
