package com.training.springcore.repository;

import com.training.springcore.model.Site;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class SiteDaoImpl implements SiteDao {

    public SiteDaoImpl() { }

    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(Site site) {
        em.persist(site);
    }

    @Override
    public Site findById(String id) {
        return em.find(Site.class , id);
    }

    @Override
    public List<Site> findAll() {
        return em.createQuery("select * from Site s inner join s.captor c" ,
                Site.class).getResultList();
    }

    @Override
    public void delete(Site site) {
        em.remove(site);
    }
}
