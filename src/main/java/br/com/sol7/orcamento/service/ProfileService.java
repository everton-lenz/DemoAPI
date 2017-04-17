package br.com.sol7.orcamento.service;

import br.com.sol7.orcamento.model.Profile;
import br.com.sol7.orcamento.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ProfileService extends BaseService<Profile, Integer> {

	@Override
	public Profile save(Profile entity) {
		return super.save(entity);
	}

	@Autowired
	public ProfileService(ProfileRepository profileRepository) {
		super(profileRepository);
	}
	
	public ProfileRepository getProfileRepository(){
		return (ProfileRepository) super.getBaseRepository();
	}
}
