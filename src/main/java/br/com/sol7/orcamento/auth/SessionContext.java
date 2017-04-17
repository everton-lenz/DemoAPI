package br.com.sol7.orcamento.auth;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

@Controller
@Scope(SCOPE_SESSION)
public final class SessionContext {

    private LoggedUser loggedUser;

    protected LoggedUser getLoggedUser() {
        return loggedUser;
    }

    protected void setLoggedUser(LoggedUser loggedUser) {
        this.loggedUser = loggedUser;

    }

}