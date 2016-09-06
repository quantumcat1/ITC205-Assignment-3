package library.daos;

import java.util.List;

import library.entities.Book;
import library.interfaces.daos.IDAO;

public class BookDAO implements IDAO<Book>
{

	@Override
	public Book add(Book book)
	{
		return null;
	}

	@Override
	public Book getById(int id)
	{
		return null;
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

	@Override
	public List<Book> list()
	{
		return null;
	}

}
