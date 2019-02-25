
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CustomizableSystem;

@Repository
public interface CustomizableSystemRepository extends JpaRepository<CustomizableSystem, Integer> {

	@Query("select c.messageWelcomePage from CustomizableSystem c")
	public String getWelcomeMessage();

	@Query("select c.spanishMessageWelcomePage from CustomizableSystem c")
	public String getSpanishWelcomeMessage();

}
