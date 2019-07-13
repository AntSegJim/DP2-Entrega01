
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Picture extends DomainEntity {

	private String	url;


	@URL
	@NotNull
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getUrl() {
		return this.url;
	}
	public void setUrl(final String url) {
		this.url = url;
	}

}
