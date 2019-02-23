
package forms;

import javax.validation.constraints.NotNull;

import domain.Brotherhood;
import domain.DomainEntity;
import domain.Posicion;

public class EnrolmentMember extends DomainEntity {

	private Brotherhood	brotherhood;
	private Posicion	position;


	@NotNull
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}
	@NotNull
	public Posicion getPosition() {
		return this.position;
	}

	public void setPosition(final Posicion position) {
		this.position = position;
	}

}
