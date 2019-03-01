
package services;

import java.util.Collection;
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

import repositories.MemberRepository;
import security.Authority;
import security.UserAccount;
import domain.Member;
import domain.Request;

@Service
@Transactional
public class MemberService {

	@Autowired
	private MemberRepository			memberRepo;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private CustomizableSystemService	customizableService;
	@Autowired
	private Validator					validator;


	//Crear nuevo member
	public Member create() {
		final Member res = new Member();
		res.setAddress("");
		res.setEmail("");
		res.setName("");
		res.setMiddleName("");
		final String telephoneCode = this.customizableService.getTelephoneCode();
		res.setPhone(telephoneCode + " ");
		res.setPhoto("");
		res.setSurname("");
		res.setRequests(new HashSet<Request>());

		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.MEMBER);
		user.getAuthorities().add(ad);
		user.setUsername("");
		user.setPassword("");
		res.setUserAccount(user);

		return res;
	}
	//Todos los member
	public Collection<Member> findAll() {
		return this.memberRepo.findAll();
	}

	//Un member por el id
	public Member findOne(final int memberId) {
		return this.memberRepo.findOne(memberId);
	}

	//Guardar
	public Member save(final Member r) {
		Member res = null;
		Assert.isTrue(r != null && r.getName() != null && r.getSurname() != null && r.getName() != "" && r.getSurname() != "" && r.getUserAccount() != null && r.getEmail() != null && r.getEmail() != "", "MemberService.save -> Name or Surname invalid");

		final String regexEmail1 = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
		final Pattern patternEmail1 = Pattern.compile(regexEmail1);
		final Matcher matcherEmail1 = patternEmail1.matcher(r.getEmail());

		final String regexEmail2 = "^[A-z0-9]+\\s*[A-z0-9\\s]*\\s\\<[A-z0-9]+\\@[A-z0-9]+\\.[A-z0-9.]+\\>";
		final Pattern patternEmail2 = Pattern.compile(regexEmail2);
		final Matcher matcherEmail2 = patternEmail2.matcher(r.getEmail());
		Assert.isTrue(matcherEmail1.find() == true || matcherEmail2.find() == true, "CustomerService.save -> Correo inv�lido");

		final List<String> emails = this.actorService.getEmails();

		if (r.getId() == 0)
			Assert.isTrue(!emails.contains(r.getEmail()));
		else {
			final Member a = this.memberRepo.findOne(r.getId());
			Assert.isTrue(a.getEmail().equals(r.getEmail()));
		}

		if (r.getPhone() != "" || r.getPhone() != null || r.getPhone() != "+34 ") {
			final String regexTelefono = "^\\+[1-9][0-9]{0,2}\\ \\([1-9][0-9]{0,2}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}|^\\+[0-9]\\ $|^$|^\\+$";
			final Pattern patternTelefono = Pattern.compile(regexTelefono);
			final Matcher matcherTelefono = patternTelefono.matcher(r.getPhone());
			Assert.isTrue(matcherTelefono.find() == true, "MemberService.save -> Telefono no valido");
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

		//Probar tele

		if (r.getPhone() == "+34 ")
			r.setPhone("");

		res = this.memberRepo.save(r);
		return res;
	}

	public Member getMemberByUserAccount(final int userAccountId) {
		return this.memberRepo.getMemberByUserAccount(userAccountId);
	}

	public Collection<Member> getMemberByBrotherhood(final Integer id) {
		return this.memberRepo.getMembersOfBrotherhood(id);
	}

	//RECONSTRUCT
	public Member reconstruct(final Member member, final BindingResult binding) {
		Member res;

		if (member.getId() == 0) {
			res = member;
			res.setRequests(new HashSet<Request>());
			this.validator.validate(res, binding);
			return res;
		} else {
			res = this.memberRepo.findOne(member.getId());
			final Member p = new Member();
			p.setId(res.getId());
			p.setVersion(res.getVersion());
			p.setAddress(member.getAddress());
			p.setEmail(member.getEmail());
			p.setMiddleName(member.getMiddleName());
			p.setName(member.getName());
			p.setPhone(member.getPhone());
			p.setPhoto(member.getPhoto());
			p.setRequests(res.getRequests());
			p.setSurname(member.getSurname());
			p.setUserAccount(res.getUserAccount());

			this.validator.validate(p, binding);
			return p;
		}

	}

}
