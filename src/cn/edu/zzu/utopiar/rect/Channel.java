package cn.edu.zzu.utopiar.rect;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zzu.entity.Wave;
import cn.edu.zzu.utopiar.bean.Clock;

public class Channel {

	/**
	 * ͼ����
	 */
	String img_flag="07";
	
	/**
	 * ͼƬ�������
	 */
	List<Wave> waves = new ArrayList<Wave>();
	
	/**
	 * �������
	 */
	int select;
	
	/**
	 * ͨ������
	 */
	boolean switch_flag = true;
	
	/**
	 * �Զ��崦�����
	 */
	boolean custom_flag = false;
	
	/**
	 * ͨ��ǿ�ȱ��
	 */
	int power = 0;
	
	/**
	 * ͨ���¶ȱ�ʶ
	 */
	int temp = 0;
	
	/**
	 * �Զ���ʱ��
	 */
	int custom_time = 0;
	
	/**
	 * ͨ����ʱ
	 */
	Clock clock = new Clock();
	
	public boolean isCustom_flag() {
		return custom_flag;
	}

	public void setCustom_flag(boolean customFlag) {
		custom_flag = customFlag;
	}

	public int getSelect() {
		return select;
	}

	public void setSelect(int select) {
		this.select = select;
	}

	public String getImg_flag() {
		return img_flag;
	}

	public void setImg_flag(String imgFlag) {
		img_flag = imgFlag;
	}

	public boolean getSwitch_flag() {
		return switch_flag;
	}

	public void setSwitch_flag(boolean switchFlag) {
		switch_flag = switchFlag;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	public Clock getClock() {
		return clock;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}

	public int getCustom_time() {
		return custom_time;
	}

	public void setCustom_time(int customTime) {
		custom_time = customTime;
	}

	public List<Wave> getWaves() {
		return waves;
	}

	public void setWaves(List<Wave> waves) {
		this.waves = waves;
	}
	
	
}
