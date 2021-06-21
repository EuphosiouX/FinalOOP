// controller package
package projectfinal.controller;

// Importing required module, libary, and package
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
import projectfinal.classes.entertainments.TvSeries;
import projectfinal.interfaces.CheckTextField;
import projectfinal.interfaces.ItemQuery;

// TvSeriesDashboardController class implements Initializable, CheckTextField, and Item Query
public class TvSeriesDashboardController implements Initializable, CheckTextField, ItemQuery {

    // Create new JDBConnection object from projectfinal.classes.connection package    
    private final JDBConnection dbLink = new JDBConnection("finalproject");
    // Call getConnection() method from JDBConnection and assign it to con variable
    private final Connection con = dbLink.getConnection();
    // Create new FxmlLoader object from  projectfinal.classes.loader package
    private final FxmlLoader loader = new FxmlLoader();
    
    // All FXML components id/variable
    @FXML
    private TableView<TvSeries> tvSeriesTb;
    @FXML
    private TableColumn<TvSeries, Integer> idClm;
    @FXML
    private TableColumn<TvSeries, String> titleClm;
    @FXML
    private TableColumn<TvSeries, String> genreClm;
    @FXML
    private TableColumn<TvSeries, Integer> yearClm;
    @FXML
    private TableColumn<TvSeries, Integer> seasonsClm;
    @FXML
    private TableColumn<TvSeries, Integer> episodesClm;
    @FXML
    private TableColumn<TvSeries, String> statusClm;
    @FXML
    private TextField titleField;
    @FXML
    private ComboBox genreComb;
    @FXML
    private TextField yearField;
    @FXML
    private TextField seasonsField;
    @FXML
    private TextField episodesField;
    @FXML
    private ComboBox statusComb;
    
    // FXML function to handle action if 'Insert' button is pressed    
    @FXML
    private void handleTvSeriesInsertButton(ActionEvent event) {
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
    private void handleTvSeriesUpdateButton(ActionEvent event) {
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
    private void handleTvSeriesDeleteButton(ActionEvent event) {
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
    private void handleTvSeriesTableClicked(MouseEvent event) {
        // Set text fields with the item available in the row     
        TvSeries tvSeries = tvSeriesTb.getSelectionModel().getSelectedItem();
        titleField.setText(tvSeries.getTitle());
        yearField.setText("" + tvSeries.getYear());
        seasonsField.setText("" + tvSeries.getSeasons());
        episodesField.setText("" + tvSeries.getEpisodes());
    }
    
    // Function that override from Initializable in javafx.fxml
    // Function to inizialize the shown FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set items in 'Genre' combo box
        ObservableList<String> genreList = FXCollections.observableArrayList("Anime", "Drama", "Comedy", "Mystery", "Cartoon");
        genreComb.setItems(genreList);
        // Set items in 'Status' combo box
        ObservableList<String> statusList = FXCollections.observableArrayList("NOT WATCHED", "WATCHING", "WATCHED");
        // Show the table by calling showItemList()
        statusComb.setItems(statusList);
        showItemList();
    }    
    
    // Function that override from CheckTextField in projectfinal.interfaces
    // Function to check if text field is empty or not
    @Override
    public boolean textFieldIsEmpty() {
        return titleField.getText().isEmpty() == true || seasonsField.getText().isEmpty() == true || yearField.getText().isEmpty() == true || episodesField.getText().isEmpty() == true;  
    }
    
    // Function that override from ItemQuery in projectfinal.interfaces
    // Function to get items from the table
    @Override
    public ObservableList<TvSeries> getItemList(){
        // Create tvseries list
        ObservableList<TvSeries> tvSeriesList = FXCollections.observableArrayList();
        // Set query
        String query = "SELECT * FROM tvseries";
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
               // Create new TvSeries object from projectfinal.classes.classes package                
               TvSeries tvSeries = new TvSeries(rs.getInt("id"), rs.getString("title"), rs.getString("genre"), rs.getInt("year"), rs.getString("status"), rs.getInt("seasons"), rs.getInt("episodes"));
               // add tvseries to tvseries list
               tvSeriesList.add(tvSeries);
            }
        }
        // Catch exception if query is not correct
        catch(Exception ex){
            // Print stack trace
            ex.printStackTrace();
        }
        return tvSeriesList;
    }
    
    // Function that override from ItemQuery in projectfinal.interfaces
    // Function to show items from the table
    @Override
    public void showItemList(){
        // Create list by calling getItemList()
        ObservableList<TvSeries> list = getItemList();
        // Set cell value factory of each table's cell
        idClm.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleClm.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreClm.setCellValueFactory(new PropertyValueFactory<>("genre"));
        yearClm.setCellValueFactory(new PropertyValueFactory<>("year"));
        seasonsClm.setCellValueFactory(new PropertyValueFactory<>("seasons")); 
        episodesClm.setCellValueFactory(new PropertyValueFactory<>("episodes"));
        statusClm.setCellValueFactory(new PropertyValueFactory<>("status"));
        // Set items in the table
        tvSeriesTb.setItems(list);
    }
    
    // Function that override from ItemQuery in projectfinal.interfaces
    // Function to insert items to the table
    @Override
    public void insertItem(){
        // Set query
        String query = "INSERT INTO tvseries VALUES (NULL, '" + titleField.getText() + "','" + genreComb.getSelectionModel().getSelectedItem().toString() + "'," + yearField.getText() + "," 
                       + seasonsField.getText() + "," + episodesField.getText() + ",'" + statusComb.getSelectionModel().getSelectedItem().toString() +  "')";
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
        String query = "UPDATE  tvseries SET title  = '" + titleField.getText() + "', genre = '" + genreComb.getSelectionModel().getSelectedItem().toString() + "', year = " +
                yearField.getText() + ", seasons = " + seasonsField.getText()+ ", episodes = " + episodesField.getText() + ", status = '" + statusComb.getSelectionModel().getSelectedItem().toString() + "' WHERE title = '" + titleField.getText() + "'";
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
        String query = "DELETE FROM tvseries WHERE title = '" + titleField.getText() + "'";
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
        String query = "SELECT count(1) FROM tvseries WHERE title = '" + titleField.getText() + "'";
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
}