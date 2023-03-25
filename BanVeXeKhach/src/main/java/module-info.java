module com.nhom1.banvexekhach {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.nhom1.banvexekhach to javafx.fxml;
    exports com.nhom1.banvexekhach;
}
