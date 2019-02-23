
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BrotherhoodService;
import services.MemberService;
import services.RequestService;
import domain.Brotherhood;
import domain.Member;
import domain.Request;

@Controller
@RequestMapping("/request/member")
public class RequestMemberController extends AbstractController {

	@Autowired
	private RequestService		requestService;
	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private MemberService		memberService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Request> requests;
		requests = this.requestService.findAll();

		result = new ModelAndView("request/list");
		result.addObject("requests", requests);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Request request;

		request = this.requestService.create();
		final Integer a = LoginService.getPrincipal().getId();
		final Member member = this.memberService.getMemberByUserAccount(a);
		final Collection<Brotherhood> brotherhoods = this.brotherhoodService.getBrotherhoodsByMember(member.getId());
		result = new ModelAndView("request/edit");
		result.addObject("request", request);
		result.addObject("brotherhoods", brotherhoods);
		return result;
	}
}
