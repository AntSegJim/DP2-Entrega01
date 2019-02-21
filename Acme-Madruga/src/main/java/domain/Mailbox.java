
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Mailbox extends DomainEntity {

	private Actor						actor;
	private Collection<Notification>	notifications;


	@Valid
	@OneToOne(optional = false)
	@NotNull
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

	@Valid
	@ManyToMany
	public Collection<Notification> getNotifications() {
		return this.notifications;
	}

	public void setNotifications(final Collection<Notification> notifications) {
		this.notifications = notifications;
	}

}
