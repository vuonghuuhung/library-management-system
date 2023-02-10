package com.libsystem.librarymanagementsystem.controller;

import com.libsystem.librarymanagementsystem.model.User;
import com.libsystem.librarymanagementsystem.service.UserService;
import com.libsystem.librarymanagementsystem.service.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {
    @FXML
    private TextField LGUsernameTF;
    @FXML
    private PasswordField LGPasswordPF;
    @FXML
    private Button LGLoginBTN;
    @FXML
    private Button LGReaderBTN;

    @FXML
    private void LGLogin(ActionEvent event) throws Exception {
        System.out.println(LGUsernameTF.getText() + " " + LGPasswordPF.getText());
        User user = UserService.authenticate(LGUsernameTF.getText(), LGPasswordPF.getText());
        if (user != null) {
            Main.stage.setUserData(user);
            UserSession userSession = UserSession.getInstace(user);
            Parent root = null;
            try {
                if (user.getRole().equalsIgnoreCase("admin")) {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin-home-page.fxml")));
                    Scene scene = new Scene(root, 960, 540);
                    Main.stage.setScene(scene);
                } else {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("librarian-home-page.fxml")));
                    Scene scene = new Scene(root, 960, 540);
                    Main.stage.setScene(scene);
                }
            } catch (IOException exception) {
                Logger.getLogger(GuessController.class.getName()).log(Level.SEVERE, null, exception);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Đăng nhập thất bại");
            alert.show();
        }
    }

    @FXML
    private void LGReader(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-page.fxml")));
            Scene scene = new Scene(root, 960, 540);
            Main.stage.setScene(scene);
        } catch (IOException exception) {
            Logger.getLogger(GuessController.class.getName()).log(Level.SEVERE, null, exception);
        }
    }
}
