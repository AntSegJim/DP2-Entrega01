
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

import security.LoginService;
import security.UserAccount;
import services.BrotherhoodService;
import services.FloatService;
import services.ProcessionService;
import domain.Brotherhood;
import domain.Paso;
import domain.Procession;

@Controller
@RequestMapping("/float/brotherhood")
public class FloatBrotherhoodController extends AbstractController {

	@Autowired
	private FloatService		floatService;
	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private ProcessionService	processionService;


	//Listado de Floats(Pasos)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView floats() {
		final ModelAndView result;
		final Collection<Paso> floats;
		final UserAccount user = LoginService.getPrincipal();
		final Brotherhood br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
		floats = this.floatService.getFloatsByBrotherhood(br.getId());

		result = new ModelAndView("float/list");
		result.addObject("floats", floats);

		return result;
	}

	//Mostrado de un float(paso)
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int floatId) {
		ModelAndView result;
		Paso paso;
		paso = this.floatService.findOne(floatId);
		result = new ModelAndView("float/show");
		result.addObject("paso", paso);
		return result;
	}

	//Creado de un float(paso)
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createFloat() {
		final ModelAndView result;
		final Paso paso;
		Collection<Procession> processions;
		final UserAccount user = LoginService.getPrincipal();
		final Brotherhood br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
		processions = this.processionService.getProcessionByBrotherhood(br.getId());

		paso = this.floatService.create();

		result = new ModelAndView("float/edit");
		result.addObject("paso", paso);
		result.addObject("processions", processions);
		return result;
	}

	//Editado de un float(paso)
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editFloat(@RequestParam final int floatId) {
		ModelAndView result;
		Paso paso;
		Collection<Procession> processions;
		final UserAccount user = LoginService.getPrincipal();
		final Brotherhood br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
		processions = this.processionService.getProcessionByBrotherhood(br.getId());

		paso = this.floatService.findOne(floatId);
		Assert.notNull(paso);
		result = new ModelAndView("float/edit");
		result.addObject("paso", paso);
		result.addObject("processions", processions);
		return result;
	}

	//Guardado de un float(paso)
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Paso paso, final BindingResult binding) {
		ModelAndView result;
		if (!binding.hasErrors()) {
			this.floatService.save(paso);
			result = new ModelAndView("redirect:list.do");
		} else {
			Collection<Procession> processions;
			final UserAccount user = LoginService.getPrincipal();
			final Brotherhood br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
			processions = this.processionService.getProcessionByBrotherhood(br.getId());
			result = new ModelAndView("float/edit");
			result.addObject("paso", paso);
			result.addObject("processions", processions);
		}
		return result;
	}

	//Borrado de un float(paso)
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Paso paso, final BindingResult binding) {
		ModelAndView result;
		try {
			this.floatService.delete(paso);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			Collection<Procession> processions;
			final UserAccount user = LoginService.getPrincipal();
			final Brotherhood br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
			processions = this.processionService.getProcessionByBrotherhood(br.getId());
			result = new ModelAndView("float/edit");
			result.addObject("paso", paso);
			result.addObject("processions", processions);
		}
		return result;

	}

}
