package creative.fire.no202.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author feuyeux@gmail.com
 * @version 2.0
 */
@Entity
@Table(name = "kms_book",  schema = "kms")
public class KmsBook implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "bookId")
	private String bookId;

	@Column(name = "bookname")
	private String bookname;

	@Column(name = "author")
	private String author;

	@Column(name = "press")
	private String press;

	@Column(name = "publishTime")
	@Temporal(TemporalType.DATE)
	private Date publishTime;

	@Column(name = "location")
	private String location;

	@Column(name = "buyTime")
	@Temporal(TemporalType.DATE)
	private Date buyTime;

	@JoinColumn(name = "knowledgeId", referencedColumnName = "knowledgeId")
	@ManyToOne(optional = false)
	private KmsKnowledge kmsKnowledge;

	@JoinColumn(name = "userId", referencedColumnName = "userId")
	@ManyToOne(optional = false)
	private KmsUser kmsUser;

	public KmsBook() {
	}

	public KmsBook(String bookId) {
		this.bookId = bookId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public KmsKnowledge getKmsKnowledge() {
		return kmsKnowledge;
	}

	public void setKmsKnowledge(KmsKnowledge kmsKnowledge) {
		this.kmsKnowledge = kmsKnowledge;
	}

	public KmsUser getKmsUser() {
		return kmsUser;
	}

	public void setKmsUser(KmsUser kmsUser) {
		this.kmsUser = kmsUser;
	}

	public Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	@Override
	public int hashCode() {
		int hash = 37;
		hash += (bookId != null ? bookId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (object == this)
			return true;
		if (KmsBook.class != object.getClass()) {
			return false;
		}
		KmsBook other = (KmsBook) object;
		if ((this.bookId == null && other.bookId != null) || (this.bookId != null && !this.bookId.equals(other.bookId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "creative.fire.no202.entity.KmsBook[bookId=" + bookId + "]";
	}

}
