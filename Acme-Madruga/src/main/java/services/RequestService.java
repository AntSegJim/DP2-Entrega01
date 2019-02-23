
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import security.LoginService;
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
	private MemberService		memberService;


	public Request create() {
		final Request request = new Request();
		request.setColumna(0);
		request.setRow(0);
		request.setDescription("");
		request.setStatus(1);
		final Procession procession = new Procession();
		request.setProcession(procession);
		final Member member = new Member();
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
			final int userAccountId = LoginService.getPrincipal().getId();
			final Member member = this.memberService.getMemberByUserAccount(userAccountId);
			Assert.isTrue(member.getId() == request.getMember().getId());
			Assert.isTrue(request.getStatus() == 1, "RequestService. No valid new request. Status must be 1.");
			final Procession procession = this.processionService.findOne(request.getProcession().getId());
			Assert.notNull(procession, "RequestService. Procession no valid.");
			final int[][] position = procession.getPosition();
			for (int i = 0; i < position.length; i++)
				for (int j = 0; j < position[0].length; j++)
					if (position[i][j] == 0) {
						request.setRow(i);
						request.setColumna(j);
						break;
					}
		} else {
			final Request oldRequest = this.requestRepository.findOne(request.getId());
			Assert.isTrue(oldRequest.getStatus() == 1, "RequestService. You can only update request with status 1.");
			Assert.isTrue(request.getStatus() == 0 || request.getStatus() == 2, "No valid request. Status must be between 0 or 2.");
			Assert.isTrue(request.getProcession() == request.getProcession(), "RequestService. You can't change this request.");
			Assert.isTrue(request.getMember() == request.getMember(), "RequestService. This member didn't create this request.");
			if (request.getStatus() == 2)
				Assert.isTrue(request.getDescription() != null && !(request.getDescription() == ""), "RequestService. You need to write a description to rejected request.");
			try {
				final int[][] position = request.getProcession().getPosition();
				Assert.isTrue(position[request.getRow()][request.getColumna()] == 0);
			} catch (final Exception e) {
				request.setColumna(oldRequest.getColumna());
				request.setRow(oldRequest.getRow());
			}
		}
		Assert.isTrue(request.getColumna() >= 0 && request.getColumna() != null && request.getRow() >= 0 && request.getRow() != null, "RequestService. No valid request. Column or Row must be a integer bigger than -1");
		Assert.isTrue(request.getProcession() != null, "RequestService. You need to provied a procession in the request.");
		Assert.isTrue(request.getMember() != null, "RequestService. You need to provied a member in the request.");
		savedRequest = this.requestRepository.save(request);
		return savedRequest;
	}
	public void delete(final Request request) {
		if (request.getStatus() == 1)
			this.requestRepository.delete(request);
	}

}
