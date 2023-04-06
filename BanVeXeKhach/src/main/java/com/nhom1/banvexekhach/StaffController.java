/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.banvexekhach;

import com.nhom1.pojo.Ticket;
import com.nhom1.pojo.Trip;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author fptshop.com.vn
 */
public class StaffController implements Initializable {

    @FXML
    private TableView<Ticket> tbTicket;
    @FXML
    private TextField txtID;
    @FXML
    private TextField start;
    @FXML
    private TextField end;
    @FXML
    private DatePicker startDate;
    @FXML
    private TextField startTime;
    @FXML
    private TextField chair;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.loadTableTicket();
            this.loadTableDataByID(null);
        } catch (SQLException ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.txtID.textProperty().addListener(o -> {
            try {
                this.loadTableDataByID(this.txtID.getText());
            } catch (SQLException ex) {
                Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.start.textProperty().addListener(o -> {
            try {
                this.loadTableDataByInfo(this.start.getText(),
                        this.end.getText(), this.chair.getText(), this.startDate.getValue(),
                        this.startTime.getText());
            } catch (SQLException ex) {
                Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.end.textProperty().addListener(o -> {
            try {
                this.loadTableDataByInfo(this.start.getText(),
                        this.end.getText(), this.chair.getText(), this.startDate.getValue(),
                        this.startTime.getText());
            } catch (SQLException ex) {
                Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.chair.textProperty().addListener(o -> {
            try {
                this.loadTableDataByInfo(this.start.getText(),
                        this.end.getText(), this.chair.getText(), this.startDate.getValue(),
                        this.startTime.getText());
            } catch (SQLException ex) {
                Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.startDate.valueProperty().addListener(o -> {
            try {
                this.loadTableDataByInfo(this.start.getText(),
                        this.end.getText(), this.chair.getText(), this.startDate.getValue(),
                        this.startTime.getText());
            } catch (SQLException ex) {
                Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.startTime.textProperty().addListener(o -> {
            try {
                this.loadTableDataByInfo(this.start.getText(),
                        this.end.getText(), this.chair.getText(), this.startDate.getValue(),
                        this.startTime.getText());
            } catch (SQLException ex) {
                Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void loadTableTicket() throws SQLException {
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
//      Tạo cột mã chuyến đi
        TableColumn colStartTime = new TableColumn("Mã chuyến đi");
        colStartTime.setCellValueFactory(new PropertyValueFactory("trip_id"));
        colStartTime.setPrefWidth(120);
//      Tạo cột mã khách hàng
        TableColumn colStart = new TableColumn("Khách hàng");
        colStart.setCellValueFactory(new PropertyValueFactory("customer_id"));
        colStart.setPrefWidth(120);
//      Tạo cột với button đổi vé
        TableColumn colChangeTicket = new TableColumn();
        colChangeTicket.setCellFactory(p -> {

            Button btnChange = new Button("Đổi vé");
            btnChange.setOnAction(e -> {
                Button b = (Button) e.getSource();
                TableCell cell = (TableCell) b.getParent();
                Ticket t = (Ticket) cell.getTableRow().getItem();
                if (!t.getStatus().contains("Reserved")) {
                    Alert confirm = MessageBox.getBox("Đổi vé", "Vé chưa đặt không thể đổi vé.\nVui lòng đặt!!!", Alert.AlertType.WARNING);
                    confirm.showAndWait();
                } else {
                    try {
                        // Load FXML popup
                        this.moveToChange(e, t);
                    } catch (IOException ex) {
                        Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            });
            TableCell cellChange = new TableCell();
            cellChange.setGraphic(btnChange);
            return cellChange;
        });

        TableColumn colCancelTicket = new TableColumn();
        colCancelTicket.setCellFactory(evt -> {
            Button btnCancel = new Button("Hủy vé");
            btnCancel.setOnAction(c -> {
                Button b = (Button) c.getSource();
                TableCell cellCan = (TableCell) b.getParent();
                Ticket t = (Ticket) cellCan.getTableRow().getItem();
                if (t.getStatus().contains("Sold")) {
                    Alert confirm = MessageBox.getBox("Hủy vé", "Vé đã bán không thể hủy.!!!", Alert.AlertType.WARNING);
                    confirm.showAndWait();
                } else if (t.getStatus().contains("Empty")) {
                    Alert confirm = MessageBox.getBox("Hủy vé", "Vé chưa đặt không thể hủy.!!!", Alert.AlertType.WARNING);
                    confirm.showAndWait();
                } else {
                    try {
                        TicketServices tks = new TicketServices();
                        tks.cancelTicket(t.getId());
                        this.loadTableDataByID(null);
                        Alert confirm1 = MessageBox.getBox("Hủy vé", "***Hủy thành công***", Alert.AlertType.INFORMATION);
                        confirm1.showAndWait();
                    } catch (SQLException ex) {
                        Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            TableCell cellCancel = new TableCell();
            cellCancel.setGraphic(btnCancel);
            return cellCancel;
        });

        this.tbTicket.getColumns().addAll(colIDTicket, colChair, colStatus,
                colStartTime, colStart, colChangeTicket, colCancelTicket);
    }

    private void moveToChange(ActionEvent event, Ticket selectedTicket) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("change_ticket.fxml"));
        Parent popup = fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ChangeTicketController ctc = fxmlLoader.getController();
        System.out.println(selectedTicket.getId());
        ctc.getTicketChange(selectedTicket);
        stage.setScene(new Scene(popup));
        stage.show();
    }

    private void loadTableDataByID(String id) throws SQLException {
        TicketServices s = new TicketServices();
        List<Ticket> tk = s.loadTicketByID(id);
        this.tbTicket.setItems(FXCollections.observableList(tk));
    }

    private void loadTableDataByInfo(String start, String end, String chair, LocalDate startDate, String startTime) throws SQLException {
        TicketServices s = new TicketServices();
        List<Ticket> tk = s.loadTicketByInfo(start, end, chair, startDate, startTime);
        this.tbTicket.setItems(FXCollections.observableList(tk));
    }

}
