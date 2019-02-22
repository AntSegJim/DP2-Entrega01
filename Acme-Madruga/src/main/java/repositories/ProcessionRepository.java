
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Member;
import domain.Procession;

public interface ProcessionRepository extends JpaRepository<Procession, Integer> {

	//Sepodría mejorar poniendo where p.ticker=?1 y el parametro String ticker
	@Query("select p.ticker from Procession p")
	public Collection<String> getAllTickers();

	@Query("select b from Brotherhood b where b.user_account = ?1")
	public Member getBrotherhoodByUserAccount(int userAccountId);
}
