package 客户端界面;

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
	private ImageIcon background;			//背景图
	private JLabel backgroundCon;
	private String _txt_account="输入你的用户名";
	private String _txt_pwd="输入你的密码";


	private JTextField account;
	private JPasswordField pwd;
	private JButton login,close_button,min_button,register,forget;

	
	MainFrame() {
		//部分的形成
		init();
		
	}
	public void init() {
	    
		setSize(800,450);		/*设置窗口大小*/
		setResizable(false);				//是否可以改变大小
		setLocationRelativeTo(null);				//设置窗口相对于指定组件的位置。如果组件当前未显示，或者 c 为 null，则此窗口将置于屏幕的中央
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);			/*关闭窗口*/
		setUndecorated(true);   //让Frame窗口失去边框和标题栏的修饰
		
		
		
		
		//登陆按钮
		login=new JButton();
		login.setBounds(506, 307,170, 33);
		add(login);
		login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		//register_button.setBorderPainted(false);//除去边框
		login.setContentAreaFilled(false);//除去默认的背景填充 
		login.addActionListener(this);//注册响应事件
		
		//注册按钮
		register=new JButton();
		register.setBounds(660, 280,30, 15);
		add(register);
		register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		register.setBorderPainted(false);//除去边框
		register.setContentAreaFilled(false);//除去默认的背景填充 
		register.addActionListener(this);//注册响应事件
		//忘记按钮
		forget=new JButton();
		forget.setBounds(472, 285,30, 15);
		add(forget);
		forget.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		forget.setBorderPainted(false);//除去边框
		forget.setContentAreaFilled(false);//除去默认的背景填充 
		forget.addActionListener(this);//注册响应事件
		//输入框
		//id
		account=new JTextField("输入你的用户名");
		account.setBackground(null);//设置输入框背景色
		account.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {if(account.getText()=="输入你的用户名")
                account.setText("");
            }
            @Override
            public void focusLost(FocusEvent e)
            {	if(account.getText()==null)
            	account.setText("输入你的用户名");
            }
        });
		account.setBorder(null);
		account.setBounds(502, 203, 180, 25);
		add(account);
		account.addFocusListener(this);
		//密码
		pwd=new JPasswordField("输入你的密码");
		pwd.setBackground(null);//设置输入框背景色
		pwd.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
            	if(pwd.getText()=="输入你的密码")
                pwd.setText("");
            }
            @Override
            public void focusLost(FocusEvent e)
            {	if(pwd.getText()==null)
            	pwd.setText("输入你的密码");
            }
        });
		pwd.setBorder(null);
		pwd.setBounds(502, 240, 180, 25);
		pwd.setEchoChar('\0');
		pwd.addFocusListener(this);
		add(pwd);
		
		
		//最顶部右方按钮
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
		min_button = new JButton("—");
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
		
		
		//设置背景层
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
		//jp.setOpaque(false);//设置透明
		setVisible(true);
		
		
		
	}
	//按钮的点击事件用actionPerformed
	@Override
	public void actionPerformed(ActionEvent e){
		/*
		 * 1：如果点击了登录按钮 首先判断帐号或者密码是否为空 然后封装为CommandTranser对象 向服务器发送数据 服务器通过与数据库的比对
		 * 来验证帐号密码，
		 * 2：如果点击了注册账号就弹出注册页面, 信息填写完整后连接服务器
		 * 3：如果点击了忘记密码弹出找回密码页面
		 */
		//处理登录(login)页面
		if(e.getSource() == login){
			String user_name = account.getText().trim();
			String user_pwd = new String(pwd.getPassword()).trim();
			if("".equals(user_name) || user_name == null || "输入你的用户名".equals(user_name)) {
				JOptionPane.showMessageDialog(null, "请输入帐号！！");
				return;
			}
			if("".equals(user_pwd) || user_pwd == null || "输入你的密码".equals(user_pwd)) {
				JOptionPane.showMessageDialog(null, "请输入密码！！");
				return;
			}
			User user = new User(user_name, user_pwd);
			CommandTranser cmd = new CommandTranser();
			cmd.setCmd("login");
			cmd.setData(user);
			cmd.setReceiver(user_name);
			cmd.setSender(user_name);
			
			//实例化客户端 连接服务器 发送数据 密码是否正确?
			
			Client client = new Client(); //创建唯一的客户端（用于接受服务器发来的消息， socket接口）， 
			client.sendData(cmd); //发送数据
			cmd = client.getData(); //接受反馈的消息
			
			if(cmd != null) {
				if(cmd.isFlag()) {
					this.dispose(); //关闭MainFrame页面
					/*
					 * 可以改进登录窗口弹出后 一段时间后自动关闭 见https://blog.csdn.net/qq_24448899/article/details/75731529
					 */
					JOptionPane.showMessageDialog(null,  "登陆成功");
					user = (User)cmd.getData(); 
					//
					FriendsUI friendsUI = new FriendsUI(user, client); //将user的全部信息传到FriendsUI中，并将唯一与服务器交流的接口传到FriendUI中 这里传client仅为了发送消息
					ChatThread thread = new ChatThread(client, user, friendsUI); //这里传client为了收消息， 整个客户端用一个 ChatTread，一个client 
					System.out.println("mainuI thread owner is "+thread.getUserName());
					friendsUI.setThread(thread);
					thread.start();
				}else {
					/*
					 * 这里this和null有什么区别?
					 */
					JOptionPane.showMessageDialog(this, cmd.getResult());
				}
			}		
		}

		//处理注册(register)页面
		if(e.getSource() == register){
			RegisterUI registerUI = new RegisterUI(this);
			//
		}

		//处理找回密码(forget)页面
		if(e.getSource() == forget){
			ForgetUI forgetUI = new ForgetUI(this);
				
		}
			
	}
	
	//鼠标的点击或移动之类的用focuslistener
	@Override
	public void focusGained(FocusEvent e) {
		//处理账号输入框
    	if(e.getSource() == account){
			if(_txt_account.equals(account.getText())){
				account.setText("");
				account.setForeground(Color.BLACK);
			}
		}
    	
		//处理密码输入框
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
		//处理账号输入框
		if(e.getSource() == account){
			if("".equals(account.getText())){
				account.setForeground(Color.gray);
				account.setText(_txt_account);
			}
		}
    	
		//处理密码输入框
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
