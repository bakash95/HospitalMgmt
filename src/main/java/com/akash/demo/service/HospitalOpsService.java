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
}
