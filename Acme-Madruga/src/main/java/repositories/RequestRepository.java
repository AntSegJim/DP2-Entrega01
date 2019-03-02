
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Request;

public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select r from Request r where r.row = ?1 and r.columna = ?2 and (r.status = 0)")
	public Request getRequestWithThisRowAndColumn(Integer row, Integer column);

	@Query("select r from Request r where r.member.id = ?1 ORDER BY  r.status")
	public Collection<Request> getAllMyRequest(int memberId);
}
