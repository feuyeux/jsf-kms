package creative.fire.no202.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author feuyeux@gmail.com
 * @version 2.0
 */
@Entity
@Table(name = "kms_article", schema = "kms")
public class KmsArticle implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "articleId")
	private String articleId;

	@Basic(optional = false)
	@Column(name = "title")
	private String title;

	@Column(name = "summary")
	private String summary;

	@Basic(optional = false)
	@Lob
	@Column(name = "content")
	private String content;

	@Column(name = "attachment")
	private String attachment;

	@Basic(optional = false)
	@Column(name = "insertTime")
	@Temporal(TemporalType.DATE)
	private Date insertTime;

	@JoinColumn(name = "userId", referencedColumnName = "userId")
	@ManyToOne(optional = false)
	private KmsUser kmsUser;

	@JoinColumn(name = "knowledgeId", referencedColumnName = "knowledgeId")
	@ManyToOne(optional = false)
	private KmsKnowledge kmsKnowledge;

	public KmsArticle() {
	}

	public KmsArticle(String articleId) {
		this.articleId = articleId;
	}

	public KmsArticle(String articleId, String title, String content, Date insertTime) {
		this.articleId = articleId;
		this.title = title;
		this.content = content;
		this.insertTime = insertTime;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public KmsUser getKmsUser() {
		return kmsUser;
	}

	public void setKmsUser(KmsUser kmsUser) {
		this.kmsUser = kmsUser;
	}

	public KmsKnowledge getKmsKnowledge() {
		return kmsKnowledge;
	}

	public void setKmsKnowledge(KmsKnowledge kmsKnowledge) {
		this.kmsKnowledge = kmsKnowledge;
	}

	@Override
	public int hashCode() {
		int hash = 37;
		hash += (articleId != null ? articleId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (object == this)
			return true;
		if (KmsArticle.class != object.getClass()) {
			return false;
		}
		
		KmsArticle other = (KmsArticle) object;
		if ((this.articleId == null && other.articleId != null) || (this.articleId != null && !this.articleId.equals(other.articleId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "creative.fire.no202.entity.KmsArticle[articleId=" + articleId + "]";
	}

}
