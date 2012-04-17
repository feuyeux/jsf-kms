package creative.fire.no202.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import creative.fire.no202.dao.KmsKnowledgeDao;
import creative.fire.no202.dao.exceptions.PreexistingEntityException;
import creative.fire.no202.dao.util.KmsPrimaryKey;
import creative.fire.no202.entity.KmsKnowledge;
import creative.fire.no202.entity.KmsUser;

/**
 * 
 * @author luh
 */
@ManagedBean
@ViewScoped
public class KmsKnowledgeBean {
	private KmsKnowledge kmsKnowledge = null;
	private List<KmsKnowledge> kmsKnowledgeItems = null;
	private KmsKnowledgeDao dao = null;

	public KmsKnowledgeBean() {
		dao = new KmsKnowledgeDao();
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String kId = params.get("kId");

		if (kId != null) {
			kmsKnowledge = dao.findKmsKnowledge(kId);
		} else {
			kmsKnowledge = new KmsKnowledge();
		}
	}

	public KmsKnowledge getKmsKnowledge() {
		return kmsKnowledge;
	}

	public void setKmsKnowledge(KmsKnowledge kmsKnowledge) {
		this.kmsKnowledge = kmsKnowledge;
	}

	public String updateKnowledge() throws PreexistingEntityException, Exception {
		dao.edit(kmsKnowledge);
		return "kmsKnowledge_list?faces-redirect=true";
	}

	public String storeKnowledge() throws PreexistingEntityException, Exception {
		kmsKnowledge.setTouchTime(new Date());
		Map<String, Object> sesions = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		KmsUser kmsUser = (KmsUser) sesions.get("kmsUser");
		kmsKnowledge.setKmsUser(kmsUser);

		String kId = KmsPrimaryKey.KNOWLEDEG + System.nanoTime();
		kmsKnowledge.setKnowledgeId(kId);

		dao.create(kmsKnowledge);
		return "kmsKnowledge_list?faces-redirect=true";
	}

	public boolean isShow() {
		kmsKnowledgeItems = getKmsKnowledgeItems();
		return kmsKnowledgeItems != null && kmsKnowledgeItems.size() > 0;
	}

	public List<KmsKnowledge> getKmsKnowledgeItems() {
		if (kmsKnowledgeItems == null) {

			Map<String, Object> sesions = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			KmsUser kmsUser = (KmsUser) sesions.get("kmsUser");

			kmsKnowledgeItems = dao.findKmsKnowledgeByUser(kmsUser);
		}
		return kmsKnowledgeItems;
	}
}
