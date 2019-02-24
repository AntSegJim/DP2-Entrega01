
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Procession;

public interface ProcessionRepository extends JpaRepository<Procession, Integer> {

	//Sepodria mejorar poniendo where p.ticker=?1 y el parametro String ticker
	@Query("select p.ticker from Procession p")
	public Collection<String> getAllTickers();

	@Query("select p.ticker from Procession p")
	public Collection<Procession> getProcessions();

	//Antonio:No borreis este metodo. La de arriba esta mal
	@Query("select p from Procession p where p.brotherhood.id = ?1")
	public Collection<Procession> getAllProcessionsByBrotherhood(int brotherhoodId);
}
