package org.feuyeux.air.jsf.kms.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author feuyeux@gmail.com
 * @version 2.0
 */
@Entity
@Table(name = "kms_user", schema = "air_kms")
@NamedQuery(name = "findByNameAndPassword", query = "SELECT k FROM KmsUser k " + "WHERE k.username = :username and k.password = :password")
public class KmsUser implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "userId")
	private String userId;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kmsUser")
	private Collection<KmsArticle> kmsArticleCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kmsUser")
	private Collection<KmsBook> kmsBookCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kmsUser")
	private Collection<KmsKnowledge> kmsKnowledgeCollection;

	public KmsUser() {
	}

	public KmsUser(String userId) {
		this.userId = userId;
	}

	public KmsUser(String userId, String username, String password) {
		this.userId = userId;
		this.username = username;
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<KmsArticle> getKmsArticleCollection() {
		return kmsArticleCollection;
	}

	public void setKmsArticleCollection(Collection<KmsArticle> kmsArticleCollection) {
		this.kmsArticleCollection = kmsArticleCollection;
	}

	public Collection<KmsBook> getKmsBookCollection() {
		return kmsBookCollection;
	}

	public void setKmsBookCollection(Collection<KmsBook> kmsBookCollection) {
		this.kmsBookCollection = kmsBookCollection;
	}

	public Collection<KmsKnowledge> getKmsKnowledgeCollection() {
		return kmsKnowledgeCollection;
	}

	public void setKmsKnowledgeCollection(Collection<KmsKnowledge> kmsKnowledgeCollection) {
		this.kmsKnowledgeCollection = kmsKnowledgeCollection;
	}

	@Override
	public int hashCode() {
		int hash = 37;
		hash += userId != null ? userId.hashCode() : 0;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (object == this) {
			return true;
		}
		if (KmsUser.class != object.getClass()) {
			return false;
		}
		KmsUser other = (KmsUser) object;
		if (userId == null && other.userId != null || userId != null && !userId.equals(other.userId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("userId=").append(userId);
		buf.append(", username=").append(username);
		buf.append(", password=").append(password);
		buf.append(", kmsArticleCollection size=").append(kmsArticleCollection == null ? 0 : kmsArticleCollection.size());
		buf.append(", kmsBookCollection=").append(kmsBookCollection == null ? 0 : kmsBookCollection.size());
		buf.append(", kmsKnowledgeCollection=").append(kmsKnowledgeCollection == null ? 0 : kmsKnowledgeCollection.size());
		return buf.toString();
	}
}
