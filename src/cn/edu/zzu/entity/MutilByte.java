package cn.edu.zzu.entity;

import cn.edu.zzu.utopiar.bean.CommWrite;

public class MutilByte {

	int head = 170;
	int channel;
	int grad;
	int chufang;
	int cs;
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public int getGrad() {
		return grad;
	}
	public void setGrad(int grad) {
		this.grad = grad;
	}
	public int getChufang() {
		return chufang;
	}
	public void setChufang(int chufang) {
		this.chufang = chufang;
	}
	public int getCs() {
		return cs;
	}
	public void setCs(int cs) {
		this.cs = cs;
	}
	public int getHead() {
		return head;
	}
	
	public static void main(String[] args){
		MutilByte bytes = new MutilByte();
		bytes.setChannel(18);
		bytes.setGrad(3);
		bytes.setChufang(41);
		bytes.setCs(232);
//		CommWrite.write(bytes, "COM3");
	}
	
}
