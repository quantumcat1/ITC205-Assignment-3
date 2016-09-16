package library.entities;

import library.enums.EBookState;
import library.entities.Entity;


public class Book extends Entity
{
	private EBookState state;
	private String author;
	private String title;
	private String callNumber;
	static private int counter = 0;

	public Book(String author, String title, String callNumber)
	{
		super(counter++);
		this.author = author;
		this.title = title;
		this.callNumber = callNumber;
		this.state = EBookState.ACCEPTABLE; //assuming if we've just added a book to the library it will be new?
	}
	@Override
	public String toString()
	{
		return "id: " + String.valueOf(id) + "\nAuthor: " + author + "\nTitle: " + title + "\nCall number: " + callNumber;
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
}
