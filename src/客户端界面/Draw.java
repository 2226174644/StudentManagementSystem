package �ͻ��˽���;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Draw.DrawListener;

/**
 * Draw�࣬���ڽ���ĳ�ʼ��
 * @author CBS
 */
@SuppressWarnings("serial")
public class Draw extends JFrame {
    private DrawListener dl;
    private Graphics g;
    private ChatUI chatui;
   
	// �����ʼ������
    public Draw(ChatUI chatui) {
    	this.chatui=chatui;
    }
    public void showUI() {
        setTitle("��ͼ");//��������
        setSize(500, 450);//�����С
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);//�������
                //��ʽ���������
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        setLayout(layout);//����ʹ����ʽ���ֹ�����
        this.setResizable(false);//�����С����
        
        this.addKeyListener(new KeyAdapter() {

            //�����ͷ�

            public void keyReleased(KeyEvent e) {

                //��Esc���˳�
                if (e.getKeyCode()==KeyEvent.VK_ENTER ) {
                    chatui.insertIcon(catchpicture());
                    Draw.this.dispose();
                }
            }
        });
    
        //ʹ�����鱣�水ť��
        String buttonName[] = { "��ֱ��", "������", "���", "���"};
                //���ڱ���ͼ�ΰ�ť��ʹ�����񲼾�
        JPanel jp1=new JPanel(new GridLayout(2, 2,0,0));
        jp1.setPreferredSize(new Dimension(150, 100));
        
        //
        JButton jbutton1 = new JButton(buttonName[2]);
        JButton jbutton2 = new JButton(buttonName[3]);
        
        
        //ʵ����DrawListener����
        dl=new DrawListener();
        //ѭ��Ϊ��ť�����Ӱ�ť
        for (int i = 0; i < 2; i++) {
            JButton jbutton = new JButton(buttonName[i]);
            jbutton.addActionListener(dl);//Ϊ��ť��Ӽ���
            jp1.add(jbutton);
        }
        jp1.add(jbutton1);
        jp1.add(jbutton2);
        
        JPanel jp2=new JPanel();//�������
        jp2.setPreferredSize(new Dimension(500, 300));
        jp2.setBackground(Color.WHITE);
        

        // ����Color���飬�����洢��ť��Ҫ��ʾ����ɫ��Ϣ
        Color[] colorArray = { Color.BLUE, Color.GREEN, 
                    Color.RED, Color.BLACK,Color.ORANGE,Color.PINK,Color.CYAN,
                    Color.MAGENTA,Color.DARK_GRAY,Color.GRAY,Color.LIGHT_GRAY,
                    Color.YELLOW};
        //���ڱ�����ɫ��ť�����
        JPanel jp3=new JPanel(new GridLayout(1,colorArray.length,3,3));
        // ѭ������colorArray���飬���������е�Ԫ����ʵ������ť����
        for (int i = 0; i < colorArray.length; i++) {
            JButton button = new JButton();
            button.setBackground(colorArray[i]);
            button.setPreferredSize(new Dimension(30, 30));
            button.addActionListener(dl);//Ϊ��ť��Ӽ���
            jp3.add(button);
        }
        //�������ӵ�������
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        //��Ӱ�ť����Ϊ��ǰ��ɫ
        JButton nowColor=new JButton();
        nowColor.setPreferredSize(new Dimension(20,20));
        nowColor.setBackground(Color.BLACK);//Ĭ�Ϻ�ɫ
        add(nowColor);
        //���ô��������ɼ������ΪFALSE�Ϳ������κ����
        setVisible(true);    
        //��ȡ���ʶ���
        g=jp2.getGraphics();
        dl.setG(g);
        dl.setNowColor(nowColor);
        //Ϊ�����������������ڻ���ͼ��
        jp2.addMouseListener(dl);
        jp2.addMouseMotionListener(dl);
        //���ְ�������
       jbutton1.addActionListener(new ActionListener() { // ��ջ���
			   public void actionPerformed(ActionEvent e) {
				   
				jp2.removeAll();
				jp2.revalidate();
			     jp2.repaint();
				   }
				  });

     jbutton2.addActionListener(new ActionListener() { // ���
			   public void actionPerformed(ActionEvent e) {
				   
				   chatui.insertIcon(catchpicture());
                Draw.this.dispose();
				   }
				  });
    }
	protected File catchpicture() {
		// TODO �Զ����ɵķ������
		try {

            Robot robot=new Robot();

            //����ָ��������ץȡ��Ļ��ָ������

            BufferedImage bi=robot.createScreenCapture(new Rectangle(Draw.this.getX()+5,Draw.this.getY()+145,490,300));

            //��ץȡ��������д��һ��jpg�ļ���
            SimpleDateFormat sdf=new SimpleDateFormat("yyyymmddHHmmss");
            String name=sdf.format(new Date());
            String format="jpg";
            String path="E:/drawpicture/"+name+"."+format;
//            System.out.println(path+name+"."+format);
            ImageIO.write(bi, "jpg", new File(path));
            File file =new File (path);
            return file;
        } catch (AWTException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
		return null;
	}

}