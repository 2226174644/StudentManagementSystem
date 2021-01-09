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
* @version ����ʱ�䣺2018��7��7�� ����3:39:05
*/
public class RegisterUI extends JFrame implements ActionListener {
	private static final long serialVersionUID=1L;
	private JButton register_button,close_button,min_button;
	private JTextField user_name,user_pwd,user_ques,user_ans;//��Ϣ�����
	private JPanel top,tmp_South,center_Center,back;
	private ImageIcon background;			//����ͼ
	private JLabel backgroundCon;			//��������
	private JComboBox<String> user_sex = new JComboBox<String>();
	@SuppressWarnings("static-access")
	private MainFrame mainFrame; //���ڹرյ�¼ҳ�� ���ע��ɹ��򽫸տ�ʼ��ע��ҳ��ر�
	//private Client client;
	
	public RegisterUI(MainFrame mainFrame) {
		
		this.mainFrame = mainFrame;
		
		//��ʼ������
		init();
		
		
	}
	public void init() {
		
			//����������
			         //new JFrame("ע��");  
					//setLocation(200, 100);				/*����λ��*/
					setSize(800,450);		/*���ô��ڴ�С*/
					setResizable(false);				//�Ƿ���Ըı��С
					setLocationRelativeTo(null);				//���ô��������ָ�������λ�á���������ǰδ��ʾ������ c Ϊ null����˴��ڽ�������Ļ������
					setDefaultCloseOperation(DISPOSE_ON_CLOSE);			/*�رմ���*/
					setUndecorated(true);   //��Frame����ʧȥ�߿�ͱ�����������
					
					//ע�ᰴť
					register_button=new JButton();
					register_button.setBounds(520, 404, 135, 26);
					add(register_button);
					register_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					//register_button.setBorderPainted(false);//��ȥ�߿�
					register_button.setContentAreaFilled(false);//��ȥĬ�ϵı������ 
					register_button.addActionListener(this);//ע����Ӧ�¼�
					
					//�����
					//id
					user_name=new JTextField("��������û���");
					user_name.setBackground(null);//��������򱳾�ɫ
					user_name.addFocusListener(new FocusAdapter()
			        {
			            @Override
			            public void focusGained(FocusEvent e)
			            {
			            	if(user_name.getText().trim().equals("��������û���")) {
			                user_name.setText("");}
			            
			            }
			            @Override
			            public void focusLost(FocusEvent e)
			            {	if(user_name.getText().trim().equals(""))
			            	user_name.setText("��������û���");
			            }
			        });
					user_name.setBorder(null);
					user_name.setBounds(502, 203, 180, 25);
					add(user_name);
					//����
					user_pwd=new JTextField("�����������");
					user_pwd.setBackground(null);//��������򱳾�ɫ
					user_pwd.addFocusListener(new FocusAdapter()
			        {
			            @Override
			            public void focusGained(FocusEvent e)
			            {
			            	if(user_pwd.getText().trim().equals("�����������"))
			                user_pwd.setText("");
			            }
			            @Override
			            public void focusLost(FocusEvent e)
			            {	if(user_pwd.getText().trim().equals(""))
			            	user_pwd.setText("�����������");
			            }
			        });
					user_pwd.setBorder(null);
					user_pwd.setBounds(502, 240, 180, 25);
					add(user_pwd);
					//�ܱ�����
					
					user_ques=new JTextField("����������⣬�����һ�����");
					user_ques.setBackground(null);//��������򱳾�ɫ
					user_ques.addFocusListener(new FocusAdapter()
			        {
			            @Override
			            public void focusGained(FocusEvent e)
			            {
			            	if(user_ques.getText().trim().equals("����������⣬�����һ�����"))
			            	user_ques.setText("");
			            }
			            @Override
			            public void focusLost(FocusEvent e)
			            {	if(user_ques.getText().trim().equals(""))
			            	user_ques.setText("����������⣬�����һ�����");
			            }
			        });
					user_ques.setBorder(null);
					user_ques.setBounds(502, 275, 180, 25);
					add(user_ques);
					//��
					user_ans=new JTextField("���������");
					user_ans.setBackground(null);//��������򱳾�ɫ
					user_ans.addFocusListener(new FocusAdapter()
			        {
			            @Override
			            public void focusGained(FocusEvent e)
			            {
			            	if(user_ans.getText().trim().equals("���������"))
			            	
			            	user_ans.setText("");
			            }
			            @Override
			            public void focusLost(FocusEvent e)
			            {	if(user_ans.getText().trim().equals(""))
			            	user_ans.setText("���������");
			            }
			        });
					user_ans.setBorder(null);
					user_ans.setBounds(502, 310, 180, 25);
					add(user_ans);
					
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
					
					user_sex.setModel(new DefaultComboBoxModel<String>(new String[] {"��", "Ů"}));
					user_sex.setBounds(520,355, 99, 21);
					add(user_sex);
					//���ñ�����
					JLayeredPane alarmLayeredPane=new JLayeredPane();
					back = new JPanel();	
					background = new ImageIcon("image/ע��ҳ��.png");
					backgroundCon = new JLabel(background);
					backgroundCon.setBounds(0, 0,background.getIconWidth(), background.getIconHeight());
					back.add(backgroundCon);
					alarmLayeredPane.add(back, alarmLayeredPane.DEFAULT_LAYER); 
					
					add(backgroundCon);
					//getLayeredPane().add(backgroundCon, new Integer(Integer.MIN_VALUE));
					//JPanel jp=(JPanel)getContentPane(); 
					//jp.setOpaque(false);//����͸��
					setVisible(true);
					
					
					
		
	}
	public void actionPerformed(ActionEvent e) {
		/*
		 * �������˵�¼��ť �����ж��ʺŻ��������Ƿ�Ϊ�� Ȼ���װΪCommandTranser���� ��������������� ������ͨ�������ݿ�ıȶ�
		 * ����֤�ʺ�����
		 */
		if (e.getSource() == register_button) {
			String username = user_name.getText().trim();
			String password =  user_pwd.getText().trim();
			String userques = user_ques.getText().trim();
			String userans = user_ans.getText().trim();
			if ("".equals(username) || username == null) {
				JOptionPane.showMessageDialog(null, "�������û�������");
				return;
			}
			if ("".equals(password) || password == null) {
				JOptionPane.showMessageDialog(null, "���������룡��");
				return;
			}
			
			if ("".equals(userques) || userques == null) {
				JOptionPane.showMessageDialog(null, "���������⣡��");
				return;
			}
			if ("".equals(userans) || userans == null) {
				JOptionPane.showMessageDialog(null, "������𰸣���");
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
			
			// ʵ�����ͻ��� ���ҷ������� ���client�ͻ��� ֱ���������� ����һֱ����
			Client client = new Client(); //����Ψһ�Ŀͻ��ˣ����ڽ��ܷ�������������Ϣ�� socket�ӿڣ��� 
			client.sendData(cmd); //��������
			cmd = client.getData(); //���ܷ�������Ϣ
			
			if(cmd != null) {
				if(cmd.isFlag()) {
					
					this.dispose(); //�ر�ע��ҳ��
					mainFrame.dispose(); //�ر�MainFrameҳ��
					/*
					 * ���ԸĽ���¼���ڵ����� һ��ʱ����Զ��ر� ��https://blog.csdn.net/qq_24448899/article/details/75731529
					 */
					
					JOptionPane.showMessageDialog(null,  "��½�ɹ�");
					
					user = (User)cmd.getData(); 
					System.out.println(user.getUserName() + user.getUserpwd());
					FriendsUI friendsUI = new FriendsUI(user, client); //��user��ȫ����Ϣ����FriendsUI�У�����Ψһ������������Ľӿڴ���FriendUI�� ���ﴫclient��Ϊ�˷�����Ϣ
					ChatThread thread = new ChatThread(client, user, friendsUI); //���ﴫclientΪ������Ϣ�� �����ͻ�����һ�� ChatTread��һ��client
					thread.start();
				}else {
					/*
					 * ����this��null��ʲô����?
					 */
					JOptionPane.showMessageDialog(this, cmd.getResult());
				}
			}		

		}

	}
	

}

