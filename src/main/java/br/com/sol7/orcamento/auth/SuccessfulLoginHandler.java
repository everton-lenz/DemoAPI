package br.com.sol7.orcamento.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
class SuccessfulLoginHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SessionContext sessionContext = getSessionContext(request);
        LoggedUser user = (LoggedUser) authentication.getPrincipal();
        sessionContext.setLoggedUser(user);
    }

    private SessionContext getSessionContext(HttpServletRequest request) {
        return WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean(SessionContext.class);
    }

}
