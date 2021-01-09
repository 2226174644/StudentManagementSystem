package �ͻ��˽���;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Entity.Note;
import Entity.NoteEntity;
import Entity.NoticeList;
import Entity.User;
import UserSocket.Client;
import Util.CommandTranser;

public class SendNoticeUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private User owner;
	private Client client;
	private JButton send_bt;
	private JPanel up_jp,center_jp,down_jp;
	private JTextField noticename;
	private JTextArea constent;
	private NoticeUI noticeUI;
	public SendNoticeUI(User owner,Client client,NoticeUI noticeUI) {
		this.owner=owner;
		this.client=client;
		this.noticeUI=noticeUI;
		init();
		
		
	}
	private void init() {
		// TODO �Զ����ɵķ������
	    JPanel jpanel = new JPanel();
        jpanel.setLayout(null);
        
        jpanel.setPreferredSize(new Dimension(500, 400));
        //��Ӱ�ť
        noticename=new JTextField("���빫������");
		noticename.setBounds(0,0,500,50);
		constent=new JTextArea("���빫������");
		constent.setBounds(0,50,500,300);
        
         send_bt=new JButton("����");
		send_bt.setBounds(220,360,60,40);
		send_bt.addActionListener(this);
		
		jpanel.add(send_bt);
		jpanel.add(constent);
		jpanel.add(noticename);
       
        
        
        
        // ���ô�������
        JFrame frame = new JFrame("��������");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(jpanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
		noticename.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
            	if(noticename.getText().trim().equals("���빫������"))
                noticename.setText("");
            }
            @Override
            public void focusLost(FocusEvent e)
            {	if(noticename.getText().trim().equals(""))
            	noticename.setText("���빫������");
            }
        });
		
		constent.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
            	if(constent.getText().trim().equals("���빫������"))
                constent.setText("");
            }
            @Override
            public void focusLost(FocusEvent e)
            {	if(constent.getText().trim().equals(""))
            	constent.setText("���빫������");
            }
        });
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getSource()==send_bt) {
			System.out.println("send notice root= "+owner.getRoot());
			if(owner.getRoot()==null) {
				JOptionPane.showMessageDialog(null, "�㲻�ǹ���Ա���޷���������"); 
				}
			else {
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm:ss a");
				String name = noticename.getText().trim();
				String txt=constent.getText().trim();
				if("".equals(name) || name == null || "���빫������".equals(name)) {
					JOptionPane.showMessageDialog(null, "�����빫�����ƣ���");
					return;
				}
				System.out.println("sendnoteUI �������� " +txt);
				Note note =new Note(name,owner.getUserName()+"    ������"+ sdf.format(date),txt);
				CommandTranser cmd=new CommandTranser();
				cmd.setCmd("send_notice");
				cmd.setSender(owner.getUserName());
				cmd.setData(note);
				//��ҳ������¹���
				ImageIcon icon=new ImageIcon("image/�ļ�.jpg");
				icon.setImage(icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
				JLabel jb=new JLabel(name+" ",icon,JLabel.CENTER);
				jb.setHorizontalAlignment(SwingConstants.LEFT);
				noticeUI.getNoticePanel().add(jb);
				jb.addMouseListener(new MyMouseListener());
				noticeUI.getNoticePanel().validate();
				noticeUI.getNoticePanel().repaint();
				noticeUI.getNoticePanel().setVisible(true);
				
				client.sendData(cmd);
			}
		}
	}
class MyMouseListener extends MouseAdapter{
		
		@Override
		public void mouseClicked(MouseEvent e) {
			
			if(e.getClickCount() == 2) {
				JLabel label = (JLabel)e.getSource(); //getSource()���ص���Object,
				
				//ͨ��label�е�getText��ȡ�������
				String noticename = label.getText().trim();
				//System.out.println(friendname + "*");
				//�鿴��ú����Ƿ񴴽�������
				NoteUI noteUI = NoticeList.getNoteUI(noticename);
				if(noteUI == null) {
					System.out.println(noticename);
					noteUI = new NoteUI(noticename, client);
					NoteEntity noteEntity = new NoteEntity();
					noteEntity.setName(noticename);
					noteEntity.setNoteUI(noteUI);
					NoticeList.addNoteUI(noteEntity);
				} else {
					noteUI.show(); //�����ǰ������������Ĵ����ڸ��� ��������ʾ
				}
				
			}	
		}

		
	}
	}

