/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import static Controller.Admin.UsersManagmentController.selectedUserToUpdate;
import static Controller.Admin.UsersManagmentController.updateStage;
import Model.Accounts;
import Model.DB;
import Model.User;
import View.ViewManager;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yahya
 */
public class AccountsManagmentController implements Initializable {

    public static Accounts selectedAccountToUpdate;
    public static Stage updateStage;

    @FXML
    private Button usersManagmentPageBtn;
    @FXML
    private Button accountsPageBtn;
    @FXML
    private Button operationsPageBtn;
    @FXML
    private Button createNewAccountrBtn;
    @FXML
    private Button showAllAccountsBtn;
    @FXML
    private Button updateSelectedAccountBtn;
    @FXML
    private Button deleteSelectedAccountBtn;
    @FXML
    private Button searchAccountBtn;
    @FXML
    private TextField accontSearchTF;
    @FXML
    private TableView<Accounts> accountTableView;
    @FXML
    private TableColumn<Accounts, Integer> idCol;
    @FXML
    private TableColumn<Accounts, Integer> accountNoCol;
    @FXML
    private TableColumn<Accounts, String> usernameCol;
    @FXML
    private TableColumn<Accounts, String> currencyCol;
    @FXML
    private TableColumn<Accounts, Double> balanceCol;
    @FXML
    private TableColumn<Accounts, Date> creationDateCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        accountNoCol.setCellValueFactory(new PropertyValueFactory<>("account_number"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        currencyCol.setCellValueFactory(new PropertyValueFactory<>("currency"));
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        creationDateCol.setCellValueFactory(new PropertyValueFactory<>("creation_date"));
    }

    @FXML
    private void showUsersManagmentPage(ActionEvent event) {
        ViewManager.adminPage.changeSceneToUsersManagment();
    }

    @FXML
    private void showAccountsPage(ActionEvent event) {
    }

    @FXML
    private void showOperationsPage(ActionEvent event) {
    }

    @FXML
    private void showAccountCreationPage(ActionEvent event) {
        ViewManager.adminPage.changeSceneToAccountsCreation();
    }

    @FXML
    private void showAllAccounts(ActionEvent event) {
        try {
            Connection conn = DB.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM accounts");
            ResultSet rs = stmt.executeQuery();
            ObservableList<Accounts> accountsList = FXCollections.observableArrayList();
            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int account_number = rs.getInt("account_number");
                String username = rs.getString("username");
                String currency = rs.getString("currency");
                double balance = rs.getDouble("balance");
                LocalDate creation_date = rs.getDate("creation_date").toLocalDate();
                Accounts account = new Accounts();
                account.setId(id);
                account.setUser_id(user_id);
                account.setAccount_number(account_number);
                account.setUsername(username);
                account.setCurrency(currency);
                account.setBalance(balance);
                account.setCreation_date(creation_date);
                accountsList.add(account);
            }
            accountTableView.setItems(accountsList);
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Something Went Wrong!");
            alert.showAndWait();
            ex.printStackTrace();
        }
    }

    @FXML
    private void updateSelectedAccount(ActionEvent event) throws IOException {
        //check if there is an user selected from the TableView
        if (accountTableView.getSelectionModel().getSelectedItem() != null) {
            //store the selected user from the TableView in our global var user selectedUserToUpdate   
            selectedAccountToUpdate = accountTableView.getSelectionModel().getSelectedItem();
            //load update page fxml
            FXMLLoader loaderUpdate = new FXMLLoader(getClass().getResource("/View/AdminFXML/UpdateAccount.fxml"));
            Parent rootUpdate = loaderUpdate.load();
            Scene updateAccountScene = new Scene(rootUpdate);
            //store loaded fxml in our global stage updateStage and show it
            updateStage = new Stage();
            updateStage.setScene(updateAccountScene);
            updateStage.setTitle("Update user " + selectedAccountToUpdate.getUsername());
            updateStage.show();
        }

    }

    @FXML
    private void deleteSelectedAccount(ActionEvent event) {
        if (accountTableView.getSelectionModel().getSelectedItem() != null) {
            Accounts selectedAccount = accountTableView.getSelectionModel().getSelectedItem();
            Alert deleteConfirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            deleteConfirmAlert.setTitle("Account delete");
            deleteConfirmAlert.setContentText("Are you sure to delete this account ?");
            deleteConfirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        //delete the selected user from database table using delete method in our User model
                        selectedAccount.delete();
                    } catch (SQLException ex) {
                        Logger.getLogger(UsersManagmentController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(UsersManagmentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Alert deletedSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
                    deletedSuccessAlert.setTitle("Account deleted");
                    deletedSuccessAlert.setContentText("Account deleted");
                    deletedSuccessAlert.show();
                }
            });
        } else {
            Alert warnAlert = new Alert(Alert.AlertType.WARNING);
            warnAlert.setTitle("Select an account");
            warnAlert.setContentText("Please select an account from the table view");
            warnAlert.show();
        }
    }

    @FXML
    private void searchForAnAccount(ActionEvent event) {
    }

}
