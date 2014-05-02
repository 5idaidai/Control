package cn.edu.zzu.utopiar.editor;

import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PicShow extends JPanel{
	
	int index=0,flag=0;
	public static HashMap<Integer, HashMap<Integer, Image>> map;
	public static HashMap<Integer, Image> img;
	
	public PicShow(int index,int flag) {
		// TODO Auto-generated constructor stub
		this.index = index;
		this.flag = flag;
	}
	
	
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}



	static{
		map = new HashMap<Integer, HashMap<Integer,Image>>();
		img = new HashMap<Integer, Image>();
		img.put(0, new ImageIcon("img/wave/����1/07.gif").getImage());
		img.put(1, new ImageIcon("img/wave/����1/06.gif").getImage());
		img.put(2, new ImageIcon("img/wave/����1/04.gif").getImage());
		img.put(3, new ImageIcon("img/wave/����1/02.gif").getImage());
		map.put(0, img);
		
		img = new HashMap<Integer, Image>();
		img.put(0, new ImageIcon("img/wave/����2/0C.gif").getImage());
		img.put(1, new ImageIcon("img/wave/����2/0D.gif").getImage());
		img.put(2, new ImageIcon("img/wave/����2/0E.gif").getImage());
		img.put(3, new ImageIcon("img/wave/����2/0F.gif").getImage());
		map.put(1, img);
		
		img = new HashMap<Integer, Image>();
		img.put(0, new ImageIcon("img/wave/����3/08.gif").getImage());
		img.put(1, new ImageIcon("img/wave/����3/09.gif").getImage());
		img.put(2, new ImageIcon("img/wave/����3/0A.gif").getImage());
		img.put(3, new ImageIcon("img/wave/����3/0B.gif").getImage());
		map.put(2, img);
		
		img = new HashMap<Integer, Image>();
		img.put(0, new ImageIcon("img/wave/����4/14.gif").getImage());
		img.put(1, new ImageIcon("img/wave/����4/15.gif").getImage());
		img.put(2, new ImageIcon("img/wave/����4/16.gif").getImage());
		img.put(3, new ImageIcon("img/wave/����4/17.gif").getImage());
		map.put(3, img);
		
		img = new HashMap<Integer, Image>();
		img.put(0, new ImageIcon("img/wave/����5/10.gif").getImage());
		img.put(1, new ImageIcon("img/wave/����5/11.gif").getImage());
		img.put(2, new ImageIcon("img/wave/����5/12.gif").getImage());
		img.put(3, new ImageIcon("img/wave/����5/13.gif").getImage());
		map.put(4, img);
		
		img = new HashMap<Integer, Image>();
		img.put(0, new ImageIcon("img/wave/����6/18.gif").getImage());
		img.put(1, new ImageIcon("img/wave/����6/19.gif").getImage());
		img.put(2, new ImageIcon("img/wave/����6/1A.gif").getImage());
		img.put(3, new ImageIcon("img/wave/����6/1B.gif").getImage());
		map.put(5, img);
		
		img = new HashMap<Integer, Image>();
		img.put(0, new ImageIcon("img/wave/����7/1C.gif").getImage());
		img.put(1, new ImageIcon("img/wave/����7/1D.gif").getImage());
		img.put(2, new ImageIcon("img/wave/����7/1E.gif").getImage());
		img.put(3, new ImageIcon("img/wave/����7/1F.gif").getImage());
		map.put(6, img);
		
		img = new HashMap<Integer, Image>();
		img.put(0, new ImageIcon("img/wave/����8/20.gif").getImage());
		img.put(1, new ImageIcon("img/wave/����8/21.gif").getImage());
		img.put(2, new ImageIcon("img/wave/����8/22.gif").getImage());
		img.put(3, new ImageIcon("img/wave/����8/23.gif").getImage());
		map.put(7, img);
		
		img = new HashMap<Integer, Image>();
		img.put(0, new ImageIcon("img/wave/����9/24.gif").getImage());
		img.put(1, new ImageIcon("img/wave/����9/25.gif").getImage());
		img.put(2, new ImageIcon("img/wave/����9/26.gif").getImage());
		img.put(3, new ImageIcon("img/wave/����9/27.gif").getImage());
		map.put(8, img);
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(map.get(index).get(flag), 0, 0,180,160, null);
	}
	
}
