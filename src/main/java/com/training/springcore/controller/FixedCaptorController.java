package com.training.springcore.controller;

import com.training.springcore.model.FixedCaptor;
import com.training.springcore.model.Site;
import com.training.springcore.repository.CaptorDao;
import com.training.springcore.repository.MeasureDao;
import com.training.springcore.repository.SiteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@Transactional
@RequestMapping("/sites/{siteId}/captors/FIXED")
public class FixedCaptorController {

    @Autowired
    private SiteDao siteDao;

    @Autowired
    private CaptorDao captorDao;

    @Autowired
    private MeasureDao measureDao;

    @GetMapping("/create")
    public ModelAndView create(@PathVariable String siteId , FixedCaptor captor){
        Site site = siteDao.findById(siteId).orElseThrow(IllegalArgumentException::new);

        return new ModelAndView("captor")
                .addObject("captor", new FixedCaptor("", site ,captor.getDefaultPowerInWatt()))
                .addObject("site", site);
    }

    @GetMapping("/{id}")
    public ModelAndView findById(@PathVariable String id, @PathVariable String siteId) {
        Site site = siteDao.findById(siteId).orElseThrow(IllegalArgumentException::new);
        return new ModelAndView("captor")
                .addObject("captor",
                        captorDao.findById(id)
                                .orElseThrow(IllegalArgumentException::new))
                .addObject("site", site);
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView save(@PathVariable String siteId, FixedCaptor captor) {
        Site site = siteDao.findById(siteId).orElseThrow(IllegalArgumentException::new);
        FixedCaptor captorToPersist;
        System.out.println(captor.getId());
        if (captor.getId() == null) {
            captorToPersist = new FixedCaptor(captor.getName(), site,
                    captor.getDefaultPowerInWatt());
        } else {
            captorToPersist = (FixedCaptor) captorDao.findById(captor.getId())
                    .orElseThrow(IllegalArgumentException::new);
            captorToPersist.setName(captor.getName());
            captorToPersist.setDefaultPowerInWatt(captor.getDefaultPowerInWatt());
        }
        captorDao.save(captorToPersist);
        return new ModelAndView("site")
                .addObject("site", site);
    }

    @PostMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable String id, @PathVariable String siteId) {
        // Comme les mesures sont liées à un capteur, nous devons faire le ménage avant
        // pour ne pas avoir d'erreur à la suppression d'un site utilisé ailleurs dans la base
        Site site = siteDao.findById(siteId).orElseThrow(IllegalArgumentException::new);

        measureDao.deleteByCaptorId(id);
        captorDao.deleteById(id);
        return new ModelAndView("site")
                .addObject("site", site);
    }
}
