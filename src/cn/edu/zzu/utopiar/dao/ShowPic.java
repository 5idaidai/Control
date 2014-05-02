package cn.edu.zzu.utopiar.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zzu.entity.Wave;

public class ShowPic{
	
	public static List<String> readTxtFile(String filePath) {
		List<String> list = new ArrayList<String>();		
		try {
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(	new FileInputStream(file),"utf-8");// ���ǵ������ʽ
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
//					System.out.println(lineTxt);
					list.add(lineTxt);
				}
				read.close();
				return list;
			} else {
				System.out.println("�Ҳ���ָ�����ļ�");
				return null;
			}
		} catch (Exception e) {
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
			return null;
		}
	}
	
	public static Wave getWave(String str){
		Wave wave = new Wave();
		String[] strings = str.split(",");
		wave.setTime1(strings[0].toCharArray()[2]-48);
		wave.setTime2(strings[0].toCharArray()[3]-48);
		wave.setName1(strings[1].substring(strings[1].indexOf('x')+1));
		wave.setName2(strings[2].substring(strings[2].indexOf('x')+1));
		wave.setTime(Integer.parseInt(strings[3]));
		return wave;
	}
	
	public static void main(String[] args){
		List<String> strings = readTxtFile("chufang/����79.txt");
		for (String string : strings) {
			getWave(string);
		}		
	}
	
}
