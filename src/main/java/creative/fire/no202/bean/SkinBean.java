package creative.fire.no202.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author feuyeux@gmail.com
 * @version 2.0
 */
@ManagedBean
@SessionScoped
public class SkinBean implements Serializable {
	private static final long serialVersionUID = 1451272060974086484L;
	private String skin;

	// initialize skin
	@PostConstruct
	public void initialize() {
		skin = "blueSky";// "emeraldTown";
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}
}
