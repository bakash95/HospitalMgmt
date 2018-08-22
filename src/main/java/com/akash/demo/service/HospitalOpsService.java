package com.akash.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akash.demo.dao.impl.HospitalDAOImpl;
import com.akash.demo.vo.MedicineVO;

@Service
public class HospitalOpsService {

	@Autowired
	private HospitalDAOImpl hospitalDAOImpl;

	public List<MedicineVO> getAllMedicineInfo() {
		return hospitalDAOImpl.getAllMedicineInfo();
	}

	public boolean validateUser(String userId, String password) {
		return hospitalDAOImpl.validateUser(userId, password);
	}

	public MedicineVO getMedicineInfoForName(String medicineName) {
		return hospitalDAOImpl.getMedicineInfoForName(medicineName);
	}

	public List<MedicineVO> getMedicineForDisease(String diseaseName) {
		return hospitalDAOImpl.getMedicineForDisease(diseaseName);
	}

	public List<MedicineVO> getMedicineForSymptom(String symptom) {
		return hospitalDAOImpl.getMedicineForSymptom(symptom);
	}

	public boolean insertMedicineToDB(String medicineName) {
		return hospitalDAOImpl.insertMedicineToDB(medicineName);
	}

	public boolean register(String userName, String password, String role) {
		String ROLE_USER = "";
		if (role.toLowerCase().equals("admin")) {
			ROLE_USER = "ADMIN";
		} else if (role.toLowerCase().equals("doctor")) {
			ROLE_USER = "DOCTOR";
		} else if (role.toLowerCase().equals("staff")) {
			ROLE_USER = "STAFF";
		}
		return hospitalDAOImpl.register(userName, password, ROLE_USER);
	}

	public boolean checkRole(String username, String string) {
		return hospitalDAOImpl.checkRole(username, string);
	}

	public boolean removeMedicineFromDB(String medicineName) {
		return hospitalDAOImpl.removeMedicineFromDB(medicineName);
	}
}
