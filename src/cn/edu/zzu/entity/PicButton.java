package cn.edu.zzu.entity;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

public class PicButton extends JButton{
        private BufferedImage image_normal;               //����ڰ�ť�ϵ�ͼƬ
        private BufferedImage image_off;                //��겻�ڰ�ť�ϵ�ͼƬ
        private BufferedImage image_pressed;            //��갴�°�ťʱ��ͼƬ
        private int buttonWidth;                        //��
        private int buttonHeight;                       //��
        private int[] pixels;                         //����ͼƬ���ݵ����飬���ڼ���contains
        private boolean mouseOn;
        private boolean mousePressed;
         
        public PicButton(String pic1, String pic2, String pic3){
                mouseOn = false;
                mousePressed = false;
                //����ͼƬ
                image_normal = loadImage(pic1);
                image_off = loadImage(pic2);
                image_pressed = loadImage(pic3);
                
                buttonWidth = image_normal.getWidth();
                buttonHeight = image_normal.getHeight();
                
                //��ȡͼƬ����
                pixels = new int[buttonWidth * buttonHeight];
                //ץȡ��������
                PixelGrabber pg = new PixelGrabber(image_normal, 0, 0, buttonWidth, buttonHeight, pixels, 0, buttonWidth);
                try{
                        pg.grabPixels();
                }
                catch(Exception e){
                        e.printStackTrace();
                }
                
                //�������ã�������в�Ӱ��
                this.setOpaque(false);
                
                this.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                this.addMouseListener(new MouseHandler());
        }
        
        //��ȡͼƬ�ļ�
        public BufferedImage loadImage(String filename){
                File file = new File(filename);
                
                if(!file.exists())
                        return null;
                
                try{
                        return ImageIO.read(file);
                }
                catch(IOException e){
                        e.printStackTrace();
                        return null;
                }
        }
        
        //���Ǵ˷��������Զ����ͼƬ
        public void paintComponent(Graphics g){
        	if(this.isEnabled()){
        		g.drawImage(image_normal, 0, 0, this);
                if(mouseOn)
                        g.drawImage(image_normal, 0, 0, this);
                else if(mousePressed)
                        g.drawImage(image_pressed, 0, 0, this);
        	}else {
        		g.drawImage(image_off, 0, 0, this);
			}
                
        }
        
        //���Ǵ˷��������Զ���ı߿�
        public void paintBorder(Graphics g){
                //��Ҫ�߿�
        }
        
        public boolean contains(int x, int y){
                //���ж��Ļ���Խ�磬�����֮��Ҳ�ἤ���������
                if(!super.contains(x, y))
                        return false;
                
                int alpha = (pixels[(buttonWidth * y + x)] >> 24) & 0xff;

                repaint();
                if(alpha == 0){
                        return false;
                }
                else{
                        return true;
                }
        }
             
        //������롢�뿪ͼƬ��Χ����Ϣ
        class MouseHandler extends MouseAdapter  {
                public void mouseExited(MouseEvent e){
                        mouseOn = false;
                        repaint();
                }
                public void mouseEntered(MouseEvent e){
                        mouseOn = true;
                        repaint();
                }
                public void mousePressed(MouseEvent e){
                        mouseOn = false;
                        mousePressed = true;
                        repaint();
                }
                public void mouseReleased(MouseEvent e){
                        //��ֹ�ڰ�ť֮��ĵط��ɿ����
                        if(contains(e.getX(), e.getY()))
                                mouseOn = true;
                        else
                                mouseOn = false;
                        
                        mousePressed = false;
                        repaint();
                }
        }
        
        public static void main(String[] args) {
    		JButton button = new PicButton("img/button_add.png", "img/button_add_off.png", "img/button_add_pressed.png");// ����һ��Բ�ΰ�ť
    		button.setBackground(Color.green);// ���ñ���ɫΪ��ɫ
    		// ����һ�������ʾ�����ť
    		button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("���");
				}
			});
    		JFrame frame = new JFrame("ͼ�ΰ�ť");
    		frame.getContentPane().setBackground(Color.yellow);
    		frame.getContentPane().setLayout(new FlowLayout());
    		frame.getContentPane().add(button);
    		frame.setSize(200, 200);
    		frame.setVisible(true);
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	}
}