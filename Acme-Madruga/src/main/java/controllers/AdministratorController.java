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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.PositionService;
import services.ProcessionService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private PositionService		positionService;

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


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

		result = new ModelAndView("administrator/dashboard");
		result.addObject("procession", procession);

		result.addObject("memberBrotherhoodAvg", memberBrotherhoodAvg);
		result.addObject("memberBrotherhoodMin", memberBrotherhoodMin);
		result.addObject("memberBrotherhoodMax", memberBrotherhoodMax);
		result.addObject("memberBrotherhoodDesv", memberBrotherhoodDesv);

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

}
