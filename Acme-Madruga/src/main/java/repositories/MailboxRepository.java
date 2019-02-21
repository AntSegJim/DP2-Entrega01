
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Mailbox;

public interface MailboxRepository extends JpaRepository<Mailbox, Integer> {

	@Query("select mb from Mailbox mb where mb.actor.id=?1")
	public Mailbox getMailboxByActor(Integer id);

}
