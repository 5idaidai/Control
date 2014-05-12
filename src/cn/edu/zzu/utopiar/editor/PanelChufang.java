package cn.edu.zzu.utopiar.editor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import cn.edu.zzu.entity.SwingUtils;
import cn.edu.zzu.entity.Wave;

public class PanelChufang extends JFrame implements ActionListener{
	
	EditorPanel owner = null;
	int flag = 0;
	static String[] du = {"基波","25%","50%","75%"};
	static JComboBox[] comboBoxs = new JComboBox[9];
	static JTextField[] textFields = new JTextField[9];
	static String[] names = new String[]{"无","波形一","波形二","波形三","波形四","波形五","波形六","波形七","波形八","波形九"};
	static Layer[] layers = new Layer[9];
	static JLabel[] pindu = new JLabel[9];
	static JLabel[] pinlv = new JLabel[9];
//	static JCheckBox[] select = new JCheckBox[9];
	static JLabel[] xuanze = new JLabel[]{new JLabel("A段"),new JLabel("B段"),new JLabel("C段"),
		new JLabel("D段"),new JLabel("E段"),new JLabel("F段"),new JLabel("G段"),new JLabel("H段"),new JLabel("I段")};
	static JComboBox[] boduan = new JComboBox[9];
	
	JLabel shijian = new JLabel("治疗时间（分钟）:");
	JComboBox sjBox = new JComboBox(new String[]{"5","10","15","20","25","30"});
	JLabel dangwei = new JLabel("加热档位:");
	JComboBox dwBox = new JComboBox(new String[]{"1","2","3","4","5","6","7","8","9"});
	JLabel zxPinlv = new JLabel("中心频率:");
	JComboBox plBox = new JComboBox(new String[]{"2","3","4","5","6","7","8"});
	JButton begin = new JButton("进入治疗");
	JButton exit = new JButton("放弃");
	int[] head;
	
	public static HashMap<Integer, HashMap<Integer, Integer>> hashMap;
	public static HashMap<Integer, Integer> index;
	
	List<int []>waveList;
	List<Wave> wavePics;
	
	static{
		hashMap = new HashMap<Integer, HashMap<Integer,Integer>>();
		index = new HashMap<Integer, Integer>();
		index.put(0, 7);
		index.put(1, 6);
		index.put(2, 4);
		index.put(3, 2);
		hashMap.put(0, index);
		
		index = new HashMap<Integer, Integer>();
		index.put(0, 12);
		index.put(1, 13);
		index.put(2, 14);
		index.put(3, 15);
		hashMap.put(1, index);
		
		index = new HashMap<Integer, Integer>();
		index.put(0, 8);
		index.put(1, 9);
		index.put(2, 10);
		index.put(3, 11);
		hashMap.put(2, index);
		
		index = new HashMap<Integer, Integer>();
		index.put(0, 20);
		index.put(1, 21);
		index.put(2, 22);
		index.put(3, 23);
		hashMap.put(3, index);
		
		index = new HashMap<Integer, Integer>();
		index.put(0, 16);
		index.put(1, 17);
		index.put(2, 18);
		index.put(3, 19);
		hashMap.put(4, index);
		
		index = new HashMap<Integer, Integer>();
		index.put(0, 24);
		index.put(1, 25);
		index.put(2, 26);
		index.put(3, 27);
		hashMap.put(5, index);
		
		index = new HashMap<Integer, Integer>();
		index.put(0, 28);
		index.put(1, 29);
		index.put(2, 30);
		index.put(3, 31);
		hashMap.put(6, index);
		
		index = new HashMap<Integer, Integer>();
		index.put(0, 32);
		index.put(1, 33);
		index.put(2, 34);
		index.put(3, 35);
		hashMap.put(7, index);
		
		index = new HashMap<Integer, Integer>();
		index.put(0, 36);
		index.put(1, 37);
		index.put(2, 38);
		index.put(3, 39);
		hashMap.put(8, index);
	}
	
