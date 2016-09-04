package library.interfaces.daos;

import library.entities.Member;

public interface IMemberHelper
{
	public Member makeMember(String firstName, String lastName, String contactPhone, String emailAddress, int id);
}
