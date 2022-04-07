package main.java.com.ahh.school.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import main.java.com.ahh.school.dao.ClassDaoImpl;
import main.java.com.ahh.school.dao.SubjectDaoImpl;
import main.java.com.ahh.school.dto.ClassDto;
import main.java.com.ahh.school.dto.SubjectDto;
import main.java.com.ahh.school.entity.SchoolClass;
import main.java.com.ahh.school.entity.Subject;
import main.java.com.ahh.school.helper.AlertDialog;
import main.java.com.ahh.school.service.ClassService;
import main.java.com.ahh.school.service.ClassServiceImpl;
import main.java.com.ahh.school.service.SubjectService;
import main.java.com.ahh.school.service.SubjectServiceImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SubjectController implements Initializable {

    @FXML
    private ListView<SchoolClass> sub_class;

    @FXML
    private TextField sub_name;

    @FXML
    private Label sub_count;

    @FXML
    private TableView<Subject> sub_table;


    @FXML
    private TableColumn<Subject, Integer> col_sub_id;

    @FXML
    private TableColumn<Subject, String> col_sub_name;


    @FXML
    private TableColumn<Subject, List<SchoolClass>> col_sub_class;

    @FXML
    private TableColumn<Subject, Void> actionColumn;

    @FXML
    private Button editBtn;

    @FXML
    private Button addBtn;


    private ObservableList<Subject> subjects;
    private SubjectService subjectService;

    private ObservableList<SchoolClass> schoolClasses;

    private Long subject_id;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        subjectService = new SubjectServiceImpl(new SubjectDaoImpl());

        ClassService classService = new ClassServiceImpl(new ClassDaoImpl());
        List<ClassDto> classDtos = classService.fetchAll();
        List<SchoolClass> resultSchoolClasses = new ArrayList<>();
        for (ClassDto out : classDtos) {
            resultSchoolClasses.add(out.getEntity());
        }
        schoolClasses = FXCollections.observableArrayList(resultSchoolClasses);
        sub_class.setItems(schoolClasses);

        sub_class.setCellFactory(TextFieldListCell.forListView(new StringConverter<>() {
            @Override
            public String toString(SchoolClass schoolClass) {
                return schoolClass.getName();
            }

            @Override
            public SchoolClass fromString(String s) {
                return null;
            }
        }));
        sub_class.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        col_sub_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_sub_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_sub_class.setCellValueFactory(new PropertyValueFactory<>("schoolClasses"));


        col_sub_class.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Subject, List<SchoolClass>> call(TableColumn<Subject, List<SchoolClass>> subjectListTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(List<SchoolClass> schoolClasses1, boolean b) {
                        super.updateItem(schoolClasses1, b);
                        if (b) {
                            setGraphic(null);
                        } else {
                            ListView<SchoolClass> schoolClassListView = new ListView<>();

                            schoolClassListView.setPrefHeight(80);
                            schoolClassListView.setItems(FXCollections.observableArrayList(schoolClasses1));
                            schoolClassListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<>() {
                                @Override
                                public String toString(SchoolClass schoolClass) {
                                    return schoolClass.getName();
                                }

                                @Override
                                public SchoolClass fromString(String s) {
                                    return null;
                                }
                            }));
                            setGraphic(schoolClassListView);
                        }
                    }
                };
            }
        });



        addDataToTable();
        customizeActionTableColumn();
    }

    @FXML
    void addSubject() {

        String subjectName = sub_name.getText();

        if (subjectName.isEmpty() || sub_class.getSelectionModel().isEmpty()) {
            AlertDialog.show("Error", "Something is empty");
        } else {
            List<SchoolClass> schoolClass = sub_class.getSelectionModel().getSelectedItems();
            Subject subject = new Subject(subjectName);
            subject.getSchoolClasses().addAll(schoolClass);
            boolean subResult = subjectService.save(new SubjectDto(subject));

            if (subResult) {
                sub_name.setText("");
                sub_class.getSelectionModel().clearSelection();
                addDataToTable();
            }
        }

    }

    @FXML
    void cancelSubject() {
        sub_name.setText("");
        sub_class.getSelectionModel().clearSelection();
        addBtn.setDisable(false);
        addBtn.setVisible(true);

        editBtn.setDisable(true);
        editBtn.setVisible(false);
    }


    @FXML
    void editSubject() {
        String subjectName = sub_name.getText();
        if (subjectName.isEmpty() || sub_class.getSelectionModel().isEmpty()) {
            AlertDialog.show("Error", "Subject is empty");
        } else {

            List<SchoolClass> schoolClass = sub_class.getSelectionModel().getSelectedItems();
            Subject subject = new Subject(subjectName);
            subject.setId(subject_id);
            subject.getSchoolClasses().addAll(schoolClass);
            SubjectDto subResult = subjectService.update(new SubjectDto(subject));


            if (subResult != null) {
                sub_name.setText("");
                sub_class.getSelectionModel().clearSelection();

                addBtn.setDisable(false);
                addBtn.setVisible(true);

                editBtn.setDisable(true);
                editBtn.setVisible(false);

                addDataToTable();
            }
        }


    }

    public void addDataToTable() {
        List<SubjectDto> subjectDtos = subjectService.fetchAll();
        List<Subject> resultSubjects = new ArrayList<>();
        for (SubjectDto out : subjectDtos) {
            resultSubjects.add(out.getEntity());
        }
        subjects = FXCollections.observableArrayList(resultSubjects);
        sub_count.setText(String.valueOf(subjects.size()));
        sub_table.setItems(subjects);
    }


    public void customizeActionTableColumn() {
        Callback<TableColumn<Subject, Void>, TableCell<Subject, Void>> factory = new Callback<>() {
            @Override
            public TableCell<Subject, Void> call(TableColumn<Subject, Void> classSectionVoidTableColumn) {
                return new TableCell<>() {
                    final HBox hBox = new HBox();
                    final Button editButton = new Button("Edit");
                    final Button deleteButton = new Button("Delete");
                    final Button addClass = new Button("Add Class");


                    {
                        hBox.setSpacing(10);
                        hBox.setPadding(new Insets(0, 10, 0, 10));
                        hBox.getChildren().addAll(editButton, deleteButton, addClass);
                        editButton.setId("editBtn");
                        editButton.setOnAction(e -> {
                            int ind = sub_table.getSelectionModel().getFocusedIndex();
                            if (ind != -1) {
                                Subject subject = subjects.get(ind);
                                sub_name.setText(subject.getName());
                                subject_id = subject.getId();


                                for (SchoolClass ce : schoolClasses) {
                                    for (SchoolClass sc : subject.getSchoolClasses()) {
                                        if (sc.getId().equals(ce.getId())) {
                                            sub_class.getSelectionModel().select(ce);

                                        }
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
                            int ind = sub_table.getSelectionModel().getFocusedIndex();
                            if (ind != -1) {
                                Subject subject = subjects.get(ind);
                                if (subjectService.delete(new SubjectDto(subject))) {
                                    addDataToTable();
                                }
                            }
                        });

                        addClass.setId("addClassBtn");
                        addClass.setOnAction(e -> {
                            int ind = sub_table.getSelectionModel().getFocusedIndex();
                            if (ind != -1) {
                                Subject subject = subjects.get(ind);
                                List<SchoolClass> schoolClass = sub_class.getSelectionModel().getSelectedItems();
                                if (schoolClass == null) {
                                    AlertDialog.show("Error", "Please Select Class");
                                } else {
                                    subject.getSchoolClasses().addAll(schoolClass);
                                    if (subjectService.update(new SubjectDto(subject)) != null) {
                                        addDataToTable();
                                    }
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

