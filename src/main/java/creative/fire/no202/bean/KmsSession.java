package creative.fire.no202.bean;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import creative.fire.no202.dao.KmsUserDao;
import creative.fire.no202.dao.util.KmsPrimaryKey;
import creative.fire.no202.entity.KmsUser;

/**
 * @author feuyeux@gmail.com
 * @version 2.0
 */
@ManagedBean
@SessionScoped
public class KmsSession implements Serializable {
	private static final long serialVersionUID = -9145582920498318744L;
	private KmsUser kmsUser;
	private KmsUserDao dao;
	private String kmsUserName = "admin";// "rvcndev";
	private String kmsPassword = "admin";// "Devlab1";
	private Logger logger;

	@PostConstruct
	public void initialize() {
		logger = Logger.getLogger(KmsSession.class);
		dao = new KmsUserDao();
		if (null == dao.findByNameAndPassword(kmsUserName, kmsPassword)) {
			String userId = KmsPrimaryKey.USER + System.currentTimeMillis();
			try {
				dao.create(new KmsUser(userId, kmsUserName, kmsPassword));
			} catch (Exception e) {
				logger.error("create rvcndev failed");
			}
		}
	}

	public String getKmsUserName() {
		return kmsUserName;
	}

	public void setKmsUserName(String kmsUserName) {
		this.kmsUserName = kmsUserName;
	}

	public String getKmsPassword() {
		return kmsPassword;
	}

	public void setKmsPassword(String kmsPassword) {
		this.kmsPassword = kmsPassword;
	}

	public String login() {
		// TODO validate user data.
		kmsUser = dao.findByNameAndPassword(kmsUserName, kmsPassword);

		if (kmsUser == null)
			return null;

		Map<String, Object> sesions = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sesions.put("kmsUser", kmsUser);
		return "kmsArticle_list";// "kmsKnowledge_list";
	}

}
