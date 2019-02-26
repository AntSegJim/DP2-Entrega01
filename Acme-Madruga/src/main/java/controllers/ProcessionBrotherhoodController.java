
package controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ProcessionService;
import domain.Procession;

@Controller
@RequestMapping("/procession/brotherhood")
public class ProcessionBrotherhoodController extends AbstractController {

	@Autowired
	private ProcessionService	processionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Procession> processions;
		processions = this.processionService.findAll();

		result = new ModelAndView("procession/list");
		result.addObject("processions", processions);
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

}
