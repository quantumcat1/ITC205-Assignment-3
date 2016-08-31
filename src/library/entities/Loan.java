package library.entities;

import java.util.Date;

import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

//TODO: implement this class

public class Loan implements ILoan
{
	IMember borrower;
	IBook book;
	int loanId;

	public void commit(int id)
	{

	}

	public void complete()
	{

	}

	public boolean isOverDue()
	{
		return false;
	}

	public boolean checkOverDue(Date currentDate)
	{
		return false;
	}

	public IMember getBorrower()
	{
		return null;
	}

	public IBook getBook()
	{
		return null;
	}

	public int getLoanId()
	{
		return 0;
	}
}
