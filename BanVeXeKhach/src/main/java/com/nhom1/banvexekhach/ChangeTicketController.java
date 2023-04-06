/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.banvexekhach;

import com.nhom1.pojo.Ticket;
import com.nhom1.services.TicketServices;
import com.nhom1.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author fptshop.com.vn
 */
public class ChangeTicketController implements Initializable {

    @FXML
    private TextField tfTicketChange;
    @FXML
    private TableView<Ticket> tbChangeTicket;
    @FXML
    private TextField tfStart;
    @FXML
    private TextField tfEnd;
    @FXML
    private DatePicker dpDate;
    @FXML
    private TextField tfTime;
    @FXML
    private TextField tfChair;
    @FXML
    private Button btnClose;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.loadTableTicketChange();
        this.loadTableDataChange();

        this.tfStart.textProperty().addListener(o -> {
            this.searchTableDataChange(this.tfStart.getText(),
                    this.tfEnd.getText(), this.tfChair.getText(), this.dpDate.getValue(),
                    this.tfTime.getText());
        });
        this.tfEnd.textProperty().addListener(o -> {
            this.searchTableDataChange(this.tfStart.getText(),
                    this.tfEnd.getText(), this.tfChair.getText(), this.dpDate.getValue(),
                    this.tfTime.getText());
        });
        this.tfChair.textProperty().addListener(o -> {
            this.searchTableDataChange(this.tfStart.getText(),
                    this.tfEnd.getText(), this.tfChair.getText(), this.dpDate.getValue(),
                    this.tfTime.getText());
        });
        this.dpDate.valueProperty().addListener(o -> {
            this.searchTableDataChange(this.tfStart.getText(),
                    this.tfEnd.getText(), this.tfChair.getText(), this.dpDate.getValue(),
                    this.tfTime.getText());
        });
        this.tfTime.textProperty().addListener(o -> {
            this.searchTableDataChange(this.tfStart.getText(),
                    this.tfEnd.getText(), this.tfChair.getText(), this.dpDate.getValue(),
                    this.tfTime.getText());
        });
        btnClose.setOnAction(eh -> {
            try {
                this.btnCloseHandler(eh);
            } catch (IOException ex) {
                Logger.getLogger(ChangeTicketController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void loadTableTicketChange() {
//      Tạo cột mã vé
        TableColumn colIDTicket = new TableColumn("Mã vé");
        colIDTicket.setCellValueFactory(new PropertyValueFactory("id"));
        colIDTicket.setPrefWidth(100);
//      Tạo cột Ghế
        TableColumn colChair = new TableColumn("Ghế");
        colChair.setCellValueFactory(new PropertyValueFactory("chair"));
        colChair.setPrefWidth(100);
//      Tạo cột trạng thái của vé
        TableColumn colStatus = new TableColumn("Trạng thái");
        colStatus.setCellValueFactory(new PropertyValueFactory("status"));
        colStatus.setPrefWidth(150);
//      Tạo nút xác nhận đổi
        TableColumn colChangeTicket = new TableColumn();
        colChangeTicket.setCellFactory(evt -> {
            Button btnChange = new Button("Đổi");

            btnChange.setOnAction(e -> {
                Button b = (Button) e.getSource();
                TableCell cell = (TableCell) b.getParent();
                Ticket selectedTicket = (Ticket) cell.getTableRow().getItem();
                if (selectedTicket.getStatus().contains("Empty")) {
                    TicketServices s = new TicketServices();
                    try {
                        if (s.changeTicket(tfTicketChange.getText(), selectedTicket)) {
                            Alert confirm = MessageBox.getBox("Đổi vé", "Đổi vé thành công", Alert.AlertType.INFORMATION);
                            confirm.showAndWait().ifPresent(a -> {
                                try {
                                    btnCloseHandler(e);
                                } catch (IOException ex) {
                                    Logger.getLogger(ChangeTicketController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });
                        } else {
                            Alert confirm = MessageBox.getBox("Đổi vé", "Đổi vé không thành công", Alert.AlertType.WARNING);
                        };
                    } catch (SQLException ex) {
                        Logger.getLogger(ChangeTicketController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    Alert confirm1 = MessageBox.getBox("Đổi vé", "Vé đã sử dụng không thể đổi", Alert.AlertType.WARNING);
                    confirm1.showAndWait();
                }

            });

            TableCell cellChange = new TableCell();
            cellChange.setGraphic(btnChange);
            return cellChange;
        });
        this.tbChangeTicket.getColumns().addAll(colIDTicket, colChair, colStatus, colChangeTicket);
    }

    private TableView<Ticket> loadTableDataChange() {
        TicketServices s1 = new TicketServices();
        List<Ticket> tkChange = null;
        try {
            tkChange = s1.loadTicketByID(null);
        } catch (SQLException ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.tbChangeTicket.setItems(FXCollections.observableList(tkChange));
        return this.tbChangeTicket;
    }

    private TableView<Ticket> searchTableDataChange(String start, String end, String chair, LocalDate startDate, String startTime) {
        TicketServices s1 = new TicketServices();
        List<Ticket> tkChange = null;
        try {
            tkChange = s1.loadTicketByInfo(start, end, chair, startDate, startTime);
        } catch (SQLException ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.tbChangeTicket.setItems(FXCollections.observableList(tkChange));
        return this.tbChangeTicket;
    }

    private void btnCloseHandler(ActionEvent evt) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("staff.fxml"));
        Parent popup = fxmlLoader.load();

        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        StaffController ctc = fxmlLoader.getController();
        stage.setScene(new Scene(popup));
        stage.show();
    }

    public void getTicketChange(Ticket t) {
        this.tfTicketChange.setText(t.toString());
        this.tfTicketChange.setEditable(false);
    }
}
