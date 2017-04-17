package br.com.sol7.orcamento.util;

import br.com.sol7.orcamento.auth.LoggedUser;
import org.primefaces.context.RequestContext;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.Map;

/**
 * 
 * Classe utilitária utilizada para acessar atributos na sessão, entre muitas operações convenientes ao JSF.
 *
 * @author Marco Noronha
 *
 */
public class JSFUtil {


	/**
	 * Guarda o nome da chave do atributo da sessão referente ao UsuarioLogado.
	 */
	private static final String USUARIO_LOGADO = "usuarioLogado";
	
	/**
	 * @return Retorna Usuario Logado
	 */
	public static LoggedUser getUsuarioLogado() {
		return (LoggedUser) getFromSessionMap(USUARIO_LOGADO);
	}

	/**
	 * Lock loading usar unblock.
	 * @param
	 */
    public static void blockWaiting(){
        RequestContext.getCurrentInstance().execute("PF('blockUI').block();");
    }

    public static void unblockWaiting(){
        RequestContext.getCurrentInstance().execute("PF('blockUI').unblock();");
    }

	public static void setUsuarioLogado(LoggedUser loggedUser){
		addToSessionMap(USUARIO_LOGADO, loggedUser);
	}

	public static void addToSessionMap(String key, Object value) {
		getSessionMap().put(key, value);
	}

	public static Object getFromSessionMap(String key) {
		return getSessionMap().get(key);
	}

	public static Map<String, Object> getSessionMap() {
		return getExternalContext().getSessionMap();
	}

	public static void invalidateSession() {
		getExternalContext().invalidateSession();
	}

	public static ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

}
