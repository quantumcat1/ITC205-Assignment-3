package library;

import library.hardware.CardReader;
import library.hardware.Display;
import library.hardware.Printer;
import library.hardware.Scanner;

import java.util.Calendar;
import java.util.Date;

import library.interfaces.IMainListener;
import library.daos.BookDAO;
import library.daos.LoanDAO;
import library.daos.MemberDAO;
import library.daos.BookDAO;
import library.entities.Book;
import library.entities.Loan;
import library.entities.Member;
import library.panels.MainPanel;

public class Main implements IMainListener
{
	private CardReader reader;
	private Scanner scanner;
	private Printer printer;
	private Display display;

	public Main()
	{
		reader = new CardReader();
		scanner = new Scanner();
		printer = new Printer();
		display = new Display();

		setupTestData();
	}


	public void showGUI()
	{
		reader.setVisible(true);
		scanner.setVisible(true);
		printer.setVisible(true);
		display.setVisible(true);
	}


	@Override
	public void borrowBooks()
	{
		BorrowUC_CTL ctl = new BorrowUC_CTL(reader, scanner, printer, display);
				// null, null, null);
        javax.swing.SwingUtilities.invokeLater
        (
	        new Runnable()
	        {
	            public void run()
	            {
	            	reader.setEnabled(true);
	            	ctl.initialise();
	            }
	        }
        );
	}


	private void setupTestData()
	{
        Book[] book = new Book[15];
		Member[] member = new Member[6];

		book[0]  = BookDAO.getInstance().add(new Book("author1", "title1", "callNo1"));
		book[1]  = BookDAO.getInstance().add(new Book("author1", "title2", "callNo2"));
		book[2]  = BookDAO.getInstance().add(new Book("author1", "title3", "callNo3"));
		book[3]  = BookDAO.getInstance().add(new Book("author1", "title4", "callNo4"));
		book[4]  = BookDAO.getInstance().add(new Book("author2", "title5", "callNo5"));
		book[5]  = BookDAO.getInstance().add(new Book("author2", "title6", "callNo6"));
		book[6]  = BookDAO.getInstance().add(new Book("author2", "title7", "callNo7"));
		book[7]  = BookDAO.getInstance().add(new Book("author2", "title8", "callNo8"));
		book[8]  = BookDAO.getInstance().add(new Book("author3", "title9", "callNo9"));
		book[9]  = BookDAO.getInstance().add(new Book("author3", "title10", "callNo10"));
		book[10] = BookDAO.getInstance().add(new Book("author4", "title11", "callNo11"));
		book[11] = BookDAO.getInstance().add(new Book("author4", "title12", "callNo12"));
		book[12] = BookDAO.getInstance().add(new Book("author5", "title13", "callNo13"));
		book[13] = BookDAO.getInstance().add(new Book("author5", "title14", "callNo14"));
		book[14] = BookDAO.getInstance().add(new Book("author5", "title15", "callNo15"));

		member[0] = MemberDAO.getInstance().add(new Member("fName0", "lName0", "0001", "email0"));
		member[1] = MemberDAO.getInstance().add(new Member("fName1", "lName1", "0002", "email1"));
		member[2] = MemberDAO.getInstance().add(new Member("fName2", "lName2", "0003", "email2"));
		member[3] = MemberDAO.getInstance().add(new Member("fName3", "lName3", "0004", "email3"));
		member[4] = MemberDAO.getInstance().add(new Member("fName4", "lName4", "0005", "email4"));
		member[5] = MemberDAO.getInstance().add(new Member("fName5", "lName5", "0006", "email5"));

		Calendar cal = Calendar.getInstance(); //defaults to now
		Date now = cal.getTime();
		//make the borrowDate be so long ago that the books are now due:
		cal.add(Calendar.DATE, -1*(Loan.LOAN_PERIOD + 1));
		Date nowOverDue = cal.getTime();


		//create a member with overdue loans
		for (int i=0; i<2; i++)
		{
			Loan loan =  LoanDAO.getInstance().add(new Loan(member[1], book[i], nowOverDue));
		}

		//create a member with maxed out unpaid fines
		member[2].addFine(Member.MAX_FINE + 1);

		//create a member with maxed out loans
		//TODO: make sure too many loans can't be added.
		for (int i=2; i<7; i++)
		{
			Loan loan = LoanDAO.getInstance().add(new Loan(member[3], book[i], now));
		}

		//a member with a fine, but not over the limit
		member[4].addFine(50);

		//a member with a couple of loans but not over the limit
		for (int i=7; i<9; i++) {
			Loan loan = LoanDAO.getInstance().add(new Loan(member[5], book[i], now));
		}
	}


	public static void main(String[] args)
	{

        // start the GUI
		Main main = new Main();
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
            	main.display.setDisplay(new MainPanel(main), "Main Menu");
                main.showGUI();
            }
        });
	}


}
