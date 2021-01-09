package Entity;

import java.io.Serializable;

public class Vote implements Serializable{
	private static final long serialVersionUID=1L;
	private String vote_name,sender,constent,tips="建议有：",username;
	private int agree_num,disagree_num,flag=0/*0为未投票，1为投过票*/,chooseflag=0/* 1为赞成，2为反对*/;
	public Vote() {
		// TODO 自动生成的构造函数存根
	}
	public Vote(String notice_name,String sender,String constent) {
		this.vote_name=notice_name;
		this.sender=sender;
		this.constent=constent;
	}
	
	public String setSender(String sender) {
		return this.sender=sender;
	}
	public String getSender() {
		return sender;
	}
	public String setVoteName(String name) {
		return this.vote_name=name;
	}
	public String getVoteName() {
		return vote_name;
	}
	public String setConstent(String constent) {
		return this.constent=constent;
	}
	public String getConstent() {
		return  constent;
	}
	public String setTips(String name) {
		return this.tips=name;
	}
	public String getTips() {
		return tips;
	}
	public String setUserName(String name) {
		return this.username=name;
	}
	public String getUserName() {
		return username;
	}
	public void setAgreeNum(int num) {
		this.agree_num=num;
	}
	public int getAgreeNum() {
		return agree_num;
	}
	public void setDisagreeNum(int num) {
		this.disagree_num=num;
	}
	public int getDisgreeNum() {
		return disagree_num;
	}
	public void setFlag(int num) {
		this.flag=num;
	}
	public int getFlag() {
		return flag;
	}
	public void setChooseFlag(int num) {
		this.chooseflag=num;
	}
	public int getChooseFlag() {
		return chooseflag;
	}
}