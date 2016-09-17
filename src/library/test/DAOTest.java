package library.test;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import library.Main;
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
	 * -search by title
	 * -search by author
	 * -search by book
	 * -search by author and title
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

		Book[] bookList = {book1, book2, book3, book4, book5};

		BookDAO.getInstance().add(bookList);
	}

	@Test
	public void testSearchByTitleBookDAO()
	{
		List<Book> bookList = BookDAO.getInstance().findBooksByTitle("Ender's Game");
		assertEquals(1, bookList.size());
		assertEquals("Orson Scott Card", bookList.get(0).getAuthor());
	}
}
