package betueltm.architecture.cache;

import java.lang.reflect.Field;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

import betueltm.architecture.util.StringUtil;

public class CacheKeyParser {
	
	public static Object parseKeyFromProcedingJointPoint(String keyPattern, ProceedingJoinPoint proceedingJoinPoint) {
		if(StringUtil.isBlank(keyPattern)) return null;
		
		if(keyPattern.contains(".")) {
			return parseFromCustomType(keyPattern, proceedingJoinPoint);
		}else {
			return parseFromNormalType(keyPattern, proceedingJoinPoint);
		}
	}

	private static Object parseFromCustomType(String keyPattern, ProceedingJoinPoint proceedingJoinPoint) {
		CodeSignature signature = (CodeSignature) proceedingJoinPoint.getSignature();
		Object[] args = proceedingJoinPoint.getArgs();
		
		String[] keyPatternSplited = keyPattern.split("\\.");
		String parameterName = keyPatternSplited[0];
		String fieldName = keyPatternSplited[1];
		
		String[] parameterNames = signature.getParameterNames();
		Object parameterObject = null;
		
		for (int index = 0; index < parameterNames.length; index++) {
			if(parameterNames[index].equals(parameterName)) {
				parameterObject = args[index];
				break;
			}
		}
		
		try {
			Field declaredField = parameterObject.getClass().getDeclaredField(fieldName);
			declaredField.setAccessible(true);
			return declaredField.get(parameterObject);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private static Object parseFromNormalType(String keyPattern, ProceedingJoinPoint proceedingJoinPoint) {
		CodeSignature signature = (CodeSignature) proceedingJoinPoint.getSignature();
		Object[] args = proceedingJoinPoint.getArgs();
		String[] parameterNames = signature.getParameterNames();
		
		for (int index = 0; index < parameterNames.length; index++) {
			if(parameterNames[index].equals(keyPattern)) return args[index];
		}
		return null;
	}
}
