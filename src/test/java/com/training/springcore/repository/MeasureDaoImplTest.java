package com.training.springcore.repository;

import com.training.springcore.model.Captor;
import com.training.springcore.model.Measure;
import com.training.springcore.model.RealCaptor;
import com.training.springcore.model.Site;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan
public class MeasureDaoImplTest {

    @Autowired
    private MeasureDao measureDao;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void findById() {
        Optional<Measure> measure = measureDao.findById(-1L);
        Assertions.assertThat(measure).get()
                .extracting("id").containsExactly(-1L);
        Assertions.assertThat(measure).get()
                .extracting("valueInWatt").containsExactly(1000000);
        Assertions.assertThat(measure).get()
                .extracting("instant").containsExactly(Instant.parse("2018-08-09T11:00:00.000Z"));
    }
    @Test
    public void findByIdShouldReturnNullWhenIdUnknown() {
        Optional<Measure> measure = measureDao.findById(-1000L);
        Assertions.assertThat(measure).isEmpty();
    }
    @Test
    public void findAll() {
        List<Measure> measures = measureDao.findAll();
        Assertions.assertThat(measures).hasSize(10);
    }
    @Test
    public void create() {
        Assertions.assertThat(measureDao.findAll()).hasSize(10);
        Captor captor = new RealCaptor("Eolienne", new Site("site"));
        captor.setId("c1");
        measureDao.save(new Measure(Instant.now(), 1000000,captor));
        Assertions.assertThat(measureDao.findAll()).hasSize(11);
    }
    @Test
    public void update() {
        Optional<Measure> measure = measureDao.findById(-1L);
        Assertions.assertThat(measure).get()
                .extracting("valueInWatt").containsExactly(1000000);
        measure.ifPresent(m -> {
            m.setValueInWatt(2000000);
            measureDao.save(m);
        });

        measure = measureDao.findById(-1L);
        Assertions.assertThat(measure).get()
                .extracting("valueInWatt").containsExactly(2000000);
    }
    @Test
    public void deleteById() {
        Captor captor = new RealCaptor("Eolienne", new Site("site"));
        captor.setId("c1");
        Measure newmeasure = measureDao.save(new Measure(Instant.now(), 1000000,captor));
        Assertions.assertThat(measureDao.findById(newmeasure.getId())).isNotEmpty();
        measureDao.delete(newmeasure);
        Assertions.assertThat(measureDao.findById(newmeasure.getId())).isEmpty();
    }
    @Test
    public void preventConcurrentWrite() {
        Measure measure = measureDao.getOne(-1L);
        // A la base le numéro de version est à sa valeur initiale
        Assertions.assertThat(measure.getVersion()).isEqualTo(0);
        // On detache cet objet du contexte de persistence
        entityManager.detach(measure);
        measure.setValueInWatt(2000000);
        // On force la mise à jour en base (via le flush) et on vérifie que l'objetretourné
        // et attaché à la session a été mis à jour
        Measure attachedMeasure = measureDao.save(measure);
        entityManager.flush();
        Assertions.assertThat(attachedMeasure.getValueInWatt()).isEqualTo(2000000);
        Assertions.assertThat(attachedMeasure.getVersion()).isEqualTo(1);
        // Si maintenant je réessaie d'enregistrer captor, comme le numéro de version est
        // à 0 je dois avoir une exception
        Assertions.assertThatThrownBy(() -> measureDao.save(measure))
                .isExactlyInstanceOf(ObjectOptimisticLockingFailureException.class);
    }
    @Test
    public void deleteByCaptorId() {
        Assertions.assertThat(measureDao.findAll().stream().filter(m ->
                m.getCaptor().getId().equals("c1"))).hasSize(5);
        measureDao.deleteByCaptorId("c1");
        Assertions.assertThat(measureDao.findAll().stream().filter(m ->
                m.getCaptor().getId().equals("c1"))).isEmpty();
    }
}
