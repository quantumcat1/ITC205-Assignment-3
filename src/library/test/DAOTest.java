package library.test;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import library.daos.BookDAO;
import library.daos.LoanDAO;
import library.daos.MemberDAO;
import library.entities.Book;

public class DAOTest
{
	//don't need actual member variables because we can get them with getInstance()
	/*private BookDAO bookDAO;
	private LoanDAO loanDAO;
	private MemberDAO memberDAO;*/

	/*
	 * To test:
	 *
	 * BookDAO
	 * -DONE search by title
	 * -DONE search by author
	 * -DONE search by book
	 * -DONE search by author and title
	 *
	 * LoanDAO
	 * -search by loan
	 * -search by member
	 * -search by book
	 * -search by book title
	 * -find overdue loans
	 *
	 * MemberDAO
	 * -search by member
	 * -search by last name
	 * -search by email address
	 * -search by first and last names
	 *
	 */

	public DAOTest()
	{
		//don't know how to make an array of mock objects so just have to declare them one by one.
		Book book1 = mock(Book.class);
		Book book2 = mock(Book.class);
		Book book3 = mock(Book.class);
		Book book4 = mock(Book.class);
		Book book5 = mock(Book.class);

		when(book1.getTitle()).thenReturn("Ender's Game");
		when(book2.getTitle()).thenReturn("Speaker For The Dead");
		when(book3.getTitle()).thenReturn("Darwin's Radio");
		when(book4.getTitle()).thenReturn("Darwin's Children");
		when(book5.getTitle()).thenReturn("Solitaire Mystery");

		when(book1.getAuthor()).thenReturn("Orson Scott Card");
		when(book2.getAuthor()).thenReturn("Orson Scott Card");
		when(book3.getAuthor()).thenReturn("Greg Bear");
		when(book4.getAuthor()).thenReturn("Greg Bear");
		when(book5.getAuthor()).thenReturn("Jostein Gaarder");

		when(book1.getId()).thenReturn(0);
		when(book2.getId()).thenReturn(1);
		when(book3.getId()).thenReturn(2);
		when(book4.getId()).thenReturn(3);
		when(book5.getId()).thenReturn(4);

		Book[] bookList = {book1, book2, book3, book4, book5};

		BookDAO.getInstance().add(bookList);
	}

	@Test
	public void testSearchByAuthorAndTitle()
	{
		Book book = BookDAO.getInstance().findBooksByAuthorTitle("Jostein Gaarder", "Solitaire Mystery");
		assertEquals(4, book.getId());
	}

	@Test
	public void testSearchByBook()
	{
		Book book = BookDAO.getInstance().getById(4);
		assertEquals("Jostein Gaarder", book.getAuthor());
		assertEquals("Solitaire Mystery", book.getTitle());
	}

	@Test
	public void testSearchByTitleBookDAO()
	{
		List<Book> bookList = BookDAO.getInstance().findBooksByTitle("Ender's Game");
		assertEquals(1, bookList.size());
		assertEquals("Orson Scott Card", bookList.get(0).getAuthor());
	}
	@Test
	public void testSearchByAuthorBookDAO()
	{
		List<Book> bookList = BookDAO.getInstance().findBooksByAuthor("Greg Bear");
		assertEquals(2, bookList.size());
		if(bookList.get(0).getTitle().equals("Darwin's Radio"))
		{
			assertEquals("Darwin's Children", bookList.get(1).getTitle());
		}
		else if(bookList.get(0).getTitle().equals("Darwin's Children"))
		{
			assertEquals("Darwin's Radio", bookList.get(1).getTitle());
		}
	}
}
