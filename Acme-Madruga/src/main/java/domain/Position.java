
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Position extends DomainEntity {

	private String	name;
	private String	idioma;


	@NotNull
	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	@NotBlank
	public String getIdioma() {
		return this.idioma;
	}

	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

}
