
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Enrolment extends DomainEntity {

	private Date		moment;
	private Date		endMoment;
	private int			status;

	private Member		member;
	private Brotherhood	brotherhood;

	private Posicion	position;


	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndMoment() {
		return this.endMoment;
	}

	public void setEndMoment(final Date endMoment) {
		this.endMoment = endMoment;
	}

	@Range(min = 0, max = 2)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}
	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Posicion getPosition() {
		return this.position;
	}
	public void setPosition(final Posicion position) {
		this.position = position;
	}

	@NotNull
	@ManyToOne(optional = false)
	@Valid
	public Member getMember() {
		return this.member;
	}
	public void setMember(final Member member) {
		this.member = member;
	}

	@NotNull
	@ManyToOne(optional = false)
	@Valid
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

}
