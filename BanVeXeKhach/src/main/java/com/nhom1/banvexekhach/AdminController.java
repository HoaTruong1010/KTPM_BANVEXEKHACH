/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.nhom1.banvexekhach;

import com.nhom1.pojo.Car;
import com.nhom1.pojo.Route;
import com.nhom1.pojo.Trip;
import com.nhom1.services.CarServices;
import com.nhom1.services.RouteServices;
import com.nhom1.services.TripServices;
import com.nhom1.utils.MessageBox;
import com.nhom1.utils.CheckData;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author HOA TRƯƠNG
 */
public class AdminController implements Initializable {

    @FXML
    private TableView<Trip> tableTrip;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtDeparting;
    @FXML
    private TextField txtArriving;
    @FXML
    private TextField txtPrice;
    @FXML
    private ComboBox<Car> cbCar;
    @FXML
    private ComboBox<Route> cbRoute;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtKeyword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadTable();
        try {
            // TODO

            this.loadTableData(null);
            this.loadCbCarData();
            this.loadCbRouteData();
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.reset();

        this.tableTrip.setRowFactory(t -> {
            TableRow row = new TableRow();

            row.setOnMouseClicked(r -> {
                Trip trip = this.tableTrip.getSelectionModel().getSelectedItem();
                this.txtID.setText(String.format("%d", trip.getId()));
                this.txtDeparting.setText(trip.getDeparting_at());
                this.txtArriving.setText(trip.getArriving_at());
                this.txtPrice.setText(String.format("%.1f", trip.getPrice()));
                this.cbCar.getSelectionModel().select(trip.getCar_id()-1);
                this.cbRoute.getSelectionModel().select(trip.getRoute_id()-1);

                btnAdd.setVisible(false);
                btnEdit.setVisible(true);
                btnDelete.setVisible(true);
            });

            return row;
        });
    }

    public void reset() {
        this.txtID.setText("");
        this.txtDeparting.setText("");
        this.txtArriving.setText("");
        this.txtPrice.setText("");
        this.cbCar.getSelectionModel().select(0);
        this.cbRoute.getSelectionModel().select(0);

        this.btnAdd.setVisible(true);
        this.btnEdit.setVisible(false);
        this.btnDelete.setVisible(false);
    }

    private void loadTable() {
        TableColumn colID = new TableColumn("Mã chuyến đi");
        colID.setCellValueFactory(new PropertyValueFactory("id"));
        colID.setPrefWidth(100);

        TableColumn colDeparting = new TableColumn("Thời gian khởi hành");
        colDeparting.setCellValueFactory(new PropertyValueFactory("departing_at"));
        colDeparting.setPrefWidth(150);

        TableColumn colArriving = new TableColumn("Thời gian kết thúc");
        colArriving.setCellValueFactory(new PropertyValueFactory("arriving_at"));
        colArriving.setPrefWidth(150);

        TableColumn colPrice = new TableColumn("Giá 1 vé/ghế");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        colPrice.setPrefWidth(120);

        TableColumn colCarID = new TableColumn("Số xe");
        colCarID.setCellValueFactory(new PropertyValueFactory("car_id"));

        TableColumn colRouteID = new TableColumn("Tuyến");
        colRouteID.setCellValueFactory(new PropertyValueFactory("route_id"));

        this.tableTrip.getColumns().addAll(colID, colDeparting, colArriving, colPrice, colCarID, colRouteID);
    }

    private void loadTableData(String kw) throws SQLException {
        TripServices t = new TripServices();

        this.tableTrip.setItems(FXCollections.observableList(t.loadTrips(kw)));
    }

    private void loadCbCarData() throws SQLException {
        CarServices c = new CarServices();

        this.cbCar.setItems(FXCollections.observableList(c.loadCars()));
    }

    private void loadCbRouteData() throws SQLException {
        RouteServices r = new RouteServices();

        this.cbRoute.setItems(FXCollections.observableList(r.loadRoutes()));
    }

