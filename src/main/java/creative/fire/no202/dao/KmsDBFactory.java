package creative.fire.no202.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 * @author feuyeux@gmail.com
 * @version 2.0
 */
public class KmsDBFactory {
	private static KmsDBFactory instance;
	private static EntityManagerFactory emf;

	private KmsDBFactory() {
	}

	public synchronized static KmsDBFactory getInstance() {
		if (instance == null) {
			instance = new KmsDBFactory();
			emf = Persistence.createEntityManagerFactory("jpa2_kms");
		}
		return instance;
	}

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();

		if (emf != null && emf.isOpen()) {
			emf.close();
		}
	}
}
