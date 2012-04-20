package creative.fire.no202.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import creative.fire.no202.dao.exceptions.IllegalOrphanException;
import creative.fire.no202.dao.exceptions.NonexistentEntityException;
import creative.fire.no202.dao.exceptions.PreexistingEntityException;
import creative.fire.no202.entity.KmsArticle;
import creative.fire.no202.entity.KmsBook;
import creative.fire.no202.entity.KmsKnowledge;
import creative.fire.no202.entity.KmsUser;
/**
 * @author feuyeux@gmail.com
 * @version 2.0
 */
public class KmsUserDao {
	public void create(KmsUser kmsUser) throws PreexistingEntityException, Exception {
		if (kmsUser.getKmsArticleCollection() == null) {
			kmsUser.setKmsArticleCollection(new ArrayList<KmsArticle>());
		}
		if (kmsUser.getKmsBookCollection() == null) {
			kmsUser.setKmsBookCollection(new ArrayList<KmsBook>());
		}
		if (kmsUser.getKmsKnowledgeCollection() == null) {
			kmsUser.setKmsKnowledgeCollection(new ArrayList<KmsKnowledge>());
		}
		EntityManager em = null;
		try {
			em = KmsDBFactory.getInstance().getEntityManager();
			em.getTransaction().begin();
			Collection<KmsArticle> attachedKmsArticleSet = new ArrayList<KmsArticle>();
			for (KmsArticle kmsArticleSetKmsArticleToAttach : kmsUser.getKmsArticleCollection()) {
				kmsArticleSetKmsArticleToAttach = em.getReference(kmsArticleSetKmsArticleToAttach.getClass(), kmsArticleSetKmsArticleToAttach.getArticleId());
				attachedKmsArticleSet.add(kmsArticleSetKmsArticleToAttach);
			}
			kmsUser.setKmsArticleCollection(attachedKmsArticleSet);
			Collection<KmsBook> attachedKmsBookSet = new ArrayList<KmsBook>();
			for (KmsBook kmsBookSetKmsBookToAttach : kmsUser.getKmsBookCollection()) {
				kmsBookSetKmsBookToAttach = em.getReference(kmsBookSetKmsBookToAttach.getClass(), kmsBookSetKmsBookToAttach.getBookId());
				attachedKmsBookSet.add(kmsBookSetKmsBookToAttach);
			}
			kmsUser.setKmsBookCollection(attachedKmsBookSet);
			Collection<KmsKnowledge> attachedKmsKnowledgeSet = new ArrayList<KmsKnowledge>();
			for (KmsKnowledge kmsKnowledgeSetKmsKnowledgeToAttach : kmsUser.getKmsKnowledgeCollection()) {
				kmsKnowledgeSetKmsKnowledgeToAttach = em.getReference(kmsKnowledgeSetKmsKnowledgeToAttach.getClass(),
						kmsKnowledgeSetKmsKnowledgeToAttach.getKnowledgeId());
				attachedKmsKnowledgeSet.add(kmsKnowledgeSetKmsKnowledgeToAttach);
			}
			kmsUser.setKmsKnowledgeCollection(attachedKmsKnowledgeSet);
			em.persist(kmsUser);
			for (KmsArticle kmsArticleSetKmsArticle : kmsUser.getKmsArticleCollection()) {
				KmsUser oldKmsUserOfKmsArticleSetKmsArticle = kmsArticleSetKmsArticle.getKmsUser();
				kmsArticleSetKmsArticle.setKmsUser(kmsUser);
				kmsArticleSetKmsArticle = em.merge(kmsArticleSetKmsArticle);
				if (oldKmsUserOfKmsArticleSetKmsArticle != null) {
					oldKmsUserOfKmsArticleSetKmsArticle.getKmsArticleCollection().remove(kmsArticleSetKmsArticle);
					oldKmsUserOfKmsArticleSetKmsArticle = em.merge(oldKmsUserOfKmsArticleSetKmsArticle);
				}
			}
			for (KmsBook kmsBookSetKmsBook : kmsUser.getKmsBookCollection()) {
				KmsUser oldKmsUserOfKmsBookSetKmsBook = kmsBookSetKmsBook.getKmsUser();
				kmsBookSetKmsBook.setKmsUser(kmsUser);
				kmsBookSetKmsBook = em.merge(kmsBookSetKmsBook);
				if (oldKmsUserOfKmsBookSetKmsBook != null) {
					oldKmsUserOfKmsBookSetKmsBook.getKmsBookCollection().remove(kmsBookSetKmsBook);
					oldKmsUserOfKmsBookSetKmsBook = em.merge(oldKmsUserOfKmsBookSetKmsBook);
				}
			}
			for (KmsKnowledge kmsKnowledgeSetKmsKnowledge : kmsUser.getKmsKnowledgeCollection()) {
				KmsUser oldKmsUserOfKmsKnowledgeSetKmsKnowledge = kmsKnowledgeSetKmsKnowledge.getKmsUser();
				kmsKnowledgeSetKmsKnowledge.setKmsUser(kmsUser);
				kmsKnowledgeSetKmsKnowledge = em.merge(kmsKnowledgeSetKmsKnowledge);
				if (oldKmsUserOfKmsKnowledgeSetKmsKnowledge != null) {
					oldKmsUserOfKmsKnowledgeSetKmsKnowledge.getKmsKnowledgeCollection().remove(kmsKnowledgeSetKmsKnowledge);
					oldKmsUserOfKmsKnowledgeSetKmsKnowledge = em.merge(oldKmsUserOfKmsKnowledgeSetKmsKnowledge);
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			if (findKmsUser(kmsUser.getUserId()) != null) {
				throw new PreexistingEntityException("KmsUser " + kmsUser + " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(KmsUser kmsUser) throws IllegalOrphanException, NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = KmsDBFactory.getInstance().getEntityManager();
			em.getTransaction().begin();
			KmsUser persistentKmsUser = em.find(KmsUser.class, kmsUser.getUserId());
			Collection<KmsArticle> kmsArticleSetOld = persistentKmsUser.getKmsArticleCollection();
			Collection<KmsArticle> kmsArticleSetNew = kmsUser.getKmsArticleCollection();
			Collection<KmsBook> kmsBookSetOld = persistentKmsUser.getKmsBookCollection();
			Collection<KmsBook> kmsBookSetNew = kmsUser.getKmsBookCollection();
			Collection<KmsKnowledge> kmsKnowledgeSetOld = persistentKmsUser.getKmsKnowledgeCollection();
			Collection<KmsKnowledge> kmsKnowledgeSetNew = kmsUser.getKmsKnowledgeCollection();
			List<String> illegalOrphanMessages = null;
			for (KmsArticle kmsArticleSetOldKmsArticle : kmsArticleSetOld) {
				if (!kmsArticleSetNew.contains(kmsArticleSetOldKmsArticle)) {
					if (illegalOrphanMessages == null) {
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain KmsArticle " + kmsArticleSetOldKmsArticle + " since its kmsUser field is not nullable.");
				}
			}
			for (KmsBook kmsBookSetOldKmsBook : kmsBookSetOld) {
				if (!kmsBookSetNew.contains(kmsBookSetOldKmsBook)) {
					if (illegalOrphanMessages == null) {
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain KmsBook " + kmsBookSetOldKmsBook + " since its kmsUser field is not nullable.");
				}
			}
			for (KmsKnowledge kmsKnowledgeSetOldKmsKnowledge : kmsKnowledgeSetOld) {
				if (!kmsKnowledgeSetNew.contains(kmsKnowledgeSetOldKmsKnowledge)) {
					if (illegalOrphanMessages == null) {
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain KmsKnowledge " + kmsKnowledgeSetOldKmsKnowledge + " since its kmsUser field is not nullable.");
				}
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			Collection<KmsArticle> attachedKmsArticleSetNew = new ArrayList<KmsArticle>();
			for (KmsArticle kmsArticleSetNewKmsArticleToAttach : kmsArticleSetNew) {
				kmsArticleSetNewKmsArticleToAttach = em.getReference(kmsArticleSetNewKmsArticleToAttach.getClass(),
						kmsArticleSetNewKmsArticleToAttach.getArticleId());
				attachedKmsArticleSetNew.add(kmsArticleSetNewKmsArticleToAttach);
			}
			kmsArticleSetNew = attachedKmsArticleSetNew;
			kmsUser.setKmsArticleCollection(kmsArticleSetNew);
			Collection<KmsBook> attachedKmsBookSetNew = new ArrayList<KmsBook>();
			for (KmsBook kmsBookSetNewKmsBookToAttach : kmsBookSetNew) {
				kmsBookSetNewKmsBookToAttach = em.getReference(kmsBookSetNewKmsBookToAttach.getClass(), kmsBookSetNewKmsBookToAttach.getBookId());
				attachedKmsBookSetNew.add(kmsBookSetNewKmsBookToAttach);
			}
			kmsBookSetNew = attachedKmsBookSetNew;
			kmsUser.setKmsBookCollection(kmsBookSetNew);
			Collection<KmsKnowledge> attachedKmsKnowledgeSetNew = new ArrayList<KmsKnowledge>();
			for (KmsKnowledge kmsKnowledgeSetNewKmsKnowledgeToAttach : kmsKnowledgeSetNew) {
				kmsKnowledgeSetNewKmsKnowledgeToAttach = em.getReference(kmsKnowledgeSetNewKmsKnowledgeToAttach.getClass(),
						kmsKnowledgeSetNewKmsKnowledgeToAttach.getKnowledgeId());
				attachedKmsKnowledgeSetNew.add(kmsKnowledgeSetNewKmsKnowledgeToAttach);
			}
			kmsKnowledgeSetNew = attachedKmsKnowledgeSetNew;
			kmsUser.setKmsKnowledgeCollection(kmsKnowledgeSetNew);
			kmsUser = em.merge(kmsUser);
			for (KmsArticle kmsArticleSetNewKmsArticle : kmsArticleSetNew) {
				if (!kmsArticleSetOld.contains(kmsArticleSetNewKmsArticle)) {
					KmsUser oldKmsUserOfKmsArticleSetNewKmsArticle = kmsArticleSetNewKmsArticle.getKmsUser();
					kmsArticleSetNewKmsArticle.setKmsUser(kmsUser);
					kmsArticleSetNewKmsArticle = em.merge(kmsArticleSetNewKmsArticle);
					if (oldKmsUserOfKmsArticleSetNewKmsArticle != null && !oldKmsUserOfKmsArticleSetNewKmsArticle.equals(kmsUser)) {
						oldKmsUserOfKmsArticleSetNewKmsArticle.getKmsArticleCollection().remove(kmsArticleSetNewKmsArticle);
						oldKmsUserOfKmsArticleSetNewKmsArticle = em.merge(oldKmsUserOfKmsArticleSetNewKmsArticle);
					}
				}
			}
			for (KmsBook kmsBookSetNewKmsBook : kmsBookSetNew) {
				if (!kmsBookSetOld.contains(kmsBookSetNewKmsBook)) {
					KmsUser oldKmsUserOfKmsBookSetNewKmsBook = kmsBookSetNewKmsBook.getKmsUser();
					kmsBookSetNewKmsBook.setKmsUser(kmsUser);
					kmsBookSetNewKmsBook = em.merge(kmsBookSetNewKmsBook);
					if (oldKmsUserOfKmsBookSetNewKmsBook != null && !oldKmsUserOfKmsBookSetNewKmsBook.equals(kmsUser)) {
						oldKmsUserOfKmsBookSetNewKmsBook.getKmsBookCollection().remove(kmsBookSetNewKmsBook);
						oldKmsUserOfKmsBookSetNewKmsBook = em.merge(oldKmsUserOfKmsBookSetNewKmsBook);
					}
				}
			}
			for (KmsKnowledge kmsKnowledgeSetNewKmsKnowledge : kmsKnowledgeSetNew) {
				if (!kmsKnowledgeSetOld.contains(kmsKnowledgeSetNewKmsKnowledge)) {
					KmsUser oldKmsUserOfKmsKnowledgeSetNewKmsKnowledge = kmsKnowledgeSetNewKmsKnowledge.getKmsUser();
					kmsKnowledgeSetNewKmsKnowledge.setKmsUser(kmsUser);
					kmsKnowledgeSetNewKmsKnowledge = em.merge(kmsKnowledgeSetNewKmsKnowledge);
					if (oldKmsUserOfKmsKnowledgeSetNewKmsKnowledge != null && !oldKmsUserOfKmsKnowledgeSetNewKmsKnowledge.equals(kmsUser)) {
						oldKmsUserOfKmsKnowledgeSetNewKmsKnowledge.getKmsKnowledgeCollection().remove(kmsKnowledgeSetNewKmsKnowledge);
						oldKmsUserOfKmsKnowledgeSetNewKmsKnowledge = em.merge(oldKmsUserOfKmsKnowledgeSetNewKmsKnowledge);
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				String id = kmsUser.getUserId();
				if (findKmsUser(id) == null) {
					throw new NonexistentEntityException("The kmsUser with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
		EntityManager em = null;
		try {
			em = KmsDBFactory.getInstance().getEntityManager();
			em.getTransaction().begin();
			KmsUser kmsUser;
			try {
				kmsUser = em.getReference(KmsUser.class, id);
				kmsUser.getUserId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The kmsUser with id " + id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			Collection<KmsArticle> kmsArticleSetOrphanCheck = kmsUser.getKmsArticleCollection();
			for (KmsArticle kmsArticleSetOrphanCheckKmsArticle : kmsArticleSetOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This KmsUser (" + kmsUser + ") cannot be destroyed since the KmsArticle " + kmsArticleSetOrphanCheckKmsArticle
						+ " in its kmsArticleSet field has a non-nullable kmsUser field.");
			}
			Collection<KmsBook> kmsBookSetOrphanCheck = kmsUser.getKmsBookCollection();
			for (KmsBook kmsBookSetOrphanCheckKmsBook : kmsBookSetOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This KmsUser (" + kmsUser + ") cannot be destroyed since the KmsBook " + kmsBookSetOrphanCheckKmsBook
						+ " in its kmsBookSet field has a non-nullable kmsUser field.");
			}
			Collection<KmsKnowledge> kmsKnowledgeSetOrphanCheck = kmsUser.getKmsKnowledgeCollection();
			for (KmsKnowledge kmsKnowledgeSetOrphanCheckKmsKnowledge : kmsKnowledgeSetOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This KmsUser (" + kmsUser + ") cannot be destroyed since the KmsKnowledge " + kmsKnowledgeSetOrphanCheckKmsKnowledge
						+ " in its kmsKnowledgeSet field has a non-nullable kmsUser field.");
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			em.remove(kmsUser);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<KmsUser> findKmsUserEntities() {
		return findKmsUserEntities(true, -1, -1);
	}

	public List<KmsUser> findKmsUserEntities(int maxResults, int firstResult) {
		return findKmsUserEntities(false, maxResults, firstResult);
	}

	private List<KmsUser> findKmsUserEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = KmsDBFactory.getInstance().getEntityManager();
		try {
			CriteriaQuery<KmsUser> cq = em.getCriteriaBuilder().createQuery(KmsUser.class);
			cq.select(cq.from(KmsUser.class));
			TypedQuery<KmsUser> q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	public KmsUser findKmsUser(String id) {
		EntityManager em = KmsDBFactory.getInstance().getEntityManager();
		try {
			return em.find(KmsUser.class, id);
		} finally {
			em.close();
		}
	}

	public KmsUser findByNameAndPassword(String name, String password) {
		EntityManager em = KmsDBFactory.getInstance().getEntityManager();
		try {
			KmsUser user = em.createNamedQuery("findByNameAndPassword", KmsUser.class).setParameter("username", name).setParameter("password", password)
					.getSingleResult();
			return user;
		} catch (Exception e) {
			return null;
		}
	}

	public int getKmsUserCount() {
		EntityManager em = KmsDBFactory.getInstance().getEntityManager();
		try {
			CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
			Root<KmsUser> rt = cq.from(KmsUser.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			TypedQuery<Long> q = em.createQuery(cq);
			return q.getSingleResult().intValue();
		} finally {
			em.close();
		}
	}
}