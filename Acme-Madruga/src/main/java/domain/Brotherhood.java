
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Brotherhood extends Actor {

	private String					title;
	private Date					establishmentDate;
	private Collection<Picture>		pictures;
	private Collection<Enrolment>	enrolments;


	@NotNull
	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEstablishmentDate() {
		return this.establishmentDate;
	}
	public void setEstablishmentDate(final Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	@OneToMany
	@Valid
	public Collection<Picture> getPictures() {
		return this.pictures;
	}
	public void setPictures(final Collection<Picture> pictures) {
		this.pictures = pictures;
	}

	@OneToMany
	@Valid
	public Collection<Enrolment> getEnrolments() {
		return this.enrolments;
	}
	public void setEnrolments(final Collection<Enrolment> enrolments) {
		this.enrolments = enrolments;
	}
}
