package UserSocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import Util.CommandTranser;

public class Client {
	private int port = 4000;				     //在客户端通过主机和端口号创建一个 socket实例,连到服务器上
	private String Sever_address = "127.0.0.1"; //服务器主机ip
	private Socket socket;
	
	//实例化， 建立连接
	public Client(){
		try {
			socket = new Socket(Sever_address, port);  //链接服务器
		} catch(UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "服务器端未开启");
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "服务器端未开启");
		}
	}
	//没用
	public Socket getSocket() {
		return socket;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	} 
	//
	//向服务端发送数据
	public void sendData(CommandTranser cmd) {
		ObjectOutputStream oos = null; //主要的作用是用于写入对象信息与读取对象信息。 对象信息一旦写到文件上那么对象的信息就可以做到持久化了
		try {
			if(socket == null) {
				return;
			}
			System.out.println(" client cmd="+cmd.getCmd());
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(cmd);
			
		} catch(UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "服务器端未开启");
			System.out.println("cuowu 1  " );
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "服务器端未开启");
			System.out.println("cuowu 2  " + e);
		}
	}
	
	//接受服务端发送的消息
	public CommandTranser getData() {
		ObjectInputStream ois = null;
		CommandTranser cmd = null;
		if(socket == null) {
		
			return null;
		}
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			cmd = (CommandTranser) ois.readObject();
			System.out.println("68   client cmd="+cmd.getCmd()+"result"+cmd.getResult());
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
		
		
	
		return cmd;
	}

}
