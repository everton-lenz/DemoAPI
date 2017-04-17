package br.com.sol7.orcamento.repository;

import br.com.sol7.orcamento.util.ObjectUtil;
import br.com.sol7.orcamento.util.ReflectUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.Alias;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
    public class HibernateDao implements Serializable {

	private static final long serialVersionUID = -4150527471650058893L;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

    public <T> T execute(InTransaction<T> inTransaction) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            Session session = entityManager.unwrap(Session.class);
            T result = inTransaction.doWork(session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }
    }

	/**
	 * Executa SQL nativo [QUERY] e o teu retorno é indefinido.
	 * @param sql 
	 * @return Retorno da Query.
	 */
	public List<?> executeSQL(final String sql) {
        return execute(new InTransaction<List<?>>() {
            @Override
            public List<?> doWork(Session session) {
                Query q = session.createSQLQuery(sql);
                return q.list();
            }
        });
	}
	
	/**
	 * Executa SQL nativo [update/delete].
	 * @param sql 
	 * @return Quantidade de linhas modificadas
	 */
	public int executeUpdateSQL(final String sql) {
        return execute(new InTransaction<Integer>() {
            @Override
            public Integer doWork(Session session) {
                Query q = session.createSQLQuery(sql);
                return q.executeUpdate();
            }
        });
	}
	
	/**
	 * Executa SQL nativo e possui um retorno definido.
	 * @param sql
	 * @param clazz 
	 * @return Lista de Objetos do tipo clazz.
	 */
	public List<?> executeSQL(final String sql, final Class<?> clazz) {
	    return execute(new InTransaction<List<?>>() {
            @Override
            public List<?> doWork(Session session) {
                SQLQuery q = session.createSQLQuery(sql);
                q.addEntity(clazz);
                return q.list();

            }
        });
	}
	
	/**
	 * Executa um HQL.
	 * @param hql Query a ser pesquisada
	 * @param firstResult Primeiro resultado
	 * @param maxResults Numero total de elementos retornados.
	 * @return 
	 */
	public List<?> executeHQL(final String hql, final int firstResult, final int maxResults){
		return execute(new InTransaction<List<?>>() {
            @Override
            public List<?> doWork(Session session) {
                Query q = session.createQuery(hql);

                if(maxResults != 0){
                    q.setFirstResult(firstResult);
                    q.setMaxResults(maxResults);
                }
                q.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

                return q.list();
            }
        });

	}

    public List<?> executeDatatablePaginationQuery(final int pageSize, final int first, final String sortField, final SortOrder sortOrder, final Class<?> entityClass, final Map<String, Object> filters) {

        return execute(new InTransaction<List<?>>() {
            @Override
            public List<?> doWork(Session session) {
                // For the root alias
                String root = entityClass.toString().substring(entityClass.toString().lastIndexOf(".") + 1).toLowerCase();
                Field pkField = ReflectUtil.getPrimaryKeyField(entityClass);

                Criteria criteria = session.createCriteria(entityClass, root);


                // order
                applyOrder(criteria, sortOrder, sortField, root, pkField.getName(), true);

                // pagination
                criteria.setFirstResult(first);
                criteria.setMaxResults(pageSize);

                for (Object filter : filters.values()) {
                    criteria.createAlias(filter.toString(), filters.get(filter).toString());
                }

                List<?> ids = criteria.list();

                if(ObjectUtil.nullOrEmpty(ids)) {
                    return new ArrayList<Object>();
                }

                ids = adjustIdsIfNecessary(ids, pkField);


                Criteria resultado = session.createCriteria(entityClass, root).add(Restrictions.in(pkField.getName(), ids));
                applyOrder(resultado, sortOrder, sortField, root, pkField.getName(), false);


                return resultado.list();
            }
        });

    }

    private List<?> adjustIdsIfNecessary(List<?> values, Field pkField) {
        Class<?> type = pkField.getType();
        if(!type.isAssignableFrom(values.get(0).getClass())) {
            List<Object> ids = new ArrayList<Object>();
            for(Object i : values) {
                Object[] array = (Object[]) i;
                for(Object obj : array) {
                    if(type.isAssignableFrom(obj.getClass())){
                        ids.add(type.cast(obj));
                        break;
                    }
                }

            }
            values = ids;
        }

        return values;
    }

    private void applyOrder(Criteria criteria, SortOrder sortOrder, String sortField, String root, String pkField, boolean addProjections) {
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.distinct(Projections.property(pkField)));

        if (!ObjectUtil.nullOrEmpty(sortField)) {

            if(sortField.contains(".")) {
                //TODO hardcode para navegação de somente uma entidade, necessário fix para suportar n
                String sortAlias = StringUtils.split(sortField, ".")[0];
                criteria.createAlias(root + "." + sortAlias, sortAlias);
            }

            criteria.addOrder(sortOrder.equals(SortOrder.ASCENDING) ? Order.asc(sortField) : Order.desc(sortField));
            criteria.addOrder(Order.desc(pkField));
            projectionList.add(Projections.property(sortField));

        } else {
            criteria.addOrder(Order.desc(pkField));
        }

        if(addProjections) {
            criteria.setProjection(projectionList);
        }
    }

    public Integer executeDatatablePaginationPageCount(final Map<String, Object> filters, final Class<?> entityClass) {

        return execute(new InTransaction<Integer>() {
            @Override
            public Integer doWork(Session session) {
                String root = entityClass.toString().substring(entityClass.toString().lastIndexOf(".") + 1).toLowerCase();

                String pkField = ReflectUtil.getPrimaryKeyField(entityClass).getName();

                Criteria countCriteria = session.createCriteria(entityClass, root);

                // rowCount
                countCriteria.setProjection(Projections.countDistinct(pkField));

                Long result = (Long) countCriteria.uniqueResult();

                return result.intValue();
            }
        });
    }
}
