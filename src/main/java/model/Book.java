package model;

public class Book {
    private String name;
    private String imageSrc;
    private String author;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {

        this.imageSrc = "C:\\Users\\aless\\IdeaProjects\\booksProject\\src\\main\\resources\\img" + imageSrc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getRecommendedBy() {
        return null;
    }

    public String getSynopsis() {
        return null;
    }

    public int getPages() {
        return 0;
    }
}
