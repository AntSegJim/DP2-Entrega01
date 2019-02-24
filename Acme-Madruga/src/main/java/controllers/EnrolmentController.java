
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
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.BrotherhoodService;
import services.EnrolmentService;
import services.PositionService;
import domain.Actor;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Posicion;

@Controller
@RequestMapping("/enrolment")
public class EnrolmentController {

	@Autowired
	private EnrolmentService	enrolmentService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/member/list", method = RequestMethod.GET)
	public ModelAndView listEnrolment() {
		final ModelAndView result;
		final Collection<Enrolment> enrolments;

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		enrolments = this.enrolmentService.enrolmentByMember(a.getId());
		Assert.notNull(enrolments);

		result = new ModelAndView("enrolment/list");
		result.addObject("enrolments", enrolments);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
		return result;

	}

	@RequestMapping(value = "/member/create", method = RequestMethod.GET)
	public ModelAndView createEnrolment() {
		final ModelAndView result;
		Collection<Posicion> positions;
		Collection<Brotherhood> brotherhoods;
		final Enrolment enrolment;
		final String language;

		language = LocaleContextHolder.getLocale().getLanguage();
		enrolment = this.enrolmentService.create();
		positions = this.positionService.getPositions(language);
		brotherhoods = this.brotherhoodService.findAll();
		Assert.notNull(enrolment);

		result = new ModelAndView("enrolment/edit");
		result.addObject("enrolment", enrolment);
		result.addObject("positions", positions);
		result.addObject("brotherhoods", brotherhoods);
		return result;

	}

	@RequestMapping(value = "/member/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editEnrolment(@Valid final Enrolment enrolment, final BindingResult binding) {
		ModelAndView result;
		//Enrolment enrolment;

		//enrolment = this.enrolmentService.reconstruct(enrolmentMember, binding);

		if (!binding.hasErrors()) {
			this.enrolmentService.save(enrolment);
			result = new ModelAndView("redirect:list.do");
		} else {
			final String language = LocaleContextHolder.getLocale().getLanguage();

			final Collection<Posicion> positions = this.positionService.getPositions(language);
			final Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
			result = new ModelAndView("enrolment/edit");
			result.addObject("enrolment", enrolment);
			result.addObject("positions", positions);
			result.addObject("brotherhoods", brotherhoods);
		}

		return result;
	}

	//BROTHERHOOD
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
