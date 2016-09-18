package library.entities;

import java.text.SimpleDateFormat;
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

	public static void resetCounter()
	{
		counter = 0;
	}

	@Override
	public String toString()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String formattedBorrowDate = sdf.format(borrowDate);
		String formattedDueDate = sdf.format(dueDate());
		return "Loan ID: " + String.valueOf(id) + "\nAuthor: " + book.getAuthor() + "\nTitle: " + book.getTitle() + "\nBorrower: " + member.fullName() + "\nBorrowed: " + formattedBorrowDate + "\nDue Date: " + formattedDueDate;
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
		Calendar cal = Calendar.getInstance();
		cal.setTime(borrowDate);
		cal.add(Calendar.DATE, LOAN_PERIOD);
		return cal.getTime();
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
