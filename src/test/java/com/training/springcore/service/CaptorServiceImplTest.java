package com.training.springcore.service;

import com.training.springcore.model.Captor;
import com.training.springcore.model.PowerSource;
import com.training.springcore.model.RealCaptor;
import com.training.springcore.model.Site;
import com.training.springcore.repository.CaptorDao;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Set;


@RunWith(SpringRunner.class)
public class CaptorServiceImplTest {

    //@Autowired
    //private CaptorServiceImpl captorService = new CaptorServiceImpl();


    @Mock
    private CaptorDao captorDao;
    @InjectMocks
    private CaptorServiceImpl captorService;
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findBySiteShouldReturnNullWhenIdIsNull() {
// Initialisation
        String siteId = null;
// Appel du SUT
        Set<Captor> captors = captorService.findBySite(siteId);
// Vérification
        Assertions.assertThat(captors).isEmpty();
    }
    @Test
    public void findBySite() {
// Initialisation
        String siteId = "siteId";
        Captor expectedCaptor = new RealCaptor("Capteur A", new Site("Florange"));
        Mockito.when(captorDao.findBySiteId(siteId)).thenReturn(Arrays.asList(expectedCaptor));
// Appel du SUT
        Set<Captor> captors = captorService.findBySite(siteId);
// Vérification
        Assertions.assertThat(captors).hasSize(1);
        Assertions.assertThat(captors).extracting(Captor::getName).contains("Capteur A");
    }

    /*
    @Test
    public void findBySiteShouldReturnNullWhenIdIsNull() {
        // Initialisation
        String siteId = null;

        // Appel du SUT
        Set<Captor> captors = captorService.findBySite(siteId);

        // Vérification
        Assertions.assertThat(captors).isEmpty();
    }

    @Test
    public void findBySite() {
        // Initialisation
        String siteId = "siteId";

        // Appel du SUT
        Set<Captor> captors = captorService.findBySite(siteId);

        // Vérification
        Assertions.assertThat(captors).hasSize(1);
        Assertions.assertThat(captors).extracting(Captor::getName).contains("Capteur A");
    }*/
}