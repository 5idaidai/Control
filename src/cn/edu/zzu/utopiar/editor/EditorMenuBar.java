package cn.edu.zzu.utopiar.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.comm.CommPortIdentifier;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import cn.edu.zzu.utopiar.bean.Clock;
import cn.edu.zzu.utopiar.bean.CommWrite;

public class EditorMenuBar extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EditorPanel editor;

	ButtonGroup buttonGroup = new ButtonGroup();
	ButtonGroup buttonGroup1 = new ButtonGroup();

	public EditorMenuBar(final EditorPanel editor) {
		JMenu menu = null;
		this.editor = editor;
		this.setOpaque(true);
		// 创建处方管理菜单
//		menu = add(new JMenu("处方管理"));
//		JMenuItem item = menu.add(new JMenuItem("左通道处方管理"));
//		item.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//				editor.chufangManage(0);
//			}
//		});
//		item = menu.add(new JMenuItem("右通道处方管理"));
//		item.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//				editor.chufangManage(1);
//			}
//		});

		// 创建病例管理菜单
		menu = add(new JMenu("病例管理"));
		JMenuItem item = menu.add(new JMenuItem("病例管理"));
		item.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editor.caseManage();
			}
		});
		
		// 创建串口选择菜单
		Enumeration portList = CommPortIdentifier.getPortIdentifiers();
		List<String> comList = new ArrayList<String>();
		while (portList.hasMoreElements()) {
			CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL){
            	comList.add(portId.getName());
            }		
		}
		menu = add(new JMenu("串口选择"));
		JRadioButtonMenuItem com1 = new JRadioButtonMenuItem();
		com1.setSelected(true);
		for (String string : comList) {
			com1.setText(string);
			menu.add(com1);
			buttonGroup.add(com1);
			com1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// 选择串口
					EditorPanel.COM = ((JRadioButtonMenuItem)e.getSource()).getText();
					CommWrite.close(EditorPanel.serialPort);
					EditorPanel.serialPort = CommWrite.open(EditorPanel.COM);
				}
			});
			com1 = new JRadioButtonMenuItem(string);
		}
		

		// 创建单元控制菜单
		menu = add(new JMenu("通道控制"));
		JRadioButtonMenuItem dy1 = new JRadioButtonMenuItem("通道1/2");
		dy1.setSelected(true);
		dy1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("单元一");
				EditorPanel.flag = 0;
				EditorPanel.b0.setEnabled(!EditorPanel.utils.get(0).getlChannel().getSwitch_flag());
				EditorPanel.b1.setEnabled(!EditorPanel.utils.get(0).getlChannel().getSwitch_flag());
				EditorPanel.b2.setEnabled(EditorPanel.utils.get(0).getlChannel().getSwitch_flag());
				EditorPanel.b3.setEnabled(!EditorPanel.utils.get(0).getlChannel().getSwitch_flag());
				EditorPanel.b4.setEnabled(!EditorPanel.utils.get(0).getrChannel().getSwitch_flag());
				EditorPanel.b5.setEnabled(!EditorPanel.utils.get(0).getrChannel().getSwitch_flag());
				EditorPanel.b6.setEnabled(EditorPanel.utils.get(0).getrChannel().getSwitch_flag());
				EditorPanel.b7.setEnabled(!EditorPanel.utils.get(0).getrChannel().getSwitch_flag());
				if(!EditorPanel.utils.get(EditorPanel.flag ).getrChannel().getSwitch_flag()&&EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSelect()>=70){
					EditorPanel.b4.setEnabled(false);
					EditorPanel.b5.setEnabled(false);
					EditorPanel.b6.setEnabled(false);
					EditorPanel.b7.setEnabled(false);
				}
				
				EditorPanel.box.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSwitch_flag());
				EditorPanel.box1.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSwitch_flag());
				
				EditorPanel.jrb1.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSwitch_flag());
				EditorPanel.jrb2.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSwitch_flag());
				EditorPanel.jrb3.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSwitch_flag());
				EditorPanel.jrb4.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSwitch_flag());				
				
				EditorPanel.s0.setValue(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getPower());
				EditorPanel.s1.setValue(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getTemp());
				EditorPanel.s2.setValue(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getPower());
				EditorPanel.s3.setValue(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getTemp());
				
				EditorPanel.clock1 = EditorPanel.utils.get(EditorPanel.flag).getlChannel().getClock();
				EditorPanel.clock2 = EditorPanel.utils.get(EditorPanel.flag).getrChannel().getClock();
				EditorPanel.clock1.setBounds(180, 30, 100, 40);
				EditorPanel.clock1.setOpaque(false);
				EditorPanel.p2.remove(1);
				EditorPanel.p2.add(EditorPanel.clock1);
				EditorPanel.p2.updateUI();
				EditorPanel.clock2.setBounds(180, 30, 100, 40);
				EditorPanel.clock2.setOpaque(false);
				EditorPanel.p11.remove(1);
				EditorPanel.p11.add(EditorPanel.clock2);
				EditorPanel.p11.updateUI();
				
				EditorPanel.isChange = true;
				if(EditorPanel.utils.get(EditorPanel.flag).getlChannel().isCustom_flag()){
					EditorPanel.box.setEnabled(false);
					EditorPanel.jrb2.setSelected(true);
				}else if(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSwitch_flag()){
					EditorPanel.box.setEnabled(true);
					EditorPanel.jrb1.setSelected(true);
				}else{					
					EditorPanel.jrb1.setSelected(true);
					EditorPanel.box.setEnabled(false);
				}
				if(EditorPanel.utils.get(EditorPanel.flag).getrChannel().isCustom_flag()){
					EditorPanel.box1.setEnabled(false);
					EditorPanel.jrb4.setSelected(true);
				}else if(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSwitch_flag()){
					EditorPanel.box1.setEnabled(true);
					EditorPanel.jrb3.setSelected(true);
				}else{					
					EditorPanel.jrb3.setSelected(true);
					EditorPanel.box1.setEnabled(false);
				}
				EditorPanel.box.setSelectedIndex(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSelect());
				EditorPanel.box1.setSelectedIndex(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSelect());
				EditorPanel.isChange = false;
				
				EditorPanel.p0.setBorder(BorderFactory.createTitledBorder("通道1"));
				EditorPanel.p13.setBorder(BorderFactory.createTitledBorder("通道2"));
			}
		});
		JRadioButtonMenuItem dy2 = new JRadioButtonMenuItem("通道3/4");
		dy2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("单元二");
				EditorPanel.flag = 1;
				EditorPanel.b0.setEnabled(!EditorPanel.utils.get(1).getlChannel().getSwitch_flag());
				EditorPanel.b1.setEnabled(!EditorPanel.utils.get(1).getlChannel().getSwitch_flag());
				EditorPanel.b2.setEnabled(EditorPanel.utils.get(1).getlChannel().getSwitch_flag());
				EditorPanel.b3.setEnabled(!EditorPanel.utils.get(1).getlChannel().getSwitch_flag());
				EditorPanel.b4.setEnabled(!EditorPanel.utils.get(1).getrChannel().getSwitch_flag());
				EditorPanel.b5.setEnabled(!EditorPanel.utils.get(1).getrChannel().getSwitch_flag());
				EditorPanel.b6.setEnabled(EditorPanel.utils.get(1).getrChannel().getSwitch_flag());
				EditorPanel.b7.setEnabled(!EditorPanel.utils.get(1).getrChannel().getSwitch_flag());
				if(!EditorPanel.utils.get(EditorPanel.flag ).getrChannel().getSwitch_flag()&&EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSelect()>=70){
					System.out.println("右通道选择："+EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSelect());
					EditorPanel.b4.setEnabled(false);
					EditorPanel.b5.setEnabled(false);
					EditorPanel.b6.setEnabled(false);
					EditorPanel.b7.setEnabled(false);
				}
				
				EditorPanel.box.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSwitch_flag());
				EditorPanel.box1.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSwitch_flag());
				
				EditorPanel.jrb1.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSwitch_flag());
				EditorPanel.jrb2.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSwitch_flag());
				EditorPanel.jrb3.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSwitch_flag());
				EditorPanel.jrb4.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSwitch_flag());
				
				EditorPanel.s0.setValue(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getPower());
				EditorPanel.s1.setValue(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getTemp());
				EditorPanel.s2.setValue(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getPower());
				EditorPanel.s3.setValue(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getTemp());
				
				EditorPanel.clock1 = EditorPanel.utils.get(EditorPanel.flag).getlChannel().getClock();
				EditorPanel.clock2 = EditorPanel.utils.get(EditorPanel.flag).getrChannel().getClock();
				EditorPanel.clock1.setBounds(180, 30, 100, 40);
				EditorPanel.clock1.setOpaque(false);
				EditorPanel.p2.remove(1);
				EditorPanel.p2.add(EditorPanel.clock1);
				EditorPanel.p2.updateUI();
				EditorPanel.clock2.setBounds(180, 30, 100, 40);
				EditorPanel.clock2.setOpaque(false);
				EditorPanel.p11.remove(1);
				EditorPanel.p11.add(EditorPanel.clock2);
				EditorPanel.p11.updateUI();
				
				EditorPanel.isChange = true;
				if(EditorPanel.utils.get(EditorPanel.flag).getlChannel().isCustom_flag()){
					EditorPanel.box.setEnabled(false);
					EditorPanel.jrb2.setSelected(true);
				}else if(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSwitch_flag()){
					EditorPanel.box.setEnabled(true);
					EditorPanel.jrb1.setSelected(true);
				}else{
					
					EditorPanel.jrb1.setSelected(true);
					EditorPanel.box.setEnabled(false);
				}
				if(EditorPanel.utils.get(EditorPanel.flag).getrChannel().isCustom_flag()){
					EditorPanel.box1.setEnabled(false);
					EditorPanel.jrb4.setSelected(true);
				}else if(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSwitch_flag()){
					EditorPanel.box1.setEnabled(true);
					EditorPanel.jrb3.setSelected(true);
				}else{
					
					EditorPanel.jrb3.setSelected(true);
					EditorPanel.box1.setEnabled(false);
				}
				EditorPanel.box.setSelectedIndex(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSelect());
				EditorPanel.box1.setSelectedIndex(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSelect());
				EditorPanel.isChange = false;				
			
				EditorPanel.p0.setBorder(BorderFactory.createTitledBorder("通道3"));
				EditorPanel.p13.setBorder(BorderFactory.createTitledBorder("通道4"));
			}
		});
		JRadioButtonMenuItem dy3 = new JRadioButtonMenuItem("通道5/6");
		dy3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("单元三");
				EditorPanel.flag = 2;
				EditorPanel.b0.setEnabled(!EditorPanel.utils.get(2).getlChannel().getSwitch_flag());
				EditorPanel.b1.setEnabled(!EditorPanel.utils.get(2).getlChannel().getSwitch_flag());
				EditorPanel.b2.setEnabled(EditorPanel.utils.get(2).getlChannel().getSwitch_flag());
				EditorPanel.b3.setEnabled(!EditorPanel.utils.get(2).getlChannel().getSwitch_flag());
				EditorPanel.b4.setEnabled(!EditorPanel.utils.get(2).getrChannel().getSwitch_flag());
				EditorPanel.b5.setEnabled(!EditorPanel.utils.get(2).getrChannel().getSwitch_flag());
				EditorPanel.b6.setEnabled(EditorPanel.utils.get(2).getrChannel().getSwitch_flag());
				EditorPanel.b7.setEnabled(!EditorPanel.utils.get(2).getrChannel().getSwitch_flag());
				if(!EditorPanel.utils.get(EditorPanel.flag ).getrChannel().getSwitch_flag()&&EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSelect()>=70){
					EditorPanel.b4.setEnabled(false);
					EditorPanel.b5.setEnabled(false);
					EditorPanel.b6.setEnabled(false);
					EditorPanel.b7.setEnabled(false);
				}
				
				EditorPanel.box.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSwitch_flag());
				EditorPanel.box1.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSwitch_flag());
				
				EditorPanel.jrb1.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSwitch_flag());
				EditorPanel.jrb2.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSwitch_flag());
				EditorPanel.jrb3.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSwitch_flag());
				EditorPanel.jrb4.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSwitch_flag());
				
				EditorPanel.s0.setValue(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getPower());
				EditorPanel.s1.setValue(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getTemp());
				EditorPanel.s2.setValue(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getPower());
				EditorPanel.s3.setValue(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getTemp());
				
				EditorPanel.clock1 = EditorPanel.utils.get(EditorPanel.flag).getlChannel().getClock();
				EditorPanel.clock2 = EditorPanel.utils.get(EditorPanel.flag).getrChannel().getClock();
				EditorPanel.clock1.setBounds(180, 30, 100, 40);
				EditorPanel.clock1.setOpaque(false);
				EditorPanel.p2.remove(1);
				EditorPanel.p2.add(EditorPanel.clock1);
				EditorPanel.p2.updateUI();
				EditorPanel.clock2.setBounds(180, 30, 100, 40);
				EditorPanel.clock2.setOpaque(false);
				EditorPanel.p11.remove(1);
				EditorPanel.p11.add(EditorPanel.clock2);
				EditorPanel.p11.updateUI();
				
				EditorPanel.isChange = true;
				if(EditorPanel.utils.get(EditorPanel.flag).getlChannel().isCustom_flag()){
					EditorPanel.box.setEnabled(false);
					EditorPanel.jrb2.setSelected(true);
				}else if(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSwitch_flag()){
					EditorPanel.box.setEnabled(true);
					EditorPanel.jrb1.setSelected(true);
				}else{
					
					EditorPanel.jrb1.setSelected(true);
					EditorPanel.box.setEnabled(false);
				}
				if(EditorPanel.utils.get(EditorPanel.flag).getrChannel().isCustom_flag()){
					EditorPanel.box1.setEnabled(false);
					EditorPanel.jrb4.setSelected(true);
				}else if(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSwitch_flag()){
					EditorPanel.box1.setEnabled(true);
					EditorPanel.jrb3.setSelected(true);
				}else{
					
					EditorPanel.jrb3.setSelected(true);
					EditorPanel.box1.setEnabled(false);
				}
				EditorPanel.box.setSelectedIndex(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSelect());
				EditorPanel.box1.setSelectedIndex(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSelect());
				EditorPanel.isChange = false;				
			
				EditorPanel.p0.setBorder(BorderFactory.createTitledBorder("通道5"));
				EditorPanel.p13.setBorder(BorderFactory.createTitledBorder("通道6"));
			}
		});
		JRadioButtonMenuItem dy4 = new JRadioButtonMenuItem("通道7/8");
		dy4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("单元四");
				EditorPanel.flag = 3;
				EditorPanel.b0.setEnabled(!EditorPanel.utils.get(3).getlChannel().getSwitch_flag());
				EditorPanel.b1.setEnabled(!EditorPanel.utils.get(3).getlChannel().getSwitch_flag());
				EditorPanel.b2.setEnabled(EditorPanel.utils.get(3).getlChannel().getSwitch_flag());
				EditorPanel.b3.setEnabled(!EditorPanel.utils.get(3).getlChannel().getSwitch_flag());
				EditorPanel.b4.setEnabled(!EditorPanel.utils.get(3).getrChannel().getSwitch_flag());
				EditorPanel.b5.setEnabled(!EditorPanel.utils.get(3).getrChannel().getSwitch_flag());
				EditorPanel.b6.setEnabled(EditorPanel.utils.get(3).getrChannel().getSwitch_flag());
				EditorPanel.b7.setEnabled(!EditorPanel.utils.get(3).getrChannel().getSwitch_flag());
				if(!EditorPanel.utils.get(EditorPanel.flag ).getrChannel().getSwitch_flag()&&EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSelect()>=70){
					EditorPanel.b4.setEnabled(false);
					EditorPanel.b5.setEnabled(false);
					EditorPanel.b6.setEnabled(false);
					EditorPanel.b7.setEnabled(false);
				}
				
				EditorPanel.box.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSwitch_flag());
				EditorPanel.box1.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSwitch_flag());
				
				EditorPanel.jrb1.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSwitch_flag());
				EditorPanel.jrb2.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSwitch_flag());
				EditorPanel.jrb3.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSwitch_flag());
				EditorPanel.jrb4.setEnabled(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSwitch_flag());
				
				EditorPanel.s0.setValue(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getPower());
				EditorPanel.s1.setValue(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getTemp());
				EditorPanel.s2.setValue(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getPower());
				EditorPanel.s3.setValue(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getTemp());
				
				EditorPanel.clock1 = EditorPanel.utils.get(EditorPanel.flag).getlChannel().getClock();
				EditorPanel.clock2 = EditorPanel.utils.get(EditorPanel.flag).getrChannel().getClock();
				EditorPanel.clock1.setBounds(180, 30, 100, 40);
				EditorPanel.clock1.setOpaque(false);
				EditorPanel.p2.remove(1);
				EditorPanel.p2.add(EditorPanel.clock1);
				EditorPanel.p2.updateUI();
				EditorPanel.clock2.setBounds(180, 30, 100, 40);
				EditorPanel.clock2.setOpaque(false);
				EditorPanel.p11.remove(1);
				EditorPanel.p11.add(EditorPanel.clock2);
				EditorPanel.p11.updateUI();
				
				EditorPanel.isChange = true;
				if(EditorPanel.utils.get(EditorPanel.flag).getlChannel().isCustom_flag()){
					EditorPanel.box.setEnabled(false);
					EditorPanel.jrb2.setSelected(true);
				}else if(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSwitch_flag()){
					EditorPanel.box.setEnabled(true);
					EditorPanel.jrb1.setSelected(true);
				}else{
					EditorPanel.jrb1.setSelected(true);
					EditorPanel.box.setEnabled(false);					
				}
				if(EditorPanel.utils.get(EditorPanel.flag).getrChannel().isCustom_flag()){
					EditorPanel.box1.setEnabled(false);
					EditorPanel.jrb4.setSelected(true);
				}else if(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSwitch_flag()){
					EditorPanel.box1.setEnabled(true);
					EditorPanel.jrb3.setSelected(true);
				}else{
					EditorPanel.jrb3.setSelected(true);
					EditorPanel.box1.setEnabled(false);					
				}
				EditorPanel.isChange = false;
				
				EditorPanel.box.setSelectedIndex(EditorPanel.utils.get(EditorPanel.flag).getlChannel().getSelect());
				EditorPanel.box1.setSelectedIndex(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getSelect());
			
				EditorPanel.p0.setBorder(BorderFactory.createTitledBorder("通道7"));
				EditorPanel.p13.setBorder(BorderFactory.createTitledBorder("通道8"));
			}
		});
		buttonGroup1.add(menu.add(dy1));
		buttonGroup1.add(menu.add(dy2));
		buttonGroup1.add(menu.add(dy3));
		buttonGroup1.add(menu.add(dy4));
		
		

		// 创建帮助菜单
		menu = add(new JMenu("帮助"));

		item = menu.add(new JMenuItem("关于"));
		item.setIcon(new ImageIcon("img/about.gif"));
		item.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editor.about();
			}
		});
	}

	public EditorPanel getEditor() {
		return editor;
	}

	public char getMnemonic(String key) {
		return (key).charAt(0);
	}

}
