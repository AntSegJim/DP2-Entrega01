
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

		Assert.isTrue((matcherEmail1.matches() == true || matcherEmail2.matches() == true), "Email");

		final List<String> emails = this.actorService.getEmails();

		if (r.getId() == 0)
			Assert.isTrue(!emails.contains(r.getEmail()), "Brotherhood.Email -> The email you entered is already being used");
		//		else {
		//			final Brotherhood a = this.brotherhoodRepo.findOne(r.getId());
		//			Assert.isTrue(a.getEmail().equals(r.getEmail()));
		//		}

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

			if (res.getPhone().length() <= 5)
				res.setPhone("");

			if (registrationForm.getPatternPhone() == false) {
				final String regexTelefono = "^\\+[0-9]{0,3}\\s\\([0-9]{0,3}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}|^\\+[0-9]\\ $|^$|^\\+$";
				final Pattern patternTelefono = Pattern.compile(regexTelefono);
				final Matcher matcherTelefono = patternTelefono.matcher(res.getPhone());
				Assert.isTrue(matcherTelefono.find() == true, "BrotherhoodService.save -> Telefono no valido");
			}

			Assert.isTrue(registrationForm.getPassword().equals(registrationForm.getUserAccount().getPassword()));
			Assert.isTrue(registrationForm.getCheck() == true);

			final String regexEmail1 = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
			final Pattern patternEmail1 = Pattern.compile(regexEmail1);
			final Matcher matcherEmail1 = patternEmail1.matcher(res.getEmail());

			final String regexEmail2 = "^[A-z0-9]+\\s*[A-z0-9\\s]*\\s\\<[A-z0-9]+\\@[A-z0-9]+\\.[A-z0-9.]+\\>";
			final Pattern patternEmail2 = Pattern.compile(regexEmail2);
			final Matcher matcherEmail2 = patternEmail2.matcher(res.getEmail());

			if (!(matcherEmail1.matches() == true || matcherEmail2.matches() == true))
				binding.rejectValue("email", "PatternNoValido");

			this.validator.validate(res, binding);

		} else {
			Assert.isTrue(registrationForm.getPassword().equals(registrationForm.getUserAccount().getPassword()));
			res = this.brotherhoodRepo.findOne(registrationForm.getId());
			final Brotherhood p = new Brotherhood();
			p.setId(res.getId());
			p.setVersion(res.getVersion());
			p.setAddress(registrationForm.getAddress());
			p.setEmail(registrationForm.getEmail());
			p.setMiddleName(registrationForm.getMiddleName());
			p.setName(registrationForm.getName());
			p.setPhone(registrationForm.getPhone());
			p.setPhoto(registrationForm.getPhoto());
			p.setSurname(registrationForm.getSurname());

			p.setPictures(res.getPictures());
			p.setTitle(registrationForm.getTitle());
			p.setEstablishmentDate(registrationForm.getEstablishmentDate());

			if (registrationForm.getPassword().equals("") || registrationForm.getPassword() == null)
				p.setUserAccount(res.getUserAccount());
			else {
				final UserAccount user = res.getUserAccount();
				final Md5PasswordEncoder encoder;
				encoder = new Md5PasswordEncoder();
				final String hash = encoder.encodePassword(registrationForm.getPassword(), null);
				user.setPassword(hash);
				p.setUserAccount(user);
			}

			if (p.getPhone().length() <= 5)
				p.setPhone("");

			if (registrationForm.getPatternPhone() == false) {
				final String regexTelefono = "^\\+[0-9]{0,3}\\s\\([0-9]{0,3}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}|^\\+[0-9]\\ $|^$|^\\+$";
				final Pattern patternTelefono = Pattern.compile(regexTelefono);
				final Matcher matcherTelefono = patternTelefono.matcher(p.getPhone());
				Assert.isTrue(matcherTelefono.find() == true, "BrotherhoodService.save -> Telefono no valido");
			}

			final String regexEmail1 = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
			final Pattern patternEmail1 = Pattern.compile(regexEmail1);
			final Matcher matcherEmail1 = patternEmail1.matcher(p.getEmail());

			final String regexEmail2 = "^[A-z0-9]+\\s*[A-z0-9\\s]*\\s\\<[A-z0-9]+\\@[A-z0-9]+\\.[A-z0-9.]+\\>";
			final Pattern patternEmail2 = Pattern.compile(regexEmail2);
			final Matcher matcherEmail2 = patternEmail2.matcher(p.getEmail());

			if (!(matcherEmail1.matches() == true || matcherEmail2.matches() == true))
				binding.rejectValue("email", "PatternNoValido");

			this.validator.validate(p, binding);
			res = p;

		}
		return res;

	}

	public Brotherhood reconstruct(final Brotherhood brotherhood, final BindingResult binding) {
		Brotherhood res;

		if (brotherhood.getId() == 0) {
			res = brotherhood;
			final Authority ad = new Authority();
			final UserAccount user = new UserAccount();
			user.setAuthorities(new HashSet<Authority>());
			ad.setAuthority(Authority.BROTHERHOOD);
			user.getAuthorities().add(ad);
			res.setUserAccount(user);
			user.setUsername(brotherhood.getUserAccount().getUsername());
			user.setPassword(brotherhood.getUserAccount().getPassword());
			res.setPictures(new HashSet<Picture>());

			this.validator.validate(res, binding);
			return res;
		} else {
			res = this.brotherhoodRepo.findOne(brotherhood.getId());
			final Brotherhood p = new Brotherhood();
			p.setId(res.getId());
			p.setVersion(res.getVersion());
			p.setAddress(brotherhood.getAddress());
			p.setEmail(brotherhood.getEmail());
			p.setMiddleName(brotherhood.getMiddleName());
			p.setName(brotherhood.getName());
			p.setPhone(brotherhood.getPhone());
			p.setPhoto(brotherhood.getPhoto());
			p.setSurname(brotherhood.getSurname());
			p.setUserAccount(res.getUserAccount());
			p.setPictures(res.getPictures());
			p.setEstablishmentDate(brotherhood.getEstablishmentDate());
			p.setTitle(brotherhood.getTitle());

			this.validator.validate(p, binding);
			return p;
		}

	}

}
