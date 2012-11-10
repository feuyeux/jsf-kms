package creative.fire.no202.dao.jpa2;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import creative.fire.no202.dao.exceptions.NonexistentEntityException;
import creative.fire.no202.dao.exceptions.PreexistingEntityException;
import creative.fire.no202.entity.KmsBook;
import creative.fire.no202.entity.KmsKnowledge;
import creative.fire.no202.entity.KmsUser;
/**
 * @author feuyeux@gmail.com
 * @version 2.0
 */
public class KmsBookDao {

	public void create(KmsBook kmsBook) throws PreexistingEntityException, Exception {
		EntityManager em = null;
		try {
			em = KmsDBFactory.getInstance().getEntityManager();
			em.getTransaction().begin();
			KmsUser kmsUser = kmsBook.getKmsUser();
			if (kmsUser != null) {
				kmsUser = em.getReference(kmsUser.getClass(), kmsUser.getUserId());
				kmsBook.setKmsUser(kmsUser);
			}
			KmsKnowledge kmsKnowledge = kmsBook.getKmsKnowledge();
			if (kmsKnowledge != null) {
				kmsKnowledge = em.getReference(kmsKnowledge.getClass(), kmsKnowledge.getKnowledgeId());
				kmsBook.setKmsKnowledge(kmsKnowledge);
			}
			em.persist(kmsBook);
			if (kmsUser != null) {
				kmsUser.getKmsBookCollection().add(kmsBook);
				kmsUser = em.merge(kmsUser);
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			if (findKmsBook(kmsBook.getBookId()) != null) {
				throw new PreexistingEntityException("KmsBook " + kmsBook + " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(KmsBook kmsBook) throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = KmsDBFactory.getInstance().getEntityManager();
			em.getTransaction().begin();
			KmsBook persistentKmsBook = em.find(KmsBook.class, kmsBook.getBookId());
			KmsUser kmsUserOld = persistentKmsBook.getKmsUser();
			KmsUser kmsUserNew = kmsBook.getKmsUser();

			KmsKnowledge kmsKnowledgeNew = kmsBook.getKmsKnowledge();
			if (kmsUserNew != null) {
				kmsUserNew = em.getReference(kmsUserNew.getClass(), kmsUserNew.getUserId());
				kmsBook.setKmsUser(kmsUserNew);
			}
			if (kmsKnowledgeNew != null) {
				kmsKnowledgeNew = em.getReference(kmsKnowledgeNew.getClass(), kmsKnowledgeNew.getKnowledgeId());
				kmsBook.setKmsKnowledge(kmsKnowledgeNew);
			}
			kmsBook = em.merge(kmsBook);
			if (kmsUserOld != null && !kmsUserOld.equals(kmsUserNew)) {
				kmsUserOld.getKmsBookCollection().remove(kmsBook);
				kmsUserOld = em.merge(kmsUserOld);
			}
			if (kmsUserNew != null && !kmsUserNew.equals(kmsUserOld)) {
				kmsUserNew.getKmsBookCollection().add(kmsBook);
				kmsUserNew = em.merge(kmsUserNew);
			}

			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				String id = kmsBook.getBookId();
				if (findKmsBook(id) == null) {
					throw new NonexistentEntityException("The kmsBook with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(String id) throws NonexistentEntityException {
		EntityManager em = null;
		try {
			em = KmsDBFactory.getInstance().getEntityManager();
			em.getTransaction().begin();
			KmsBook kmsBook;
			try {
				kmsBook = em.getReference(KmsBook.class, id);
				kmsBook.getBookId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The kmsBook with id " + id + " no longer exists.", enfe);
			}

			em.remove(kmsBook);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<KmsBook> findKmsBookEntities() {
		return findKmsBookEntities(true, -1, -1);
	}

	public List<KmsBook> findKmsBookEntities(int maxResults, int firstResult) {
		return findKmsBookEntities(false, maxResults, firstResult);
	}

	private List<KmsBook> findKmsBookEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = KmsDBFactory.getInstance().getEntityManager();
		try {
			CriteriaQuery<KmsBook> cq = em.getCriteriaBuilder().createQuery(KmsBook.class);
			cq.select(cq.from(KmsBook.class));
			TypedQuery<KmsBook> q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	public KmsBook findKmsBook(String id) {
		EntityManager em = KmsDBFactory.getInstance().getEntityManager();
		try {
			return em.find(KmsBook.class, id);
		} finally {
			em.close();
		}
	}

	public int getKmsBookCount() {
		EntityManager em = KmsDBFactory.getInstance().getEntityManager();
		try {
			CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
			Root<KmsBook> rt = cq.from(KmsBook.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			TypedQuery<Long> q = em.createQuery(cq);
			return q.getSingleResult().intValue();
		} finally {
			em.close();
		}
	}

}
