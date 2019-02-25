
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView editEnrolment(@RequestParam final Integer idEnrolment) {
		final ModelAndView result;
		final Enrolment enrolment;

		enrolment = this.enrolmentService.findOne(idEnrolment);
		Assert.notNull(enrolment);

		result = new ModelAndView("enrolment/edit");
		result.addObject("enrolment", enrolment);
		return result;

	}

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editEnrolment(@Valid final Enrolment enrolment, final BindingResult binding) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				this.enrolmentService.save(enrolment);
				result = new ModelAndView("redirect:list.do");
			} else {

				result = new ModelAndView("enrolment/edit");
				result.addObject("enrolment", enrolment);
			}

		} catch (final Exception e) {

			result = new ModelAndView("enrolment/edit");
			result.addObject("enrolment", enrolment);
			result.addObject("exception", e);
		}

		return result;
	}
}
