
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProcessionRepository;
import security.LoginService;
import domain.Brotherhood;
import domain.Procession;
import domain.Request;

@Service
@Transactional
public class ProcessionService {

	@Autowired
	private ProcessionRepository	processionRepository;
	@Autowired
	private BrotherhoodService		brotherhoodService;
	@Autowired
	private Validator				validator;


	public Procession create() {
		final Procession procession = new Procession();
		procession.setTicker("");
		procession.setTitle("");
		procession.setMoment(new Date());
		procession.setDescription("");
		procession.setDraftMode(1);
		procession.setPositionsRow(new ArrayList<Integer>());
		procession.setPositionsColumn(new ArrayList<Integer>());
		procession.setRequests(new HashSet<Request>());
		final Brotherhood brotherhood = new Brotherhood();
		procession.setBrotherhood(brotherhood);
		procession.setMaxRows(2);
		procession.setMaxColumns(3000);
		return procession;
	}
	public Procession findOne(final int processionId) {
		return this.processionRepository.findOne(processionId);
	}
	public Collection<Procession> findAll() {
		return this.processionRepository.findAll();
	}

	public Procession save(final Procession procession) {
		final Procession savedProcession;
		if (procession.getId() == 0) {
			Assert.isTrue(procession.getPositionsColumn().isEmpty() && procession.getPositionsRow().isEmpty());
			Assert.isTrue(!this.processionRepository.getAllTickers().contains(procession.getTicker()), "Used ticker");
		} else {
			Assert.isTrue(this.processionRepository.findOne(procession.getId()).getDraftMode() == 1);
			final Collection<Procession> allMyProcession = this.processionRepository.getAllProcessionsByBrotherhood(this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()).getId());
			Assert.isTrue(allMyProcession.contains(procession));
		}
		Assert.isTrue(procession.getTicker() != null && procession.getTicker() != "", "No valid procession. Ticker must'nt be blank or null");
		Assert.isTrue(procession.getDescription() != null && procession.getDescription() != "", "You need to provied a drescription.");
		Assert.isTrue(procession.getTitle() != null && procession.getTitle() != "", "You need to provied a title.");
		Assert.isTrue(procession.getDraftMode() == 0 || procession.getDraftMode() == 1, "Draft Mode only can be 0 or 1.");
		Assert.isTrue(procession.getBrotherhood().equals(this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId())), "Bad brother");

		savedProcession = this.processionRepository.save(procession);
		return savedProcession;
	}
	public void delete(final Procession procession) {
		if (procession.getDraftMode() == 1)
			this.processionRepository.delete(procession);
	}

	public static String generarTicker(final Date date) {
		final int tam = 5;
		final Integer ano = date.getYear() + 1900;
		final Integer mes = date.getMonth() + 1;
		final Integer dia = date.getDate();

		String day = dia.toString();
		String month = mes.toString();
		if (mes < 10)
			month = "0" + mes.toString();
		if (dia < 10)
			day = "0" + dia.toString();
		final String d = ano.toString().substring(ano.toString().length() - 2, ano.toString().length()) + month + day;

		String ticker = "-";
		final String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		for (int i = 0; i < tam; i++) {
			final Integer random = (int) (Math.floor(Math.random() * a.length()) % a.length());
			ticker = ticker + a.charAt(random);
		}

		return d + ticker;

	}

	public Collection<Procession> getAllProcessionsByBrotherhood(final int brotherhoodId) {
		return this.processionRepository.getAllProcessionsByBrotherhood(brotherhoodId);
	}
	public Collection<Procession> getAllProcessionsByBrotherhoodFinalMode(final int brotherhoodId) {
		return this.processionRepository.getAllProcessionsByBrotherhoodFinalMode(brotherhoodId);
	}

	//RECONSTRUCT
	public Procession reconstruct(final Procession procession, final BindingResult binding) {
		Procession res;

		if (procession.getId() == 0) {
			res = procession;
			res.setTicker(ProcessionService.generarTicker(new Date()));
			res.setBrotherhood(this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()));
			res.setPositionsColumn(new ArrayList<Integer>());
			res.setPositionsRow(new ArrayList<Integer>());
			res.setRequests(new HashSet<Request>());
			this.validator.validate(res, binding);
		} else {
			res = this.processionRepository.findOne(procession.getId());
			final Procession p = new Procession();
			p.setId(res.getId());
			p.setVersion(res.getVersion());
			p.setTicker(res.getTicker());
			p.setTitle(procession.getTitle());
			p.setMoment(procession.getMoment());
			p.setDescription(procession.getDescription());
			p.setDraftMode(procession.getDraftMode());
			p.setPositionsRow(res.getPositionsRow());
			p.setPositionsColumn(res.getPositionsColumn());
			p.setRequests(res.getRequests());
			p.setBrotherhood(res.getBrotherhood());
			p.setMaxRows(res.getMaxRows());
			p.setMaxColumns(res.getMaxColumns());
			this.validator.validate(p, binding);
			res = p;
		}
		return res;
	}
	public Collection<String> procession() {
		return this.processionRepository.processions();
	}
}
