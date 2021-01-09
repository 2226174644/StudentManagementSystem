package �ͻ��˽���;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Entity.FileEntity;
import Entity.Files;
import Entity.Message;
import Entity.User;
import Entity.Vote;
import UserSocket.ChatThread;
import UserSocket.Client;
import Util.ChatUIList;
import Util.CommandTranser;
import Util.VoteList;
 
public class ChatUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel filepanel,chatpanel,filepage,votepanel,votepage;
	private String owner_name;
	private String friend_name;
	private String who;
	private User owner;
	private Client client; //�ڷ���Ϣ�õ��� ����Ϣ�����������
	private ChatThread thread;
	private ImageIcon imageIcon1 = new ImageIcon("image/����.png");
	private ImageIcon imageIcon2 = new ImageIcon("image/�ļ�.jpg");
	private ImageIcon imageIcon3 = new ImageIcon("image/ͶƱ.jpg");
	private Files files;
	private Vote vote=new Vote();
	private VoteList votelist;
	private int file_num=0;
	private int vote_num=0;
	 private JScrollPane scrollPane = null;
	 private JScrollPane scrollPane2 = null;
	 private JScrollPane scrollPane3 = null;
	 private JTextPane text =  new JTextPane();
	 private Box box = null; // ���������������
	 private JButton b_insert = null, b_remove = null, b_icon = null,upload,sendvote; // ���밴ť;�����ť;����ͼƬ��ť
	 private JTextField addText = null; // ���������
	 private JComboBox fontName = null, fontSize = null, fontStyle = null,
	   fontColor = null, fontBackColor = null; // ��������;�ֺŴ�С;������ʽ;������ɫ;���ֱ�����ɫ
	 
	 private StyledDocument doc =text.getStyledDocument(); // ���JTextPane��Document
	 
	 public ChatUI(String ower_name, String friend_name, String who, Client client,ChatThread thread,User owner){
			this.owner_name = ower_name;
			this.friend_name = friend_name;
			this.client = client;
			this.who = who;
			this.thread=thread;
			this.owner=owner;
			
			imageIcon1.setImage(imageIcon1.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
			imageIcon2.setImage(imageIcon2.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
			imageIcon3.setImage(imageIcon3.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
			setSize(600,600);
			//�ļ���ѯ
			if("WorldChat".equals(owner_name)) {
			files=new Files();
			CommandTranser cmd=new CommandTranser();
			cmd.setCmd("check_file");
			cmd.setSender(who);
			cmd.setData(files);
			client.sendData(cmd);
			System.out.println("107 �����ļ�����");
			files=thread.getFiles();
			while(files.getFileNum()==0) {
				files=thread.getFiles();
			}

			//ͶƱ�б��ѯ
			votelist=new VoteList();
			CommandTranser cmd2=new CommandTranser();
			cmd2.setSender(who);
			cmd2.setCmd("check_vote");
			cmd2.setData(votelist);
			client.sendData(cmd2);
			votelist=thread.getVoteList();
			while(votelist.getVoteNum()==0) {
				votelist=thread.getVoteList();
			}
			}
			init();
			
			if("WorldChat".equals(owner_name)) {
				setTitle("�༶Ⱥ��");
			}
			else{setTitle(ower_name + "���ں�" + friend_name + "����");}
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setVisible(true);
			
	 }
	 public void setThread(ChatThread thread) {
		 this.thread=thread;
	 }
	 public JPanel getFilePanel() {
		 return filepanel;
	 }
	 public JPanel getVotePanel() {
		 return votepage;
	 }
	 
	 private void init() {
		 //�������
		 chatpanel=new JPanel();
		 
		 try { // ʹ��Windows�Ľ�����
			   UIManager
			     .setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
			 
			  
			  text.setEditable(false);
			 
			  scrollPane = new JScrollPane(text);
			  scrollPane.setPreferredSize(new Dimension(500, 400));
			  addText = new JTextField(18);
			  String[] str_name = { "����", "����", "Dialog", "Gulim" };
			  String[] str_Size = { "12", "14", "18", "22", "30", "40" };
			  String[] str_Style = { "����", "б��", "����", "��б��" };
			  String[] str_Color = { "��ɫ", "��ɫ", "��ɫ", "��ɫ", "��ɫ" };
			  String[] str_BackColor = { "��ɫ", "��ɫ", "����", "����", "����", "����" };
			  fontName = new JComboBox(str_name); // ��������
			  fontSize = new JComboBox(str_Size); // �ֺ�
			  fontStyle = new JComboBox(str_Style); // ��ʽ
			  fontColor = new JComboBox(str_Color); // ��ɫ
			  fontBackColor = new JComboBox(str_BackColor); // ������ɫ
			  b_insert = new JButton("����"); // ����
			  b_remove = new JButton("��ͼ"); // ��ͼ
			  b_icon = new JButton("ͼƬ"); // ����ͼƬ
			 
			  b_insert.addActionListener(new ActionListener() { // �������ֵ��¼�
			   public void actionPerformed(ActionEvent e) {
			    insert(getFontAttrib());
			    addText.setText("");
			   }
			  });
			 
			  b_remove.addActionListener(new ActionListener() { // ��ͼ�¼�
			   public void actionPerformed(ActionEvent e) {
//			    text.setText("");
				  Draw draw= new Draw(ChatUI.this);
				  draw.showUI();
			   }
			  });
			 
			  b_icon.addActionListener(new ActionListener() { // ����ͼƬ�¼�
			   public void actionPerformed(ActionEvent arg0) {
			    JFileChooser f = new JFileChooser(); // �����ļ�
			    f.showOpenDialog(null);
			    insertIcon(f.getSelectedFile()); // ����ͼƬ
			   }
			  });
			  box = Box.createVerticalBox(); // ���ṹ
			  Box box_1 = Box.createHorizontalBox(); // ��ṹ
			  Box box_2 = Box.createHorizontalBox(); // ��ṹ
			  box.add(box_1);
			  box.add(Box.createVerticalStrut(8)); // ���еļ��
			  box.add(box_2);
			  box.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8)); // 8���ı߾�
			  // ��ʼ�����������������
			 
			  box_1.add(new JLabel("���壺")); // �����ǩ
			  box_1.add(fontName); // �������
			  box_1.add(Box.createHorizontalStrut(8)); // ���
			  box_1.add(new JLabel("��ʽ��"));
			  box_1.add(fontStyle);
			  box_1.add(Box.createHorizontalStrut(8));
			  box_1.add(new JLabel("�ֺţ�"));
			  box_1.add(fontSize);
			  box_1.add(Box.createHorizontalStrut(8));
			  box_1.add(new JLabel("��ɫ��"));
			  box_1.add(fontColor);
			  box_1.add(Box.createHorizontalStrut(8));
			  box_1.add(new JLabel("������"));
			  box_1.add(fontBackColor);
			  box_1.add(Box.createHorizontalStrut(8));
			  box_1.add(b_icon);
			  box_2.add(addText);
			  box_2.add(Box.createHorizontalStrut(8));
			  box_2.add(b_insert);
			  box_2.add(Box.createHorizontalStrut(8));
			  box_2.add(b_remove);
			  this.getRootPane().setDefaultButton(b_insert); // Ĭ�ϻس���ť!
			  chatpanel.add(scrollPane);//
			  chatpanel.add(box, BorderLayout.SOUTH);
			  
			 // pack();
			  addText.requestFocus();
			  JTabbedPane tabbedPane = new JTabbedPane();
				tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);//����ѡ���ǩ�Ĳ��ַ�ʽΪ��������
				
				if("WorldChat".equals(owner_name)) {
				//�ļ����
				System.out.println("chatuI thread owner is "+thread.getUserName());
				
				filepanel=new JPanel();
				 filepage=new JPanel(new GridLayout(40,1,3,0));
				file_num=files.getFileNum();
				System.out.println("file_num="+file_num);
				 scrollPane2 = new JScrollPane(filepage);
				 scrollPane2.setPreferredSize(new Dimension(500,500));
				JPanel filesname[]=new JPanel[file_num];
				//JButton download[]=new JButton[file_num];
				
				String insert = new String();
				ArrayList<String> fileslist = new ArrayList<String>(files.getFiles());//��ȡ�ļ������б�
				ArrayList<String> pathlist = new ArrayList<String>(files.getPath());//��ȡ·��
				//ѭ���ļ�
				for (int i = 0; i < file_num; ++i) {
					// ����icon��ʾλ����jlabel�����
					insert = (String)fileslist.get(i);
//					while(insert.length() < 38) {
//						insert = (String)(insert + " ");
//					}
					//��ť
					String path=(String)pathlist.get(i);	//ԭ·����
					String newpath="E:/groupfiles/"+insert; //��·����
					JButton download=new JButton("����");
					
					//����¼�
					download.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(e.getActionCommand().equals("����")) {
						    	File oldf=new File(path);//�õ�ԭ�ļ�
						    	try {
									FileInputStream fis = new FileInputStream(oldf);
								    //����һ������������
						    	    File newf=new File(newpath);
						    	    download.setText("��");
						              //����ļ������ڣ��򴴽��µ��ļ�
						              if(!newf.exists()){
						                  newf.createNewFile();
						                  FileOutputStream fos = new FileOutputStream(newf);
						                  System.out.println("success create file,the file is ");
						                  //�����ļ��ɹ���д�����ݵ��ļ���
						                  byte[] buf = new byte[1024];//ÿ�ζ����ļ�������
						          		int len = -1;
						          		StringBuffer sbuf = new StringBuffer("");
						          		while ((len = (fis.read(buf))) != -1) {
						          			sbuf.append(new String(buf, 0, len));//�� buf �����������������ַ�����ʾ��ʽ׷�ӵ�������
						          			//��0��ʼ,����len��-1������
						          		}
						          		fos.write(buf);
						          
						          		//this.setText("");
						          		JOptionPane.showMessageDialog(null, "���سɹ�"); 						          		
						          		fos.close();
						              }
						          } catch (Exception ex) {
						              ex.printStackTrace();
						          }
						    	
						    }
							if(e.getActionCommand().equals("��")) {
								try {
									useAWTDesktop(newpath);
								} catch (IOException e1) {
									// TODO �Զ����ɵ� catch ��
									e1.printStackTrace();
								}
							}
						}


						});
					//
					download.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					//filesname[i] = new JPanel(insert, imageIcon2, JLabel.CENTER);
					filesname[i]=new JPanel();
					filesname[i].setLayout(new GridLayout(1,3));
					JLabel jb1=new JLabel(imageIcon2);
					JLabel jb2=new JLabel(insert);
					filesname[i].add(jb1);
					filesname[i].add(jb2);
					filesname[i].add(download);
					download.setHorizontalAlignment(SwingConstants.RIGHT);
					//filesname[i].addMouseListener(new MyMouseListener());
					filepage.add(filesname[i]);//���ļ���ӵ��ļ�panel��
					}
				
				//�ϴ���ť
				final JPanel down_S = new JPanel();
				down_S.setLayout(new BorderLayout());
				add(down_S, BorderLayout.SOUTH);
				
				final JPanel down_S_W = new JPanel();
				final FlowLayout flowLayout = new FlowLayout();
				flowLayout.setAlignment(FlowLayout.RIGHT);
				down_S_W.setLayout(flowLayout);
				down_S.add(down_S_W); //�����EAST��ɾ��,���ñ�ʶλ���� 
				upload = new JButton(); //��������� ȥ�� set...
				down_S_W.add(upload);
				upload.setHorizontalTextPosition(SwingConstants.RIGHT);
				upload.setHorizontalAlignment(SwingConstants.RIGHT);
				upload.setText("�ϴ��ļ�");
				upload.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				upload.addActionListener(this);
					
				filepanel.add(scrollPane2);
				
				//ͶƱ
				
				votepanel=new JPanel();
				votepage=new JPanel(new GridLayout(40,1,1,2));
				vote_num=votelist.getVoteNum();
				scrollPane3=new JScrollPane(votepage);
				scrollPane3.setPreferredSize(new Dimension(580,450));
				JPanel votesname[]=new JPanel[vote_num];
				String votename=new String();
				String sender=new String();
				ArrayList<String> list=new ArrayList<String>(votelist.getVoteslist());
				ArrayList<String> senderlist=new ArrayList<String>(votelist.getSenderlist());
				for(int i=0;i<vote_num;++i) {
					votename=(String)list.get(i);//?????
					sender=(String)senderlist.get(i);
					votesname[i]=new JPanel();
					votesname[i].setLayout(new GridLayout(1,3));
					JLabel jb3=new JLabel(imageIcon3);
					JLabel jb4=new JLabel(votename);
					JLabel jb5=new JLabel(sender);
					votesname[i].add(jb3);
					votesname[i].add(jb4);
					votesname[i].add(jb5);
					votesname[i].addMouseListener(new MyMouseListener());
					
					votepage.add(votesname[i]);
				}
				//����ͶƱ��ť
				JPanel sendpanel=new JPanel();
				sendpanel.setLayout(new BorderLayout());
				sendvote=new JButton();
				sendvote.setText("����ͶƱ");
				sendvote.setHorizontalTextPosition(SwingConstants.RIGHT);
				sendvote.setHorizontalAlignment(SwingConstants.RIGHT);
				sendvote.addActionListener(this);
				sendpanel.add(sendvote);
				
				votepanel.add(scrollPane3);
				votepanel.add(sendpanel,BorderLayout.SOUTH);
				}
				//ѡ�����	
				tabbedPane.addTab("����", imageIcon1, chatpanel);//����ǩ�����ӵ�ѡ��У�����Ҫ������ʾ
				if("WorldChat".equals(owner_name)) {
					tabbedPane.addTab("�ļ�", imageIcon2, filepanel);
					tabbedPane.addTab("ͶƱ", imageIcon3, votepanel);
					
				}
				tabbedPane.setSelectedIndex(0); //��������Ϊ0��ѡ���ѡ��
				add(tabbedPane);}
			 
		
	


	public void insertIcon(File file) {
		int b=(int) file.length();
		String name=file.getName();
		byte[] bt=new byte[b];
		String path=file.getPath();
		try {
		RandomAccessFile raf;
			 raf = new RandomAccessFile(path, "r");
			 raf.read(bt);
			 raf.close();
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		CommandTranser cmd= new CommandTranser();
		cmd.setCmd("message");
		if("WorldChat".equals(owner_name)) {
			cmd.setCmd("WorldChat");//�������Ϊ��Ⱥ��
		}
		Message ms=new Message();
		ms.setType(1);//ͼ��������Ϣ
		ms.setPhoto(bt);
		ms.setFileName(name);
		//��������Ϣ
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm:ss a");
		String message =who+ "˵ : "+ "\t"+ sdf.format(date)+ "\n" ;
		try {
			doc.insertString(doc.getLength(), message,null);
		} catch (BadLocationException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		ms.setMessage(message);
		cmd.setSender(who);
		cmd.setReceiver(friend_name);
		cmd.setData(ms);
		client.sendData(cmd);
	  text.setCaretPosition(doc.getLength()); // ���ò���λ��
	  text.insertIcon(new ImageIcon(file.getPath())); // ����ͼƬ
	  insert(new FontAttrib()); // ���������Ի���
	 }
	 
	 
	 private void insert(FontAttrib attrib) {
	  try { // �����ı�
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm:ss a");
			String message =who+ "˵ : "+ "\t"+ sdf.format(date)+ "\n"+"\t"+ attrib.getText() + "\n" ;
			Message ms=new Message(doc.getLength(),message,attrib.getAttrSet());
			doc.insertString(doc.getLength(), message ,attrib.getAttrSet());
	 //����
		CommandTranser cmd = new CommandTranser();
		cmd.setCmd("message");
		if("WorldChat".equals(owner_name)) {
			cmd.setCmd("WorldChat");//�������Ϊ��Ⱥ��
		}
		cmd.setSender(who);
		cmd.setReceiver(friend_name);
		cmd.setData(ms);
		
		//����
		client.sendData(cmd);
		
	  } catch (BadLocationException e) {
	   e.printStackTrace();
	  }
	 }
	 public JTextPane getJTextPane() {
		 return text;
	 }
	 public StyledDocument getChatWin() {
			return doc;
		}
	 
	 private FontAttrib getFontAttrib() {
	  FontAttrib att = new FontAttrib();
	  att.setText(addText.getText());
	  att.setName((String) fontName.getSelectedItem());
	  att.setSize(Integer.parseInt((String) fontSize.getSelectedItem()));
	  String temp_style = (String) fontStyle.getSelectedItem();
	  if (temp_style.equals("����")) {
	   att.setStyle(FontAttrib.GENERAL);
	  } else if (temp_style.equals("����")) {
	   att.setStyle(FontAttrib.BOLD);
	  } else if (temp_style.equals("б��")) {
	   att.setStyle(FontAttrib.ITALIC);
	  } else if (temp_style.equals("��б��")) {
	   att.setStyle(FontAttrib.BOLD_ITALIC);
	  }
	  String temp_color = (String) fontColor.getSelectedItem();
	  if (temp_color.equals("��ɫ")) {
	   att.setColor(new Color(0, 0, 0));
	  } else if (temp_color.equals("��ɫ")) {
	   att.setColor(new Color(255, 0, 0));
	  } else if (temp_color.equals("��ɫ")) {
	   att.setColor(new Color(0, 0, 255));
	  } else if (temp_color.equals("��ɫ")) {
	   att.setColor(new Color(255, 255, 0));
	  } else if (temp_color.equals("��ɫ")) {
	   att.setColor(new Color(0, 255, 0));
	  }
	  String temp_backColor = (String) fontBackColor.getSelectedItem();
	  if (!temp_backColor.equals("��ɫ")) {
	   if (temp_backColor.equals("��ɫ")) {
	    att.setBackColor(new Color(200, 200, 200));
	   } else if (temp_backColor.equals("����")) {
	    att.setBackColor(new Color(255, 200, 200));
	   } else if (temp_backColor.equals("����")) {
	    att.setBackColor(new Color(200, 200, 255));
	   } else if (temp_backColor.equals("����")) {
	    att.setBackColor(new Color(255, 255, 200));
	   } else if (temp_backColor.equals("����")) {
	    att.setBackColor(new Color(200, 255, 200));
	   }
	  }
	  return att;
	 }
	 
	 
	 
	 private class FontAttrib {
	  public static final int GENERAL = 0; // ����
	  public static final int BOLD = 1; // ����
	  public static final int ITALIC = 2; // б��
	  public static final int BOLD_ITALIC = 3; // ��б��
	  private SimpleAttributeSet attrSet = null; // ���Լ�
	  private String text = null, name = null; // Ҫ������ı�����������
	  private int style = 0, size = 0; // ��ʽ���ֺ�
	  private Color color = null, backColor = null; // ������ɫ�ͱ�����ɫ
	 
	  
	  public FontAttrib() {
	  }
	 
	  public SimpleAttributeSet getAttrSet() {
	   attrSet = new SimpleAttributeSet();
	   if (name != null) {
	    StyleConstants.setFontFamily(attrSet, name);
	   }
	   if (style == FontAttrib.GENERAL) {
	    StyleConstants.setBold(attrSet, false);
	    StyleConstants.setItalic(attrSet, false);
	   } else if (style == FontAttrib.BOLD) {
	    StyleConstants.setBold(attrSet, true);
	    StyleConstants.setItalic(attrSet, false);
	   } else if (style == FontAttrib.ITALIC) {
	    StyleConstants.setBold(attrSet, false);
	    StyleConstants.setItalic(attrSet, true);
	   } else if (style == FontAttrib.BOLD_ITALIC) {
	    StyleConstants.setBold(attrSet, true);
	    StyleConstants.setItalic(attrSet, true);
	   }
	   StyleConstants.setFontSize(attrSet, size);
	   if (color != null) {
	    StyleConstants.setForeground(attrSet, color);
	   }
	   if (backColor != null) {
	    StyleConstants.setBackground(attrSet, backColor);
	   }
	   return attrSet;
	  }
	 
	  public void setAttrSet(SimpleAttributeSet attrSet) {
	   this.attrSet = attrSet;
	  }
	 
	  public String getText() {
	   return text;
	  }
	 
	  public void setText(String text) {
	   this.text = text;
	  }
	 
	  public Color getColor() {
	   return color;
	  }
	 
	  public void setColor(Color color) {
	   this.color = color;
	  }
	 
	  public Color getBackColor() {
	   return backColor;
	  }
	 
	  public void setBackColor(Color backColor) {
	   this.backColor = backColor;
	  }
	 
	  public String getName() {
	   return name;
	  }
	 
	  public void setName(String name) {
	   this.name = name;
	  }
	 
	  public int getSize() {
	   return size;
	  }
	 
	  public void setSize(int size) {
	   this.size = size;
	  }
	 
	  public int getStyle() {
	   return style;
	  }
	 
	  public void setStyle(int style) {
	   this.style = style;
	  }
	 }



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getSource() == upload){
			 JFileChooser f = new JFileChooser(); // ѡ���ļ�
			    f.showOpenDialog(null);
			    File file=f.getSelectedFile();
			    int b=(int) file.length();
				String name=file.getName();
				System.out.println("�ļ��� "+name);
				String path=file.getPath();
				CommandTranser cmd= new CommandTranser();
				cmd.setCmd("upload");
				FileEntity fileEntity =new FileEntity(name,path);
				cmd.setData(fileEntity);
				//���ģ��
				JPanel temp=new JPanel();
				JButton temp_bt=new JButton();
				temp_bt.setText("����");
				String newpath="E:/groupfiles/"+name; //��·����
				//����¼�
				temp_bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(e.getActionCommand().equals("����")) {
					    	File oldf=new File(path);//�õ�ԭ�ļ�
					    	try {
								FileInputStream fis = new FileInputStream(oldf);
							    //����һ������������
					    	    File newf=new File(newpath);
					    	    temp_bt.setText("��");
					              //����ļ������ڣ��򴴽��µ��ļ�
					              if(!newf.exists()){
					                  newf.createNewFile();
					                  FileOutputStream fos = new FileOutputStream(newf);
					                  System.out.println("success create file,the file is ");
					                  //�����ļ��ɹ���д�����ݵ��ļ���
					                  byte[] buf = new byte[1024];//ÿ�ζ����ļ�������
					          		int len = -1;
					          		StringBuffer sbuf = new StringBuffer("");
					          		while ((len = (fis.read(buf))) != -1) {
					          			sbuf.append(new String(buf, 0, len));//�� buf �����������������ַ�����ʾ��ʽ׷�ӵ�������
					          			//��0��ʼ,����len��-1������
					          		}
					          		fos.write(buf);
					          
					          		//this.setText("");
					          		JOptionPane.showMessageDialog(null, "���سɹ�"); 						          		
					          		fos.close();
					              }
					          } catch (Exception ex) {
					              ex.printStackTrace();
					          }
					    	
					    }
						if(e.getActionCommand().equals("��")) {
							try {
								useAWTDesktop(newpath);
							} catch (IOException e1) {
								// TODO �Զ����ɵ� catch ��
								e1.printStackTrace();
							}
						}
					}});
				temp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				temp.setLayout(new GridLayout(1,3));
				JLabel jb1=new JLabel(imageIcon2);
				JLabel jb2=new JLabel(name);
				temp.add(jb1);
				temp.add(jb2);
				temp.add(temp_bt);
				filepage.add(temp);
				//
				filepage.validate();
				filepage.repaint();
				filepage.setVisible(true);
				client.sendData(cmd);
		}
		if(e.getSource() == sendvote) {
			new SendVoteUI(owner,client,ChatUI.this, thread);
			//����UI
		}
		}
	//�򿪶�Ӧ·�����ļ�
	 private static void useAWTDesktop(String path) throws IOException{

		    Desktop.getDesktop().open(new File(path));

		    }
	 
	 
	 class MyMouseListener extends MouseAdapter{
			
			@Override
			public void mouseClicked(MouseEvent e) {
				//˫���ҵĺ��ѵ�����ú��ѵ������
				if(e.getClickCount() == 2) {
					JPanel panel = (JPanel)e.getSource(); //getSource()���ص���Object,
					
					
					JLabel temp=(JLabel) panel.getComponent(1);
					String name = temp.getText().trim();
					System.out.println(" chat ui votename="+name);
					new VoteUI(owner,client,name,thread);
					
				}	
			}
	}}
 

