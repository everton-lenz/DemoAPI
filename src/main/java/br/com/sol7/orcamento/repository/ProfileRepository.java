package br.com.sol7.orcamento.repository;

import br.com.sol7.orcamento.model.Module;
import br.com.sol7.orcamento.model.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProfileRepository extends PagingAndSortingRepository<Profile, Integer> {

    @Query("select m from Module m order by m.name")
    public List<Module> findAllModule();

    @Query("select m from Module m where m.url is not null and m.url <> '' order by m.name")
    public List<Module> findAllModuleWithUrl();

}
