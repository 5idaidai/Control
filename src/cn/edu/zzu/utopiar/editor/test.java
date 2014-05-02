package cn.edu.zzu.utopiar.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.omg.PortableServer.POAPackage.AdapterAlreadyExists;

public class test extends JFrame implements ActionListener{
	
	// 加载背景图片
	 ImageIcon bg1 = new ImageIcon("img/bg1.png"); 
	// 把背景图片显示在一个标签里
	 JLabel label1 = new JLabel(bg1){
		 protected void paintComponent(java.awt.Graphics g) {
			 
		 };
		 
		 public void paint(java.awt.Graphics g) {
			 g.drawImage(bg1.getImage(), 0, 0, null);
		 };
	 };
	 //把标签的大小位置设置为图片刚好填充整个面
	 JButton button;
	 

	public test() {
		// TODO Auto-generated constructor stub
		// 加载背景图片
		 ImageIcon bg = new ImageIcon("img/bg.png"); 
		// 把背景图片显示在一个标签里
		 JLabel label = new JLabel(bg);
		 //把标签的大小位置设置为图片刚好填充整个面
		 label.setBounds(0,0,bg.getIconWidth(),bg.getIconHeight());
		 //添加图片到frame的第二
		 this.getLayeredPane().add(label1,new Integer(Integer.MIN_VALUE));
		 //获取frame的最上层面板为了设置其背景颜色（JPanel有设置透明的方法）
		 JPanel jp=(JPanel)this.getContentPane(); jp.setOpaque(false);
		//设置透明  
		 //测试用的
		JPanel panel=new JPanel();    
		panel.setOpaque(false);
		//也要让他透明  
		 panel.setLayout(null);
		//为了使用按钮的定位   
		 button=new JButton("OK");  
		 button.addActionListener(this);
		 button.setSize(300, 300); 
		  button.setLocation(100, 50);  
		 panel.add(button);  
		 this.add(panel);
		 this.setVisible(true);
	}
	
	public static void main(String[] args){
		new test();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button)
		// TODO Auto-generated method stub
		label1.setBounds(0,0,bg1.getIconWidth(),bg1.getIconHeight());
		this.getLayeredPane().add(label1);
	}
	
}
