
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

}
