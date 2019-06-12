package com.training.springcore.repository;

import com.training.springcore.model.Captor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class CaptorDaoImpl implements CaptorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(Captor captor) {
        em.persist(captor);
    }

    @Override
    public List<Captor> findBySiteId(String siteId) {
        return em.createQuery("select c from Captor c inner join c.site s where s.id = :id" ,
                Captor.class).setParameter("id" , siteId).getResultList();
    }

    @Override
    public Captor findById(String id) {
        return em.find(Captor.class , id);
    }

    @Override
    public List<Captor> findAll() {
        return em.createQuery("select c from Captor c inner join c.site s" ,
                Captor.class).getResultList();
    }

    @Override
    public void delete(Captor captor) {
        em.remove(captor);
    }
}
