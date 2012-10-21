package creative.fire.no202.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

public class FileUploadBean implements Serializable {
	private static final long serialVersionUID = -8784272940213937971L;
	private ArrayList<KMSFile> files = new ArrayList<KMSFile>();
	private int uploadsAvailable = 20;
	private boolean autoUpload = true;
	private boolean useFlash = true;

	public FileUploadBean() {
		System.out.println("start a new file upload bean");
	}

	public int getSize() {
		if (files.size() > 0) {
			return files.size();
		} else {
			return 0;
		}
	}

	public void paint(OutputStream stream, Object object) throws IOException {
		Integer index = (Integer) object;
		if (getSize() == 0)
			return;
		KMSFile file = files.get(index);
		if (file != null)
			stream.write(file.getData());
	}

	// public void listener(UploadEvent event) throws Exception {
	public void listener(FileUploadEvent event) throws Exception {
		// UploadItem item = event.getUploadItem();
		UploadedFile file = event.getUploadedFile();
		KMSFile kmsfile = new KMSFile();

		kmsfile.setName(/* file.getFileName() */file.getName());

		if (file.getData() != null) {
			kmsfile.setLength(file.getData().length);
			kmsfile.setData(file.getData());
		}
		files.add(kmsfile);
		uploadsAvailable--;
	}

	public String clearUploadData() {
		files.clear();
		setUploadsAvailable(5);
		return null;
	}

	public long getTimeStamp() {
		return System.currentTimeMillis();
	}

	public ArrayList<KMSFile> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<KMSFile> files) {
		this.files = files;
	}

	public int getUploadsAvailable() {
		return uploadsAvailable;
	}

	public void setUploadsAvailable(int uploadsAvailable) {
		this.uploadsAvailable = uploadsAvailable;
	}

	public boolean isAutoUpload() {
		return autoUpload;
	}

	public void setAutoUpload(boolean autoUpload) {
		this.autoUpload = autoUpload;
	}

	public boolean isUseFlash() {
		return useFlash;
	}

	public void setUseFlash(boolean useFlash) {
		this.useFlash = useFlash;
	}

}