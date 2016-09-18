package library;

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
import library.enums.EMemberState;
import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.ICardReaderListener;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;
import library.interfaces.hardware.IScannerListener;

public class BorrowUC_CTL implements ICardReaderListener,
									 IScannerListener,
									 IBorrowUIListener
{
	private IDisplay display;
	private IBorrowUI ui;

	//lists for temporary data until confirmed:
	private List<Book> bookList; //books scanned
	private List<Loan> loanList; //pending loans awaiting confirmation
	private Member borrower; //borrower whose card was scanned

	private JPanel previous;


	public BorrowUC_CTL(ICardReader reader, IScanner scanner,
			IPrinter printer, IDisplay display)
	{
		bookList = new LinkedList<Book>();
		loanList = new LinkedList<Loan>();

		this.display = display;
		this.ui = new BorrowUC_UI(this);
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
			ui.displayErrorMessage("Member ID " + memberId + " not found");
		}
		else
		{
			//will do this whether restricted or not:
			borrower = member;
			Main.setEnabled(false, true, false, true); //only main borrow panel and book scanner enabled
			EMemberState state = Member.checkRestricted(member);
			if(state != EMemberState.NOT_RESTRICTED)
			{
				ui.setState(EBorrowState.BORROWING_RESTRICTED);
				if(state == EMemberState.RESTRICTED_FINES)
				{
					ui.displayOverFineLimitMessage(member.getFineAmount());
				}
				else if (state == EMemberState.RESTRICTED_LOANS)
				{
					ui.displayAtLoanLimitMessage();
				}
				ui.displayErrorMessage("Member " + memberId + " cannot borrow at this time.");
			}
			else
			{
				ui.setState(EBorrowState.SCANNING_BOOKS);
			}
			ui.displayMemberDetails(borrower.getId(), borrower.fullName(), borrower.getPhoneNumber());
			List<Loan> loans = LoanDAO.getInstance().findLoansByBorrower(borrower);
			ui.displayExistingLoan(buildLoanListDisplay(loans));
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
		//TODO: also change borrow panel state: goes back to beginning, and printer displays the completed loan
		ui.setState(EBorrowState.INITIALIZED);
		LoanDAO.getInstance().add(loanList);
		Main.setEnabled(false, false, false, true); //only main borrow panel enabled
	}

	@Override
	public void loansRejected()
	{
		ui.setState(EBorrowState.SCANNING_BOOKS);
		loanList.clear();
		ui.displayPendingLoan("");
		ui.displayScannedBookDetails("");
		Main.setEnabled(false, true, false, true); //main borrow panel and scan books panel enabled
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
