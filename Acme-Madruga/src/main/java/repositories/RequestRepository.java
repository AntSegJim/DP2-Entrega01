
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Member;
import domain.Request;

public interface RequestRepository extends JpaRepository<Request, Integer> {

	//Pasar a memberRepository y en servicio
	@Query("select m from Member where m.user_account = ?1")
	public Member getMemberByUserAccount(int userAccountId);
}
