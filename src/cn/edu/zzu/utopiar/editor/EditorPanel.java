package cn.edu.zzu.utopiar.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.comm.SerialPort;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.FontUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.widget.N9ComponentFactory;

import cn.edu.zzu.entity.MutilByte;
import cn.edu.zzu.entity.PicButton;
import cn.edu.zzu.entity.SwingUtils;
import cn.edu.zzu.entity.Wave;
import cn.edu.zzu.utopiar.bean.Clock;
import cn.edu.zzu.utopiar.bean.CommWrite;
import cn.edu.zzu.utopiar.dao.ShowPic;
import cn.edu.zzu.utopiar.editor.PanelBottom.Bottom;
import cn.edu.zzu.utopiar.rect.Util;

public class EditorPanel extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	public static JPanel p2 = null;
	public static JPanel p11 = null;
	
	private static final long serialVersionUID = 1L;
	
	public static int[] head = new int[4];
	
	public static List<int []>waveList = new ArrayList<int[]>();

	protected static boolean stop2 = false;
	static HashMap<Integer, Boolean> lStop = new HashMap<Integer, Boolean>();

	protected static boolean stop1 = false;
	static HashMap<Integer, Boolean> rStop = new HashMap<Integer, Boolean>();
	
	HashMap<Integer,Thread> lThreads = new HashMap<Integer, Thread>();
	HashMap<Integer, Thread> rThreads = new HashMap<Integer, Thread>();
	
	public static String COM = "COM1";
	public static int RVO = 0;
	public static boolean Arrived = false;

	/**
	 * 图片及开关
	 */
	public static List<Util> utils = new ArrayList<Util>();
	
	/**
	 * led标识
	 */
	public static List<Integer> leds = new ArrayList<Integer>();
	
	/**
	 * 当前单元标识
	 */
	public static int flag = 0;
	
	public static HashMap<String, Image> img = new HashMap<String, Image>();
	static{
		
		File dir = new File("img/misc");
		File[] files = dir.listFiles();
		String name ;
		for (File file : files) {
			if(!file.isDirectory()){
				name = file.getName();
				img.put(name.subSequence(0, name.indexOf('.')).toString(), 
						new ImageIcon(file.getPath()).getImage());
			}
		}
	}
	
	/**
	 * 通道1剩余时间标签
	 */
	JLabel label0 = null;
	
	/**
	 * 通道2剩余时间标签
	 */
	JLabel label1 = null;
	public static Clock clock1 = null;
	
	/**
	 * 通道1当前电极功率标签
	 */
	JLabel l1 = null;
	public static Clock clock2 = null;
	
	/**
	 * 左通道处方选择方案
	 */
	JLabel l7 = null;
	public static JRadioButton jrb1 = null;
	public static JRadioButton jrb2 = null;
	ButtonGroup bg1 = new ButtonGroup();
	
	/**
	 * 右通道处方选择方案
	 */
	JLabel l8 = null;
	public static JRadioButton jrb3 = null;
	public static JRadioButton jrb4 = null;
	ButtonGroup bg2 = new ButtonGroup();
	
	/**
	 * 通道2当前电极功率标签
	 */
	JLabel l4 = null;
	
	/**
	 * 通道1当前电极温度标签
	 */
	JLabel l2 = null;
	
	/**
	 * 通道2当前电极温度标签
	 */
	JLabel l6 = null;
	
	/**
	 * 通道1处方选择
	 */
	public static JComboBox box = null;
	
	/**
	 * 通道2处方选择
	 */
	public static JComboBox box1 = null;
	
	/**
	 * 通道1按钮
	 */
	public static JButton b0 = null;
	public static JButton b1 = null;
	public static JButton b2 = null;
	public static JButton b3 = null;
	
	JButton b8 = null;
	JButton b9 = null;
	
	/**
	 * 通道1按钮
	 */
	public static JButton b4 = null;
	public static JButton b5 = null;
	public static JButton b6 = null;
	public static JButton b7 = null;
	
	JButton b10 = null;
	JButton b11 = null;
	
	/**
	 * 通道1Slider
	 */
	public static JSlider s0 = null;
	public static JSlider s1 = null;
	
	/**
	 * 通道2Slider
	 */
	public static JSlider s2 = null;
	public static JSlider s3 = null;
	
	/**
	 * 通道1显示波形
	 */
	public static PicPanel pic1 = null;
//	static List<Wave> waves = new ArrayList<Wave>();
//	static HashMap<Integer, List<Wave>> wave1list = new HashMap<Integer, List<Wave>>(4);
	
	/**
	 * 通道2显示波形
	 */
	public static RPicPanel pic2 = null;
//	static List<Wave> wave2s = new ArrayList<Wave>();
//	static HashMap<Integer, List<Wave>> wave2list = new HashMap<Integer, List<Wave>>(4);
	
	/**
	 * 处方对应时间
	 */
	static HashMap<Integer, String>timeMap = new HashMap<Integer, String>();
	
	/**
	 * 底部面板
	 */
	public static PanelBottom p12 = null;
	
	Thread mainThread = null;
	
	Thread mainThread2 = null;
	
	public static SerialPort serialPort = CommWrite.open(COM);
	
	static{
		List<String>list = ShowPic.readTxtFile("chufang/处方1.txt");
		List<String>timeList = ShowPic.readTxtFile("data/time.csv");
		for (int i=0;i<timeList.size();i++) {
			String tmp = timeList.get(i);
			timeMap.put(i, tmp.substring(tmp.indexOf(',')+1));
		}
		for(int i=0;i<4;i++){
			lStop.put(i, false);
			rStop.put(i, false);
			utils.add(new Util());
			for (String string : list) {
				utils.get(i).getlChannel().getWaves().add(ShowPic.getWave(string));
				utils.get(i).getrChannel().getWaves().add(ShowPic.getWave(string));
			}
		}
	}
	ImageIcon bg;
	
	public static JPanel p0;
	public static JPanel p13;
	public EditorMain editorMain;
	/**
	 * 
	 * @param flag
	 */
	
	public EditorPanel(final EditorMain editorMain) {
		
		//隐藏标题栏
//		this.setUndecorated(true);
//		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
		this.addWindowListener(new WindowAdapter() {		
			public void windowClosing(WindowEvent e){
				editorMain.setVisible(true);
				setVisible(true);
			}		
		});
		
		bg = new ImageIcon("img/bg2.png");
//		JLabel label = new JLabel(bg);
//		label.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
//		// 添加图片到frame的第二
//		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
//		// 获取frame的最上层面板为了设置其背景颜色（JPanel有设置透明的方法）
//		JPanel jp = (JPanel) this.getContentPane();
//		jp.setOpaque(false);
		JPanel panel = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				g.drawImage(bg.getImage(), 0, 0, null);
			}
//			@Override
//			public void paint(Graphics g) {
//				// TODO Auto-generated method stub
//				g.drawImage(new ImageIcon("img/bg.png").getImage(), 0, 0, null);
//			}
		};
//		panel.setOpaque(false);
		// 也要让他透明
		panel.setLayout(null);
		this.add(panel);
		 
//		Toolkit t=getToolkit();
//		Dimension d=t.getScreenSize();
//		setBounds((d.width-1600)/2,(d.height-900)/2,1600,900);
		setBounds(0,0,1600,900);
		setResizable(false);
		JMenuBar menuBar = new EditorMenuBar(this);
		setJMenuBar(menuBar);
		
		//----------------------------
		for(int i = 0;i<4;i++){
			
			leds.add(1);
			leds.add(1);
		}
		//————————————————————————————
		p0 = new JPanel();
		p0.setLayout(null);
		p0.setBorder(BorderFactory.createTitledBorder("通道1"));
		p0.setBounds(30,100,750,600);
		p0.setOpaque(false);
		panel.add(p0);
		//————————————————————————————
		p13 = new JPanel();
		p13.setLayout(null);
		p13.setBorder(BorderFactory.createTitledBorder("通道2"));
		p13.setBounds(810,100,750,600);
		p13.setOpaque(false);
		panel.add(p13);
		//————————————————————————————
		//-----------------------------
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		pic1 = new PicPanel();
		p1.add(pic1,"Center");
		p1.setBounds(10,20,360,340);
		p1.setBorder(BorderFactory.createTitledBorder("状态"));
		p1.setOpaque(false);
		p0.add(p1);
		//————————————————————————————
		p2 = new JPanel();
		p2.setLayout(null);
		p2.setBorder(BorderFactory.createTitledBorder("计时"));
//		label0 = N9ComponentFactory.createLabel_style1("剩余时间：");
		label0 = new JLabel("剩余时间：");
		label0.setFont(new Font("微软雅黑", 0, 30));
		label0.setText("剩余时间：");
		label0.setBounds(30, 30, 160, 40);
		clock1 = utils.get(flag).getlChannel().getClock();
		clock1.setBounds(180, 30, 100, 40);
		clock1.setOpaque(false);		
		p2.add(label0);	
		p2.add(clock1);
		p2.setBounds(10, 370, 360, 90);
		p2.setOpaque(false);
		p0.add(p2);
		//————————————————————————————
		JPanel p3 = new JPanel();
		p3.setLayout(null);
		p3.setBounds(10, 470, 360, 110);
		p3.setBorder(BorderFactory.createTitledBorder("处方选择"));
		String[] chufang = new String[99];
		for (int i=0;i<99;i++) {
			chufang[i] = "处方"+(i+1);
		}
		box= new JComboBox(chufang);
		box.setOpaque(false);
		box.setBounds(90, 30,140,28);
		JLabel l0 = new JLabel("处方：");
		l0.setBounds(30, 30, 80, 28);
		l7 = new JLabel("处方选择模式：");
		jrb1 = new JRadioButton("专家处方");
		jrb2 = new JRadioButton("自定义处方");
		jrb1.setBounds(120,70,90,30);
		jrb1.setOpaque(false);
		jrb1.setSelected(true);
		jrb2.setBounds(210,70,120,30);
		jrb2.setOpaque(false);
		l7.setBounds(20, 70, 100, 28);
		bg1.add(jrb1);
		bg1.add(jrb2);
		p3.add(l7);
		p3.add(jrb1);
		p3.add(jrb2);
		p3.add(box);
		p3.add(l0);
		p3.setOpaque(false);
		p0.add(p3);
		
		//————————————————————————————
		JPanel p4 = new JPanel();
		p4.setLayout(null);
		p4.setBorder(BorderFactory.createTitledBorder("强度"));
		b0 = new PicButton("img/button_add.png", "img/button_add_off.png", "img/button_add_pressed.png");
		b1 = new PicButton("img/button_jx.png", "img/button_jx_off.png", "img/button_jx_pressed.png");
		b2 = new PicButton("img/button_begin.png", "img/button_begin_off.png", "img/button_begin_pressed.png");
		b3 = new PicButton("img/button_stop.png", "img/button_stop_off.png", "img/button_stop_pressed.png");
		
		b0.setEnabled(false);
		b1.setEnabled(false);
		b3.setEnabled(false);
		
		l1 = N9ComponentFactory.createLabel_style1("当前强度：0");
		l1.setFont(new Font("微软雅黑", 0, 18));
		l1.setText("当前强度：0");
		s0 = new JSlider(0, 99, 10);
		s0.setOpaque(false);//add by jb2011：不填充默认背景色（否则放到白色面板板会很难看 ）
		s0.putClientProperty("JSlider.isFilled", Boolean.TRUE );
		s0.setEnabled(false);
		s0.setPaintTicks(true);
		s0.setMajorTickSpacing(10);
		s0.setMinorTickSpacing(1);
		s0.setPaintLabels(false);
		s0.setSnapToTicks(false);
		s0.setValue(0);
		
		s0.setBounds(25, 90, 300, 40);
		l1.setBounds(30, 30, 140, 40);
		b0.setBounds(30,160,140,50);
		b1.setBounds(180, 160, 140, 50);
		b2.setBounds(30, 220, 140,50);
		b3.setBounds(180, 220, 140,50);
		p4.setBounds(380, 20, 350, 300);
		p4.add(s0);
		p4.add(l1);
		p4.add(b0);
		p4.add(b1);
		p4.add(b2);
		p4.add(b3);
		p4.setOpaque(false);
		p0.add(p4);
		//————————————————————————————
		JPanel p5 = new JPanel();
		p5.setLayout(null);
		p5.setBorder(BorderFactory.createTitledBorder("透热强度"));
		l2 = N9ComponentFactory.createLabel_style1("当前电极透热强度：0");
		l2.setFont(new Font("微软雅黑", 0, 18));
		l2.setText("当前电极透热强度：0");
		b8 = new PicButton("img/button_add.png", "img/button_add_off.png", "img/button_add_pressed.png");
		b9 = new PicButton("img/button_jx.png", "img/button_jx_off.png", "img/button_jx_pressed.png");
		s1 = new JSlider(0, 10, 6);
		s1.setOpaque(false);//add by jb2011：不填充默认背景色（否则放到白色面板板会很难看 ）
		s1.putClientProperty("JSlider.isFilled", Boolean.TRUE );
		s1.setPaintTicks(true);
		//设置主刻度
		s1.setMajorTickSpacing(5);
		//设置副刻度
		s1.setMinorTickSpacing(1);
		s1.setPaintLabels(false);
		s1.setSnapToTicks(false);
		s1.setValue(0);
		l2.setBounds(30, 30, 200, 40);
		s1.setBounds(30, 85, 160, 40);
		b8.setBounds(30,160,140,50);
		b9.setBounds(180, 160, 140, 50);
		p5.setBounds(380,330,350,250);
		p5.add(b8);
		p5.add(b9);
		p5.add(l2);
		p5.add(s1);
		p5.setOpaque(false);
		p0.add(p5);
		
		//————————————————————————————
		JPanel p7 = new JPanel();
		p7.setLayout(null);
		p7.setBorder(BorderFactory.createTitledBorder("强度"));
