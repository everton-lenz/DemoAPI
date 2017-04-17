package br.com.sol7.orcamento.repository;

import org.hibernate.Session;

public interface InTransaction<T> {
    T doWork(Session session);
}
