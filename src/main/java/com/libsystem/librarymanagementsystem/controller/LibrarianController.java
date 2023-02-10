package com.libsystem.librarymanagementsystem.controller;

import com.libsystem.librarymanagementsystem.database.BookDAO;
import com.libsystem.librarymanagementsystem.database.BorrowBookDAO;
import com.libsystem.librarymanagementsystem.database.BorrowNoteDAO;
import com.libsystem.librarymanagementsystem.database.ReaderDAO;
import com.libsystem.librarymanagementsystem.model.Book;
import com.libsystem.librarymanagementsystem.model.BorrowNote;
import com.libsystem.librarymanagementsystem.model.Reader;
import com.libsystem.librarymanagementsystem.model.User;
import com.libsystem.librarymanagementsystem.service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibrarianController extends GuessController{
    User userSession;
    @FXML
    private Button LHPReaderManageBTN;
    @FXML
    private Button LHPBorrowReturnManageBTN;
    @FXML
    private Button LHPStoreManageBTN;
    @FXML
    private Button LHPAccountBTN;
    @FXML
    private Button LHPLogoutBTN;

    @FXML
    private void LHPReaderManage(ActionEvent event) {
        loadPage("librarian-reader-management");
    }

    @FXML
    private void LHPBorrowReturnManage(ActionEvent event) {
        loadPage("librarian-borrow-return-management");
    }

    @FXML
    private void LHPStoreManage(ActionEvent event) {
        loadPage("librarian-store-management");
    }

    @FXML
    private void LHPAccount(ActionEvent event) {
        loadPage("librarian-self-info");
    }

    @FXML
    private void LHPLogout(ActionEvent event) {
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
            HPWelcomeLB.setText("Xin chào, " + name + "!");
        }
        if (SIViewAP != null) {
            SIFullNameTF.setText(userSession.getFullName());
            SIUsernameTF.setText(userSession.getUsername());
            SIPasswordPF.setText(userSession.getPassword());
            SIDateOfBirthTF.setText(userSession.getDateOfBirth());
            SIPhoneNumberTF.setText(userSession.getPhoneNumber());
            SIGenderTF.setText(userSession.getGender());
        }
        if (RMViewAP != null) {
            ObservableList<Reader> books = FXCollections.observableArrayList(BookService.getTopBorrowed());
            if (!books.isEmpty()) {
                RMReaderIDTC.setCellValueFactory(new PropertyValueFactory<>("readerID"));
                RMReaderNameTC.setCellValueFactory(new PropertyValueFactory<>("readerName"));
                RMReaderBorrowedTimesTC.setCellValueFactory(new PropertyValueFactory<>("borrowTimes"));
                RMReaderTV.setItems(books);
            }
        }
        if (BRMViewAP != null) {
            ObservableList<BorrowNote> notes = FXCollections.observableArrayList(BorrowReturnService.getWarningNote());
            if (!notes.isEmpty()) {
                BRMBorrowNoteIDTC.setCellValueFactory(new PropertyValueFactory<>("borrowNoteID"));
                BRMReaderIDTC.setCellValueFactory(new PropertyValueFactory<>("readerID"));
                BRMPromiseDateTC.setCellValueFactory(new PropertyValueFactory<>("promiseReturnDate"));
                BRMBorrowNoteTV.setItems(notes);
            }
        }
        if (SMViewAP != null) {
            ObservableList<Book> books = FXCollections.observableArrayList(BookService.getNewestBook());
            if (!books.isEmpty()) {
                SMBookIDTC.setCellValueFactory(new PropertyValueFactory<>("bookID"));
                SMBookNameTC.setCellValueFactory(new PropertyValueFactory<>("bookName"));
                SMAvailableAmountTC.setCellValueFactory(new PropertyValueFactory<>("availableAmount"));
                SMBookTV.setItems(books);
            }
        }
        if (RUViewAP != null) {
            Reader reader = (Reader) Main.stage.getUserData();
            RUReaderIDTF.setText(String.valueOf(reader.getReaderID()));
            RUReaderIDTF.setDisable(true);
            RUFullNameTF.setText(reader.getReaderName());
            RUDateOfBirthTF.setText(reader.getDateOfBirth());
            RUAddressTF.setText(reader.getAddress());
            RUPhoneNumberTF.setText(reader.getPhoneNumber());
            RUIdentityNumberTF.setText(reader.getIdentityNumber());
            RUBelievePointTF.setText(String.valueOf(reader.getBelievePoint()));

            ObservableList<BorrowNote> notes = FXCollections.observableArrayList(BorrowReturnService.getNoteOfReaderID(reader.getReaderID()));
            if (!notes.isEmpty()) {
                RUNoteIDTC.setCellValueFactory(new PropertyValueFactory<>("borrowNoteID"));
                RUBorrowDateTC.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
                RUStatusTC.setCellValueFactory(new PropertyValueFactory<>("status"));
                RUUserFullNameTC.setCellValueFactory(new PropertyValueFactory<>("userName"));
                RUReaderTV.setItems(notes);
            }
        }
        if (BRUViewAP != null) {
            BorrowNote note = (BorrowNote) Main.stage.getUserData();
            BRUReaderIDTF.setText(String.valueOf(note.getReaderID()));
            BRUReaderIDTF.setDisable(true);
            BRUBorrowDateTF.setText(note.getBorrowDate());
            BRUBorrowDateTF.setDisable(true);
            BRUPromisedReturnDateTF.setText(note.getPromiseReturnDate());
            BRUStatusTF.setText(note.getStatus());

            BorrowBookDAO borrowBookDAO = new BorrowBookDAO();
            ObservableList<Book> books = FXCollections.observableArrayList(borrowBookDAO.getAllBookFromNoteID(note.getBorrowNoteID()));
            BRUBookIDTC.setCellValueFactory(new PropertyValueFactory<>("bookID"));
            BRUBookNameTC.setCellValueFactory(new PropertyValueFactory<>("bookName"));
            BRUBookTV.setItems(books);
        }
        if (SAViewAP != null) {
            ObservableList<String> languages = FXCollections.observableArrayList(BookService.getAllLanguage().values());
            ObservableList<String> bookTypes = FXCollections.observableArrayList(BookService.getBookType().values());
            SALanguagesCB.getItems().addAll(languages);
            SABookTypeCB.getItems().addAll(bookTypes);
            SALanguagesCB.setValue(languages.get(0));
            SABookTypeCB.setValue(bookTypes.get(0));
        }
        if (SUViewAP != null) {
            Book book = (Book) Main.stage.getUserData();
            Map<Integer, String> languagesMap = BookService.getAllLanguage();
            Map<Integer, String> bookTypesMap = BookService.getBookType();
            book.setBookType(bookTypesMap.get(book.getBookTypeID()));
            book.setLanguage(languagesMap.get(book.getLanguageID()));
            SUBookNameTF.setText(book.getBookName());
            SUPriceTF.setText(book.getPrice());
            SUAuthorTF.setText(book.getAuthor());
            SUPublisherTF.setText(book.getPublisher());
            SUAvailableNumberTF.setText(String.valueOf(book.getAvailableAmount()));
            SUTakenDateTF.setText(book.getTakenDate());

            ObservableList<String> languages = FXCollections.observableArrayList(BookService.getAllLanguage().values());
            ObservableList<String> bookTypes = FXCollections.observableArrayList(BookService.getBookType().values());
            SULanguagesCB.getItems().addAll(languages);
            SUBookTypeCB.getItems().addAll(bookTypes);
            SULanguagesCB.setValue(book.getLanguage());
            SUBookTypeCB.setValue(book.getBookType());
        }
    }

    /**
     * Controller of librarian-self-info.fxml
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
        alert.setTitle("Xác nhận cập nhật");
        alert.setContentText("Bạn có chắc chắn muốn cập nhật tài khoản của mình không?");
        Optional<ButtonType> option = alert.showAndWait();
        boolean choice = option.get() == ButtonType.OK;
        if (choice) {
            User user = UserService.authenticate(SIOldUsernameTF.getText(), SIOldPasswordTF.getText());
            if (user == null || !Objects.equals(user.getUsername(), userSession.getUsername())) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Cập nhật thất bại");
                alert1.setContentText("Mật khẩu và tài khoản cũ không chính xác");
                alert1.show();
            } else {
                if (user.getRole().equalsIgnoreCase("librarian")) {
                    if (!(SIFullNameTF.getText().isEmpty() || SIUsernameTF.getText().isEmpty() || SIPasswordPF.getText().isEmpty() || SIDateOfBirthTF.getText().isEmpty() || SIGenderTF.getText().isEmpty() || SIPhoneNumberTF.getText().isEmpty())) {
                        if (UserService.updateUser(SIFullNameTF.getText(), SIUsernameTF.getText(), SIPasswordPF.getText(),SIDateOfBirthTF.getText(), SIGenderTF.getText(), SIPhoneNumberTF.getText(), userSession)) {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("Cập nhật thành công");
                            alert1.setContentText("Cập nhật thành công, truy cập tài khoản lại để xem kết quả");
                            alert1.show();
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

    /**
     * Controller of librarian-reader-management.fxml
     */
    @FXML
    private AnchorPane RMViewAP;
    @FXML
    private TableView<Reader> RMReaderTV;
    @FXML
    private TableColumn<Reader, Integer> RMReaderIDTC;
    @FXML
    private TableColumn<Reader, String> RMReaderNameTC;
    @FXML
    private TableColumn<Reader, Integer> RMReaderBorrowedTimesTC;

    @FXML
    private void RMUpdateInfo() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Cập nhật thông tin");
        dialog.setHeaderText("Nhập mã độc giả cần cập nhật");

        Optional<String> result = dialog.showAndWait();
        String readerID = "";
        if (result.isPresent() && result.get().matches("[0-9]+")) {
            readerID = result.get();
            Reader reader = ReaderService.findReader(readerID);
            if (reader != null) {
                Main.stage.setUserData(reader);
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("librarian-reader-update-info.fxml")));
                } catch (IOException ex) {
                    Logger.getLogger(GuessController.class.getName()).log(Level.SEVERE, null, ex);
                }
                RMViewAP.getChildren().add(root);
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Thao tác thất bại");
                alert1.setContentText("Không tìm thấy độc giả có mã " + readerID);
                alert1.show();
            }
        }
    }

    @FXML
    private void RMAddReader() {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("librarian-reader-add.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(GuessController.class.getName()).log(Level.SEVERE, null, ex);
        }
        RMViewAP.getChildren().add(root);
    }

    @FXML
    private void RMFindReader() {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("librarian-reader-find.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(GuessController.class.getName()).log(Level.SEVERE, null, ex);
        }
        RMViewAP.getChildren().add(root);
    }

    /**
     * Controller of librarian-borrow-return-management.fxml
     */
    @FXML
    private AnchorPane BRMViewAP;
    @FXML
    private TableView<BorrowNote> BRMBorrowNoteTV;
    @FXML
    private TableColumn<BorrowNote, Integer> BRMBorrowNoteIDTC;
    @FXML
    private TableColumn<BorrowNote, Integer> BRMReaderIDTC;
    @FXML
    private TableColumn<BorrowNote, Integer> BRMPromiseDateTC;

    @FXML
    private void BRMUpdateBorrowNote() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Cập nhật thông tin");
        dialog.setHeaderText("Nhập mã phiếu mượn cần cập nhật");

        Optional<String> result = dialog.showAndWait();
        String noteID = "";
        if (result.isPresent() && result.get().matches("[0-9]+")) {
            noteID = result.get();
            BorrowNote note = BorrowReturnService.findNote(noteID);
            if (note != null) {
                Main.stage.setUserData(note);
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("librarian-borrow-return-update.fxml")));
                } catch (IOException ex) {
                    Logger.getLogger(GuessController.class.getName()).log(Level.SEVERE, null, ex);
                }
                BRMViewAP.getChildren().add(root);
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Thao tác thất bại");
                alert1.setContentText("Không tìm thấy độc giả có mã " + noteID);
                alert1.show();
            }
        }
    }

    @FXML
    private void BRMAddBorrowNote() {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("librarian-borrow-return-add.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(GuessController.class.getName()).log(Level.SEVERE, null, ex);
        }
        BRMViewAP.getChildren().add(root);
    }

    @FXML
    private void BRMFindBorrowNote() {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("librarian-borrow-return-find.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(GuessController.class.getName()).log(Level.SEVERE, null, ex);
        }
        BRMViewAP.getChildren().add(root);
    }

    /**
     * Controller of librarian-store-management.fxml
     */
    @FXML
    private AnchorPane SMViewAP;
    @FXML
    private TableView<Book> SMBookTV;
    @FXML
    private TableColumn<Book, Integer> SMBookIDTC;
    @FXML
    private TableColumn<Book, String> SMBookNameTC;
    @FXML
    private TableColumn<Book, Integer> SMAvailableAmountTC;

    @FXML
    private void SMUpdateBook() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Cập nhật thông tin");
        dialog.setHeaderText("Nhập mã sách cần cập nhật");

        Optional<String> result = dialog.showAndWait();
        String bookID = "";
        if (result.isPresent() && result.get().matches("[0-9]+")) {
            bookID = result.get();
            Book book = BookService.findBookByID(bookID);
            if (book != null) {
                Main.stage.setUserData(book);
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("librarian-store-update.fxml")));
                } catch (IOException ex) {
                    Logger.getLogger(GuessController.class.getName()).log(Level.SEVERE, null, ex);
                }
                SMViewAP.getChildren().add(root);
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Thao tác thất bại");
                alert1.setContentText("Không tìm thấy độc giả có mã " + bookID);
                alert1.show();
            }
        }
    }

    @FXML
    private void SMAddBook() {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("librarian-store-add.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(GuessController.class.getName()).log(Level.SEVERE, null, ex);
        }
        SMViewAP.getChildren().add(root);
    }
    @FXML
    private void SMAddBatchBook() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Nhập link file csv");

        Optional<String> result = dialog.showAndWait();
        String filePath = result.get();
        BookDAO bookDAO = new BookDAO();
        if (bookDAO.addBatchBook(filePath)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thêm tệp sách thành công");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thêm tệp sách thất bại");
            alert.show();
        }
    }

    /**
     * Controller of librarian-reader-add.fxml
     */
    @FXML
    private TextField RAFullNameTF;
    @FXML
    private TextField RADateOfBirthTF;
    @FXML
    private TextField RAPhoneNumberTF;
    @FXML
    private TextField RAIdentityNumberTF;
    @FXML
    private TextField RAAddressTF;

    @FXML
    private void RAAddReader(ActionEvent event) {
        if (!(RAFullNameTF.getText().isEmpty() || RADateOfBirthTF.getText().isEmpty() || RAPhoneNumberTF.getText().isEmpty() || RAIdentityNumberTF.getText().isEmpty() || RAAddressTF.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận thêm");
            alert.setContentText("Bạn có chắc chắn muốn thêm độc giả này không?");
            Optional<ButtonType> option = alert.showAndWait();
            boolean choice = option.get() == ButtonType.OK;
            if (choice) {
                if (ReaderService.addReader(RAFullNameTF.getText(), RADateOfBirthTF.getText(), RAPhoneNumberTF.getText(), RAIdentityNumberTF.getText(), RAAddressTF.getText())) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Thêm thành công");
                    alert1.setContentText("Cập nhật thành công, tìm kiếm độc giả để xem kết quả");
                    alert1.show();
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Thao tác thất bại");
                    alert1.show();
                }
            }
        }
    }

    /**
     * Controller of librarian-reader-find.fxml
     */
    @FXML
    private TextField RFReaderIDTF;
    @FXML
    private TextField RFFullNameTF;
    @FXML
    private TextField RFDateOfBirthTF;
    @FXML
    private TextField RFPhoneNumberTF;
    @FXML
    private TextField RFIdentityNumberTF;
    @FXML
    private TableView<Reader> RFReaderTV;
    @FXML
    private TableColumn<Reader, Integer> RFReaderIDTC;
    @FXML
    private TableColumn<Reader, String> RFReaderNameTC;
    @FXML
    private TableColumn<Reader, Integer> RFBelievePointIDTC;
    @FXML
    private TableColumn<Reader, String> RFDateOfBirthTC;

    @FXML
    private void RFFindReader(ActionEvent event) {
        if (RFReaderIDTF.getText().isEmpty()) {
            ReaderService.readerID = -1;
        } else if (RFReaderIDTF.getText().matches("[0-9]+")) {
            ReaderService.readerID = Integer.parseInt(RFReaderIDTF.getText());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Mã độc giả phải là số");
            alert.show();
            return;
        }

        ReaderService.fullName = RFFullNameTF.getText();
        ReaderService.dateOfBirth = RFDateOfBirthTF.getText();
        ReaderService.phoneNumber = RFPhoneNumberTF.getText();
        ReaderService.identityNumber = RFIdentityNumberTF.getText();

        if (!RFReaderIDTF.getText().isEmpty() || !RFFullNameTF.getText().isEmpty() || !RFDateOfBirthTF.getText().isEmpty() || !RFPhoneNumberTF.getText().isEmpty() || !RFIdentityNumberTF.getText().isEmpty()) {
            if (RFFullNameTF.getText().contains("-") || RFDateOfBirthTF.getText().contains("-") || RFPhoneNumberTF.getText().contains("-") || RFIdentityNumberTF.getText().contains("-")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Không nhập kí tự đặc biệt!");
                alert.show();
                return;
            }
            ObservableList<Reader> readers = FXCollections.observableArrayList(ReaderService.findReaders());
            if (!readers.isEmpty()) {
                RFReaderIDTC.setCellValueFactory(new PropertyValueFactory<>("readerID"));
                RFReaderNameTC.setCellValueFactory(new PropertyValueFactory<>("readerName"));
                RFBelievePointIDTC.setCellValueFactory(new PropertyValueFactory<>("believePoint"));
                RFDateOfBirthTC.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

                RFReaderTV.setItems(readers);
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

    /**
     * Controller of librarian-reader-update-info.fxml
     */
    @FXML
    private AnchorPane RUViewAP;
    @FXML
    private TextField RUReaderIDTF;
    @FXML
    private TextField RUFullNameTF;
    @FXML
    private TextField RUDateOfBirthTF;
    @FXML
    private TextField RUPhoneNumberTF;
    @FXML
    private TextField RUIdentityNumberTF;
    @FXML
    private TextField RUAddressTF;
    @FXML
    private TextField RUBelievePointTF;
    @FXML
    private TableView<BorrowNote> RUReaderTV;
    @FXML
    private TableColumn<BorrowNote, Integer> RUNoteIDTC;
    @FXML
    private TableColumn<BorrowNote, String> RUBorrowDateTC;
    @FXML
    private TableColumn<BorrowNote, String> RUStatusTC;
    @FXML
    private TableColumn<BorrowNote, String> RUUserFullNameTC;

    @FXML
    private void RUDeleteReader(ActionEvent event) {
        Reader reader = (Reader) Main.stage.getUserData();
        ReaderDAO readerDAO = new ReaderDAO();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận cập nhật");
        alert.setContentText("Bạn có chắc chắn muốn xóa độc giả này không?");
        Optional<ButtonType> option = alert.showAndWait();
        boolean choice = option.get() == ButtonType.OK;
        if (choice) {
            if (readerDAO.deleteReader(reader.getReaderID())) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Xóa độc giả thành công");
                alert1.show();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setContentText("Hãy thử xóa lại!");
                alert1.show();
            }
        }
    }

    @FXML
    private void RUUpdateReader(ActionEvent event) {
        Reader reader = (Reader) Main.stage.getUserData();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận cập nhật");
        alert.setContentText("Bạn có chắc chắn muốn cập nhật độc giả này không?");
        Optional<ButtonType> option = alert.showAndWait();
        boolean choice = option.get() == ButtonType.OK;
        if (choice) {
            if (!(RUFullNameTF.getText().isEmpty() || RUDateOfBirthTF.getText().isEmpty() || RUAddressTF.getText().isEmpty() || RUPhoneNumberTF.getText().isEmpty() || RUIdentityNumberTF.getText().isEmpty() || RUBelievePointTF.getText().isEmpty())) {
                if (ReaderService.updateReader(reader.getReaderID(), RUFullNameTF.getText(), RUDateOfBirthTF.getText(), RUAddressTF.getText(),RUPhoneNumberTF.getText(), RUIdentityNumberTF.getText(), Integer.parseInt(RUBelievePointTF.getText()))) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Cập nhật thành công");
                    alert1.setContentText("Cập nhật thành công");
                    alert1.show();
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
        }
    }

    /**
     * Controller of librarian-borrow-return-find.fxml
     */
    @FXML
    private TextField BRFReaderNameTF;
    @FXML
    private TextField BRFBorrowDateTF;
    @FXML
    private TextField BRFPromisedReturnDateTF;
    @FXML
    private TextField BRFReaderIDTF;
    @FXML
    private TableView<BorrowNote> BRFBorrowNoteTV;
    @FXML
    private TableColumn<BorrowNote, Integer> BRFNoteIDTC;
    @FXML
    private TableColumn<BorrowNote, String> BRFReaderNameTC;
    @FXML
    private TableColumn<BorrowNote, Integer> BRFReaderIDTC;
    @FXML
    private TableColumn<BorrowNote, String> BRFStatusTC;

    @FXML
    private void BRFFindNote(ActionEvent event) {
        if (BRFReaderIDTF.getText().isEmpty()) {
            BorrowReturnService.readerID = -1;
        } else if (BRFReaderIDTF.getText().matches("[0-9]+")) {
            BorrowReturnService.readerID = Integer.parseInt(BRFReaderIDTF.getText());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Mã độc giả phải là số");
            alert.show();
            return;
        }

        BorrowReturnService.fullName = BRFReaderNameTF.getText();
        BorrowReturnService.borrowDate = BRFBorrowDateTF.getText();
        BorrowReturnService.promisedReturnDate = BRFPromisedReturnDateTF.getText();

        if (!BRFReaderIDTF.getText().isEmpty() || !BRFReaderNameTF.getText().isEmpty() || !BRFBorrowDateTF.getText().isEmpty() || !BRFPromisedReturnDateTF.getText().isEmpty()) {
            if (BRFReaderNameTF.getText().contains("-") || BRFBorrowDateTF.getText().contains("-") || BRFPromisedReturnDateTF.getText().contains("-")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Không nhập kí tự đặc biệt!");
                alert.show();
                return;
            }
            ObservableList<BorrowNote> notes = FXCollections.observableArrayList(BorrowReturnService.findNotes());
            if (!notes.isEmpty()) {
                BRFNoteIDTC.setCellValueFactory(new PropertyValueFactory<>("borrowNoteID"));
                BRFReaderNameTC.setCellValueFactory(new PropertyValueFactory<>("readerName"));
                BRFReaderIDTC.setCellValueFactory(new PropertyValueFactory<>("readerID"));
                BRFStatusTC.setCellValueFactory(new PropertyValueFactory<>("status"));

                BRFBorrowNoteTV.setItems(notes);
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


    /**
     * Controller of librarian-borrow-return-add.fxml
     */
    @FXML
    private TextField BRAReaderIDTF;
    @FXML
    private TextField BRABorrowDateTF;
    @FXML
    private TextField BRAPromisedReturnDateTF;
    @FXML
    private TextField BRADepositTF;
    @FXML
    private TextField BRABookIDTF;
    @FXML
    private TableView<Book> BRABookTV;
    @FXML
    private TableColumn<Book, Integer> BRABookIDTC;
    @FXML
    private TableColumn<Book, String> BRABookNameTC;

    @FXML
    private void BRAAddBorrowNote(ActionEvent event) {
        List<Book> consultations = BRABookTV.getItems();
        ArrayList<Book> books;
        if (consultations instanceof ArrayList<?>) {
            books = (ArrayList<Book>) consultations;
        } else {
            books = new ArrayList<>(consultations);
        }

        if (!BRAReaderIDTF.getText().isEmpty() && !BRABorrowDateTF.getText().isEmpty() && !BRAPromisedReturnDateTF.getText().isEmpty() && !BRADepositTF.getText().isEmpty() && books.size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận thêm");
            alert.setContentText("Bạn có chắc chắn muốn thêm phiếu mượn này không?");
            Optional<ButtonType> option = alert.showAndWait();
            boolean choice = option.get() == ButtonType.OK;
            if (choice) {
                if (BRAReaderIDTF.getText().matches("[0-9]+")) {
                    if (BorrowReturnService.addNote(BRAReaderIDTF.getText(), BRABorrowDateTF.getText(), BRAPromisedReturnDateTF.getText(), BRADepositTF.getText(), books, userSession.getUserID())) {
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Thêm thành công");
                        alert1.setContentText("Cập nhật thành công");
                        alert1.show();
                    } else {
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("Mã độc giả không phù hợp");
                        alert1.show();
                    }
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Mã độc giả không phù hợp");
                    alert1.show();
                }
            }
        }
    }

    @FXML
    private void BRAAddBook(ActionEvent event) {
        BRABookIDTC.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        BRABookNameTC.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        if (BRABookIDTF.getText().matches("[0-9]+")) {
            Book book = BookService.findBookByID(BRABookIDTF.getText());
            if (book == null) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Không tìm thấy sách");
                alert1.show();
            } else {
                BRABookTV.getItems().add(book);
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Không tìm thấy sách");
            alert1.show();
        }
    }

    /**
     * Controller of librarian-borrow-return-update.fxml
     */
    @FXML
    private AnchorPane BRUViewAP;
    @FXML
    private TextField BRUReaderIDTF;
    @FXML
    private TextField BRUBorrowDateTF;
    @FXML
    private TextField BRUPromisedReturnDateTF;
    @FXML
    private TextField BRUStatusTF;
    @FXML
    private TableView<Book> BRUBookTV;
    @FXML
    private TableColumn<Book, Integer> BRUBookIDTC;
    @FXML
    private TableColumn<Book, String> BRUBookNameTC;

    @FXML
    private void BRUDeleteBorrowNote(ActionEvent event) {
        BorrowNote borrowNote = (BorrowNote) Main.stage.getUserData();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận cập nhật");
        alert.setContentText("Bạn có chắc chắn muốn xóa phiếu mượn này không?");
        Optional<ButtonType> option = alert.showAndWait();
        boolean choice = option.get() == ButtonType.OK;
        if (choice) {
            if (BorrowReturnService.deleteBorrowNote(borrowNote.getBorrowNoteID())) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Xóa độc giả thành công");
                alert1.show();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setContentText("Hãy thử xóa lại!");
                alert1.show();
            }
        }
    }

    @FXML
    private void BRUUpdateBorrowNote(ActionEvent event) {
        BorrowNote borrowNote = (BorrowNote) Main.stage.getUserData();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận cập nhật");
        alert.setContentText("Bạn có chắc chắn muốn cập nhật phiếu mượn này không?");
        Optional<ButtonType> option = alert.showAndWait();
        boolean choice = option.get() == ButtonType.OK;
        if (choice) {
            if (!(BRUReaderIDTF.getText().isEmpty() || BRUBorrowDateTF.getText().isEmpty() || BRUPromisedReturnDateTF.getText().isEmpty() || BRUStatusTF.getText().isEmpty())) {
                if (BorrowReturnService.updateNote(borrowNote.getBorrowNoteID(), BRUPromisedReturnDateTF.getText(), BRUStatusTF.getText(), borrowNote)) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Cập nhật thành công");
                    alert1.setContentText("Cập nhật thành công");
                    alert1.show();
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
        }
    }

    /**
     * Controller of librarian-store-add.fxml
     */
    @FXML
    private AnchorPane SAViewAP;
    @FXML
    private TextField SABookNameTF;
    @FXML
    private TextField SAPriceTF;
    @FXML
    private TextField SAAuthorTF;
    @FXML
    private TextField SAPublisherTF;
    @FXML
    private TextField SAAvailableNumberTF;
    @FXML
    private TextField SATakenDateTF;
    @FXML
    private ComboBox<String> SALanguagesCB;
    @FXML
    private ComboBox<String> SABookTypeCB;

    @FXML
    private void SAAddBook(ActionEvent event) {
        if (!(SABookNameTF.getText().isEmpty() || SAPriceTF.getText().isEmpty() || SAAuthorTF.getText().isEmpty() || SAPublisherTF.getText().isEmpty() || SAAvailableNumberTF.getText().isEmpty() || SATakenDateTF.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận thêm");
            alert.setContentText("Bạn có chắc chắn muốn thêm cuốn sách này không?");
            Optional<ButtonType> option = alert.showAndWait();
            boolean choice = option.get() == ButtonType.OK;
            if (choice) {
                if (BookService.addBook(SABookNameTF.getText(), SAPriceTF.getText(), SAAuthorTF.getText(), SAPublisherTF.getText(), SAAvailableNumberTF.getText(), SATakenDateTF.getText(), SALanguagesCB.getValue(), SABookTypeCB.getValue())) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Thêm thành công");
                    alert1.setContentText("Cập nhật thành công");
                    alert1.show();
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Thao tác thất bại");
                    alert1.show();
                }
            }
        }
    }

    /**
     * Controller of librarian-store-update.fxml
     */
    @FXML
    private AnchorPane SUViewAP;
    @FXML
    private TextField SUBookNameTF;
    @FXML
    private TextField SUPriceTF;
    @FXML
    private TextField SUAuthorTF;
    @FXML
    private TextField SUPublisherTF;
    @FXML
    private TextField SUAvailableNumberTF;
    @FXML
    private TextField SUTakenDateTF;
    @FXML
    private ComboBox<String> SULanguagesCB;
    @FXML
    private ComboBox<String> SUBookTypeCB;

    @FXML
    private void SUUpdateBook(ActionEvent event) {
        Book book = (Book) Main.stage.getUserData();
        if (!(SUBookNameTF.getText().isEmpty() || SUPriceTF.getText().isEmpty() || SUAuthorTF.getText().isEmpty() || SUPublisherTF.getText().isEmpty() || SUAvailableNumberTF.getText().isEmpty() || SUTakenDateTF.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận thêm");
            alert.setContentText("Bạn có chắc chắn muốn cập nhật cuốn sách này không?");
            Optional<ButtonType> option = alert.showAndWait();
            boolean choice = option.get() == ButtonType.OK;
            if (choice) {
                if (BookService.updateBook(SUBookNameTF.getText(), SUPriceTF.getText(), SUAuthorTF.getText(), SUPublisherTF.getText(), SUAvailableNumberTF.getText(), SUTakenDateTF.getText(), SULanguagesCB.getValue(), SUBookTypeCB.getValue(), book)) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Thêm thành công");
                    alert1.setContentText("Cập nhật thành công");
                    alert1.show();
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Thao tác thất bại");
                    alert1.show();
                }
            }
        }
    }
}
