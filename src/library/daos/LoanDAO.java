package library.daos;

import java.util.Date;
import java.util.List;

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

	//TODO: find out what the purpose of "commit" is and whether it's important
	public void commitLoan(Loan loan)
	{
	}


	public Loan getLoanByBook(Book book) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Loan> findLoansByBorrower(Member borrower)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public List<Loan> findLoansByBookTitle(String title)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void updateOverDueStatus(Date currentDate)
	{
		// TODO Auto-generated method stub
	}

	public List<Loan> findOverDueLoans()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
