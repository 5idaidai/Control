package cn.edu.zzu.utopiar.editor;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import cn.edu.zzu.entity.IButton;
import cn.edu.zzu.entity.SwingUtils;

public class EditorMain extends JFrame implements ActionListener{

	ImageIcon bg;
	IButton zhiliao;
	IButton dangan;
	IButton jianjie;
	IButton lianxi;
	
	EditorPanel editorPanel;
	EditorInfo editorJianJie;
	EditorInfo editorLianXi;
	PanelCase panelCase;
	
	public EditorMain() {
		
		editorPanel = new EditorPanel(this);
		panelCase = new PanelCase(editorPanel);
		editorJianJie = new EditorInfo("img/公司简介.png", this);
		editorLianXi = new EditorInfo("img/联系方式.png", this);

		bg = new ImageIcon("img/启动界面.png");
		
		JPanel panel = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				g.drawImage(bg.getImage(), 0, 0, null);
			}
		};
		panel.setLayout(null);
		this.add(panel);
		
		//添加按钮
		zhiliao = new IButton("img/button_normal.png", "img/button_over.png", "img/button_pressed.png");
		zhiliao.setBounds(200, 525, 255, 187);
		zhiliao.addActionListener(this);
		panel.add(zhiliao);
		
		dangan = new IButton("img/button_normal.png", "img/button_over.png", "img/button_pressed.png");
		dangan.setBounds(512, 525, 255, 187);
		dangan.addActionListener(this);
		panel.add(dangan);
		
		jianjie = new IButton("img/button_normal.png", "img/button_over.png", "img/button_pressed.png");
		jianjie.setBounds(827, 525, 255, 187);
		jianjie.addActionListener(this);
		panel.add(jianjie);
		
		lianxi = new IButton("img/button_normal.png", "img/button_over.png", "img/button_pressed.png");
		lianxi.setBounds(1145, 525, 255, 187);
		lianxi.addActionListener(this);
		panel.add(lianxi);
		
		//隐藏标题栏
//		this.setUndecorated(true);
//		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 1600, 900);
		this.setVisible(true);
	}
	
	public static void main(String[] args){
//		Date date = new Date();
//		if (date.getYear()>114) {
//			return;
//		}
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	        UIManager.put("RootPane.setupButtonVisible", false);
	        
		} catch (Exception e) {
			// TODO: handle exception
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
		new EditorMain();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == zhiliao) {
			this.editorPanel.setVisible(true);
			this.setVisible(false);
		}else if (e.getSource() == dangan) {
			this.panelCase.setVisible(true);		
		}else if (e.getSource() == jianjie) {
			this.editorJianJie.setVisible(true);
			this.setVisible(false);
		}else if (e.getSource() == lianxi) {
			this.editorLianXi.setVisible(true);
			this.setVisible(false);
		}
	}
}
