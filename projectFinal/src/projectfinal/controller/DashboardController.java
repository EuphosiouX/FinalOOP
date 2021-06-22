// controller package
package projectfinal.controller;

// Importing required module, library, and package
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import projectfinal.classes.loader.FxmlLoader;

// DashboardController class
public class DashboardController {
    
    // Create new FxmlLoader object from  projectfinal.classes.loader package
    private final FxmlLoader loader = new FxmlLoader();

    // All FXML components id/variable
    @FXML
    private Button moviesBtn;
    @FXML
    private Button booksBtn;
    @FXML
    private Button tvseriesBtn;
    @FXML
    private BorderPane dashboardPane;
    @FXML
    private Button backBtn;
    
    @FXML
    // FXML function to handle action if 'Movies' button is pressed
    private void handleMoviesButton(ActionEvent event) throws IOException {
        // Show MoviesDashboard.fxml inside Dashboard.fxml
        Pane view = loader.getView("/projectfinal/fxml/MoviesDashboard.fxml");    
        dashboardPane.setCenter(view);
    }

    // FXML function to handle action if 'Books' button is pressed
    @FXML
    private void handleBooksButton(ActionEvent event) throws IOException {
        // Show BooksDashboard.fxml inside Dashboard.fxml
        Pane view = loader.getView("/projectfinal/fxml/BooksDashboard.fxml");    
        dashboardPane.setCenter(view);
    }

    // FXML function to handle action if 'Tvseries' button is pressed
    @FXML
    private void handleTvseriesButton(ActionEvent event) throws IOException{
        // Show TvSeriesDashboard.fxml inside Dashboard.fxml
        Pane view = loader.getView("/projectfinal/fxml/TvSeriesDashboard.fxml");    
        dashboardPane.setCenter(view);
    }
    
    // FXML function to handle action if 'Back' button is pressed
    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        // Close current FXML
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();    
        // Show Login.fxml by calling showStage() from FxmlLoader
        loader.showStage("/projectfinal/fxml/Login.fxml");
    }
}    