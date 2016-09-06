package library.daos;

import java.util.List;

import library.entities.Member;
import library.interfaces.daos.IDAO;

public class MemberDAO implements IDAO<Member>
{

	@Override
	public Member add(Member member)
	{
		return null;
	}

	@Override
	public Member getById(int id)
	{
		return null;
	}

	@Override
	public List<Member> list()
	{
		return null;
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
