
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.MailboxRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Mailbox;
import domain.Notification;

@Service
@Transactional
public class MailboxService {

	@Autowired
	private MailboxRepository	mailBoxRepository;

	@Autowired
	private ActorService		actorService;


	public Mailbox create() {
		final Mailbox mailbox = new Mailbox();

		mailbox.setActor(new Actor());
		mailbox.setNotifications(new HashSet<Notification>());

		return mailbox;
	}

	public Collection<Mailbox> findAll() {
		return this.mailBoxRepository.findAll();
	}

	public Mailbox findOne(final Integer id) {
		return this.mailBoxRepository.findOne(id);
	}

	public Mailbox save(final Mailbox m) {
		final Mailbox res;

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		m.setActor(a);

		res = this.mailBoxRepository.save(m);

		return res;
	}

	public void createMailBoxForNewUser(final Actor a) {
		final Mailbox mb1 = new Mailbox();
		mb1.setActor(a);
		mb1.setNotifications(new HashSet<Notification>());
		this.mailBoxRepository.save(mb1);

	}

	public Mailbox getMailboxByActor(final Integer id) {
		return this.mailBoxRepository.getMailboxByActor(id);
	}

}
