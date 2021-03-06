
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.MemberService;
import domain.Member;
import forms.MemberRegistrationForm;

@Controller
@RequestMapping("/member")
public class MemberController extends AbstractController {

	@Autowired
	private MemberService	memberService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView crearMember() {
		ModelAndView result;
		final MemberRegistrationForm member = new MemberRegistrationForm();
		member.setId(0);
		member.setVersion(0);
		member.setAddress("");
		member.setEmail("");
		member.setMiddleName("");
		member.setName("");
		member.setPhoto("");
		member.setPhone("");
		member.setCheck(false);
		member.setSurname("");
		member.setPassword2("");
		final UserAccount userAccount = new UserAccount();
		userAccount.setUsername("");
		userAccount.setPassword("");
		member.setUserAccount(userAccount);
		result = new ModelAndView("member/create");
		result.addObject("memberRegistrationForm", member);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final MemberRegistrationForm memberRegistrationForm, final BindingResult binding) {
		ModelAndView result;
		Member member;
		try {
			member = this.memberService.reconstruct(memberRegistrationForm, binding);
			if (!binding.hasErrors()) {
				this.memberService.save(member);
				result = new ModelAndView("redirect:../");
			} else {
				result = new ModelAndView("member/create");
				result.addObject("memberRegistrationForm", memberRegistrationForm);
			}
		} catch (final Exception e) {
			result = new ModelAndView("member/create");
			result.addObject("exception", e);
			memberRegistrationForm.getUserAccount().setPassword("");
			result.addObject("memberRegistrationForm", memberRegistrationForm);

		}
		return result;
	}

}
