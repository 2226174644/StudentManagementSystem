package UserSocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import Util.CommandTranser;

public class Client {
	private int port = 4000;				     //�ڿͻ���ͨ�������Ͷ˿ںŴ���һ�� socketʵ��,������������
	private String Sever_address = "127.0.0.1"; //����������ip
	private Socket socket;
	
	//ʵ������ ��������
	public Client(){
		try {
			socket = new Socket(Sever_address, port);  //���ӷ�����
		} catch(UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "��������δ����");
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "��������δ����");
		}
	}
	//û��
	public Socket getSocket() {
		return socket;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	} 
	//
	//�����˷�������
	public void sendData(CommandTranser cmd) {
		ObjectOutputStream oos = null; //��Ҫ������������д�������Ϣ���ȡ������Ϣ�� ������Ϣһ��д���ļ�����ô�������Ϣ�Ϳ��������־û���
		try {
			if(socket == null) {
				return;
			}
			System.out.println(" client cmd="+cmd.getCmd());
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(cmd);
			
		} catch(UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "��������δ����");
			System.out.println("cuowu 1  " );
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "��������δ����");
			System.out.println("cuowu 2  " + e);
		}
	}
	
	//���ܷ���˷��͵���Ϣ
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
