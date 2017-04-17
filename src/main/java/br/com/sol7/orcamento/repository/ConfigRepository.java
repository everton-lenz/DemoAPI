package br.com.sol7.orcamento.repository;

import br.com.sol7.orcamento.model.Config;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ConfigRepository extends PagingAndSortingRepository<Config, Integer> {

	@Query("Select c from Config c where c.key = ?1")
	public Config searchForKey(String key);
}
