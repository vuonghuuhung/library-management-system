package com.libsystem.librarymanagementsystem.controller;

import com.libsystem.librarymanagementsystem.model.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class BookInfoController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private Label bookName;
    @FXML
    private Label author;
    @FXML
    private Label publisher;
    @FXML
    private Label bookType;
    @FXML
    private Label takenDate;
    @FXML
    private Label status;

    private Scene preScene;

    public void setPreScene(Scene preScene) {
        this.preScene = preScene;
    }

    @Override
    public void initialize(URL location, ResourceBundle rs) {
        Book book = (Book) Main.stage.getUserData();
//        System.out.println(book.getBookName());
        bookName.setText(book.getBookName());
        publisher.setText(book.getPublisher());
        author.setText(book.getAuthor());
        bookType.setText(book.getBookType());
        takenDate.setText(book.getTakenDate());
        status.setText(book.getAvailableAmount() > 0 ? "Có sẵn" : "Chưa có sẵn");
    }

    @FXML
    private void onClickBack(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

//        Main.stage.initStyle(StageStyle.DECORATED);
//        Main.stage.resizableProperty().setValue(false);
        stage.setScene(preScene);
        stage.show();
    }
}
