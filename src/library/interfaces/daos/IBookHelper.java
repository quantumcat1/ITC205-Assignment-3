package library.interfaces.daos;

import library.entities.Book;

public interface IBookHelper {

	public Book makeBook(String author, String title, String callNumber, int id);

}
