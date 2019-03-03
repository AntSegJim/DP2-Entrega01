
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MemberService;
import domain.Member;

@Controller
@RequestMapping("/member")
public class MemberController extends AbstractController {

	@Autowired
	private MemberService	memberService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView crearMember() {
		ModelAndView result;
		final Member member;
		member = this.memberService.create();
		result = new ModelAndView("member/create");
		result.addObject("member", member);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Member member, final BindingResult binding) {
		ModelAndView result;
		try {
			if (!binding.hasErrors()) {
				this.memberService.save(member);
				result = new ModelAndView("redirect:https://localhost:8443/Acme-Madruga");
			} else {
				result = new ModelAndView("member/create");
				result.addObject("member", member);
			}
		} catch (final Exception e) {
			result = new ModelAndView("member/create");
			result.addObject("exception", e);
			member.getUserAccount().setPassword("");
			result.addObject("member", member);
		}

		return result;
	}

	@RequestMapping(value = "/list-All", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int idBrotherhood) {
		final ModelAndView result;
		final Collection<Member> members;

		members = this.memberService.getMemberByBrotherhood(idBrotherhood);

		result = new ModelAndView("member/list-All");
		result.addObject("members", members);
		result.addObject("idBrotherhood", idBrotherhood);

		return result;

	}

}
