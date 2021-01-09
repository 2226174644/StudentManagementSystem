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

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Entity.User;
import UserSocket.ChatThread;
import UserSocket.Client;
import Util.CommandTranser;

/**
* @author zzz
* @version 创建时间：2018年7月7日 下午3:39:05
*/
public class RegisterUI extends JFrame implements ActionListener {
	private static final long serialVersionUID=1L;
	private JButton register_button,close_button,min_button;
	private JTextField user_name,user_pwd,user_ques,user_ans;//信息输入框
	private JPanel top,tmp_South,center_Center,back;
	private ImageIcon background;			//背景图
	private JLabel backgroundCon;			//背景容器
	private JComboBox<String> user_sex = new JComboBox<String>();
	@SuppressWarnings("static-access")
	private MainFrame mainFrame; //用于关闭登录页面 如果注册成功则将刚开始的注册页面关闭
	//private Client client;
	
	public RegisterUI(MainFrame mainFrame) {
		
		this.mainFrame = mainFrame;
		
		//初始化界面
		init();
		
		
	}
	public void init() {
		
			//设置主界面
			         //new JFrame("注册");  
					//setLocation(200, 100);				/*设置位置*/
					setSize(800,450);		/*设置窗口大小*/
					setResizable(false);				//是否可以改变大小
					setLocationRelativeTo(null);				//设置窗口相对于指定组件的位置。如果组件当前未显示，或者 c 为 null，则此窗口将置于屏幕的中央
					setDefaultCloseOperation(DISPOSE_ON_CLOSE);			/*关闭窗口*/
					setUndecorated(true);   //让Frame窗口失去边框和标题栏的修饰
					
					//注册按钮
					register_button=new JButton();
					register_button.setBounds(520, 404, 135, 26);
					add(register_button);
					register_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					//register_button.setBorderPainted(false);//除去边框
					register_button.setContentAreaFilled(false);//除去默认的背景填充 
					register_button.addActionListener(this);//注册响应事件
					
					//输入框
					//id
					user_name=new JTextField("输入你的用户名");
					user_name.setBackground(null);//设置输入框背景色
					user_name.addFocusListener(new FocusAdapter()
			        {
			            @Override
			            public void focusGained(FocusEvent e)
			            {
			            	if(user_name.getText().trim().equals("输入你的用户名")) {
			                user_name.setText("");}
			            
			            }
			            @Override
			            public void focusLost(FocusEvent e)
			            {	if(user_name.getText().trim().equals(""))
			            	user_name.setText("输入你的用户名");
			            }
			        });
					user_name.setBorder(null);
					user_name.setBounds(502, 203, 180, 25);
					add(user_name);
					//密码
					user_pwd=new JTextField("输入你的密码");
					user_pwd.setBackground(null);//设置输入框背景色
					user_pwd.addFocusListener(new FocusAdapter()
			        {
			            @Override
			            public void focusGained(FocusEvent e)
			            {
			            	if(user_pwd.getText().trim().equals("输入你的密码"))
			                user_pwd.setText("");
			            }
			            @Override
			            public void focusLost(FocusEvent e)
			            {	if(user_pwd.getText().trim().equals(""))
			            	user_pwd.setText("输入你的密码");
			            }
			        });
					user_pwd.setBorder(null);
					user_pwd.setBounds(502, 240, 180, 25);
					add(user_pwd);
					//密保问题
					
					user_ques=new JTextField("输入你的问题，用于找回密码");
					user_ques.setBackground(null);//设置输入框背景色
					user_ques.addFocusListener(new FocusAdapter()
			        {
			            @Override
			            public void focusGained(FocusEvent e)
			            {
			            	if(user_ques.getText().trim().equals("输入你的问题，用于找回密码"))
			            	user_ques.setText("");
			            }
			            @Override
			            public void focusLost(FocusEvent e)
			            {	if(user_ques.getText().trim().equals(""))
			            	user_ques.setText("输入你的问题，用于找回密码");
			            }
			        });
					user_ques.setBorder(null);
					user_ques.setBounds(502, 275, 180, 25);
					add(user_ques);
					//答案
					user_ans=new JTextField("输入问题答案");
					user_ans.setBackground(null);//设置输入框背景色
					user_ans.addFocusListener(new FocusAdapter()
			        {
			            @Override
			            public void focusGained(FocusEvent e)
			            {
			            	if(user_ans.getText().trim().equals("输入问题答案"))
			            	
			            	user_ans.setText("");
			            }
			            @Override
			            public void focusLost(FocusEvent e)
			            {	if(user_ans.getText().trim().equals(""))
			            	user_ans.setText("输入问题答案");
			            }
			        });
					user_ans.setBorder(null);
					user_ans.setBounds(502, 310, 180, 25);
					add(user_ans);
					
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
					
					user_sex.setModel(new DefaultComboBoxModel<String>(new String[] {"男", "女"}));
					user_sex.setBounds(520,355, 99, 21);
					add(user_sex);
					//设置背景层
					JLayeredPane alarmLayeredPane=new JLayeredPane();
					back = new JPanel();	
					background = new ImageIcon("image/注册页面.png");
					backgroundCon = new JLabel(background);
					backgroundCon.setBounds(0, 0,background.getIconWidth(), background.getIconHeight());
					back.add(backgroundCon);
					alarmLayeredPane.add(back, alarmLayeredPane.DEFAULT_LAYER); 
					
					add(backgroundCon);
					//getLayeredPane().add(backgroundCon, new Integer(Integer.MIN_VALUE));
					//JPanel jp=(JPanel)getContentPane(); 
					//jp.setOpaque(false);//设置透明
					setVisible(true);
					
					
					
		
	}
	public void actionPerformed(ActionEvent e) {
		/*
		 * 如果点击了登录按钮 首先判断帐号或者密码是否为空 然后封装为CommandTranser对象 向服务器发送数据 服务器通过与数据库的比对
		 * 来验证帐号密码
		 */
		if (e.getSource() == register_button) {
			String username = user_name.getText().trim();
			String password =  user_pwd.getText().trim();
			String userques = user_ques.getText().trim();
			String userans = user_ans.getText().trim();
			if ("".equals(username) || username == null) {
				JOptionPane.showMessageDialog(null, "请输入用户名！！");
				return;
			}
			if ("".equals(password) || password == null) {
				JOptionPane.showMessageDialog(null, "请输入密码！！");
				return;
			}
			
			if ("".equals(userques) || userques == null) {
				JOptionPane.showMessageDialog(null, "请输入问题！！");
				return;
			}
			if ("".equals(userans) || userans == null) {
				JOptionPane.showMessageDialog(null, "请输入答案！！");
				return;
			}
			
			User user = new User(username, password);
			user.setUserQuestion(userques);
			user.setUserAnswer(userans);
			user.setUserSex(user_sex.getSelectedIndex());
			
			CommandTranser cmd = new CommandTranser();
			cmd.setCmd("register");
			cmd.setData(user);
			cmd.setReceiver(username);
			cmd.setSender(username);
			
			// 实例化客户端 并且发送数据 这个client客户端 直到进程死亡 否则一直存在
			Client client = new Client(); //创建唯一的客户端（用于接受服务器发来的消息， socket接口）， 
			client.sendData(cmd); //发送数据
			cmd = client.getData(); //接受反馈的消息
			
			if(cmd != null) {
				if(cmd.isFlag()) {
					
					this.dispose(); //关闭注册页面
					mainFrame.dispose(); //关闭MainFrame页面
					/*
					 * 可以改进登录窗口弹出后 一段时间后自动关闭 见https://blog.csdn.net/qq_24448899/article/details/75731529
					 */
					
					JOptionPane.showMessageDialog(null,  "登陆成功");
					
					user = (User)cmd.getData(); 
					System.out.println(user.getUserName() + user.getUserpwd());
					FriendsUI friendsUI = new FriendsUI(user, client); //将user的全部信息传到FriendsUI中，并将唯一与服务器交流的接口传到FriendUI中 这里传client仅为了发送消息
					ChatThread thread = new ChatThread(client, user, friendsUI); //这里传client为了收消息， 整个客户端用一个 ChatTread，一个client
					thread.start();
				}else {
					/*
					 * 这里this和null有什么区别?
					 */
					JOptionPane.showMessageDialog(this, cmd.getResult());
				}
			}		

		}

	}
	

}

