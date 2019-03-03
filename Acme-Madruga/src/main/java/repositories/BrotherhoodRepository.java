
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;

@Repository
public interface BrotherhoodRepository extends JpaRepository<Brotherhood, Integer> {

	@Query("select b from Brotherhood b where b.userAccount.id = ?1")
	public Brotherhood brotherhoodUserAccount(Integer id);

	@Query("select e.brotherhood from Enrolment e where e.member.id = ?1 and e.status = 1 and e.isOut = 0")
	public Collection<Brotherhood> getBrotherhoodsByMember(Integer memberId);

	@Query("select e.brotherhood from Enrolment e where e.member.id = ?1 and e.status=1")
	public Collection<Brotherhood> getBrotherhoodsbelongsByMember(Integer memberId);

	@Query("select e.brotherhood from Enrolment e where e.member.id = ?1 and e.status=2 and e.endMoment!=null")
	public Collection<Brotherhood> getBrotherhoodsbelongedByMember(Integer memberId);

	//DASHBOARD
	@Query("select avg(1.0*(select count(e.member) from Enrolment e where e.status=1 and e.brotherhood.id = b.id)), min(1.0*(select count(e.member) from Enrolment e where e.status=1 and e.brotherhood.id = b.id)), max(1.0*(select count(e.member) from Enrolment e where e.status=1 and e.brotherhood.id = b.id)), sqrt(1.0*sum(1.0*(select count(e.member) from Enrolment e where e.status=1 and e.brotherhood.id = b.id) * (select count(e.member) from Enrolment e where e.status=1 and e.brotherhood.id = b.id)) / count(b) - avg(1.0*(select count(e.member) from Enrolment e where e.status=1 and e.brotherhood.id = b.id)) * avg(1.0*(select count(e.member) from Enrolment e where e.status=1 and e.brotherhood.id = b.id))) from Brotherhood b")
	public List<Object[]> getMaxMinAvgDesvMembersBrotherhood();

}
