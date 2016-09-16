package library;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import library.interfaces.IBorrowUI;
import library.interfaces.IBorrowUIListener;
import library.daos.BookDAO;
import library.daos.LoanDAO;
import library.daos.MemberDAO;
import library.entities.Book;
import library.entities.Loan;
import library.entities.Member;
import library.enums.EBookState;
import library.enums.EBorrowState;
import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.ICardReaderListener;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;
import library.interfaces.hardware.IScannerListener;
import library.panels.borrow.ABorrowPanel;
import library.panels.borrow.ScanningPanel;
import library.panels.borrow.SwipeCardPanel;

public class BorrowUC_CTL implements ICardReaderListener,
									 IScannerListener,
									 IBorrowUIListener
{
	private ICardReader reader;
	private IScanner scanner;
	private IPrinter printer;
	private IDisplay display;
	//private String state;
	private int scanCount = 0;
	private IBorrowUI ui;
	private EBorrowState state;

	//not needed, using singletons:
	//private BookDAO bookDAO;
	//private MemberDAO memberDAO;
	//private LoanDAO loanDAO;

	//temporary ones until they are confirmed and sent to the daos (I think):
	private List<Book> bookList; //books scanned awaiting confirmation of loan?
	private List<Loan> loanList; //to show pending loans
	private Member borrower; //borrower whose card we scanned?

	private JPanel previous;


	public BorrowUC_CTL(ICardReader reader, IScanner scanner,
			IPrinter printer, IDisplay display)
			//not needed, using singletons:
			//BookDAO bookDAO, LoanDAO loanDAO, MemberDAO memberDAO)
	{
		bookList = new LinkedList<Book>();
		loanList = new LinkedList<Loan>();

		this.display = display;
		this.ui = new BorrowUC_UI(this);
		state = EBorrowState.CREATED;
	}

	public void initialise()
	{
		previous = display.getDisplay();
		display.setDisplay((JPanel) ui, "Borrow UI");
		ui.setState(EBorrowState.INITIALIZED);
	}

	public void close()
	{
		display.setDisplay(previous, "Main Menu");
	}

	@Override
	public void cardSwiped(int memberId)
	{
		Member member = MemberDAO.getInstance().getById(memberId);
		if(member == null)
		{
			//TODO: show error message, member not registered with library
		}
		else
		{
			ui.setState(EBorrowState.SCANNING_BOOKS);
			borrower = member;
			ui.displayMemberDetails(memberId, "fred", "857");
			List<Loan> loans = LoanDAO.getInstance().findLoansByBorrower(borrower);
			ui.displayExistingLoan(buildLoanListDisplay(loans));
			Main.setEnabled(false, true, false, true); //only main borrow panel and book scanner enabled
		}
	}

	@Override
	public void bookScanned(int id)
	{
		Book book = BookDAO.getInstance().getById(id);
		if(book == null)
		{
			//TODO: book doesn't exist, do something here
		}
		else if(LoanDAO.getInstance().getLoanByBook(book) == null) //not already on loan
		{
			if(book.getState() == EBookState.ACCEPTABLE) //exists, not on loan, in good enough condition to borrow
			{
				bookList.add(book);
				ui.displayScannedBookDetails(bookList.get(bookList.size()-1).toString());

				Calendar cal = Calendar.getInstance();
				Date now = cal.getTime();
				loanList.add(new Loan(borrower, book, now));
				ui.displayPendingLoan(buildLoanListDisplay(loanList));
			}
			else
			{
				//TODO: error message because book is damaged or disposed
			}
		}
		else
		{
			//TODO: error message because book is on loan
		}
	}


	private void setState(EBorrowState state)
	{
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public void cancelled()
	{
		close();
	}

	@Override
	public void scansCompleted()
	{
		ui.setState(EBorrowState.CONFIRMING_LOANS);
		ui.displayConfirmingLoan(buildLoanListDisplay(loanList));
		Main.setEnabled(false, false, false, true); //only main borrow panel enabled
	}

	@Override
	public void loansConfirmed()
	{
		//TODO: also change borrow panel state
		LoanDAO.getInstance().add(loanList);
	}

	@Override
	public void loansRejected()
	{
		throw new RuntimeException("Not implemented yet");
	}

	private String buildLoanListDisplay(List<Loan> loans)
	{
		StringBuilder bld = new StringBuilder();
		for (Loan loan : loans)
		{
			if (bld.length() > 0) bld.append("\n\n");
			bld.append(loan.toString());
		}
		return bld.toString();
	}
}
