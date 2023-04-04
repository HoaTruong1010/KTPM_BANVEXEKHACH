/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.nhom1.banvexekhach;

import com.nhom1.pojo.Route;
import com.nhom1.pojo.Trip;
import com.nhom1.services.RouteServices;
import com.nhom1.services.TripServices;
import com.nhom1.utils.CheckData;
import com.nhom1.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HOA TRƯƠNG
 */
public class BookingController implements Initializable {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @FXML
    private TableView<Trip> tableTrip;
    @FXML
    private ComboBox<Route> cbFillterRoute;
    @FXML
    private TextField txtKeyword;
    @FXML
    private Label lbStart;
    @FXML
    private Label lbStartText;
    @FXML
    private Label lbEndText;
    @FXML
    private Label lbEnd;
    @FXML
    private Button btBook;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadTable();
        try {
            // TODO
            loadTableData(null, 0);
            this.loadCbFillterRouteData();
            reset();
            reload();
        } catch (SQLException ex) {
            Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.tableTrip.setRowFactory(t -> {
            TableRow row = new TableRow();

            row.setOnMouseClicked(r -> {
                try {
                    Route route = RouteServices.getRouteById(
                            this.tableTrip.getSelectionModel().getSelectedItem().getRoute_id());
                    this.lbStart.setVisible(true);
                    this.lbStartText.setText(route.getStart());
                    this.lbEnd.setVisible(true);
                    this.lbEndText.setText(route.getEnd());
                    btBook.setDisable(false);
                } catch (SQLException ex) {
                    Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            return row;
        });
    }

    public void reload() {
        Timer timer = new Timer("Reload");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    loadTableData(null, 0);
                } catch (SQLException ex) {
                    Logger.getLogger(Booking_detailController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        timer.schedule(task, 60 * 5000L);
    }

    public void reset() {
        this.lbStart.setVisible(false);
        this.lbStartText.setText("");
        this.lbEnd.setVisible(false);
        this.lbEndText.setText("");

        this.txtKeyword.setText("");
        this.cbFillterRoute.getSelectionModel().select(0);
        btBook.setDisable(true);
    }

    private void loadTableData(String kw, int routeID) throws SQLException {
        TripServices t = new TripServices();
        List<Trip> list = t.loadTrips(kw, routeID);

        list = list.stream().filter((Trip x) -> {
            LocalDateTime departing = LocalDateTime.parse(x.getDeparting_at(), Trip.formatDate);
            Date now = new Date();
            long getTime = departing.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            long getDiff = getTime - now.getTime();
            return getDiff > 3600000;

        }).collect(Collectors.toList());

        this.tableTrip.setItems(FXCollections.observableList(list));
    }

    private void loadTable() {
        TableColumn colID = new TableColumn("Mã chuyến đi");
        colID.setCellValueFactory(new PropertyValueFactory("id"));
        colID.setPrefWidth(110);

        TableColumn colDeparting = new TableColumn("Thời gian khởi hành");
        colDeparting.setCellValueFactory(new PropertyValueFactory("departing_at"));
        colDeparting.setPrefWidth(170);

        TableColumn colArriving = new TableColumn("Thời gian kết thúc");
        colArriving.setCellValueFactory(new PropertyValueFactory("arriving_at"));
        colArriving.setPrefWidth(170);

        TableColumn colPrice = new TableColumn("Giá 1 vé/ghế");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        colPrice.setPrefWidth(120);

        TableColumn colRouteID = new TableColumn("Tuyến");
        colRouteID.setCellValueFactory(new PropertyValueFactory("route_id"));
        colRouteID.setPrefWidth(70);

        this.tableTrip.getColumns().addAll(colID, colDeparting, colArriving,
                colPrice, colRouteID);
    }

    private void loadCbFillterRouteData() throws SQLException {
        RouteServices r = new RouteServices();

        this.cbFillterRoute.setItems(FXCollections.observableList(r.loadRoutes()));
    }

    public void txtSearch_Click(ActionEvent e) throws SQLException {
        String kw = this.txtKeyword.getText();
        int selectRoute = this.cbFillterRoute.getSelectionModel().getSelectedItem().getId();
        this.txtKeyword.setText(kw);
        this.cbFillterRoute.getSelectionModel().select(selectRoute - 1);

        if (!kw.isEmpty() && !CheckData.isDateTimeFormat(kw)) {
            MessageBox.getBox("Error", "Từ khóa không đúng định dạng:\n năm-tháng-ngày giờ:phút:giây", Alert.AlertType.ERROR).show();
            kw = null;
        }

        this.loadTableData(kw, selectRoute);
    }

    public void btnBook_Click(ActionEvent e) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("booking_detail.fxml"));
        Parent bookingDetail = fxmlLoader.load();

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Trip selectedTrip = this.tableTrip.getSelectionModel().getSelectedItem();
        Booking_detailController bdc = fxmlLoader.getController();
        bdc.setTripDetail(selectedTrip);
        stage.setScene(new Scene(bookingDetail));
    }

    public void btnReload_Click() throws SQLException {
        TripServices t = new TripServices();
        reset();

        this.tableTrip.setItems(FXCollections.observableList(t.loadTrips(null, 0)));
    }

    public void btnExit_Click(ActionEvent e) {
        System.exit(0);
    }
}