//		b4 = new JButton("增加");
//		b5 = new JButton("减小");
//		b6 = new JButton("开始");
//		b7 = new JButton("停止");
		
		b4 = new PicButton("img/button_add.png", "img/button_add_off.png", "img/button_add_pressed.png");
		b5 = new PicButton("img/button_jx.png", "img/button_jx_off.png", "img/button_jx_pressed.png");
		b6 = new PicButton("img/button_begin.png", "img/button_begin_off.png", "img/button_begin_pressed.png");
		b7 = new PicButton("img/button_stop.png", "img/button_stop_off.png", "img/button_stop_pressed.png");
		
		b4.setEnabled(false);
		b5.setEnabled(false);
		b7.setEnabled(false);
		
		l4 = N9ComponentFactory.createLabel_style1("");
		l4.setFont(new Font("微软雅黑", 0, 18));
		l4.setText("当前强度：0");
		s2 = new JSlider(0, 99, 10);
		
		s2.setOpaque(false);//add by jb2011：不填充默认背景色（否则放到白色面板板会很难看 ）
		s2.putClientProperty("JSlider.isFilled", Boolean.TRUE );
		s2.setPaintTicks(true);
		s2.setEnabled(false);
		s2.setMajorTickSpacing(10);
		s2.setMinorTickSpacing(1);
		s2.setPaintLabels(false);
		s2.setSnapToTicks(false);
		s2.setValue(0);
		
		s2.setBounds(25, 90, 300, 40);
		l4.setBounds(30, 30, 140, 40);
//		b4.setBounds(40,160,110,30);
//		b5.setBounds(200, 160, 110, 30);
//		b6.setBounds(40, 220, 110,30);
//		b7.setBounds(200, 220, 110,30);
		
		b4.setBounds(30,160,140,50);
		b5.setBounds(180, 160, 140, 50);
		b6.setBounds(30, 220, 140,50);
		b7.setBounds(180, 220, 140,50);
		
		p7.setBounds(380, 20, 350, 300);
		p7.add(s2);
		p7.add(l4);
		p7.add(b4);
		p7.add(b5);
		p7.add(b6);
		p7.add(b7);
		p7.setOpaque(false);
		p13.add(p7);
		//————————————————————————————
		JPanel p8 = new JPanel();
		p8.setLayout(new BorderLayout());
		pic2 = new RPicPanel();
		p8.add(pic2,"Center");
		p8.setBounds(10,20,360,340);
		p8.setBorder(BorderFactory.createTitledBorder("状态"));
		p8.setOpaque(false);
		p13.add(p8);
		//————————————————————————————
		JPanel p9 = new JPanel();
		p9.setLayout(null);
		p9.setBounds(10, 470, 360, 110);
		p9.setBorder(BorderFactory.createTitledBorder("处方选择"));
		String[] chufang1 = new String[99];
		for (int i=0;i<99;i++) {
			chufang1[i] = "处方"+(i+1);
		}
		box1= new JComboBox(chufang1);
		box1.setOpaque(false);
		box1.setBounds(90, 30,140,28);
		JLabel l5 = new JLabel("处方：");
		l5.setBounds(30, 30, 80, 28);
		l8 = new JLabel("处方选择模式：");
		jrb3 = new JRadioButton("专家处方");
		jrb4 = new JRadioButton("自定义处方");
		jrb3.setBounds(120,70,90,30);
		jrb3.setOpaque(false);
		jrb3.setSelected(true);
		jrb4.setBounds(210,70,120,30);
		jrb4.setOpaque(false);
		l8.setBounds(20, 70, 100, 28);
		bg2.add(jrb3);
		bg2.add(jrb4);
		p9.add(l8);
		p9.add(jrb3);
		p9.add(jrb4);
		p9.add(box1);
		p9.add(l5);
		p9.setOpaque(false);
		p13.add(p9);
		//———————————————————————— ————
		JPanel p10 = new JPanel();
		p10.setLayout(null);
		p10.setBorder(BorderFactory.createTitledBorder("透热强度"));
		l6 = N9ComponentFactory.createLabel_style1("当前电极透热强度：0");
		l6.setFont(new Font("微软雅黑", 0, 18));
		l6.setText("当前电极透热强度：0");
//		b10 = new JButton("增加");
//		b11 = new JButton("减小");
		
		b10 = new PicButton("img/button_add.png", "img/button_add_off.png", "img/button_add_pressed.png");
		b11 = new PicButton("img/button_jx.png", "img/button_jx_off.png", "img/button_jx_pressed.png");
		
//		b10.setBounds(40,160,110,30);
//		b11.setBounds(200, 160, 110, 30);
		
		b10.setBounds(30,160,140,50);
		b11.setBounds(180, 160, 140, 50);
		
		s3 = new JSlider(0, 10, 6);
		s3.setOpaque(false);//add by jb2011：不填充默认背景色（否则放到白色面板板会很难看 ）
		s3.putClientProperty("JSlider.isFilled", Boolean.TRUE );
		s3.setPaintTicks(true);
		s3.setMajorTickSpacing(5);
		s3.setMinorTickSpacing(1);
		s3.setPaintLabels(false);
		s3.setSnapToTicks(true);
		s3.setValue(0);
		l6.setBounds(30, 30, 200, 40);
		s3.setBounds(30, 85, 160, 40);
		p10.setBounds(380,330,350,250);
		p10.add(l6);
		p10.add(s3);
		p10.add(b10);
		p10.add(b11);
		p10.setOpaque(false);
		p13.add(p10);
		
		//————————————————————————————
		p11 = new JPanel();
		p11.setLayout(null);
		p11.setBorder(BorderFactory.createTitledBorder("计时"));
//		label1 = N9ComponentFactory.createLabel_style1("剩余时间：");
		label1 = new JLabel("剩余时间：");
		label1.setFont(new Font("微软雅黑", 0, 30));
		label1.setText("剩余时间：");
		label1.setBounds(30, 30, 160, 40);
		clock2 = utils.get(flag).getrChannel().getClock();
		clock2.setOpaque(false);
		clock2.setBounds(180, 30, 100, 40);
		p11.add(label1);
		p11.add(clock2);
		p11.setBounds(10, 370, 360, 90);
		p11.setOpaque(false);
		p13.add(p11);
		//————————————————————————————
		p12 = new PanelBottom();
		p12.setBorder(BorderFactory.createTitledBorder("通道工作状态"));
		p12.setLayout(null);
		p12.setBounds(30,720,1250,80);
		p12.setOpaque(false);
		panel.add(p12);
		//————————————————————————————
		
		JPanel p6 = new JPanel();
		p6.setLayout(null);
		p6.setBounds(10,440,730,45);
		p6.setOpaque(false);
		panel.add(p6);
		//————————————————————————————
		s0.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				S0StateChanged(e);
			}
		});
		
		s1.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				S1StateChanged(e);
			}
		});
		
		s1.addMouseListener(new MouseAdapter() {
		
			@Override
			public void mouseReleased(MouseEvent e) {
				EditorPanel.Arrived = false;
				EditorPanel.RVO = 0;
				int now = s1.getValue();
				int[] send = new int[5];
				send[0] = 170;
				int sum=170;
				send[1] = (flag*2+1)*16+2;
				sum += (flag*2+1)*16+2;
				send[2] = now;
				sum += now;
				send[3] = 1;
				sum += 1;
				send[4] = sum;
				CommWrite.write(send, serialPort);
				
				System.out.println("左通道温度："+s1.getValue());
			}
		});
		
		s2.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				S2StateChanged(e);
			}
		});
		
		s3.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				S3StateChanged(e);
			}
		});
		
		s3.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				EditorPanel.Arrived = false;
				EditorPanel.RVO = 0;
				int now = s3.getValue();
				int[] send = new int[5];
				send[0] = 170;
				int sum=170;
				send[1] = (flag*2+1)*16+2;
				sum += (flag*2+1)*16+2;
				send[2] = now;
				sum += now;
				send[3] = 1;
				sum += 1;
				send[4] = sum;
				CommWrite.write(send, serialPort);
				
				System.out.println("右通道温度："+s3.getValue());
			}
		});
		
		box.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent e) {
				BoxItemStateChanged(e);
			}
		});
		
		jrb1.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(((JRadioButton)e.getSource()).isSelected()&&utils.get(flag).getlChannel().getSwitch_flag()){
					System.out.println("专家模式");
					box.setEnabled(true);
					
					//初始化为处方一
					utils.get(flag).getlChannel().getWaves().removeAll(utils.get(flag).getlChannel().getWaves());
					List<String>list = ShowPic.readTxtFile("chufang/处方1.txt");
					for (String string : list) {
						utils.get(flag).getlChannel().getWaves().add(ShowPic.getWave(string));						
					}
					box.setSelectedIndex(0);
					EditorPanel.utils.get(flag).getlChannel().setCustom_flag(false);
					EditorPanel.utils.get(EditorPanel.flag).getlChannel().getClock().changeLabel(timeMap.get(0));
				}
			}
		});
		
		jrb2.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(((JRadioButton)e.getSource()).isSelected()&&utils.get(flag).getlChannel().getSwitch_flag()){
					box.setEnabled(false);
					utils.get(flag).getlChannel().getWaves().removeAll(utils.get(flag).getlChannel().getWaves());
					if(!EditorPanel.utils.get(EditorPanel.flag).getlChannel().isCustom_flag())
						new PanelChufang(head, waveList,utils.get(flag).getlChannel().getWaves(), 0);
					EditorPanel.utils.get(flag).getlChannel().setCustom_flag(true);
				}
			}
		});
		
		jrb3.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(((JRadioButton)e.getSource()).isSelected()&&utils.get(flag).getrChannel().getSwitch_flag()){
					box1.setEnabled(true);
					//初始化为处方一
					utils.get(flag).getrChannel().getWaves().removeAll(utils.get(flag).getrChannel().getWaves());
					List<String>list = ShowPic.readTxtFile("chufang/处方1.txt");
					for (String string : list) {
						utils.get(flag).getrChannel().getWaves().add(ShowPic.getWave(string));
					}
					box1.setSelectedIndex(0);
					EditorPanel.utils.get(flag).getrChannel().setCustom_flag(false);
					EditorPanel.utils.get(EditorPanel.flag).getrChannel().getClock().changeLabel(timeMap.get(0));
				}
			}
		});
		
		jrb4.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(((JRadioButton)e.getSource()).isSelected()&&utils.get(flag).getrChannel().getSwitch_flag()){
					box1.setEnabled(false);
					utils.get(flag).getrChannel().getWaves().removeAll(utils.get(flag).getrChannel().getWaves());
					if(!EditorPanel.utils.get(EditorPanel.flag).getrChannel().isCustom_flag())
						new PanelChufang(head, waveList,utils.get(flag).getrChannel().getWaves(), 1);
					EditorPanel.utils.get(flag).getrChannel().setCustom_flag(true);
				}
			}
		});

		box1.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				Box1ItemStateChanged(e);
			}
		});
		
		b0.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);
		b8.addActionListener(this);
		b9.addActionListener(this);
		b10.addActionListener(this);
		b11.addActionListener(this);
		
