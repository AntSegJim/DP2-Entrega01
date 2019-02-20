
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Float;

@Component
@Transactional
public class FloatToStringConverter implements Converter<Float, String> {

	@Override
	public String convert(final Float source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());
		return result;
	}
}
