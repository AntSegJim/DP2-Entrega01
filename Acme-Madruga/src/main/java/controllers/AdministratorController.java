/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.BrotherhoodService;
import services.CustomizableSystemService;
import services.PositionService;
import services.ProcessionService;
import services.RequestService;
import domain.Administrator;
import forms.RegistrationFormAdmin;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private PositionService				positionService;

	@Autowired
	private ProcessionService			processionService;

	@Autowired
	private BrotherhoodService			brotherhoodService;

	@Autowired
	private AdministratorService		administratorService;

	@Autowired
	private RequestService				requestService;

	@Autowired
	private CustomizableSystemService	customizableService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView result;

		final Collection<String> procession = this.processionService.procession();

		final List<Object[]> memberBrotherhood = this.brotherhoodService.getMaxMinAvgDesvMembersBrotherhood();
		final Double memberBrotherhoodAvg = (Double) memberBrotherhood.get(0)[0];
		final Double memberBrotherhoodMin = (Double) memberBrotherhood.get(0)[1];
		final Double memberBrotherhoodMax = (Double) memberBrotherhood.get(0)[2];
		final Double memberBrotherhoodDesv = (Double) memberBrotherhood.get(0)[3];

		final Collection<String> largestBrotherhoods = this.brotherhoodService.getLargestBrotherhoods();
		final Collection<String> smallestBrotherhoods = this.brotherhoodService.getSmallestBrotherhoods();

		result = new ModelAndView("administrator/dashboard");
		result.addObject("procession", procession);

		result.addObject("memberBrotherhoodAvg", memberBrotherhoodAvg);
		result.addObject("memberBrotherhoodMin", memberBrotherhoodMin);
		result.addObject("memberBrotherhoodMax", memberBrotherhoodMax);
		result.addObject("memberBrotherhoodDesv", memberBrotherhoodDesv);

		result.addObject("largestBrotherhoods", largestBrotherhoods);
		result.addObject("smallestBrotherhoods", smallestBrotherhoods);

		result.addObject("ratioPendingRequest", this.requestService.pendingRatio());
		result.addObject("ratioAcceptedRequest", this.requestService.acceptedRatio());
		result.addObject("ratioRejectedRequest", this.requestService.rejectedRatio());

		return result;
	}

	@RequestMapping("/graph")
	public ModelAndView graph() {

		ModelAndView result;
		Map<String, Double> statistics;

		statistics = this.positionService.computeStatistics();

		result = new ModelAndView("administrator/bar-chart");
		result.addObject("statistics", statistics);

		return result;

	}

	//	@RequestMapping(value = "/create", method = RequestMethod.GET)
	//	public ModelAndView action1() {
	//		ModelAndView result;
	//		final Administrator administrator;
	//
	//		administrator = this.administratorService.create();
	//
	//		result = new ModelAndView("administrator/create");
	//		result.addObject("administrator", administrator);
	//
	//		return result;
	//	}
	//
	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView edit(@Valid final Administrator administrator, final BindingResult binding) {
	//		ModelAndView result;
	//		try {
	//			if (!binding.hasErrors()) {
	//				this.administratorService.save(administrator);
	//				result = new ModelAndView("redirect:https://localhost:8443/Acme-Madruga");
	//			} else {
	//				result = new ModelAndView("administrator/create");
	//				result.addObject("administrator", administrator);
	//			}
	//		} catch (final Exception e) {
	//			result = new ModelAndView("administrator/create");
	//			result.addObject("exception", e);
	//			result.addObject("administrator", administrator);
	//		}
	//
	//		return result;
	//	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView result;
		RegistrationFormAdmin registrationForm = new RegistrationFormAdmin();

		registrationForm = registrationForm.createToAdmin();
		final String telephoneCode = this.customizableService.getTelephoneCode();
		registrationForm.setPhone(telephoneCode + " ");

		result = new ModelAndView("administrator/create");
		result.addObject("registrationForm", registrationForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegistrationFormAdmin registrationForm, final BindingResult binding) {
		ModelAndView result;
		Administrator administrator = null;

		try {

			administrator = this.administratorService.reconstruct(registrationForm, binding);
			if (!binding.hasErrors() && registrationForm.getUserAccount().getPassword().equals(registrationForm.getPassword())) {
				this.administratorService.save(administrator);
				result = new ModelAndView("redirect:https://localhost:8443/Acme-Madruga");
			} else {

				result = new ModelAndView("administrator/create");
				result.addObject("administrator", administrator);
			}
		} catch (final Exception e) {
			result = new ModelAndView("administrator/create");
			result.addObject("exception", e);
			result.addObject("administrator", administrator);

		}

		return result;
	}

}
