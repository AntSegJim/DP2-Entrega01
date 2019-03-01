
package repositories;

import java.util.Collection;

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

}
