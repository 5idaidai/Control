package cn.edu.zzu.utopiar.rect;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zzu.entity.Wave;
import cn.edu.zzu.utopiar.bean.Clock;

public class Channel {

	/**
	 * 图像标记
	 */
	String img_flag="07";
	
	/**
	 * 图片标记序列
	 */
	List<Wave> waves = new ArrayList<Wave>();
	
	/**
	 * 处方标记
	 */
	int select;
	
	/**
	 * 通道开关
	 */
	boolean switch_flag = true;
	
	/**
	 * 自定义处方标记
	 */
	boolean custom_flag = false;
	
	/**
	 * 通道强度标记
	 */
	int power = 0;
	
	/**
	 * 通道温度标识
	 */
	int temp = 0;
	
	/**
	 * 自定义时间
	 */
	int custom_time = 0;
	
	/**
	 * 通道计时
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
