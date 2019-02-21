
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.PositionService;
import domain.Posicion;

@Controller
@RequestMapping("/position/administrator")
public class PosicionAdministratorController extends AbstractController {

	@Autowired
	private PositionService	posicionService;


	public PosicionAdministratorController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listPosition() {
		final ModelAndView result;
		final Collection<Posicion> positions;

		positions = this.posicionService.findAll();
		Assert.notNull(positions);

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		return result;

	}

}
