package br.com.sol7.orcamento.service;

import br.com.sol7.orcamento.model.SignInSystem;
import br.com.sol7.orcamento.model.SignInSystem;
import br.com.sol7.orcamento.model.User;
import br.com.sol7.orcamento.repository.SignInSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@Transactional
public class SignInSystemService extends BaseService<SignInSystem, Integer> {

	@Override
	public SignInSystem save(SignInSystem entity) {
		return super.save(entity);
	}

	@Autowired
	public SignInSystemService(SignInSystemRepository signInSystemRepository) {
		super(signInSystemRepository);
	}

	public Date getLastLogin(User user){
		return getSignInSystemRepository().getLastLogin(user);
	}
	
	public SignInSystemRepository getSignInSystemRepository(){
		return (SignInSystemRepository) super.getBaseRepository();
	}
}
