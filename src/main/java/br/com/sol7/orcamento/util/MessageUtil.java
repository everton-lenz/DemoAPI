package br.com.sol7.orcamento.util;


import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ResourceBundle;

@Component
public class MessageUtil implements Serializable {

	private static final long serialVersionUID = 8998324938535942009L;

	public enum MessageUtilType{
		INFO,ERROR;
	}


	public void sendExceptionMessageToUser(ServiceException e) {
		FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage());
		addMessageToJsfContext(facesMessage);
	}

	public void sendMessageToUser(MessageUtilType type, String keyTitulo, String keyConteudo, String aditicionalInformation, Object... params) {
		if(type == null){
			String txt = "O Tipo da mensagem não pode ser nula!";
			throw new IllegalArgumentException(txt);
		}
		
		if(aditicionalInformation == null){
			aditicionalInformation = "";
		}else{
			aditicionalInformation = " "+ aditicionalInformation;
		}
		
		String content;
		
		if(!ObjectUtil.nullOrEmpty(params)){
			content = MessageFormat.format(getMessageBundle(keyConteudo), params);
		}else{
			content = getMessageBundle(keyConteudo);
		}

		Severity severity = MessageUtilType.INFO.equals(type) ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_ERROR;

		FacesMessage facesMessage = createMessage(severity, getMessageBundle(keyTitulo), content+aditicionalInformation);
		try {
			addMessageToJsfContext(facesMessage);
			//redirect - removido para evitar duplicação de mensagens.
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
		}catch (Exception e){
		}
	}

	public void sendSimpleMessage(String msg, Boolean error){
		if(error){
			sendMessageToUser(MessageUtil.MessageUtilType.ERROR, "global.error", "validation", msg);
		}else{
			sendMessageToUser(MessageUtil.MessageUtilType.INFO, "global.sucess", "validation", msg);
		}
	}

	public String getMessageBundle(String key) {
		return ResourceBundle.getBundle("mensagens").getString(key);
	}

	private FacesMessage createMessage(Severity severity, String titulo, String conteudo) {
		return new FacesMessage(severity, titulo, conteudo);
	}

	private void addMessageToJsfContext(FacesMessage facesMessage) {
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
}