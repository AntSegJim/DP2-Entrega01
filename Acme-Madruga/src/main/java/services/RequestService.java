
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import security.LoginService;
import domain.Brotherhood;
import domain.Member;
import domain.Procession;
import domain.Request;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository	requestRepository;
	@Autowired
	private ProcessionService	processionService;
	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private MemberService		memberService;
	@Autowired
	private Validator			validator;


	public Request create() {
		final Request request = new Request();
		request.setColumna(0);
		request.setRow(0);
		request.setDescription("");
		request.setStatus(1);
		final Procession procession = new Procession();
		request.setProcession(procession);
		final int userAccountId = LoginService.getPrincipal().getId();
		final Member member = this.memberService.getMemberByUserAccount(userAccountId);
		request.setMember(member);
		return request;
	}
	public Request findOne(final int requestId) {
		return this.requestRepository.findOne(requestId);
	}
	public Collection<Request> findAll() {
		return this.requestRepository.findAll();
	}
	public Request save(final Request request) {
		final Request savedRequest;
		if (request.getId() == 0) {
			Assert.isTrue(request.getStatus() == 1, "RequestService. No valid new request. Status must be 1.");
			final int userAccountId = LoginService.getPrincipal().getId();
			final Member member = this.memberService.getMemberByUserAccount(userAccountId);
			final Collection<Brotherhood> brotherhoods = this.brotherhoodService.getBrotherhoodsByMember(member.getId());
			final Set<Brotherhood> brotherhoodsWithOutDuplicates = new HashSet<Brotherhood>(brotherhoods);
			Assert.isTrue(brotherhoodsWithOutDuplicates.contains(request.getProcession().getBrotherhood()), "RequestService. You only can use a procession of your brotherhood");
			Assert.isTrue(request.getProcession() != null && request.getProcession().getDraftMode() == 0, "RequestService. You need to provied a procession in the request.");
			Assert.isTrue(request.getMember() != null, "RequestService. You need to provied a member in the request.");
		} else {
			final Request oldRequest = this.requestRepository.findOne(request.getId());
			Assert.isTrue(oldRequest.getStatus() == 1, "RequestService. You can only update request with status 1.");
			Assert.isTrue(request.getStatus() == 0 || request.getStatus() == 2, "No valid request. Status must be between 0 or 2.");
			Assert.isTrue(request.getProcession() == oldRequest.getProcession(), "RequestService. You can't change this request.");
			Assert.isTrue(request.getMember() == oldRequest.getMember(), "RequestService. This member didn't create this request.");
			Assert.isTrue(request.getMember() != null, "RequestService. This member didn't create this request.");
			Assert.isTrue(request.getProcession() != null, "RequestService. You need to provied a procession in the request.");
			if (request.getStatus() == 2)
				Assert.isTrue(request.getDescription() != null && !(request.getDescription() == ""), "RequestService. You need to write a description to rejected request.");
			if (request.getStatus() == 0) {

				final Request comprobrarRowAndColumn = this.requestRepository.getRequestWithThisRowAndColumn(request.getRow(), request.getColumna());
				Assert.isNull(comprobrarRowAndColumn);
				Assert.isTrue(request.getColumna() >= 0 && request.getColumna() != null && request.getRow() >= 0 && request.getRow() != null, "RequestService. No valid request. Column or Row must be a integer bigger than -1");
			}
		}

		savedRequest = this.requestRepository.save(request);
		if (request.getId() == 0) {
			final Member miembroRequest = this.memberService.getMemberByUserAccount(LoginService.getPrincipal().getId());
			final Collection<Request> coleccion = miembroRequest.getRequests();
			coleccion.add(savedRequest);
			miembroRequest.setRequests(coleccion);
			this.memberService.save(miembroRequest);
		} else {
			final Procession p = this.processionService.findOne(savedRequest.getProcession().getId());
			final List<Integer> r = p.getPositionsRow();
			r.add(savedRequest.getRow());
			final List<Integer> c = p.getPositionsColumn();
			c.add(savedRequest.getColumna());
			p.setPositionsRow(r);
			p.setPositionsColumn(c);
			this.processionService.save(p);
		}
		return savedRequest;
	}
	public void delete(final Request request) {
		Assert.isTrue(request.getStatus() == 1);
		final int userAccountId = LoginService.getPrincipal().getId();
		final Member member = this.memberService.getMemberByUserAccount(userAccountId);
		final Collection<Brotherhood> brotherhoods = this.brotherhoodService.getBrotherhoodsByMember(member.getId());
		final Set<Brotherhood> brotherhoodsWithOutDuplicates = new HashSet<Brotherhood>(brotherhoods);
		Assert.isTrue(brotherhoodsWithOutDuplicates.contains(request.getProcession().getBrotherhood()));
		this.requestRepository.delete(request);
	}

	//RECONSTRUCT
	public Request reconstruct(final Request request, final BindingResult binding) {
		Request res;

		if (request.getId() == 0) {
			res = request;
			res.setDescription("");
			res.setMember(this.memberService.getMemberByUserAccount(LoginService.getPrincipal().getId()));
			final Procession procession = this.processionService.findOne(request.getProcession().getId());
			Assert.notNull(procession, "RequestService. Procession no valid.");
			//			Boolean recomendacionEncontrada = false;
			//			Integer recomendar = 0;
			//			final List<Integer> filas = procession.getPositionsRow();
			//			final List<Integer> columnas = procession.getPositionsColumn();
			//
			//			for (int xy = 0; xy < filas.size(); xy++) {
			//				if (recomendar < filas.get(xy))
			//					recomendar = filas.get(xy);
			//				if (recomendar < columnas.get(xy))
			//					recomendar = columnas.get(xy);
			//			}
			//			while (recomendacionEncontrada == false) {
			//				recomendar += 1;
			//				final Request comprobrarRowAndColumn = this.requestRepository.getRequestWithThisRowAndColumn(recomendar, recomendar);
			//				if (comprobrarRowAndColumn == null) {
			//					recomendacionEncontrada = true;
			//					res.setRow(recomendar);
			//					res.setColumna(recomendar);
			//				}
			//			}
			res.setStatus(1);

			this.validator.validate(res, binding);
			return res;
		} else {
			res = this.requestRepository.findOne(request.getId());
			final Request p = new Request();
			p.setId(res.getId());
			p.setVersion(res.getVersion());
			p.setMember(res.getMember());
			p.setProcession(res.getProcession());
			p.setStatus(request.getStatus());
			if (request.getStatus() == 0) {
				final Procession procession = this.processionService.findOne(res.getProcession().getId());
				final List<Integer> filas = procession.getPositionsRow();
				final List<Integer> columnas = procession.getPositionsColumn();
				for (int xy = 0; xy < filas.size(); xy++)
					if (filas.get(xy) == request.getRow() && columnas.get(xy) == request.getColumna())
						Assert.notNull(null);
				Assert.notNull(request.getRow());
				Assert.notNull(request.getColumna());
				p.setRow(request.getRow());
				p.setColumna(request.getColumna());
			} else if (request.getStatus() == 2) {
				p.setDescription(request.getDescription());
				Assert.isTrue(p.getDescription() != null && p.getDescription() != "");
				p.setColumna(null);
				p.setRow(null);
			} else
				Assert.notNull(null);
			this.validator.validate(p, binding);
			return p;
		}

	}
	public Collection<Request> getAllMyRequest(final int memberId) {
		return this.requestRepository.getAllMyRequest(memberId);
	}
	public Request getRequestWithThisRowAndColumn(final Integer recomendar, final Integer recomendar2) {
		return this.requestRepository.getRequestWithThisRowAndColumn(recomendar, recomendar2);
	}

	public Double pendingRatio() {
		return this.requestRepository.pendingRatio();
	}

	public Double acceptedRatio() {
		return this.requestRepository.acceptedRatio();
	}

	public Double rejectedRatio() {
		return this.requestRepository.rejectedRatio();
	}

}
