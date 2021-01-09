package Entity;
import java.io.Serializable;

import javax.swing.text.SimpleAttributeSet;
public class Message implements Serializable{
	private String message,filename;
	private int length;
	private SimpleAttributeSet attrSet = null;
	private int type=0;//设置消息类型 0为文本信息 1为图片信息
	private byte[] photo;
	
	public Message(int length,String message,SimpleAttributeSet attrSet) {
		this.length=length;
		this.message=message;
		this.attrSet=attrSet;
	}
	public Message() {
		super();
	}
	public String setMessage(String message) {
		return this.message=message;
	}
	public String getMessage() {
		return message;
	}
	public int setLength(int length) {
		return this.length=length;
	}
	public int getLength() {
		return length;
	}
	public SimpleAttributeSet setAttrSet(SimpleAttributeSet attrSet) {
		return this.attrSet=attrSet;
	}
	public SimpleAttributeSet getAttrSet() {
		return attrSet;
	}
	public int setType(int type) {
		return this.type=type;
	}
	public int getType() {
		return type;
	}
	public byte[] setPhoto(byte[] bt) {
		return photo=bt;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public String setFileName(String name) {
		return this.filename=name;
	}
	public String getFileName() {
		return filename;
	}
}
