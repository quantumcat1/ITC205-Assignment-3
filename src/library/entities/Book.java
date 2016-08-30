package library.entities;

import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;


public class Book implements IBook
{
	EBookState state;
	String author;
	String title;
	String callNumber;
	//bookId, not id - otherwise could get confused with the member Id who is borrowing it.
	int bookId;

	public void borrow(ILoan loan)
	{
		//TODO: implement borrow()
	}

	public ILoan getLoan()
	{
		//TODO: implement loan()
		return null;
	}

	public void returnBook(boolean damaged)
	{
		//can't return book unless it was on loan.
		if(state == EBookState.ON_LOAN)
		{
			if(damaged)
			{
				state = EBookState.DAMAGED;
			}
			else
			{
				state = EBookState.AVAILABLE;
			}
		}
		//TODO: add something about changing the loan?
	}

	public void lose()
	{
		state = EBookState.LOST;
	}

	public void repair()
	{
		//should not become available if it is lost, on loan, or disposed of
		if(state == EBookState.DAMAGED)
		{
			state = EBookState.AVAILABLE;
		}
		//TODO: make sure it is not part of a loan
	}

	public void dispose()
	{
		//should not be thrown away if it is available, on loan, or lost.
		if(state == EBookState.DAMAGED)
		{
			state = EBookState.DISPOSED;
		}
		//TODO: make sure it is not part of a loan
	}

	public EBookState getState()
	{
		return state;
	}

	public String getAuthor()
	{
		return author;
	}

	public String getTitle()
	{
		return title;
	}

	public String getCallNumber()
	{
		return callNumber;
	}

	public int getId()
	{
		return bookId;
	}
}
