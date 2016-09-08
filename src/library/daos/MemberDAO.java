package library.daos;

import java.util.List;

import library.entities.Member;

public class MemberDAO extends DAO <Member>
{
	private static MemberDAO instance = null;

	public static MemberDAO getInstance()
	{
		if(instance == null)
		{
			instance = new MemberDAO();
		}
		return instance;
	}

	public List<Member> findMembersByLastName(String lastName)
	{
		return null;
	}

	public List<Member> findMembersByEmailAddress(String emailAddress)
	{
		return null;
	}

	public List<Member> findMembersByNames(String firstName, String lastName)
	{
		return null;
	}
}
