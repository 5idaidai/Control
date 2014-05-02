package cn.edu.zzu.utopiar.editor;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import cn.edu.zzu.entity.IButton;

public class EditorInfo extends JFrame implements ActionListener{

	ImageIcon bg;
	IButton fh;
	EditorMain editorMain;
	
	public EditorInfo(String bgPath, EditorMain editorMain) {
		
		bg = new ImageIcon(bgPath);
		
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
		fh = new IButton("img/FH.png", "img/FH.png", "img/FH_pressed.png");
		fh.setBounds(1410, 106, 67, 73);
		fh.addActionListener(this);
		panel.add(fh);
		
		//隐藏标题栏
		this.setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		this.setBounds(0, 0, 1600, 900);
//		this.setVisible(true);
		if(editorMain!=null){
//			editorMain.setVisible(false);
			this.editorMain = editorMain;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		editorMain.setVisible(true);
		this.setVisible(false);
	}
	
	public static void main(String[] args){
		new EditorInfo("img/公司简介.png",null);
	}
	
}
