
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;
import domain.Picture;
import forms.RegistrationFormBrotherhood;

@Service
@Transactional
public class BrotherhoodService {

	@Autowired
	private BrotherhoodRepository		brotherhoodRepo;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private PictureService				pictureService;

	@Autowired
	private CustomizableSystemService	customizableService;

																;

	@Autowired
	private Validator					validator;


	//Metodos CRUD

	public Brotherhood create() {
		final Brotherhood res = new Brotherhood();

		res.setTitle("");
		res.setEstablishmentDate(new Date());
		res.setPictures(new HashSet<Picture>());
		res.setAddress("");
		res.setEmail("");
		res.setName("");
		res.setMiddleName("");
		final String telephoneCode = this.customizableService.getTelephoneCode();
		res.setPhone(telephoneCode + " ");
		res.setPhoto("");
		res.setSurname("");

		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.BROTHERHOOD);
		user.getAuthorities().add(ad);
		user.setUsername("");
		user.setPassword("");
		res.setUserAccount(user);

		return res;
	}

	//listing
	public Collection<Brotherhood> findAll() {
		return this.brotherhoodRepo.findAll();
	}

	public Brotherhood findOne(final int brotherhoodId) {
		return this.brotherhoodRepo.findOne(brotherhoodId);
	}

	//updating
	public Brotherhood save(final Brotherhood r) {

		//final UserAccount userLoged = LoginService.getPrincipal();
		//Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"), "Comprobar que hay brotherhood conectado");

		Brotherhood res = null;
		Assert.isTrue(r.getTitle() != null && r.getTitle() != "" && r.getEstablishmentDate() != null, "BrotherService.save -> Tilte or EstablishmentDate invalid");
		Assert.isTrue(r != null && r.getName() != null && r.getSurname() != null && r.getName() != "" && r.getSurname() != "" && r.getUserAccount() != null && r.getEmail() != null && r.getEmail() != "", "BrotherService.save -> Name or Surname invalid");

		final String regexEmail1 = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
		final Pattern patternEmail1 = Pattern.compile(regexEmail1);
		final Matcher matcherEmail1 = patternEmail1.matcher(r.getEmail());

		final String regexEmail2 = "^[A-z0-9]+\\s*[A-z0-9\\s]*\\s\\<[A-z0-9]+\\@[A-z0-9]+\\.[A-z0-9.]+\\>";
		final Pattern patternEmail2 = Pattern.compile(regexEmail2);
		final Matcher matcherEmail2 = patternEmail2.matcher(r.getEmail());
		Assert.isTrue(matcherEmail1.find() == true || matcherEmail2.find() == true, "CustomerService.save -> Correo inv�lido");

		final List<String> emails = this.actorService.getEmails();

		if (r.getId() == 0)
			Assert.isTrue(!emails.contains(r.getEmail()), "Brotherhood.Email -> The email you entered is already being used");
		else {
			final Brotherhood a = this.brotherhoodRepo.findOne(r.getId());
			Assert.isTrue(a.getEmail().equals(r.getEmail()));
		}

		if (r.getPhone() != "" || r.getPhone() != null) {
			final String regexTelefono = "^\\+[1-9][0-9]{0,2}\\ \\([1-9][0-9]{0,2}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}$";
			final Pattern patternTelefono = Pattern.compile(regexTelefono);
			final Matcher matcherTelefono = patternTelefono.matcher(r.getPhone());
			//Assert.isTrue(matcherTelefono.find() == true, "BrotherhoodService.save -> Telefono no valido");
		}

		//NUEVO
		Assert.isTrue(r.getUserAccount().getUsername() != null && r.getUserAccount().getUsername() != "");
		Assert.isTrue(r.getUserAccount().getPassword() != null && r.getUserAccount().getPassword() != "");

		if (r.getId() == 0) {

			final Md5PasswordEncoder encoder;
			encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(r.getUserAccount().getPassword(), null);
			final UserAccount user = r.getUserAccount();
			user.setPassword(hash);
		}

		res = this.brotherhoodRepo.save(r);
		return res;
	}

	public Brotherhood brotherhoodUserAccount(final Integer id) {
		return this.brotherhoodRepo.brotherhoodUserAccount(id);
	}

	public Collection<Brotherhood> getBrotherhoodsByMember(final Integer memberId) {
		return this.brotherhoodRepo.getBrotherhoodsByMember(memberId);
	}

	public Collection<Brotherhood> getBrotherhoodsBelongsByMember(final Integer memberId) {
		final UserAccount userLoged = LoginService.getPrincipal();
		Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("MEMBER"));
		return this.brotherhoodRepo.getBrotherhoodsbelongsByMember(memberId);
	}

	public Collection<Brotherhood> getBrotherhoodsBelongedByMember(final Integer memberId) {
		final UserAccount userLoged = LoginService.getPrincipal();
		Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("MEMBER"));
		return this.brotherhoodRepo.getBrotherhoodsbelongedByMember(memberId);
	}

	public List<Object[]> getMaxMinAvgDesvMembersBrotherhood() {
		return this.brotherhoodRepo.getMaxMinAvgDesvMembersBrotherhood();
	}

	public Collection<String> getLargestBrotherhoods() {
		return this.brotherhoodRepo.getLargestBrotherhoods();
	}

	public Collection<String> getSmallestBrotherhoods() {
		return this.brotherhoodRepo.getSmallestBrotherhoods();
	}
	public Brotherhood reconstruct(final RegistrationFormBrotherhood registrationForm, final BindingResult binding) {
		Brotherhood res = new Brotherhood();

		if (registrationForm.getId() == 0) {
			res.setId(registrationForm.getUserAccount().getId());
			res.setVersion(registrationForm.getVersion());
			res.setAddress(registrationForm.getAddress());
			res.setEmail(registrationForm.getEmail());
			res.setMiddleName(registrationForm.getMiddleName());
			res.setName(registrationForm.getName());
			res.setPhone(registrationForm.getPhone());
			res.setPhoto(registrationForm.getPhoto());
			res.setSurname(registrationForm.getSurname());
			final Authority ad = new Authority();
			final UserAccount user = new UserAccount();
			user.setAuthorities(new HashSet<Authority>());
			ad.setAuthority(Authority.BROTHERHOOD);
			user.getAuthorities().add(ad);
			res.setUserAccount(user);
			user.setUsername(registrationForm.getUserAccount().getUsername());
			user.setPassword(registrationForm.getUserAccount().getPassword());

			res.setPictures(new HashSet<Picture>());
			res.setTitle(registrationForm.getTitle());
			res.setEstablishmentDate(registrationForm.getEstablishmentDate());

			this.validator.validate(res, binding);

		} else {

			res = this.brotherhoodRepo.findOne(registrationForm.getId());
			final Brotherhood p = new Brotherhood();
			p.setId(res.getId());
			p.setVersion(res.getVersion());
			p.setAddress(res.getAddress());
			p.setEmail(res.getEmail());
			p.setMiddleName(res.getMiddleName());
			p.setName(res.getName());
			p.setPhone(res.getPhone());
			p.setPhoto(res.getPhoto());
			p.setSurname(res.getSurname());
			p.setUserAccount(res.getUserAccount());
			p.setPictures(res.getPictures());
			p.setTitle(registrationForm.getTitle());
			p.setEstablishmentDate(registrationForm.getEstablishmentDate());

			this.validator.validate(p, binding);
			res = p;

		}
		return res;

	}

}
