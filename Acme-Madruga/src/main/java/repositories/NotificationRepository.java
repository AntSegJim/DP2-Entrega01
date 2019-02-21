
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

	@Query("select m.notifications from Mailbox m where m.id = ?1")
	public Collection<Notification> notificationsById(Integer id);

}
