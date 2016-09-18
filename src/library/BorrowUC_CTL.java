package library;

import java.awt.CardLayout;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import library.panels.borrow.ABorrowPanel;
import library.panels.borrow.ConfirmLoanPanel;
import library.panels.borrow.RestrictedPanel;
import library.panels.borrow.ScanningPanel;
import library.panels.borrow.SwipeCardPanel;

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


	public BorrowUC_CTL(IDisplay display)
	{
		bookList = new LinkedList<Book>();
		loanList = new LinkedList<Loan>();

		this.display = display;
		//this.ui = new BorrowUC_UI(this);
		this.ui = new BorrowUC_UI();
	}

	public Member getBorrower()
	{
		return borrower;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	public List<Loan> getLoanList() {
		return loanList;
	}

	public void setLoanList(List<Loan> loanList) {
		this.loanList = loanList;
	}

	public void setBorrower(Member borrower)
	{
		this.borrower = borrower;
	}

	public IBorrowUI getUi()
	{
		return ui;
	}

	public void setUi(BorrowUC_UI ui)
	{
		this.ui = ui;
	}

	public void initialise()
	{
		previous = display.getDisplay();
		display.setDisplay((JPanel) ui, "Borrow UI");
		//ui.setState(EBorrowState.INITIALIZED);
		ui.setState(EBorrowState.INITIALIZED);
	}

	public void close()
	{
		display.setDisplay(previous, "Main Menu");
	}

	@Override
	public void cardSwiped(int memberId)
	{
		cardSwiped(memberId, false);
	}
	public void cardSwiped(int memberId, boolean bTest)
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
			if(!bTest) //Main static methods cause an unresolveable problem in integration testing
			{
				Main.setEnabled(false, true, false, true); //only main borrow panel and book scanner enabled
			}
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
			ui.displayErrorMessage("Book " + id + " not found");
		}
		else if(LoanDAO.getInstance().getLoanByBook(book) == null) //not already on loan
		{
			ui.setState(EBorrowState.SCANNING_BOOKS); //should already be scanning books, but in testing we're not
			if(book.getState() == EBookState.ACCEPTABLE) //exists, not on loan, in good enough condition to borrow
			{
				bookList.add(book);
				ui.displayScannedBookDetails(bookList.get(bookList.size() - 1).toString());

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
		scansCompleted(false);
	}
	public void scansCompleted(boolean bTest)
	{
		ui.setState(EBorrowState.CONFIRMING_LOANS);
		ui.displayConfirmingLoan(buildLoanListDisplay(loanList));
		if(!bTest) //Main static methods kill integration testing and there is no way to fix it
		{
			Main.setEnabled(false, false, false, true); //only main borrow panel enabled
		}
	}

	@Override
	public void loansConfirmed()
	{
		loansConfirmed(false);
	}
	public void loansConfirmed(boolean bTest)
	{
		//TODO: also change borrow panel state: goes back to beginning, and printer displays the completed loan
		ui.setState(EBorrowState.CREATED);
		LoanDAO.getInstance().add(loanList);
		if(!bTest) //static methods kill integration testing
		{
			Main.setEnabled(false, false, false, true); //only main borrow panel enabled
		}
	}

	@Override
	public void loansRejected()
	{
		loansRejected(false);
	}
	public void loansRejected(boolean bTest)
	{
		ui.setState(EBorrowState.SCANNING_BOOKS);
		loanList.clear();
		ui.displayPendingLoan("");
		ui.displayScannedBookDetails("");
		if(!bTest)//static methods kill integration testing
		{
			Main.setEnabled(false, true, false, true); //main borrow panel and scan books panel enabled
		}
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







	public class BorrowUC_UI extends JPanel implements IBorrowUI
	{
		private static final long serialVersionUID = 1L;
		private IBorrowUIListener listener;
		private EBorrowState state;
		private Map<EBorrowState,IBorrowUI> panels;


		public BorrowUC_UI()
		{
			this.listener = BorrowUC_CTL.this;
			this.panels = new HashMap<EBorrowState,IBorrowUI>();
			this.setLayout(new CardLayout());
			this.state = EBorrowState.INITIALIZED;

			addPanel(new SwipeCardPanel(listener),   EBorrowState.INITIALIZED);
			addPanel(new ScanningPanel(listener),    EBorrowState.SCANNING_BOOKS);
			addPanel(new RestrictedPanel(listener),  EBorrowState.BORROWING_RESTRICTED);
			addPanel(new ConfirmLoanPanel(listener), EBorrowState.CONFIRMING_LOANS);
			//addPanel(new CancelledPanel(),           EBorrowState.CANCELLED);
			//addPanel(new CompletedPanel(),           EBorrowState.COMPLETED);
		}

		private void addPanel(ABorrowPanel panel, EBorrowState state)
		{
	        this.panels.put(state, panel);
	        this.add(panel, state.toString());
	 	}

		public void setState(EBorrowState state)
		{
			CardLayout cl = (CardLayout) (this.getLayout());

			switch (state)
			{
			case INITIALIZED:
				cl.show(this, state.toString());
				break;

			case SCANNING_BOOKS:
				cl.show(this, state.toString());
				break;

			case BORROWING_RESTRICTED:
				cl.show(this, state.toString());
				break;

			case CONFIRMING_LOANS:
				cl.show(this, state.toString());
				break;

			case CREATED:
				cl.show(this, state.toString());

	 		case COMPLETED:
				break;

			case CANCELLED:
				break;

			default:
				throw new RuntimeException("Unknown state");
			}
			this.state = state;
		}

		public EBorrowState getState()
		{
			return state;
		}

		public IBorrowUI get()
		{
			return panels.get(state);
		}

		@Override
		public void displayMemberDetails(int memberID, String memberName, String memberPhone)
		{
			get().displayMemberDetails(memberID, memberName, memberPhone);

		}

		@Override
		public void displayExistingLoan(String loanDetails)
		{
			get().displayExistingLoan(loanDetails);

		}

		@Override
		public void displayOverDueMessage()
		{
			get().displayOverDueMessage();

		}

		@Override
		public void displayAtLoanLimitMessage()
		{
			get().displayAtLoanLimitMessage();

		}

		@Override
		public void displayOutstandingFineMessage(float amountOwing)
		{
			get().displayOutstandingFineMessage(amountOwing);

		}

		@Override
		public void displayOverFineLimitMessage(float amountOwing)
		{
			get().displayOverFineLimitMessage(amountOwing);

		}

		@Override
		public void displayScannedBookDetails(String bookDetails)
		{
			get().displayScannedBookDetails(bookDetails);

		}

		@Override
		public void displayPendingLoan(String loanDetails)
		{
			get().displayPendingLoan(loanDetails);

		}

		@Override
		public void displayConfirmingLoan(String loanDetails)
		{
			get().displayConfirmingLoan(loanDetails);

		}

		@Override
		public void displayErrorMessage(String errorMesg)
		{
			get().displayErrorMessage(errorMesg);

		}
	}
}
