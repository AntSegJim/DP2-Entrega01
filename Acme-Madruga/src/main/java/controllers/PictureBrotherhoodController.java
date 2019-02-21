
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.BrotherhoodService;
import services.FloatService;
import services.PictureService;
import domain.Brotherhood;
import domain.Paso;
import domain.Picture;

@Controller
@RequestMapping("/picture/brotherhood")
public class PictureBrotherhoodController extends AbstractController {

	@Autowired
	private PictureService		pictureService;
	@Autowired
	private FloatService		floatService;
	@Autowired
	private BrotherhoodService	brotherhoodService;


	//Imagenes de una Brotherhood(Hermandad)
	@RequestMapping(value = "/picturesBrotherhood", method = RequestMethod.GET)
	public ModelAndView picturesBrotherhood() {
		final ModelAndView result;
		final Collection<Picture> pictures;
		final UserAccount user = LoginService.getPrincipal();
		final Brotherhood br = this.brotherhoodService.brotherhoodUserAccount(user.getId());

		pictures = br.getPictures();

		result = new ModelAndView("picture/picturesBrotherhood");
		result.addObject("pictures", pictures);

		return result;
	}

	//Imagenes de un Float(Paso)
	@RequestMapping(value = "/picturesFloat", method = RequestMethod.GET)
	public ModelAndView picturesFloat(@RequestParam final int floatId) {
		final ModelAndView result;
		final Collection<Picture> pictures;

		final Paso fl = this.floatService.findOne(floatId);
		pictures = fl.getPictures();

		result = new ModelAndView("picture/picturesFloat");
		result.addObject("pictures", pictures);

		return result;
	}

	//Creacion de una imagen tanto para brotherhood como para float ya que es la 
	//misma jsp para ambos
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView createPictureBrotherhoodFloat() {
		ModelAndView result;
		Picture picture;

		picture = this.pictureService.create();
		result = new ModelAndView("picture/editPicture");
		result.addObject("picture", picture);

		return result;
	}

}
