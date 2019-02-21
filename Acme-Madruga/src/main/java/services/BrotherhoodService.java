
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.BrotherhoodRepository;
import domain.Brotherhood;

@Service
@Transactional
public class BrotherhoodService {

	@Autowired
	private BrotherhoodRepository	brotherhoodRepo;


	public Brotherhood brotherhoodUserAccount(final Integer id) {
		return this.brotherhoodRepo.brotherhoodUserAccount(id);
	}

}
