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
        private BufferedImage image_normal;               //鼠标在按钮上的图片
        private BufferedImage image_off;                //鼠标不在按钮上的图片
        private BufferedImage image_pressed;            //鼠标按下按钮时的图片
        private int buttonWidth;                        //宽
        private int buttonHeight;                       //高
        private int[] pixels;                         //储存图片数据的数组，用于计算contains
        private boolean mouseOn;
        private boolean mousePressed;
         
        public PicButton(String pic1, String pic2, String pic3){
                mouseOn = false;
                mousePressed = false;
                //加载图片
                image_normal = loadImage(pic1);
                image_off = loadImage(pic2);
                image_pressed = loadImage(pic3);
                
                buttonWidth = image_normal.getWidth();
                buttonHeight = image_normal.getHeight();
                
                //读取图片数据
                pixels = new int[buttonWidth * buttonHeight];
                //抓取像素数据
                PixelGrabber pg = new PixelGrabber(image_normal, 0, 0, buttonWidth, buttonHeight, pixels, 0, buttonWidth);
                try{
                        pg.grabPixels();
                }
                catch(Exception e){
                        e.printStackTrace();
                }
                
                //必须设置！否则会有残影！
                this.setOpaque(false);
                
                this.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                this.addMouseListener(new MouseHandler());
        }
        
        //读取图片文件
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
        
        //覆盖此方法绘制自定义的图片
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
        
        //覆盖此方法绘制自定义的边框
        public void paintBorder(Graphics g){
                //不要边框
        }
        
        public boolean contains(int x, int y){
                //不判定的话会越界，在组件之外也会激发这个方法
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
             
        //处理进入、离开图片范围的消息
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
                        //防止在按钮之外的地方松开鼠标
                        if(contains(e.getX(), e.getY()))
                                mouseOn = true;
                        else
                                mouseOn = false;
                        
                        mousePressed = false;
                        repaint();
                }
        }
        
        public static void main(String[] args) {
    		JButton button = new PicButton("img/button_add.png", "img/button_add_off.png", "img/button_add_pressed.png");// 产生一个圆形按钮
    		button.setBackground(Color.green);// 设置背景色为绿色
    		// 产生一个框架显示这个按钮
    		button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("点击");
				}
			});
    		JFrame frame = new JFrame("图形按钮");
    		frame.getContentPane().setBackground(Color.yellow);
    		frame.getContentPane().setLayout(new FlowLayout());
    		frame.getContentPane().add(button);
    		frame.setSize(200, 200);
    		frame.setVisible(true);
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	}
}