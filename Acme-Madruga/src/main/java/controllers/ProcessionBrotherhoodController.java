
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.ProcessionService;
import domain.Procession;

@Controller
@RequestMapping("/procession/brotherhood")
public class ProcessionBrotherhoodController extends AbstractController {

	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private ProcessionService	processionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Procession> processions;
		processions = this.processionService.findAll();

		result = new ModelAndView("procession/list");
		result.addObject("procession", processions);
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
	public ModelAndView edit(@Valid final Procession newProcession, final BindingResult binding) {
		final ModelAndView result;

		if (!binding.hasErrors()) {
			this.processionService.save(newProcession);
			result = new ModelAndView("redirect:list.do");
		} else {
			result = new ModelAndView("procession/edit");
			result.addObject("procession", newProcession);
		}
		return result;

	}

}
