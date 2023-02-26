package org.example;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class SecondaryController implements Initializable {

    @FXML
    private Label editLabel;

    @FXML
    private AnchorPane loginForm;

    @FXML
    private AnchorPane mainForm;

    @FXML
    private Button sILoginBtn;

    @FXML
    private PasswordField sIPassword;

    @FXML
    private TextField sIUsername;

    @FXML
    private AnchorPane signUpForm;

    @FXML
    private TextField suEmail;

    @FXML
    private PasswordField suPassword;

    @FXML
    private Button suSignUpBtn;

    @FXML
    private TextField suUsername;

    @FXML
    private AnchorPane subForm;

    @FXML
    private Button subSigninBtn;

    @FXML
    private Button subSignupBtn;

    //   DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void login() {

        String sql = "SELECT * FROM admin WHERE username = ? and password = ?";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, sIUsername.getText());
            prepare.setString(2, sIPassword.getText());
            result = prepare.executeQuery();

            Alert alert;

            if (sIUsername.getText().isEmpty() || sIPassword.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                if (result.next()) {

                    Data.username = sIUsername.getText();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();

                    sILoginBtn.getScene().getWindow().hide();

                    // LINK YOUR DASHBOARD FORM
                    Parent root = null;
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("C:\\Users\\aless\\IdeaProjects\\booksProject\\src\\main\\resources\\org\\example\\primary.fxml"));
                        root = loader.load();
                    } catch (IOException e) {
                        // Handle the exception
                        e.printStackTrace();
                    }

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setScene(scene);
                    stage.show();


                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void signup() {

        String sql = "INSERT INTO admin (email, username, password) VALUES(?,?,?)";

        connect = Database.connectDb();

        try {
            Alert alert;

            if (suEmail.getText().isEmpty() || suUsername.getText().isEmpty()
                    || suPassword.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                if (suPassword.getText().length() < 8) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid password :3");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, suEmail.getText());
                    prepare.setString(2, suUsername.getText());
                    prepare.setString(3, suPassword.getText());

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully create new account!");
                    alert.showAndWait();

                    prepare.executeUpdate();

                    suEmail.setText("");
                    suUsername.setText("");
                    suPassword.setText("");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void signupSlider() {

        TranslateTransition slider1 = new TranslateTransition();
        slider1.setNode(subForm);
        slider1.setToX(300);
        slider1.setDuration(Duration.seconds(.5));
        slider1.play();

        slider1.setOnFinished((ActionEvent event) -> {
            editLabel.setText("Login Account");

            subSignupBtn.setVisible(false);
            subSigninBtn.setVisible(true);

        });

    }

    public void loginSlider() {

        TranslateTransition slider1 = new TranslateTransition();
        slider1.setNode(subForm);
        slider1.setToX(0);
        slider1.setDuration(Duration.seconds(.5));
        slider1.play();

        slider1.setOnFinished((ActionEvent event) -> {
            editLabel.setText("Create Account");

            subSignupBtn.setVisible(true);
            subSigninBtn.setVisible(false);
        });

    }

    public void close() {
        javafx.application.Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }
}