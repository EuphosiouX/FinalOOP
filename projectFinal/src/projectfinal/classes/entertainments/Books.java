// classes.entertainments package
package projectfinal.classes.entertainments;

// Books class extends Entertainments
public class Books extends Entertainments{

    // Set class variables
    private String author;
    private Integer pages;

    // Constructor
    public Books(Integer id, String title, String genre, Integer year, String status, String author, Integer pages) {
        super(id, title, genre, year, status);
        this.author = author;
        this.pages = pages;
    }

    // Author getter
    public String getAuthor() {
        return author;
    }

    // Pages getter
    public Integer getPages() {
        return pages;
    }
}