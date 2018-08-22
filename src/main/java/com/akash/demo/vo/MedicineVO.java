package com.akash.demo.vo;

public class MedicineVO {
	String medicineName;
	String disease;
	String symptomsOfDisease;
	String afterOrBeforeFood;
	String timing; // change to enum

	public MedicineVO(String medicineName, String disease, String symptomsOfDisease, String afterOrBeforeFood,
			String timing) {
		super();
		this.medicineName = medicineName;
		this.disease = disease;
		this.symptomsOfDisease = symptomsOfDisease;
		this.afterOrBeforeFood = afterOrBeforeFood;
		this.timing = timing;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public String getSymptomsOfDisease() {
		return symptomsOfDisease;
	}

	public void setSymptomsOfDisease(String symptomsOfDisease) {
		this.symptomsOfDisease = symptomsOfDisease;
	}

	public String isAfterOrBeforeFood() {
		return afterOrBeforeFood;
	}

	public void setAfterOrBeforeFood(String afterOrBeforeFood) {
		this.afterOrBeforeFood = afterOrBeforeFood;
	}

	public String getTiming() {
		return timing;
	}

	public void setTiming(String timing) {
		this.timing = timing;
	}

}
