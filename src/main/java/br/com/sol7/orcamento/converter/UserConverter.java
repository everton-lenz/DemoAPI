package br.com.sol7.orcamento.converter;

import br.com.sol7.orcamento.model.User;
import br.com.sol7.orcamento.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope("request")
public class UserConverter extends BaseConverter<User, Integer> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	public UserConverter(UserService userService) {
		super(userService, User.class);
	}


}
