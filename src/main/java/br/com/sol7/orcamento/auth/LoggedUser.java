package br.com.sol7.orcamento.auth;

import br.com.sol7.orcamento.model.User;

import java.security.Principal;

public interface LoggedUser extends Principal {

    Integer getIdBI();

    User getUser();

    String getToken();

    Boolean haveAccess(String outcome);

}
