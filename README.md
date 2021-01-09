# StudentManagementSystem
java课设-学生管理系统
结合Java的GUI技术，数据库技术，网络技术，多线程技术，图形技术等开发一个综合性的实际应用系统。
使用swings技术完成如下基本功能需求：
1、	班级公告通知
2、	文稿匿名传阅投票
3、	文件共享（上传、下载）
4、	即时通信（一对一，多对多）
实验步骤与内容：
一、程序设计的步骤：
1.	需求分析，将所有功能一一列出，并逐一对其进行详细剖析，找出其最基本的要求和实现需要实现的难度
2.	在图纸上写出构想中的实现各种功能的包和类，对其功能和属性进行设计和解释。
3.	构思实现各种功能的界面样式，在图纸上画出草图，学习GUI，从登陆界面开始绘制界面，把基本的注册界面，忘记密码界面，好友列表界面，聊天界面，添加好友界面、修改密码界面、公告界面绘制好。
4.	创建程序中要用到的信息单元类，如用户User类，命令传输ConmmandTranser类，聊天信息和图片 Message类，公告Note类，投票 Vote类等。
5.	搭建服务器，把信息的处理分为服务器线程和客户端线程。服务器向系统注册一个ServerSocke等待连接客户端的socket,客户端在登陆后分配得到一个socket用于连接数据库；
6.	学习数据库的使用，下载MySQL，并安装好JDBC驱动，下载NaViCat for MySQL 来帮助设计数据库中的表，如用户列表，好友列表，公告列表，投票列表等。
7.	按照程序执行的流程来编写代码，根据程序的行为，按行为一步步将基本功能添加进各个组件，使用输入输出流实现客户端信息与服务器与数据库的交流。功能实现后多次调试，纠正因疏漏和考虑不当而产生的错误。
8.	多次调试程序，逐步将程序完善。
二、实验内容及程序最终实现的功能：
1.登陆界面
 
用于输入用户名和密码，同时兼有忘记密码、注册和登陆的按键。点击忘记密码和注册会通向对应的UI界面，点登陆则向服务器发送登陆请求，调用数据库检索用户信息是否正确，来实现登陆功能，登陆成功后再次发送命令获取公告和好友列表，从服务器得到返回数据后进入好友界面。
2.注册界面
 
与登陆界面相似，输入信息后点注册，向服务器发送注册申请，调用数据库，如果用户名不重复则注册成功，向数据库添加用户信息，并客户端进入好友界面。
3忘记密码
 
通过之前设置过的问题和答案来找回密码。
4.好友列表
 
好友列表显示用户名和身份，普通用户显示为学生，管理员显示为管理员。列表第一项固定为班级群聊，点击进入群聊，其余双击好友JLabel,进入私聊页面。最下方有三项功能基本按钮添加好友，修改密码，公告栏。
5.私聊界面

 
私聊界面可以发送文字和图片，同时可以设置文字字体，样式，字号和颜色。同时具有绘图功能。好友来消息时还会发出“滴滴”提示音。
   

简单的画图功能展示，可画直线，曲线，可清除，点完成则可发送。
6.群聊界面
 

使用卡片组件，包含了聊天，文件和投票的功能，同样有改变字体、发送图片和绘图的方法。
7.文件共享
 
下方可点击上传文件，通过文件选择器选择文件上传，文件下载后可点击打开，打开下载的文件。
8.投票列表
 
双击对应投票的Label得到对应的投票页面，下方有发起投票的按钮，用于发起投票。
9.投票页面
   

投票页面可查看投票内容、发布者、当前投票情况和已有建议。用户需在投票栏投出自己的票，如果已经投过一次票，那么会弹出已投过票的警告。
10.发布投票页面
   

前一栏输入投票名称，后一栏输入投票内容，如果用户的身份不是管理员，那么会弹出不是管理员的警告窗口。
11.添加好友页面
 
较为简单的添加好友页面，用于添加好友，添加成功后会刷新好友列表。
12.修改密码界面
 
同样较为简单的修改密码界面，用于修改密码。
13.公告列表
   

双击公告对应Label，查看对应公告
14.发布公告
   


发布公告类似于发布投票，不是管理员无法发布公告，同时弹出警告。
15.托盘
该程序实现了托盘，能右键托盘弹出菜单，菜单有显示列表，打开贪吃蛇游戏，打开音乐播放器和退出的功能。
16.贪吃蛇游戏
 
17.音乐播放器
 
可选择文件中的音乐进行播放。

