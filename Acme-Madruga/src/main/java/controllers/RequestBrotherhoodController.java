
package controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BrotherhoodService;
import services.ProcessionService;
import services.RequestService;
import domain.Procession;
import domain.Request;

@Controller
@RequestMapping("/request/brotherhood")
public class RequestBrotherhoodController extends AbstractController {

	@Autowired
	private RequestService		requestService;
	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private ProcessionService	processionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int processionId) {
		ModelAndView result;
		try {
			final Collection<Request> requests;
			final Procession procession = this.processionService.findOne(processionId);
			requests = procession.getRequests();
			final int brotherhoodId = this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()).getId();
			final Collection<Procession> processions = this.processionService.getAllProcessionsByBrotherhood(brotherhoodId);
			if (processions.contains(procession)) {
				result = new ModelAndView("request/list");
				result.addObject("requests", requests);
			} else
				result = new ModelAndView("redirect:procession/brotherhood/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:procession/brotherhood/list.do");
		}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int processionId, @RequestParam final int requestId, @RequestParam final int status) {
		ModelAndView result;

		try {
			final Request request;
			final Collection<Request> requests;
			final Procession procession = this.processionService.findOne(processionId);
			requests = procession.getRequests();
			final int brotherhoodId = this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()).getId();
			final Collection<Procession> processions = this.processionService.getAllProcessionsByBrotherhood(brotherhoodId);
			request = this.requestService.findOne(requestId);
			Assert.notNull(request);
			Assert.isTrue(processions.contains(procession) && requests.contains(request));
			result = new ModelAndView("request/edit");
			result.addObject("request", request);
			if (status == 0) {
				result.addObject("status", status);
				Boolean recomendacionEncontrada = false;
				Integer recomendar = 0;
				final List<Integer> filas = procession.getPositionsRow();
				final List<Integer> columnas = procession.getPositionsColumn();

				for (int xy = 0; xy < filas.size(); xy++) {
					if (recomendar < filas.get(xy))
						recomendar = filas.get(xy);
					if (recomendar < columnas.get(xy))
						recomendar = columnas.get(xy);
				}
				while (recomendacionEncontrada == false) {
					recomendar += 1;
					final Request comprobrarRowAndColumn = this.requestService.getRequestWithThisRowAndColumn(recomendar, recomendar);
					if (comprobrarRowAndColumn == null) {
						recomendacionEncontrada = true;
						request.setRow(recomendar);
						request.setColumna(recomendar);
					}
				}
			} else if (status == 2)
				result.addObject("status", status);
			else
				Assert.isTrue(false);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:procession/brotherhood/list.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(Request newRequest, final BindingResult binding, @RequestParam int status) {
		ModelAndView result;

		newRequest.setStatus(status);
		try {
			newRequest = this.requestService.reconstruct(newRequest, binding);
			final Procession procession = this.processionService.findOne(newRequest.getProcession().getId());
			final int brotherhoodId = this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()).getId();
			final Collection<Procession> processions = this.processionService.getAllProcessionsByBrotherhood(brotherhoodId);
			if (!processions.contains(procession)) {
				status = -1;
				Assert.notNull(null);
			}
			try {
				if (!binding.hasErrors()) {
					this.requestService.save(newRequest);
					result = new ModelAndView("redirect:list.do?processionId=" + newRequest.getProcession().getId());
				} else
					result = new ModelAndView("redirect:list.do?processionId=" + newRequest.getProcession().getId());
			} catch (final Exception e) {
				result = new ModelAndView("redirect:list.do?processionId=" + newRequest.getProcession().getId());
			}
		} catch (final Exception e) {
			if (status == 0) {
				result = new ModelAndView("request/edit");
				result.addObject("request", newRequest);
				result.addObject("status", 0);
				result.addObject("exception", 0);
			} else if (status == 2) {
				result = new ModelAndView("request/edit");
				result.addObject("request", newRequest);
				result.addObject("status", 2);
				result.addObject("exception", 2);
			} else
				result = new ModelAndView("redirect:procession/brotherhood/list.do");
		}
		return result;

	}
}
