package com.libsystem.librarymanagementsystem.controller;

import com.libsystem.librarymanagementsystem.model.Book;
import com.libsystem.librarymanagementsystem.model.User;
import com.libsystem.librarymanagementsystem.service.BookService;
import com.libsystem.librarymanagementsystem.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.*;
import java.util.logging.*;

import java.io.IOException;
import java.net.URL;

public class GuessController implements Initializable {

    /**
     * Controller of home-page.fxml
     * Login, Homing, Find books, News and Introducing
     */
    @FXML
    protected BorderPane HPContainerBP;
    @FXML
    protected BorderPane HPLeftBP;
    @FXML
    protected AnchorPane HPCenterAP;
    @FXML
    protected Label HPWelcomeLB;
    @FXML
    protected Pane HPListButtonP;
    @FXML
    protected Button HPHomeBTN;
    @FXML
    protected Button HPFindBookBTN;
    @FXML
    protected Button HPNewsBTN;
    @FXML
    protected Button HPIntroduceBTN;
    @FXML
    protected Button HPLoginBTN;

    @FXML
    protected void HPHome(ActionEvent event) {
        HPContainerBP.setCenter(HPCenterAP);
    }

    @FXML
    protected void HPFindBook(ActionEvent event) {
        loadPage("find-book");
    }

    @FXML
    protected void news(ActionEvent event) {
        loadPage("news");
    }

    @FXML
    protected void introduce(ActionEvent event) {
        loadPage("introduce");
    }

    @FXML
    protected void HPLogin(ActionEvent event) {
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
        if (FBViewAP != null) {
            FBResultTV.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                        Book book = FBResultTV.getSelectionModel().getSelectedItem();
//                        System.out.println(book.getBookName());
                        Main.stage.setUserData(book);

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book-info.fxml"));
                        Scene scene = null;
                        try {
                            scene = new Scene(fxmlLoader.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        BookInfoController controller = fxmlLoader.getController();
                        controller.setPreScene(Main.stage.getScene());
//                        Main.stage.initStyle(StageStyle.UNDECORATED);

                        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    }
                }

            });
        }
        if (AFBViewAP != null) {
            ObservableList<String> languages = FXCollections.observableArrayList(BookService.getAllLanguage().values());
            ObservableList<String> bookTypes = FXCollections.observableArrayList(BookService.getBookType().values());
            languages.add("Tất cả");
            bookTypes.add("Tất cả");
            AFBLanguageCB.getItems().addAll(languages);
            AFBBookTypeCB.getItems().addAll(bookTypes);
            AFBLanguageCB.setValue("Tất cả");
            AFBBookTypeCB.setValue("Tất cả");
            AFBResultTV.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                        Book book = AFBResultTV.getSelectionModel().getSelectedItem();
//                        System.out.println(book.getBookName());
                        Main.stage.setUserData(book);

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book-info.fxml"));
                        Scene scene = null;
                        try {
                            scene = new Scene(fxmlLoader.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        BookInfoController controller = fxmlLoader.getController();
                        controller.setPreScene(Main.stage.getScene());
//                        Main.stage.initStyle(StageStyle.UNDECORATED);

                        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    }
                }

            });
        }
    }

    protected void loadPage(String page) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(page + ".fxml")));
        } catch (IOException ex) {
            Logger.getLogger(GuessController.class.getName()).log(Level.SEVERE, null, ex);
        }
        HPContainerBP.setCenter(root);

    }

    /**
     * Controller of find-book.fxml
     * Finding and Advanced Finding Books
     */
    @FXML
    protected AnchorPane FBViewAP;
    @FXML
    protected TextField FBFindingPhraseTF;
    @FXML
    protected Button FBFindBTN;
    @FXML
    protected Button FBAdvancedFindBTN;
    @FXML
    protected TableView<Book> FBResultTV;
    @FXML
    protected TableColumn<Book,String> FBBookIDTC;
    @FXML
    protected TableColumn<Book,String> FBBookNameIDTC;
    @FXML
    protected TableColumn<Book,String> FBAuthorIDTC;
    @FXML
    protected TableColumn<Book,String> FBBookTypeIDTC;
    @FXML
    protected TableColumn<Book,String> FBLanguageIDTC;
    @FXML
    protected TableColumn<Book,String> FBAvailableIDTC;

    @FXML
    protected void FBFindBook(ActionEvent event) {
        if (!FBFindingPhraseTF.getText().isEmpty()) {
            if (FBFindingPhraseTF.getText().contains("-")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Không nhập kí tự đặc biệt!");
                alert.show();
                return;
            }
            ObservableList<Book> books = FXCollections.observableArrayList(BookService.findBook(FBFindingPhraseTF.getText()));
            if (!books.isEmpty()) {
                FBBookIDTC.setCellValueFactory(new PropertyValueFactory<>("bookID"));
                FBBookNameIDTC.setCellValueFactory(new PropertyValueFactory<>("bookName"));
                FBAuthorIDTC.setCellValueFactory(new PropertyValueFactory<>("author"));
                FBBookTypeIDTC.setCellValueFactory(new PropertyValueFactory<>("bookType"));
                FBLanguageIDTC.setCellValueFactory(new PropertyValueFactory<>("language"));
                FBAvailableIDTC.setCellValueFactory(new PropertyValueFactory<>("availableAmount"));
                FBResultTV.setItems(books);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Không tìm thấy kết quả nào!");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Hãy nhập cụm từ bạn muốn tìm!");
            alert.show();
        }
    }

    @FXML
    protected void FBAdvanceFindBook(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("advanced-find-book.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(GuessController.class.getName()).log(Level.SEVERE, null, ex);
        }
        FBViewAP.getChildren().add(root);
    }

    /**
     * Controller of advanced-find-book.fxml
     */
    @FXML
    protected AnchorPane AFBViewAP;
    @FXML
    protected TextField AFBBookNameTF;
    @FXML
    protected TextField AFBAuthorTF;
    @FXML
    protected TextField AFBPublisherTF;
    @FXML
    protected TextField AFBTakenDateTF;
    @FXML
    protected TextField AFBPriceTF;
    @FXML
    protected ComboBox<String> AFBBookTypeCB;
    @FXML
    protected ComboBox<String> AFBLanguageCB;
    @FXML
    protected TableView<Book> AFBResultTV;
    @FXML
    protected TableColumn<Book,String> AFBBookIDTC;
    @FXML
    protected TableColumn<Book,String> AFBBookNameIDTC;
    @FXML
    protected TableColumn<Book,String> AFBAuthorIDTC;
    @FXML
    protected TableColumn<Book,String> AFBBookTypeIDTC;
    @FXML
    protected TableColumn<Book,String> AFBLanguageIDTC;
    @FXML
    protected TableColumn<Book,String> AFBAvailableIDTC;
    @FXML
    protected Button AFBFindBookBTN;

