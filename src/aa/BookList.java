package aa;

import BOOK.Book;

public class BookList {
    private int usedsize;
    private Book[] books;



//    public BookList() {
//        this.books = new  Book[100];
//        books[0]=new Book("三国演义","罗贯中","9787020125555","小说",false);
//        books[1]=new Book("西游记","吴承恩","9787532512003","小说",false);
//        books[2]=new Book("红楼梦","曹雪芹","9787805464060","小说",false);
//        books[3]=new Book("水浒传","斯奈庵","9787020015016","小说",false);
//        this.usedsize=4;
//
//    }



    //下面可以写一些功能；
    public void  setBooks(int pos,Book book){
        this.books[pos]=book;
    }
    public Book getBook(int pos){
        return this.books[pos];
    }

    public int getUsedsize() {
        return usedsize;
    }

    public Book[] getBooks() {
        return books;
    }

    public void setUsedsize(int usedsize) {
        this.usedsize = usedsize;
    }

    public void addBook(Book book) {
    }
}