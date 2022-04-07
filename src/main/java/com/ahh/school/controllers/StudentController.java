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
import main.java.com.ahh.school.dao.StudentDaoImpl;
import main.java.com.ahh.school.dto.ClassDto;
import main.java.com.ahh.school.dto.StudentDto;
import main.java.com.ahh.school.entity.SchoolClass;
import main.java.com.ahh.school.entity.Student;
import main.java.com.ahh.school.helper.AlertDialog;
import main.java.com.ahh.school.repository.StudentRepository;
import main.java.com.ahh.school.service.ClassService;
import main.java.com.ahh.school.service.ClassServiceImpl;
import main.java.com.ahh.school.service.StudentService;
import main.java.com.ahh.school.service.StudentServiceImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    private TextField s_name;

    @FXML
    private TextField s_age;

    @FXML
    private TextField s_phone;

    @FXML
    private ChoiceBox<String> s_gender;

    @FXML
    private ChoiceBox<SchoolClass> stu_class;

    @FXML
    private TextArea s_address;

    @FXML
    private Label s_count;

    @FXML
    private TextField s_search_box;

    @FXML
    private Button searchBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Button addBtn;

    @FXML
    private TableView<Student> stu_table;

    @FXML
    private TableColumn<Student, Integer> col_stu_id;

    @FXML
    private TableColumn<Student, String> col_stu_name;

    @FXML
    private TableColumn<Student, Integer> col_stu_age;

    @FXML
    private TableColumn<Student, String> col_stu_phone;

    @FXML
    private TableColumn<Student, String> col_stu_gender;


    @FXML
    private TableColumn<Student, SchoolClass> col_stu_class;

    @FXML
    private TableColumn<Student, String> col_stu_address;

    @FXML
    private TableColumn<Student, Void> actionColumn;

    private StudentService studentService;
    private ObservableList<Student> students;

    private ObservableList<SchoolClass> schoolClasses;

    private Long student_id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ImageView searchImg = new ImageView("/main/resources/img/search.png");
        searchImg.setFitWidth(20);
        searchImg.setFitHeight(20);
        searchBtn.setGraphic(searchImg);


        StudentRepository studentRepository = new StudentDaoImpl();
        studentService = new StudentServiceImpl(studentRepository);

        ObservableList<String> genders = FXCollections.observableArrayList("male", "female");
        s_gender.setItems(genders);

        ClassService classService = new ClassServiceImpl(new ClassDaoImpl());
        List<ClassDto> classDtos = classService.fetchAll();
        List<SchoolClass> resultSchoolClasses = new ArrayList<>();
        for (ClassDto out : classDtos) {
            resultSchoolClasses.add(out.getEntity());
        }
        schoolClasses = FXCollections.observableArrayList(resultSchoolClasses);
        stu_class.setItems(schoolClasses);

        stu_class.setConverter(new StringConverter<>() {
            @Override
            public String toString(SchoolClass schoolClass) {
                return schoolClass.getName();
            }

            @Override
            public SchoolClass fromString(String s) {
                return null;
            }
        });


        col_stu_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_stu_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_stu_age.setCellValueFactory(new PropertyValueFactory<>("age"));
        col_stu_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_stu_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_stu_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        col_stu_class.setCellValueFactory(new PropertyValueFactory<>("schoolClass"));
        col_stu_class.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Student, SchoolClass> call(TableColumn<Student, SchoolClass> payAmountSchoolClassTableColumn) {
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
    void search() {
        String searchTxt = s_search_box.getText();
        List<StudentDto> studentDtos = studentService.fetchAllByName(searchTxt);
        List<Student> resultStudents = new ArrayList<>();
        for (StudentDto out : studentDtos) {
            resultStudents.add(out.getEntity());
        }
        students = FXCollections.observableArrayList(resultStudents);
        s_count.setText(String.valueOf(students.size()));
        stu_table.setItems(students);
    }

    @FXML
    void addStudent() {
        try {
            String name = s_name.getText();
            int age = Integer.parseInt(s_age.getText());
            String phone = s_phone.getText();
            String address = s_address.getText();

            if (age < 0  || phone.isEmpty() || name.isEmpty() || address.isEmpty() || s_gender.getSelectionModel().isEmpty() || stu_class.getSelectionModel().isEmpty()) {
                AlertDialog.show("Error", "Something is Wrong");
            } else {
                if (phone.length() > 11) {
                    throw new Exception("Phone No must be 11 length");
                }
                String gender = s_gender.getSelectionModel().getSelectedItem();
                SchoolClass schoolClass = stu_class.getSelectionModel().getSelectedItem();
                Student student = new Student(name,age, phone, gender, address, schoolClass);

                if (studentService.save(new StudentDto(student))) {

                        s_name.setText("");
                        s_age.setText("");
                        s_phone.setText("");
                        s_address.setText("");
                        s_gender.getSelectionModel().clearSelection();
                        stu_class.getSelectionModel().clearSelection();

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

        List<StudentDto> studentDtos = studentService.fetchAll();

        List<Student> resultStudents = new ArrayList<>();
        for (StudentDto out : studentDtos) {
            resultStudents.add(out.getEntity());
        }
        students = FXCollections.observableArrayList(resultStudents);
        s_count.setText(String.valueOf(students.size()));
        stu_table.setItems(students);
    }

    @FXML
    void editStudent() {
        try {
            String name = s_name.getText();
            int age = Integer.parseInt(s_age.getText());
            String phone = s_phone.getText();
            String address = s_address.getText();

            if (age < 0  || phone.isEmpty() || name.isEmpty() || address.isEmpty() || s_gender.getSelectionModel().isEmpty()|| stu_class.getSelectionModel().isEmpty()) {
                AlertDialog.show("Error", "Something is Wrong");
            } else {
                if (phone.length() > 11) {
                    throw new Exception("Phone No must be 11 length");
                }
                String gender = s_gender.getSelectionModel().getSelectedItem();
                SchoolClass schoolClass = stu_class.getSelectionModel().getSelectedItem();
                Student student = new Student(name,age, phone, gender, address, schoolClass);
                student.setId(student_id);

                if (studentService.update(new StudentDto(student)) != null) {
                    s_name.setText("");
                    s_age.setText("");
                    s_phone.setText("");
                    s_address.setText("");
                    s_gender.getSelectionModel().clearSelection();
                    stu_class.getSelectionModel().clearSelection();


                    addBtn.setDisable(false);
                    addBtn.setVisible(true);

                    editBtn.setDisable(true);
                    editBtn.setVisible(false);

                    addDataToTable();
                }
            }
        }catch (NumberFormatException e) {
            AlertDialog.show("Error", "Write Number");
        } catch (Exception e) {
            AlertDialog.show("Error", e.getMessage());
        }
    }

    @FXML
    void cancelStudent() {
        s_name.setText("");
        s_age.setText("");
        s_phone.setText("");
        s_address.setText("");
        s_search_box.setText("");
        s_gender.getSelectionModel().clearSelection();
        stu_class.getSelectionModel().clearSelection();

        addBtn.setDisable(false);
        addBtn.setVisible(true);

        editBtn.setDisable(true);
        editBtn.setVisible(false);
    }

    public void customizeActionTableColumn() {
        Callback<TableColumn<Student, Void>, TableCell<Student, Void>> factory = new Callback<>() {
            @Override
            public TableCell<Student, Void> call(TableColumn<Student, Void> classSectionVoidTableColumn) {
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
                            int ind = stu_table.getSelectionModel().getFocusedIndex();
                            if (ind != -1) {
                                Student student = students.get(ind);
                                s_name.setText(student.getName());
                                s_age.setText(String.valueOf(student.getAge()));
                                s_phone.setText(student.getPhone());
                                s_address.setText(student.getAddress());
                                s_gender.getSelectionModel().select(student.getGender());

                                for (SchoolClass ce : schoolClasses) {
                                    if (ce.getId().equals(student.getSchoolClass().getId())) {
                                        stu_class.getSelectionModel().select(ce);
                                        break;
                                    }
                                }

                                student_id = student.getId();

                                addBtn.setDisable(true);
                                addBtn.setVisible(false);

                                editBtn.setDisable(false);
                                editBtn.setVisible(true);

                            }
                        });

                        deleteButton.setId("cancelBtn");
                        deleteButton.setOnAction(e -> {
                            int ind = stu_table.getSelectionModel().getFocusedIndex();

                            if (ind != -1) {
                                Student student = students.get(ind);
                                if (studentService.delete(new StudentDto(student))) {
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
