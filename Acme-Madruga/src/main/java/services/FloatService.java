
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FloatRepository;
import security.UserAccount;
import domain.Brotherhood;
import domain.Float;
import domain.Picture;
import domain.Procession;

@Service
@Transactional
public class FloatService {

	@Autowired
	private FloatRepository	FRepo;
	@Autowired
	private ActorService	actorS;


	//Metodo create
	public Float create() {
		final Float paso = new Float();
		paso.setTitle("");
		paso.setDescription("");
		paso.setPictures(new HashSet<Picture>());
		paso.setBrotherhood(new Brotherhood());
		paso.setProcession(new Procession());
		return paso;
	}
	//Metodo findAll
	public Collection<Float> finaAll() {
		return this.FRepo.findAll();
	}
	//Metodo findOne
	public Float findOne(final int floatId) {
		return this.FRepo.findOne(floatId);
	}
	//Metodo save
	public Float save(final Float paso) {
		//Que la iamgen que se va a guardar no se nulla y la url de la iamgen no sea nula
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(paso != null && paso.getTitle() != null && paso.getTitle() != "" && paso.getDescription() != null && paso.getDescription() != "" && paso.getBrotherhood() != null);
		return this.FRepo.save(paso);
	}
	//Metodo delete
	public void delete(final Float paso) {
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		this.FRepo.delete(paso);
	}
}