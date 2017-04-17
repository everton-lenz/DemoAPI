package br.com.sol7.orcamento.converter;


import br.com.sol7.orcamento.service.BaseService;
import br.com.sol7.orcamento.util.ReflectUtil;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.TypeMismatchException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.io.Serializable;

public abstract class BaseConverter<T, PK extends Serializable> implements Converter {

	private BaseService<T, PK> baseService;

	private Class<T> entityClass;

	public BaseConverter(BaseService<T, PK> baseService, Class<T> entityClass) {
		this.baseService = baseService;
		this.entityClass = entityClass;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if (arg2 == null || arg2.equals("")) {
			return null;
		}

		try {
			return baseService.findOne((PK) new SimpleTypeConverter().convertIfNecessary(arg2, ReflectUtil.getPrimaryKeyField(entityClass).getType()));
		} catch (TypeMismatchException e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) {
			return "";
		}

		return arg2.toString();

	}

}
