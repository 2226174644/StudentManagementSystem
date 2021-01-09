package �ͻ��˽���;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import Draw.SnakeFrame;
import Draw.playAudio;
import Entity.ChatUIEntity;
import Entity.User;
import UserSocket.ChatThread;
import UserSocket.Client;
import Util.ChatUIList;
import Util.CommandTranser;


public class FriendsUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private User owner;// ��ǰ�û�
	private Client client;// �ͻ���
    private JButton changepwd_bt;
    private JButton addfriends_bt;
    private JButton notice;
    private JButton world_bt;
    private SystemTray tray;// ϵͳ����
    private TrayIcon trayIcon;
    private ChatThread thread;//
    boolean Tipflag=false;
    
	public FriendsUI(User owner, Client client) {
		this.owner = owner;
		this.client = client;
		//��ʼ������
		init();
		setTray();
		setTitle(owner.getUserName() + "-����");
		setSize(290, 670);
		setLocation(1050, 50);
		ImageIcon logo = new ImageIcon("image/friendsui/login_successful_image.jpg"); //���Ϸ�Сͼ��
		setIconImage(logo.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		setResizable(false);
		setVisible(true);
		
	}
	public void setThread(ChatThread thread) {
		this.thread=thread;
	}

	// ����
	/*
	 * ���˽���һ�� ��ʽ���� �� �߿򣨽磩���� �ֱ���ο�
	 * https://blog.csdn.net/liujun13579/article/details/7771191
	 *  https://blog.csdn.net/liujun13579/article/details/7772215
	 */
	//��������ο��� https://www.cnblogs.com/qingyundian/p/8012527.html
	private void init() {
		// TODO Auto-generated method stub
		//��¼�ɹ����ϲ��֣�����ͷ�� �û����� ����ǩ���� �������ǩ���̶�
		final JPanel upper_N = new JPanel();
		upper_N.setLayout(new BorderLayout()); // ���ñ߽粼��
		add(upper_N, BorderLayout.NORTH);
		
		ImageIcon my_avata = new ImageIcon("image/friendsui/sdu.jpg"); //ͷ�񲿷�
		my_avata.setImage(my_avata.getImage().getScaledInstance(79, 79, Image.SCALE_DEFAULT));
		final JLabel upper_N_W = new JLabel(my_avata);
		upper_N.add(upper_N_W, BorderLayout.WEST);
		upper_N_W.setPreferredSize(new Dimension(79, 79));
		
		final JPanel upper_N_Cen = new JPanel(); 
		upper_N_Cen.setLayout(new BorderLayout());
		upper_N.add(upper_N_Cen, BorderLayout.CENTER);
		
		final JLabel upper_N_Cen_Cen = new JLabel(); //�û�������
		upper_N_Cen_Cen.setText(owner.getUserName());
		upper_N_Cen_Cen.setFont(new Font("����", 1, 16));
		upper_N_Cen.add(upper_N_Cen_Cen, BorderLayout.CENTER);
		
		final JLabel upper_N_Cen_S = new JLabel(); // ����ǩ������
		String state;
		if(owner.getRoot()==null) {
			state= "��ݣ�ѧ��";
		}else {
			state="��ݣ� ����Ա";
		}
		upper_N_Cen_S.setText(state);
		upper_N_Cen.add(upper_N_Cen_S, BorderLayout.SOUTH);
		
		
		
		//��½�ɹ����²��� �޸�����, ��Ӻ���
		final JPanel down_S = new JPanel();
		down_S.setLayout(new BorderLayout());
		add(down_S, BorderLayout.SOUTH);
		
		final JPanel down_S_W = new JPanel();
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		down_S_W.setLayout(flowLayout);
		down_S.add(down_S_W); //�����EAST��ɾ��,���ñ�ʶλ���� 
		
		
		addfriends_bt = new JButton(); //��������� ȥ�� set...
		down_S_W.add(addfriends_bt);
		addfriends_bt.setHorizontalTextPosition(SwingConstants.LEFT);
		addfriends_bt.setHorizontalAlignment(SwingConstants.LEFT);
		addfriends_bt.setText("��Ӻ���");
		addfriends_bt.addActionListener(this);
		
		changepwd_bt = new JButton();
		down_S_W.add(changepwd_bt);
		changepwd_bt.setText("�޸�����");
		changepwd_bt.addActionListener(this);
		
		//���水ť
		notice=new JButton("����");
		down_S_W.add(notice);
		notice.addActionListener(this);
		
		final JTabbedPane jtp = new JTabbedPane();
		add(jtp, BorderLayout.CENTER);
		
		final JPanel friend_pal = new JPanel();
		//final JPanel world_propaganda = new JPanel();
		
		int friendsnum = owner.getFriendsNum();
		friend_pal.setLayout(new GridLayout(50, 1, 2, 2));
		
		//�༶Ⱥ��
		ImageIcon world_image = new ImageIcon("image/worldchat.jpg");
		world_image.setImage(world_image.getImage().getScaledInstance(245, 75, Image.SCALE_DEFAULT));
		world_bt = new JButton();
		world_bt.setIcon(world_image);//Ϊ��ť����ͼ��
		world_bt.setBackground(Color.white);
		world_bt.setBorderPainted(false); //���������Ӧ�û��Ʊ߿���Ϊ true������Ϊ false
		world_bt.setBorder(null); //���ô�����ı߿� ��
		world_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //�������Ϊ ��С�֡���״
		world_bt.addActionListener(this);
		 friend_pal.add(world_bt);
		//����
		final JLabel friendsname[];// ������ҵĺ��� = new JLabel[];
		friendsname = new JLabel[friendsnum];
		//����ͷ�� 
		ImageIcon icon[] = new ImageIcon[5];
		for(int i = 0; i < 5; ++i) {
			//System.out.println("image/friendsui/" + Integer.toString(i) + ".jpg");
			icon[i] = new ImageIcon((String)("image/friendsui/" + Integer.toString(i) + ".jpg"));
			icon[i].setImage(icon[i].getImage().getScaledInstance(70, 70,
					Image.SCALE_DEFAULT));
		}	
		//
		String insert = new String();
		ArrayList<String> friendslist = new ArrayList<String>(owner.getFriend());
		for (int i = 0; i < friendsnum; ++i) {
			// ����icon��ʾλ����jlabel�����
			insert = (String)friendslist.get(i);
			while(insert.length() < 38) {
				insert = (String)(insert + " ");
			}
			friendsname[i] = new JLabel(insert, icon[i % 5], JLabel.CENTER);
			friendsname[i].addMouseListener(new MyMouseListener());
			friendsname[i].setOpaque(false);//
			friend_pal.add(friendsname[i]);//�ɺ���label��ӵ�����panel��
		}
		
		final JScrollPane jsp = new JScrollPane(friend_pal);//�������������ڹ������
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jtp.addTab("�ҵĺ���", jsp);
		
		//
		jsp.setOpaque(false);
		upper_N_Cen_S.setOpaque(false);
		friend_pal.setOpaque(false);  //
		upper_N_Cen.setOpaque(false);
		down_S_W.setOpaque(false);
	//
		//����
		ImageIcon back=new ImageIcon("image/friendsui.png");
		JLabel backJL = new JLabel(back);
		backJL.setBounds(0, 0,back.getIconWidth(), back.getIconHeight());
		this.getLayeredPane().add(backJL, new Integer(Integer.MIN_VALUE));  
		((JPanel)this.getContentPane()).setOpaque(false); //����͸��  
		
		
		//���ڹر��¼�
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				CommandTranser cmd = new CommandTranser();
				cmd.setCmd("logout");
				cmd.setReceiver("Server");
				cmd.setSender(owner.getUserName());
				client.sendData(cmd);
			}
			@Override
			public void windowIconified(WindowEvent e) {
				FriendsUI.this.setVisible(false);
				}
			@Override
			public void windowClosed(WindowEvent e) {
				CommandTranser cmd = new CommandTranser();
				cmd.setCmd("logout");
				cmd.setReceiver("Server");
				cmd.setSender(owner.getUserName());
				client.sendData(cmd);
			}
		});		
	}
	

	@Override
	public void actionPerformed(ActionEvent e){
		//����޸����� �� ��Ӻ���
		
		//�޸�����
		if(e.getSource() == changepwd_bt){
			
			new ChangePwdUI(owner, client);
			
		}

		//��Ӻ���ҳ��
		if(e.getSource() == addfriends_bt){
			
			new AddFriendUI(owner, client);
		}
		//����ҳ��
		if(e.getSource()==notice) {
			new NoticeUI(owner,client);
		}
		
		if(e.getSource() == world_bt) {
			ChatUI chatUI = ChatUIList.getChatUI("WorldChat");
			if(chatUI == null) {
				System.out.println("friendsuI thread owner is "+thread.getUserName());
				chatUI = new ChatUI("WorldChat", "WorldChat", owner.getUserName(), client,thread,owner);
				ChatUIEntity chatUIEntity = new ChatUIEntity();
				chatUIEntity.setName("WorldChat");
				chatUIEntity.setChatUI(chatUI);
				ChatUIList.addChatUI(chatUIEntity);
			} else {
				chatUI.show(); //�����ǰ������������Ĵ����ڸ��� ��������ʾ
			}
		}
	}
	
	class MyMouseListener extends MouseAdapter{
		
		@Override
		public void mouseClicked(MouseEvent e) {
			//˫���ҵĺ��ѵ�����ú��ѵ������
			if(e.getClickCount() == 2) {
				JLabel label = (JLabel)e.getSource(); //getSource()���ص���Object,
				
				//ͨ��label�е�getText��ȡ�������
				String friendname = label.getText().trim();
				//System.out.println(friendname + "*");
				//�鿴��ú����Ƿ񴴽�������
				ChatUI chatUI = ChatUIList.getChatUI(friendname);
				if(chatUI == null) {
					System.out.println("friendsuI thread owner is "+thread.getUserName());
					chatUI = new ChatUI(owner.getUserName(), friendname, owner.getUserName(), client,thread,owner);
					ChatUIEntity chatUIEntity = new ChatUIEntity();
					chatUIEntity.setName(friendname);
					chatUIEntity.setChatUI(chatUI);
					ChatUIList.addChatUI(chatUIEntity);
				} else {
					chatUI.show(); //�����ǰ������������Ĵ����ڸ��� ��������ʾ
				}
				
			}	
		}
		
		//����ȥ�����б� ����ɫ��ɫ	
		@Override
		public void mouseEntered(MouseEvent e) {
			JLabel label = (JLabel)e.getSource();
			label.setOpaque(true); //���ÿؼ���͸��
			label.setBackground(new Color(255, 240, 230));
		}
		
		// �������˳��ҵĺ����б� ����ɫ��ɫ
		@Override
		public void mouseExited(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			label.setOpaque(false);
			label.setBackground(Color.WHITE);
		}
	}

	private void setTray() {
        ImageIcon icon =new ImageIcon("image/friendsui/login_successful_image.jpg");
        icon.setImage(icon.getImage().getScaledInstance(16,16, Image.SCALE_DEFAULT));
        // ���Image����
        Image image = icon.getImage();
        // ��������ͼ��
        trayIcon = new TrayIcon(image);
        // Ϊ����������������
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FriendsUI.this.setVisible(true);//��ʾ����
                toFront();
                Tipflag = false;
            }
        });
        trayIcon.setToolTip("ID:" + owner.getUserName()+ "\n" + "״̬:����");
        PopupMenu trayMenu = new PopupMenu();
        var open = new MenuItem("Open");
        var shutdown = new MenuItem("Exit");
        var music = new MenuItem("MusicAudio");
        var gameClass = new MenuItem("Game");
        shutdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FriendsUI.this.setVisible(true);//��ʾ����
            }
        });
        gameClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              SnakeFrame sf=new SnakeFrame();
              sf.launch();
            }
        });
        music.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             playAudio pa=new playAudio();
             
            }
        });
        trayMenu.add(open);
        trayMenu.addSeparator();//�ֽ�
        trayMenu.add(gameClass);
        trayMenu.add(music);
        trayMenu.addSeparator();//�ֽ�
        trayMenu.add(shutdown);
        trayIcon.setPopupMenu(trayMenu);
        tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        Toolkit tk = Toolkit.getDefaultToolkit();
        //������˸
//        new Thread(() ->
//        {
//            try {
//                while (true) {
////                    System.out.println(Tipflag);
//                    if(this.isVisible()==true) Tipflag=false;
//                        if (Tipflag == true)
//                        {
//
//                            for (int i = 0; i < 5; i++) {
//                                trayIcon.setImage(tk.createImage(""));
//                                Thread.sleep(200);
//                                trayIcon.setImage(image);
//                                Thread.sleep(200);
//                            }
//                        }
//                    Thread.sleep(1000);
//                    }
//
//            }catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }

}
