package lab.dao.jpa;

import lombok.Setter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

public class AbstractJpaDao {


	@Setter(onMethod = @__(@PersistenceUnit))
	protected EntityManagerFactory emf;


}