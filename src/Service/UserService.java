package Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Entity.FileEntity;
import Entity.Files;
import Entity.Note;
import Entity.User;
import Entity.Vote;
import Util.DBHelper;
import Util.VoteList;

public class UserService {
	
	//login��֤�˺�����
	public boolean checkUser(User user) {
		PreparedStatement stmt = null; //PreparedStatement������ִ��SQL��ѯ����API֮һ
		Connection conn = null; //���ض����ݿ�����ӣ��Ự������������������ִ�� SQL ��䲢���ؽ��
		ResultSet rs = null; //�������в�ѯ������ص�һ�ֶ��󣬿���˵�������һ���洢��ѯ����Ķ��󣬵��ǽ���������������д洢�Ĺ��ܣ���ͬʱ�����в������ݵĹ��ܣ�������ɶ����ݵĸ��µ�
		conn = DBHelper.getConnection();
		String sql = "select * from tb_user where user_name =? and user_pwd =?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getUserName());
			stmt.setString(2, user.getUserpwd());
			rs = stmt.executeQuery();
			if(rs.next()) {
				
				return true;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	//��½����ͻ��˷�������Ѻ͹����б�
	public User getFriendsList(User user) {
		PreparedStatement stmt = null; 
		PreparedStatement stmt2 = null; 
		PreparedStatement stmt3 = null; 
		Connection conn = null; 
		ResultSet rs = null; 
		ResultSet rs2 = null; 
		ResultSet rs3 = null; 
		conn = DBHelper.getConnection();
		String sql = "select * from " + user.getUserName() + "_friends";
		ArrayList<String> friendslist = new ArrayList<String>(); //���������Ѳ�����20��
		String sql2 = "select * from noticeList" ;
		ArrayList<String> noticelist = new ArrayList<String>(); //������蹫�治����20��
		//Ϊ��֪Ȩ��
		String sql3="select * from tb_user where user_name =?" ;
		try {
			System.out.println("userservice���� "+user.getUserName());
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			//�����б��ȡ
			int friendscount = 0;
			while(rs.next()) {
				friendslist.add(rs.getString(1));	//��ȡ����name
				friendscount++;
			}
			System.out.println("userservice ���� "+user.getUserName());
			stmt2 = conn.prepareStatement(sql2);
			rs2 = stmt2.executeQuery();
			//�����б��ȡ
			int noticecount = 0;
			while(rs2.next()) {
				noticelist.add(rs2.getString(1));	//��ȡ����name
				noticecount++;
			}
			//System.out.println("userservice Ȩ�� "+user.getUserName());
			//�û�Ȩ�޻�ȡ
			stmt3 = conn.prepareStatement(sql3);
			stmt3.setString(1, user.getUserName());
			rs3=stmt3.executeQuery();
			String temp ="";
			if(rs3.next()) {
				 temp=rs3.getString("user_root");
			}
			System.out.println("user_root "+temp);
			//
			user.setRoot(temp);
			user.setFriendsNum(friendscount);
			user.setFriendsList(friendslist);
			user.setNoticeNum(noticecount);
			user.setNoticeList(noticelist);
			//System.out.println("????????????????? "+rs2.getString(1));
			return user;
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	
	//�û�ע��
	public boolean registerUser(User user) {
		PreparedStatement stmt1 = null; //PreparedStatement������ִ��SQL��ѯ����API֮һ
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		Connection conn = null; 
		ResultSet rs = null; 
		int insertFlag = 0;
		int creatFlag = 0;
		conn = DBHelper.getConnection();
		String sql = "select * from tb_user where user_name =?";
		String insertusersql = "insert into tb_user (user_name, user_pwd, user_question, user_ans,user_sex) values(?, ?, ?, ?,?)";
		String creatfriendstabsql = "CREATE TABLE " + user.getUserName() + "_friends " + "( name VARCHAR(45) NOT NULL, PRIMARY KEY (name))";
		try {
			stmt1 = conn.prepareStatement(sql);
			stmt1.setString(1, user.getUserName());
			rs = stmt1.executeQuery();
			if(rs.next()) {
				System.out.println("���û��Ѵ���" + user.getUserName() + "***");
				//�û��ѱ�ע��
				return false;
			}
			else {
				System.out.println("���û�������" + user.getUserName() + "***");
				//���û����������
				stmt2 = conn.prepareStatement(insertusersql);
				stmt2.setString(1, user.getUserName());
				stmt2.setString(2, user.getUserpwd());
				stmt2.setString(3, user.getUserQuestion());
				stmt2.setString(4, user.getUserAnswer());
				stmt2.setLong(5, user.getUserSex());
				insertFlag = stmt2.executeUpdate();
				System.out.println("����в�������" + user.getUserName() + "***" + insertFlag);
				//�������ѱ�
				stmt3 = conn.prepareStatement(creatfriendstabsql);
				creatFlag = stmt3.executeUpdate();
				
				System.out.println("������" + user.getUserName() + "***" + creatFlag);
				if(insertFlag == 1) {
					return true;
				}
				
				System.out.println("������" + user.getUserName() + "***");
				//return true;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt1 != null) {
					stmt1.close();
				}
				if(stmt2 != null) {
					stmt2.close();
				}
				if(stmt3 != null) {
					stmt3.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	//��Ӻ���
	public boolean addFriend(String sender, String receiver) {
		PreparedStatement stmt1 = null; 
		PreparedStatement stmt2 = null; 
		Connection conn = null; 
		int updateResult1 = 0;
		int updateResult2 = 0;
		conn = DBHelper.getConnection();
		String sql1 = "insert into " + sender + "_friends (name) values(?)";//�������ߵĺ��ѱ��м��Ͻ�����
		//String sql1 = "insert into ? (name) values(?)";
		String sql2 = "insert into " + receiver + "_friends (name) values(?)";
		//String sql2 = "insert into ? (name) values(?)";
		try {
			stmt1 = conn.prepareStatement(sql1);
			stmt2 = conn.prepareStatement(sql2);
			stmt1.setString(1, receiver);
			stmt2.setString(1, sender);
			System.out.println("userservice  sender"+sender+"reciver"+receiver);
			updateResult1 = stmt1.executeUpdate();
			updateResult2 = stmt2.executeUpdate();
			if(updateResult1 == 1 && updateResult2 == 1) {
				return true;
			}
			else {
				//ϣ���ܲ��룬 ������벻�ɹ��Ļ���Ӧ�ý�����ɹ���ɾ��....���ﲻ��������
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(stmt1 != null) {
					stmt1.close();
				}
				if(stmt2 != null) {
					stmt2.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	//�޸���Ϣ
	public boolean changeInfo(User user) {
		return false;
	}
	
	//�޸����� ��������
	public boolean changePassword(User user) {
		PreparedStatement stmt1 = null; //PreparedStatement������ִ��SQL��ѯ����API֮һ
		PreparedStatement stmt2 = null; //PreparedStatement������ִ��SQL��ѯ����API֮һ
		Connection conn = null; //���ض����ݿ�����ӣ��Ự������������������ִ�� SQL ��䲢���ؽ��
		ResultSet rs = null; //�������в�ѯ������ص�һ�ֶ��󣬿���˵�������һ���洢��ѯ����Ķ��󣬵��ǽ���������������д洢�Ĺ��ܣ���ͬʱ�����в������ݵĹ��ܣ�������ɶ����ݵĸ��µ�
		int updateFlag = 0;
		conn = DBHelper.getConnection();
		//String sql = "select * from tb_user where user_question =? and user_ans =?";
		String updatesql = "update tb_user set user_pwd =? where user_name = ?";
		
		try {
			
			//if(rs.next()) {
				
				stmt2 = conn.prepareStatement(updatesql);
				stmt2.setString(1, user.getUserpwd());
				stmt2.setString(2, user.getUserName());
				updateFlag = stmt2.executeUpdate();
				if(updateFlag == 1)
				  return true;
			//}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt1 != null) {
					stmt1.close();
				}
				if(stmt2 != null) {
					stmt2.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	//����û��������Ϣ
	public User getUser(User user) {
		PreparedStatement stmt1 = null; 
		PreparedStatement stmt2 = null; 
		Connection conn = null; 
		ResultSet rs = null; 
		conn = DBHelper.getConnection();
		String sql = "select * from tb_user where user_name =?";
		try {
			
			stmt1 = conn.prepareStatement(sql);
			stmt1.setString(1, user.getUserName());
			rs = stmt1.executeQuery();
			if(rs.next()) {
				user.setUserName(rs.getString("user_name"));
				user.setUserAnswer(rs.getString("user_ans"));
				user.setUserQuestion(rs.getString("user_question"));
				//user.setRoot(rs.getString("user_root"));
				return user; 
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt1 != null) {
					stmt1.close();
				}
				if(stmt2 != null) {
					stmt2.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	//��ȡĳ������
	public Note getNote(Note note) {
		// TODO �Զ����ɵķ������
		PreparedStatement stmt1 = null; 
		PreparedStatement stmt2 = null; 
		Connection conn = null; 
		ResultSet rs = null; 
		conn = DBHelper.getConnection();
		String sql = "select * from noticeList where notice_name =?";
		try {
			stmt1 = conn.prepareStatement(sql);
			stmt1.setString(1, note.getNoticeName());
			rs = stmt1.executeQuery();
			if(rs.next()) {
				note.setNoticeName(rs.getString("notice_name"));
				note.setSender(rs.getString("sender"));
				note.setConstent(rs.getString("constent"));
				
				return note; 
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt1 != null) {
					stmt1.close();
				}
				if(stmt2 != null) {
					stmt2.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	//�������� 
	public boolean sendNotice(Note note) {
		// TODO �Զ����ɵķ������
		PreparedStatement stmt=null;
		Connection conn=null;
		ResultSet rs=null;
		conn=DBHelper.getConnection();
		int insertFlag=0;
		String sql="insert into noticeList (notice_name,sender,constent)values(?, ?, ?)";
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, note.getNoticeName());
			stmt.setString(2, note.getSender());
			stmt.setString(3, note.getConstent());
			insertFlag=stmt.executeUpdate();
			System.out.println("����в�������" + note.getNoticeName() + "***" + note.getConstent());
			if(insertFlag==1) {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
	}
		return false;
	}

	public Files getFilesList(Files files) {
		PreparedStatement stmt = null; 
		
		Connection conn = null; 
		ResultSet rs = null; 
		
		conn = DBHelper.getConnection();
		String sql = "select * from filelist";
		ArrayList<String> filelist = new ArrayList<String>();
		ArrayList<String> pathlist = new ArrayList<String>();
		try {
			stmt=conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			int file_num=0;
			while(rs.next()) {
				filelist.add(rs.getString("filename"));
				pathlist.add(rs.getString("path"));
				file_num++;
			}
			System.out.println("file_num="+file_num+"�ļ���"+filelist.get(0));
			files.setFileNum(file_num);
			files.setFilesList(filelist);
			files.setPath(pathlist);
			return files;
		}catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return files;
}

	public boolean uploadFile(FileEntity fe) {
		PreparedStatement stmt = null; 
		
		Connection conn = null; 
		int updateResult = 0;
		
		conn = DBHelper.getConnection();
		String sql = "insert into filelist (filename,path) values(?,?)";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, fe.getFileName());
			stmt.setString(2, fe.getPath());
			System.out.println("userservice  filename"+fe.getFileName()+" path:"+fe.getPath());
			updateResult = stmt.executeUpdate();
			if(updateResult == 1) {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(stmt != null) {
					stmt.close();
				}
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public VoteList getVoteList(VoteList vote) {
		// TODO �Զ����ɵķ������
	PreparedStatement stmt = null; 
		
		Connection conn = null; 
		ResultSet rs = null; 
		
		conn = DBHelper.getConnection();
		String sql = "select * from votelist";
		ArrayList<String> votelist = new ArrayList<String>();
		ArrayList<String> senderlist = new ArrayList<String>();
		try {
			stmt=conn.prepareStatement(sql);
			rs=stmt.executeQuery();
			int vote_num=0;
			while(rs.next()) {
				votelist.add(rs.getString("vote_name"));
				senderlist.add(rs.getString("sender"));
				vote_num++;
			}
			System.out.println("vote_num="+vote_num+"�ļ���"+votelist.get(0));
			vote.setVoteNum(vote_num);
			vote.setSenderlist(senderlist);
			vote.setVoteslist(votelist);
			return vote;
		}catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return vote;
	}

	public boolean sendVote(Vote vote) {
		// TODO �Զ����ɵķ������
		PreparedStatement stmt=null;
		PreparedStatement stmt2 = null;
		Connection conn=null;
		ResultSet rs=null;
		conn=DBHelper.getConnection();
		int insertFlag=0;
		String sql="insert into votelist (vote_name,sender,constent,tips,agree_num,disagree_num)values(?, ?, ?, ?, ?, ?)";
		String sql2="CREATE TABLE "+vote.getVoteName()+"_usedOne"+ "( name VARCHAR(45) NOT NULL, PRIMARY KEY (name))";
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, vote.getVoteName());
			stmt.setString(2, vote.getSender());
			stmt.setString(3, vote.getConstent());
			stmt.setString(4, vote.getTips());
			stmt.setLong(5, 0);
			stmt.setLong(6, 0);
			insertFlag=stmt.executeUpdate();
			System.out.println("����в�������" + vote.getVoteName() + "����Ϊ" + vote.getConstent());
			stmt2=conn.prepareStatement(sql2);
			int creatFlag = stmt2.executeUpdate();
			
			System.out.println("������" + vote.getVoteName() + "***" + creatFlag);
			if(insertFlag==1) {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
	}
		return false;
	}

	public Vote getVote(Vote vote) {
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		Connection conn = null; 
		ResultSet rs = null; 
		ResultSet rs2 = null; 
		conn = DBHelper.getConnection();
		String sql = "select * from votelist where vote_name =?";
		String sql2= "select * from "+vote.getVoteName()+"_usedone where name =?";
		try {
			//��ȡͶƱ�����Ϣ
			stmt1 = conn.prepareStatement(sql);
			stmt1.setString(1, vote.getVoteName());
			rs = stmt1.executeQuery();
			if(rs.next()) {
				vote.setSender(rs.getString("sender"));
				vote.setAgreeNum(rs.getInt("agree_num"));
				vote.setDisagreeNum(rs.getInt("disagree_num"));
				vote.setConstent(rs.getString("constent"));
				vote.setTips(rs.getString("tips"));
			}
			//������û��Ƿ��Ѿ�Ͷ��Ʊ
			System.out.println("server votename" +vote.getVoteName());
			stmt2 = conn.prepareStatement(sql2);
			stmt2.setString(1, vote.getUserName());
			rs2=stmt2.executeQuery();
			if(rs2.next()) {
				vote.setFlag(1);
			}
			return vote;
		} catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt1 != null) {
					stmt1.close();
				}
				if(stmt2 != null) {
					stmt2.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public boolean updateVote(Vote vote) {
		PreparedStatement stmt1 = null; //PreparedStatement������ִ��SQL��ѯ����API֮һ
		PreparedStatement stmt2 = null; //PreparedStatement������ִ��SQL��ѯ����API֮һ
		PreparedStatement stmt3 = null;
		PreparedStatement stmt4 = null;
		Connection conn = null; //���ض����ݿ�����ӣ��Ự������������������ִ�� SQL ��䲢���ؽ��
		int rs1,rs2,rs3,rs4; //�������в�ѯ������ص�һ�ֶ��󣬿���˵�������һ���洢��ѯ����Ķ��󣬵��ǽ���������������д洢�Ĺ��ܣ���ͬʱ�����в������ݵĹ��ܣ�������ɶ����ݵĸ��µ�
		
		conn = DBHelper.getConnection();
		String sql1 = "update votelist set tips =? where vote_name = ?";
		String sql2 = "insert into "+ vote.getVoteName()+"_usedone (name) values(?)";
		String sql3 =  "update votelist set agree_num =? where vote_name = ?";
		String sql4 =  "update votelist set disagree_num =? where vote_name = ?";
		try {
			stmt1 = conn.prepareStatement(sql1);
			stmt1.setString(1, vote.getTips());
			stmt1.setString(2, vote.getVoteName());
			rs1 = stmt1.executeUpdate();
			
			stmt2=conn.prepareStatement(sql2);
			stmt2.setString(1, vote.getUserName());
			rs2=stmt2.executeUpdate();
			System.out.println("userserive ѡ����"+vote.getChooseFlag());
			if(vote.getChooseFlag()==1) {
			stmt3 = conn.prepareStatement(sql3);
			stmt3.setLong(1, vote.getAgreeNum()+1);
			System.out.println("��ͬ����"+vote.getAgreeNum()+1);
			stmt3.setString(2, vote.getVoteName());
			rs3=stmt3.executeUpdate();
			System.out.println("rs3 "+rs3);
			}else {
			stmt4 = conn.prepareStatement(sql4);
			stmt4.setLong(1, vote.getDisgreeNum()+1);
			stmt4.setString(2, vote.getVoteName());
			rs4=stmt4.executeUpdate();
			System.out.println("rs4"+rs4);
			}
			if(rs1==1&&rs2==1) {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(stmt1 != null) {
					stmt1.close();
				}
				if(stmt2 != null) {
					stmt2.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	}

