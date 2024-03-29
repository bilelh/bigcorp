package com.training.springcore.controller;

import com.training.springcore.controller.dto.MeasureDto;
import com.training.springcore.exception.NotFoundException;
import com.training.springcore.model.Captor;
import com.training.springcore.model.MeasureStep;
import com.training.springcore.model.PowerSource;
import com.training.springcore.model.SimulatedCaptor;
import com.training.springcore.repository.CaptorDao;
import com.training.springcore.service.measure.SimulatedMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/measures/captors/{id}/last/hours/{nbHours}")
@Transactional
public class MeasureController {

    @Autowired
    private CaptorDao captorDao;

    @Autowired
    private SimulatedMeasureService simulatedMeasureService;


    @GetMapping
    //public List findById(@PathVariable String id, @PathVariable  long nbHours) {
    private List findById(@PathVariable String id, @PathVariable  long nbHours){
        Captor captor = captorDao.findById(id).orElseThrow(NotFoundException::new);
        if (captor.getPowerSource() == PowerSource.SIMULATED) {
            return simulatedMeasureService.readMeasures(((SimulatedCaptor) captor),
                    Instant.now().minus(Duration.ofHours(nbHours)).truncatedTo(ChronoUnit.MINUTES),
                    Instant.now().truncatedTo(ChronoUnit.MINUTES),
                    MeasureStep.ONE_MINUTE)
                    .stream()
                    .map(m -> new MeasureDto(m.getInstant(),
                            m.getValueInWatt()))
                    .collect(Collectors.toList());
        }
        // Pour le moment on ne gère qu'un type
        return new ArrayList<>();
    }
}
