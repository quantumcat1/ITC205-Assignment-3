package library.daos;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import library.entities.Book;
import library.entities.Loan;
import library.entities.Member;
//TODO: make sure that a member can't get more than Member.MAX_LOANS loans
public class LoanDAO extends DAO <Loan>
{
	private static LoanDAO instance = null;

	public static LoanDAO getInstance()
	{
		if(instance == null)
		{
			instance = new LoanDAO();
		}
		return instance;
	}


	public List<Loan> getLoanByBook(Book book)
	{
		List<Loan> list = new LinkedList<Loan>();
		for(Loan loan : database.values())
		{
			if(loan.getBook().getId() == book.getId())
			{
				list.add(loan);
			}
		}
		return list;
	}

	public List<Loan> findLoansByBorrower(Member borrower)
	{
		List<Loan> list = new LinkedList<Loan>();
		for(Loan loan : database.values())
		{
			if(loan.getMember().getId() == borrower.getId())
			{
				list.add(loan);
			}
		}
		return list;
	}

	public List<Loan> findLoansByBookTitle(String title)
	{
		List<Loan> list = new LinkedList<Loan>();
		for(Loan loan : database.values())
		{
			if(loan.getBook().getTitle() == title)
			{
				list.add(loan);
			}
		}
		return list;
	}

	public List<Loan> findOverDueLoans()
	{
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		List<Loan> list = new LinkedList<Loan>();
		for(Loan loan : database.values())
		{
			if(loan.checkOverDue(now))
			{
				list.add(loan);
			}
		}
		return list;
	}

}
