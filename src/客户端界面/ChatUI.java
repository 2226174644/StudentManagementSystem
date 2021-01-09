package 客户端界面;


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
	private Client client; //在发消息用到， 收消息不用这个处理
	private ChatThread thread;
	private ImageIcon imageIcon1 = new ImageIcon("image/聊天.png");
	private ImageIcon imageIcon2 = new ImageIcon("image/文件.jpg");
	private ImageIcon imageIcon3 = new ImageIcon("image/投票.jpg");
	private Files files;
	private Vote vote=new Vote();
	private VoteList votelist;
	private int file_num=0;
	private int vote_num=0;
	 private JScrollPane scrollPane = null;
	 private JScrollPane scrollPane2 = null;
	 private JScrollPane scrollPane3 = null;
	 private JTextPane text =  new JTextPane();
	 private Box box = null; // 放输入组件的容器
	 private JButton b_insert = null, b_remove = null, b_icon = null,upload,sendvote; // 插入按钮;清除按钮;插入图片按钮
	 private JTextField addText = null; // 文字输入框
	 private JComboBox fontName = null, fontSize = null, fontStyle = null,
	   fontColor = null, fontBackColor = null; // 字体名称;字号大小;文字样式;文字颜色;文字背景颜色
	 
	 private StyledDocument doc =text.getStyledDocument(); // 获得JTextPane的Document
	 
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
			//文件查询
			if("WorldChat".equals(owner_name)) {
			files=new Files();
			CommandTranser cmd=new CommandTranser();
			cmd.setCmd("check_file");
			cmd.setSender(who);
			cmd.setData(files);
			client.sendData(cmd);
			System.out.println("107 发送文件申请");
			files=thread.getFiles();
			while(files.getFileNum()==0) {
				files=thread.getFiles();
			}

			//投票列表查询
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
				setTitle("班级群聊");
			}
			else{setTitle(ower_name + "正在和" + friend_name + "聊天");}
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
		 //聊天组件
		 chatpanel=new JPanel();
		 
		 try { // 使用Windows的界面风格
			   UIManager
			     .setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
			 
			  
			  text.setEditable(false);
			 
			  scrollPane = new JScrollPane(text);
			  scrollPane.setPreferredSize(new Dimension(500, 400));
			  addText = new JTextField(18);
			  String[] str_name = { "宋体", "黑体", "Dialog", "Gulim" };
			  String[] str_Size = { "12", "14", "18", "22", "30", "40" };
			  String[] str_Style = { "常规", "斜体", "粗体", "粗斜体" };
			  String[] str_Color = { "黑色", "红色", "蓝色", "黄色", "绿色" };
			  String[] str_BackColor = { "无色", "灰色", "淡红", "淡蓝", "淡黄", "淡绿" };
			  fontName = new JComboBox(str_name); // 字体名称
			  fontSize = new JComboBox(str_Size); // 字号
			  fontStyle = new JComboBox(str_Style); // 样式
			  fontColor = new JComboBox(str_Color); // 颜色
			  fontBackColor = new JComboBox(str_BackColor); // 背景颜色
			  b_insert = new JButton("发送"); // 发送
			  b_remove = new JButton("绘图"); // 绘图
			  b_icon = new JButton("图片"); // 插入图片
			 
			  b_insert.addActionListener(new ActionListener() { // 插入文字的事件
			   public void actionPerformed(ActionEvent e) {
			    insert(getFontAttrib());
			    addText.setText("");
			   }
			  });
			 
			  b_remove.addActionListener(new ActionListener() { // 绘图事件
			   public void actionPerformed(ActionEvent e) {
//			    text.setText("");
				  Draw draw= new Draw(ChatUI.this);
				  draw.showUI();
			   }
			  });
			 
			  b_icon.addActionListener(new ActionListener() { // 插入图片事件
			   public void actionPerformed(ActionEvent arg0) {
			    JFileChooser f = new JFileChooser(); // 查找文件
			    f.showOpenDialog(null);
			    insertIcon(f.getSelectedFile()); // 插入图片
			   }
			  });
			  box = Box.createVerticalBox(); // 竖结构
			  Box box_1 = Box.createHorizontalBox(); // 横结构
			  Box box_2 = Box.createHorizontalBox(); // 横结构
			  box.add(box_1);
			  box.add(Box.createVerticalStrut(8)); // 两行的间距
			  box.add(box_2);
			  box.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8)); // 8个的边距
			  // 开始将所需组件加入容器
			 
			  box_1.add(new JLabel("字体：")); // 加入标签
			  box_1.add(fontName); // 加入组件
			  box_1.add(Box.createHorizontalStrut(8)); // 间距
			  box_1.add(new JLabel("样式："));
			  box_1.add(fontStyle);
			  box_1.add(Box.createHorizontalStrut(8));
			  box_1.add(new JLabel("字号："));
			  box_1.add(fontSize);
			  box_1.add(Box.createHorizontalStrut(8));
			  box_1.add(new JLabel("颜色："));
			  box_1.add(fontColor);
			  box_1.add(Box.createHorizontalStrut(8));
			  box_1.add(new JLabel("背景："));
			  box_1.add(fontBackColor);
			  box_1.add(Box.createHorizontalStrut(8));
			  box_1.add(b_icon);
			  box_2.add(addText);
			  box_2.add(Box.createHorizontalStrut(8));
			  box_2.add(b_insert);
			  box_2.add(Box.createHorizontalStrut(8));
			  box_2.add(b_remove);
			  this.getRootPane().setDefaultButton(b_insert); // 默认回车按钮!
			  chatpanel.add(scrollPane);//
			  chatpanel.add(box, BorderLayout.SOUTH);
			  
			 // pack();
			  addText.requestFocus();
			  JTabbedPane tabbedPane = new JTabbedPane();
				tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);//设置选项卡标签的布局方式为滚动布局
				
				if("WorldChat".equals(owner_name)) {
				//文件面版
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
				ArrayList<String> fileslist = new ArrayList<String>(files.getFiles());//获取文件名字列表
				ArrayList<String> pathlist = new ArrayList<String>(files.getPath());//获取路径
				//循环文件
				for (int i = 0; i < file_num; ++i) {
					// 设置icon显示位置在jlabel的左边
					insert = (String)fileslist.get(i);
//					while(insert.length() < 38) {
//						insert = (String)(insert + " ");
//					}
					//按钮
					String path=(String)pathlist.get(i);	//原路径名
					String newpath="E:/groupfiles/"+insert; //新路径名
					JButton download=new JButton("下载");
					
					//点击事件
					download.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(e.getActionCommand().equals("下载")) {
						    	File oldf=new File(path);//得到原文件
						    	try {
									FileInputStream fis = new FileInputStream(oldf);
								    //建立一个输入流对象
						    	    File newf=new File(newpath);
						    	    download.setText("打开");
						              //如果文件不存在，则创建新的文件
						              if(!newf.exists()){
						                  newf.createNewFile();
						                  FileOutputStream fos = new FileOutputStream(newf);
						                  System.out.println("success create file,the file is ");
						                  //创建文件成功后，写入内容到文件里
						                  byte[] buf = new byte[1024];//每次读入文件数据量
						          		int len = -1;
						          		StringBuffer sbuf = new StringBuffer("");
						          		while ((len = (fis.read(buf))) != -1) {
						          			sbuf.append(new String(buf, 0, len));//将 buf 数组参数的子数组的字符串表示形式追加到此序列
						          			//从0开始,遇到len（-1）结束
						          		}
						          		fos.write(buf);
						          
						          		//this.setText("");
						          		JOptionPane.showMessageDialog(null, "下载成功"); 						          		
						          		fos.close();
						              }
						          } catch (Exception ex) {
						              ex.printStackTrace();
						          }
						    	
						    }
							if(e.getActionCommand().equals("打开")) {
								try {
									useAWTDesktop(newpath);
								} catch (IOException e1) {
									// TODO 自动生成的 catch 块
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
					filepage.add(filesname[i]);//把文件添加到文件panel中
					}
				
				//上传按钮
				final JPanel down_S = new JPanel();
				down_S.setLayout(new BorderLayout());
				add(down_S, BorderLayout.SOUTH);
				
				final JPanel down_S_W = new JPanel();
				final FlowLayout flowLayout = new FlowLayout();
				flowLayout.setAlignment(FlowLayout.RIGHT);
				down_S_W.setLayout(flowLayout);
				down_S.add(down_S_W); //下面的EAST已删除,不用标识位置了 
				upload = new JButton(); //这里待测试 去掉 set...
				down_S_W.add(upload);
				upload.setHorizontalTextPosition(SwingConstants.RIGHT);
				upload.setHorizontalAlignment(SwingConstants.RIGHT);
				upload.setText("上传文件");
				upload.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				upload.addActionListener(this);
					
				filepanel.add(scrollPane2);
				
				//投票
				
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
				//发布投票按钮
				JPanel sendpanel=new JPanel();
				sendpanel.setLayout(new BorderLayout());
				sendvote=new JButton();
				sendvote.setText("发布投票");
				sendvote.setHorizontalTextPosition(SwingConstants.RIGHT);
				sendvote.setHorizontalAlignment(SwingConstants.RIGHT);
				sendvote.addActionListener(this);
				sendpanel.add(sendvote);
				
				votepanel.add(scrollPane3);
				votepanel.add(sendpanel,BorderLayout.SOUTH);
				}
				//选项卡设置	
				tabbedPane.addTab("聊天", imageIcon1, chatpanel);//将标签组件添加到选项卡中，并且要求有提示
				if("WorldChat".equals(owner_name)) {
					tabbedPane.addTab("文件", imageIcon2, filepanel);
					tabbedPane.addTab("投票", imageIcon3, votepanel);
					
				}
				tabbedPane.setSelectedIndex(0); //设置索引为0的选项卡被选中
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		CommandTranser cmd= new CommandTranser();
		cmd.setCmd("message");
		if("WorldChat".equals(owner_name)) {
			cmd.setCmd("WorldChat");//把命令改为了群聊
		}
		Message ms=new Message();
		ms.setType(1);//图像类型消息
		ms.setPhoto(bt);
		ms.setFileName(name);
		//发送人信息
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm:ss a");
		String message =who+ "说 : "+ "\t"+ sdf.format(date)+ "\n" ;
		try {
			doc.insertString(doc.getLength(), message,null);
		} catch (BadLocationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		ms.setMessage(message);
		cmd.setSender(who);
		cmd.setReceiver(friend_name);
		cmd.setData(ms);
		client.sendData(cmd);
	  text.setCaretPosition(doc.getLength()); // 设置插入位置
	  text.insertIcon(new ImageIcon(file.getPath())); // 插入图片
	  insert(new FontAttrib()); // 这样做可以换行
	 }
	 
	 
	 private void insert(FontAttrib attrib) {
	  try { // 插入文本
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm:ss a");
			String message =who+ "说 : "+ "\t"+ sdf.format(date)+ "\n"+"\t"+ attrib.getText() + "\n" ;
			Message ms=new Message(doc.getLength(),message,attrib.getAttrSet());
			doc.insertString(doc.getLength(), message ,attrib.getAttrSet());
	 //数据
		CommandTranser cmd = new CommandTranser();
		cmd.setCmd("message");
		if("WorldChat".equals(owner_name)) {
			cmd.setCmd("WorldChat");//把命令改为了群聊
		}
		cmd.setSender(who);
		cmd.setReceiver(friend_name);
		cmd.setData(ms);
		
		//发送
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
	  if (temp_style.equals("常规")) {
	   att.setStyle(FontAttrib.GENERAL);
	  } else if (temp_style.equals("粗体")) {
	   att.setStyle(FontAttrib.BOLD);
	  } else if (temp_style.equals("斜体")) {
	   att.setStyle(FontAttrib.ITALIC);
	  } else if (temp_style.equals("粗斜体")) {
	   att.setStyle(FontAttrib.BOLD_ITALIC);
	  }
	  String temp_color = (String) fontColor.getSelectedItem();
	  if (temp_color.equals("黑色")) {
	   att.setColor(new Color(0, 0, 0));
	  } else if (temp_color.equals("红色")) {
	   att.setColor(new Color(255, 0, 0));
	  } else if (temp_color.equals("蓝色")) {
	   att.setColor(new Color(0, 0, 255));
	  } else if (temp_color.equals("黄色")) {
	   att.setColor(new Color(255, 255, 0));
	  } else if (temp_color.equals("绿色")) {
	   att.setColor(new Color(0, 255, 0));
	  }
	  String temp_backColor = (String) fontBackColor.getSelectedItem();
	  if (!temp_backColor.equals("无色")) {
	   if (temp_backColor.equals("灰色")) {
	    att.setBackColor(new Color(200, 200, 200));
	   } else if (temp_backColor.equals("淡红")) {
	    att.setBackColor(new Color(255, 200, 200));
	   } else if (temp_backColor.equals("淡蓝")) {
	    att.setBackColor(new Color(200, 200, 255));
	   } else if (temp_backColor.equals("淡黄")) {
	    att.setBackColor(new Color(255, 255, 200));
	   } else if (temp_backColor.equals("淡绿")) {
	    att.setBackColor(new Color(200, 255, 200));
	   }
	  }
	  return att;
	 }
	 
	 
	 
	 private class FontAttrib {
	  public static final int GENERAL = 0; // 常规
	  public static final int BOLD = 1; // 粗体
	  public static final int ITALIC = 2; // 斜体
	  public static final int BOLD_ITALIC = 3; // 粗斜体
	  private SimpleAttributeSet attrSet = null; // 属性集
	  private String text = null, name = null; // 要输入的文本和字体名称
	  private int style = 0, size = 0; // 样式和字号
	  private Color color = null, backColor = null; // 文字颜色和背景颜色
	 
	  
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
		// TODO 自动生成的方法存根
		if(e.getSource() == upload){
			 JFileChooser f = new JFileChooser(); // 选择文件
			    f.showOpenDialog(null);
			    File file=f.getSelectedFile();
			    int b=(int) file.length();
				String name=file.getName();
				System.out.println("文件名 "+name);
				String path=file.getPath();
				CommandTranser cmd= new CommandTranser();
				cmd.setCmd("upload");
				FileEntity fileEntity =new FileEntity(name,path);
				cmd.setData(fileEntity);
				//添加模块
				JPanel temp=new JPanel();
				JButton temp_bt=new JButton();
				temp_bt.setText("下载");
				String newpath="E:/groupfiles/"+name; //新路径名
				//点击事件
				temp_bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(e.getActionCommand().equals("下载")) {
					    	File oldf=new File(path);//得到原文件
					    	try {
								FileInputStream fis = new FileInputStream(oldf);
							    //建立一个输入流对象
					    	    File newf=new File(newpath);
					    	    temp_bt.setText("打开");
					              //如果文件不存在，则创建新的文件
					              if(!newf.exists()){
					                  newf.createNewFile();
					                  FileOutputStream fos = new FileOutputStream(newf);
					                  System.out.println("success create file,the file is ");
					                  //创建文件成功后，写入内容到文件里
					                  byte[] buf = new byte[1024];//每次读入文件数据量
					          		int len = -1;
					          		StringBuffer sbuf = new StringBuffer("");
					          		while ((len = (fis.read(buf))) != -1) {
					          			sbuf.append(new String(buf, 0, len));//将 buf 数组参数的子数组的字符串表示形式追加到此序列
					          			//从0开始,遇到len（-1）结束
					          		}
					          		fos.write(buf);
					          
					          		//this.setText("");
					          		JOptionPane.showMessageDialog(null, "下载成功"); 						          		
					          		fos.close();
					              }
					          } catch (Exception ex) {
					              ex.printStackTrace();
					          }
					    	
					    }
						if(e.getActionCommand().equals("打开")) {
							try {
								useAWTDesktop(newpath);
							} catch (IOException e1) {
								// TODO 自动生成的 catch 块
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
			//更新UI
		}
		}
	//打开对应路径的文件
	 private static void useAWTDesktop(String path) throws IOException{

		    Desktop.getDesktop().open(new File(path));

		    }
	 
	 
	 class MyMouseListener extends MouseAdapter{
			
			@Override
			public void mouseClicked(MouseEvent e) {
				//双击我的好友弹出与该好友的聊天框
				if(e.getClickCount() == 2) {
					JPanel panel = (JPanel)e.getSource(); //getSource()返回的是Object,
					
					
					JLabel temp=(JLabel) panel.getComponent(1);
					String name = temp.getText().trim();
					System.out.println(" chat ui votename="+name);
					new VoteUI(owner,client,name,thread);
					
				}	
			}
	}}
 

