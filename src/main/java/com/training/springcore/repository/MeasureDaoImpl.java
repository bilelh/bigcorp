package com.training.springcore.repository;

import com.training.springcore.model.Captor;
import com.training.springcore.model.Measure;
import com.training.springcore.model.Site;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class MeasureDaoImpl implements MeasureDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(Measure measure) {
        em.persist(measure);
    }

    @Override
    public Measure findById(Long id) {
        return em.find(Measure.class , id);
    }

    @Override
    public List<Measure> findAll() {
        return em.createQuery("select m from Measure m" ,
                Measure.class).getResultList();
    }

    @Override
    public void delete(Measure measure) {
        em.remove(measure);
    }
}
