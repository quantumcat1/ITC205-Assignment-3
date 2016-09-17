package library.test;

import org.junit.Test;
import static org.mockito.Mockito.*;

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


	public DAOTest ()
	{

	}
}
