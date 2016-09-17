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

	private Member member1; //no fines
	private Member member2; //some fines but less than max fines
	/*Will test:
	 * - DONE if different members have different ids
	 * - DONE should have zero fines on creation
	 * - DONE if finesPayable works (for no fines or fines)
	 * - DONE if hasReachedFineLimit works (for 0 fine, some fine, exactly max fine and over max fine)
	 * - DONE if addFine works (for adding fines for the result to be over or under
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
		assertEquals(true, member1.getFineAmount() == 0);
		assertEquals(true, member2.getFineAmount() == 0);

		member2.addFine(Member.MAX_FINE/2);

		loan_future = new Loan(member1, book1, borrowedInFuture);
		loan_barely_overdue = new Loan(member1, book2, justOver);
		loan_almost_overdue = new Loan(member2, book1, soon);
		loan_overdue = new Loan(member2, book2, overdue);
	}

	@Test
	public void testAddFine()
	{
		float fine1 = member1.getFineAmount();
		float fine2 = member2.getFineAmount();

		member1.addFine(Member.MAX_FINE + 1);
		assertEquals(true, fine1 == member1.getFineAmount()); //should be unchanged, as we can't add beyond max fine

		float fineAmountShortOfMax = Member.MAX_FINE - fine2;
		assertEquals(true, fineAmountShortOfMax > 0);
		member2.addFine(fineAmountShortOfMax/2); //add some, but not enough to pass max fine
		assertEquals(true, member2.getFineAmount() == (fine2 + fineAmountShortOfMax/2)); //should have successfully added

		//put back to what it was
		member1.setFineAmount(fine1);
		member2.setFineAmount(fine2);

		member1.addFine(fine1*-2); //try to rrdce below zero
		member2.addFine(fine2*-1); //reduce to actually zero
		assertEquals(true, member1.getFineAmount() == fine1); //should not have been successful
		assertEquals(true, member1.getFineAmount() == 0); //should have reduced to zero

		//set back to what it was before the test
		member1.setFineAmount(fine1);
		member2.setFineAmount(fine2);
	}

	@Test
	public void testFinesPayable()
	{
		boolean hasFine = member1.hasFinesPayable();
		assertEquals(false, hasFine);

		hasFine = member2.hasFinesPayable();
		assertEquals(true, hasFine);
	}

	@Test
	public void testHasReachedFineLimit()
	{
		float fine1 = member1.getFineAmount();
		float fine2 = member2.getFineAmount();

		boolean hasReachedLimit1 = member1.hasReachedFineLimit();
		boolean hasReachedLimit2 = member2.hasReachedFineLimit();

		assertEquals(false, hasReachedLimit1);
		assertEquals(false, hasReachedLimit2);

		member1.setFineAmount(Member.MAX_FINE);
		member2.setFineAmount(Member.MAX_FINE + 1);

		hasReachedLimit1 = member1.hasReachedFineLimit();
		hasReachedLimit2 = member2.hasReachedFineLimit();

		assertEquals(true, hasReachedLimit1);
		assertEquals(true, hasReachedLimit2);

		//set it back to what it was before the test
		member1.setFineAmount(fine1);
		member2.setFineAmount(fine2);
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

		Calendar cal = new GregorianCalendar(2016,7,13); //13th August 2015
		Date borrowDate = cal.getTime();

		Loan testLoan = new Loan(member1, book2, borrowDate);
		Date dueDate = testLoan.dueDate();

		cal.add(Calendar.DATE, Loan.LOAN_PERIOD);
		Date expectedDueDate = cal.getTime();

		assertEquals(true, dueDate.equals(expectedDueDate));
	}
}
