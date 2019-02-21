
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NotificationRepository;
import domain.Actor;
import domain.Mailbox;
import domain.Notification;

@Service
@Transactional
public class NotificationService {

	@Autowired
	private NotificationRepository	notificationRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private MailboxService			mailboxService;


	public Notification create() {

		final Notification notification = new Notification();
		notification.setSubject("");
		notification.setBody("");

		return notification;

	}

	public Collection<Notification> findAll() {
		return this.notificationRepository.findAll();
	}

	public Notification findOne(final Integer id) {
		return this.notificationRepository.findOne(id);
	}

	public Notification save(final Notification n) {
		Notification res;

		Assert.notNull(n.getBody() != null && n.getBody() != "", "No debe tener un cuerpo vacio");
		Assert.notNull(n.getSubject() != null && n.getSubject() != "", "No debe tener un subject vacio");

		res = this.notificationRepository.save(n);

		return res;

	}

	//Enviar la notificacion
	public void sendNotification(final Notification n) {
		final List<Actor> actors = this.actorService.findAll();

		for (int i = 0; i < actors.size(); i++) {
			final Mailbox m = this.mailboxService.getMailboxByActor(actors.get(i).getId());
			m.getNotifications().add(n);
		}
	}

	public Collection<Notification> notificationByMailBox(final Integer id) {
		return this.notificationRepository.notificationsById(id);
	}
}
