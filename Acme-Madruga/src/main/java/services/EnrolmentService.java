
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EnrolmentRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;
import domain.Posicion;
import forms.EnrolmentMember;

@Service
@Transactional
public class EnrolmentService {

	@Autowired
	private EnrolmentRepository	enrolmentRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	public Enrolment create() {
		final Enrolment enrolment = new Enrolment();

		enrolment.setMoment(new Date());
		enrolment.setBrotherhood(null);
		enrolment.setStatus(0);
		enrolment.setIsOut(0);
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		enrolment.setMember((Member) a);
		enrolment.setPosition(new Posicion());
		enrolment.setBrotherhood(new Brotherhood());
		return enrolment;
	}

	public Collection<Enrolment> findAll() {
		return this.enrolmentRepository.findAll();
	}

	public Enrolment findOne(final int idEnrolment) {
		return this.enrolmentRepository.findOne(idEnrolment);
	}

	public Enrolment save(final Enrolment enrolment) {
		Enrolment res = null;

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		if (enrolment.getId() == 0) {
			enrolment.setMember((Member) a);
			enrolment.setMoment(new Date());
		}

		Assert.isTrue(enrolment.getBrotherhood() != null, "Enrolment.service: Brotherhood no puede ser null");
		Assert.isTrue(enrolment.getMember() != null, "Enrolment.service: Member no puede ser null");
		Assert.isTrue(enrolment.getPosition() != null, "Enrolment.service: Position no puede ser null");
		Assert.isTrue(enrolment.getStatus() >= 0 && enrolment.getStatus() <= 2, "Enrolment.service: Status debe tener un valor entre 0 y 2");
		Assert.isTrue(enrolment.getIsOut() >= 0 && enrolment.getIsOut() <= 1, "Enrolment.service: Is out debe tener un valor entre 0 y 2");

		res = this.enrolmentRepository.save(enrolment);
		return res;
	}

	//RECONSTRUCT
	public Enrolment reconstruct(final EnrolmentMember enrolmentForm, final BindingResult binding) {
		Enrolment res;

		if (enrolmentForm.getId() == 0)
			res = this.create();
		else {
			res = this.enrolmentRepository.findOne(enrolmentForm.getId());
			res.setPosition(enrolmentForm.getPosition());
			res.setBrotherhood(enrolmentForm.getBrotherhood());

			this.validator.validate(res, binding);

		}
		return res;
	}
	public Collection<Enrolment> enrolmentByMember(final Integer id) {
		return this.enrolmentRepository.enrolmentByMember(id);
	}

	public Collection<Enrolment> enrolmentByBrotherhood(final Integer id) {
		return this.enrolmentRepository.enrolmentByBrotherhood(id);
	}

	//	public void delete(final Enrolment enrolment) {
	//		if (enrolment.getStatus() == 2)
	//			this.enrolmentRepository.delete(enrolment);
	//	}

}
