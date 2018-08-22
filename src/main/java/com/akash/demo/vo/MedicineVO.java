package com.akash.demo.vo;

public class MedicineVO {
	int medicineID;
	String medicineName;
	String disease;
	String symptomsOfDisease;
	boolean afterOrBeforeFood;
	String timing; //change to enum

	public MedicineVO(int medicineID, String medicineName, String disease, String symptomsOfDisease,
			boolean afterOrBeforeFood, String timing) {
		super();
		this.medicineID = medicineID;
		this.medicineName = medicineName;
		this.disease = disease;
		this.symptomsOfDisease = symptomsOfDisease;
		this.afterOrBeforeFood = afterOrBeforeFood;
		this.timing = timing;
	}

	public int getMedicineID() {
		return medicineID;
	}

	public void setMedicineID(int medicineID) {
		this.medicineID = medicineID;
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

	public boolean isAfterOrBeforeFood() {
		return afterOrBeforeFood;
	}

	public void setAfterOrBeforeFood(boolean afterOrBeforeFood) {
		this.afterOrBeforeFood = afterOrBeforeFood;
	}

	public String getTiming() {
		return timing;
	}

	public void setTiming(String timing) {
		this.timing = timing;
	}

}
