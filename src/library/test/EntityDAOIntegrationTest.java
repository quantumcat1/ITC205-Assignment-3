package library.test;

import java.util.Calendar;
import java.util.Date;

import library.daos.BookDAO;
import library.daos.LoanDAO;
import library.daos.MemberDAO;
import library.entities.Book;
import library.entities.Loan;
import library.entities.Member;

public class EntityDAOIntegrationTest extends DAOTest
{
	/*making sure the real objects work with
	 * the DAOs, not just mock objects. So
	 * we do the exact same tests, only with
	 * real objects.
	 */
	public EntityDAOIntegrationTest()
	{
		initialise();
	}

	@Override
	public void initialise()
	{
		//ensure ids will start with zero
		Book.resetCounter();
		Loan.resetCounter();
		Member.resetCounter();

		//clear anything from previous tests
		BookDAO.getInstance().clear();
		MemberDAO.getInstance().clear();
		LoanDAO.getInstance().clear();

		book1 = new Book("Orson Scott Card", "Ender's Game", "9780812550702");
		book2 = new Book("Orson Scott Card", "Speaker For The Dead", "9780812550757");
		book3 = new Book("Greg Bear", "Darwin's Radio", "9780613277860");
		book4 = new Book("Greg Bear", "Darwin's Children", "9780007132386");
		book5 = new Book("Jostein Gaarder", "Solitaire Mystery", "9780425159996");
		testBook = new Book("Eugene Hecht", "Optics", "9780321188786");

		Book[] bookArr = {book1, book2, book3, book4, book5};
		BookDAO.getInstance().add(bookArr);

		member1 = new Member("Valentine", "Wiggin", "02 64447777", "demosthenes@newsnet.com");
		member2 = new Member("Peter", "Wiggin", "02 64447777", "locke@newsnet.com");
		member3 = new Member("Mitch", "Rafelson", "03 9666 3333", "mitch@sheva.org");
		member4 = new Member("Hans-Thomas", "Frode", "04 7666 1212", "goldfish@rainbowfizz.net");
		testMember = new Member("Primrose", "Everdeen", "05 6666 3333", "primrose@district13.org");

		Member[] memberArr = {member1, member2, member3, member4};
		MemberDAO.getInstance().add(memberArr);

		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		cal.add(Calendar.DATE, -2*Loan.LOAN_PERIOD);
		Date past = cal.getTime();

		loan1 = new Loan(member1, book2, past); //overdue
		loan2 = new Loan(member1, book3, past); //overdue
		loan3 = new Loan(member3, book4, now); //just borrowed (not overdue)

		Loan[] loanArr = {loan1, loan2, loan3};
		LoanDAO.getInstance().add(loanArr);
	}
}
