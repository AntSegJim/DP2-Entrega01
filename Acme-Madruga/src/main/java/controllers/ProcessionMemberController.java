
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import services.ProcessionService;
import domain.Procession;

@Controller
//@RestController()
@RequestMapping("/procession/member")
public class ProcessionMemberController extends AbstractController {

	private ProcessionService	processionService;


	@RequestMapping(value = "/list2", method = RequestMethod.GET)
	public ResponseEntity<String> list2(@RequestParam final int brotherhoodId) {
		final List<String> a = new ArrayList<String>();
		a.add("procession1:1201");
		a.add("procession2:1202");
		a.add("hermandad3:1203");
		a.add("hermandad4:1204");
		String processions = "";
		for (int x = 0; x < a.size(); x++)
			if (x == 0)
				processions += a.get(x);
			else
				processions += ";" + a.get(x);
		return new ResponseEntity<String>(processions, HttpStatus.OK);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<String> list(@RequestParam final int brotherhoodId) {
		try {
			final Collection<Procession> p = this.processionService.getProcessionsByBrotherhood(brotherhoodId);
			final List<Procession> a = new ArrayList<Procession>(p);
			String processions = "";
			for (int x = 0; x < a.size(); x++)
				if (x == 0)
					processions += a.get(x).getTitle() + ":" + a.get(x).getId();
				else
					processions += ";" + a.get(x).getTitle() + ":" + a.get(x).getId();
			return new ResponseEntity<String>(processions, HttpStatus.OK);
		} catch (final Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
