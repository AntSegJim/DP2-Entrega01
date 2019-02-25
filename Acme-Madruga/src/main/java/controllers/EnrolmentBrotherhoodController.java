
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.EnrolmentService;
import domain.Actor;
import domain.Enrolment;

@Controller
@RequestMapping("/enrolment")
public class EnrolmentBrotherhoodController {

	@Autowired
	private EnrolmentService	enrolmentService;

	@Autowired
	private ActorService		actorService;


	@RequestMapping(value = "/brotherhood/list", method = RequestMethod.GET)
	public ModelAndView listBrotherhood() {
		final ModelAndView result;
		final Collection<Enrolment> enrolments;

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		enrolments = this.enrolmentService.enrolmentByBrotherhood(a.getId());
		Assert.notNull(enrolments);

		result = new ModelAndView("enrolment/list");
		result.addObject("enrolments", enrolments);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
		return result;

	}
}
