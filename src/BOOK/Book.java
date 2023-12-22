package BOOK;
public class Book {
    private String ISBN;
    private String name;
    private String author;
    private String type;
    private int number;
    private boolean status;

    public Book(String ISBN, String name, String author, String type, int number, boolean status) {
        this.ISBN = ISBN;
        this.name = name;
        this.author = author;
        this.type = type;
        this.number = number;
        this.status = status;
    }

    // getters and setters for all fields

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}


//package BOOK;
//
//public class Book {
//    private String ISBN;
//    private String name;
//    private String author;
//    private String type;
//    private int number;
//    private boolean status;
//    private int book_id;
//
//    public Book(String ISBN, String name, String author, String type, int number, boolean status) {
//        this.book_id = book_id;
//        this.ISBN = ISBN;
//        this.name = name;
//        this.author = author;
//        this.type = type;
//        this.number = number;
//        this.status = status;
//    }
//
//    public Book(int bookId, String isbn, String name, String author, String type, int number, int remainingNumber, boolean status) {
//    }
//
//    // getters and setters for all fields
//
//    public int getBookId() {
//        return book_id;
//    }
//
//    public void setBookId(int book_id) {
//        this.book_id = book_id;
//    }
//
//    public String getISBN() {
//        return ISBN;
//    }
//
//    public void setISBN(String ISBN) {
//        this.ISBN = ISBN;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public int getNumber() {
//        return number;
//    }
//
//    public void setNumber(int number) {
//        this.number = number;
//    }
//
//    public boolean isStatus() {
//        return status;
//    }
//
//    public void setStatus(boolean status) {
//        this.status = status;
//    }
//
//    public int getRemaining_number() {
//        return 0;
//    }
//
//    public void setRemaining_number(int i) {
//    }
//}