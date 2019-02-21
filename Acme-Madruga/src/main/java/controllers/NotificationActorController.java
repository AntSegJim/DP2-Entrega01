
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.MailboxService;
import services.NotificationService;
import domain.Actor;
import domain.Mailbox;
import domain.Notification;

@Controller
@RequestMapping("/notification/actor")
public class NotificationActorController extends AbstractController {

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private MailboxService		mailboxService;

	@Autowired
	private ActorService		actorService;


	public NotificationActorController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listNotification() {
		final ModelAndView result;
		final Collection<Notification> notifications;

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		final Mailbox mb = this.mailboxService.getMailboxByActor(a.getId());
		notifications = this.notificationService.notificationByMailBox(mb.getId());
		Assert.notNull(notifications);

		result = new ModelAndView("notification/list");
		result.addObject("notifications", notifications);
		return result;

	}
}
