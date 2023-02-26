package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;
import org.controlsfx.control.PopOver;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    public Button addBookBtn;
    @FXML
    private HBox readBooksLayout;

    @FXML
    private GridPane bookContainer;

    private List<Book> recentlyRead;
    private List<Book> tbr;

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

    @FXML
    public void handleAddBookButtonAction(javafx.event.ActionEvent actionEvent) throws IOException {
        // Load the FXML file for the add book window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addBook.fxml"));
        Parent root = loader.load();

        // Get the controller for the add book window
        AddBookController addBookController = loader.getController();

        // Wait for the user to enter book information and click "Add"
        Stage addBookStage = new Stage();
        addBookStage.setScene(new Scene(root));
        addBookStage.showAndWait();

        // Add the new book to the list of TBR books
        if (addBookController.isAddButtonClicked()) {
            Book book = new Book();
            book.setName(addBookController.getNameField().getText());
            book.setImageSrc(addBookController.getImageField().getText());
            book.setAuthor(addBookController.getAuthorField().getText());
            tbr.add(book);

            // Add the new book to the TBR grid in its card layout
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("book.fxml"));
                VBox bookBox = fxmlLoader.load();
                BookRController bookRController = fxmlLoader.getController();
                bookRController.setData(book);

                int column = (tbr.size() - 1) % 6;
                int row = (tbr.size() - 1) / 6 + 1;

                bookContainer.add(bookBox, column, row);
                GridPane.setMargin(bookBox, new Insets(10));

                // Add the popover to show full book information on hover
                PopOver popOver = new PopOver();
                FXMLLoader popOverLoader = new FXMLLoader();
                popOverLoader.setLocation(getClass().getResource("bookDetails.fxml"));
                HBox detailsBox = popOverLoader.load();
                BookDetailsController bookDetailsController = popOverLoader.getController();
                bookDetailsController.setData(book);
                popOver.setContentNode(detailsBox);
                popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
                popOver.setDetachable(false);
                popOver.setAutoHide(true);
                popOver.setAutoFix(true);
                popOver.setHideOnEscape(true);

                bookBox.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) {
                        // Open the book details popover when the book box is double-clicked
                        popOver.show(bookBox);
                    }
                });

                bookBox.setOnMouseEntered(event -> {
                    // Show the book details popover when the mouse enters the book box
                    popOver.show(bookBox);
                });

                bookBox.setOnMouseExited(event -> {
                    // Hide the book details popover when the mouse exits the book box
                    popOver.hide();
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



