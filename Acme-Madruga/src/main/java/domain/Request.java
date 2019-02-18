
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity {

	private int		status;
	private Integer	column;
	private Integer	row;
	private String	description;
	private Member	member;


	@Range(min = 0, max = 2)
	public int getStatus() {
		return this.status;
	}
	public void setStatus(final int status) {
		this.status = status;
	}

	@Min(0)
	public Integer getColumn() {
		return this.column;
	}
	public void setColumn(final Integer column) {
		this.column = column;
	}

	@Min(0)
	public Integer getRow() {
		return this.row;
	}
	public void setRow(final Integer row) {
		this.row = row;
	}

	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@ManyToOne(optional = false)
	@Valid
	public Member getMember() {
		return this.member;
	}
	public void setMember(final Member member) {
		this.member = member;
	}
}
