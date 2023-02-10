package com.libsystem.librarymanagementsystem.controller;

import com.libsystem.librarymanagementsystem.model.Book;
import com.libsystem.librarymanagementsystem.model.User;
import com.libsystem.librarymanagementsystem.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserInfoController implements Initializable {
    private User user;
    @FXML
    private AnchorPane UIContainerAP;
    @FXML
    private Button UIbackButton;
    @FXML
    private Label UIFullName;
    @FXML
    private Label UIRole;
    @FXML
    private Label UIDateOfBirth;
    @FXML
    private Label UIGender;
    @FXML
    private Label UIPhoneNumber;
    @FXML
    private Button UIUpdateInfo;
    @FXML
    private Button UIDeleteAccount;

    private Scene preScene;

    public void setPreScene(Scene preScene) {
        this.preScene = preScene;
    }

    @Override
    public void initialize(URL location, ResourceBundle rs) {
        if (UIContainerAP != null) {
            user = (User) Main.stage.getUserData();
//        System.out.println(book.getBookName());
            UIFullName.setText(user.getFullName());
            UIRole.setText(user.getRole());
            UIDateOfBirth.setText(user.getDateOfBirth());
            UIGender.setText(user.getGender());
            UIPhoneNumber.setText(user.getPhoneNumber());
        }
        if (AUUContainerAP != null) {
            user = (User) Main.stage.getUserData();
            AUUFullNameTF.setText(user.getFullName());
            AUUUsernameTF.setText(user.getUsername());
            AUUPasswordTF.setText(user.getPassword());
            AUUDateOfBirthTF.setText(user.getDateOfBirth());
            AUUGenderTF.setText(user.getGender());
            AUUPhoneNumberTF.setText(user.getPhoneNumber());
        }
    }

    @FXML
    private void onClickBack(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

//        Main.stage.initStyle(StageStyle.DECORATED);
//        Main.stage.resizableProperty().setValue(false);
        stage.setScene(preScene);
        stage.show();
    }

    @FXML
    private void onClickUpdate(ActionEvent event) {
        Main.stage.setUserData(user);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin-user-update-info.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UserInfoController controller = fxmlLoader.getController();
        controller.setPreScene(Main.stage.getScene());
//                        Main.stage.initStyle(StageStyle.UNDECORATED);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onClickDelete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setContentText("Bạn có chắc chắn muốn xóa tài khoản này không?");
        Optional<ButtonType> option = alert.showAndWait();
        boolean choice = option.get() == ButtonType.OK;
        if(choice) {
            if (UserService.deleteUser(user.getUserID())) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Xóa thành công");
                alert1.setContentText("Cập nhật thành công, truy cập quản lý tài khoản lại để xem kết quả");
                alert1.show();
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(preScene);
                stage.show();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Xóa thành thất bại");
                alert1.setContentText("thao tác thất bại");
                alert1.show();
            }
        }
    }

    /**
     * Controller of admin-user-update-info.fxml
     */
    @FXML
    private AnchorPane AUUContainerAP;
    @FXML
    private TextField AUUFullNameTF;
    @FXML
    private TextField AUUUsernameTF;
    @FXML
    private PasswordField AUUPasswordTF;
    @FXML
    private TextField AUUDateOfBirthTF;
    @FXML
    private TextField AUUGenderTF;
    @FXML
    private TextField AUUPhoneNumberTF;
    @FXML
    private TextField AUUAdminUsernameTF;
    @FXML
    private TextField AUUAdminPasswordTF;
    @FXML
    private Button AUUUpdateBTN;

    @FXML
    private void AUUUpdate(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận cập nhật");
        alert.setContentText("Bạn có chắc chắn muốn cập nhật tài khoản này không?");
        Optional<ButtonType> option = alert.showAndWait();
        boolean choice = option.get() == ButtonType.OK;
        if (choice) {
            User user = UserService.authenticate(AUUAdminUsernameTF.getText(), AUUAdminPasswordTF.getText());
            if (user == null) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Cập nhật thất bại");
                alert1.setContentText("Mật khẩu và tài khoản của admin không chính xác");
                alert1.show();
            } else {
                if (user.getRole().equalsIgnoreCase("admin")) {
                    if (!(AUUFullNameTF.getText().isEmpty() || AUUUsernameTF.getText().isEmpty() || AUUPasswordTF.getText().isEmpty() || AUUDateOfBirthTF.getText().isEmpty() || AUUGenderTF.getText().isEmpty() || AUUPhoneNumberTF.getText().isEmpty())) {
                        if (UserService.updateUser(AUUFullNameTF.getText(), AUUUsernameTF.getText(), AUUPasswordTF.getText(),AUUDateOfBirthTF.getText(), AUUGenderTF.getText(), AUUPhoneNumberTF.getText(), this.user)) {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("Cập nhật thành công");
                            alert1.setContentText("Cập nhật thành công, truy cập quản lý tài khoản lại để xem kết quả");
                            alert1.show();
                            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                            stage.setScene(preScene);
                            stage.show();
                        } else {
                            Alert alert1 = new Alert(Alert.AlertType.ERROR);
                            alert1.setTitle("Cập nhật thất bại");
                            alert1.setContentText("Các trường có ký tự không phù hợp!");
                            alert1.show();
                        }
                    } else {
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("Cập nhật thất bại");
                        alert1.setContentText("Không có trường nào được bỏ trống");
                        alert1.show();
                    }
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Cập nhật thất bại");
                    alert1.setContentText("Bạn không có quyền cập nhật");
                    alert1.show();
                }
            }

        }
    }
}
