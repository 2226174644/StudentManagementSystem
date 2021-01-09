package Entity;

import java.io.Serializable;

public class FileEntity implements Serializable{
	private static final long serialVersionUID=1L;
	private String filename,path;
	public FileEntity() {
		super();
	}
	public FileEntity(String filename,String path) {
		this.filename=filename;
		this.path=path;
	}
	public String setFileName(String name) {
		return this.filename=name;
	}
	public String getFileName() {
		return filename;
	}
	public String setPath(String path) {
		return this.path=path;
	}
	public String getPath() {
		return path;
	}
}
