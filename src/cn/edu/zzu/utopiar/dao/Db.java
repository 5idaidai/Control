package cn.edu.zzu.utopiar.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cn.edu.zzu.entity.Patient;

public class Db {

	public static void update(Patient patient){
		try {
			ResultSet rs1;
			Connection conn1 = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=data/Case.mdb");
			PreparedStatement prestmt1;
			Connection conn2 = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=data/Case.mdb");
			PreparedStatement prestmt2;
			String sql1 = "SELECT 病历号 FROM patient"; 
			String sql2 = "";
			prestmt1 = conn1.prepareStatement(sql1);
			rs1 = prestmt1.executeQuery();
			while(rs1.next()){
				String str = rs1.getString(1);
				if(str.equalsIgnoreCase(patient.getId())){
					sql2 = "UPDATE patient SET 姓名 = ?, 性别 = ?, 年龄 = ?, 职业 = ?, 电话 = ?, 处方号 = ?, 病症描述 = ? WHERE 病历号 = ?";
					System.out.println(sql2);
					prestmt2 = conn2.prepareStatement(sql2);					
					prestmt2.setObject(1, patient.getName());
					prestmt2.setObject(2, patient.getSex());
					prestmt2.setObject(3, patient.getAge());
					prestmt2.setObject(4, patient.getCareer());
					prestmt2.setObject(5, patient.getPhone());
					prestmt2.setObject(6, patient.getRecipe());
					prestmt2.setObject(7, patient.getDescribe());
					prestmt2.setString(8, patient.getId());
					
					prestmt2.executeUpdate();
					prestmt2.close();
					conn2.close();
					break;
				}
						
			}
			if(sql2.equalsIgnoreCase("")){
				sql2 = "INSERT INTO patient (病历号, 姓名 , 性别, 年龄, 职业 , 电话, 处方号, 病症描述) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
				System.out.println(sql2);
				prestmt2 = conn2.prepareStatement(sql2);
				prestmt2.setString(1, patient.getId());
				prestmt2.setObject(2, patient.getName());
				prestmt2.setObject(3, patient.getSex());
				prestmt2.setObject(4, patient.getAge());
				prestmt2.setObject(5, patient.getCareer());
				prestmt2.setObject(6, patient.getPhone());
				prestmt2.setObject(7, patient.getRecipe());
				prestmt2.setObject(8, patient.getDescribe());
				
				prestmt2.executeUpdate();
				prestmt2.close();
				conn2.close();
			}
			
			prestmt1.close();
			conn1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("Success");
	}
	
	public static Patient serch(String ID){
		Patient patient = null;
		try {
			ResultSet rs2;
			Connection conn2 = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=data/Case.mdb");
			PreparedStatement prestmt2;
			String sql2 = "SELECT * FROM patient";
			prestmt2 = conn2.prepareStatement(sql2);
			rs2 = prestmt2.executeQuery();
			while(rs2.next()){
				String str = rs2.getString(1);
				if(str.equalsIgnoreCase(ID)){
					patient = new Patient();
					patient.setId(ID);
					patient.setName(rs2.getString(2));
					patient.setSex(rs2.getString(3));
					patient.setAge(rs2.getString(4));
					patient.setCareer(rs2.getString(5));
					patient.setPhone(rs2.getString(6));
					patient.setRecipe(rs2.getString(7));
					patient.setDescribe(rs2.getString(8));
					break;	
				}
					
			}			
			prestmt2.close();
			conn2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("Success");
		return patient;
	}
	
	public static void main(String[] args){
		Patient patient = new Patient();
		patient.setId("1");
		patient.setName("张金瑞");
		patient.setSex("男");
		patient.setAge("24");
		patient.setCareer("学生");
		patient.setPhone("1234567");
		patient.setRecipe("1");
		patient.setDescribe("hehe");
		update(patient);
		
		Patient p = serch("2");
		if(p!=null){
			System.out.println("病历号："+p.getId());
			System.out.println("姓名："+patient.getName());
			System.out.println("性别："+patient.getSex());
			System.out.println("年龄："+patient.getAge());
			System.out.println("职业："+patient.getCareer());
			System.out.println("电话："+patient.getPhone());
			System.out.println("处方号："+patient.getRecipe());
			System.out.println("病症描述："+patient.getDescribe());
		}
		
	}
	
}
