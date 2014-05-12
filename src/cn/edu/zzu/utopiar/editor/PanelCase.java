package cn.edu.zzu.utopiar.editor;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import cn.edu.zzu.entity.Patient;
import cn.edu.zzu.entity.SwingUtils;
import cn.edu.zzu.utopiar.dao.Db;

public class PanelCase extends JFrame implements ActionListener{
	
	JLabel l0 = null;
	JLabel l1 = null;
	JLabel l2 = null;
	JLabel l3 = null;
	JLabel l4 = null;
	JLabel l5 = null;
	JLabel l6 = null;
	JLabel l7 = null;
	JLabel l8 = null;
	
	JTextField tf1 = null;
	JTextField tf2 = null;
	JTextField tf3 = null;
	JTextField tf4 = null;
	JTextField tf5 = null;
	JTextField tf6 = null;
	JTextField tf7 = null;
	
	JTextArea ta = null;

	JButton b0 = null;
	JButton b1 = null;
	JButton b2 = null;
	JButton b3 = null;
	JButton b4 = null;
	
	EditorPanel owner = null;
	
	public PanelCase(EditorPanel owner) {
		// TODO Auto-generated constructor stub
		if(owner!=null){
//			owner.setVisible(false);
			this.owner = owner;
		}		
		Toolkit t=getToolkit();
		Dimension d=t.getScreenSize();
		setBounds(d.width/6,d.height/6,850,680);
		setResizable(false);
		JPanel panel = new JPanel();
		this.add(panel);
		panel.setLayout(null);
		//---------------------------------
		l0 = new JLabel("病例管理");
		l0.setFont(new java.awt.Font("微软雅黑", 0, 24));
		l0.setBounds(350, 10, 200, 50);
		panel.add(l0);
		//----------------------------------
		
		l1 = new JLabel("病历号:");
		l1.setFont(new java.awt.Font("微软雅黑", 0, 14));
		l1.setBounds(40, 80, 50, 30);
		panel.add(l1);
		tf1 = new JTextField();
		tf1.setBounds(130,80,80,30);
		panel.add(tf1);
		
		l2 = new JLabel("姓名:");
		l2.setFont(new java.awt.Font("微软雅黑", 0, 14));
		l2.setBounds(250, 80, 50, 30);
		panel.add(l2);
		tf2 = new JTextField();
		tf2.setBounds(310,80,80,30);
		panel.add(tf2);
		
		l3 = new JLabel("性别:");
		l3.setFont(new java.awt.Font("微软雅黑", 0, 14));
		l3.setBounds(430, 80, 50, 30);
		panel.add(l3);
		tf3 = new JTextField();
		tf3.setBounds(490,80,80,30);
		panel.add(tf3);
		
		l4 = new JLabel("年龄:");
		l4.setFont(new java.awt.Font("微软雅黑", 0, 14));
		l4.setBounds(610, 80, 50, 30);
		panel.add(l4);
		tf4 = new JTextField();
		tf4.setBounds(670,80,80,30);
		panel.add(tf4);
		//---------------------------------
		l5 = new JLabel("职业:");
		l5.setFont(new java.awt.Font("微软雅黑", 0, 14));
		l5.setBounds(40, 130, 50, 30);
		panel.add(l5);
		tf5 = new JTextField();
		tf5.setBounds(130,130,80,30);
		panel.add(tf5);
		
		l6 = new JLabel("电话:");
		l6.setFont(new java.awt.Font("微软雅黑", 0, 14));
		l6.setBounds(250, 130, 50, 30);
		panel.add(l6);
		tf6 = new JTextField();
		tf6.setBounds(310,130,260,30);
		panel.add(tf6);
		
		l7 = new JLabel("处方:");
		l7.setFont(new java.awt.Font("微软雅黑", 0, 14));
		l7.setBounds(610, 130, 50, 30);
		panel.add(l7);
		tf7 = new JTextField();
		tf7.setBounds(670,130,80,30);
		panel.add(tf7);
		//-----------------------------------
		
		l8 = new JLabel("病情描述：");
		l8.setFont(new java.awt.Font("微软雅黑", 0, 14));
		l8.setBounds(40, 180, 80, 30);
		panel.add(l8);
		ta = new JTextArea();
		ta.setBounds(40, 230, 710, 300);
		panel.add(ta);
		//----------------------------------
		
		b0 = new JButton("查找");
		b0.setBounds(310, 550, 80, 30);
		panel.add(b0);
		
		b1 = new JButton("保存");
		b1.setBounds(400, 550, 80, 30);
		panel.add(b1);
		
		b2 = new JButton("打印");
		b2.setBounds(490, 550, 80, 30);
		panel.add(b2);
		
		b3 = new JButton("进入治疗");
		b3.setBounds(580, 550, 80, 30);
		panel.add(b3);
		
		b4 = new JButton("返回主页");
		b4.setBounds(670, 550, 80, 30);
		panel.add(b4);
		
		//----------------------------------
		b0.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		//----------------------------------
		
//		setVisible(true);
	}
	
	
	
