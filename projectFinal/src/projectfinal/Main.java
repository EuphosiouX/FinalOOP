// base package
package projectfinal;

// Importing required module, libary, and package
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Main class extends Application
public class Main extends Application {
    
    // Start the JavaFX application
    @Override
    public void start(Stage stage) throws Exception {
        // Create root fxml
        Parent root = FXMLLoader.load(getClass().getResource("/projectfinal/fxml/Login.fxml"));
        // Set stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        // Show stage
        stage.show();
    }

    public static void main(String[] args) {
        // Launch application
        launch(args);
    } 
}
