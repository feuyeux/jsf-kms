package creative.fire.no202.util;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class DateTest {
	private Date start;
	private Date end;

	private Date endStart = new Date();

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public void oo() {
		endStart = start;
	}

	public Date getEndStart() {
		return endStart;
	}

	public void setEndStart(Date endStart) {
		this.endStart = endStart;
	}
}