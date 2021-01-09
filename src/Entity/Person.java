package Entity;

import java.io.Serializable;
import javax.swing.ImageIcon;

public class Person implements Serializable {
	
	private static final long serializableVersionUID=1L; //java������֤����Ҫ���ڰ汾���ơ�
	public int user_id;//�û���
	public String user_name="zxz";//�ǳ�
	public int user_sex=-1;// 0 �� 1 Ů
	public ImageIcon user_avata;//�û�ͷ��
	
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