	public static void main(String[] args){
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	        UIManager.put("RootPane.setupButtonVisible", false);
	        for (int i = 0; i < SwingUtils.DEFAULT_FONT.length; i++)
	            UIManager.put(SwingUtils.DEFAULT_FONT[i],new Font("微软雅黑", Font.PLAIN,14));
		} catch (Exception e) {
			// TODO: handle exception
		}
		new PanelCase(null);
	}

	class PrintPanel extends JPanel {

		public PrintPanel(final Patient pa) {
			try {
				PrinterJob pjob = PrinterJob.getPrinterJob();
				pjob.setJobName("Graphics Demo Printout");
				pjob.setCopies(1);
				pjob.setPrintable(new Printable() {
					public int print(Graphics pg, PageFormat pf, int pageNum) {
						if (pageNum > 0) // we only print one page
							return Printable.NO_SUCH_PAGE; // ie., end of job
						pg.setClip(0, 0, 1200, 2400);
						pg.setFont(new Font("微软雅黑", 0, 36));
						pg.drawString("病例报告", 550, 150);					
						pg.setFont(new Font("微软雅黑", 0, 24));
						pg.drawString("病例号："+pa.getId(), 100, 230);
						pg.drawString(pa.getRecipe(), 300, 230);
						pg.drawLine(100, 250, 1100, 250);
						pg.drawString("姓名："+pa.getName(), 100, 300);
						pg.drawString("性别："+pa.getSex(), 350, 300);
						pg.drawString("年龄："+pa.getAge(), 500, 300);
						pg.drawString("职业："+pa.getCareer(), 650, 300);
						pg.drawString("电话："+pa.getPhone(), 850, 300);
						pg.setFont(new Font("微软雅黑", 0, 30));
						pg.drawString("病情描述：", 100, 400);
						pg.setFont(new Font("微软雅黑", 0, 24));
						String str = pa.getDescribe();
						if(str.length()>=40)
							System.out.println(str.substring(0, 39));
						int i=0;
						while ((str.length()-i*40)>=40) {
							System.out.println(str);
							pg.drawString(str.substring(0+i*40, (i+1)*40), 100, 500+i*50);
							i++;
						}
						pg.drawString(str.substring(0+i*40, str.length()), 100, 500+i*50);
						return Printable.PAGE_EXISTS;
					}
				});

				if (pjob.printDialog() == false) // choose printer
					return;
				pjob.print();
			} catch (PrinterException pe) {
				pe.printStackTrace();
			}
		}

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b0){
			if(!tf1.getText().matches("^([1-9][0-9]*)$")){
				System.out.println("错误");
				JOptionPane.showMessageDialog(null, "请输入正确的病历号(数字)进行查找", "警告", JOptionPane.WARNING_MESSAGE);
				return;
			}else {
				System.out.println("正确");
				Patient patient = Db.serch(tf1.getText());
				if(patient == null){
					JOptionPane.showMessageDialog(null, "没有该病历号的记录，请重新输入病历号搜索", "警告", JOptionPane.WARNING_MESSAGE);
				}else{
					tf2.setText(patient.getName());
					tf3.setText(patient.getSex());
					tf4.setText(patient.getAge());
					tf5.setText(patient.getCareer());
					tf6.setText(patient.getPhone());
					tf7.setText(patient.getRecipe());
					ta.setText(patient.getDescribe());
				}
				
			}
		}
		else if(e.getSource()==b1){
			if(!tf1.getText().matches("^([1-9][0-9]*)$")){
				System.out.println("错误");
				JOptionPane.showMessageDialog(null, "请输入正确的病历号(数字)", "警告", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(!tf4.getText().matches("^([1-9][0-9]*)$")){
				System.out.println("错误");
				JOptionPane.showMessageDialog(null, "请输入正确的年龄(数字)", "警告", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(!tf6.getText().matches("^([1-9][0-9]*)$")){
				System.out.println("错误");
				JOptionPane.showMessageDialog(null, "请输入正确的电话(数字)", "警告", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(!tf7.getText().matches("^([1-9][0-9]*)$")){
				System.out.println("错误");
				JOptionPane.showMessageDialog(null, "请输入正确的处方(数字)", "警告", JOptionPane.WARNING_MESSAGE);
				return;
			}
			Patient patient = new Patient();
			patient.setId(tf1.getText());
			patient.setName(tf2.getText());
			patient.setSex(tf3.getText());
			patient.setAge(tf4.getText());
			patient.setCareer(tf5.getText());
			patient.setPhone(tf6.getText());
			patient.setRecipe(tf7.getText());
			patient.setDescribe(ta.getText());
			Db.update(patient);
		}else if(e.getSource()==b2){
			System.out.println("开始打印");
			Patient pa = new Patient();
			pa.setId(tf1.getText());
			pa.setName(tf2.getText());
			pa.setSex(tf3.getText());
			pa.setAge(tf4.getText());
			pa.setCareer(tf5.getText());
			pa.setPhone(tf6.getText());
			pa.setRecipe(tf7.getText());
			pa.setDescribe(ta.getText());
			new PrintPanel(pa);
		}else if(e.getSource()==b3){
			System.out.println("开始治疗");
			
			if(owner!=null){
				owner.setVisible(true);
				owner.editorMain.setVisible(false);
			}
			setVisible(false);
		}else if(e.getSource()==b4){
			this.setVisible(false);			
			owner.setVisible(false);
			this.owner.editorMain.setVisible(true);
			
		}
	}
}
