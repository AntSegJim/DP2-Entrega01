
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Picture extends DomainEntity {

	private String	picture;


	@URL
	@NotNull
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}

}
