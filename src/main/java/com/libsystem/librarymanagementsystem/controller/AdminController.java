package com.libsystem.librarymanagementsystem.controller;

import com.libsystem.librarymanagementsystem.database.BookDAO;
import com.libsystem.librarymanagementsystem.model.Book;
import com.libsystem.librarymanagementsystem.model.User;
import com.libsystem.librarymanagementsystem.service.BookService;
import com.libsystem.librarymanagementsystem.service.UserService;
import com.libsystem.librarymanagementsystem.service.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminController extends GuessController {
    User userSession;
    @FXML
    private Button AHPUserManageBTN;
    @FXML
    private Button AHPStatisticBTN;
    @FXML
    private Button AHPAccountBTN;
    @FXML
    private Button AHPLogoutBTN;

    @FXML
    private void AHPUserManage(ActionEvent event) {
        loadPage("admin-user-management");
    }

    @FXML
    private void AHPAccount(ActionEvent event) {
        loadPage("admin-self-info");
    }

    @FXML
    private void AHPStatistic(ActionEvent event) { loadPage("admin-statistical"); }

    @FXML
    private void AHPLogout(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
            Scene scene = new Scene(root, 960, 540);
            Main.stage.setScene(scene);
        } catch (IOException exception) {
            Logger.getLogger(GuessController.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle rs) {
        userSession = UserSession.getUser();
        System.out.println(userSession.getUsername());
        if (HPContainerBP != null) {
            User user = (User) Main.stage.getUserData();
            String[] arr = user.getFullName().split(" ");
            String name = arr[arr.length - 1];
            HPWelcomeLB.setText("Xin ch??o, " + name + "!");
        }
        if (AUMResultTV != null) {
            AUMResultTV.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                        User user = AUMResultTV.getSelectionModel().getSelectedItem();
//                        System.out.println(book.getBookName());
                        Main.stage.setUserData(user);

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin-user-info.fxml"));
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
                }

            });
        }
        if (AUAViewAP != null) {
            ObservableList<String> roles = FXCollections.observableArrayList(UserService.getAllRoles());
            AUARolesCB.getItems().addAll(roles);
            AUARolesCB.setValue(roles.get(0));
        }
        if (SIViewAP != null) {
            SIFullNameTF.setText(userSession.getFullName());
            SIUsernameTF.setText(userSession.getUsername());
            SIPasswordPF.setText(userSession.getPassword());
            SIDateOfBirthTF.setText(userSession.getDateOfBirth());
            SIPhoneNumberTF.setText(userSession.getPhoneNumber());
            SIGenderTF.setText(userSession.getGender());
        }
        if (ASViewAP != null) {
            BookDAO bookDAO = new BookDAO();
            ArrayList<Book> books = bookDAO.showAllBook();
            ASNumOfBooksLB.setText(String.valueOf(books.size()));
            ASBorrowingBookLB.setText(String.valueOf(bookDAO.getBorrowingBook()));
            ASBorrowingLateBooksLB.setText(String.valueOf(bookDAO.getBorrowingLateBook()));
        }
    }

    /**
     * Controller of admin-user-management.fxml
     */
    @FXML
    private AnchorPane AUMViewAP;
    @FXML
    private TextField AUMFindingPhraseTF;
    @FXML
    private TableView<User> AUMResultTV;
    @FXML
    private TableColumn<User, Integer> AUMUserIDTC;
    @FXML
    private TableColumn<User, String> AUMUserNameTC;
    @FXML
    private TableColumn<User, String> AUMUserRoleTC;

    @FXML
    private void AUMFindUser(ActionEvent event) {
        if (!AUMFindingPhraseTF.getText().isEmpty()) {
            if (AUMFindingPhraseTF.getText().contains("-")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Kh??ng nh???p k?? t??? ?????c bi???t!");
                alert.show();
                return;
            }
            ObservableList<User> users = FXCollections.observableArrayList(UserService.findUser(AUMFindingPhraseTF.getText()));
            if (!users.isEmpty()) {
                AUMUserIDTC.setCellValueFactory(new PropertyValueFactory<>("userID"));
                AUMUserNameTC.setCellValueFactory(new PropertyValueFactory<>("fullName"));
                AUMUserRoleTC.setCellValueFactory(new PropertyValueFactory<>("role"));
                AUMResultTV.setItems(users);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Kh??ng t??m th???y nh??n vi??n n??o!");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("H??y nh???p t??n/vai tr?? c???a nh??n vi??n b???n mu???n t??m!");
            alert.show();
        }
    }

    @FXML
    private void AUMAddUser(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin-user-add.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(GuessController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AUMViewAP.getChildren().add(root);
    }

    @FXML
    private void AUMShowAllUser(ActionEvent event) {
        ObservableList<User> users = FXCollections.observableArrayList(UserService.showAllUser());
        if (!users.isEmpty()) {
            AUMUserIDTC.setCellValueFactory(new PropertyValueFactory<>("userID"));
            AUMUserNameTC.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            AUMUserRoleTC.setCellValueFactory(new PropertyValueFactory<>("role"));
            AUMResultTV.setItems(users);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Kh??ng t??m th???y nh??n vi??n n??o!");
            alert.show();
        }
    }

    /**
     * Controller of admin-user-add.fxml
     */
    @FXML
    private AnchorPane AUAViewAP;
    @FXML
    private TextField AUAFullNameTF;
    @FXML
    private TextField AUAUsernameTF;
    @FXML
    private PasswordField AUAPasswordTF;
    @FXML
    private ComboBox<String> AUARolesCB;
    @FXML
    private TextField AUADateOfBirthTF;
    @FXML
    private TextField AUAPhoneNumberTF;
    @FXML
    private TextField AUAGenderTF;

    @FXML
    private void AUAAddUser(ActionEvent event) throws Exception {
        if (!(AUAFullNameTF.getText().isEmpty() || AUAUsernameTF.getText().isEmpty() || AUAPasswordTF.getText().isEmpty() || AUADateOfBirthTF.getText().isEmpty() || AUAPhoneNumberTF.getText().isEmpty() || AUAGenderTF.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("X??c nh???n th??m");
            alert.setContentText("B???n c?? ch???c ch???n mu???n th??m t??i kho???n n??y kh??ng?");
            Optional<ButtonType> option = alert.showAndWait();
            boolean choice = option.get() == ButtonType.OK;
            if (choice) {
                if (UserService.addUser(AUAFullNameTF.getText(), AUAUsernameTF.getText(), AUAPasswordTF.getText(), AUARolesCB.getValue(), AUADateOfBirthTF.getText(), AUAPhoneNumberTF.getText(), AUAGenderTF.getText())) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Th??m th??nh c??ng");
                    alert1.setContentText("C???p nh???t th??nh c??ng, truy c???p qu???n l?? t??i kho???n l???i ????? xem k???t qu???");
                    alert1.show();
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Thao t??c th???t b???i");
                    alert1.show();
                }
            }
        }
    }

    /**
     * Controller of admin-self-info.fxml
     */
    @FXML
    private AnchorPane SIViewAP;
    @FXML
    private TextField SIFullNameTF;
    @FXML
    private TextField SIUsernameTF;
    @FXML
    private PasswordField SIPasswordPF;
    @FXML
    private TextField SIDateOfBirthTF;
    @FXML
    private TextField SIPhoneNumberTF;
    @FXML
    private TextField SIGenderTF;
    @FXML
    private TextField SIOldUsernameTF;
    @FXML
    private TextField SIOldPasswordTF;

    @FXML
    private void SIUpdateInfo(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("X??c nh???n c???p nh???t");
        alert.setContentText("B???n c?? ch???c ch???n mu???n c???p nh???t t??i kho???n c???a m??nh kh??ng?");
        Optional<ButtonType> option = alert.showAndWait();
        boolean choice = option.get() == ButtonType.OK;
        if (choice) {
            User user = UserService.authenticate(SIOldUsernameTF.getText(), SIOldPasswordTF.getText());
            if (user == null || !Objects.equals(user.getUsername(), userSession.getUsername())) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("C???p nh???t th???t b???i");
                alert1.setContentText("M???t kh???u v?? t??i kho???n c?? kh??ng ch??nh x??c");
                alert1.show();
            } else {
                if (user.getRole().equalsIgnoreCase("admin")) {
                    if (!(SIFullNameTF.getText().isEmpty() || SIUsernameTF.getText().isEmpty() || SIPasswordPF.getText().isEmpty() || SIDateOfBirthTF.getText().isEmpty() || SIGenderTF.getText().isEmpty() || SIPhoneNumberTF.getText().isEmpty())) {
                        if (UserService.updateUser(SIFullNameTF.getText(), SIUsernameTF.getText(), SIPasswordPF.getText(),SIDateOfBirthTF.getText(), SIGenderTF.getText(), SIPhoneNumberTF.getText(), userSession)) {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("C???p nh???t th??nh c??ng");
                            alert1.setContentText("C???p nh???t th??nh c??ng, truy c???p t??i kho???n l???i ????? xem k???t qu???");
                            alert1.show();
                        } else {
                            Alert alert1 = new Alert(Alert.AlertType.ERROR);
                            alert1.setTitle("C???p nh???t th???t b???i");
                            alert1.setContentText("C??c tr?????ng c?? k?? t??? kh??ng ph?? h???p!");
                            alert1.show();
                        }
                    } else {
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("C???p nh???t th???t b???i");
                        alert1.setContentText("Kh??ng c?? tr?????ng n??o ???????c b??? tr???ng");
                        alert1.show();
                    }
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("C???p nh???t th???t b???i");
                    alert1.setContentText("B???n kh??ng c?? quy???n c???p nh???t");
                    alert1.show();
                }
            }

        }
    }

    /**
     * Controller of admin-statistical.fxml
     */
    @FXML
    private AnchorPane ASViewAP;
    @FXML
    private Label ASNumOfBooksLB;
    @FXML
    private Label ASBorrowingBookLB;
    @FXML
    private Label ASBorrowingLateBooksLB;
}
