package Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Entity.FileEntity;
import Entity.Files;
import Entity.Note;
import Entity.SocketEntity;
import Entity.User;
import Entity.Vote;
import Service.UserService;
import Util.CommandTranser;
import Util.SocketList;
import Util.VoteList;


public class ServerThread extends Thread{
	private Socket socket;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		ObjectInputStream ois = null;
		ObjectOutputStream oos1 = null;
		ObjectOutputStream oos2 = null;
		//ObjectOutputStream oos3 = null;
		
		while(socket != null) {
			try {
				ois = new ObjectInputStream(socket.getInputStream());
				CommandTranser cmd = (CommandTranser) ois.readObject();
				
				//执行命令来自客户端的请求
				cmd = execute(cmd);
				
				//消息对话请求，服务器将sender发来的消息发送给receiver
				if("message".equals(cmd.getCmd())) {
					//如果 msg.ifFlag即 服务器处理成功 可以向朋友发送信息 如果服务器处理信息失败 信息发送给发送者本人
					if(cmd.isFlag()) {
						//System.out.println("对方在线");
						oos1 = new ObjectOutputStream(SocketList.getSocket(cmd.getReceiver()).getOutputStream());
					} else {
						//System.out.println("对方未在线");
						
						oos2 = new ObjectOutputStream(socket.getOutputStream());
					}
				}
				
				if ("WorldChat".equals(cmd.getCmd())) {
					HashMap<String, Socket> map = SocketList.getMap();
					Iterator<Map.Entry<String, Socket>> it = map.entrySet().iterator();
					while(it.hasNext()) {
						Map.Entry<String, Socket> entry = it.next();
						if(!entry.getKey().equals(cmd.getSender())) {
							oos1 = new ObjectOutputStream(entry.getValue().getOutputStream());
							oos1.writeObject(cmd);
						}
					}
						continue;
					
				}
				//读取文件列表
				if("check_file".equals(cmd.getCmd())) {
					System.out.println("serverthread  file sender="+cmd.getSender());
					oos1 = new ObjectOutputStream(socket.getOutputStream());
				}
				//读取投票列表
				if("check_vote".equals(cmd.getCmd())) {
					System.out.println("serverthread  vote sender="+cmd.getSender());
					oos1 = new ObjectOutputStream(socket.getOutputStream());
				}
				//获取某个投票
				if("get_vote".equals(cmd.getCmd())) {
					oos1 = new ObjectOutputStream(socket.getOutputStream());
				}
				//上传成功通知
				if("upload".equals(cmd.getCmd())) {
					oos1 = new ObjectOutputStream(socket.getOutputStream());
				}
				//读取公告 返回数据给sender
				if("check_notice".equals(cmd.getCmd())) {
					oos1 = new ObjectOutputStream(socket.getOutputStream());
				}
				//登录请求 将数据发送给sender
				if ("login".equals(cmd.getCmd())) {
					oos1 = new ObjectOutputStream(socket.getOutputStream());
				}
				
				//注册请求 将数据发送给sender
				if ("register".equals(cmd.getCmd())) {
					System.out.println("向客户端发送消息");
					oos1 = new ObjectOutputStream(socket.getOutputStream());
				}
				//发布公告
				if("send_notice".equals(cmd.getCmd())) {
					System.out.println("向客户端发送发布公告成功的消息");
					oos1=new ObjectOutputStream(socket.getOutputStream());
					
				}
				//发布投票
				if("send_vote".equals(cmd.getCmd())) {
					oos1=new ObjectOutputStream(socket.getOutputStream());
					
				}
				//更新投票
				if("update_vote".equals(cmd.getCmd())) {
					oos1 = new ObjectOutputStream(socket.getOutputStream());
					
				}
				//添加好友请求将数据发送给 receiver
				if ("requeste_add_friend".equals(cmd.getCmd())) {
					//在线，将请求发给receiver
					if(cmd.isFlag()) {
						oos1 = new ObjectOutputStream(SocketList.getSocket(cmd.getReceiver()).getOutputStream());
//						oos3 = new ObjectOutputStream(socket.getOutputStream());
//						CommandTranser newcmd =  new CommandTranser();
//						newcmd = cmd;
//						newcmd.setCmd("successful");
//						oos3.writeObject(newcmd);
					} else {
						//不管在不在线都要向发送方提示消息发送成功
						oos2 = new ObjectOutputStream(socket.getOutputStream());
					}
				}
				
				//同意添加好友请求将数据发送给 receiver和sender
				if ("accept_add_friend".equals(cmd.getCmd())) {
					//无论是否成功插入数据库都要将结果反馈，但有可能最初请求的客户下线了
					oos1 = new ObjectOutputStream(socket.getOutputStream());
					if(SocketList.getSocket(cmd.getReceiver()) != null) {
						oos2 = new ObjectOutputStream(SocketList.getSocket(cmd.getReceiver()).getOutputStream());
					}
				}
				
				if("updatefriendlist".equals(cmd.getCmd())) {
					oos1 = new ObjectOutputStream(socket.getOutputStream());
					//oos2 = new ObjectOutputStream(SocketList.getSocket(cmd.getReceiver()).getOutputStream());
				}
				
				//拒绝添加好友请求将数据发送给 receiver
				if ("refuse_to_add".equals(cmd.getCmd())) {
					//被拒绝方在线
					if(cmd.isFlag()) {
						oos1 = new ObjectOutputStream(SocketList.getSocket(cmd.getReceiver()).getOutputStream());
					}else { //被拒方不在线则向拒绝方发送消息
						oos2 = new ObjectOutputStream(socket.getOutputStream());
					}
				}
				
				//修改资料请求 发送给sender 功能暂未实现
				if ("changeinfo".equals(cmd.getCmd())) {
					oos1 = new ObjectOutputStream(socket.getOutputStream());
				}
				
				//修改密码请求 将数据发送给sender
				if ("changepwd".equals(cmd.getCmd())) {
					oos1 = new ObjectOutputStream(socket.getOutputStream());
				}
				
				//忘记密码 发送给sender
				if ("forgetpwd".equals(cmd.getCmd())) {
					oos1 = new ObjectOutputStream(socket.getOutputStream());
				}
				
				//用户下线
				if("logout".equals(cmd.getCmd())) {
					//
				}
				
				if(oos1 != null) {
					oos1.writeObject(cmd);	
				}
				if(oos2 != null) {
					oos2.writeObject(cmd);	
				}
			} catch(IOException e) {
				socket = null;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//处理客户端发来的命令
	private CommandTranser execute(CommandTranser cmd) {
		
		//登录请求
		if("login".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			User user = (User)cmd.getData();
			cmd.setFlag(userservice.checkUser(user));
			//如果登陆成功，将该客户端加入已经连接成功的map集合里面 并且开启此用户的接受线程
			if(cmd.isFlag()) {
				// 将该线程加入连接成功的map集合
				SocketEntity socketEntity = new SocketEntity();
				socketEntity.setName(cmd.getSender());
				socketEntity.setSocket(socket);
				SocketList.addSocket(socketEntity);
				
				//从数据库获取其好友列表和公告列表并将其好友列表发送至客户端
				cmd.setData(userservice.getFriendsList(user));
				cmd.setResult("登陆成功");
			} else {
				cmd.setResult("密码错误");
			}
		}
		
		//注册请求
		if("register".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			User user = (User)cmd.getData();
			cmd.setFlag(userservice.registerUser(user));
			//如果注册成功
			if(cmd.isFlag()) {
				SocketEntity socketEntity = new SocketEntity();
				socketEntity.setName(cmd.getSender());
				socketEntity.setSocket(socket);
				SocketList.addSocket(socketEntity);
				cmd.setData(userservice.getFriendsList(user));
				//刚注册的肯定没有好友 
				cmd.setResult("注册成功");
			} else {
				cmd.setResult("注册失败可能该用户已存在");
			}
		}
		//查询文件
		if("check_file".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			Files files=(Files)cmd.getData();
			cmd.setData(userservice.getFilesList(files));
			cmd.setResult("查询成功");
		}
		//查询投票请求
		if("check_vote".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			VoteList vote=(VoteList)cmd.getData();
			cmd.setData(userservice.getVoteList(vote));
			cmd.setResult("查询成功");
		}
		//上传文件
		if("upload".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			FileEntity fe=(FileEntity)cmd.getData();
			cmd.setFlag(userservice.uploadFile(fe));
			if(cmd.isFlag()) {
				cmd.setResult("上传成功");
			}else {
				cmd.setResult("上传失败");
			}
		}
		//发布公告请求
		if("send_notice".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			Note note =(Note)cmd.getData();
			cmd.setFlag(userservice.sendNotice(note));
			if(cmd.isFlag()) {
				cmd.setResult("发布公告成功");
			}else {
				cmd.setResult("发布失败");
			}
		}
		//发布投票请求
		if("send_vote".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			Vote vote =(Vote)cmd.getData();
			cmd.setFlag(userservice.sendVote(vote));
			if(cmd.isFlag()) {
				cmd.setResult("发布投票成功");
			}else {
				cmd.setResult("发布失败");
			}
		}
		//查看投票
		if("get_vote".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			Vote vote=(Vote)cmd.getData();
			cmd.setData(userservice.getVote(vote));
			cmd.setResult("查询成功");
		}
		//更新投票
		if("update_vote".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			Vote vote=(Vote)cmd.getData();
			cmd.setFlag(userservice.updateVote(vote));
			if(cmd.isFlag()) {
				cmd.setResult("投票成功");
			} else {
				cmd.setResult("投票失败");
			}
		}
		//修改资料请求 功能暂未实现
		if("changeInfo".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			User user = (User)cmd.getData();
			cmd.setFlag(userservice.changeInfo(user));
			if(cmd.isFlag()) {
				cmd.setResult("修改信息成功");
			} else {
				cmd.setResult("修改信息失败");
			}
		}
		
		//添加好友
		if("requeste_add_friend".equals(cmd.getCmd())) {
			//检查用户是否在线
			if(SocketList.getSocket(cmd.getReceiver()) != null) {
				cmd.setFlag(true);
				cmd.setResult("对方收到了您的好友请求");
			} else {
				cmd.setFlag(false);
				cmd.setResult("当前用户不在线或者改用户不存在");
			}
		}
		
		//同意添加好友请求
		if("accept_add_friend".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			cmd.setFlag(userservice.addFriend(cmd.getReceiver(), cmd.getSender()));
			if(cmd.isFlag()) {
				cmd.setResult("好友添加成功请重新登陆刷新");
			} else {
				cmd.setResult("服务器故障导致添加好友失败或者您们已经为好友");
			}
		} 
		
		if("updatefriendlist".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			User user = (User)cmd.getData();
			System.out.println("serverthread 232 username= "+user.getUserName());
			user = userservice.getFriendsList(user);
			System.out.println("serverthread 234 friendsnum= "+user.getFriendsNum());
			if(user.getFriendsNum() == 0) {
				cmd.setFlag(false);
			} else {
				cmd.setFlag(true);
				cmd.setData(user);
			}
		}
		
		//拒绝添加好友
		if("refuse_to_add".equals(cmd.getCmd())) {
			//检查是否在线
			if(SocketList.getSocket(cmd.getReceiver()) != null) {
				cmd.setFlag(true);
				cmd.setResult("您被 " + cmd.getSender() +  " 拒绝了");
			} else {
				cmd.setFlag(false);
				cmd.setResult("对方不在线不知道你拒绝了他的好友请求");
			}
		}
		
		//发送消息指令
		if("message".equals(cmd.getCmd())) {
			//检查是否在线
			if(SocketList.getSocket(cmd.getReceiver()) != null) {
				
				cmd.setFlag(true);
				
			} else {
				//System.out.println("神奇啊");
				cmd.setFlag(false);
				cmd.setResult("当前用户不在线");
			}
		}
		
		if("WorldChat".equals(cmd.getCmd())) {
			cmd.setFlag(true);
		}
		//查阅公告指令
		if("check_notice".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			Note note=(Note)cmd.getData();
			note=userservice.getNote(note);
			if(note != null ) {
				cmd.setResult("查询成功");
				cmd.setData(note);
				cmd.setFlag(true);
			} else {
				cmd.setResult("公告可能不存在");
				cmd.setFlag(false);
			}	
		}
		//
		//忘记密码指令 这里最后要讲用户的问题和答案返回
		if("forgetpwd".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			User user = (User)cmd.getData();
			user = userservice.getUser(user);
			//如果用户存在
			if(user != null ) {
				cmd.setResult("查询成功");
				cmd.setData(user);
				cmd.setFlag(true);
			} else {
				cmd.setResult("用户可能不存在");
				cmd.setFlag(false);
			}
		}	
		
		//修改密码
		if ("changepwd".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			User user = (User)cmd.getData();
			cmd.setFlag(userservice.changePassword(user));
			System.out.println("there 111 ");
			System.out.println(user.getUserName());
			if(cmd.isFlag()) {
				cmd.setResult("修改密码成功");
			}else {
				cmd.setResult("修改密码失败");
			}
		}
		
		if("logout".equals(cmd.getCmd())) {
			SocketList.getSocket(cmd.getSender());
		}
		
		return cmd;
	}
}
