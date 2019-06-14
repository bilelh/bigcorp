package com.training.springcore.service.measure;

import com.training.springcore.config.properties.BigcorpApplicationMeasureProperties;
import com.training.springcore.config.properties.BigcorpApplicationProperties;
import com.training.springcore.model.Captor;
import com.training.springcore.model.Measure;
import com.training.springcore.model.MeasureStep;
import com.training.springcore.model.SimulatedCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SimulatedMeasureService implements MeasureService <SimulatedCaptor> {

    @Autowired
    BigcorpApplicationProperties properties;

    private RestTemplate restTemplate;

    public SimulatedMeasureService(RestTemplateBuilder builder) {
        this.restTemplate = builder.setConnectTimeout(Duration.ofSeconds(1)).build();
    }

    @Override
    public List<Measure> readMeasures(SimulatedCaptor captor, Instant start, Instant end, MeasureStep step) {
        checkReadMeasuresAgrs(captor, start, end, step);

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8090/measures")
                .path("")
                .queryParam("start", start)
                .queryParam("end", end)
                .queryParam("min", captor.getMinPowerInWatt())
                .queryParam("max", captor.getMaxPowerInWatt())
                .queryParam("step", step.getDurationInSecondes());

        Measure[] measures = this.restTemplate.getForObject(builder.toUriString(), Measure[].class);
        return Arrays.asList(measures);

        /*
        List<Measure> measures = new ArrayList<>();
        Instant current = start;
        while(current.isBefore(end)){
            measures.add(new Measure(current, properties.getMeasure().getDefaultSimulated(), captor));
            current = current.plusSeconds(step.getDurationInSecondes());
        }
        return measures;*/
    }
}
