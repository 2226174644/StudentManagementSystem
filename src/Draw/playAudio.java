package Draw;


import java.awt.event.*;

import java.io.File;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;

import javax.sound.sampled.AudioInputStream;

import javax.sound.sampled.AudioSystem;

import javax.sound.sampled.DataLine;

import javax.sound.sampled.LineUnavailableException;

import javax.sound.sampled.SourceDataLine;

import javax.sound.sampled.UnsupportedAudioFileException;

import javax.swing.*;

import java.awt.*;

public class playAudio extends JFrame implements ActionListener {

static String filePath =null;//�����ļ�·��

JButton btPause, btExit, btPlay, btLoop, btStop;

JPanel panel;

JMenuBar mb;

JMenu menu;

JMenuItem menuMi1,menuMi2;

JTextArea textarea;





// ���췽��

public playAudio() {

	setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);



// ���尴ť

btPause = new JButton("Pause");

btPause.setFont(new Font("Chaparral Pro Light",Font.BOLD,15));

btExit = new JButton("EXIT");

btExit.setFont(new Font("Chaparral Pro Light",Font.BOLD,15));

btPlay = new JButton("PLAY");

btPlay.setFont(new Font("Chaparral Pro Light",Font.BOLD,15));

btLoop = new JButton("LOOP");

btLoop.setFont(new Font("Chaparral Pro Light",Font.BOLD,15));

btStop = new JButton("STOP");

btStop.setFont(new Font("Chaparral Pro Light",Font.BOLD,15));



//�����ı���

textarea=new JTextArea("Import music fileDialog...");

textarea.setEditable(false);

textarea.setFont(new Font("Chaparral Pro Light",Font.HANGING_BASELINE,20));

//����˵���

mb=new JMenuBar();

menu=new JMenu("File");

menu.setMnemonic('F');

menu.setFont(new Font("Chaparral Pro Light",Font.HANGING_BASELINE,15));

menuMi1=new JMenuItem("Open");

menuMi1.setMnemonic('O');

menuMi1.setFont(new Font("Chaparral Pro Light",Font.HANGING_BASELINE,15));

menuMi2=new JMenuItem("Exit");

menuMi2.setMnemonic('E');

menuMi2.setFont(new Font("Chaparral Pro Light",Font.HANGING_BASELINE,15));



//�������

panel = new JPanel();

// ���������



panel.add(btPlay);

panel.add(btLoop);

panel.add(btPause);

panel.add(btStop);

panel.add(btExit);



//��Ӳ˵����

mb.add(menu);

menu.add(menuMi1);

menu.add(menuMi2);

// ����¼�����

btPause.addActionListener(this);

btPause.setActionCommand("pause");

btExit.addActionListener(this);

btExit.setActionCommand("exit");

btPlay.addActionListener(this);

btPlay.setActionCommand("play");

btLoop.addActionListener(this);

btLoop.setActionCommand("loop");

btStop.addActionListener(this);

btStop.setActionCommand("stop");

menuMi1.addActionListener(this);

menuMi1.setActionCommand("open");

menuMi2.addActionListener(this);

menuMi2.setActionCommand("exit");

// ��ʽ����

//this.setLayout(new GridLayout(2, 1,5,0));

this.setJMenuBar(mb);

this.add(textarea);

this.add(panel,BorderLayout.SOUTH);

// ��ʾ����

this.setIconImage(new ImageIcon("image/music.JPG").getImage());

this.setTitle("MusicPlayer");// �����û�����

this.setSize(400, 150);// ���ô��ڴ�С�����ִ�������

this.setLocation(300, 260);// ���ô���λ��

this.setResizable(false);// ���ô����С�Ƿ�ɵ���

this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// �رս���

this.setVisible(true);// true ��ʾ��false����ʾ

}

// �¼�����

