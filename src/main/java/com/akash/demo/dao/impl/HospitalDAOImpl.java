package com.akash.demo.dao.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.akash.demo.vo.MedicineVO;

@Repository
public class HospitalDAOImpl {

	public boolean validateUser(String userId, String password) {
		if (userId.equals("akash") && password.equals("password")) {
			return true;
		} else {
			return false;
		}
	}

	public List<MedicineVO> getAllMedicineInfo() {
		return Arrays.asList(new MedicineVO(10, "para", "cold", "cold", false, "morn"));
	}

	public MedicineVO getMedicineInfoForName(String medicineName) {
		return new MedicineVO(10, "para", "cold", "cold", false, "morn");
	}

	public List<MedicineVO> getMedicineForDisease(String diseaseName) {
		return null;
	}

	public List<MedicineVO> getMedicineForSymptom(String symptom) {
		return null;
	}

}
