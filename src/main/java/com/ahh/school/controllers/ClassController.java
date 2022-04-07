package main.java.com.ahh.school.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import main.java.com.ahh.school.dao.ClassDaoImpl;
import main.java.com.ahh.school.dto.ClassDto;
import main.java.com.ahh.school.entity.SchoolClass;
import main.java.com.ahh.school.helper.AlertDialog;
import main.java.com.ahh.school.service.ClassService;
import main.java.com.ahh.school.service.ClassServiceImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClassController implements Initializable {
    @FXML
    private TextField c_name;

    @FXML
    private Button editBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Label c_count;

    @FXML
    private TextField c_search_box;

    @FXML
    private Button searchBtn;

    @FXML
    private TableView<SchoolClass> classTable;

    @FXML
    private TableColumn<SchoolClass, Integer> col_class_id;

    @FXML
    private TableColumn<SchoolClass, String> col_class_name;


    @FXML
    private TableColumn<SchoolClass, Void> actionColumn;


    private ObservableList<SchoolClass> schoolClasses;
    private ClassService classService;
    private Long class_id = 0L;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        classService = new ClassServiceImpl(new ClassDaoImpl());

        ImageView searchImg = new ImageView("/main/resources/img/search.png");
        searchImg.setFitWidth(20);
        searchImg.setFitHeight(20);
        searchBtn.setGraphic(searchImg);

        col_class_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_class_name.setCellValueFactory(new PropertyValueFactory<>("name"));

        addDataToTable();
        customizeActionTableColumn();

    }

    @FXML
    void search() {
        String searchTxt = c_search_box.getText();

        List<ClassDto> classDtos = classService.fetchAllByName(searchTxt);
        List<SchoolClass> resultClasses = new ArrayList<>();
        for (ClassDto out : classDtos) {
            resultClasses.add(out.getEntity());
        }
        schoolClasses = FXCollections.observableArrayList(resultClasses);
        c_count.setText(String.valueOf(schoolClasses.size()));
        classTable.setItems(schoolClasses);
    }

    @FXML
    void addClass() {
        String className = c_name.getText();

        if (className.isEmpty()) {
            AlertDialog.show("Error", "Something is empty");
        } else {
            SchoolClass schoolClass = new SchoolClass(className);
            boolean result = classService.save(new ClassDto(schoolClass));

            if (result) {
                c_name.setText("");
                addDataToTable();
            }
        }

    }


    @FXML
    void editClass() {
        String className = c_name.getText();

        if (className.isEmpty()) {
            AlertDialog.show("Error", "ClassName is empty");
        } else {
            SchoolClass schoolClass = new SchoolClass(className);
            schoolClass.setId(class_id);
            boolean result = classService.save(new ClassDto(schoolClass));

            if (result) {
                c_name.setText("");

                addBtn.setDisable(false);
                addBtn.setVisible(true);

                editBtn.setDisable(true);
                editBtn.setVisible(false);

                addDataToTable();
            }
        }


    }

    @FXML
    void cancelClass() {
        c_name.setText("");
        c_search_box.setText("");

        addBtn.setDisable(false);
        addBtn.setVisible(true);

        editBtn.setDisable(true);
        editBtn.setVisible(false);

    }



    public void addDataToTable() {
        List<ClassDto> classDtos = classService.fetchAll();
        List<SchoolClass> resultClasses = new ArrayList<>();
        for (ClassDto out : classDtos) {
            resultClasses.add(out.getEntity());
        }
        schoolClasses = FXCollections.observableArrayList(resultClasses);
        c_count.setText(String.valueOf(schoolClasses.size()));
        classTable.setItems(schoolClasses);

    }

    public void customizeActionTableColumn() {
        Callback<TableColumn<SchoolClass, Void>, TableCell<SchoolClass, Void>> factory = new Callback<>() {
            @Override
            public TableCell<SchoolClass, Void> call(TableColumn<SchoolClass, Void> classSectionVoidTableColumn) {
                return new TableCell<>() {
                    final HBox hBox = new HBox();
                    final Button editButton = new Button("Edit");


                    {
                        hBox.setSpacing(10);
                        hBox.setAlignment(Pos.CENTER);
                        hBox.setPadding(new Insets(0, 10, 0, 10));
                        hBox.getChildren().addAll(editButton);
                        editButton.setId("editBtn");
                        editButton.setOnAction(e -> {
                            int ind = classTable.getSelectionModel().getFocusedIndex();
                            if (ind != -1) {
                                SchoolClass schoolClass = schoolClasses.get(ind);
                                c_name.setText(schoolClass.getName());
                                class_id = schoolClass.getId();

                                addBtn.setDisable(true);
                                addBtn.setVisible(false);

                                editBtn.setDisable(false);
                                editBtn.setVisible(true);

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
