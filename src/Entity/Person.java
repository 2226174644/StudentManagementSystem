package Entity;

import java.io.Serializable;
import javax.swing.ImageIcon;

public class Person implements Serializable {
	
	private static final long serializableVersionUID=1L; //java类的身份证。主要用于版本控制。
	public int user_id;//用户名
	public String user_name="zxz";//昵称
	public int user_sex=-1;// 0 男 1 女
	public ImageIcon user_avata;//用户头像
	
	public Person() {
		super();
	}
	public Person(int user_id,String user_name) {
		super();
		this.user_id=user_id;
		this.user_name=user_name;
	}
	
	public int getUserId() {
		return user_id;
	}
	
	public int getUserSex() {
		return user_sex;
	}
	public ImageIcon getUserAvata() {
		return user_avata;
	}
}
