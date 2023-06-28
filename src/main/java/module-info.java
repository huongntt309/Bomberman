module bomberman {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.sql;
    requires javafx.media;
    requires lombok;
    requires java.desktop;
    requires javafx.swing;

    opens bomberman to javafx.fxml;
    exports bomberman;
    exports bomberman.Data;
    opens bomberman.Data to javafx.fxml;
    exports bomberman.Controller.Admin;
    opens bomberman.Controller.Admin to javafx.fxml;
    exports bomberman.Controller.User;
    opens bomberman.Controller.User to javafx.fxml;
    exports bomberman.Controller.Common;
    opens bomberman.Controller.Common to javafx.fxml;
}