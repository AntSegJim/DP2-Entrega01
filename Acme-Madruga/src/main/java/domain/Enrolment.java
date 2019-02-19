
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
@Access(AccessType.PROPERTY)
public class Enrolment extends DomainEntity {

	private Date		moment;
	private Member		member;
	private Brotherhood	brotherhood;

	private Position	position;


	@NotNull
	@Past
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@ManyToOne(optional = true)
	@Valid
	public Position getPosition() {
		return this.position;
	}
	public void setPosition(final Position position) {
		this.position = position;
	}

	@ManyToOne(optional = false)
	@Valid
	public Member getMember() {
		return this.member;
	}
	public void setMember(final Member member) {
		this.member = member;
	}

	@ManyToOne(optional = false)
	@Valid
	public Brotherhood getBrotherhooh() {
		return this.brotherhood;
	}
	public void setBroterhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}
}
