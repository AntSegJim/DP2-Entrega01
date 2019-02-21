
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.FloatService;
import domain.Paso;

@Controller
@RequestMapping("/float/brotherhood")
public class FloatBrotherhoodController extends AbstractController {

	@Autowired
	private FloatService	floatService;


	//Listado de Floats(Pasos)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView floats() {
		final ModelAndView result;
		final Collection<Paso> floats;
		final UserAccount user = LoginService.getPrincipal();

		result = new ModelAndView("float/list");
		//result.addObject("floats", floats);

		return result;
	}
}
