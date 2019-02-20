
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NotificationRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Notification;

@Service
@Transactional
public class NotificationService {

	@Autowired
	private NotificationRepository	notificationRepository;

	@Autowired
	private ActorService			actorService;


	public Notification create() {

		final Notification notification = new Notification();
		notification.setAdministrator(new Administrator());
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

		final UserAccount user = LoginService.getPrincipal();
		final Administrator a = (Administrator) this.actorService.getActorByUserAccount(user.getId());
		n.setAdministrator(a);

		Assert.notNull(n.getAdministrator() != null, "Debe tener un administrador asignado");
		Assert.notNull(n.getBody() != null && n.getBody() != "", "No debe tener un cuerpo vacio");
		Assert.notNull(n.getSubject() != null && n.getSubject() != "", "No debe tener un subject vacio");

		res = this.notificationRepository.save(n);

		return res;

	}
}
