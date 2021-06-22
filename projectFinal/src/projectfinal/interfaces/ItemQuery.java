// interfaces package
package projectfinal.interfaces;

// Importing required module, libary, and package
import javafx.collections.ObservableList;

// ItemQuery interface
public interface ItemQuery {
    // Declare methods
    public ObservableList getItemList();
    public void showItemList();
    public void insertItem();
    public void updateItem();
    public void deleteItem();
    public boolean searchTitle();
}