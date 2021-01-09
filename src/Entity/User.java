package Entity;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;
public class User extends Person implements Serializable{
	private static final long serialVersionUID=1L;
	private String user_pwd;
	private String ques_to_retrieve_the_pwd;
	private String ans_to_retrieve_the_pwd;
	private int friends_num=0;
	private int notice_num=0;
	private String root="";
	private ArrayList<String> friendslist;
	private ArrayList<String> noticelist;
	public User() {
		super();
	}
	
	
	
	public User(String user_name, String user_pwd) {
		this.user_name=user_name;
		this.user_pwd=user_pwd;
	}

	public String getUserpwd() {
		return user_pwd;
	}

	public String setUserpwd(String password) {
		return this.user_pwd=password;
	}
	
	public String setUserName(String name) {
		return this.user_name=name;
	}
	public String getUserName() {
		return user_name;
	}
	public String getUserQuestion() {
		return ques_to_retrieve_the_pwd;
	}
	
	public String setUserQuestion(String question) {
		return this.ques_to_retrieve_the_pwd=question;
	}
	
	public String getUserAnswer() {
		return this.ans_to_retrieve_the_pwd;
	}
	
	public String setUserAnswer(String answer) {
		return this.ans_to_retrieve_the_pwd = answer;
	}
	
	public int setUserSex(int sex) {
		return this.user_sex=sex;
	}
	
	public ImageIcon setUserAvata(ImageIcon user_avata) {
		return this.user_avata=user_avata;
	}
	
	public int getFriendsNum() {
		return friends_num;
	}
	
	public void setFriendsNum(int friends_num) {
		this.friends_num=friends_num;
	}
	
	public void setFriendsList(ArrayList<String> friendList) {
		this.friendslist=new ArrayList<String>(friendList);
		this.friends_num=friendList.size();
	}
	
	public ArrayList<String> getFriend(){
		return friendslist;
	}
	
	public int getNoticeNum() {
		return notice_num;
	}
	
	public void setNoticeNum(int notice_num) {
		this.notice_num=notice_num;
	}
	
	public void setNoticeList(ArrayList<String> noticeList) {
		this.noticelist=new ArrayList<String>(noticeList);
		this.notice_num=noticeList.size();
	}
	
	public ArrayList<String> getNotice(){
		return noticelist;
	}
	public String setRoot(String root) {
		return this.root=root;
	}
	public String getRoot() {
		return root;
	}
}









