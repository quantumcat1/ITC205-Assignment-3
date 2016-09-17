package library.daos;

import java.util.LinkedList;
import java.util.List;

import library.entities.Book;
import library.entities.Member;

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
		List<Book> list = new LinkedList<Book>();
		for(Book book : database.values())
		{
			if(book.getAuthor() == author)
			{
				list.add(book);
			}
		}
		return list;
	}

	public List<Book> findBooksByTitle(String title)
	{
		List<Book> list = new LinkedList<Book>();
		for(Book book : database.values())
		{
			if(book.getTitle() == title)
			{
				list.add(book);
			}
		}
		return list;
	}

	public Book findBooksByAuthorTitle(String author, String title)
	{
		for(Book book : database.values())
		{
			if(book.getAuthor() == author && book.getTitle() == title)
			{
				return book;
			}
		}
		return null;
	}

}