//    private void initializeAdvancedFindPage() {
//
//    }

    @FXML
    protected void AFBFindBook(ActionEvent event) {
        BookService.bookName = AFBBookNameTF.getText();
        BookService.author = AFBAuthorTF.getText();
        BookService.publisher = AFBPublisherTF.getText();
        BookService.takenDate = AFBTakenDateTF.getText();
        BookService.price = AFBPriceTF.getText();
        if (AFBLanguageCB.getValue().equals("Tất cả")) {
            BookService.language = "";
        } else {
            BookService.language = AFBLanguageCB.getValue();
        }
        if (AFBBookTypeCB.getValue().equals("Tất cả")) {
            BookService.bookType = "";
        } else {
            BookService.bookType = AFBBookTypeCB.getValue();
        }

        if (!AFBBookNameTF.getText().isEmpty() || !AFBAuthorTF.getText().isEmpty() || !AFBPriceTF.getText().isEmpty() || !AFBTakenDateTF.getText().isEmpty() || !AFBPublisherTF.getText().isEmpty()) {
            if (AFBBookNameTF.getText().contains("-") || AFBAuthorTF.getText().contains("-") || AFBPublisherTF.getText().contains("-") || AFBTakenDateTF.getText().contains("-") || AFBPriceTF.getText().contains("-")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Không nhập kí tự đặc biệt!");
                alert.show();
                return;
            }
            ObservableList<Book> books = FXCollections.observableArrayList(BookService.advancedFindBook());
            if (!books.isEmpty()) {
                AFBBookIDTC.setCellValueFactory(new PropertyValueFactory<>("bookID"));
                AFBBookNameIDTC.setCellValueFactory(new PropertyValueFactory<>("bookName"));
                AFBAuthorIDTC.setCellValueFactory(new PropertyValueFactory<>("author"));
                AFBBookTypeIDTC.setCellValueFactory(new PropertyValueFactory<>("bookType"));
                AFBLanguageIDTC.setCellValueFactory(new PropertyValueFactory<>("language"));
                AFBAvailableIDTC.setCellValueFactory(new PropertyValueFactory<>("availableAmount"));
                AFBResultTV.setItems(books);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Không tìm thấy kết quả nào!");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Hãy nhập cụm từ bạn muốn tìm!");
            alert.show();
        }
    }

}
