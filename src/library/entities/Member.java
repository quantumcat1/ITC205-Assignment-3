package library.entities;

import java.util.List;

import library.enums.EMemberState;
import library.interfaces.entities.IEntity;

//TODO: finish implementing this class

public class Member implements IEntity
{
	private int id;
	private String firstName;
	private String lastName;
	private float fineAmount;
	private String phoneNumber;
	private String emailAddress;
	private EMemberState state;

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
		//TODO: implement a max fine info somewhere
		return false;
	}

	public float getFineAmount()
	{
		return fineAmount;
	}

	//TODO: finish this after max fine amount info is added somewhere
	public boolean addFine(float fine)
	{
		float newAmount = fineAmount + fine;
		//check if exceeds fine limit and return false if it does, otherwise:
		fineAmount = newAmount;
		return true;
	}

	public boolean payFine(float payment)
	{
		if(payment <= fineAmount)
		{
			fineAmount -= payment;
			return true;
		}
		return false;
	}

	public EMemberState getState()
	{
		return state;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public String  getEmailAddress()
	{
		return emailAddress;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}
