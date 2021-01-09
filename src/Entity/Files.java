package Entity;
import java.io.Serializable;
import java.util.ArrayList;
public class Files implements Serializable{
	private static final long serialVersionUID=1L; 
	private int file_num=0;
	private int path_num=0;
	private ArrayList<String> fileslist;
	private ArrayList<String> pathlist;
	public Files() {
		super();
	}
	public void setFileNum(int num) {
		 this.file_num=num;
	}
	public int getFileNum() {
		return file_num;
	}
	public void setFilesList(ArrayList<String> friendList) {
		this.fileslist=new ArrayList<String>(friendList);
		this.file_num=friendList.size();
	}
	
	public ArrayList<String> getFiles(){
		return fileslist;
	}
	public void setPath(ArrayList<String> path) {
		 this.pathlist=path;
		 this.path_num=pathlist.size();
	}
	public ArrayList<String> getPath() {
		return pathlist;
	}
}