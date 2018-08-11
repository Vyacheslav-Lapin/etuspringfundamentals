package lab.dao.jpa;

import lab.dao.CountryDao;
import lab.model.Country;
import lombok.Cleanup;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
public class CountryJpaJdbcDaoImpl extends AbstractJpaDao implements CountryDao {

//    @PersistenceContext
//    EntityManager em;

	@Override
	public void save(Country country) {
		@Cleanup EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.merge(country);
		transaction.commit();
	}

	@Override
	public List<Country> getAllCountries() {
        @Cleanup EntityManager em = emf.createEntityManager();
		return em.createQuery("select c from Country c", Country.class)
                .getResultList();
	}

	@Override
	public Country getCountryByName(String name) {
        @Cleanup EntityManager em = emf.createEntityManager();
        return em.createQuery("select c from Country c where c.name = :name", Country.class)
                .setParameter("name", name)
                .getResultList().get(0);
	}

    @Override
    public void delete(Country country) {
        @Cleanup EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
//        if (!em.contains(country)) {
//            em.refresh(country);
//        }
//        System.out.println(country.getId());
        em.remove(country);
//        em.createQuery("delete from Country where id=:id")
//                .setParameter("id", country.getId())
//                .executeUpdate();
        em.flush();
        transaction.commit();
    }

    @Override
    public void deleteAll() {
        @Cleanup EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.createQuery("delete from Country").executeUpdate();
        transaction.commit();
    }

}
