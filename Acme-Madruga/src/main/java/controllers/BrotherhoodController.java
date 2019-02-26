
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import domain.Brotherhood;

@Controller
@RequestMapping("/brotherhood")
public class BrotherhoodController extends AbstractController {

	@Autowired
	private BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Brotherhood brotherhood;
		//final Collection<Picture> pictures = this.pictureService.finaAll();
		brotherhood = this.brotherhoodService.create();
		result = new ModelAndView("brotherhood/create");
		result.addObject("brotherhood", brotherhood);
		//result.addObject("pictures", pictures);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Brotherhood brotherhood, final BindingResult binding) {
		ModelAndView result;
		try {
			if (!binding.hasErrors()) {
				this.brotherhoodService.save(brotherhood);
				result = new ModelAndView("redirect:http://localhost:8080/Acme-Madruga");
			} else {
				result = new ModelAndView("brotherhood/create");
				result.addObject("brotherhood", brotherhood);
			}
		} catch (final Exception e) {
			result = new ModelAndView("brotherhood/create");
			result.addObject("exception", e);
			brotherhood.getUserAccount().setPassword("");
			result.addObject("brotherhood", brotherhood);
		}

		return result;
	}
}
