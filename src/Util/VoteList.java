package Util;

import java.io.Serializable;
import java.util.ArrayList;


public class VoteList implements Serializable {
	private static final long serialVersionUID=1L; 
	private int vote_num=0;
	private ArrayList<String> voteslist;
	private ArrayList<String> senderlist;
	public VoteList() {
		super();
	}
	public void setVoteNum(int num) {
		this.vote_num=num;
	}
	public int getVoteNum() {
		return vote_num;
	}
	public void setVoteslist(ArrayList<String> voteslist) {
		this.voteslist=voteslist;
	}
	public ArrayList<String> getVoteslist(){
		return voteslist;
	}
	public void setSenderlist(ArrayList<String> senderlist) {
		this.senderlist=senderlist;
	}
	public ArrayList<String> getSenderlist() {
		return senderlist;
	}
}
