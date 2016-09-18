package library.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
/*import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;*/

import library.BorrowUC_CTL;
import library.BorrowUC_CTL.BorrowUC_UI;
import library.Main;
import library.daos.BookDAO;
import library.daos.LoanDAO;
import library.daos.MemberDAO;
import library.entities.Book;
import library.entities.Loan;
import library.entities.Member;
import library.enums.EBorrowState;
import library.hardware.Display;
import library.panels.borrow.*;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest({Main.class})
public class CTLIntegrationTest
{
	BorrowUC_CTL ctl;
	Display display;
	ScanningPanel scanningPanel;
	SwipeCardPanel swipeCardPanel;
	ConfirmLoanPanel confirmLoanPanel;
	RestrictedPanel restrictedPanel;

	/*
	 * To test:

	 * -cardSwiped
	 * -bookScanned
	 * -scansCompleted
	 * -loansConfirmed
	 * -loansRejected
	 * -buildLoanListDisplay
	 *
	 */

	public CTLIntegrationTest()
	{
		//ensure ids will start with zero
		Book.resetCounter();
		Loan.resetCounter();
		Member.resetCounter();

		//clear any data from previous tests
		BookDAO.getInstance().clear();
		MemberDAO.getInstance().clear();
		LoanDAO.getInstance().clear();

		Main.setupTestData();

		//PowerMockito.mockStatic(Main.class);

		display = mock(Display.class);
		scanningPanel = mock(ScanningPanel.class);
		confirmLoanPanel = mock(ConfirmLoanPanel.class);
		restrictedPanel = mock(RestrictedPanel.class);
		swipeCardPanel = mock(SwipeCardPanel.class);

		//when(Main.setEnabled(any(boolean.class), any(boolean.class), any(boolean.class),any(boolean.class))).thenReturn(true);

		ctl = new BorrowUC_CTL(display);
	}

	@Test
	public void testCardSwiped()
	{
		ctl.cardSwiped(2, true);

		//borrower has been set to the id entered:
		assertEquals(MemberDAO.getInstance().getById(2), ctl.getBorrower());

		//member with id 2 has maxed out fines
		assertEquals(EBorrowState.BORROWING_RESTRICTED, ((BorrowUC_UI) (ctl.getUi())).getState());
	}

	@Test
	public void testBookScanned()
	{
		//no books yet scanned, make sure bookList has nothing in it
		assertEquals(0, ctl.getBookList().size());

		ctl.setBorrower(MemberDAO.getInstance().getById(3));
		//otherwise it isn't happy when it tries to make a loan with a null borrower.

		//scan a book that is on loan to make sure it doesn't get added
		ctl.bookScanned(2);
		assertEquals(0, ctl.getBookList().size());

		ctl.bookScanned(14);
		//make sure bookList now has one thing in it
		assertEquals(1, ctl.getBookList().size());
	}

	@Test
	public void testScansCompleted()
	{
		ctl.scansCompleted(true);
		assertEquals(EBorrowState.CONFIRMING_LOANS, ((BorrowUC_UI) (ctl.getUi())).getState());
	}
	@Test
	public void testLoansConfirmed()
	{
		//make sure there is a loan so we can test properly
		List<Loan> loanList = new LinkedList<Loan>();
		loanList.add(new Loan(MemberDAO.getInstance().getById(0), BookDAO.getInstance().getById(13), Calendar.getInstance().getTime()));

		ctl.setLoanList(loanList);
		ctl.loansConfirmed(true);
		assertEquals(EBorrowState.INITIALIZED, ((BorrowUC_UI) (ctl.getUi())).getState());
	}
	@Test
	public void loansRejected()
	{
		//make sure there is a loan so we can test properly
		List<Loan> loanList = new LinkedList<Loan>();
		loanList.add(new Loan(MemberDAO.getInstance().getById(0), BookDAO.getInstance().getById(13), Calendar.getInstance().getTime()));

		ctl.setLoanList(loanList);

		ctl.loansRejected(true);
		assertEquals(EBorrowState.SCANNING_BOOKS, ((BorrowUC_UI) (ctl.getUi())).getState());
		assertEquals(0, ctl.getLoanList().size());
	}
}
