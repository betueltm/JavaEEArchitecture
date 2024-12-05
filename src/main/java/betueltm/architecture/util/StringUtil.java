package betueltm.architecture.util;

import java.util.Objects;

public class StringUtil {

	public static boolean notEmpty(String value) {
		return Objects.nonNull(value) && !value.isEmpty();
	}
}
