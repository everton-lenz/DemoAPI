package br.com.sol7.orcamento.controller;


import br.com.sol7.orcamento.model.IModel;
import br.com.sol7.orcamento.service.BaseService;
import br.com.sol7.orcamento.service.ModuleService;
import br.com.sol7.orcamento.spring.SpringApplicationContextSupport;
import br.com.sol7.orcamento.util.MessageUtil;
import br.com.sol7.orcamento.util.ObjectUtil;

import javax.faces.event.ActionEvent;
import java.io.Serializable;


public abstract class BaseController<T extends IModel, PK extends Serializable> {

	private T entity;

	private Class<T> entityClass;

	private String selectedFilter;

	private String helper;

	private Boolean bHelper = false;

	private MessageUtil messageUtil;

	public BaseController(Class<T> clazz) {
		super();
		try {
			this.entity = clazz.newInstance();
			messageUtil = SpringApplicationContextSupport.getInstance().getBean(MessageUtil.class);
			this.entityClass = clazz;
			loadHelper();
			reset();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	public void loadHelper(){
		ModuleService moduleService = SpringApplicationContextSupport.getInstance().getBean(ModuleService.class);
		String help = moduleService.getRepository().findHelper(getListPath());
		if(!ObjectUtil.nullOrEmpty(help)){
			helper = help;
			bHelper = true;
		}else{
			bHelper = false;
		}
	}


	public abstract String getFormPath();

	public abstract String getListPath();

	public abstract BaseService<T, PK> getBaseService();

	public boolean isNewEntity() {
		return ObjectUtil.nullOrEmpty(entity.getId());
	}

	public void insert(ActionEvent er) {
		try {
			reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String save() {

		try {
			getBaseService().save(entity);
			reset();
			return getListPath();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void delete() {
		try {
			getBaseService().delete(entity);
			getMessageUtil().sendMessageToUser(MessageUtil.MessageUtilType.INFO, "global.sucess", "global.delete.sucess", null, entityClass.getSimpleName());
		} catch (Exception e) {
			getMessageUtil().sendMessageToUser(MessageUtil.MessageUtilType.ERROR, "global.error", "global.delete.error", null, entityClass.getSimpleName());
			e.printStackTrace();
		}
	}

	public void reset() throws Exception {
		entity = (T) entity.getClass().newInstance();
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}


	public MessageUtil getMessageUtil() {
		return messageUtil;
	}

	public void setMessageUtil(MessageUtil messageUtil) {
		this.messageUtil = messageUtil;
	}

	public String getHelper() {
		return helper;
	}

	public void setHelper(String helper) {
		this.helper = helper;
	}

	public Boolean getbHelper() {
		return bHelper;
	}

	public void setbHelper(Boolean bHelper) {
		this.bHelper = bHelper;
	}
}