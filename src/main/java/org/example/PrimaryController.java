package org.example;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Book;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {


    public Button addBookBtn;


    // menu buttons

    public Button reviews_btn;

    public Button rRTBR_btn;

    public Button currentlyR_btn;

    // layouts

    @FXML
    private HBox readBooksLayout;

    @FXML
    private GridPane bookContainer;


    // currently reading Buttons

    @FXML
    private Button addBtnCR;

    @FXML
    private Button deleteBtnCR;

    @FXML
    private Button updateBtnCR;


    // progress table

    @FXML
    private TableColumn<?, ?> colBookCR;

    @FXML
    private TableColumn<?, ?> colEndDateCR;

    @FXML
    private TableColumn<?, ?> colMemberCR;

    @FXML
    private TableColumn<?, ?> colProgressBarCR;

    @FXML
    private TableColumn<?, ?> colStartDateCR;

    @FXML
    private TableView<?> tableViewCR;


    // data for table


    @FXML
    private ComboBox<?> selectBookCR;


    @FXML
    private ComboBox<?> selectMemberCR;

    @FXML
    private DatePicker startDateCR;

    @FXML
    private TextField pageNumCR;

    @FXML
    private TextField totalPagesCR;


    // menu

    @FXML
    private AnchorPane currentlyReadingForm;

    @FXML
    private AnchorPane recentlyReadTBRForm;

    @FXML
    public AnchorPane reviewsForm;


    // others

    @FXML
    private Button logout;

    @FXML
    private BorderPane main_form;


    private List<Book> recentlyRead;
    public List<Book> tbr;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recentlyRead = new ArrayList<>(recentlyRead());
        tbr = new ArrayList<>(books());
        int column = 0;
        int row = 1;
        try {
            for (Book value : recentlyRead) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(value);
                readBooksLayout.getChildren().add(cardBox);

            }
            for (Book book : tbr) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("book.fxml"));
                VBox bookBox = fxmlLoader.load();
                BookRController bookRController = fxmlLoader.getController();
                bookRController.setData(book);

                if (column == 6) {
                    column = 0;
                    ++row;
                }

                bookContainer.add(bookBox, column++, row);
                GridPane.setMargin(bookBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void updateTbrList() throws IOException {
        bookContainer.getChildren().removeAll();
        int column = 0;
        int row = 1;
        for (Book book : tbr) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("book.fxml"));
            VBox bookBox = fxmlLoader.load();
            BookRController bookRController = fxmlLoader.getController();
            bookRController.setData(book);

            if (column == 6) {
                column = 0;
                ++row;
            }

            bookContainer.add(bookBox, column++, row);
            GridPane.setMargin(bookBox, new Insets(10));
        }
    }

    private List<Book> recentlyRead() {
        List<Book> ls = new ArrayList<>();
        Book book = new Book();
        book.setName("SIX OF CROWS");
        book.setImageSrc("@../../../../img/read/read1.jpg");
        book.setAuthor("Leigh Bardugo");
        ls.add(book);

        book = new Book();
        book.setName("BUNNY");
        book.setImageSrc("@../../../../img/read/read2.jpg");
        book.setAuthor("Mona Awad");
        ls.add(book);

        book = new Book();
        book.setName("I WAS BORN FOR THIS");
        book.setImageSrc("@../../../../img/read/read3.jpg");
        book.setAuthor("Alice Oseman");
        ls.add(book);

        return ls;
    }

    private List<Book> books() {
        List<Book> ls = new ArrayList<>();
        Book book = new Book();
        book.setName("KNOW MY NAME");
        book.setImageSrc("@../../../../img/tbr/tbr1.jpg");
        book.setAuthor("Chanel Miller");
        ls.add(book);

        book = new Book();
        book.setName("EVERYONE IN THIS ROOM WILL SOMEDAY BE DEAD");
        book.setImageSrc("@../../../../img/tbr/tbr2.jpg");
        book.setAuthor("Emily Austin");
        ls.add(book);

        book = new Book();
        book.setName("CLEOPATRA AND FRANKENSTEIN");
        book.setImageSrc("@../../../../img/tbr/tbr3.jpg");
        book.setAuthor("Coco Mellors");
        ls.add(book);

        return ls;
    }

    public void handleAddBookButtonAction(ActionEvent actionEvent) throws IOException {
        addNewBook();
        updateTbrList();

    }

    public void addNewBook() {
        Dialog<Book> dialog = new Dialog<>();
        dialog.initModality(Modality.NONE);
        Stage stage = (Stage) bookContainer.getScene().getWindow();
        dialog.initOwner(stage);

        //start making the stuff in the dialog
        dialog.setTitle("Add a new book");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Label nameLabel = new Label("Name:");
        TextField name = new TextField("");
        Label authorLabel = new Label("Author:");
        TextField author = new TextField("");
        Label imageSrcLabel = new Label("Image Source:");
        TextField imageSrc = new TextField();
        Button imageButton = new Button("Select Image");

        // set an action for the image button
        imageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                String imagePath = selectedFile.toURI().toString();
                imageSrc.setText(imagePath);
            }
        });

        //add all the labels and text fields etc...
        dialogPane.setContent(new VBox(nameLabel, name, authorLabel, author, imageSrcLabel, new HBox(imageSrc, imageButton)));

        //make an ok button
        final Button btOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);

        //Create what you want it to do when you click the button
        btOk.addEventFilter(ActionEvent.ACTION, event -> {
            if (!name.getText().isEmpty() && !author.getText().isEmpty() && !imageSrc.getText().isEmpty()) {
                Book book = new Book();
                book.setName(name.getText());
                book.setAuthor(author.getText());
                book.setImageSrc(imageSrc.getText());
                tbr.add(book);

                // Insert the new book data into the MySQL database
                Connection connection = Database.connectDb();
                try {
                    String sql = "INSERT INTO books (name, author, image_src) VALUES (?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, book.getName());
                    statement.setString(2, book.getAuthor());
                    statement.setString(3, book.getImageSrc());
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("New book inserted successfully.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // else if some text field is empty or incorrect. give them an error message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Incorrect input");
                alert.setHeaderText(null);
                alert.setContentText("Make sure everything is filled in correctly.");
                alert.showAndWait();
                event.consume(); //consume the ok button event so it doesn't close the dialog.
            }
        });

        Optional<Book> optionalResult = dialog.showAndWait(); //show the dialog.
    }


    public void switchForm(ActionEvent event) {

        if (event.getSource() == rRTBR_btn) {

            recentlyReadTBRForm.setVisible(true);
            currentlyReadingForm.setVisible(false);
            reviewsForm.setVisible(false);


        } else if (event.getSource() == currentlyR_btn) {

            recentlyReadTBRForm.setVisible(false);
            currentlyReadingForm.setVisible(true);
            reviewsForm.setVisible(false);


            // TO UPDATE WHEN YOU CLICK THE MENU BUTTON LIKE COACHES BUTTON


        } else if (event.getSource() == reviews_btn) {

            recentlyReadTBRForm.setVisible(false);
            currentlyReadingForm.setVisible(false);
            reviewsForm.setVisible(true);

        }
    }


    private double x = 0;
    private double y = 0;

    public void logout() {

        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                // TO HIDE YOUR DASHBOARD FORM
                logout.getScene().getWindow().hide();

                // LINK YOUR LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("secondary.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });


                stage.setScene(scene);
                stage.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void populateBookComboBox(ActionEvent actionEvent) {
        Connection connection = null;
        try {
            connection = Database.connectDb();
            String sql = "SELECT name FROM books";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            ObservableList<String> bookNames = FXCollections.observableArrayList();
            while (result.next()) {
                bookNames.add(result.getObject("name").toString());
            }
            selectBookCR.setItems(bookNames);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}





