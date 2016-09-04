package library.interfaces.daos;

import java.util.List;

import library.entities.Member;

public interface IMemberDAO
{
		public Member addMember(String firstName, String lastName, String ContactPhone, String emailAddress);

		public Member getMemberByID(int id);

		public List<Member> listMembers();

		public List<Member> findMembersByLastName(String lastName);

		public List<Member> findMembersByEmailAddress(String emailAddress);

		public List<Member> findMembersByNames(String firstName, String lastName);
}
