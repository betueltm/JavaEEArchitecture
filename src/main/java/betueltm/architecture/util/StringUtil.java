package betueltm.architecture.util;

import java.util.Objects;

public class StringUtil {

	public static boolean notEmpty(String value) {
		return Objects.nonNull(value) && !value.isEmpty();
	}

	public static boolean isBlank(String value) {
		return Objects.isNull(value) || value.isBlank();
	}
}
