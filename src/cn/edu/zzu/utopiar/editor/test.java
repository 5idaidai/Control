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
	
	// ���ر���ͼƬ
	 ImageIcon bg1 = new ImageIcon("img/bg1.png"); 
	// �ѱ���ͼƬ��ʾ��һ����ǩ��
	 JLabel label1 = new JLabel(bg1){
		 protected void paintComponent(java.awt.Graphics g) {
			 
		 };
		 
		 public void paint(java.awt.Graphics g) {
			 g.drawImage(bg1.getImage(), 0, 0, null);
		 };
	 };
	 //�ѱ�ǩ�Ĵ�Сλ������ΪͼƬ�պ����������
	 JButton button;
	 

	public test() {
		// TODO Auto-generated constructor stub
		// ���ر���ͼƬ
		 ImageIcon bg = new ImageIcon("img/bg.png"); 
		// �ѱ���ͼƬ��ʾ��һ����ǩ��
		 JLabel label = new JLabel(bg);
		 //�ѱ�ǩ�Ĵ�Сλ������ΪͼƬ�պ����������
		 label.setBounds(0,0,bg.getIconWidth(),bg.getIconHeight());
		 //���ͼƬ��frame�ĵڶ�
		 this.getLayeredPane().add(label1,new Integer(Integer.MIN_VALUE));
		 //��ȡframe�����ϲ����Ϊ�������䱳����ɫ��JPanel������͸���ķ�����
		 JPanel jp=(JPanel)this.getContentPane(); jp.setOpaque(false);
		//����͸��  
		 //�����õ�
		JPanel panel=new JPanel();    
		panel.setOpaque(false);
		//ҲҪ����͸��  
		 panel.setLayout(null);
		//Ϊ��ʹ�ð�ť�Ķ�λ   
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
