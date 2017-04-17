package br.com.sol7.orcamento.auth;

import br.com.sol7.orcamento.exceptions.ConnectionException;
import br.com.sol7.orcamento.exceptions.LoginInvalidoException;
import br.com.sol7.orcamento.model.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Everton on 24/02/2015.
 */
public interface LoginService {
    User login(String username, String password) throws IllegalArgumentException, UnsupportedEncodingException, NoSuchAlgorithmException, LoginInvalidoException, ConnectionException;
}