// controller package
package projectfinal.controller;

// Importing required module, library, and package
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import projectfinal.classes.loader.FxmlLoader;
import projectfinal.classes.connection.JDBConnection;
import projectfinal.classes.entertainments.Movies;
import projectfinal.interfaces.CheckTextField;
import projectfinal.interfaces.ItemQuery;

// MoviesDashboardController class implements Initializable, CheckTextField, and Item Query
public class MoviesDashboardController implements Initializable, CheckTextField, ItemQuery {
    
    // Create new JDBConnection object from projectfinal.classes.connection package
    private final JDBConnection dbLink = new JDBConnection("finalproject");
    // Call getConnection() method from JDBConnection and assign it to con variable
    private final Connection con = dbLink.getConnection();
    // Create new FxmlLoader object from  projectfinal.classes.loader package
    private final FxmlLoader loader = new FxmlLoader();

    // All FXML components id/variable
    @FXML
    private TableView<Movies> moviesTb;
    @FXML
    private TableColumn<Movies, Integer> idClm;
    @FXML
    private TableColumn<Movies, String> titleClm;
    @FXML
    private TableColumn<Movies, String> directorClm;
    @FXML
    private TableColumn<Movies, String> genreClm;
    @FXML
    private TableColumn<Movies, Integer> yearClm;
    @FXML
    private TableColumn<Movies, String> durationClm;
    @FXML
    private TableColumn<Movies, String> statusClm;
    @FXML
    private TextField titleField;
    @FXML
    private TextField directorField;
    @FXML
    private ComboBox genreComb;
    @FXML
    private TextField yearField;
    @FXML
    private TextField durationField;
    @FXML
    private ComboBox statusComb;
 
    // FXML function to handle action if 'Insert' button is pressed
    @FXML
    private void handleMoviesInsertButton(ActionEvent event) {
        // Check if textFieldIsEmpty() == true
        if(textFieldIsEmpty()){
            // Throw alert
            loader.showAlert("Fill in required data!!");
        }
        // Check if searchTitle() == true
        else if(searchTitle()){
            // Throw alert
            loader.showAlert("Title already exist!!");
        }
        else{
            // Insert item into table by calling insertItem()
            insertItem();
            // Throw alert
            loader.showAlert("Item successfully inserted");
        }  
    }
    
    // FXML function to handle action if 'Update' button is pressed
    @FXML
    private void handleMoviesUpdateButton(ActionEvent event) {
        // Check if textFieldIsEmpty() == true
        if(textFieldIsEmpty()){
            // Throw alert
            loader.showAlert("Fill in required data!!");
        }
        // Check if searchTitle() == false
        else if(!searchTitle()){
            loader.showAlert("Title does not exist!!");
        }
        else{
            // Update item by calling updateItem()
            updateItem();
            // Throw alert
            loader.showAlert("Item successfully updated");
        }  
    }

    // FXML function to handle action if 'Delete' button is pressed
    @FXML
    private void handleMoviesDeleteButton(ActionEvent event) {
        // Check if textFieldIsEmpty() == true
        if(textFieldIsEmpty()){
            // Throw alert
            loader.showAlert("Fill in required data!!");
        }
        // Check if searchTitle() == false
        else if(!searchTitle()){
            // Throw alert
            loader.showAlert("Title does not exist!!");
        }
        else{
            // Delete item by calling deleteItem()
           deleteItem();
           // Throw alert
           loader.showAlert("Item successfully deleted");
        }  
    }
    
    // FXML function to handle action if a row in the table is clicked
    @FXML
    private void handleMoviesTableClicked(MouseEvent event) {
        // Set text fields with the item available in the row 
        Movies movies = moviesTb.getSelectionModel().getSelectedItem();
        titleField.setText(movies.getTitle());
        directorField.setText(movies.getDirector());
        yearField.setText("" + movies.getYear());
        durationField.setText(movies.getDuration());
    }
    
