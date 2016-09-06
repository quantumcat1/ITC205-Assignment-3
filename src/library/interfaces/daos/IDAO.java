package library.interfaces.daos;

import java.util.List;

public interface IDAO <T>
{
	T add(T t);
	T getById(int id);
	List<T> list();
}
