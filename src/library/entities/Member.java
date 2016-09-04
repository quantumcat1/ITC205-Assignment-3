package library.entities;

import java.util.List;

import library.interfaces.entities.EMemberState;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

//TODO: implement this class

public class Member implements IMember
{
	int memberId;
	String firstName;
	String lastName;
	float fineAmount;
	String contactPhone;
	String emailAddress;

	public boolean hasOverDueLoans()
	{
		return false;
	}

	public boolean hasReachedLoanLimit()
	{
		return false;
	}

	public boolean hasFinesPayable()
	{
		if(fineAmount > 0)
		{
			return true;
		}
		return false;
	}

	public boolean hasReachedFineLimit()
	{
		return false;
	}

	public float   getFineAmount()
	{
		return fineAmount;
	}

	//TODO: this will return void after interfaces are gone
	public void addFine(float fine)
	{
		float newAmount = fineAmount + fine;
		//check if exceeds fine limit and return false if it does, otherwise:
		fineAmount = newAmount;
		//return true;
	}

	public void payFine(float payment)
	{

	}

	public void addLoan(ILoan loan)
	{

	}

	public List<ILoan> getLoans()
	{
		return null;
	}

	public void    removeLoan(ILoan loan)
	{

	}

	public EMemberState   getState()
	{
		return null;
	}

	public String  getFirstName()
	{
		return "";
	}

	public String  getLastName()
	{
		return "";
	}

	public String  getContactPhone()
	{
		return "";
	}

	public String  getEmailAddress()
	{
		return "";
	}

	public int     getMemberId()
	{
		return 0;
	}
}
