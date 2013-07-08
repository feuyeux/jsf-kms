package org.feuyeux.air.jsf.kms.bean.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.feuyeux.air.jsf.kms.dao.jpa2.KmsKnowledgeDao;
import org.feuyeux.air.jsf.kms.entity.KmsKnowledge;
import org.feuyeux.air.jsf.kms.entity.KmsUser;
/**
 * @author feuyeux@gmail.com
 * @version 2.0
 */
@ManagedBean
@RequestScoped
public class KnowledgeItems {
	private KmsKnowledgeDao dao;
	private List<SelectItem> items;

	public KnowledgeItems() {

		Map<String, Object> sessions = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		KmsUser kmsUser = (KmsUser) sessions.get("kmsUser");
		dao = new KmsKnowledgeDao();
		List<KmsKnowledge> kks = dao.findKmsKnowledgeByUser(kmsUser, -1, -1);

		if (kks != null && kks.size() > 0) {
			items = new ArrayList<SelectItem>();
			SelectItem item0 = new SelectItem();
			item0.setLabel("");
			item0.setValue("");
			items.add(item0);

			for (KmsKnowledge kk : kks) {
				SelectItem item = new SelectItem();
				item.setLabel(kk.getName());
				item.setValue(kk.getKnowledgeId());

				items.add(item);
			}
		}
		System.identityHashCode(kmsUser);
	}

	public List<SelectItem> getItems() {
		return items;
	}
}