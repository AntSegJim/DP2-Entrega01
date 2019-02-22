
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
}
