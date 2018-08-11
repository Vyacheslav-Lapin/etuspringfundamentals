package lab.model.orm;

import lab.dao.CountryDao;
import lab.model.Config;
import lab.model.Country;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 * Illustrates basic use of Hibernate as a JPA provider.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:application-context.xml")
@ContextConfiguration(classes = Config.class)
public class CountryDaoImplTest {

    Country exampleCountry = Country.builder().name("Australia").codeName("AU").build();

    @Autowired
    CountryDao countryDao;

    @Before
    public void setUp() {
        countryDao.save(exampleCountry);
    }

    @Test
    public void testSaveCountry() {
        List<Country> countryList = countryDao.getAllCountries();
        assertEquals(1, countryList.size());
        assertEquals(exampleCountry, countryList.get(0));
    }

    @Test
    public void testGetAllCountries() {
        countryDao.save(new Country("Canada", "CA"));
        List<Country> countryList = countryDao.getAllCountries();
        assertEquals(2, countryList.size());
    }

    @Test
    public void testGetCountryByName() {
        Country country = countryDao.getCountryByName("Australia");
        assertEquals(exampleCountry, country);
    }

    @After
    public void tearDown() {
        countryDao.deleteAll();
    }
}
