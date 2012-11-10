package creative.fire.no202.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import creative.fire.no202.dao.jpa2.KmsUserDao;
import creative.fire.no202.entity.KmsUser;

/**
 * @author feuyeux@gmail.com
 * @version 2.0
 */
@ManagedBean
@ViewScoped
public class KmsUserBean implements Serializable {
	private static final long serialVersionUID = 4117114762838795047L;
	private KmsUser kmsUser = null;
	private List<KmsUser> kmsUserItems = null;
	private KmsUserDao dao = null;

	public KmsUserBean() {
		dao = new KmsUserDao();
	}

	public KmsUser getKmsUser() {
		return kmsUser;
	}

	public String listSetup() {
		return "kmsUser_list";
	}

	public String createSetup() {
		kmsUser = new KmsUser();
		return "kmsUser_create";
	}

	public List<KmsUser> getKmsUserItems() {
		if (kmsUserItems == null) {
			kmsUserItems = dao.findKmsUserEntities();
		}
		return kmsUserItems;
	}

}