//		setVisible(true);
		if(editorMain!=null){
//			editorMain.setVisible(false);
			this.editorMain = editorMain;
		}
	}

	protected void S0StateChanged(ChangeEvent e) {
		JSlider js = (JSlider)e.getSource();
		int end = js.getValue();
		System.out.println("左通道当前强度："+end);
		EditorPanel.utils.get(flag).getlChannel().setPower(end);
		l1.setText("当前强度："+end);
	}
	
	protected void S1StateChanged(ChangeEvent e) {
		JSlider js = (JSlider)e.getSource();
		int end = js.getValue();
		System.out.println("左通道当前电极透热强度："+end);
		EditorPanel.utils.get(flag).getlChannel().setTemp(end);
		l2.setText("当前电极透热强度："+end);
	}

	protected void S2StateChanged(ChangeEvent e) {
		JSlider js = (JSlider)e.getSource();
		int end = js.getValue();
		System.out.println("右通道当前强度："+end);
		EditorPanel.utils.get(flag).getrChannel().setPower(end);
		l4.setText("当前强度："+end);
	}
	
	protected void S3StateChanged(ChangeEvent e) {
		JSlider js = (JSlider)e.getSource();
		int end = js.getValue();
		System.out.println("右通道当前电极透热强度："+end);
		EditorPanel.utils.get(flag).getrChannel().setTemp(end);
		l6.setText("当前电极透热强度："+end);
	}
	
	/**
	 * 通道1处方选择事件响应
	 * @param e
	 */
	protected void BoxItemStateChanged(ItemEvent e) {	
		
		
		if(e.getStateChange() == ItemEvent.SELECTED){
			
			if(EditorPanel.utils.get(flag).getlChannel().getSelect() == box.getSelectedIndex()){
				System.out.println("切换通道");
				return;
			}
			
			if(EditorPanel.utils.get(flag).getlChannel().getSelect()>=70){
				utils.get(flag).getrChannel().setSelect(0);
				box1.setSelectedIndex(0);
			}
			
			String chufang = (String) box.getSelectedItem();
			System.out.println("左通道选择处方：" + chufang);
			if(box.getSelectedIndex()>=70){
				//干扰电模式
				if(rStop.get(flag)){
					JOptionPane.showMessageDialog(null, "请先关闭通道"+(flag*2+2)+"再使用干扰电模式", "警告", JOptionPane.WARNING_MESSAGE);
					box.setSelectedIndex(0);
					return;
				}
				System.out.println("干扰电模式");
				box1.setSelectedIndex(box.getSelectedIndex());
			}
			
			//初始化处方
			List<String> list = ShowPic.readTxtFile("chufang/" + chufang + ".txt");			
			utils.get(flag).getlChannel().getWaves().removeAll(utils.get(flag).getlChannel().getWaves());
			for (String string : list) {
				utils.get(flag).getlChannel().getWaves().add(ShowPic.getWave(string));						
			}
			utils.get(flag).getlChannel().setSelect(box.getSelectedIndex());
			
			//设置时间
			utils.get(flag).getlChannel().getClock().changeLabel(timeMap.get(utils.get(flag).getlChannel().getSelect()));
		}
	}

	/**
	 * 通道2处方选择事件响应
	 * @param e
	 */
	protected void Box1ItemStateChanged(ItemEvent e) {
				
		if(e.getStateChange() == ItemEvent.SELECTED){
			
			if (EditorPanel.utils.get(flag).getrChannel().getSelect() == box1.getSelectedIndex()) {
				System.out.println("切换通道");
				return;
			}
			
			if(EditorPanel.utils.get(flag).getrChannel().getSelect()>=70){
				utils.get(flag).getlChannel().setSelect(0);
				box.setSelectedIndex(0);
			}
			
			if(box1.getSelectedIndex()>=70){
				//干扰电模式
				if(lStop.get(flag)){
					JOptionPane.showMessageDialog(null, "请先关闭通道"+(flag*2+1)+"再使用干扰电模式", "警告", JOptionPane.WARNING_MESSAGE);
					box1.setSelectedIndex(0);
					return;
				}
				System.out.println("干扰电模式");
				box.setSelectedIndex(box1.getSelectedIndex());
			}
			String chufang1 = (String)box1.getSelectedItem();
			System.out.println("右通道选择处方："+chufang1);
			List<String>list = ShowPic.readTxtFile("chufang/"+chufang1+".txt");
			//初始化为处方一
			utils.get(flag).getrChannel().getWaves().removeAll(utils.get(flag).getrChannel().getWaves());
			for (String string : list) {
				utils.get(flag).getrChannel().getWaves().add(ShowPic.getWave(string));
			}
			utils.get(flag).getrChannel().setSelect(box1.getSelectedIndex());
			
			//设置时间
			utils.get(flag).getrChannel().getClock().changeLabel(timeMap.get(utils.get(flag).getrChannel().getSelect()));
		}
	}


	/**
	 * 
	 * @param name
	 * @param action
	 * @return a new Action bound to the specified string name
	 */
	public Action bind(String name, final Action action)
	{
		return bind(name, action, null);
	}

	/**
	 * 
	 * @param name
	 * @param action
	 * @return a new Action bound to the specified string name and icon
	 */
	@SuppressWarnings("serial")
	public Action bind(String name, final Action action, String iconUrl)
	{
		AbstractAction newAction = new AbstractAction(name, (iconUrl != null) ? new ImageIcon(
				iconUrl) : null)
		{
			public void actionPerformed(ActionEvent e)
			{
				action.actionPerformed(new ActionEvent(this, e.getID(), e.getActionCommand()));
			}
		};
		
		newAction.putValue(Action.SHORT_DESCRIPTION, action.getValue(Action.SHORT_DESCRIPTION));
		
		return newAction;
	}

	/**
	 * 处方管理
	 */
	public void chufangManage(int flag){
		JFrame frame = this;

		if (frame != null)
		{
			if(flag==0){
				new PanelChufang(head, waveList,utils.get(flag).getlChannel().getWaves(), 0);
			}else if(flag==1){
				new PanelChufang(head, waveList,utils.get(flag).getrChannel().getWaves(), 1);
			}
			
		}
	}
	
	/**
	 * 病例管理
	 */
	public void caseManage(){
		
		new PanelCase(this).setVisible(true);
	}
	
	/**
	 * 设置关于窗口
	 */
	public void about()
	{
		JFrame frame = this;

		if (frame != null)
		{
			EditorAboutFrame about = new EditorAboutFrame(frame);
			about.setModal(true);

			// Centers inside the application frame
			int x = frame.getX() + (frame.getWidth() - about.getWidth()) / 2;
			int y = frame.getY() + (frame.getHeight() - about.getHeight()) / 2;
			about.setLocation(x, y);

			// Shows the modal dialog and waits
			about.setVisible(true);			
		}
	}
	
	/**
	 * 窗口居中显示
	 * @param jFrame
	 */
	public static void setFrameCenter(JFrame jFrame){
		//居中显示
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int x = screen.width - jFrame.getWidth()>>1;
		int y = (screen.height - jFrame.getHeight()>>1)-32;
		jFrame.setLocation(x, y);
	}
	
	/**
	 * 设置全局字体
	 * @param fnt
	 */
	public static void initGlobalFontSetting(Font fnt) {
		FontUIResource fontRes = new FontUIResource(fnt);
		for (Enumeration<?> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource)
				UIManager.put(key, fontRes);
		}
	}
	
	public static void main(String[] args){
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	        UIManager.put("RootPane.setupButtonVisible", false);
	        
		} catch (Exception e) {
		}
		try {
			//windows风格
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			//motif风格
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			for (int i = 0; i < SwingUtils.DEFAULT_FONT.length; i++)
	            UIManager.put(SwingUtils.DEFAULT_FONT[i],new Font("微软雅黑", Font.PLAIN,14));
		} catch (Exception e) {
			e.printStackTrace();
		}
		new EditorPanel(null);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==b0){
			int now = s0.getValue();
			if (now<99) {
				now++;
				final int set = now;
				final int send = (flag*2+1)*16+2;
				EditorPanel.Arrived = false;
				EditorPanel.RVO = 0;
				CommWrite.read(serialPort);
				CommWrite.write(send, serialPort);
				final long startTime = System.currentTimeMillis();				
				Thread zj1 = new Thread(){
					@Override
					public void run() {
						while (System.currentTimeMillis()-startTime<10000) {
							if(EditorPanel.Arrived) {
								if(EditorPanel.RVO==send%256){
									s0.setValue(set);									
								}
								else {
									JOptionPane.showMessageDialog(null, "返回值错误，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
								}
								EditorPanel.Arrived = false;
								EditorPanel.RVO = 0;
								return;
							}					
						}
						JOptionPane.showMessageDialog(null, "治疗仪未响应，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
						System.out.println("超时！");
					}
				};
				zj1.start();
			}
			
		}
		else if(e.getSource()==b1){
			int now = s0.getValue();
			if (now>0) {
				now--;
				final int set = now;
				final int send = (flag*2+1)*16+3;
				EditorPanel.Arrived = false;
				EditorPanel.RVO = 0;
				CommWrite.read(serialPort);
				CommWrite.write(send, serialPort);
				final long startTime = System.currentTimeMillis();				
				Thread jx1 = new Thread(){
					@Override
					public void run() {
						while (System.currentTimeMillis()-startTime<10000) {
							if(EditorPanel.Arrived) {
								if(EditorPanel.RVO==send%256){
									s0.setValue(set);									
								}
								else {
									JOptionPane.showMessageDialog(null, "返回值错误，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
								}
								EditorPanel.Arrived = false;
								EditorPanel.RVO = 0;
								return;
							}					
						}
						JOptionPane.showMessageDialog(null, "治疗仪未响应，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
						System.out.println("超时！");
					}
				};
				jx1.start();
			}
			
		}
		else if(e.getSource()==b2){
			
			//自定义处方模式
			if (jrb2.isSelected()) {
				final int myFlag = flag;
				if (utils.get(flag).getlChannel().getWaves().isEmpty()) {
					JOptionPane.showMessageDialog(null, "请先设置通道"+(myFlag*2+1)+"处方", "警告", JOptionPane.WARNING_MESSAGE);
					return;
				}
				head[2] = flag*2+1;
				EditorPanel.Arrived = false;
				EditorPanel.RVO = 0;
				CommWrite.read(serialPort);
				CommWrite.write(head, serialPort);
				for (int[] p : waveList) {
					CommWrite.write(p, serialPort);
				}
				
				long startTime = System.currentTimeMillis();
				while(System.currentTimeMillis() - startTime <10000){
					if(EditorPanel.Arrived){
						if (EditorPanel.RVO == head[1]%256) {
							final Timer timer = new Timer();
							final long end = System.currentTimeMillis() + utils.get(myFlag).getlChannel().getCustom_time() * 60 * 1000;
							timer.schedule(new TimerTask() {
								public void run() {
									lThreads.get(myFlag).interrupt();
								}
							}, new Date(end));
							utils.get(myFlag).getlChannel().getClock().changeTime(String.valueOf(utils.get(myFlag).getlChannel().getCustom_time()));
							lStop.put(myFlag, true);
							leds.set(myFlag*2, 0);							
							utils.get(myFlag).getlChannel().setSwitch_flag(false);
							List<Bottom> bottoms = p12.getBottoms();
							for (Bottom bottom : bottoms) {
								bottom.repaint();
							}
							
							b0.setEnabled(true);
							b1.setEnabled(true);
							b3.setEnabled(true);
							b2.setEnabled(false);
							jrb1.setEnabled(false);
							jrb2.setEnabled(false);
							box.setEnabled(false);
							
							//左通道更换图片线程
							mainThread = new Thread(){
								@Override
								public void run() {									
									while(lStop.get(myFlag)){										
										int i = 0;
										final List<Wave> tmp = utils.get(myFlag).getlChannel().getWaves();
										for (int j=0; j<tmp.size();j++) {
											System.out.println("myFlag:"+myFlag);
											Wave wave = tmp.get(j);
											if(!lStop.get(myFlag)){
												break;
											}
											i = 0;
											if (wave.getTime(i)==54) {
												utils.get(myFlag).getlChannel().setImg_flag(wave.getName(i));
												if(myFlag == flag){
													pic1.repaint();
												}								
												try {
													sleep(wave.getTime(3)*1000);
												} catch (InterruptedException e) {
													System.out.println("interrupt1");
													lStop.put(myFlag, false);
													System.out.print(lStop.get(myFlag));
													break;
												}
											}else if (wave.getTime(i)==49) {
												long startMili=System.currentTimeMillis();
												long nowMili=System.currentTimeMillis();
												while(nowMili-startMili<=(wave.getTime(3)*1000)){
													utils.get(myFlag).getlChannel().setImg_flag(wave.getName(i%2));
													if(myFlag == flag){
														pic1.repaint();
													}									
													try {
														sleep(600*1000);
													}catch (InterruptedException e) {
														System.out.println("interrupt2");
														lStop.put(myFlag, false);
														System.out.print(lStop.get(myFlag));
														break;
													}
													nowMili = System.currentTimeMillis();
													i++;
												}
											}else {
												long startMili=System.currentTimeMillis();
												long nowMili=System.currentTimeMillis();
												while(nowMili-startMili<=(wave.getTime(3)*1000)){
													utils.get(myFlag).getlChannel().setImg_flag(wave.getName(i%2));
													if(myFlag == flag){
														pic1.repaint();
													}									
													try {
														sleep(wave.getTime(i%2)*1000);
													}catch (InterruptedException e) {
														System.out.println("interrupt3");
														lStop.put(myFlag, false);
														System.out.print(lStop.get(myFlag));
														break;
													}
													nowMili = System.currentTimeMillis();
													i++;
												}
											}
										}
										
										//TODO 结束
										EditorPanel.Arrived = false;
										EditorPanel.RVO = 0;
										
										CommWrite.read(serialPort);
										CommWrite.write((myFlag*2+1)*16+1, serialPort);
										long startTime = System.currentTimeMillis();
										while(System.currentTimeMillis() - startTime <10000){
											if(EditorPanel.Arrived){
												if (EditorPanel.RVO != (myFlag*2+1)*16+1) {
													JOptionPane.showMessageDialog(null, "治疗仪未正常结束，请手动终止！", "警告", JOptionPane.WARNING_MESSAGE);
												}
												utils.get(myFlag).getlChannel().setImg_flag("07");
												if(myFlag == flag){
													pic1.repaint();
												}						
												lStop.put(myFlag, false);
																	
												leds.set(myFlag*2, 1);
												
												if(myFlag == flag){
													b0.setEnabled(false);
													b1.setEnabled(false);
													b3.setEnabled(false);
													b2.setEnabled(true);
													s0.setValue(0);
													s1.setValue(0);
													jrb1.setEnabled(true);
													jrb2.setEnabled(true);
													box.setEnabled(true);													
												}
												
												utils.get(myFlag).getlChannel().setPower(0);
												utils.get(myFlag).getlChannel().setTemp(0);
												utils.get(myFlag).getlChannel().setSwitch_flag(true);
												List<Bottom> bottoms = p12.getBottoms();
												for (Bottom bottom : bottoms) {
													bottom.repaint();
												}
												System.out.println("线程结束");
												EditorPanel.Arrived = false;
												EditorPanel.RVO = 0;
												
												//设置时间
												utils.get(flag).getlChannel().getClock().changeLabel(String.valueOf(utils.get(myFlag).getlChannel().getCustom_time()));
												
												return;
											}
										}
										utils.get(myFlag).getlChannel().setImg_flag("07");				
										lStop.put(myFlag, false);					
										leds.set(myFlag*2, 1);
										
										if(myFlag == flag){
											pic1.repaint();
											b0.setEnabled(false);
											b1.setEnabled(false);
											b3.setEnabled(false);
											b2.setEnabled(true);
											s0.setValue(0);
											s1.setValue(0);
											jrb1.setEnabled(true);
											jrb2.setEnabled(true);
											box.setEnabled(true);
										}
										
										utils.get(myFlag).getlChannel().setPower(0);
										utils.get(myFlag).getlChannel().setTemp(0);
										utils.get(myFlag).getlChannel().setSwitch_flag(true);
										List<Bottom> bottoms = p12.getBottoms();
										for (Bottom bottom : bottoms) {
											bottom.repaint();
										}
										System.out.println("线程结束");
										//设置时间
										utils.get(flag).getlChannel().getClock().changeLabel(String.valueOf(utils.get(myFlag).getlChannel().getCustom_time()));
										JOptionPane.showMessageDialog(null, "治疗仪未响应，请手动终止！", "警告", JOptionPane.WARNING_MESSAGE);
										
									}					
								}
							};
							mainThread.start();
							lThreads.put(flag, mainThread);							

							int now = s1.getValue();
							int[] send = new int[5];
							send[0] = 170;
							int sum=170;
							send[1] = (flag*2+1)*16+2;
							sum += (flag*2+1)*16+2;
							send[2] = now;
							sum += now;
							send[3] = 1;
							sum += 1;
							send[4] = sum;
							CommWrite.write(send, serialPort);
							
						}else {
							JOptionPane.showMessageDialog(null, "返回值错误，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
						}
						EditorPanel.Arrived = false;
						EditorPanel.RVO = 0;
						return;
					}					
				}
				JOptionPane.showMessageDialog(null, "治疗仪未响应，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
				System.out.println("超时！");				
				return;
			}//end of 自定义处方模式
			
			//专家处方模式
			else{
				//干扰电模式
				if(box.getSelectedIndex()>=70){	
					final int myFlag = flag;
					if(rStop.get(myFlag)){
						JOptionPane.showMessageDialog(null, "请先关闭通道"+(myFlag*2+2)+",再使用干扰电模式", "警告", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					int sum = 170;
					MutilByte mb = new MutilByte();
					mb.setChannel((myFlag * 2 + 1) * 16 + 2);
					sum += (myFlag * 2 + 1) * 16 + 2;
					mb.setGrad(s1.getValue());
					sum += s1.getValue();
					mb.setChufang(box1.getSelectedIndex() + 1);
					sum += box1.getSelectedIndex() + 1;
					sum = sum%256;
					mb.setCs(sum);
					
					EditorPanel.Arrived = false;
					EditorPanel.RVO = 0;
					
					CommWrite.read(serialPort);
					CommWrite.write(mb, serialPort);
					
					long startTime = System.currentTimeMillis();
					while (System.currentTimeMillis() - startTime <10000) {
						if(EditorPanel.Arrived){
							if(EditorPanel.RVO == sum%256){
								final Timer timer = new Timer();
								final long end = System.currentTimeMillis() + Integer.parseInt(timeMap.get(utils.get(myFlag).getlChannel().getSelect())) * 60 * 1000;
								timer.schedule(new TimerTask() {
									public void run() {
										lThreads.get(myFlag).interrupt();
										rThreads.get(myFlag).interrupt();
									}
								}, new Date(end));
								utils.get(myFlag).getlChannel().getClock().changeTime(timeMap.get(utils.get(myFlag).getlChannel().getSelect()));
								utils.get(myFlag).getrChannel().getClock().changeTime(timeMap.get(utils.get(myFlag).getrChannel().getSelect()));
								leds.set(myFlag*2+1, 0);
								leds.set(myFlag*2, 0);
								utils.get(myFlag).getrChannel().setSwitch_flag(false);
								List<Bottom> bottoms = p12.getBottoms();
								for (Bottom bottom : bottoms) {
									bottom.repaint();
								}
								
								rStop.put(myFlag,true);
								b6.setEnabled(false);
								box1.setEnabled(false);
								jrb3.setEnabled(false);
								jrb4.setEnabled(false);
								
								//通道2更换图片线程
								mainThread2 = new Thread(){
									@Override
									public void run() {										
										while (rStop.get(myFlag)) {											
											int i = 0;
											final List<Wave> tmp =  utils.get(myFlag).getlChannel().getWaves();
											for (int j=0;j<tmp.size();j++) {
												System.out.println("myFlag:"+myFlag);
												Wave wave = tmp.get(j);
												if(!rStop.get(myFlag)){
													break;
												}
												i = 0;
												if (wave.getTime(i)==54) {
													utils.get(myFlag).getrChannel().setImg_flag(wave.getName(i));
													if(myFlag == flag){
														pic2.repaint();
													}										
													try {
														sleep(wave.getTime(3)*1000);
													} catch (InterruptedException e) {
														rStop.put(myFlag,false);
														break;
													}
												}else if (wave.getTime(i)==49) {
													long startMili=System.currentTimeMillis();
													long nowMili=System.currentTimeMillis();
													while(nowMili-startMili<=(wave.getTime(3)*1000)){
														utils.get(myFlag).getrChannel().setImg_flag(wave.getName(i%2));
														if(myFlag == flag){
															pic2.repaint();
														}											
														try {
															sleep(600*1000);
														}catch (InterruptedException e) {
															System.out.println("interrupt");
															rStop.put(myFlag,false);
															System.out.print(rStop.get(myFlag));
															break;
														}
														nowMili = System.currentTimeMillis();
														i++;
													}
												}else {
													long startMili=System.currentTimeMillis();
													long nowMili=System.currentTimeMillis();
													while(nowMili-startMili<=(wave.getTime(3)*1000)){
														utils.get(myFlag).getrChannel().setImg_flag(wave.getName(i%2));
														if(myFlag == flag){
															pic2.repaint();
														}											
														try {
															sleep(wave.getTime(i%2)*1000);
														} catch (InterruptedException e) {
															rStop.put(myFlag,false);
															break;
														}
														nowMili = System.currentTimeMillis();
														i++;
													}
												}						
											}
											utils.get(myFlag).getrChannel().setImg_flag("07");
											if(myFlag == flag){
												pic2.repaint();
												b6.setEnabled(true);												
												jrb3.setEnabled(true);
												jrb4.setEnabled(true);
												s2.setValue(0);
												s3.setValue(0);
												box1.setEnabled(true);
											}
												
											leds.set(myFlag*2+1, 1);
																						
											utils.get(myFlag).getrChannel().setSwitch_flag(true);
											List<Bottom> bottoms = p12.getBottoms();
											for (Bottom bottom : bottoms) {
												bottom.repaint();
											}
											
											
											utils.get(myFlag).getrChannel().setPower(0);
											utils.get(myFlag).getrChannel().setTemp(0);
											rStop.put(myFlag,false);
										}							
									}
								};
								mainThread2.start();
								rThreads.put(myFlag, mainThread2);
								
								lStop.put(myFlag, true);					
								
								utils.get(myFlag).getlChannel().setSwitch_flag(false);
								b0.setEnabled(true);
								b1.setEnabled(true);
								b3.setEnabled(true);
								b2.setEnabled(false);		
								jrb1.setEnabled(false);
								jrb2.setEnabled(false);
								box.setEnabled(false);
								
								//左通道更换图片线程
								mainThread = new Thread(){
									@Override
									public void run() {										
										while(lStop.get(myFlag)){											
											int i = 0;
											final List<Wave> tmp = utils.get(myFlag).getlChannel().getWaves();
											for (int j=0; j<tmp.size();j++) {
												System.out.println("myFlag:"+myFlag);
												Wave wave = tmp.get(j);
												if(!lStop.get(myFlag)){
													break;
												}
												i = 0;
												if (wave.getTime(i)==54) {
													utils.get(myFlag).getlChannel().setImg_flag(wave.getName(i));
													if(myFlag == flag){
														pic1.repaint();
													}								
													try {
														sleep(wave.getTime(3)*1000);
													} catch (InterruptedException e) {
														System.out.println("interrupt1");
														lStop.put(myFlag, false);
														System.out.print(lStop.get(myFlag));
														break;
													}
												}else if (wave.getTime(i)==49) {
													long startMili=System.currentTimeMillis();
													long nowMili=System.currentTimeMillis();
													while(nowMili-startMili<=(wave.getTime(3)*1000)){
														utils.get(myFlag).getlChannel().setImg_flag(wave.getName(i%2));
														if(myFlag == flag){
															pic1.repaint();
														}									
														try {
															sleep(600*1000);
														}catch (InterruptedException e) {
															System.out.println("interrupt2");
															lStop.put(myFlag, false);
															System.out.print(lStop.get(myFlag));
															break;
														}
														nowMili = System.currentTimeMillis();
														i++;
													}
												}else {
													long startMili=System.currentTimeMillis();
													long nowMili=System.currentTimeMillis();
													while(nowMili-startMili<=(wave.getTime(3)*1000)){
														utils.get(myFlag).getlChannel().setImg_flag(wave.getName(i%2));
														if(myFlag == flag){
															pic1.repaint();
														}									
														try {
															sleep(wave.getTime(i%2)*1000);
														}catch (InterruptedException e) {
															System.out.println("interrupt3");
															lStop.put(myFlag, false);
															System.out.print(lStop.get(myFlag));
															break;
														}
														nowMili = System.currentTimeMillis();
														i++;
													}
												}
											}
											
											//TODO 结束
											EditorPanel.Arrived = false;
											EditorPanel.RVO = 0;
											CommWrite.read(serialPort);
											CommWrite.write((myFlag*2+1)*16+1, serialPort);
											long startTime = System.currentTimeMillis();
											while(System.currentTimeMillis() - startTime <10000){
												if(EditorPanel.Arrived){
													if (EditorPanel.RVO != (myFlag*2+1)*16+1) {
														JOptionPane.showMessageDialog(null, "治疗仪未正常结束，请手动终止！", "警告", JOptionPane.WARNING_MESSAGE);
													}
													utils.get(myFlag).getlChannel().setImg_flag("07");
													if(myFlag == flag){
														pic1.repaint();
														
														//结束设置增大、减小、结束按钮为不可按
														b0.setEnabled(false);
														b1.setEnabled(false);
														b3.setEnabled(false);
														b2.setEnabled(true);
														s0.setValue(0);
														s1.setValue(0);
														jrb1.setEnabled(true);
														jrb2.setEnabled(true);
														box.setEnabled(true);
													}
													
													utils.get(myFlag).getlChannel().setPower(0);
													utils.get(myFlag).getlChannel().setTemp(0);
													lStop.put(myFlag, false);
																			
													leds.set(myFlag*2, 1);
													leds.set(myFlag*2+1, 1);
													
													utils.get(myFlag).getlChannel().setSwitch_flag(true);
													List<Bottom> bottoms = p12.getBottoms();
													for (Bottom bottom : bottoms) {
														bottom.repaint();
													}
													System.out.println("线程结束");
													EditorPanel.Arrived = false;
													EditorPanel.RVO = 0;
													
													//设置时间
													utils.get(myFlag).getlChannel().getClock().changeLabel(timeMap.get(utils.get(myFlag).getlChannel().getSelect()));
													utils.get(myFlag).getrChannel().getClock().changeLabel(timeMap.get(utils.get(myFlag).getrChannel().getSelect()));
													
													return;
												}
											}
											utils.get(myFlag).getlChannel().setImg_flag("07");
											if(myFlag == flag){
												pic1.repaint();
												
												b0.setEnabled(false);
												b1.setEnabled(false);
												b3.setEnabled(false);
												b2.setEnabled(true);
												s0.setValue(0);
												s1.setValue(0);
												jrb1.setEnabled(true);
												jrb2.setEnabled(true);
												box.setEnabled(true);
											}
											
											utils.get(myFlag).getlChannel().setTemp(0);
											utils.get(myFlag).getlChannel().setPower(0);
											lStop.put(myFlag, false);																	
											leds.set(myFlag*2, 1);
											
											utils.get(myFlag).getlChannel().setSwitch_flag(true);
											List<Bottom> bottoms = p12.getBottoms();
											for (Bottom bottom : bottoms) {
												bottom.repaint();
											}
											System.out.println("线程结束");
											//设置时间
											utils.get(myFlag).getlChannel().getClock().changeLabel(timeMap.get(utils.get(myFlag).getlChannel().getSelect()));
											utils.get(myFlag).getrChannel().getClock().changeLabel(timeMap.get(utils.get(myFlag).getrChannel().getSelect()));
											JOptionPane.showMessageDialog(null, "治疗仪未响应，请手动终止！", "警告", JOptionPane.WARNING_MESSAGE);
											
										}					
									}
								};
								mainThread.start();
								lThreads.put(myFlag, mainThread);
							}else {
								System.out.println(EditorPanel.RVO);
								JOptionPane.showMessageDialog(null, "返回值错误，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
							}
							EditorPanel.Arrived = false;
							EditorPanel.RVO = 0;
							return;
						}						
					}					
					JOptionPane.showMessageDialog(null, "治疗仪未响应，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
					System.out.println("超时！");
					return;
				}//end of 干扰电模式
				
				//普通模式
				else {
					final int myFlag = flag;
					int sum = 170;					
					MutilByte mb = new MutilByte();
					mb.setChannel((myFlag * 2 + 1) * 16 + 2);
					sum += (myFlag * 2 + 1) * 16 + 2;
					mb.setGrad(s1.getValue());
					sum += s1.getValue();
					mb.setChufang(box.getSelectedIndex() + 1);
					sum += box.getSelectedIndex() + 1;
					mb.setCs(sum);
					System.out.println(Integer.toHexString(box.getSelectedIndex() + 1));
					
					EditorPanel.Arrived = false;
					EditorPanel.RVO = 0;
					
					CommWrite.read(serialPort);
					CommWrite.write(mb, serialPort);
					
					long startTime = System.currentTimeMillis();
					while(System.currentTimeMillis() - startTime <10000){
//						System.out.println("监听！");
//						System.out.println(System.currentTimeMillis() - startTime);
						if(EditorPanel.Arrived){
							if (EditorPanel.RVO == sum%256) {
								final Timer timer = new Timer();
								final long end = System.currentTimeMillis() + Integer.parseInt(timeMap.get(utils.get(myFlag).getlChannel().getSelect())) * 60 * 1000;
								timer.schedule(new TimerTask() {
									public void run() {
										lThreads.get(myFlag).interrupt();
									}
								}, new Date(end));
								utils.get(myFlag).getlChannel().getClock().changeTime(timeMap.get(utils.get(myFlag).getlChannel().getSelect()));
								
								//TODO 测试同步
//								final Timer timer = new Timer();
//								final long end = System.currentTimeMillis() + 1 * 60 * 1000;
//								timer.schedule(new TimerTask() {
//									public void run() {
//										lThreads.get(myFlag).interrupt();
//									}
//								}, new Date(end));
//								utils.get(myFlag).getlChannel().getClock().changeTime("1");
								
								
								lStop.put(myFlag, true);
								leds.set(myFlag*2, 0);
								
								utils.get(myFlag).getlChannel().setSwitch_flag(false);
								List<Bottom> bottoms = p12.getBottoms();
								for (Bottom bottom : bottoms) {
									bottom.repaint();
								}
								b0.setEnabled(true);
								b1.setEnabled(true);
								b3.setEnabled(true);
								b2.setEnabled(false);
								box.setEnabled(false);
								jrb1.setEnabled(false);
								jrb2.setEnabled(false);
								
								//左通道更换图片线程
								mainThread = new Thread(){
									@Override
									public void run() {
										
										while(lStop.get(myFlag)){
											
											int i = 0;											
											for (int j=0; j<utils.get(myFlag).getlChannel().getWaves().size();j++) {
												System.out.println("myFlag:"+myFlag);
												Wave wave = utils.get(myFlag).getlChannel().getWaves().get(j);
												if(!lStop.get(myFlag)){
													break;
												}
												i = 0;
												if (wave.getTime(i)==54) {
													utils.get(myFlag).getlChannel().setImg_flag(wave.getName(i));
													if(myFlag == flag){
														pic1.repaint();
													}								
													try {
														sleep(wave.getTime(3)*1000);
													} catch (InterruptedException e) {
														System.out.println("interrupt1");
														lStop.put(myFlag, false);
														System.out.print(lStop.get(myFlag));
														break;
													}
												}else if (wave.getTime(i)==49) {
													long startMili=System.currentTimeMillis();
													long nowMili=System.currentTimeMillis();
													while(nowMili-startMili<=(wave.getTime(3)*1000)){
														utils.get(myFlag).getlChannel().setImg_flag(wave.getName(i%2));
														if(myFlag == flag){
															pic1.repaint();
														}									
														try {
															sleep(600*1000);
														}catch (InterruptedException e) {
															
															System.out.println("interrupt2");
															lStop.put(myFlag, false);
															System.out.print(lStop.get(myFlag));
															break;
														}
														nowMili = System.currentTimeMillis();
														i++;
													}
												}else {
													long startMili=System.currentTimeMillis();
													long nowMili=System.currentTimeMillis();
													while(nowMili-startMili<=(wave.getTime(3)*1000)){
														utils.get(myFlag).getlChannel().setImg_flag(wave.getName(i%2));
														if(myFlag == flag){
															pic1.repaint();
														}									
														try {
															sleep(wave.getTime(i%2)*1000);
														}catch (InterruptedException e) {
															System.out.println("interrupt3");
															lStop.put(myFlag, false);
															System.out.print(lStop.get(myFlag));
															break;
														}
														nowMili = System.currentTimeMillis();
														i++;
													}
												}
												System.out.println("myFlag:"+myFlag);
												System.out.println("myFlag:"+utils.get(myFlag).getlChannel().getWaves().get(0).getTime(3));
												System.out.println("wave大小："+utils.get(myFlag).getlChannel().getWaves().size());
											}
											
											//TODO 结束
											EditorPanel.Arrived = false;
											EditorPanel.RVO = 0;
											
											CommWrite.read(serialPort);
											CommWrite.write((myFlag*2+1)*16+1, serialPort);
											long startTime = System.currentTimeMillis();
											while(System.currentTimeMillis() - startTime <10000){
												if(EditorPanel.Arrived){
													if (EditorPanel.RVO != (myFlag*2+1)*16+1) {
														JOptionPane.showMessageDialog(null, "治疗仪未正常结束，请手动终止！", "警告", JOptionPane.WARNING_MESSAGE);
													}
													utils.get(myFlag).getlChannel().setImg_flag("07");
													if(myFlag == flag){
														pic1.repaint();
														
														b0.setEnabled(false);
														b1.setEnabled(false);
														b3.setEnabled(false);
														b2.setEnabled(true);
														s0.setValue(0);
														s1.setValue(0);
														jrb1.setEnabled(true);
														jrb2.setEnabled(true);
														box.setEnabled(true);
													}
													
													utils.get(myFlag).getlChannel().setPower(0);
													utils.get(myFlag).getlChannel().setTemp(0);
													lStop.put(myFlag, false);																			
													leds.set(myFlag*2, 1);
													
													utils.get(myFlag).getlChannel().setSwitch_flag(true);
													List<Bottom> bottoms = p12.getBottoms();
													for (Bottom bottom : bottoms) {
														bottom.repaint();
													}
													System.out.println("线程结束");
													EditorPanel.Arrived = false;
													EditorPanel.RVO = 0;
													
													utils.get(myFlag).getlChannel().getClock().changeLabel(timeMap.get(utils.get(myFlag).getlChannel().getSelect()));
													
													return;
												}
												
											}
											utils.get(myFlag).getlChannel().setImg_flag("07");
											if(myFlag == flag){
												pic1.repaint();
												
												b0.setEnabled(false);
												b1.setEnabled(false);
												b3.setEnabled(false);
												b2.setEnabled(true);
												s0.setValue(0);
												s1.setValue(0);
												jrb1.setEnabled(true);
												jrb2.setEnabled(true);
												box.setEnabled(true);
											}
											
											utils.get(myFlag).getlChannel().setPower(0);
											utils.get(myFlag).getlChannel().setTemp(0);
											lStop.put(myFlag, false);																	
											leds.set(myFlag*2, 1);
											
											utils.get(myFlag).getlChannel().setSwitch_flag(true);
											List<Bottom> bottoms = p12.getBottoms();
											for (Bottom bottom : bottoms) {
												bottom.repaint();
											}
											System.out.println("线程结束");
											
											utils.get(myFlag).getlChannel().getClock().changeLabel(timeMap.get(utils.get(myFlag).getlChannel().getSelect()));
											
											JOptionPane.showMessageDialog(null, "治疗仪未响应，请手动终止！", "警告", JOptionPane.WARNING_MESSAGE);
											
										}					
									}
								};
								mainThread.start();
								lThreads.put(myFlag, mainThread);
							}else {
								System.out.println(EditorPanel.RVO);
								JOptionPane.showMessageDialog(null, "返回值错误，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
							}
							EditorPanel.Arrived = false;
							EditorPanel.RVO = 0;
							return;
						}						
					}
					JOptionPane.showMessageDialog(null, "治疗仪未响应，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
					System.out.println("超时！");
					return;
				}//end of 普通模式
				
			}//end of 专家处方模式		
			
		}
		else if(e.getSource()==b3){
			if(box.getSelectedIndex()>=70){
//				b6.setEnabled(true);
//				if(jrb3.isSelected()){
//					box1.setEnabled(true);
//				}				
//				jrb3.setEnabled(true);
//				jrb4.setEnabled(true);
//				rThreads.get(flag).interrupt();
//				s2.setValue(0);
//				s3.setValue(0);
//				utils.get(flag).getrChannel().setSwitch_flag(true);
//				leds.set(flag*2+1, 1);
//				List<Bottom> bottoms = p12.getBottoms();
//				for (Bottom bottom : bottoms) {
//					bottom.repaint();
//				}
				rThreads.get(flag).interrupt();
			}
//			s0.setValue(0);
//			s1.setValue(0);
//			if (jrb1.isSelected()) {
//				box.setEnabled(true);
//			}
//			jrb1.setEnabled(true);
//			jrb2.setEnabled(true);
//			leds.set(flag*2, 1);
//			b0.setEnabled(false);
//			b1.setEnabled(false);
//			b3.setEnabled(false);
//			b2.setEnabled(true);
//			utils.get(flag).getlChannel().setSwitch_flag(true);
//			List<Bottom> bottoms = p12.getBottoms();
//			for (Bottom bottom : bottoms) {
//				bottom.repaint();
//			}
			lThreads.get(flag).interrupt();
		}
		else if(e.getSource()==b4){
			int now = s2.getValue();
			if (now<99) {
				now++;
				final int set = now;
				final int send;
				if(box1.getSelectedIndex()>=70){
					send = (flag*2+1)*16+2;
				}else{
					send = (flag*2+2)*16+2;
				}				
				
				EditorPanel.Arrived = false;
				EditorPanel.RVO = 0;
				
				CommWrite.read(serialPort);
				CommWrite.write(send, serialPort);
				final long startTime = System.currentTimeMillis();
				Thread zj2 = new Thread(){
					@Override
					public void run() {
						while (System.currentTimeMillis()-startTime<10000) {
							if(EditorPanel.Arrived) {
								if(EditorPanel.RVO==send%256){
									s2.setValue(set);									
								}
								else {
									System.out.println(EditorPanel.RVO);
									JOptionPane.showMessageDialog(null, "返回值错误，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
								}
								EditorPanel.Arrived = false;
								EditorPanel.RVO = 0;
								return;
							}					
						}
						JOptionPane.showMessageDialog(null, "治疗仪未响应，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
						System.out.println("超时！");
					}
				};
				zj2.start();
			}
			
		}
		else if(e.getSource()==b5){
			int now = s2.getValue();
			if (now>0) {
				now--;
				final int set = now;
				final int send;
				if(box1.getSelectedIndex()>=70){
					send = (flag*2+1)*16+3;
				}else{
					send = (flag*2+2)*16+3;
				}				
				
				EditorPanel.Arrived = false;
				EditorPanel.RVO = 0;
				
				CommWrite.read(serialPort);
				CommWrite.write(send, serialPort);
				final long startTime = System.currentTimeMillis();
				
				Thread jx2 = new Thread(){
					@Override
					public void run() {
						while (System.currentTimeMillis()-startTime<10000) {
							if(EditorPanel.Arrived) {
								if(EditorPanel.RVO==send%256){
									s2.setValue(set);									
								}
								else {
									System.out.println(EditorPanel.RVO);
									JOptionPane.showMessageDialog(null, "返回值错误，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
								}
								EditorPanel.Arrived = false;
								EditorPanel.RVO = 0;
								return;
							}					
						}
						JOptionPane.showMessageDialog(null, "治疗仪未响应，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
						System.out.println("超时！");
					}
				};
				jx2.start();
			}
			
		}
		else if(e.getSource()==b6){
			final int myFlag = flag;
			//自定义处方模式
			if (jrb4.isSelected()) {
				if (utils.get(flag).getrChannel().getWaves().isEmpty()) {
					JOptionPane.showMessageDialog(null, "请先设置通道"+(myFlag*2+2)+"处方", "警告", JOptionPane.WARNING_MESSAGE);
					return;
				}
				head[2] = myFlag*2+2;
				
				EditorPanel.Arrived = false;
				EditorPanel.RVO = 0;
				
				CommWrite.read(serialPort);
				CommWrite.write(head, serialPort);
				for (int[] p : waveList) {
					CommWrite.write(p, serialPort);
				}
				
				long startTime = System.currentTimeMillis();
				while (System.currentTimeMillis() - startTime < 10000) {
					if(EditorPanel.Arrived){
						if(EditorPanel.RVO == head[1]%256){
							final Timer timer = new Timer();
							final long end = System.currentTimeMillis() + utils.get(myFlag).getrChannel().getCustom_time() * 60 * 1000;
							timer.schedule(new TimerTask() {
								public void run() {
									rThreads.get(myFlag).interrupt();
								}
							}, new Date(end));
							utils.get(myFlag).getrChannel().getClock().changeTime(String.valueOf(utils.get(myFlag).getrChannel().getCustom_time()));
							rStop.put(myFlag,true);		
							leds.set(myFlag*2+1, 0);
							b4.setEnabled(true);
							b5.setEnabled(true);
							b6.setEnabled(false);
							b7.setEnabled(true);
							box1.setEnabled(false);
							jrb3.setEnabled(false);
							jrb4.setEnabled(false);
							utils.get(myFlag).getrChannel().setSwitch_flag(false);
							List<Bottom> bottoms = p12.getBottoms();
							for (Bottom bottom : bottoms) {
								bottom.repaint();
							}
							
							//通道2更换图片线程
							mainThread2 = new Thread(){
								@Override
								public void run() {
									while (rStop.get(myFlag)) {
										int i = 0;
										final List<Wave> tmp = utils.get(myFlag).getrChannel().getWaves();
										for (int j=0;j<tmp.size();j++) {
											Wave wave = tmp.get(j);
											if(!rStop.get(myFlag)){
												break;
											}
											i = 0;
											System.out.println("右通道图片："+wave.getName(i));
											if (wave.getTime(i)==54) {
												utils.get(myFlag).getrChannel().setImg_flag(wave.getName(i));
												if(myFlag == flag){									
													pic2.repaint();
												}								
												try {
													sleep(wave.getTime(3)*1000);
												} catch (InterruptedException e) {
													rStop.put(myFlag, false);
													break;
												}
											}else if (wave.getTime(i)==49) {
												long startMili=System.currentTimeMillis();
												long nowMili=System.currentTimeMillis();
												while(nowMili-startMili<=(wave.getTime(3)*1000)){
													utils.get(myFlag).getrChannel().setImg_flag(wave.getName(i%2));
													if(myFlag == flag){
														
														pic2.repaint();
													}									
													try {
														sleep(600*1000);
													}catch (InterruptedException e) {
														System.out.println("interrupt");
														rStop.put(myFlag,false);
														System.out.print(rStop.get(myFlag));
														break;
													}
													nowMili = System.currentTimeMillis();
													i++;
												}
											}else {
												long startMili=System.currentTimeMillis();
												long nowMili=System.currentTimeMillis();
												while(nowMili-startMili<=(wave.getTime(3)*1000)){
													utils.get(myFlag).getrChannel().setImg_flag(wave.getName(i%2));
													if(myFlag == flag){
														pic2.repaint();
													}									
													try {
														sleep(wave.getTime(i%2)*1000);
													} catch (InterruptedException e) {
														rStop.put(myFlag,false);
														break;
													}
													nowMili = System.currentTimeMillis();
													i++;
												}
											}						
										}
										
										//TODO 结束异常怎么办？
										EditorPanel.Arrived = false;
										EditorPanel.RVO = 0;
										
										CommWrite.read(serialPort);
										CommWrite.write((myFlag*2+2)*16+1, serialPort);
										long startTime = System.currentTimeMillis();
										while(System.currentTimeMillis() - startTime <10000){
											if(EditorPanel.Arrived){
												if (EditorPanel.RVO != (myFlag*2+2)*16+1) {
													JOptionPane.showMessageDialog(null, "治疗仪未正常结束，请手动终止！", "警告", JOptionPane.WARNING_MESSAGE);
												}
												utils.get(myFlag).getrChannel().setImg_flag("07");
												if(myFlag == flag){
													pic2.repaint();
													
													b7.setEnabled(false);
													b4.setEnabled(false);
													b5.setEnabled(false);
													b6.setEnabled(true);
													jrb3.setEnabled(true);
													jrb4.setEnabled(true);
													s2.setValue(0);
													s3.setValue(0);
													box1.setEnabled(true);
												}
												
												utils.get(myFlag).getrChannel().setPower(0);
												utils.get(myFlag).getrChannel().setTemp(0);
												rStop.put(myFlag,false);
													
												leds.set(myFlag*2+1, 1);
												
												
												utils.get(myFlag).getrChannel().setSwitch_flag(true);
												List<Bottom> bottoms = p12.getBottoms();
												for (Bottom bottom : bottoms) {
													bottom.repaint();
												}
												
												EditorPanel.Arrived = false;
												EditorPanel.RVO = 0;
												
												utils.get(myFlag).getrChannel().getClock().changeLabel(String.valueOf(utils.get(myFlag).getrChannel().getCustom_time()));
												
												return;
											}
										}
										utils.get(myFlag).getrChannel().setImg_flag("07");
										if(myFlag == flag){
											pic2.repaint();
											
											b7.setEnabled(false);
											b4.setEnabled(false);
											b5.setEnabled(false);
											b6.setEnabled(true);
											jrb3.setEnabled(true);
											jrb4.setEnabled(true);
											s2.setValue(0);
											s3.setValue(0);
											box1.setEnabled(true);
										}	
										utils.get(myFlag).getrChannel().setPower(0);
										utils.get(myFlag).getrChannel().setTemp(0);
										rStop.put(myFlag,false);											
										leds.set(myFlag*2+1, 1);
										
										utils.get(myFlag).getrChannel().setSwitch_flag(true);
										List<Bottom> bottoms = p12.getBottoms();
										for (Bottom bottom : bottoms) {
											bottom.repaint();
										}
										
										utils.get(myFlag).getrChannel().getClock().changeLabel(String.valueOf(utils.get(myFlag).getrChannel().getCustom_time()));
										
										JOptionPane.showMessageDialog(null, "治疗仪未响应，请手动终止！", "警告", JOptionPane.WARNING_MESSAGE);
										
									}
								}
							};
							mainThread2.start();
							rThreads.put(myFlag, mainThread2);
							
							int now = s3.getValue();
							int[] send = new int[5];
							send[0] = 170;
							int sum=170;
							send[1] = (flag*2+1)*16+2;
							sum += (flag*2+1)*16+2;
							send[2] = now;
							sum += now;
							send[3] = 1;
							sum += 1;
							send[4] = sum;
							CommWrite.write(send, serialPort);
						}else {
							System.out.println(EditorPanel.RVO);
							JOptionPane.showMessageDialog(null,	"返回值错误，请重启操作！", "警告",
									JOptionPane.WARNING_MESSAGE);
						}					
						
						EditorPanel.Arrived = false;
						EditorPanel.RVO = 0;
						return;
					}
				}
				JOptionPane.showMessageDialog(null, "治疗仪未响应，请重启操作！", "警告",
						JOptionPane.WARNING_MESSAGE);
				System.out.println("超时！");
				
				
				return;	
			}//end of 自定义处方模式
			
			//专家处方模式
			else {
				
				//干扰电模式
				if(box1.getSelectedIndex()>=70){
					if(lStop.get(myFlag)){
						JOptionPane.showMessageDialog(null, "请先关闭通道"+(myFlag*2+1)+"再使用干扰电模式", "警告", JOptionPane.WARNING_MESSAGE);
					    return;
					}
					
					int sum = 170;
					MutilByte mb = new MutilByte();
					mb.setChannel((myFlag * 2 + 1) * 16 + 2);
					sum += (myFlag * 2 + 1) * 16 + 2;
					mb.setGrad(s3.getValue());
					sum += s3.getValue();
					mb.setChufang(box1.getSelectedIndex() + 1);
					sum += box1.getSelectedIndex() + 1;
					mb.setCs(sum);
					
					EditorPanel.Arrived = false;
					EditorPanel.RVO = 0;
					
					CommWrite.read(serialPort);
					CommWrite.write(mb, serialPort);
					
					long startTime = System.currentTimeMillis();
					while (System.currentTimeMillis() - startTime < 10000) {
						if (EditorPanel.Arrived) {
							if (EditorPanel.RVO == sum%256) {
								final Timer timer = new Timer();
								final long end = System.currentTimeMillis() + Integer.parseInt(timeMap.get(utils.get(myFlag).getrChannel().getSelect())) * 60 * 1000;
								timer.schedule(new TimerTask() {
									public void run() {
										lThreads.get(myFlag).interrupt();
										rThreads.get(myFlag).interrupt();
									}
								}, new Date(end));
								utils.get(myFlag).getlChannel().getClock().changeTime(timeMap.get(utils.get(myFlag).getrChannel().getSelect()));
								utils.get(myFlag).getrChannel().getClock().changeTime(timeMap.get(utils.get(myFlag).getrChannel().getSelect()));
								b2.setEnabled(false);
								b0.setEnabled(true);
								b1.setEnabled(true);
								b3.setEnabled(true);
								box.setEnabled(false);
								jrb1.setEnabled(false);
								jrb2.setEnabled(false);
								
								lStop.put(myFlag,true);
								rStop.put(myFlag,true);
								//左通道更换图片线程
								mainThread = new Thread(){
									@Override
									public void run() {
										while(lStop.get(myFlag)){
											int i = 0;
											final List<Wave> tmp = utils.get(myFlag).getrChannel().getWaves();
											for (int j=0;j<tmp.size();j++) {
												Wave wave = tmp.get(j);
												if(!lStop.get(myFlag)){
													break;
												}
												i = 0;
												if (wave.getTime(i)==54) {
													utils.get(myFlag).getlChannel().setImg_flag(wave.getName(i));
													if(myFlag == flag){
														pic1.repaint();
													}										
													try {
														sleep(wave.getTime(3)*1000);
													} catch (InterruptedException e) {
														System.out.println("interrupt");
														lStop.put(myFlag, false);
														System.out.print(lStop.get(myFlag));
														break;
													}
												}else if (wave.getTime(i)==49) {
													long startMili=System.currentTimeMillis();
													long nowMili=System.currentTimeMillis();
													while(nowMili-startMili<=(wave.getTime(3)*1000)){
														utils.get(myFlag).getlChannel().setImg_flag(wave.getName(i%2));
														if(myFlag == flag){
															pic1.repaint();
														}											
														try {
															sleep(600*1000);
														}catch (InterruptedException e) {
															System.out.println("interrupt");
															lStop.put(myFlag, false);
															System.out.print(lStop.get(myFlag));
															break;
														}
														nowMili = System.currentTimeMillis();
														i++;
													}
												}else {
													long startMili=System.currentTimeMillis();
													long nowMili=System.currentTimeMillis();
													while(nowMili-startMili<=(wave.getTime(3)*1000)){
														utils.get(myFlag).getlChannel().setImg_flag(wave.getName(i%2));
														if(myFlag == flag){
															pic1.repaint();
														}											
														try {
															sleep(wave.getTime(i%2)*1000);
														}catch (InterruptedException e) {
															System.out.println("interrupt");
															lStop.put(myFlag, false);
															System.out.print(lStop.get(myFlag));
															break;
														}
														nowMili = System.currentTimeMillis();
														i++;
													}
												}
											}
											utils.get(myFlag).getlChannel().setImg_flag("07");
											if(myFlag == flag){
												pic1.repaint();
												
												b0.setEnabled(false);
												b1.setEnabled(false);
												b2.setEnabled(true);
												b3.setEnabled(false);
												jrb1.setEnabled(true);
												jrb2.setEnabled(true);
												s0.setValue(0);
												s1.setValue(0);
											}
											
											utils.get(myFlag).getlChannel().setPower(0);
											utils.get(myFlag).getlChannel().setTemp(0);
											lStop.put(myFlag, false);
											System.out.println("线程结束");
										}						
									}
								};
								mainThread.start();
								lThreads.put(myFlag, mainThread);
									
								leds.set(myFlag*2+1, 0);
								leds.set(myFlag*2, 0);
								b6.setEnabled(false);
								box1.setEnabled(false);
								jrb3.setEnabled(false);
								jrb4.setEnabled(false);
								utils.get(flag).getlChannel().setSwitch_flag(false);
								utils.get(flag).getrChannel().setSwitch_flag(false);
								List<Bottom> bottoms = p12.getBottoms();
								for (Bottom bottom : bottoms) {
									bottom.repaint();
								}
								
								//右通道更换图片线程
								mainThread2 = new Thread(){
									@Override
									public void run() {
										while (rStop.get(myFlag)) {
											int i = 0;
											final List<Wave> tmp = utils.get(myFlag).getrChannel().getWaves();
											for (int j=0;j<tmp.size();j++) {
												Wave wave = tmp.get(j);
												if(!rStop.get(myFlag)){
													break;
												}
												i = 0;
												System.out.println("干扰电右通道图片："+wave.getName(i));
												if (wave.getTime(i)==54) {
													utils.get(myFlag).getrChannel().setImg_flag(wave.getName(i));
													if(myFlag == flag){									
														pic2.repaint();
													}								
													try {
														sleep(wave.getTime(3)*1000);
													} catch (InterruptedException e) {
														rStop.put(myFlag, false);
														break;
													}
												}else if (wave.getTime(i)==49) {
													long startMili=System.currentTimeMillis();
													long nowMili=System.currentTimeMillis();
													while(nowMili-startMili<=(wave.getTime(3)*1000)){
														utils.get(myFlag).getrChannel().setImg_flag(wave.getName(i%2));
														if(myFlag == flag){
															
															pic2.repaint();
														}									
														try {
															sleep(600*1000);
														}catch (InterruptedException e) {
															System.out.println("interrupt");
															rStop.put(myFlag,false);
															System.out.print(rStop.get(myFlag));
															break;
														}
														nowMili = System.currentTimeMillis();
														i++;
													}
												}else {
													long startMili=System.currentTimeMillis();
													long nowMili=System.currentTimeMillis();
													while(nowMili-startMili<=(wave.getTime(3)*1000)){
														utils.get(myFlag).getrChannel().setImg_flag(wave.getName(i%2));
														if(myFlag == flag){
															pic2.repaint();
														}									
														try {
															sleep(wave.getTime(i%2)*1000);
														} catch (InterruptedException e) {
															rStop.put(myFlag,false);
															break;
														}
														nowMili = System.currentTimeMillis();
														i++;
													}
												}						
											}
											
											//TODO 结束异常怎么办？
											EditorPanel.Arrived = false;
											EditorPanel.RVO = 0;
											
											CommWrite.read(serialPort);
											CommWrite.write((myFlag*2+1)*16+1, serialPort);
											long startTime = System.currentTimeMillis();
											while(System.currentTimeMillis() - startTime <10000){
												if(EditorPanel.Arrived){
													if (EditorPanel.RVO != (myFlag*2+1)*16+1) {
														JOptionPane.showMessageDialog(null, "治疗仪未正常结束，请手动终止！", "警告", JOptionPane.WARNING_MESSAGE);
													}
													utils.get(myFlag).getrChannel().setImg_flag("07");
													if(myFlag == flag){
														pic2.repaint();
														
														b6.setEnabled(true);
														jrb3.setEnabled(true);
														jrb4.setEnabled(true);
														s2.setValue(0);
														s3.setValue(0);
													}	
													
													utils.get(myFlag).getrChannel().setPower(0);
													utils.get(myFlag).getrChannel().setTemp(0);
													rStop.put(myFlag,false);
													leds.set(myFlag*2, 1);
													leds.set(myFlag*2+1, 1);
													
													utils.get(myFlag).getlChannel().setSwitch_flag(true);
													utils.get(myFlag).getrChannel().setSwitch_flag(true);
													List<Bottom> bottoms = p12.getBottoms();
													for (Bottom bottom : bottoms) {
														bottom.repaint();
													}
													
													EditorPanel.Arrived = false;
													EditorPanel.RVO = 0;
													
													utils.get(myFlag).getlChannel().getClock().changeLabel(timeMap.get(utils.get(myFlag).getlChannel().getSelect()));
													utils.get(myFlag).getrChannel().getClock().changeLabel(timeMap.get(utils.get(myFlag).getrChannel().getSelect()));
													
													return;
												}
											}
											utils.get(myFlag).getrChannel().setImg_flag("07");
											if(myFlag == flag){
												pic2.repaint();
											}						
											rStop.put(myFlag,false);
											if(jrb3.isSelected()){
												b6.setEnabled(true);
												jrb3.setEnabled(true);
												jrb4.setEnabled(true);
												s2.setValue(0);
												s3.setValue(0);
											}	
											
											utils.get(myFlag).getrChannel().setPower(0);
											utils.get(myFlag).getrChannel().setTemp(0);
											leds.set(myFlag*2+1, 1);
											
											utils.get(myFlag).getrChannel().setSwitch_flag(true);
											List<Bottom> bottoms = p12.getBottoms();
											for (Bottom bottom : bottoms) {
												bottom.repaint();
											}
											
											utils.get(myFlag).getlChannel().getClock().changeLabel(timeMap.get(utils.get(myFlag).getlChannel().getSelect()));
											utils.get(myFlag).getrChannel().getClock().changeLabel(timeMap.get(utils.get(myFlag).getrChannel().getSelect()));
											
											JOptionPane.showMessageDialog(null, "治疗仪未响应，请手动终止！", "警告", JOptionPane.WARNING_MESSAGE);
											
										}
									}
								};
								mainThread2.start();
								rThreads.put(myFlag, mainThread2);
							} else {
								JOptionPane.showMessageDialog(null,
										"返回值错误，请重启操作！", "警告",
										JOptionPane.WARNING_MESSAGE);
							}
							EditorPanel.Arrived = false;
							EditorPanel.RVO = 0;
							return;
						}
					}
					JOptionPane.showMessageDialog(null, "治疗仪未响应，请重启操作！", "警告",
							JOptionPane.WARNING_MESSAGE);
					System.out.println("超时！");
					return;				
					
				}//end of 干扰电模式
				
				//普通模式
				else{					
					int sum = 170;
					MutilByte mb = new MutilByte();
					mb.setChannel((flag * 2 + 2) * 16 + 2);
					sum += (flag * 2 + 2) * 16 + 2;
					mb.setGrad(s3.getValue());
					sum += s3.getValue();
					mb.setChufang(box1.getSelectedIndex() + 1);
					sum += box1.getSelectedIndex() + 1;
					mb.setCs(sum);
					
					EditorPanel.Arrived = false;
					EditorPanel.RVO = 0;
					
					CommWrite.read(serialPort);
					CommWrite.write(mb, serialPort);
					
					long startTime = System.currentTimeMillis();
					while (System.currentTimeMillis() - startTime < 10000) {
						if(EditorPanel.Arrived){
							if(EditorPanel.RVO == sum%256){
								final Timer timer = new Timer();
								final long end = System.currentTimeMillis() + Integer.parseInt(timeMap.get(utils.get(myFlag).getrChannel().getSelect())) * 60 * 1000;
								timer.schedule(new TimerTask() {
									public void run() {
										rThreads.get(myFlag).interrupt();
									}
								}, new Date(end));
								utils.get(myFlag).getrChannel().getClock().changeTime(timeMap.get(utils.get(myFlag).getrChannel().getSelect()));
								rStop.put(myFlag,true);		
								leds.set(flag*2+1, 0);
								b6.setEnabled(false);
								b7.setEnabled(true);
								b4.setEnabled(true);
								b5.setEnabled(true);
								box1.setEnabled(false);
								jrb3.setEnabled(false);
								jrb4.setEnabled(false);
								utils.get(flag).getrChannel().setSwitch_flag(false);
								List<Bottom> bottoms = p12.getBottoms();
								for (Bottom bottom : bottoms) {
									bottom.repaint();
								}
								
								//通道2更换图片线程
								mainThread2 = new Thread(){
									@Override
									public void run() {
										while (rStop.get(myFlag)) {
											int i = 0;
											final List<Wave> tmp = utils.get(myFlag).getrChannel().getWaves();
											for (int j=0;j<tmp.size();j++) {
												Wave wave = tmp.get(j);
												if(!rStop.get(myFlag)){
													break;
												}
												i = 0;
												System.out.println("普通模式右通道图片："+wave.getName(i));
												if (wave.getTime(i)==54) {
													utils.get(myFlag).getrChannel().setImg_flag(wave.getName(i));
													if(myFlag == flag){									
														pic2.repaint();
													}								
													try {
														sleep(wave.getTime(3)*1000);
													} catch (InterruptedException e) {
														rStop.put(myFlag, false);
														break;
													}
												}else if (wave.getTime(i)==49) {
													long startMili=System.currentTimeMillis();
													long nowMili=System.currentTimeMillis();
													while(nowMili-startMili<=(wave.getTime(3)*1000)){
														utils.get(myFlag).getrChannel().setImg_flag(wave.getName(i%2));
														if(myFlag == flag){
															
															pic2.repaint();
														}									
														try {
															sleep(600*1000);
														}catch (InterruptedException e) {
															System.out.println("interrupt");
															rStop.put(myFlag,false);
															System.out.print(rStop.get(myFlag));
															break;
														}
														nowMili = System.currentTimeMillis();
														i++;
													}
												}else {
													long startMili=System.currentTimeMillis();
													long nowMili=System.currentTimeMillis();
													while(nowMili-startMili<=(wave.getTime(3)*1000)){
														utils.get(myFlag).getrChannel().setImg_flag(wave.getName(i%2));
														if(myFlag == flag){
															pic2.repaint();
														}									
														try {
															sleep(wave.getTime(i%2)*1000);
														} catch (InterruptedException e) {
															rStop.put(myFlag,false);
															break;
														}
														nowMili = System.currentTimeMillis();
														i++;
													}
												}						
											}
											
											//TODO 结束异常怎么办？
											EditorPanel.Arrived = false;
											EditorPanel.RVO = 0;
											
											CommWrite.read(serialPort);
											CommWrite.write((myFlag*2+2)*16+1, serialPort);
											long startTime = System.currentTimeMillis();
											while(System.currentTimeMillis() - startTime <10000){
												if(EditorPanel.Arrived){
													if (EditorPanel.RVO != (myFlag*2+2)*16+1) {
														JOptionPane.showMessageDialog(null, "治疗仪未正常结束，请手动终止！", "警告", JOptionPane.WARNING_MESSAGE);
													}
													utils.get(myFlag).getrChannel().setImg_flag("07");
													if(myFlag == flag){
														pic2.repaint();
														b7.setEnabled(false);
														b4.setEnabled(false);
														b5.setEnabled(false);
														b6.setEnabled(true);
														jrb3.setEnabled(true);
														jrb4.setEnabled(true);
														s2.setValue(0);
														s3.setValue(0);
														box1.setEnabled(true);
													}	
													utils.get(myFlag).getrChannel().setPower(0);
													utils.get(myFlag).getrChannel().setTemp(0);
													rStop.put(myFlag,false);
														
													leds.set(myFlag*2+1, 1);
													
													utils.get(myFlag).getrChannel().setSwitch_flag(true);
													List<Bottom> bottoms = p12.getBottoms();
													for (Bottom bottom : bottoms) {
														bottom.repaint();
													}
													
													utils.get(myFlag).getrChannel().getClock().changeLabel(timeMap.get(utils.get(myFlag).getrChannel().getSelect()));
													
													EditorPanel.Arrived = false;
													EditorPanel.RVO = 0;
													return;
												}
											}
											utils.get(myFlag).getrChannel().setImg_flag("07");
											if(myFlag == flag){
												pic2.repaint();
												
												b7.setEnabled(false);
												b4.setEnabled(false);
												b5.setEnabled(false);
												b6.setEnabled(true);
												jrb3.setEnabled(true);
												jrb4.setEnabled(true);
												s2.setValue(0);
												s3.setValue(0);
												box1.setEnabled(true);
											}	
											
											utils.get(myFlag).getrChannel().setPower(0);
											utils.get(myFlag).getrChannel().setTemp(0);
											rStop.put(myFlag,false);
																					
											leds.set(myFlag*2+1, 1);
											
											utils.get(myFlag).getrChannel().setSwitch_flag(true);
											List<Bottom> bottoms = p12.getBottoms();
											for (Bottom bottom : bottoms) {
												bottom.repaint();
											}
											
											utils.get(myFlag).getrChannel().getClock().changeLabel(timeMap.get(utils.get(myFlag).getrChannel().getSelect()));
											
											JOptionPane.showMessageDialog(null, "治疗仪未响应，请手动终止！", "警告", JOptionPane.WARNING_MESSAGE);
											
										}
									}
								};
								mainThread2.start();
								rThreads.put(myFlag, mainThread2);
							}else {
								JOptionPane.showMessageDialog(null,
										"返回值错误，请重启操作！", "警告",
										JOptionPane.WARNING_MESSAGE);
							}
							EditorPanel.Arrived = false;
							EditorPanel.RVO = 0;
							return;
						}
					}
					JOptionPane.showMessageDialog(null, "治疗仪未响应，请重启操作！", "警告",
							JOptionPane.WARNING_MESSAGE);
					System.out.println("超时！");
					return;	
				}//end of 普通模式
			}//end of 专家处方模式			
			
		}
		else if(e.getSource()==b7){
			System.out.println("通道2停止工作");
			if(box1.getSelectedIndex()>=70){
//				b2.setEnabled(true);
//				if (jrb1.isSelected()) {
//					box.setEnabled(true);
//				}
//				jrb1.setEnabled(true);
//				jrb2.setEnabled(true);
				
				lThreads.get(flag).interrupt();
//				s0.setValue(0);
//				s1.setValue(0);
			}
//			if(jrb3.isSelected()){
//				box1.setEnabled(true);
//			}
//			jrb3.setEnabled(true);
//			jrb4.setEnabled(true);
//			s2.setValue(0);
//			s3.setValue(0);
//			leds.set(flag*2+1, 1);
//			b7.setEnabled(false);
//			b4.setEnabled(false);
//			b5.setEnabled(false);
//			b6.setEnabled(true);
//			utils.get(flag).getrChannel().setSwitch_flag(true);
//			List<Bottom> bottoms = p12.getBottoms();
//			for (Bottom bottom : bottoms) {
//				bottom.repaint();
//			}
			rThreads.get(flag).interrupt();
		}
		else if(e.getSource()==b8){
			if(s1.getValue()<10){
				int now = s1.getValue();
				now++;
				final int set = now;
				int[] send = new int[5];
				send[0] = 170;
				int sum=170;
				send[1] = (flag*2+1)*16+2;
				sum += (flag*2+1)*16+2;
				send[2] = now;
				sum += now;
				send[3] = 1;
				sum += 1;
				send[4] = sum;
				
				EditorPanel.Arrived = false;
				EditorPanel.RVO = 0;
				
				CommWrite.read(serialPort);
				CommWrite.write(send, serialPort);
				
				
				final int jy = sum;
				final long startTime = System.currentTimeMillis();
				
				Thread zj1 = new Thread(){
					@Override
					public void run() {
						while (System.currentTimeMillis()-startTime<10000) {
							if(EditorPanel.Arrived) {
								if(EditorPanel.RVO==jy%256){
									s1.setValue(set);									
								}
								else {
									System.out.println(EditorPanel.Arrived);
//									System.out.println(EditorPanel.RVO);
									JOptionPane.showMessageDialog(null, "返回值错误，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
								}
								EditorPanel.Arrived = false;
								EditorPanel.RVO = 0;
								return;
							}					
						}
						JOptionPane.showMessageDialog(null, "治疗仪未响应，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
						System.out.println("超时！");
					}
				};
				zj1.start();
			}
			
		}
		else if(e.getSource()==b9){
			if(s1.getValue()>0){
				int now = s1.getValue();
				now--;
				final int set = now;
				int[] send = new int[5];
				send[0] = 170;
				int sum=170;
				send[1] = (flag*2+1)*16+2;
				sum += (flag*2+1)*16+2;
				send[2] = now;
				sum += now;
				send[3] = 1;
				sum += 1;
				send[4] = sum;
				
				EditorPanel.Arrived = false;
				EditorPanel.RVO = 0;
				
				CommWrite.read(serialPort);
				CommWrite.write(send, serialPort);
				
				final int jy = sum;
				final long startTime = System.currentTimeMillis();
				Thread jx1 = new Thread(){
					@Override
					public void run() {
						while (System.currentTimeMillis()-startTime<10000) {
							if(EditorPanel.Arrived) {
								if(EditorPanel.RVO==jy%256){
									s1.setValue(set);									
								}
								else {
									JOptionPane.showMessageDialog(null, "返回值错误，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
								}
								EditorPanel.Arrived = false;
								EditorPanel.RVO = 0;
								return;
							}					
						}
						JOptionPane.showMessageDialog(null, "治疗仪未响应，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
						System.out.println("超时！");
					}
				};
				jx1.start();
			}
		}
		else if(e.getSource()==b10){
			if(s3.getValue()<10){
				int now = s3.getValue();
				now++;
				final int set = now;
				int[] send = new int[5];
				send[0] = 170;
				int sum=170;
				send[1] = (flag*2+2)*16+2;
				sum += (flag*2+2)*16+2;
				send[2] = now;
				sum += now;
				send[3] = 1;
				sum += 1;
				send[4] = sum;
				
				EditorPanel.Arrived = false;
				EditorPanel.RVO = 0;
				
				CommWrite.read(serialPort);
				CommWrite.write(send, serialPort);
				
				final int jy = sum;
				final long startTime = System.currentTimeMillis();
				Thread zj2 = new Thread(){
					@Override
					public void run() {
						while (System.currentTimeMillis()-startTime<10000) {
							if(EditorPanel.Arrived) {
								if(EditorPanel.RVO==jy%256){
									s3.setValue(set);									
								}
								else {
									JOptionPane.showMessageDialog(null, "返回值错误，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
								}
								EditorPanel.Arrived = false;
								EditorPanel.RVO = 0;
								return;
							}					
						}
						JOptionPane.showMessageDialog(null, "治疗仪未响应，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
						System.out.println("超时！");
					}
				};
				zj2.start();
			}
			
		}
		else if(e.getSource()==b11){
			if(s3.getValue()>0){
				int now = s3.getValue();
				now--;
				final int set = now;
				int[] send = new int[5];
				send[0] = 170;
				int sum=170;
				send[1] = (flag*2+2)*16+2;
				sum += (flag*2+2)*16+2;
				send[2] = now;
				sum += now;
				send[3] = 1;
				sum += 1;
				send[4] = sum;
				
				EditorPanel.Arrived = false;
				EditorPanel.RVO = 0;
				
				CommWrite.read(serialPort);
				CommWrite.write(send, serialPort);
				
				final int jy = sum;
				final long startTime = System.currentTimeMillis();
				Thread jx2 = new Thread(){
					@Override
					public void run() {
						while (System.currentTimeMillis()-startTime<10000) {
							if(EditorPanel.Arrived) {
								if(EditorPanel.RVO==jy%256){
									s3.setValue(set);									
								}
								else {
									System.out.println(EditorPanel.Arrived);
									System.out.println(EditorPanel.RVO);
									JOptionPane.showMessageDialog(null, "返回值错误，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
								}
								EditorPanel.Arrived = false;
								EditorPanel.RVO = 0;
								return;
							}					
						}
						JOptionPane.showMessageDialog(null, "治疗仪未响应，请重启操作！", "警告", JOptionPane.WARNING_MESSAGE);
						System.out.println("超时！");
					}
				};
				jx2.start();
			}
		}
	}

}
