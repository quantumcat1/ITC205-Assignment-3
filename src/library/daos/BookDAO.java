package library.daos;

import java.util.List;

import library.entities.Book;

public class BookDAO extends DAO <Book>
{
	private static BookDAO instance = null;

	public static BookDAO getInstance()
	{
		if(instance == null)
		{
			instance = new BookDAO();
		}
		return instance;
	}

	public List<Book> findBooksByAuthor(String author)
	{
		return null;
	}

	public List<Book> findBooksByTitle(String title)
	{
		return null;
	}

	public List<Book> findBooksByAuthorTitle(String author, String title)
	{
		return null;
	}

}
