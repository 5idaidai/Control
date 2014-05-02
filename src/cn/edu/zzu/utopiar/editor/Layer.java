package cn.edu.zzu.utopiar.editor;

import java.awt.BorderLayout;

import javax.swing.JPanel;


public class Layer extends JPanel{
	
	PicShow pic;
	
	public Layer(int index){
		this.setLayout(new BorderLayout());
		pic = new PicShow(index,0);
		add(pic,BorderLayout.CENTER);		
		pic.repaint();
	}
	
	void changeFlag(int flag){
		this.pic.setFlag(flag);
	}

	public PicShow getPic() {
		return pic;
	}
	
}


