package library.daos;

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

	public List<Loan> findOverDueLoans()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
