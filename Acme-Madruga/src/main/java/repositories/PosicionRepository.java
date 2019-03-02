
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Posicion;

public interface PosicionRepository extends JpaRepository<Posicion, Integer> {

	@Query("select p from Posicion p where p.idioma=?1")
	public Collection<Posicion> getPositions(String language);

	//PARA DELETE
	@Query("select p.position.name from Enrolment p")
	public Collection<String> getUsedNames();

	//HISTOGRAMA
	@Query("select count(e) from Enrolment e where e.status=1")
	public Double countTotal();

	@Query("select count(e) from Enrolment e where e.status=1 and (e.position.name='Presidente' or e.position.name='President')")
	public Double countPresident();

	@Query("select count(e) from Enrolment e where e.status=1 and (e.position.name='Vice-President' or e.position.name='Vicepresidente')")
	public Double countVicePresident();

	@Query("select count(e) from Enrolment e where e.status=1 and (e.position.name='Secretario' or e.position.name='Secretary')")
	public Double countSecretaty();

	@Query("select count(e) from Enrolment e where e.status=1 and (e.position.name='Tesorero' or e.position.name='Treasurer')")
	public Double countTreasurer();

	@Query("select count(e) from Enrolment e where e.status=1 and (e.position.name='Historiador' or e.position.name='Historian')")
	public Double countHistorian();

	@Query("select count(e) from Enrolment e where e.status=1 and (e.position.name='Promotor' or e.position.name='Fundraiser')")
	public Double countFundraiser();

	@Query("select count(e) from Enrolment e where e.status=1 and (e.position.name='Vocal' or e.position.name='Officer')")
	public Double countOfficer();

}
