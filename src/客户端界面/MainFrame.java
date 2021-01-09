package �ͻ��˽���;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Entity.User;
import UserSocket.ChatThread;
import UserSocket.Client;
import Util.CommandTranser;



public class MainFrame extends JFrame implements ActionListener, FocusListener {
	private static final long serialVersionUID = 1L;
	private ImageIcon background;			//����ͼ
	private JLabel backgroundCon;
	private String _txt_account="��������û���";
	private String _txt_pwd="�����������";


	private JTextField account;
	private JPasswordField pwd;
	private JButton login,close_button,min_button,register,forget;

	
	MainFrame() {
		//���ֵ��γ�
		init();
		
	}
	public void init() {
	    
		setSize(800,450);		/*���ô��ڴ�С*/
		setResizable(false);				//�Ƿ���Ըı��С
		setLocationRelativeTo(null);				//���ô��������ָ�������λ�á���������ǰδ��ʾ������ c Ϊ null����˴��ڽ�������Ļ������
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);			/*�رմ���*/
		setUndecorated(true);   //��Frame����ʧȥ�߿�ͱ�����������
		
		
		
		
		//��½��ť
		login=new JButton();
		login.setBounds(506, 307,170, 33);
		add(login);
		login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		//register_button.setBorderPainted(false);//��ȥ�߿�
		login.setContentAreaFilled(false);//��ȥĬ�ϵı������ 
		login.addActionListener(this);//ע����Ӧ�¼�
		
		//ע�ᰴť
		register=new JButton();
		register.setBounds(660, 280,30, 15);
		add(register);
		register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		register.setBorderPainted(false);//��ȥ�߿�
		register.setContentAreaFilled(false);//��ȥĬ�ϵı������ 
		register.addActionListener(this);//ע����Ӧ�¼�
		//���ǰ�ť
		forget=new JButton();
		forget.setBounds(472, 285,30, 15);
		add(forget);
		forget.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		forget.setBorderPainted(false);//��ȥ�߿�
		forget.setContentAreaFilled(false);//��ȥĬ�ϵı������ 
		forget.addActionListener(this);//ע����Ӧ�¼�
		//�����
		//id
		account=new JTextField("��������û���");
		account.setBackground(null);//��������򱳾�ɫ
		account.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {if(account.getText()=="��������û���")
                account.setText("");
            }
            @Override
            public void focusLost(FocusEvent e)
            {	if(account.getText()==null)
            	account.setText("��������û���");
            }
        });
		account.setBorder(null);
		account.setBounds(502, 203, 180, 25);
		add(account);
		account.addFocusListener(this);
		//����
		pwd=new JPasswordField("�����������");
		pwd.setBackground(null);//��������򱳾�ɫ
		pwd.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
            	if(pwd.getText()=="�����������")
                pwd.setText("");
            }
            @Override
            public void focusLost(FocusEvent e)
            {	if(pwd.getText()==null)
            	pwd.setText("�����������");
            }
        });
		pwd.setBorder(null);
		pwd.setBounds(502, 240, 180, 25);
		pwd.setEchoChar('\0');
		pwd.addFocusListener(this);
		add(pwd);
		
		
		//����ҷ���ť
		close_button = new JButton("x");
		close_button.setBounds(760, 0, 40,20);
		close_button.setBackground(Color.white) ;
		close_button.setBorderPainted(false);
		close_button.addActionListener(new ActionListener(){
	        @Override public void actionPerformed(ActionEvent e){
	        	System.exit(0);
	        }
	    });
		close_button.setFocusPainted(false);
		min_button = new JButton("��");
		min_button.setBounds(700, 0, 50,20);
		min_button.setBackground(Color.white) ;
		min_button.setBorderPainted(false);
		min_button.addActionListener(new ActionListener(){
	        @Override public void actionPerformed(ActionEvent e){
	            setExtendedState(JFrame.ICONIFIED);
	        }
	    });
		min_button.setFocusPainted(false);
		add(close_button);
		add(min_button);
		
		
		//���ñ�����
		//JLayeredPane alarmLayeredPane=new JLayeredPane();
		//back = new JPanel();	
		background = new ImageIcon("image/loginpage.png");
		backgroundCon = new JLabel(background);
		backgroundCon.setBounds(0, 0,background.getIconWidth(), background.getIconHeight());
