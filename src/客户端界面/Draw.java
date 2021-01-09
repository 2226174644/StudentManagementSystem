package 客户端界面;

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
 * Draw类，用于界面的初始化
 * @author CBS
 */
@SuppressWarnings("serial")
public class Draw extends JFrame {
    private DrawListener dl;
    private Graphics g;
    private ChatUI chatui;
   
	// 界面初始化方法
    public Draw(ChatUI chatui) {
    	this.chatui=chatui;
    }
    public void showUI() {
        setTitle("画图");//窗体名称
        setSize(500, 450);//窗体大小
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);//窗体居中
                //流式布局左对齐
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        setLayout(layout);//窗体使用流式布局管理器
        this.setResizable(false);//窗体大小不变
        
        this.addKeyListener(new KeyAdapter() {

            //按键释放

            public void keyReleased(KeyEvent e) {

                //按Esc键退出
                if (e.getKeyCode()==KeyEvent.VK_ENTER ) {
                    chatui.insertIcon(catchpicture());
                    Draw.this.dispose();
                }
            }
        });
    
        //使用数组保存按钮名
        String buttonName[] = { "画直线", "画曲线", "清除", "完成"};
                //用于保存图形按钮，使用网格布局
        JPanel jp1=new JPanel(new GridLayout(2, 2,0,0));
        jp1.setPreferredSize(new Dimension(150, 100));
        
        //
        JButton jbutton1 = new JButton(buttonName[2]);
        JButton jbutton2 = new JButton(buttonName[3]);
        
        
        //实例化DrawListener对象
        dl=new DrawListener();
        //循环为按钮面板添加按钮
        for (int i = 0; i < 2; i++) {
            JButton jbutton = new JButton(buttonName[i]);
            jbutton.addActionListener(dl);//为按钮添加监听
            jp1.add(jbutton);
        }
        jp1.add(jbutton1);
        jp1.add(jbutton2);
        
        JPanel jp2=new JPanel();//画布面板
        jp2.setPreferredSize(new Dimension(500, 300));
        jp2.setBackground(Color.WHITE);
        

        // 定义Color数组，用来存储按钮上要显示的颜色信息
        Color[] colorArray = { Color.BLUE, Color.GREEN, 
                    Color.RED, Color.BLACK,Color.ORANGE,Color.PINK,Color.CYAN,
                    Color.MAGENTA,Color.DARK_GRAY,Color.GRAY,Color.LIGHT_GRAY,
                    Color.YELLOW};
        //用于保存颜色按钮的面板
        JPanel jp3=new JPanel(new GridLayout(1,colorArray.length,3,3));
        // 循环遍历colorArray数组，根据数组中的元素来实例化按钮对象
        for (int i = 0; i < colorArray.length; i++) {
            JButton button = new JButton();
            button.setBackground(colorArray[i]);
            button.setPreferredSize(new Dimension(30, 30));
            button.addActionListener(dl);//为按钮添加监听
            jp3.add(button);
        }
        //将面板添加到主窗体
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        //添加按钮，作为当前颜色
        JButton nowColor=new JButton();
        nowColor.setPreferredSize(new Dimension(20,20));
        nowColor.setBackground(Color.BLACK);//默认黑色
        add(nowColor);
        //设置窗体的组件可见，如果为FALSE就看不到任何组件
        setVisible(true);    
        //获取画笔对象
        g=jp2.getGraphics();
        dl.setG(g);
        dl.setNowColor(nowColor);
        //为面板添加鼠标监听，用于绘制图形
        jp2.addMouseListener(dl);
        jp2.addMouseMotionListener(dl);
        //部分按键处理
       jbutton1.addActionListener(new ActionListener() { // 清空画版
			   public void actionPerformed(ActionEvent e) {
				   
				jp2.removeAll();
				jp2.revalidate();
			     jp2.repaint();
				   }
				  });

     jbutton2.addActionListener(new ActionListener() { // 完成
			   public void actionPerformed(ActionEvent e) {
				   
				   chatui.insertIcon(catchpicture());
                Draw.this.dispose();
				   }
				  });
    }
	protected File catchpicture() {
		// TODO 自动生成的方法存根
		try {

            Robot robot=new Robot();

            //根据指定的区域抓取屏幕的指定区域

            BufferedImage bi=robot.createScreenCapture(new Rectangle(Draw.this.getX()+5,Draw.this.getY()+145,490,300));

            //把抓取到的内容写到一个jpg文件中
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