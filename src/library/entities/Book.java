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

	public void borrow()
	{
		//will be gone when interface gone
	}
	public void returnBook (boolean damaged)
	{
		//will be gone when interface gone
	}

	public void lose()
	{
		state = EBookState.LOST;
	}

	public void repair()
	{
		//if not damaged, can't repair it
		if(state == EBookState.DAMAGED)
		{
			state = EBookState.ACCEPTABLE;
		}
	}

	public void dispose()
	{
		//can't throw away an acceptable book
		if(state == EBookState.DAMAGED)
		{
			state = EBookState.DISPOSED;
		}
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

	@Override
	public void borrow(ILoan loan) {
		// TODO Auto-generated method stub

	}

	@Override
	public ILoan getLoan() {
		// TODO Auto-generated method stub
		return null;
	}


}
