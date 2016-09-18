package library.test;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;

import library.daos.BookDAO;
import library.daos.LoanDAO;
import library.daos.MemberDAO;
import library.entities.Book;
import library.entities.Loan;
import library.entities.Member;

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
	 * -DONE search by loan
	 * -DONE search by member
	 * -DONE search by book
	 * -DONE search by book title
	 * -DONE find overdue loans
	 *
	 * MemberDAO
	 * -DONE search by member
	 * -DONE search by last name
	 * -DONE search by email address
	 * -DONE search by first and last names
	 *
	 */

	Book book1;
	Book book2;
	Book book3;
	Book book4;
	Book book5;
	Book testBook;

	Member member1;
	Member member2;
	Member member3;
	Member member4;
	Member testMember;

	Loan loan1;
	Loan loan2;
	Loan loan3;

	public DAOTest()
	{
		initialise();
	}

	public void initialise()
	{
		/*
		 * Clear anything from previous tests.
		 * (I know this one gets run first,
		 * so this wouldn't matter, but best
		 * to be flexible)
		 */
		BookDAO.getInstance().clear();
		MemberDAO.getInstance().clear();
		LoanDAO.getInstance().clear();

		book1 = mock(Book.class);
		book2 = mock(Book.class);
		book3 = mock(Book.class);
		book4 = mock(Book.class);
		book5 = mock(Book.class);

		testBook = mock(Book.class); //will not be added to DAOs

		member1 = mock(Member.class);
		member2 = mock(Member.class);
		member3 = mock(Member.class);
		member4 = mock(Member.class);

		testMember = mock(Member.class); //will not be added to DAOs

		loan1 = mock(Loan.class);
		loan2 = mock(Loan.class);
		loan3 = mock(Loan.class);

		when(book1.getTitle()).thenReturn("Ender's Game");
		when(book2.getTitle()).thenReturn("Speaker For The Dead");
		when(book3.getTitle()).thenReturn("Darwin's Radio");
		when(book4.getTitle()).thenReturn("Darwin's Children");
		when(book5.getTitle()).thenReturn("Solitaire Mystery");
		when(testBook.getTitle()).thenReturn("Optics");

		when(book1.getAuthor()).thenReturn("Orson Scott Card");
		when(book2.getAuthor()).thenReturn("Orson Scott Card");
		when(book3.getAuthor()).thenReturn("Greg Bear");
		when(book4.getAuthor()).thenReturn("Greg Bear");
		when(book5.getAuthor()).thenReturn("Jostein Gaarder");
		when(testBook.getAuthor()).thenReturn("Eugene Hect");

		when(book1.getId()).thenReturn(0);
		when(book2.getId()).thenReturn(1);
		when(book3.getId()).thenReturn(2);
		when(book4.getId()).thenReturn(3);
		when(book5.getId()).thenReturn(4);
		when(testBook.getId()).thenReturn(5);

		Book[] bookArr = {book1, book2, book3, book4, book5};
		BookDAO.getInstance().add(bookArr);

		when(member1.getId()).thenReturn(0);
		when(member2.getId()).thenReturn(1);
		when(member3.getId()).thenReturn(2);
		when(member4.getId()).thenReturn(3);
		when(testMember.getId()).thenReturn(4);

		when(member1.getFirstName()).thenReturn("Valentine");
		when(member2.getFirstName()).thenReturn("Peter");
		when(member3.getFirstName()).thenReturn("Mitch");
		when(member4.getFirstName()).thenReturn("Hans-Thomas");
		when(testMember.getFirstName()).thenReturn("Primrose");

		when(member1.getLastName()).thenReturn("Wiggin");
		when(member2.getLastName()).thenReturn("Wiggin");
		when(member3.getLastName()).thenReturn("Rafelson");
		when(member4.getLastName()).thenReturn("Frode");
		when(testMember.getLastName()).thenReturn("Everdeen");

		when(member1.getEmailAddress()).thenReturn("demosthenes@newsnet.com");
		when(member2.getEmailAddress()).thenReturn("locke@newsnet.com");
		when(member3.getEmailAddress()).thenReturn("mitch@sheva.org");
		when(member4.getEmailAddress()).thenReturn("goldfish@rainbowfizz.net");
		when(testMember.getEmailAddress()).thenReturn("primrose@district13.org");

		Member[] memberArr = {member1, member2, member3, member4};
		MemberDAO.getInstance().add(memberArr);

		when(loan1.getMember()).thenReturn(member1);
		when(loan1.getBook()).thenReturn(book2);
		when(loan2.getMember()).thenReturn(member1);
		when(loan2.getBook()).thenReturn(book3);
		when(loan3.getMember()).thenReturn(member3);
		when(loan3.getBook()).thenReturn(book4);

		when(loan1.getId()).thenReturn(0);
		when(loan2.getId()).thenReturn(1);
		when(loan3.getId()).thenReturn(2);

		when(loan1.checkOverDue(any(Date.class))).thenReturn(true);
		when(loan2.checkOverDue(any(Date.class))).thenReturn(true);
		when(loan3.checkOverDue(any(Date.class))).thenReturn(false);

		Loan[] loanArr = {loan1, loan2, loan3};
		LoanDAO.getInstance().add(loanArr);
	}

	@Test
	public void testFindOverDueLoans()
	{
		List<Loan> loanList = LoanDAO.getInstance().findOverDueLoans();
		assertEquals(2, loanList.size());
		if(loanList.get(0).getBook().getId() == book2.getId())
		{
			assertEquals(book3.getId(), loanList.get(1).getBook().getId());
		}
		else if(loanList.get(0).getBook().getId() == book3.getId())
		{
			assertEquals(book2.getId(), loanList.get(1).getBook().getId());
		}
		else
		{
			assertEquals(true, false); // has to be both loans, one or the other way around
		}
	}

	@Test
	public void testSearchByBookTitleLoanDAO()
	{
		List<Loan> loanList = LoanDAO.getInstance().findLoansByBookTitle("Speaker For The Dead");
		assertEquals(1, loanList.size());
		assertEquals(member1, loanList.get(0).getMember());

		loanList = LoanDAO.getInstance().findLoansByBookTitle("Optics");
		assertEquals(0, loanList.size());
	}

	@Test
	public void testSearchByBookLoanDAO()
	{
		Loan loan = LoanDAO.getInstance().getLoanByBook(book4);
		assertEquals(member3, loan.getMember());

		loan = LoanDAO.getInstance().getLoanByBook(testBook);
		assertEquals(null, loan);
	}

	@Test
	public void testSearchByMemberLoanDAO()
	{
		List<Loan> loanList = LoanDAO.getInstance().findLoansByBorrower(member1);
		assertEquals(2, loanList.size());
		if(loanList.get(0).getBook().getId() == book2.getId())
		{
			assertEquals(book3.getId(), loanList.get(1).getBook().getId());
		}
		else if(loanList.get(0).getBook().getId() == book3.getId())
		{
			assertEquals(book2.getId(), loanList.get(1).getBook().getId());
		}
		else
		{
			assertEquals(true, false); // has to be both members, one or the other way around
		}

		loanList = LoanDAO.getInstance().findLoansByBorrower(testMember);
		assertEquals(0, loanList.size());
	}

	@Test
	public void testSearchByLoanLoanDAO()
	{
		Loan loan = LoanDAO.getInstance().getById(1);
		assertEquals(member1, loan.getMember());
		assertEquals(book3, loan.getBook());

		loan = LoanDAO.getInstance().getById(3);
		assertEquals(null, loan);
	}

	@Test
	public void testSearchFirstLastNamesMemberDAO()
	{
		Member member = MemberDAO.getInstance().findMembersByNames("Hans-Thomas", "Frode");
		assertEquals(3, member.getId());

		member = MemberDAO.getInstance().findMembersByNames("Primrose", "Everdeen");
		assertEquals(null, member);
	}

	@Test
	public void testSearchByEmailAddressMemberDAO()
	{
		List<Member> memberList = MemberDAO.getInstance().findMembersByEmailAddress("mitch@sheva.org");
		assertEquals(1, memberList.size());
		assertEquals("Mitch", memberList.get(0).getFirstName());

		memberList = MemberDAO.getInstance().findMembersByEmailAddress("primrose@district13.org");
		assertEquals(0, memberList.size());
	}

	@Test
	public void testSearchByLastNameMemberDAO()
	{
		List<Member> memberList = MemberDAO.getInstance().findMembersByLastName("Wiggin");
		assertEquals(2, memberList.size());
		if(memberList.get(0).getFirstName().equals("Valentine"))
		{
			assertEquals("Peter", memberList.get(1).getFirstName());
		}
		else if(memberList.get(0).getFirstName().equals("Peter"))
		{
			assertEquals("Valentine", memberList.get(1).getFirstName());
		}
		else
		{
			assertEquals(true, false); // has to be both members, one or the other way around
		}

		memberList = MemberDAO.getInstance().findMembersByLastName("Everdeen");
		assertEquals(0, memberList.size());
	}

	@Test
	public void testSearchByMemberMemberDAO()
	{
		Member member = MemberDAO.getInstance().getById(2);
		assertEquals("Rafelson", member.getLastName());

		member = MemberDAO.getInstance().getById(7);
		assertEquals(null, member);
	}

	@Test
	public void testSearchByAuthorAndTitle()
	{
		Book book = BookDAO.getInstance().findBooksByAuthorTitle("Jostein Gaarder", "Solitaire Mystery");
		assertEquals(4, book.getId());

		book = BookDAO.getInstance().findBooksByAuthorTitle("Eugene Hecht", "Optics");
		assertEquals(null, book);
	}

	@Test
	public void testSearchByBook()
	{
		Book book = BookDAO.getInstance().getById(4);
		assertEquals("Jostein Gaarder", book.getAuthor());
		assertEquals("Solitaire Mystery", book.getTitle());

		book = BookDAO.getInstance().getById(5);
		assertEquals(null, book);
	}

	@Test
	public void testSearchByTitleBookDAO()
	{
		List<Book> bookList = BookDAO.getInstance().findBooksByTitle("Ender's Game");
		assertEquals(1, bookList.size());
		assertEquals("Orson Scott Card", bookList.get(0).getAuthor());

		bookList = BookDAO.getInstance().findBooksByTitle("Quantum Physics of Atoms, Molecules, Solids, Nuclei And Particles");
		assertEquals(0, bookList.size());
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
		else
		{
			assertEquals(true, false); // has to be both books, one or the other way around
		}

		bookList = BookDAO.getInstance().findBooksByAuthor("Robin Hobb");
		assertEquals(0, bookList.size());
	}
}
