
package controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BrotherhoodService;
import services.ProcessionService;
import domain.Procession;

@Controller
@RequestMapping("/procession/brotherhood")
public class ProcessionBrotherhoodController extends AbstractController {

	@Autowired
	private ProcessionService	processionService;
	@Autowired
	private BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Procession> allMyProcession = this.processionService.getAllProcessionsByBrotherhood(this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()).getId());

		result = new ModelAndView("procession/list");
		result.addObject("processions", allMyProcession);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Procession procession;
		procession = this.processionService.create();

		result = new ModelAndView("procession/edit");
		result.addObject("procession", procession);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int processionId) {
		ModelAndView result;
		Procession procession;
		try {
			procession = this.processionService.findOne(processionId);
			Assert.notNull(procession);
			Assert.isTrue(this.processionService.findOne(processionId).getDraftMode() == 1);
			final Collection<Procession> allMyProcession = this.processionService.getAllProcessionsByBrotherhood(this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()).getId());
			Assert.isTrue(allMyProcession.contains(procession));
			result = new ModelAndView("procession/edit");
			result.addObject("procession", procession);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int processionId) {
		ModelAndView result;
		Procession procession;
		try {
			procession = this.processionService.findOne(processionId);
			Assert.notNull(procession);
			final Collection<Procession> allMyProcession = this.processionService.getAllProcessionsByBrotherhood(this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()).getId());
			Assert.isTrue(allMyProcession.contains(procession));
			result = new ModelAndView("procession/show");
			result.addObject("procession", procession);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(Procession newProcession, final BindingResult binding) {
		ModelAndView result;
		try {
			newProcession = this.processionService.reconstruct(newProcession, binding);
			Assert.isTrue(newProcession.getMoment().after(new Date()));
			if (!binding.hasErrors()) {
				this.processionService.save(newProcession);
				result = new ModelAndView("redirect:list.do");
			} else {
				result = new ModelAndView("procession/edit");
				result.addObject("procession", newProcession);
			}
		} catch (final Exception e) {
			result = new ModelAndView("procession/edit");
			result.addObject("procession", newProcession);
			result.addObject("fechaPasada", "fechaPasada");
		}

		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int processionId) {
		ModelAndView result;
		final Procession p = this.processionService.findOne(processionId);
		final Collection<Procession> allMyProcession = this.processionService.getAllProcessionsByBrotherhood(this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()).getId());
		try {
			Assert.isTrue(allMyProcession.contains(p));
			this.processionService.delete(p);
			result = new ModelAndView("redirect:list.do");
			return result;
		} catch (final Exception e) {
			result = new ModelAndView("procession/list");
			result.addObject("exception", e);
			result.addObject("processions", allMyProcession);
			return result;
		}
	}

}
