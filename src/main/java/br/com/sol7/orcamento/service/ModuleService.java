package br.com.sol7.orcamento.service;

import br.com.sol7.orcamento.model.Module;
import br.com.sol7.orcamento.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ModuleService extends BaseService<Module, Integer>  {

    @Autowired
    public ModuleService(ModuleRepository moduleRepository) {
        super(moduleRepository);
    }

    public ModuleRepository getRepository(){
        return (ModuleRepository) super.getBaseRepository();
    }

}
