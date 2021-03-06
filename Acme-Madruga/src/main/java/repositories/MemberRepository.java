
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

	@Query("select m from Member m where m.userAccount.id = ?1")
	public Member getMemberByUserAccount(int userAccountId);

	@Query("select distinct m.member from Enrolment m where m.brotherhood.id=?1 and m.status=1")
	public Collection<Member> getMembersOfBrotherhood(Integer id);

	//DASHBOARD
	@Query("select m.name from Member m where (select count(r) * 0.1 from Request r where r.member.id = m.id) < (select count(c) from Request c where c.status=1 and c.member.id=m.id)")
	public Collection<String> member10Percentage();

}
