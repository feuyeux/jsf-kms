package creative.fire.no202.bean;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import creative.fire.no202.dao.KmsBookDao;
import creative.fire.no202.dao.KmsKnowledgeDao;
import creative.fire.no202.dao.exceptions.PreexistingEntityException;
import creative.fire.no202.dao.util.KmsPrimaryKey;
import creative.fire.no202.entity.KmsBook;
import creative.fire.no202.entity.KmsKnowledge;
import creative.fire.no202.entity.KmsUser;

/**
 * 
 * @author luh
 */
@ManagedBean
@ViewScoped
public class KmsBookBean {
	private KmsBook kmsBook = null;
	private List<KmsBook> kmsBookItems = null;
	private KmsBookDao dao = null;
	private KmsKnowledgeDao kdao;
	private String kmsKnowledgeId;

	public KmsBookBean() {
		dao = new KmsBookDao();
		kdao = new KmsKnowledgeDao();
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String bId = params.get("bId");

		if (bId != null) {
			kmsBook = dao.findKmsBook(bId);
		} else {
			kmsBook = new KmsBook();
		}
	}

	public KmsBook getKmsBook() {
		return kmsBook;
	}

	public String create() throws PreexistingEntityException, Exception {

		KmsKnowledge kmsKnowledge = kdao.findKmsKnowledge(kmsKnowledgeId);
		kmsBook.setKmsKnowledge(kmsKnowledge);

		String bookId = KmsPrimaryKey.BOOK + System.nanoTime();
		kmsBook.setBookId(bookId);

		Map<String, Object> sesions = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		KmsUser kmsUser = (KmsUser) sesions.get("kmsUser");
		kmsBook.setKmsUser(kmsUser);

		dao.create(kmsBook);
		return "kmsBook_list?faces-redirect=true";
	}

	public String update() {
		return "kmsBook_list?faces-redirect=true";
	}

	public List<KmsBook> getKmsBookItems() {
		if (kmsBookItems == null) {
			kmsBookItems = dao.findKmsBookEntities();
		}
		return kmsBookItems;
	}

	public String getKmsKnowledgeId() {
		return kmsKnowledgeId;
	}

	public void setKmsKnowledgeId(String kmsKnowledgeId) {
		this.kmsKnowledgeId = kmsKnowledgeId;
	}
}