	static{
		
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	        UIManager.put("RootPane.setupButtonVisible", false);
			//windows风格
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			//motif风格
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			for (int i = 0; i < SwingUtils.DEFAULT_FONT.length; i++)
	            UIManager.put(SwingUtils.DEFAULT_FONT[i],new Font("微软雅黑", Font.PLAIN,14));
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i=0;i<9;i++){
			final JComboBox com;
			final JTextField textField;
			final Layer layer;
			comboBoxs[i] = new JComboBox(du);
			com = comboBoxs[i];
			textFields[i] = new JTextField();
			textField = textFields[i];
			layers[i] = new Layer(i);
			layer = layers[i];
			pindu[i] = new JLabel("调制度");
			pinlv[i] = new JLabel("调制频率Hz");
//			select[i] = new JCheckBox();
			
			com.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					
					layer.changeFlag(com.getSelectedIndex());
					layer.getPic().repaint();
				}
			});
			
			textField.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					String str = textField.getText();
					if(str.equalsIgnoreCase("")){
						return;
					}
					if(!str.matches("^([1-9][0-9]*)$")){
						System.out.println("错误");
						textField.setText("");
						JOptionPane.showMessageDialog(null, "请输入正确的频率(数字)", "警告", JOptionPane.WARNING_MESSAGE);
						return;
					}else {
						if (Integer.parseInt(str)>=1&&Integer.parseInt(str)<=150) {
							return;
						}else {
							System.out.println("输入值区间错误");
							textField.setText("");
							JOptionPane.showMessageDialog(null, "请输入正确的频率(1~150)", "警告", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
				};
				
				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		for(int i=0;i<9;i++){
			final JComboBox bd;
			boduan[i] = new JComboBox(names);
			bd = boduan[i];
			
			bd.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
					if (e.getStateChange() == ItemEvent.SELECTED){
						int id = bd.getSelectedIndex();
						if(id!=0&&textFields[id-1].getText().equalsIgnoreCase("")){
							JOptionPane.showMessageDialog(null, "请先对"+bd.getSelectedItem()+"进行设置", "警告", JOptionPane.WARNING_MESSAGE);
							bd.setSelectedIndex(0);
						}
					}
				}
			});
		}
	}
	
	public boolean check(String str){
		if(!str.matches("^([1-9][0-9]*)$")){
			System.out.println("错误");
			JOptionPane.showMessageDialog(null, "请输入正确的频率(数字)", "警告", JOptionPane.WARNING_MESSAGE);
			return false;
		}else {
			if (Integer.parseInt(str)>=1&&Integer.parseInt(str)<=150) {
				return true;
			}else {
				System.out.println("输入值区间错误");
				JOptionPane.showMessageDialog(null, "请输入正确的频率(1~150)", "警告", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
	}
	
	private JPanel addWave(int x, int y ,int index) {
		JPanel wave = new JPanel();
		wave.setBorder(BorderFactory.createTitledBorder(names[index+1]));
		wave.setBounds(x, y, 280, 200);
		wave.setLayout(null);
//		select[index].setBounds(200, 20, 60, 20);
		textFields[index].setBounds(200, 100, 70, 20);
		comboBoxs[index].setBounds(200, 160, 70, 20);
		pindu[index].setBounds(200, 140, 70, 20);
		pinlv[index].setBounds(200, 80, 80, 20);
		layers[index].setBounds(10, 20, 180, 160);
//		wave.add(select[index]);
		wave.add(pindu[index]);
		wave.add(pinlv[index]);
		wave.add(layers[index]);
		wave.add(textFields[index]);
		wave.add(comboBoxs[index]);
		return wave;
	}
	
	public static String getHex(int id){
		String hex = Integer.toHexString(id);
		if(hex.length()==1){
			hex = "0"+hex;
		}
		return hex;
	}
	
	public PanelChufang(int[] head, List<int []>waveList, List<Wave>wavePics, final int flag){
//		if(owner!=null){
////			owner.setVisible(false);
//			this.owner = owner;
//		}
		
		this.addWindowListener(new WindowAdapter() {		
			public void windowClosing(WindowEvent e){
				if (flag==0) {
					EditorPanel.jrb1.setSelected(true);
					EditorPanel.box.setEnabled(true);
				}else {
					EditorPanel.jrb3.setSelected(true);
					EditorPanel.box1.setEnabled(true);
				}
				setVisible(false);
			}		
		});
		
		this.flag = flag;
		this.head = head;
		this.waveList = waveList;
		this.wavePics = wavePics;
		wavePics.clear();
		head[0]=187;
		setBounds(300,20,1140,820);
		setResizable(false);
		setVisible(true);
		//---------------------------------
		JPanel p0 = new JPanel();		
		p0.setLayout(null);
		p0.setBounds(0, 0, 980, 60);
		JLabel l0 = new JLabel("自编处方设计");
		l0.setFont(new java.awt.Font("微软雅黑", 0, 32));
		l0.setBounds(500, 5, 500, 60);
		p0.add(l0);
		add(p0);
		//----------------------------------
		
		JPanel p1 = new JPanel();
		p1.setBorder(BorderFactory.createTitledBorder("波形设计"));
		p1.setLayout(null);
		p1.setBounds(10, 70, 900, 650);
		int index=0;
		for(int i=0; i<3;i++){
			
			for(int j=0;j<3;j++){
				p1.add(addWave(20+290*j,20+210*i,index));
				index++;
			}
		}
		add(p1);
		
		JPanel p3 = new JPanel();
		p3.setBounds(10, 730, 1100, 50);
		p3.setLayout(null);
		shijian.setBounds(10, 10, 120, 20);
		p3.add(shijian);
		
		sjBox.setBounds(140, 10, 60, 20);
		p3.add(sjBox);
		dangwei.setBounds(210, 10, 60, 20);
		p3.add(dangwei);
		dwBox.setBounds(280, 10, 60, 20);
		p3.add(dwBox);
		zxPinlv.setBounds(350, 10, 60, 20);
		p3.add(zxPinlv);
		plBox.setBounds(420, 10, 60, 20);
		p3.add(plBox);
		begin.setBounds(750, 5, 100, 30);
		p3.add(begin);
		exit.setBounds(950, 5, 80, 30);
		p3.add(exit);
		begin.addActionListener(this);
		exit.addActionListener(this);
		add(p3);
		
		JPanel p2 = new JPanel();
		p2.setBorder(BorderFactory.createTitledBorder("波形选择"));
		p2.setBounds(920, 70, 200, 650);
		p2.setLayout(null);
		for(int i=0;i<9;i++){
			xuanze[i].setBounds(30, 60+i*60, 80, 20);
			boduan[i].setBounds(80, 60+i*60, 80, 20);
			p2.add(boduan[i]);
			p2.add(xuanze[i]);
		}
		add(p2);
		
		 
		JPanel p = new JPanel();
		p.setBounds(0, 790, 1150, 10);
		add(p);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==begin){
			
			final List<Integer> ids = new ArrayList<Integer>();
			final List<Integer> indexs = new ArrayList<Integer>();
			ids.clear();
			indexs.clear();
			List<int []>waves = new ArrayList<int[]>();
			List<Wave> wavePicList = new ArrayList<Wave>();
			for (int i = 0; i < 9; i++) {
				int id = boduan[i].getSelectedIndex();
				if (id != 0) {
					id--;
					ids.add(id);
					System.out.println("ID:"+id);
					indexs.add(comboBoxs[id].getSelectedIndex());
				}
			}
			
			if(ids.size()==0){
				JOptionPane.showMessageDialog(null, "请先选择波形", "警告", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if(ids.size()%2!=0){
				ids.add(ids.get(ids.size()-1));
				indexs.add(indexs.get(indexs.size()-1));
			}
			
//			int[] wave = new int[6];
//			Wave wavePic = new Wave();
			int j = 0;
			waves.clear();
			wavePicList.clear();
			for (int i = 0; i < ids.size(); i += 2) {
//				wave = new int[6];
//				wavePic = new Wave();				
				waves.add(new int[6]);				
				wavePicList.add(new Wave());
				System.out.println("i:"+i);
				System.out.println("ids.size()"+ids.size());								
				waves.get(j)[0] = 85;
				wavePicList.get(j).setTime1(5);
				wavePicList.get(j).setTime2(5);
				waves.get(j)[1] = hashMap.get(ids.get(i)).get(indexs.get(i));
				System.out.println(getHex(waves.get(j)[1]));
				wavePicList.get(j).setName1(getHex(waves.get(j)[1]));
				waves.get(j)[2] = hashMap.get(ids.get(i + 1)).get(indexs.get(i + 1));
				System.out.println(getHex(waves.get(j)[2]));
				wavePicList.get(j).setName2(getHex(waves.get(j)[2]));
				waves.get(j)[3] = Integer.parseInt(textFields[ids.get(i)].getText());
				waves.get(j)[4] = Integer.parseInt(textFields[ids.get(i + 1)].getText());
				waves.get(j)[5] = 60;
				wavePicList.get(j).setTime(60);
				j++;
			}
			
			for(int[] in : waves){
				for(int i=0;i<in.length;i++){
					System.out.print(in[i]+"  ");
					
				}
				System.out.println();	
			}
			
			int time = (sjBox.getSelectedIndex()+1)*5;
			if(flag==0){
				EditorPanel.utils.get(EditorPanel.flag).getlChannel().setCustom_time(time);
				EditorPanel.utils.get(EditorPanel.flag).getlChannel().getClock().changeLabel(String.valueOf(time));
			}else {
				EditorPanel.utils.get(EditorPanel.flag).getrChannel().setCustom_time(time);
				EditorPanel.utils.get(EditorPanel.flag).getrChannel().getClock().changeLabel(String.valueOf(time));
			}
			
			waveList.clear();
			wavePics.clear();
			for(int i = 0;i<time/waves.size();i++){
				waveList.addAll(waves);
				wavePics.addAll(wavePicList);
			}
			for (int i = 0; i <time%waves.size() ; i++) {
				waveList.add(waves.get(i));
				wavePics.add(wavePicList.get(i));
			}
			int sizeOfByte = 4+time*6;
			head[1] = sizeOfByte;
			head[3] = plBox.getSelectedIndex()+2;
//			CommWrite.write(head, "COM3");
//			
//			for (int[] q : waveList) {
//				for (int i : q) {
//					System.out.print(i+" ");
//				}
//				System.out.println();
//				CommWrite.write(q, "COM3");
//			}
			
			if (flag == 0) {
				EditorPanel.s1.setValue(dwBox.getSelectedIndex()+1);
				
			}else {
				EditorPanel.s3.setValue(dwBox.getSelectedIndex()+1);
			}
			
			this.setVisible(false);
			
		}else if(e.getSource()==exit){
			System.out.println("退出");
			if (flag==0) {
				EditorPanel.jrb1.setSelected(true);
				EditorPanel.box.setEnabled(true);
			}else {
				EditorPanel.jrb3.setSelected(true);
				EditorPanel.box1.setEnabled(true);
			}
			this.setVisible(false);
		}
	}

	public static void main(String[] args){
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	        UIManager.put("RootPane.setupButtonVisible", false);
			//windows风格
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			//motif风格
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			for (int i = 0; i < SwingUtils.DEFAULT_FONT.length; i++)
	            UIManager.put(SwingUtils.DEFAULT_FONT[i],new Font("微软雅黑", Font.PLAIN,14));
		} catch (Exception e) {
			e.printStackTrace();
		}
		new PanelChufang(new int[4], new ArrayList<int []>(), new ArrayList<Wave>(), 0);
	}
	
}
