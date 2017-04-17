package br.com.sol7.orcamento.repository;

import br.com.sol7.orcamento.model.Module;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ModuleRepository extends PagingAndSortingRepository<Module, Integer> {
    @Query("select m from Module m order by m.name")
    public List<Module> findAllModule();

    @Query("select m.description from Module m where m.url like ?1")
    public String findHelper(String url);
}
