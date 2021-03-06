
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
import services.ActorService;
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
	@Autowired
	private ActorService		actorS;


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
		ModelAndView result;
		try {
			final Collection<Picture> pictures;

			final Paso fl = this.floatService.findOne(floatId);
			pictures = fl.getPictures();

			result = new ModelAndView("picture/picturesFloat");
			result.addObject("pictures", pictures);
			result.addObject("paso", fl);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../float/brotherhood/list.do");
		}
		return result;
	}

	//Creacion de una imagen para la hermandad
	@RequestMapping(value = "/createPictureBrotherhood", method = RequestMethod.GET)
	public ModelAndView createPictureBrotherhood() {
		ModelAndView result;
		Picture picture;

		picture = this.pictureService.create();
		result = new ModelAndView("picture/editPictureBrotherhood");
		result.addObject("picture", picture);

		return result;
	}

	//Edicion de una imagne de una brotherhood(hermandad)
	@RequestMapping(value = "/editPictureBrotherhood", method = RequestMethod.GET)
	public ModelAndView editPictureBrotherhood(@RequestParam final int pictureId) {
		ModelAndView result;
		try {
			Picture picture;

			picture = this.pictureService.findOne(pictureId);
			Assert.notNull(picture);
			result = new ModelAndView("picture/editPictureBrotherhood");
			result.addObject("picture", picture);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:picturesBrotherhood.do");
		}
		return result;
	}

	@RequestMapping(value = "/editPictureBrotherhood", method = RequestMethod.POST, params = "save")
	public ModelAndView savePictureBrotherhood(@Valid final Picture picture, final BindingResult binding) {
		ModelAndView result;
		final UserAccount user = LoginService.getPrincipal();
		final Brotherhood br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
		if (!binding.hasErrors()) {
			final Picture picSave = this.pictureService.save(picture);
			if (br.getPictures().contains(picture)) {
				br.getPictures().remove(picture);
				br.getPictures().add(picSave);
			} else
				br.getPictures().add(picSave);
			this.brotherhoodService.save(br);
			result = new ModelAndView("redirect:picturesBrotherhood.do");
		} else {
			result = new ModelAndView("picture/editPictureBrotherhood");
			result.addObject("picture", picture);
		}
		return result;
	}

	@RequestMapping(value = "/editPictureBrotherhood", method = RequestMethod.POST, params = "delete")
	public ModelAndView deletePictureBrotherhood(final Picture picture, final BindingResult binding) {
		ModelAndView result;
		final UserAccount user = LoginService.getPrincipal();
		final Brotherhood br = this.brotherhoodService.brotherhoodUserAccount(user.getId());

		if (!binding.hasErrors()) {
			br.getPictures().remove(picture);
			this.pictureService.delete(picture);
			result = new ModelAndView("redirect:picturesBrotherhood.do");
		} else {
			result = new ModelAndView("picture/editPictureBrotherhood");
			result.addObject("picture", picture);
		}

		return result;

	}

	//Creacion de una imagen para la hermandad
	@RequestMapping(value = "/createPictureFloat", method = RequestMethod.GET)
	public ModelAndView createPictureFloat(@RequestParam final int floatId) {
		ModelAndView result;
		try {
			Picture picture;
			final Paso fl = this.floatService.findOne(floatId);

			picture = this.pictureService.create();
			result = new ModelAndView("picture/editPictureFloat");
			result.addObject("picture", picture);
			result.addObject("paso", fl);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../float/brotherhood/list.do");
		}
		return result;
	}

	//Edicion de una imagne de un float(paso)
	@RequestMapping(value = "/editPictureFloat", method = RequestMethod.GET)
	public ModelAndView editPictureFloat(@RequestParam final int pictureId, @RequestParam final int floatId) {
		ModelAndView result;
		try {
			Picture picture;
			Paso paso;

			picture = this.pictureService.findOne(pictureId);
			paso = this.floatService.findOne(floatId);
			Assert.notNull(picture);
			result = new ModelAndView("picture/editPictureFloat");
			result.addObject("picture", picture);
			result.addObject("paso", paso);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../float/brotherhood/list.do");
		}

		return result;
	}

	@RequestMapping(value = "/editPictureFloat", method = RequestMethod.POST, params = "save")
	public ModelAndView savePictureFloat(@Valid final Picture picture, final BindingResult binding, @RequestParam final int floatId) {
		ModelAndView result;
		if (!binding.hasErrors()) {
			final Picture picSave = this.pictureService.save(picture);
			final Paso fl = this.floatService.findOne(floatId);
			if (fl.getPictures().contains(picSave)) {
				fl.getPictures().remove(picSave);
				fl.getPictures().add(picSave);
			} else
				fl.getPictures().add(picSave);
			this.floatService.save(fl);
			result = new ModelAndView("redirect:picturesFloat.do?floatId=" + floatId);
		} else {
			final Paso fl = this.floatService.findOne(floatId);
			result = new ModelAndView("picture/editPictureFloat");
			result.addObject("picture", picture);
			result.addObject("paso", fl);
		}
		return result;
	}

	@RequestMapping(value = "/editPictureFloat", method = RequestMethod.POST, params = "delete")
	public ModelAndView deletePictureFloat(final Picture picture, final BindingResult binding, @RequestParam final int floatId) {
		ModelAndView result;

		if (!binding.hasErrors()) {
			final Paso p = this.floatService.findOne(floatId);
			p.getPictures().remove(picture);
			this.pictureService.delete(picture);
			this.floatService.save(p);
			result = new ModelAndView("redirect:picturesFloat.do?floatId=" + floatId);
		} else {
			result = new ModelAndView("picture/editPictureFloat");
			result.addObject("picture", picture);
			result.addObject("paso", this.floatService.findOne(floatId));
		}
		return result;
	}

}
