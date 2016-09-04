package library.entities;

import java.util.Date;

import library.entities.Book;
import library.entities.Member;
import library.interfaces.entities.IEntity;

//TODO: implement this class

public class Loan implements IEntity
{
	public static final int LOAN_PERIOD = 14;
	private Member member;
	private Book book;
	private int id;
	private Date borrowDate;

	//TODO: implement a loan period info somewhere
	public boolean checkOverDue(Date currentDate)
	{
		return false;
	}

	public Member getMember()
	{
		return member;
	}

	public Book getBook()
	{
		return book;
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
