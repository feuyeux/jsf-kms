package org.feuyeux.air.jsf.kms.dao.jpa2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.feuyeux.air.jsf.kms.dao.exceptions.IllegalOrphanException;
import org.feuyeux.air.jsf.kms.dao.exceptions.NonexistentEntityException;
import org.feuyeux.air.jsf.kms.dao.exceptions.PreexistingEntityException;
import org.feuyeux.air.jsf.kms.dao.util.KmsConditionPair;
import org.feuyeux.air.jsf.kms.entity.KmsArticle;
import org.feuyeux.air.jsf.kms.entity.KmsBook;
import org.feuyeux.air.jsf.kms.entity.KmsKnowledge;
import org.feuyeux.air.jsf.kms.entity.KmsUser;
/**
 * @author feuyeux@gmail.com
 * @version 2.0
 */
public class KmsKnowledgeDao {

	public void create(KmsKnowledge kmsKnowledge) throws PreexistingEntityException, Exception {
		if (kmsKnowledge.getKmsArticleCollection() == null) {
			kmsKnowledge.setKmsArticleCollection(new ArrayList<KmsArticle>());
		}
		if (kmsKnowledge.getKmsBookCollection() == null) {
			kmsKnowledge.setKmsBookCollection(new ArrayList<KmsBook>());
		}
		EntityManager em = null;
		try {
			em = KmsDBFactory.getInstance().getEntityManager();
			em.getTransaction().begin();
			KmsUser kmsUser = kmsKnowledge.getKmsUser();
			if (kmsUser != null) {
				kmsUser = em.getReference(kmsUser.getClass(), kmsUser.getUserId());
				kmsKnowledge.setKmsUser(kmsUser);
			}

			Collection<KmsArticle> kmsArticles = new ArrayList<KmsArticle>();
			for (KmsArticle kmsArticle : kmsKnowledge.getKmsArticleCollection()) {
				kmsArticle = em.getReference(kmsArticle.getClass(), kmsArticle.getArticleId());
				kmsArticles.add(kmsArticle);
			}
			kmsKnowledge.setKmsArticleCollection(kmsArticles);

			Collection<KmsBook> kmsBooks = new ArrayList<KmsBook>();
			for (KmsBook kmsBook : kmsKnowledge.getKmsBookCollection()) {
				kmsBook = em.getReference(kmsBook.getClass(), kmsBook.getBookId());
				kmsBooks.add(kmsBook);
			}
			kmsKnowledge.setKmsBookCollection(kmsBooks);

			em.persist(kmsKnowledge);

			if (kmsUser != null) {
				kmsUser.getKmsKnowledgeCollection().add(kmsKnowledge);
				kmsUser = em.merge(kmsUser);
			}
			for (KmsArticle kmsArticle : kmsKnowledge.getKmsArticleCollection()) {
				KmsKnowledge kmsKnowledge1 = kmsArticle.getKmsKnowledge();
				kmsArticle.setKmsKnowledge(kmsKnowledge);
				kmsArticle = em.merge(kmsArticle);
				if (kmsKnowledge1 != null) {
					kmsKnowledge1.getKmsArticleCollection().remove(kmsArticle);
					kmsKnowledge1 = em.merge(kmsKnowledge1);
				}
			}
			for (KmsBook kmsBook : kmsKnowledge.getKmsBookCollection()) {
				KmsKnowledge kmsKnowledge2 = kmsBook.getKmsKnowledge();
				kmsBook.setKmsKnowledge(kmsKnowledge);
				kmsBook = em.merge(kmsBook);
				if (kmsKnowledge2 != null) {
					kmsKnowledge2.getKmsBookCollection().remove(kmsBook);
					kmsKnowledge2 = em.merge(kmsKnowledge2);
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			if (findKmsKnowledge(kmsKnowledge.getKnowledgeId()) != null) {
				throw new PreexistingEntityException("KmsKnowledge " + kmsKnowledge + " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(KmsKnowledge kmsKnowledge) throws IllegalOrphanException, NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = KmsDBFactory.getInstance().getEntityManager();
			em.getTransaction().begin();

			kmsKnowledge = em.merge(kmsKnowledge);
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				String id = kmsKnowledge.getKnowledgeId();
				if (findKmsKnowledge(id) == null) {
					throw new NonexistentEntityException("The kmsKnowledge with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<KmsKnowledge> findKmsKnowledgeByUser(KmsUser user) {
		return findKmsKnowledgeByUser(user, -1, -1);
	}

	public List<KmsKnowledge> findKmsKnowledgeByUser(KmsUser user, int maxResults, int firstResult) {
		EntityManager em = KmsDBFactory.getInstance().getEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<KmsKnowledge> c = cb.createQuery(KmsKnowledge.class);
			Root<KmsKnowledge> root = c.from(KmsKnowledge.class);
			Path<KmsUser> ex = root.get("kmsUser");
			Predicate p = cb.equal(ex, user);
			c.select(root).where(p);
			TypedQuery<KmsKnowledge> q = em.createQuery(c);

			if (firstResult > 0) {
				q.setFirstResult(firstResult);
			}

			if (maxResults > 0) {
				q.setMaxResults(maxResults);
			}
			List<KmsKnowledge> results = q.getResultList();

			for (KmsKnowledge k : results) {
				k.setArticles(k.getKmsArticleCollection().size());
				k.setBooks(k.getKmsBookCollection().size());
			}

			return results;
		} catch (Exception e) {
			e.getMessage();
			return null;
		} finally {
			em.close();
		}
	}

	public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
		EntityManager em = null;
		try {
			em = KmsDBFactory.getInstance().getEntityManager();
			em.getTransaction().begin();
			KmsKnowledge kmsKnowledge;
			try {
				kmsKnowledge = em.getReference(KmsKnowledge.class, id);
				kmsKnowledge.getKnowledgeId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The kmsKnowledge with id " + id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			Collection<KmsArticle> kmsArticles = kmsKnowledge.getKmsArticleCollection();
			for (KmsArticle kmsArticle : kmsArticles) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This KmsKnowledge (" + kmsKnowledge + ") cannot be destroyed since the KmsArticle " + kmsArticle
						+ " in its kmsArticleCollection field has a non-nullable kmsKnowledge field.");
			}
			Collection<KmsBook> kmsBooks = kmsKnowledge.getKmsBookCollection();
			for (KmsBook kmsBook : kmsBooks) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This KmsKnowledge (" + kmsKnowledge + ") cannot be destroyed since the KmsBook " + kmsBook
						+ " in its kmsBookCollection field has a non-nullable kmsKnowledge field.");
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			KmsUser kmsUser = kmsKnowledge.getKmsUser();
			if (kmsUser != null) {
				kmsUser.getKmsKnowledgeCollection().remove(kmsKnowledge);
				kmsUser = em.merge(kmsUser);
			}
			em.remove(kmsKnowledge);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public KmsKnowledge findKmsKnowledge(String id) {
		if (id == null) {
			return null;
		}
		EntityManager em = KmsDBFactory.getInstance().getEntityManager();
		try {
			return em.find(KmsKnowledge.class, id);
		} finally {
			em.close();
		}
	}

	public int getKmsKnowledgeCount() {
		EntityManager em = KmsDBFactory.getInstance().getEntityManager();
		try {
			CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
			Root<KmsKnowledge> rt = cq.from(KmsKnowledge.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			TypedQuery<Long> q = em.createQuery(cq);
			return q.getSingleResult().intValue();
		} finally {
			em.close();
		}
	}

	public List<KmsKnowledge> findKmsKnowledge(KmsConditionPair... kmsConditionPairs) {
		EntityManager em = KmsDBFactory.getInstance().getEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<KmsKnowledge> c = cb.createQuery(KmsKnowledge.class);
			Root<KmsKnowledge> root = c.from(KmsKnowledge.class);

			CriteriaQuery<KmsKnowledge> cq = c.select(root);

			for (KmsConditionPair kmsConditionPair : kmsConditionPairs) {
				Path<KmsUser> ex = root.get(kmsConditionPair.getParameter());
				Predicate p = cb.equal(ex, kmsConditionPair.getValue());
				cq = cq.where(p);
			}

			TypedQuery<KmsKnowledge> q = em.createQuery(cq);
			// q.setMaxResults(maxResults);
			// q.setFirstResult(firstResult);
			List<KmsKnowledge> results = q.getResultList();
			return results;
		} finally {
			em.close();
		}
	}
}