public void actionPerformed(ActionEvent e) {

String text="";//�ļ�·������

Audio player = new Audio(filePath);//��ʼ��������



      // ��

if (e.getActionCommand().equals("open")) {

 // ��ѡ���ļ�ѡ�����

 FileDialog fd = new FileDialog(this,"Chooes music", FileDialog.LOAD);

       fd.setVisible(true); // ��ʾѡ���

       filePath = fd.getDirectory() +fd.getFile(); // �ļ�����=��ȡ���ļ��Ի����Ŀ¼+�ļ���

     

       if(filePath!=null)

  System.out.println(text="filePath is: "+filePath);

       else

        System.out.println(text="Couldn't open file");

}

// �˳�

else if (e.getActionCommand().equals("exit")) {

 player.interrupt();

 System.out.println(text="Exit program!");

 System.exit(0);

}

// ����

else if (e.getActionCommand().equals("play")) {

 if(filePath!=null){

  player.start();

  System.out.println(text="Play music: "+filePath);

 }else

  System.out.println(text="There is not any file!");

 

}

// ѭ��

else if (e.getActionCommand().equals("loop")) {

 if(filePath!=null){

  while(!player.isAlive()){

   try{

    player.start();

    System.out.println(text="Loop playing"+filePath);

   }catch(Exception e1){

    System.err.println(text="Loop Error");

   }

  }

 }else

  System.out.println(text="There is not any file!");

}

// ֹͣ

else if (e.getActionCommand().equals("stop")) {

 player.interrupt();

 System.out.println(text="Stop playing!");

}

//��ͣ

else if(e.getActionCommand().equals("pause")){

 player.interrupt();

 System.out.println(text="Pause playing!");

}

this.textarea.setText(text);

}

}

//Audio��ʵ������������ 1.������Ƶ�ļ��ı�����

//������Ҫ�� һ�����ڴ洢��Ƶ�ļ��������ֵ�String���� filename��

//2.���캯������ʼ��filename

//3.�߳����к�����д

class Audio extends Thread {

// 1.������Ƶ�ļ��ı�����������Ҫ��һ�����ڴ洢��Ƶ�ļ��������ֵ�String���� filename

private String filename;

// 2.���캯������ʼ��filename

public Audio(String filename) {

this.filename = filename;

}

// 3.�߳����к�����д

public void run() {

// 1.����һ���ļ��������ã�ָ����Ϊfilename�Ǹ��ļ�

File sourceFile = new File(filename);

// ����һ��AudioInputStream���ڽ����������Ƶ����

AudioInputStream audioInputStream = null;

// ʹ��AudioSystem����ȡ��Ƶ����Ƶ������

try {

 audioInputStream = AudioSystem.getAudioInputStream(sourceFile);

} catch (UnsupportedAudioFileException e) {

 e.printStackTrace();

} catch (IOException e) {

 e.printStackTrace();

}

// 4,��AudioFormat����ȡAudioInputStream�ĸ�ʽ

AudioFormat format = audioInputStream.getFormat();

// 5.Դ������SoureDataLine�ǿ���д�����ݵ�������

SourceDataLine auline = null;

// ��ȡ��������֧�ֵ���Ƶ��ʽDataLine.info

DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

// �����ָ��info������ƥ�����

try {

 auline = (SourceDataLine) AudioSystem.getLine(info);

 // �򿪾���ָ����ʽ���У�������ʹ�л����������ϵͳ��Դ����ÿɲ���

 auline.open();

} catch (LineUnavailableException e) {

 e.printStackTrace();

}

// ����ĳһ��������ִ������i/o

auline.start();

// д������

int nBytesRead = 0;



byte[] abData = new byte[1024];

// ����Ƶ����ȡָ������������������ֽڣ����������������ֽ������С�

try {

 while (nBytesRead != -1) {

  nBytesRead = audioInputStream.read(abData, 0, abData.length);

  // ͨ����Դ�����н�����д���Ƶ��

  if (nBytesRead >= 0)

   auline.write(abData, 0, nBytesRead);

 }

} catch (IOException e) {

 e.printStackTrace();

} finally {

 auline.drain();
 auline.close();

}

}

}



