package cn.edu.zzu.utopiar.editor;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

class RPicPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	RPicPanel(){  
//		setBackground(Color.white);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
	}
	
	@Override
	public void paint(Graphics g) {
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(EditorPanel.img.get(EditorPanel.utils.get(EditorPanel.flag).getrChannel().getImg_flag()), 0, 0, this);
	}
	
}
