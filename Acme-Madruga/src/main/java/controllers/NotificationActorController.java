
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NotificationService;
import domain.Notification;

@Controller
@RequestMapping("/notification/actor")
public class NotificationActorController extends AbstractController {

	@Autowired
	private NotificationService	notificationService;


	public NotificationActorController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Notification> notifications;

		notifications = this.notificationService.findAll();
		Assert.notNull(notifications);

		result = new ModelAndView("notification/list");
		result.addObject("notifications", notifications);
		return result;

	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final Integer notificationId) {
		final ModelAndView result;
		final Notification notification;

		notification = this.notificationService.findOne(notificationId);
		Assert.notNull(notification);

		result = new ModelAndView("notification/show");
		result.addObject("notification", notification);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Notification notification;

		notification = this.notificationService.create();
		Assert.notNull(notification);

		result = new ModelAndView("notification/edit");
		result.addObject("notification", notification);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int notificationId) {
		ModelAndView result;
		final Notification notification;

		notification = this.notificationService.findOne(notificationId);

		result = new ModelAndView("notification/edit");
		result.addObject("notification", notification);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Notification notification, final BindingResult binding) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				this.notificationService.save(notification);
				result = new ModelAndView("redirect:list.do");
			} else {
				result = new ModelAndView("notification/edit");
				result.addObject("notification", notification);
			}
		} catch (final Exception e) {
			result = new ModelAndView("notification/edit");
			result.addObject("exception", e);
			result.addObject("notification", notification);
		}

		return result;
	}

}
