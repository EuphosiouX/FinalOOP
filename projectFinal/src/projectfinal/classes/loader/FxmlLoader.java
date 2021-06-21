// classes.loader package
package projectfinal.classes.loader;

// Importing required module, libary, and package
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// FxmlLoader class
public class FxmlLoader {
    
    // Set class variables
    private Pane view;


    // Method to get view of the provided file URL
    public Pane getView(String file) throws IOException {
        view = FXMLLoader.load(getClass().getResource(file));
        return view;
    }

    // Method to show the stage of the provided file URL
    public void showStage(String file) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(file));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    // Method to show alert with the provided message
    public void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}