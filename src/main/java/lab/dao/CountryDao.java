package lab.dao;

import java.util.List;

import lab.model.Country;

public interface CountryDao {

	void save(Country country);

	List<Country> getAllCountries();

	Country getCountryByName(String name);

    void delete(Country country);

    void deleteAll();

}