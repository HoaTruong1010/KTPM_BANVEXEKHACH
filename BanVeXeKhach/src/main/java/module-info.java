module com.nhom1.banvexekhach {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires java.desktop;
    requires java.logging;
    requires com.google.zxing;
    requires com.google.protobuf;
    requires org.apache.pdfbox;
    requires javafx.swing;
    requires org.apache.commons.codec;

    

    opens com.nhom1.banvexekhach to javafx.fxml;
    exports com.nhom1.banvexekhach;
    exports com.nhom1.pojo;
}
