package com.nhom1.banvexekhach;

import java.io.IOException;
import javafx.fxml.FXML;

public class SigninController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
