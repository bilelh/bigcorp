package com.training.springcore.repository;

import com.training.springcore.model.Measure;
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

    public MeasureDaoImpl() { }

    @Override
    public void persist(Measure measure) {
        em.persist(measure);
    }

    @Override
    public Measure findById(Long id) {
        return null;
    }

    @Override
    public List<Measure> findAll() {

        return null;
    }

    @Override
    public void delete(Measure measure) {
        em.remove(measure);
    }
}
