package lab.dao;

import lab.model.Country;
import lombok.experimental.FieldDefaults;
import org.intellij.lang.annotations.Language;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

import static lab.common.Java9BackPort.mapOf;
import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CountryJdbcDao extends NamedParameterJdbcDaoSupport {

    static String LOAD_COUNTRIES_SQL = "insert into country (name, code_name) values ";

    static String GET_ALL_COUNTRIES_SQL = "select id, name, code_name from country";
    static String GET_COUNTRIES_BY_NAME_SQL = "select * from country where name like :name";
    static String GET_COUNTRY_BY_NAME_SQL = "select * from country where name = '";
    static String GET_COUNTRY_BY_CODE_NAME_SQL = "select * from country where code_name = '";

    static String UPDATE_COUNTRY_NAME_SQL = "update country SET name=:name where code_name=:code_name";

    static String ID = "id";
    static String NAME = "name";
    static String CODE_NAME = "code_name";

    static RowMapper<Country> COUNTRY_ROW_MAPPER = (resultSet, __) ->
            Country.builder()
                    .id(resultSet.getInt(ID))
                    .name(resultSet.getString(NAME))
                    .codeName(resultSet.getString(CODE_NAME))
                    .build();


    public List<Country> getCountryList() {
        return getJdbcTemplate()
                .query(GET_ALL_COUNTRIES_SQL, COUNTRY_ROW_MAPPER);
    }

    public List<Country> getCountryListStartWith(String name) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getDataSource());
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(
                "name", name + "%");
        return namedParameterJdbcTemplate.query(GET_COUNTRIES_BY_NAME_SQL,
                sqlParameterSource, COUNTRY_ROW_MAPPER);
    }

    public void updateCountryName(String codeName, String newCountryName) {
        getNamedParameterJdbcTemplate()
                .update(UPDATE_COUNTRY_NAME_SQL,
                        mapOf("name", newCountryName, "code_name", codeName));
    }

    public void loadCountries(String[][] countryInitData) {
        for (String[] countryData : countryInitData) {
            String sql = LOAD_COUNTRIES_SQL + "('" + countryData[0] + "', '"
                    + countryData[1] + "');";
//			System.out.println(sql);
            getJdbcTemplate().execute(sql);
        }
    }

    public Country getCountryByCodeName(String codeName) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();

        String sql = GET_COUNTRY_BY_CODE_NAME_SQL + codeName + "'";
//		System.out.println(sql);

        return jdbcTemplate.query(sql, COUNTRY_ROW_MAPPER).get(0);
    }

    public Country getCountryByName(String name)
            throws CountryNotFoundException {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        List<Country> countryList = jdbcTemplate.query(GET_COUNTRY_BY_NAME_SQL
                + name + "'", COUNTRY_ROW_MAPPER);
        if (countryList.isEmpty()) {
            throw new CountryNotFoundException();
        }
        return countryList.get(0);
    }
}
