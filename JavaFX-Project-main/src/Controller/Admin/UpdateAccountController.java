/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import static Controller.Admin.AccountsManagmentController.selectedAccountToUpdate;
import Model.Accounts;
import View.ViewManager;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Masterz
 */
public class UpdateAccountController implements Initializable {
    
    private Accounts oldAccount;
    
    @FXML
    private Button saveNewAccountBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField userIDTF;
    @FXML
    private TextField accountNoTF;
    @FXML
    private TextField usernameTF;
    @FXML
    private TextField currencyTF;
    @FXML
    private TextField balanceTF;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.oldAccount = Controller.Admin.AccountsManagmentController.selectedAccountToUpdate;
        userIDTF.setText(String.valueOf(oldAccount.getUser_id()));
        accountNoTF.setText(String.valueOf(oldAccount.getAccount_number()));
        usernameTF.setText(oldAccount.getUsername());
        currencyTF.setText(oldAccount.getCurrency());
        balanceTF.setText(String.valueOf(oldAccount.getBalance()));
        
    }
    
    @FXML
    private void saveNewAccount(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            int userID = Integer.parseInt(userIDTF.getText());
            int accountNo = Integer.parseInt(accountNoTF.getText());
            String username = usernameTF.getText();
            String currency = currencyTF.getText();
            Double Balance = Double.parseDouble(balanceTF.getText());
            Accounts account = new Accounts(userID, accountNo, username, currency, Balance);
            account.setAccount_number(oldAccount.getAccount_number());
            account.update();
            Alert deletedSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
            deletedSuccessAlert.setTitle("Updateed Successfully");
            deletedSuccessAlert.setContentText("Account Updated");
            deletedSuccessAlert.show();
        } catch (SQLException ex) {
            Alert warnAlert = new Alert(Alert.AlertType.ERROR);
            warnAlert.setTitle("Account Updating Error");
            warnAlert.setContentText("Something went wrong");
            warnAlert.show();
            
        }
    }
    
    @FXML
    private void cancelAccountCreation(ActionEvent event) {
        Controller.Admin.AccountsManagmentController.updateStage.close(); 
    }
    
}
