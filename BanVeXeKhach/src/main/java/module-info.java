module com.nhom1.banvexekhach {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.nhom1.banvexekhach to javafx.fxml;
    exports com.nhom1.banvexekhach;
}
