package lab.model.dao;

import static lombok.AccessLevel.PRIVATE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import lab.dao.CountryDao;
import lab.model.Country;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@ContextConfiguration("classpath:application-context.xml")
public class JdbcTest{

    @NonFinal
	@Autowired
    CountryDao countryDao;

	List<Country> expectedCountryList = new ArrayList<>();
	List<Country> expectedCountryListStartsWithA = new ArrayList<>();
	Country countryWithChangedName = new Country(8, "Russia", "RU");

	static String[][] COUNTRY_INIT_DATA = { { "Australia", "AU" },
            { "Canada", "CA" }, { "France", "FR" }, { "Hong Kong", "HK" },
            { "Iceland", "IC" }, { "Japan", "JP" }, { "Nepal", "NP" },
            { "Russian Federation", "RU" }, { "Sweden", "SE" },
            { "Switzerland", "CH" }, { "United Kingdom", "GB" },
            { "United States", "US" } };

    @Before
    public void setUp() {
        initExpectedCountryLists();
        countryDao.loadCountries(COUNTRY_INIT_DATA);
    }
    
    @Test
    @DirtiesContext
    public void testCountryList() {
        List<Country> countryList = countryDao.getCountryList();
        assertNotNull(countryList);
        assertEquals(expectedCountryList.size(), countryList.size());
        for (int i = 0; i < expectedCountryList.size(); i++) {
            assertEquals(expectedCountryList.get(i), countryList.get(i));
        }
    }

    @Test
    @DirtiesContext
    public void testCountryListStartsWithA() {
        List<Country> countryList = countryDao.getCountryListStartWith("A");
        assertNotNull(countryList);
        assertEquals(expectedCountryListStartsWithA.size(), countryList.size());
        for (int i = 0; i < expectedCountryListStartsWithA.size(); i++) {
            assertEquals(expectedCountryListStartsWithA.get(i), countryList.get(i));
        }
    }

    @Test
    @DirtiesContext
    public void testCountryChange() {
        countryDao.updateCountryName("RU", "Russia");
        assertEquals(countryWithChangedName, countryDao.getCountryByCodeName("RU"));
    }

    private void initExpectedCountryLists() {
         for (int i = 0; i < COUNTRY_INIT_DATA.length;) {
             String[] countryInitData = COUNTRY_INIT_DATA[i++];
             Country country = new Country(i, countryInitData[0], countryInitData[1]);
             expectedCountryList.add(country);
             if (country.getName().startsWith("A"))
                 expectedCountryListStartsWithA.add(country);
         }
     }
}