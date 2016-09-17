package library.test;

import org.junit.Test;



import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import library.entities.*;
import library.enums.EBookState;

public class EntityTest
{
	private Book book1; //acceptable
	private Book book2; //damaged
	private Book book3; //disposed
	/*Will test:
	*- DONE whether making more than one books results in a different id
	*- DONE They should be 'acceptable' condition upon being created
	*- DONE "repair" only turns a damaged book into an acceptable book and doesn't change the state otherwise
	*whether it is on loan is outside the scope of the test - the book itself does not know if
	*it is on loan.
	*- DONE "dispose" only turns a damaged book into a disposed book and doesn't touch the state otherwise
	**/

	private Loan loan_future;
	private Loan loan_overdue;
	private Loan loan_almost_overdue;
	private Loan loan_barely_overdue;
	private Date now;
	/*Will test:
	 * - DONE whether making more than one loan results in them having different ids
	 * - DONE checkOverdue works in several cases (overdue a long time ago, overdue same
	 * day (slightly before or slightly after borrow date, currently on loan but not
	 * due(borrow date in past but overdue date in future), not yet borrowed.
	 * - DONE dueDate gives the correct date
	 */

	private Member member1;
	private Member member2;
	/*Will test:
	 * - DONE if different members have different ids
	 * - if finesPayable works (for no fines or fines)
	 * - if hasReachedFineLimit works (for has and hasn't)
	 * - if addFine works (for adding fines for the result to be over or under
	 * max fine and exactly max fine, and for paying fines, reducing it to
	 * below zero, exactly zero, or above zero)
	 */

	public EntityTest()
	{
		Calendar cal = Calendar.getInstance();
		now = cal.getTime();

		//minus loan period from now so it's exactly overdue
		cal.add(Calendar.DATE, -1*(Loan.LOAN_PERIOD));
		Date exactlyOverDue = cal.getTime();

		//is already exactly over due - add ten minutes so it's a bit over
		cal.add(Calendar.MINUTE, 10);
		Date soon = cal.getTime();

		//Now minus ten mins from 'exactly over due' so it's almost due:
		cal.setTime(exactlyOverDue);
		cal.add(Calendar.MINUTE, -10);
		Date justOver = cal.getTime();

		//One whole day over due:
		cal.setTime(exactlyOverDue);
		cal.add(Calendar.DATE, -1);
		Date overdue = cal.getTime();

		//borrowed in the past but due in future:
		cal.add(Calendar.DATE, Loan.LOAN_PERIOD/2);
		Date notOverDue = cal.getTime();

		//borrowed 2 days in the future:
		cal.setTime(now);
		cal.add(Calendar.DATE, 2);
		Date borrowedInFuture = cal.getTime();

		/*let's check our dates are what we expect.
		*earliest ... latest
		*overdue - justOver - exactlyOverDue - soon - notOverDue - now - borrowedInFuture
		*/
		assertEquals(true, overdue.before(justOver));
		assertEquals(true, justOver.before(exactlyOverDue));
		assertEquals(true, exactlyOverDue.before(soon));
		assertEquals(true, soon.before(notOverDue));
		assertEquals(true, notOverDue.before(now));
		assertEquals(true, now.before(borrowedInFuture));


		book1 = new Book("John Wyndam", "Day Of The Triffids", "100812967127");
		book2 = new Book("Norman Juster", "The Phantom Tollbooth", "9780394820378");
		book3 = new Book("Jostein Gaarder", "Sophie's World", "9781427200860");
		assertEquals(true, book1.getState() == EBookState.ACCEPTABLE);
		assertEquals(true, book2.getState() == EBookState.ACCEPTABLE);
		assertEquals(true, book3.getState() == EBookState.ACCEPTABLE);

		book2.setState(EBookState.DAMAGED);
		book3.setState(EBookState.DISPOSED);
		member1 = new Member("Milo", "Tock", "62225555", "milo@dictionopolis.com");
		member2 = new Member("Moon", "Face", "65552222", "moonface@enchanted_wood.org");
		loan_future = new Loan(member1, book1, borrowedInFuture);
		loan_barely_overdue = new Loan(member1, book2, justOver);
		loan_almost_overdue = new Loan(member2, book1, soon);
		loan_overdue = new Loan(member2, book2, overdue);
	}

	@Test
	public void testDifferentBookIds()
	{
		assertEquals(false, book1.getId() == book2.getId());
	}

	@Test
	public void testDifferentMemberIds()
	{
		assertEquals(false, member1.getId() == member2.getId());
	}

	@Test
	public void testDifferentLoanIds()
	{
		assertEquals(false, loan_overdue.getId() == loan_future.getId());
	}

	@Test
	public void testRepair()
	{
		EBookState stateBook1 = book1.getState();
		EBookState stateBook2 = book2.getState();
		EBookState stateBook3 = book3.getState();

		book1.repair();
		book2.repair();
		book3.repair();
		assertEquals(true, book1.getState() == EBookState.ACCEPTABLE);
		assertEquals(true, book2.getState() == EBookState.ACCEPTABLE);
		assertEquals(true, book3.getState() == EBookState.DISPOSED); //can't repair a book that has been thrown away

		//put back to state before the test
		book1.setState(stateBook1);
		book2.setState(stateBook2);
		book3.setState(stateBook3);
	}

	@Test
	public void testDispose()
	{
		EBookState stateBook1 = book1.getState();
		EBookState stateBook2 = book2.getState();
		EBookState stateBook3 = book3.getState();

		book1.dispose();
		book2.dispose();
		book3.dispose();
		assertEquals(true, book1.getState() == EBookState.ACCEPTABLE); //can't throw away a good book
		assertEquals(true, book2.getState() == EBookState.DISPOSED);
		assertEquals(true, book3.getState() == EBookState.DISPOSED);

		//put back to state before the test
		book1.setState(stateBook1);
		book2.setState(stateBook2);
		book3.setState(stateBook3);
	}

	@Test
	public void testCheckOverDue()
	{
		boolean overdue = loan_future.checkOverDue(now);
		assertEquals(false, overdue);

		overdue = loan_overdue.checkOverDue(now);
		assertEquals(true, overdue);

		overdue = loan_almost_overdue.checkOverDue(now);
		assertEquals(false, overdue);

		overdue = loan_barely_overdue.checkOverDue(now);
		assertEquals(true, overdue);
	}

	@Test
	public void testDueDate()
	{
		/*The only way to test the due dates of the Loan member variables would
		 * be to repeat the exact same code in the due date method (adding a loan
		 * period to the borrow date). This would be a complete waste of time. So
		 * I will make a new loan variable and set dates manually (instead of
		 * them being gotten at runtime).
		 */

		Calendar cal = new GregorianCalendar(2015,7,13); //13th August 2015
		Date borrowDate = cal.getTime();

		Loan testLoan = new Loan(member1, book2, borrowDate);
		Date dueDate = testLoan.dueDate();

		cal.add(Calendar.DATE, Loan.LOAN_PERIOD);
		Date expectedDueDate = cal.getTime();

		assertEquals(true, dueDate.equals(expectedDueDate));
	}
}
