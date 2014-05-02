package cn.edu.zzu.entity;

public class Wave {

	int time1;
	int time2;
	
	String name1;
	String name2;
	
	int time;
	
	public int getTime(int flag){
		switch (flag) {
		case 3:
			return time;
			
		case 0:
			return time1;
			
		case 1:
			return time2;

		default:
			return 0;
		}
	}
	
	public String getName(int flag) {
		switch (flag) {
		case 0:
			return name1;
			
		case 1:
			return name2;
			
		default:
			return null;
		}
	}

	public void setTime1(int time1) {
		this.time1 = time1;
	}

	public void setTime2(int time2) {
		this.time2 = time2;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
}
