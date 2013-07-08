package org.feuyeux.air.jsf.kms.dao.jpa2;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.feuyeux.air.jsf.kms.dao.exceptions.NonexistentEntityException;
import org.feuyeux.air.jsf.kms.dao.exceptions.PreexistingEntityException;
import org.feuyeux.air.jsf.kms.entity.KmsArticle;
import org.feuyeux.air.jsf.kms.entity.KmsKnowledge;
import org.feuyeux.air.jsf.kms.entity.KmsUser;
/**
 * @author feuyeux@gmail.com
 * @version 2.0
 */
public class KmsArticleDao {

	public void create(KmsArticle kmsArticle) throws PreexistingEntityException, Exception {
		EntityManager em = null;
		try {
			em = KmsDBFactory.getInstance().getEntityManager();
			em.getTransaction().begin();
			KmsUser kmsUser = kmsArticle.getKmsUser();
			if (kmsUser != null) {
				kmsUser = em.getReference(kmsUser.getClass(), kmsUser.getUserId());
				kmsArticle.setKmsUser(kmsUser);
			} else {

			}

			kmsArticle.setInsertTime(new Date());
			em.persist(kmsArticle);
			if (kmsUser != null) {
				kmsUser.getKmsArticleCollection().add(kmsArticle);
				kmsUser = em.merge(kmsUser);
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			if (findKmsArticle(kmsArticle.getArticleId()) != null) {
				throw new PreexistingEntityException("KmsArticle " + kmsArticle + " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(KmsArticle kmsArticle) throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = KmsDBFactory.getInstance().getEntityManager();
			em.getTransaction().begin();
			KmsArticle persistentKmsArticle = em.find(KmsArticle.class, kmsArticle.getArticleId());
			KmsUser kmsUserOld = persistentKmsArticle.getKmsUser();
			KmsUser kmsUserNew = kmsArticle.getKmsUser();
			if (kmsUserNew != null) {
				kmsUserNew = em.getReference(kmsUserNew.getClass(), kmsUserNew.getUserId());
				kmsArticle.setKmsUser(kmsUserNew);
			}
			kmsArticle = em.merge(kmsArticle);
			if (kmsUserOld != null && !kmsUserOld.equals(kmsUserNew)) {
				kmsUserOld.getKmsArticleCollection().remove(kmsArticle);
				kmsUserOld = em.merge(kmsUserOld);
			}
			if (kmsUserNew != null && !kmsUserNew.equals(kmsUserOld)) {
				kmsUserNew.getKmsArticleCollection().add(kmsArticle);
				kmsUserNew = em.merge(kmsUserNew);
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				String id = kmsArticle.getArticleId();
				if (findKmsArticle(id) == null) {
					throw new NonexistentEntityException("The kmsArticle with id " + id + " no longer exists.");
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
			KmsArticle kmsArticle;
			try {
				kmsArticle = em.getReference(KmsArticle.class, id);
				kmsArticle.getArticleId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The kmsArticle with id " + id + " no longer exists.", enfe);
			}

			em.remove(kmsArticle);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<KmsArticle> findKmsArticleEntities() {
		return findKmsArticleEntities(true, -1, -1);
	}

	public List<KmsArticle> findKmsArticleEntities(int maxResults, int firstResult) {
		return findKmsArticleEntities(false, maxResults, firstResult);
	}

	private List<KmsArticle> findKmsArticleEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = KmsDBFactory.getInstance().getEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<KmsArticle> cq = cb.createQuery(KmsArticle.class);
			Root<KmsArticle> kmsArticle = cq.from(KmsArticle.class);
			cq.select(kmsArticle);
			cq.orderBy(cb.desc(kmsArticle.get("insertTime")));
			TypedQuery<KmsArticle> q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	public KmsArticle findKmsArticle(String id) {
		EntityManager em = KmsDBFactory.getInstance().getEntityManager();
		try {
			return em.find(KmsArticle.class, id);
		} finally {
			em.close();
		}
	}

	public List<KmsArticle> searchKmsArticle(KmsKnowledge kmsKnowledge) {
		EntityManager em = KmsDBFactory.getInstance().getEntityManager();
		try {
			TypedQuery<KmsArticle> query = em.createQuery("from KmsArticle as kmsArticle where kmsArticle.kmsKnowledge=?", KmsArticle.class);
			List<KmsArticle> kmsArticles = query.setParameter(1, kmsKnowledge).getResultList();
			return kmsArticles;
		} finally {
			em.close();
		}
	}

	public int getKmsArticleCount() {
		EntityManager em = KmsDBFactory.getInstance().getEntityManager();
		try {
			CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
			Root<KmsArticle> rt = cq.from(KmsArticle.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

}
