package br.com.sol7.orcamento.repository;

import br.com.sol7.orcamento.model.Profile;
import br.com.sol7.orcamento.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    @Query("select distinct u from User u where u.email = ?1 and u.password = ?2")
    public User findByLoginAndPassword(String login, String password);

    @Query("select distinct u from User u where u.email = ?1")
    public User findByEmail(String email);

    @Query("select distinct u from User u where u.profile.id = ?1 and u.state = true")
    public List<User> listAllProfile(Long integer);

    @Query("select u from User u where u.state = true order by u.name ")
    public List<User> listAllUsers();

 }
