package br.com.sol7.orcamento.auth;

import br.com.sol7.orcamento.util.JSFUtil;
import br.com.sol7.orcamento.util.ObjectUtil;

import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import java.util.Map;

/**
 * Listener que vai tratar do controle de acesso .
 * 
 * Algumas regras para o projeto filho:
 * - Página de erro padrão:
 *          /view/error/error.xhtml
 * - Página de login deve conter a seguinte String no path:
 *          login
 * - Existir uma página padrão chamada
 *          default.xhtml
 *
 */
public class AuthorizesListener implements PhaseListener {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Trata páginas disponíveis, por exemplo.: Páginas de erro, página do login
	 * @param pagina Página que quer acesso
	 * @return true caso tenha acesso.
	 */
	protected boolean ignoredPage(String page){
		String[] ignorados = {"error","default","changePassword", "analysis"};
		for(String ig : ignorados){
			if(page.contains(ig)){
				return true;
			}
		}
		return false;
	}
	
	public void afterPhase(PhaseEvent event) {
		try {

			FacesContext facesContext = event.getFacesContext();
			String currentPage = facesContext.getViewRoot().getViewId();
			NavigationHandler nh = facesContext.getApplication().getNavigationHandler();

			if (ObjectUtil.nullOrEmpty(currentPage)) {
				nh.handleNavigation(facesContext, null, "/view/error/error?faces-redirect=true");
				return;
			}

			boolean isLoginPage = currentPage.contains("login");


			LoggedUser loggedUser = JSFUtil.getUsuarioLogado();

			if (!isLoginPage && ObjectUtil.nullOrEmpty(loggedUser)){
				nh.handleNavigation(facesContext, null, "/view/login/login?faces-redirect=true");
			}else if ( (isLoginPage && !ObjectUtil.nullOrEmpty(loggedUser)) && !ignoredPage(currentPage)) {
				nh.handleNavigation(facesContext, null, "/default?faces-redirect=true");
			}else if(!isLoginPage && !loggedUser.haveAccess(currentPage) && !ignoredPage(currentPage)){
				nh.handleNavigation(facesContext, null, "/view/error/accessDenied?faces-redirect=true");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map<String, Object> getSessionMap() {
		return getExternalContext().getSessionMap();
	}

	public static ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}


	public void beforePhase(PhaseEvent event) {

	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}
