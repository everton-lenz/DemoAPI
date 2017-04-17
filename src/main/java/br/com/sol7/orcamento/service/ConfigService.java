package br.com.sol7.orcamento.service;

import br.com.sol7.orcamento.model.Config;
import br.com.sol7.orcamento.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConfigService extends BaseService<Config, Integer> {

	@Autowired
	public ConfigService(ConfigRepository configRepository) {
		super(configRepository);
	}

	public Config searchForKey(String key) {
		return getConfigRepository().searchForKey(key);
	}
	
	
	public ConfigRepository getConfigRepository(){
		return (ConfigRepository) super.getBaseRepository();
	}
}
