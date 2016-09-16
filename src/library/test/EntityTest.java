package library.test;

import org.junit.Test;



import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import library.entities.*;

public class EntityTest
{
	private Book book1;
	private Book book2;
	/*Will test:
	*- whether making more than one books results in a different id
	*- "repair" only turns a non-loaned damaged book into an acceptable book and doesn't change the state otherwise
	*- "dispose" only turns a damaged book into a disposed book and doesn't touch the state otherwise
	**/

	private Loan loan_future;
	private Loan loan_overdue;
	private Loan loan_almost_overdue;
	private Loan loan_barely_overdue;
	/*Will test:
	 * - whether making more than one loan results in them having different ids
	 * - checkOverdue works in several cases (overdue a long time ago, overdue same
	 * day (slightly before or slightly after borrow date, currently on loan but not
	 * due(borrow date in past but overdue date in future), not yet borrowed.
	 * - dueDate gives the correct date
	 */

	private Member member;
	/*Will test:
	 * - if finesPayable works (for no fines or fines)
	 * - if hasReachedFineLimit works (for has and hasn't)
	 * - if addFine works (for adding fines for the result to be over or under
	 * max fine and exactly max fine, and for paying fines, reducing it to
	 * below zero, exactly zero, or above zero)
	 */

	public EntityTest()
	{
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();

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
		cal = Calendar.getInstance(); //now
		cal.add(Calendar.DATE, 2);
		Date borrowedInFuture = cal.getTime();

		/*let's check our dates are what we expect.
		*earliest ... latest
		*overdue - justOver - exactlyOverDue - soon - notOverDue - borrowedInFuture
		*/
		assertEquals(true, overdue.before(justOver));
		assertEquals(true, justOver.before(exactlyOverDue));
		assertEquals(true, exactlyOverDue.before(soon));
		assertEquals(true, soon.before(notOverDue));
		assertEquals(true, notOverDue.before(borrowedInFuture));


		book1 = new Book("Enid Blyton", "The Faraway Tree", "9781405240925");
		book2 = new Book("Norman Juster", "The Phantom Tollbooth", "9780394820378");
		member = new Member("Milo", "Tock", "62225555", "milo@dictionopolis.com");
		loan_future = new Loan(member, book1, borrowedInFuture);
		loan_barely_overdue = new Loan(member, book2, justOver);
		loan_almost_overdue = new Loan(member, book1, soon);
		loan_overdue = new Loan(member, book2, overdue);
	}
	@Test
	public void testDifferentBookIds()
	{
		assertEquals(false, book1.getId() == book2.getId());
	}


}
