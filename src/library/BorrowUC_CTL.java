package library;

import java.util.ArrayList;
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
	private List<Loan> loanList; //not sure what this is for
	private Member borrower; //borrower whose card we scanned?

	private JPanel previous;


	public BorrowUC_CTL(ICardReader reader, IScanner scanner,
			IPrinter printer, IDisplay display)
			//not needed, using singletons:
			//BookDAO bookDAO, LoanDAO loanDAO, MemberDAO memberDAO)
	{

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
		//display.setDisplay(previous, "Main Menu");
	}

	@Override
	public void cardSwiped(int memberId)
	{
		Member member = MemberDAO.getInstance().getById(memberId);
		if(member == null)
		{
			//TODO: show error message
		}
		else
		{
			borrower = member;
			ui.displayMemberDetails(memberId, "fred", "857");
		}

	}



	@Override
	public void bookScanned(int id)
	{
		Book testBook = BookDAO.getInstance().getById(id);
		if(testBook == null)
		{
			//TODO: book doesn't exist, do something here
		}
		else
		{
			bookList.add(testBook);
		}
		//also need to test that it's available and not damaged etc
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
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public void loansConfirmed()
	{
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public void loansRejected()
	{
		throw new RuntimeException("Not implemented yet");
	}

	private String buildLoanListDisplay(List<Loan> loans)
	{
		StringBuilder bld = new StringBuilder();
		for (Loan ln : loans)
		{
			if (bld.length() > 0) bld.append("\n\n");
			bld.append(ln.toString());
		}
		return bld.toString();
	}
}
