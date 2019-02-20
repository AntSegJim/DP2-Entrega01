
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.FloatRepository;
import domain.Float;

@Component
@Transactional
public class StringToFloatConverter implements Converter<String, Float> {

	@Autowired
	private FloatRepository	floatRepository;


	@Override
	public Float convert(final String source) {

		Float paso;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				paso = null;
			else {
				id = Integer.valueOf(source);
				paso = this.floatRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return paso;
	}
}
