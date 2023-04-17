/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.banvexekhach;

import com.nhom1.pojo.Route;
import com.nhom1.pojo.Ticket;
import com.nhom1.pojo.Trip;
import com.nhom1.pojo.User;
import com.nhom1.services.RouteServices;
import com.nhom1.services.TicketServices;
import com.nhom1.services.TripServices;
import com.nhom1.utils.CheckData;
import com.nhom1.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author fptshop.com.vn
 */
public class ChangeTicketController implements Initializable {

    @FXML
    private TextField tfTicketChange;
    @FXML
    private TextField tfTripTicketChange;
    @FXML
    private TableView<Ticket> tbChangeTicket;
    @FXML
    private ComboBox<Trip> cbTrip;
    @FXML
    private TextField tfChair;
    @FXML
    private Button btnClose;
    @FXML
    private Label lbCurrentUsername;
    private User currentUser;
    private String start;
    private String end;
    private LocalDate date;
    private String time;

    /**
     * @return the currentUser
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * @param currentUser the currentUser to set
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        this.lbCurrentUsername.setText(String.format("%s",
                currentUser.getName()));
    }

    public void getTicketChange(Ticket t) {
        this.tfTicketChange.setText(t.toString());
        this.tfTicketChange.setEditable(false);
    }

    public void getTripTicketChange(int t) {
        this.tfTripTicketChange.setText(Integer.toString(t));
        this.tfTripTicketChange.setEditable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.loadTableTicketChange();
        this.tfTripTicketChange.textProperty().addListener(o -> {
            try {
                this.loadTableDataChange(this.tfTripTicketChange.getText());
                this.loadComboboxTrip(this.tfTripTicketChange.getText());
                Trip trip = TripServices.getTripById(Integer.parseInt(this.tfTripTicketChange.getText()));
                Route route = RouteServices.getRouteById(trip.getRoute_id());
                this.start = route.getStart();
                this.end = route.getEnd();
                String dateStart = trip.getDeparting_at();
                LocalDateTime dateTime = LocalDateTime.parse(dateStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                date = dateTime.toLocalDate();
                time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
                this.cbTrip.getSelectionModel().select(trip);
            } catch (SQLException ex) {
                Logger.getLogger(ChangeTicketController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.cbTrip.valueProperty().addListener(o -> {
            try {
                Trip trip = cbTrip.getValue();
                Route route = RouteServices.getRouteById(trip.getRoute_id());
                this.start = route.getStart();
                this.end = route.getEnd();
                String dateStart = trip.getDeparting_at();
                LocalDateTime dateTime = LocalDateTime.parse(dateStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                date = dateTime.toLocalDate();
                time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
                this.loadTableDataChange(Integer.toString(trip.getId()));
            } catch (SQLException ex) {
                Logger.getLogger(ChangeTicketController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.reload();
        this.tfChair.textProperty().addListener(o -> {
            this.searchTableDataChange(start,
                    end, this.tfChair.getText(), date,
                    time);
        });
        btnClose.setOnAction(eh -> {
            try {
                this.btnCloseHandler(eh);
            } catch (IOException ex) {
                Logger.getLogger(ChangeTicketController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void reload() {
        Timer timer = new Timer("Reload");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                searchTableDataChange(null, null, null, null, null);
            }
        };
        timer.schedule(task, 60 * 5000L);
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
//      Tạo cột Mã chuyến đi
        TableColumn colTripID = new TableColumn("Mã chuyến đi");
        colTripID.setCellValueFactory(new PropertyValueFactory("trip_id"));
        colTripID.setPrefWidth(150);
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
                        Ticket t = TicketServices.getTicketById(Integer.parseInt(tfTicketChange.getText()));
                        Trip trip = TripServices.getTripById(t.getTrip_id());
                        Trip tripoftkchange = TripServices.getTripById(selectedTicket.getTrip_id());
                        if (CheckData.isChoosing(tripoftkchange.getDeparting_at(), (1000 * 60 * 60))) {
                            if (CheckData.isChoosing(trip.getDeparting_at(), (1000 * 60 * 60))) {
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
                                }
                            } else {
                                Alert confirm = MessageBox.getBox("Đổi vé", "Vé đã hết thời gian để đổi", Alert.AlertType.WARNING);
                            }
                        } else {
                            MessageBox.getBox("Error", "Vé đã quá thời gian cho phép đổi!", Alert.AlertType.ERROR).show();
                        }
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
        this.tbChangeTicket.getColumns().addAll(colIDTicket, colChair, colStatus, colTripID, colChangeTicket);
    }

    public void loadTableDataChange(String str) throws SQLException {
        TicketServices s1 = new TicketServices();
        List<Ticket> tkChange = null;
        tkChange = s1.getTicketsByStringTripID(str);
        this.tbChangeTicket.setItems(FXCollections.observableList(tkChange));
    }

    private void loadComboboxTrip(String tripId) throws SQLException {
        TripServices s = new TripServices();
        Trip t = s.getTripById(Integer.parseInt(tripId));
        List<Trip> list = new ArrayList<>();
        list = s.loadTrips(null, 0);
        list = list.stream().filter(x -> x.getRoute_id() == t.getRoute_id()).collect(Collectors.toList());
        this.cbTrip.setItems(FXCollections.observableList(list));
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
        ctc.setCurrentUser(currentUser);
        stage.setScene(new Scene(popup));
        stage.show();
    }
}
