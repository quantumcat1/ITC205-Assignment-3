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
	private static CardReader reader;
	private static Scanner scanner;
	private static Printer printer;
	private static Display display;
	public static BorrowUC_CTL ctl;

	public Main()
	{
		reader = new CardReader();
		scanner = new Scanner();
		printer = new Printer();
		display = new Display();

		setupTestData();
	}

	//public static void setEnabled (boolean bReader, boolean bScanner, boolean bPrinter, boolean bDisplay)
	public static boolean setEnabled (boolean bReader, boolean bScanner, boolean bPrinter, boolean bDisplay)
	{
		reader.setEnabled(bReader);
		scanner.setEnabled(bScanner);
		printer.setEnabled(bPrinter);
		display.setEnabled(bDisplay);
		return true;
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
		//BorrowUC_CTL ctl = new BorrowUC_CTL(reader, scanner, printer, display);
				// null, null, null);
		//ctl = new BorrowUC_CTL(reader, scanner, printer, display);
		ctl = new BorrowUC_CTL(display);
        javax.swing.SwingUtilities.invokeLater
        (
	        new Runnable()
	        {
	            public void run()
	            {
	            	ctl.initialise();
	            	setEnabled(true, false, false, true);
	            }
	        }
        );
	}


	public static void setupTestData()
	{
        Book[] bookArr = {new Book("author1", "title1", "callNo1"),
		        		new Book("author1", "title2", "callNo2"),
		        		new Book("author1", "title3", "callNo3"),
		        		new Book("author1", "title4", "callNo4"),
		        		new Book("author2", "title5", "callNo5"),
		        		new Book("author2", "title6", "callNo6"),
		        		new Book("author2", "title7", "callNo7"),
		        		new Book("author2", "title8", "callNo8"),
		        		new Book("author3", "title9", "callNo9"),
		        		new Book("author3", "title10", "callNo10"),
		        		new Book("author4", "title11", "callNo11"),
		        		new Book("author4", "title12", "callNo12"),
		        		new Book("author5", "title13", "callNo13"),
		        		new Book("author5", "title14", "callNo14"),
		        		new Book("author5", "title15", "callNo15")};

        BookDAO.getInstance().add(bookArr);

		Member[] memberArr = {new Member("fName0", "lName0", "0001", "email0"),
							new Member("fName1", "lName1", "0002", "email1"),
							new Member("fName2", "lName2", "0003", "email2"),
							new Member("fName3", "lName3", "0004", "email3"),
							new Member("fName4", "lName4", "0005", "email4"),
							new Member("fName5", "lName5", "0006", "email5")};

		MemberDAO.getInstance().add(memberArr);

		Calendar cal = Calendar.getInstance(); //defaults to now
		Date now = cal.getTime();
		//make the borrowDate be so long ago that the books are now due (loan period + 1 days ago):
		cal.add(Calendar.DATE, -1*(Loan.LOAN_PERIOD + 1));
		Date nowOverDue = cal.getTime();


		//create a member with overdue loans
		for (int i=0; i<2; i++)
		{
			Loan loan = new Loan(memberArr[1], bookArr[i], nowOverDue);
			LoanDAO.getInstance().add(loan);
		}

		//create a member with maxed out unpaid fines
		memberArr[2].addFine(Member.MAX_FINE);

		//create a member with maxed out loans
		//TODO: make sure too many loans can't be added.
		for (int i=2; i<7; i++)
		{
			Loan loan = new Loan(memberArr[3], bookArr[i], now);
			LoanDAO.getInstance().add(loan);
		}

		//a member with a fine, but not over the limit
		memberArr[4].addFine(50);

		//a member with a couple of loans but not over the limit
		for (int i=7; i<9; i++)
		{
			LoanDAO.getInstance().add(new Loan(memberArr[5], bookArr[i], now));
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
            	Main.display.setDisplay(new MainPanel(main), "Main Menu");
            	//main.display.setDisplay(MainPanel.getInstance(main), "Main Menu");
                main.showGUI();
            }
        });
	}


}
