package br.com.sol7.orcamento.service;

import br.com.sol7.orcamento.model.Layout;
import br.com.sol7.orcamento.repository.LayoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LayoutService extends BaseService<Layout, Integer> {

	@Autowired
	public LayoutService(LayoutRepository layoutRepository) {
		super(layoutRepository);
	}

    public LayoutRepository getRepository(){
        return (LayoutRepository) super.getBaseRepository();
    }

    public Layout getLayout(){
        return getRepository().getLayout();
    }
}
