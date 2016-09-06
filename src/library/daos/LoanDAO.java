package library.daos;

import java.util.Date;
import java.util.List;

import library.entities.Book;
import library.entities.Loan;
import library.entities.Member;
import library.interfaces.daos.IDAO;

public class LoanDAO implements IDAO<Loan>
{
	public Loan add(Loan loan)
	{
		return null;
	}

	//TODO: find out what the purpose of "commit" is and whether it's important
	public void commitLoan(Loan loan)
	{
	}

	@Override
	public Loan getById(int id)
	{
		return null;
	}

	public Loan getLoanByBook(Book book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Loan> list()
	{
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
