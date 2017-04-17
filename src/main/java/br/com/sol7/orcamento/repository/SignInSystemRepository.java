package br.com.sol7.orcamento.repository;

import br.com.sol7.orcamento.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

public interface SignInSystemRepository extends PagingAndSortingRepository<SignInSystem, Integer> {

    @Query("select MAX(s.date) from SignInSystem s where s.user= ?1")
    public Date getLastLogin(User user);


}
