package Entity;

import java.io.Serializable;

public class Note implements Serializable{
	private static final long serialVersionUID=1L;
	private String notice_name,sender,constent;
	public Note() {
		// TODO 自动生成的构造函数存根
	}
	public Note(String notice_name,String sender,String constent) {
		this.notice_name=notice_name;
		this.sender=sender;
		this.constent=constent;
	}
	public String setSender(String sender) {
		return this.sender=sender;
	}
	public String getSender() {
		return sender;
	}
	public String setNoticeName(String notice_name) {
		return this.notice_name=notice_name;
	}
	public String getNoticeName() {
		return notice_name;
	}
	public String setConstent(String constent) {
		return this.constent=constent;
	}
	public String getConstent() {
		return  constent;
	}
}
