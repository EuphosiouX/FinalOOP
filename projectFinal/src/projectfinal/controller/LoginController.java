// controller package
package projectfinal.controller;

// Importing required module, libary, and package
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import projectfinal.classes.loader.FxmlLoader;
import projectfinal.classes.connection.JDBConnection;
import projectfinal.interfaces.CheckTextField;

// LoginController class implements CheckTextField
public class LoginController implements CheckTextField{
    
    // Create new JDBConnection object from projectfinal.classes.connection package
    private final JDBConnection dbLink = new JDBConnection("finalproject"); 
    // Call getConnection() method from JDBConnection and assign it to con variable
    private final Connection con = dbLink.getConnection();  
    // Create new FxmlLoader object from  projectfinal.classes.loader package
    private final FxmlLoader loader = new FxmlLoader();
    // Declare stage variable
    private Stage stage;

    // All FXML components id/variable
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button loginBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button signupBtn;

    // FXML function to handle action if 'Login' button is pressed
    @FXML
    private void loginButtonAction(ActionEvent event) throws IOException {
        // Check if textFieldIsEmpty() == false
        if (!textFieldIsEmpty())   
            // Check if checkLogin()) == true
            if(checkLogin()){
                // Show Dashboard.fxml by calling showStage() from FxmlLoader
                loader.showStage("/projectfinal/fxml/Dashboard.fxml");
                // Close current FXML
                stage = (Stage) loginBtn.getScene().getWindow();
                stage.close();
            }
            else{
                // Throw alert
                loader.showAlert("Username / password ivalid or doesn't exist!!");
            }
        else{
            // Throw alert
            loader.showAlert("Fill in required data!!");
        }
    }
    
    // FXML function to handle action if 'Signup' button is pressed
    @FXML
    private void signupButtonAction(ActionEvent event) throws IOException {
        // Check if textFieldIsEmpty() == false
        if (!textFieldIsEmpty()){   
            // Check if checkLogin()) == true
            if(!checkUsername()){
                // Insert username and password to database
                insertUser();
                // Throw alert
                loader.showAlert("Signup successful");
            }
            else{
                // Throw alert
                loader.showAlert("Username already exist");
            }
        }    
        else{
            // Throw alert
            loader.showAlert("Fill in required data!!");
        }
    }
    
    // FXML function to handle action if 'Cancel' button is pressed
    @FXML
    private void cancelButtonAction(ActionEvent event) {
        // Close current FXML
        stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
     
    // Function to check if username and password are available in the database or no
    public boolean checkLogin(){
        // Set query
        String query = "SELECT count(1) FROM users WHERE username = '" + username.getText() + "' AND password = '" + password.getText() + "'";
        // Declare st and rs variable
        Statement st;
        ResultSet rs;
        
        // Try the query
        try{
            // Create statement
            st = con.createStatement();
            // Execute the query
            rs = st.executeQuery(query);
            // While there is/are column(s) in the table
            while(rs.next()){
                // Check if value in first column in databse == 1
                if(rs.getInt(1) == 1){
                    return true;
                }
            }    
        }
        // Catch exception if query is not correct
        catch(Exception ex){
            // Print stack trace
            ex.printStackTrace();
        }
        return false;
    }
    
    // Function to check if username is already exist
    public boolean checkUsername(){
        // Set query
        String query = "SELECT count(1) FROM users WHERE username = '" + username.getText() + "'";
        // Declare st and rs variable
        Statement st;
        ResultSet rs;
        
        // Try the query
        try{
            // Create statement
            st = con.createStatement();
            // Execute the query
            rs = st.executeQuery(query);
            // While there is/are column(s) in the table
            while(rs.next()){
                // Check if value in first column in database == 1
                if(rs.getInt(1) == 1){
                    return true;
                }
            }    
        }
        // Catch exception if query is not correct
        catch(Exception ex){
            // Print stack trace
            ex.printStackTrace();
        }
        return false;
    }

    public void insertUser(){
        // Set query
        String query = "INSERT INTO users VALUES (NULL, '" + username.getText() + "','" + password.getText() + "')";
        // Execute the query by calling executeQuery() from JDBConnection
        dbLink.executeQuery(query);
    }
    
    // Function that override from CheckTextField in projectfinal.interfaces
    // Function to check if text field is empty or not
    @Override
    public boolean textFieldIsEmpty() {
        return (username.getText().isEmpty() == true || password.getText().isEmpty() == true);
    }
}