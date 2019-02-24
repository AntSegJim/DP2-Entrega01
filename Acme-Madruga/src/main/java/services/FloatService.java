
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FloatRepository;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;
import domain.Paso;
import domain.Picture;
import domain.Procession;

@Service
@Transactional
public class FloatService {

	@Autowired
	private FloatRepository		FRepo;
	@Autowired
	private ActorService		actorS;
	@Autowired
	private BrotherhoodService	brotherhoodService;


	//Metodo create
	public Paso create() {
		final Paso paso = new Paso();
		final UserAccount user = LoginService.getPrincipal();
		final Brotherhood br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
		paso.setTitle("");
		paso.setDescription("");
		paso.setPictures(new HashSet<Picture>());
		paso.setBrotherhood(br);
		paso.setProcession(new Procession());
		return paso;
	}
	//Metodo findAll
	public Collection<Paso> finaAll() {
		return this.FRepo.findAll();
	}
	//Metodo findOne
	public Paso findOne(final int floatId) {
		return this.FRepo.findOne(floatId);
	}
	//Metodo save
	public Paso save(final Paso paso) {
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(paso != null && paso.getTitle() != null && paso.getTitle() != "" && paso.getDescription() != null && paso.getDescription() != "" && paso.getBrotherhood() != null);
		return this.FRepo.save(paso);
	}
	//Metodo delete
	public void delete(final Paso paso) {
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		this.FRepo.delete(paso);
	}

	//Metodo que devuelve las floats(pasos) de una brotherhood
	public Collection<Paso> getFloatsByBrotherhood(final Integer brotherhoodId) {
		return this.FRepo.getFlotasByBrotherhood(brotherhoodId);
	}
}
