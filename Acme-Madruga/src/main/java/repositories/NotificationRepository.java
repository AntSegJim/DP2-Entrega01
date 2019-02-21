
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
