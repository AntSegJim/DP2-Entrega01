
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.RequestRepository;
import domain.Member;
import domain.Procession;
import domain.Request;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository	requestRepository;


	public Request create() {
		final Request request = new Request();
		request.setColumn(0);
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

	}
	public void delete(final Request request) {
		this.requestRepository.delete(request);
	}

}
