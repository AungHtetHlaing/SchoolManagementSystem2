package main.java.com.ahh.school.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import main.java.com.ahh.school.dao.ClassDaoImpl;
import main.java.com.ahh.school.dao.PayAmountDaoImpl;
import main.java.com.ahh.school.dto.ClassDto;
import main.java.com.ahh.school.dto.PayAmountDto;
import main.java.com.ahh.school.entity.PayAmount;
import main.java.com.ahh.school.entity.SchoolClass;
import main.java.com.ahh.school.helper.AlertDialog;
import main.java.com.ahh.school.service.ClassService;
import main.java.com.ahh.school.service.ClassServiceImpl;
import main.java.com.ahh.school.service.PayAmountService;
import main.java.com.ahh.school.service.PayAmountServiceImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PayAmountController implements Initializable {

    @FXML
    private ChoiceBox<SchoolClass> pay_class;

    @FXML
    private TextField pay_student;

    @FXML
    private TextField pay_teacher;

    @FXML
    private Label pay_count;



    @FXML
    private Button editBtn;

    @FXML
    private Button addBtn;

    @FXML
    private TableView<PayAmount> pay_table;

    @FXML
    private TableColumn<PayAmount, Integer> col_pay_id;

    @FXML
    private TableColumn<PayAmount, SchoolClass> col_pay_class;

    @FXML
    private TableColumn<PayAmount, Integer> col_pay_student;

    @FXML
    private TableColumn<PayAmount, Integer> col_pay_teacher;

    @FXML
    private TableColumn<PayAmount, Void> col_pay_action;


    private PayAmountService payAmountService;
    private ObservableList<PayAmount> payAmounts;

    private ObservableList<SchoolClass> schoolClasses;

    private Long payAmount_id;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        payAmountService = new PayAmountServiceImpl(new PayAmountDaoImpl());


        ClassService classService = new ClassServiceImpl(new ClassDaoImpl());
        List<ClassDto> classDtos = classService.fetchAll();
        List<SchoolClass> resultSchoolClasses = new ArrayList<>();
        for (ClassDto out : classDtos) {
            resultSchoolClasses.add(out.getEntity());
        }
        schoolClasses = FXCollections.observableArrayList(resultSchoolClasses);
        pay_class.setItems(schoolClasses);

        pay_class.setConverter(new StringConverter<>() {
            @Override
            public String toString(SchoolClass schoolClass) {
                return schoolClass.getName();
            }

            @Override
            public SchoolClass fromString(String s) {
                return null;
            }
        });

        col_pay_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_pay_class.setCellValueFactory(new PropertyValueFactory<>("schoolClass"));
        col_pay_class.setCellFactory(new Callback<>() {
            @Override
            public TableCell<PayAmount, SchoolClass> call(TableColumn<PayAmount, SchoolClass> payAmountSchoolClassTableColumn) {
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
        col_pay_student.setCellValueFactory(new PropertyValueFactory<>("student_amount"));
        col_pay_teacher.setCellValueFactory(new PropertyValueFactory<>("teacher_amount"));

        addDataToTable();
        customizeActionTableColumn();
    }

    @FXML
    void addPay() {
        try {
            int studentAmount = Integer.parseInt(pay_student.getText());
            int teacherAmount = Integer.parseInt(pay_teacher.getText());

            if (studentAmount < 0 || teacherAmount < 0 || pay_class.getSelectionModel().isEmpty()) {
                AlertDialog.show("Error", "Something is Wrong");
            } else {
                SchoolClass schoolClass = pay_class.getSelectionModel().getSelectedItem();

                PayAmount payAmount = new PayAmount(schoolClass, teacherAmount,studentAmount);
                boolean result = payAmountService.save(new PayAmountDto(payAmount));

                if (result) {
                    pay_teacher.setText("");
                    pay_student.setText("");
                    pay_class.getSelectionModel().clearSelection();

                    addDataToTable();
                }
            }
        } catch (NumberFormatException e) {
            AlertDialog.show("Error", "Write Number");
        }
    }

    public void addDataToTable() {
        List<PayAmountDto> payAmountDtos = payAmountService.fetchAll();
        List<PayAmount> resultPayAmounts = new ArrayList<>();
        for (PayAmountDto out : payAmountDtos) {
            resultPayAmounts.add(out.getEntity());
        }
        payAmounts = FXCollections.observableArrayList(resultPayAmounts);
        pay_count.setText(String.valueOf(payAmounts.size()));
        pay_table.setItems(payAmounts);

    }

    @FXML
    void cancelPay() {
        pay_student.setText("");
        pay_teacher.setText("");
        pay_class.getSelectionModel().clearSelection();

        addBtn.setDisable(false);
        addBtn.setVisible(true);

        editBtn.setDisable(true);
        editBtn.setVisible(false);

    }

    @FXML
    void editPay() {
        try {
            int studentAmount = Integer.parseInt(pay_student.getText());
            int teacherAmount = Integer.parseInt(pay_teacher.getText());

            if (studentAmount < 0 || teacherAmount < 0 || pay_class.getSelectionModel().isEmpty()) {
                AlertDialog.show("Error", "Something is Wrong");
            } else {
                SchoolClass schoolClass = pay_class.getSelectionModel().getSelectedItem();

                PayAmount payAmount = new PayAmount(schoolClass, teacherAmount, studentAmount);
                payAmount.setId(payAmount_id);
                PayAmountDto result = payAmountService.update(new PayAmountDto(payAmount));
                if (result != null) {
                    pay_teacher.setText("");
                    pay_student.setText("");
                    pay_class.getSelectionModel().clearSelection();

                    addBtn.setDisable(false);
                    addBtn.setVisible(true);

                    editBtn.setDisable(true);
                    editBtn.setVisible(false);

                    addDataToTable();
                }
            }
        }catch (NumberFormatException e) {
                AlertDialog.show("Error", "Write Number");
            }
    }


    public void customizeActionTableColumn() {
        Callback<TableColumn<PayAmount, Void>, TableCell<PayAmount, Void>> factory = new Callback<>() {
            @Override
            public TableCell<PayAmount, Void> call(TableColumn<PayAmount, Void> classSectionVoidTableColumn) {
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
                            int ind = pay_table.getSelectionModel().getFocusedIndex();
                            if (ind != -1) {
                                PayAmount payAmount = payAmounts.get(ind);
                                pay_teacher.setText(String.valueOf(payAmount.getTeacher_amount()));
                                pay_student.setText(String.valueOf(payAmount.getStudent_amount()));

                                for (SchoolClass ce : schoolClasses) {
                                    if (ce.getId().equals(payAmount.getSchoolClass().getId())) {
                                        pay_class.getSelectionModel().select(ce);
                                        break;
                                    }
                                }

                                payAmount_id = payAmount.getId();

                                addBtn.setDisable(true);
                                addBtn.setVisible(false);

                                editBtn.setDisable(false);
                                editBtn.setVisible(true);

                            }
                        });

                        deleteButton.setId("cancelBtn");
                        deleteButton.setOnAction(e -> {
                            int ind = pay_table.getSelectionModel().getFocusedIndex();
                            if (ind != -1) {
                                PayAmount payAmount = payAmounts.get(ind);
                                if (payAmountService.delete(new PayAmountDto(payAmount))) {
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
        col_pay_action.setCellFactory(factory);

    }
}