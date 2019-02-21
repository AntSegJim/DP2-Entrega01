
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PosicionRepository;
import domain.Posicion;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PosicionRepository	positionRepository;


	public Posicion create() {

		final Posicion posicion = new Posicion();
		posicion.setIdioma("");
		posicion.setName("");

		return posicion;

	}

	public Collection<Posicion> findAll() {
		return this.positionRepository.findAll();
	}

	public Posicion findOne(final Integer id) {
		return this.positionRepository.findOne(id);
	}

	public Posicion save(final Posicion p) {
		Posicion res;

		Assert.notNull(p.getIdioma() != null && p.getIdioma() != "", "No debe tener un idioma vacio");
		Assert.notNull(p.getName() != null && p.getName() != "", "No debe tener un nombre vacio");

		res = this.positionRepository.save(p);

		return res;

	}

	//	public void delete(final Posicion p) {
	//
	//		this.positionRepository.delete(p);
	//
	//	}

}
