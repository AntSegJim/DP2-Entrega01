
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Procession extends DomainEntity {

	private int[][]				position;
	private String				ticker;
	private Date				moment;
	private String				description;
	private String				title;
	private int					draftMode;
	private Collection<Request>	requests;
	private Brotherhood			brotherhood;


	@Pattern(regexp = "^[0-9]{6}\\-[A-Z]{5}$")
	@Column(unique = true)
	@NotNull
	@NotBlank
	public String getTicker() {
		return this.ticker;
	}
	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	//@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@NotNull
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@Range(min = 0, max = 1)
	public int getDraftMode() {
		return this.draftMode;
	}
	public void setDraftMode(final int draftMode) {
		this.draftMode = draftMode;
	}

	@OneToMany(mappedBy = "procession")
	@Valid
	public Collection<Request> getRequests() {
		return this.requests;
	}

	public void setRequests(final Collection<Request> requests) {
		this.requests = requests;
	}

	@ManyToOne(optional = false)
	@Valid
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}
	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

	public int[][] getPosition() {
		return this.position;
	}

	public void setPosition(final int[][] position) {
		this.position = position;
	}

}