    // Function that override from Initializable in javafx.fxml
    // Function to initialize the shown FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set items in 'Genre' combo box
        ObservableList<String> genreList = FXCollections.observableArrayList("Action", "Drama", "Animated", "Sci-fi", "Fantasy");
        genreComb.setItems(genreList);
        // Set items in 'Status' combo box
        ObservableList<String> statusList = FXCollections.observableArrayList("NOT WATCHED", "WATCHING", "WATCHED");
        statusComb.setItems(statusList);
        // Show the table by calling showItemList()
        showItemList();
    }    
    
    // Function that override from CheckTextField in projectfinal.interfaces
    // Function to check if text field is empty or not
    @Override
    public boolean textFieldIsEmpty() {
        return titleField.getText().isEmpty() == true || directorField.getText().isEmpty() == true 
               || yearField.getText().isEmpty() == true || durationField.getText().isEmpty() == true;  
    }
    
    // Function that override from ItemQuery in projectfinal.interfaces
    // Function to get items from the table
    @Override
    public ObservableList<Movies> getItemList(){
        // Create movie list
        ObservableList<Movies> movieList = FXCollections.observableArrayList();
        // Set query
        String query = "SELECT * FROM movies";
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
               // Create new Movies object from projectfinal.classes.classes package
               Movies movies = new Movies(rs.getInt("id"), rs.getString("title"), rs.getString("genre"), rs.getInt("year"), rs.getString("status"), rs.getString("director"), rs.getString("duration"));
               // add movies to movie list
               movieList.add(movies);
            }
        }
        // Catch exception if query is not correct
        catch(Exception ex){
            // Print stack trace
            ex.printStackTrace();
        }
        return movieList;
    }
    
    // Function that override from ItemQuery in projectfinal.interfaces
    // Function to show items from the table
    @Override
    public void showItemList(){
        // Create list by calling getItemList()
        ObservableList<Movies> list = getItemList();
        // Set cell value factory of each table's cell
        idClm.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleClm.setCellValueFactory(new PropertyValueFactory<>("title"));
        directorClm.setCellValueFactory(new PropertyValueFactory<>("director"));
        genreClm.setCellValueFactory(new PropertyValueFactory<>("genre"));
        yearClm.setCellValueFactory(new PropertyValueFactory<>("year"));   
        durationClm.setCellValueFactory(new PropertyValueFactory<>("duration"));
        statusClm.setCellValueFactory(new PropertyValueFactory<>("status"));
        // Set items in the table
        moviesTb.setItems(list);
    }
    
    // Function that override from ItemQuery in projectfinal.interfaces
    // Function to insert items to the table
    @Override
    public void insertItem(){
        // Set query
        String query = "INSERT INTO movies VALUES (NULL, '" + titleField.getText() + "','" + directorField.getText() + "','" 
                       + genreComb.getSelectionModel().getSelectedItem().toString() + "'," + yearField.getText() + ",'" + durationField.getText() + "','" + statusComb.getSelectionModel().getSelectedItem().toString() +  "')";
        // Execute the query by calling executeQuery() from JDBConnection
        dbLink.executeQuery(query);
        // Show the table by calling showItemList() 
        showItemList();
    }
    
    // Function that override from ItemQuery in projectfinal.interfaces
    // Function to update items in the table
    @Override
    public void updateItem(){
        // Set query
        String query = "UPDATE  movies SET title  = '" + titleField.getText() + "', director = '" + directorField.getText()+ "', genre = '" + genreComb.getSelectionModel().getSelectedItem().toString() + "', year = " +
                yearField.getText() + ", duration = '" + durationField.getText() + "', status = '" + statusComb.getSelectionModel().getSelectedItem().toString() + "' WHERE title = '" + titleField.getText() + "'";
        // Execute the query by calling executeQuery() from JDBConnection
        dbLink.executeQuery(query);
        // Show the table by calling showItemList() 
        showItemList();
    }
    
    // Function that override from ItemQuery in projectfinal.interfaces
    // Function to delete items from the table
    @Override
    public void deleteItem(){
        // Set query
        String query = "DELETE FROM movies WHERE title = '" + titleField.getText() + "'";
        // Execute the query by calling executeQuery() from JDBConnection
        dbLink.executeQuery(query);
        // Show the table by calling showItemList() 
        showItemList();
    }

    // Function that override from ItemQuery in projectfinal.interfaces
    // Function to search if title exist or not in the table
    @Override
    public boolean searchTitle() {
        // Set query
        String query = "SELECT count(1) FROM movies WHERE title = '" + titleField.getText() + "'";
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
}