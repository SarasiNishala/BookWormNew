package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.service.ServiceFactory;
import lk.ijse.service.custom.UserService;

import java.io.IOException;

public class LoginController {
    @FXML
    public TextField txtUserName;
    @FXML
    public TextField txtPassword;
    @FXML
    private AnchorPane signInPane;

    @FXML
    private CheckBox checkBox;

    @FXML
    private PasswordField hidePassword;

    public static String LoginPageUserName;

    UserService userService = (UserService) ServiceFactory.getServiceFactory().getService(ServiceFactory.ServiceTypes.USER);

    public void initialize(){
        txtPassword.setVisible(false);
    }

    String password = "";
    @FXML
    void showPwdOnAction(ActionEvent event) {
        if (checkBox.isSelected()){
            password = hidePassword.getText();
            txtPassword.setText(password);

            hidePassword.setVisible(false);
            txtPassword.setVisible(true);
        } else {
            password = txtPassword.getText();
            hidePassword.setText(password);

            txtPassword.setVisible(false);
            hidePassword.setVisible(true);
        }
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        LoginPageUserName = txtUserName.getText();

        if (hidePassword.isVisible()) {

            password = hidePassword.getText();
        }else {
            password = txtPassword.getText();
        }

        try {
            boolean isIn = userService.searchUser(LoginPageUserName, password);
            if (!isIn) {
                new Alert(Alert.AlertType.WARNING, "Invalid UserName or Password").show();
            } else {
                System.out.println("user in");
                isAdmin(LoginPageUserName);
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void isAdmin(String username) {
        try {

            boolean isAdmin = userService.checkAdmin(username);

            if(isAdmin){
                System.out.println("admin");
                Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/AdminNavPane.fxml"));

                Scene scene = new Scene(rootNode);
                Stage stage = (Stage) this.signInPane.getScene().getWindow();
                stage.setTitle("DASHBOARD");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            }else{
                System.out.println("customer");
//                Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/CustomerNavPane.fxml"));

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomerNavPane.fxml"));

                Parent rootNode = loader.load();

                CustomerNavPane customerNavPane = loader.getController();

                customerNavPane.setUserName(
                        username
                );

                Scene scene = new Scene(rootNode);
                Stage stage = (Stage) this.signInPane.getScene().getWindow();
                stage.setTitle("DISCOVER");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void OpenSignUpOnAction(MouseEvent event) {
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(getClass().getResource("/view/signUp.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.signInPane.getScene().getWindow();
        stage.setTitle("REGISTER");
        stage.setScene(scene);
        stage.centerOnScreen();
    }
}
