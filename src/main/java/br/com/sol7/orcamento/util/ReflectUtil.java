package br.com.sol7.orcamento.util;

import org.springframework.beans.BeanWrapperImpl;

import javax.persistence.Id;
import java.lang.reflect.Field;

public class ReflectUtil {


	public static <T> Object getFieldValue(T obj, String field) throws Exception {
		Object value = null;
		value = new BeanWrapperImpl(obj).getPropertyValue(field);
		return value;
	}
	
	public static Field getPrimaryKeyField(Class<?> type) {
        String className = type.getSimpleName();
        while(type!= null) {
            for (Field f : type.getDeclaredFields()) {
                if (f.isAnnotationPresent(Id.class)) {
                    return f;
                }
            }
            type = type.getSuperclass();
        }
		throw new IllegalArgumentException("Não foi encontrado nenhum mapeamento @Id na classe " + className);
	}

}
