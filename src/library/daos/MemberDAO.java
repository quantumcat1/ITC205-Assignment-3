package library.daos;

import java.util.LinkedList;
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
		List<Member> list = new LinkedList<Member>();
		for(Member member : database.values())
		{
			if(member.getLastName() == lastName)
			{
				list.add(member);
			}
		}
		return list;
	}

	public List<Member> findMembersByEmailAddress(String emailAddress)
	{
		List<Member> list = new LinkedList<Member>();
		for(Member member : database.values())
		{
			if(member.getEmailAddress() == emailAddress)
			{
				list.add(member);
			}
		}
		return list;
	}

	public Member findMembersByNames(String firstName, String lastName)
	{
		for(Member member : database.values())
		{
			if(member.getLastName() == lastName && member.getFirstName() == firstName)
			{
				return member;
			}
		}
		return null;
	}
}