    public void btnAdd_Click(ActionEvent e) {
        TripServices ts = new TripServices();
        String departing = this.txtDeparting.getText();
        String arriving = this.txtArriving.getText();

        if (CheckData.isDateTimeFormat(departing) && CheckData.isDateTimeFormat(arriving)) {
            if (CheckData.isDouble(this.txtPrice.getText())) {
                Car c = this.cbCar.getSelectionModel().getSelectedItem();
                Trip trip = new Trip(departing, arriving, Double.parseDouble(this.txtPrice.getText()),
                        c.getId(),
                        this.cbRoute.getSelectionModel().getSelectedItem().getId());
                try {
                    if (CheckData.isValidTrip(trip) == 0) {
                        ts.addTrip(trip, c.getSumChair());
                        MessageBox.getBox("Information", "Thêm thành công!", Alert.AlertType.INFORMATION).show();
                        this.loadTableData(null);
                        this.reset();
                    } else {
                        MessageBox.getBox("Error", "Xe đã có lịch đi trong khoảng thời gian này!", Alert.AlertType.ERROR).show();
                    }
                } catch (SQLException ex) {
//                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                    MessageBox.getBox("Error", "Thêm thất bại!", Alert.AlertType.ERROR).show();
                }
            } else {
                MessageBox.getBox("Error", "Giá 1 vé/ghế không đúng định dạng!", Alert.AlertType.ERROR).show();
            }
        } else {
            MessageBox.getBox("Error", "Thời gian khởi hành không đúng định dạng:\n năm-tháng-ngày giờ:phút:giây", Alert.AlertType.ERROR).show();
        }
    }

    public void btnEdit_Click(ActionEvent e) {
        TripServices ts = new TripServices();
        String departing = this.txtDeparting.getText();
        String arriving = this.txtArriving.getText();

        if (CheckData.isDateTimeFormat(departing) && CheckData.isDateTimeFormat(arriving)) {
            if (CheckData.isDouble(this.txtPrice.getText())) {
                Trip trip = new Trip(Integer.parseInt(txtID.getText()), departing, arriving, Double.parseDouble(this.txtPrice.getText()),
                        this.cbCar.getSelectionModel().getSelectedItem().getId(),
                        this.cbRoute.getSelectionModel().getSelectedItem().getId());

                try {
                    if (CheckData.isValidTrip(trip) == 0) {
                        ts.editTrip(trip);
                        MessageBox.getBox("Information", "Sửa thành công!", Alert.AlertType.INFORMATION).show();
                        this.loadTableData(null);
                        this.reset();
                    } else {                        
                        if (CheckData.isValidTrip(trip) == -1)
                            MessageBox.getBox("Error", "Xe đã có lịch đi trong khoảng thời gian này!", Alert.AlertType.ERROR).show();
                        else
                            MessageBox.getBox("Error", "Vui lòng chọn xe có chung số lượng ghế!", Alert.AlertType.ERROR).show();
                    }
                } catch (SQLException ex) {
                    MessageBox.getBox("Error", "Sửa thất bại!", Alert.AlertType.ERROR).show();
//                MessageBox.getBox("Error", ex.getMessage(), Alert.AlertType.ERROR).show();
                }
            } else {
                MessageBox.getBox("Error", "Giá 1 vé/ghế không đúng định dạng!", Alert.AlertType.ERROR).show();
            }
        } else {
            MessageBox.getBox("Error", "Thời gian khởi hành không đúng định dạng:\n năm-tháng-ngày giờ:phút:giây", Alert.AlertType.ERROR).show();
        }
    }

    public void btnDelete_Click(ActionEvent e) {
        TripServices ts = new TripServices();
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setContentText("Bạn có chắc chắn xóa?");
        confirm.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                try {
                    Trip trip = this.tableTrip.getSelectionModel().getSelectedItem();
                    Car c = CarServices.getCarById(trip.getCar_id());
                    ts.deleteTrip(trip.getId(), c.getSumChair());
                    MessageBox.getBox("Information", "Xóa thành công!", Alert.AlertType.INFORMATION).show();
                    this.loadTableData(null);
                    this.reset();
                } catch (SQLException ex) {
                    MessageBox.getBox("Error", "Xóa thất bại!", Alert.AlertType.ERROR).show();
//                    MessageBox.getBox("Error", ex.getMessage(), Alert.AlertType.ERROR).show();
                }
            }
        });
    }

    public void txtSearch_Click(ActionEvent e) throws SQLException {
        String kw = this.txtKeyword.getText();

        if (!CheckData.isDateTimeFormat(kw)) {
            MessageBox.getBox("Error", "Từ khóa không đúng định dạng:\n năm-tháng-ngày giờ:phút:giây", Alert.AlertType.ERROR).show();
            kw = null;
        }

        this.loadTableData(kw);
    }

    public void btnExit_Click(ActionEvent e) {
        System.exit(0);
    }
}
