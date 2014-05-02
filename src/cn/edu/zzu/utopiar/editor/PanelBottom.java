package cn.edu.zzu.utopiar.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelBottom extends JPanel {

	public static List<Image> img = new ArrayList<Image>();
	static{
		
		File dir = new File("img/led");
		File[] files = dir.listFiles();
		for (File file : files) {
			if(!file.isDirectory()){
				img.add(new ImageIcon(file.getPath()).getImage());
			}
		}
	}
	
	List<Bottom> bottoms = null;
	
	
	
	public List<Bottom> getBottoms() {
		return bottoms;
	}

	public PanelBottom() {
		// TODO Auto-generated constructor stub
		List<JLabel> labels = new ArrayList<JLabel>();
		setLayout(null);
		bottoms = new ArrayList<Bottom>();
		for(int i=1;i<9;i++){
			labels.add(new JLabel("Í¨µÀ"+i+"£º"));
			Bottom b = new Bottom();
			b.index = i-1;
			bottoms.add(b);
		}
		int i=0;
		for (JLabel jLabel : labels) {
			jLabel.setBounds(50+i*(150), 40, 60, 20);
			add(jLabel);
			i++;
		}
		i=0;
		for (Bottom bottom : bottoms) {
			bottom.setBounds(110+i*(150), 20, 50, 50);
			add(bottom);
			i++;
		}
	}
	
	class Bottom extends JPanel{
		public int index = 0;
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			paint(g);
		}
		
		@Override
		public void paint(Graphics g) {
			// TODO Auto-generated method stub
//			g.clearRect(0, 0, 100, 100);
			this.setOpaque(false);
			g.drawImage(img.get(EditorPanel.leds.get(index)), 0, 10,null);
		}
		
	}
	
	
}
