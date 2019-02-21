
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomizableSystemRepository;
import security.UserAccount;
import domain.CustomizableSystem;

@Service
@Transactional
public class CustomizableSystemService {

	@Autowired
	private CustomizableSystemRepository	customizableSystemRepository;

	@Autowired
	private ActorService					actorService;


	public CustomizableSystem create() {
		final CustomizableSystem res = new CustomizableSystem();
		res.setNameSystem("");
		res.setBanner("");
		res.setMessageWelcomePage("");
		res.setTelephoneCode("");

		return res;
	}

	public Collection<CustomizableSystem> findAll() {
		return this.customizableSystemRepository.findAll();
	}

	//		public CustomizableSystem findOne(final int customizableSystemId) {
	//			return this.customizableSystemRepository.findOne(customizableSystemId);
	//		}

	//updating
	public CustomizableSystem save(final CustomizableSystem customizableSystem) {
		final UserAccount user = this.actorService.getActorLogged().getUserAccount();
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
		Assert.isTrue(customizableSystem.getBanner() != null && customizableSystem.getBanner() != "" && customizableSystem.getMessageWelcomePage() != null && customizableSystem.getMessageWelcomePage() != "" && customizableSystem.getNameSystem() != null
			&& customizableSystem.getNameSystem() != "" && customizableSystem.getTelephoneCode() != null & customizableSystem.getTelephoneCode() != "");
		return this.customizableSystemRepository.save(customizableSystem);
	}

}