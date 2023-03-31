module com.nhom1.banvexekhach {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires java.desktop;
    requires java.logging;

    opens com.nhom1.banvexekhach to javafx.fxml;
    exports com.nhom1.banvexekhach;
    exports com.nhom1.pojo;
}
