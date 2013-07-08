package org.feuyeux.air.jsf.kms.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.feuyeux.air.jsf.kms.dao.exceptions.PreexistingEntityException;
import org.feuyeux.air.jsf.kms.dao.jpa2.KmsKnowledgeDao;
import org.feuyeux.air.jsf.kms.dao.util.KmsPrimaryKey;
import org.feuyeux.air.jsf.kms.entity.KmsKnowledge;
import org.feuyeux.air.jsf.kms.entity.KmsUser;

/**
 * @author feuyeux@gmail.com
 * @version 2.0
 */
@ManagedBean
@ViewScoped
public class KmsKnowledgeBean implements Serializable{
	private static final long serialVersionUID = 1212309347807432892L;
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
