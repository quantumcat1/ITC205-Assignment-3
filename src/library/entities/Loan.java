package library.entities;

import java.util.Calendar;
import java.util.Date;

import library.entities.Book;
import library.entities.Member;


public class Loan extends Entity
{
	public static final int LOAN_PERIOD = 14;
	private Member member;
	private Book book;
	private int id;
	private Date borrowDate;
	private static int counter = 0;

	public Loan(Member member, Book book, Date borrowDate)
	{
		super(counter++);
		this.member = member;
		this.book = book;
		this.borrowDate = borrowDate;
	}

	@Override
	public String toString()
	{
		return "Loan ID: " + String.valueOf(id) + "\nAuthor: " + book.getAuthor() + "\nTitle: " + book.getTitle() + "\nBorrower: " + member.fullName() + "\nBorrowed: " + borrowDate + "\nDue Date: " + dueDate();
	}

	public boolean checkOverDue(Date currentDate)
	{
		Date dueDate = dueDate();
		if(currentDate.after(dueDate))
		{
			return true;
		}
		return false;
	}

	public Date dueDate()
	{
		Calendar c = Calendar.getInstance();
		c.setTime(borrowDate);
		c.add(Calendar.DATE, LOAN_PERIOD);
		return c.getTime();
	}

	public Member getMember()
	{
		return member;
	}

	public Book getBook()
	{
		return book;
	}
}
