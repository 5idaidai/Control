package cn.edu.zzu.utopiar.bean;

import java.awt.Color;
import java.awt.Font;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Clock extends JPanel {

//	private boolean onetime = true;
	public String time = "0";
	private JLabel l;
	private String label = "20:00";
	private ChangeLabel cl = null;

	public Clock() {
		l = new JLabel(label);
		l.setFont(new Font("����", Font.BOLD, 30));
		l.setForeground(Color.BLACK);
//		this.cl = new ChangeLabel(time + ":0");
		add(l);

	}
	
	Clock(String time){
		this.time = time;
		l = new JLabel(label);

		l.setFont(new Font("����", Font.BOLD, 30));
		l.setForeground(Color.BLACK);
//		this.cl = new ChangeLabel(time + ":0");
		add(l);
	}
	
	public void changeLabel(String limitTime){
		if(Integer.parseInt(limitTime)<10){
			limitTime = "0"+limitTime;
		}
		if (this.cl!=null) {
			this.cl.destory();
		}
		label = limitTime+":00";
		l.setText(label);
	}
	
	public void changeTime(String time){
		if (this.cl!=null) {
			this.cl.destory();
		}
		
		this.cl = new ChangeLabel(time + ":0");
	}

	class ChangeLabel // ���������߳�
	{

		private int minitues;

		private String Sminitues;

		private int sound;

		private String Ssound;

		private String LabelTime;
		
		private final Timer timer;

		public ChangeLabel(String time) {
			// TODO Auto-generated constructor stub
			this.minitues = Integer.parseInt(time.substring(0, time.indexOf(':')));

			this.sound = Integer.parseInt(time.substring(time.indexOf(':') + 1));

			long start = System.currentTimeMillis();
			// end �������ʱ��
			final long end = start + this.minitues * 60 * 1000 + this.sound
					* 1000;

			timer = new Timer();
			// �ӳ�0���루������ִ�У���ʼ��ÿ��1000����ִ��һ��
			timer.schedule(new TimerTask() {
				public void run() {
					// show��ʣ��ʱ�䣬��Ҫ��ʾ��ʱ��
					long show = end - System.currentTimeMillis();
					minitues = (int) (show / 1000 / 60 % 60);// ��
					sound = (int) (show / 1000 % 60);// ��
//					System.out.println("����ʱ�䣺" + minitues + "��" + sound + "��");
					if(minitues==0&&sound==0)
						timer.cancel();
					LabelTime = getTime();
					display();
				}
			}, 0, 1000);
			// ��ʱ����ʱ��ֹͣȫ��timer��ʱ�ƻ�����
			timer.schedule(new TimerTask() {
				public void run() {
					timer.cancel();
				}

			}, new Date(end));
		}
		
		private void destory (){
			timer.cancel();
		}
		
		private String getTime() {
			if (minitues < 10)
				this.Sminitues = "0" + minitues;
			else
				this.Sminitues = "" + minitues;
			if (sound < 10)
				this.Ssound = "0" + sound;
			else
				this.Ssound = "" + sound;
			return this.Sminitues + ":" + this.Ssound;
		}

		public void display() {
			/*
			 * ��ʾ����ʱ
			 */
			l.setText(this.LabelTime);
		}
	}



	public static void main(String[] args){
		JFrame frame = new JFrame();
		Clock clockJPanel = new Clock("10");
		frame.add(clockJPanel);
		clockJPanel.changeTime("5");
		frame.setVisible(true);
	}
	
}
