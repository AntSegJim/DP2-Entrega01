
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Paso;

@Repository
public interface FloatRepository extends JpaRepository<Paso, Integer> {

}
