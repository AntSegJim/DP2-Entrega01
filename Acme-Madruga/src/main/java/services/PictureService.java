
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PictureRepository;
import security.UserAccount;
import domain.Picture;

@Service
@Transactional
public class PictureService {

	@Autowired
	private PictureRepository	PRepo;
	@Autowired
	private ActorService		actorS;
	@Autowired
	private BrotherhoodService	brotherhoodService;


	//@Autowired
	//private FloatService		floatService;

	//Metodo create
	public Picture create() {
		final Picture pic = new Picture();
		pic.setUrl("");
		return pic;
	}
	//Metodo findAll
	public Collection<Picture> finaAll() {
		return this.PRepo.findAll();
	}
	//Metodo findOne
	public Picture findOne(final int PictureId) {
		return this.PRepo.findOne(PictureId);
	}
	//Metodo save
	public Picture save(final Picture picture) {
		//Que la iamgen que se va a guardar no se nulla y la url de la iamgen no sea nula
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(picture != null && picture.getUrl() != null);
		//		final Picture picSave = this.PRepo.save(picture);
		//		final Brotherhood br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
		//		br.getPictures().add(picSave);
		return this.PRepo.save(picture);
		//return picSave;
	}
	//Metodo delete
	public void delete(final Picture picture) {
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		this.PRepo.delete(picture);
	}

	public Collection<Picture> getPicturesFloatByBrotherhood(final Integer id) {
		return this.PRepo.getPicturesFloatByBrotherhood(id);
	}

}
