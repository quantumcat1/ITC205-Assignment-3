package library.interfaces.daos;

import java.util.List;

import library.entities.Book;

public interface IBookDAO
{
	public Book addBook(String author, String title, String callNo);

	public Book getBookByID(int id);

	public List<Book> listBooks();

	public List<Book> findBooksByAuthor(String author);

	public List<Book> findBooksByTitle(String title);

	public List<Book> findBooksByAuthorTitle(String author, String title);
}
