package br.com.sol7.orcamento.repository;

import br.com.sol7.orcamento.model.Layout;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LayoutRepository extends PagingAndSortingRepository<Layout, Integer> {

    @Query("select l from Layout l")
    public Layout getLayout();
}
