package com.training.springcore.service;

import com.training.springcore.model.Captor;
import com.training.springcore.repository.CaptorDao;
import com.training.springcore.service.measure.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CaptorServiceImpl implements CaptorService{

    private MeasureService fixedMeasureService;
    private MeasureService simulatedMeasureService;
    private MeasureService realMeasureService;

    private CaptorDao captorDao;

    public CaptorServiceImpl() {}

    @Autowired
    public CaptorServiceImpl(MeasureService fixedMeasureService,
                             MeasureService simulatedMeasureService,
                             MeasureService realMeasureService,
                             CaptorDao captorDao) {
        this.fixedMeasureService = fixedMeasureService;
        this.simulatedMeasureService = simulatedMeasureService;
        this.realMeasureService = realMeasureService;
        this.captorDao = captorDao;
    }

    @Override
    public Set<Captor> findBySite(String siteId) {
        /*Set<Captor> captors = new HashSet<>();
        if (siteId == null) {
            return captors;
        }
        captors.add(new Captor("Capteur A"));*/

        List<Captor> captors = captorDao.findBySiteId(siteId) ;
        try {
            return captors.stream().collect(Collectors.toSet());
        } catch (NullPointerException e) {return null;}

    }
}