//		back.add(backgroundCon);
//		alarmLayeredPane.add(back, alarmLayeredPane.DEFAULT_LAYER); 
		
		add(backgroundCon);
		//getLayeredPane().add(backgroundCon, new Integer(Integer.MIN_VALUE));
		//JPanel jp=(JPanel)getContentPane(); 
		//jp.setOpaque(false);//����͸��
		setVisible(true);
		
		
		
	}
	//��ť�ĵ���¼���actionPerformed
	@Override
	public void actionPerformed(ActionEvent e){
		/*
		 * 1���������˵�¼��ť �����ж��ʺŻ��������Ƿ�Ϊ�� Ȼ���װΪCommandTranser���� ��������������� ������ͨ�������ݿ�ıȶ�
		 * ����֤�ʺ����룬
		 * 2����������ע���˺ž͵���ע��ҳ��, ��Ϣ��д���������ӷ�����
		 * 3�����������������뵯���һ�����ҳ��
		 */
		//�����¼(login)ҳ��
		if(e.getSource() == login){
			String user_name = account.getText().trim();
			String user_pwd = new String(pwd.getPassword()).trim();
			if("".equals(user_name) || user_name == null || "��������û���".equals(user_name)) {
				JOptionPane.showMessageDialog(null, "�������ʺţ���");
				return;
			}
			if("".equals(user_pwd) || user_pwd == null || "�����������".equals(user_pwd)) {
				JOptionPane.showMessageDialog(null, "���������룡��");
				return;
			}
			User user = new User(user_name, user_pwd);
			CommandTranser cmd = new CommandTranser();
			cmd.setCmd("login");
			cmd.setData(user);
			cmd.setReceiver(user_name);
			cmd.setSender(user_name);
			
			//ʵ�����ͻ��� ���ӷ����� �������� �����Ƿ���ȷ?
			
			Client client = new Client(); //����Ψһ�Ŀͻ��ˣ����ڽ��ܷ�������������Ϣ�� socket�ӿڣ��� 
			client.sendData(cmd); //��������
			cmd = client.getData(); //���ܷ�������Ϣ
			
			if(cmd != null) {
				if(cmd.isFlag()) {
					this.dispose(); //�ر�MainFrameҳ��
					/*
					 * ���ԸĽ���¼���ڵ����� һ��ʱ����Զ��ر� ��https://blog.csdn.net/qq_24448899/article/details/75731529
					 */
					JOptionPane.showMessageDialog(null,  "��½�ɹ�");
					user = (User)cmd.getData(); 
					//
					FriendsUI friendsUI = new FriendsUI(user, client); //��user��ȫ����Ϣ����FriendsUI�У�����Ψһ������������Ľӿڴ���FriendUI�� ���ﴫclient��Ϊ�˷�����Ϣ
					ChatThread thread = new ChatThread(client, user, friendsUI); //���ﴫclientΪ������Ϣ�� �����ͻ�����һ�� ChatTread��һ��client 
					System.out.println("mainuI thread owner is "+thread.getUserName());
					friendsUI.setThread(thread);
					thread.start();
				}else {
					/*
					 * ����this��null��ʲô����?
					 */
					JOptionPane.showMessageDialog(this, cmd.getResult());
				}
			}		
		}

		//����ע��(register)ҳ��
		if(e.getSource() == register){
			RegisterUI registerUI = new RegisterUI(this);
			//
		}

		//�����һ�����(forget)ҳ��
		if(e.getSource() == forget){
			ForgetUI forgetUI = new ForgetUI(this);
				
		}
			
	}
	
	//���ĵ�����ƶ�֮�����focuslistener
	@Override
	public void focusGained(FocusEvent e) {
		//�����˺������
    	if(e.getSource() == account){
			if(_txt_account.equals(account.getText())){
				account.setText("");
				account.setForeground(Color.BLACK);
			}
		}
    	
		//�������������
		if(e.getSource() == pwd){
			if(_txt_pwd.equals(pwd.getText())){
				pwd.setText("");
				pwd.setEchoChar('*');
				pwd.setForeground(Color.BLACK);
			}
		}
		
	}
	
	@Override
	public void focusLost(FocusEvent e) {
		//�����˺������
		if(e.getSource() == account){
			if("".equals(account.getText())){
				account.setForeground(Color.gray);
				account.setText(_txt_account);
			}
		}
    	
		//�������������
		if(e.getSource() == pwd){
			if("".equals(pwd.getText())){
				pwd.setForeground(Color.gray);
				pwd.setText(_txt_pwd);
				pwd.setEchoChar('\0');
			}
		}

	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainFrame mainframe = new MainFrame();
	}

}
