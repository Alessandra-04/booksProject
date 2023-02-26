package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.Book;
import org.controlsfx.control.PopOver;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class BookRController {

    @FXML
    private Label authorName;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;

    @FXML
    private Label bookSynopsis;

    @FXML
    private Label bookRecommendedBy;

    @FXML
    private Label bookPages;

    private Book book;
    private PopOver popover;

    public void setData(Book book) {
        this.book = book;
        bookImage.setImage(new Image(book.getImageSrc()));
        bookName.setText(book.getName());
        authorName.setText(book.getAuthor());
        bookSynopsis.setText(book.getSynopsis());
        bookRecommendedBy.setText(book.getRecommendedBy());
        bookPages.setText(Integer.toString(book.getPages()));

        // Create the PopOver and set its content
        popover = new PopOver();
        VBox bookInfo = new VBox(10);
        Label synopsis = new Label("Synopsis: " + book.getSynopsis());
        Label recommendedBy = new Label("Recommended by: " + book.getRecommendedBy());
        Label pages = new Label("Pages: " + book.getPages());
        bookInfo.getChildren().addAll(synopsis, recommendedBy, pages);
        popover.setContentNode(bookInfo);
        popover.setAutoHide(true);
        popover.setDetachable(false);
    }

    public void handleDeleteButtonAction(ActionEvent actionEvent) {
        // TODO: Handle delete button action

    }

    private String getBookInfo() {
        // Return a string with all the book information
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(book.getName()).append("\n");
        sb.append("Author: ").append(book.getAuthor()).append("\n");
        sb.append("Synopsis: ").append(book.getSynopsis()).append("\n");
        sb.append("Recommended by: ").append(book.getRecommendedBy()).append("\n");
        sb.append("Pages: ").append(book.getPages());
        return sb.toString();
    }

    public void handleMouseEntered(MouseEvent mouseEvent) {
        PopOver popOver = new PopOver();
        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        popOver.setAutoHide(true);

        // create a VBox to hold all the book information
        VBox bookInfo = new VBox(10);

        // add all the book information to the VBox
        Label bookName = new Label("Know My Name");
        bookName.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label authorName = new Label("Chanel Miller");
        authorName.setStyle("-fx-font-size: 14px;");
        Label synopsis = new Label("A memoir by Chanel Miller, who was sexually assaulted by Brock Turner at Stanford University.");
        Label recommendedBy = new Label("Recommended by Oprah Winfrey");
        Label pages = new Label("Pages: 368");

        bookInfo.getChildren().addAll(bookName, authorName, synopsis, recommendedBy, pages);

        // set the content of the PopOver to the VBox
        popOver.setContentNode(bookInfo);

        // show the PopOver
        popOver.show(bookImage);
    }
}