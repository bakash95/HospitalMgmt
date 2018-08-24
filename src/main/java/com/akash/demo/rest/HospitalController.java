package com.akash.demo.rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akash.demo.constants.CommonConstants;
import com.akash.demo.jwt.JWTAuth;
import com.akash.demo.service.HospitalOpsService;
import com.akash.demo.vo.MedicineVO;

@RestController
public class HospitalController {

	@Autowired
	private HospitalOpsService hospitalOpsService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ResponseEntity<String> register(@RequestParam("username") String userName,
			@RequestParam("password") String password, @RequestParam("role") String role) {
		if (hospitalOpsService.register(userName, password, role)) {
			return ResponseEntity.status(HttpStatus.OK).body("User registered successfully!");
		}
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("System Unavailable");
	}

	@RequestMapping(value = "/getAllMedicineInfo", produces = "application/json", method = RequestMethod.GET)
	private List<MedicineVO> getAllMedicineInfo() {
		return hospitalOpsService.getAllMedicineInfo();
	}

	@RequestMapping(value = "/getInfoForMedicineForSymptom", produces = "application/json", method = RequestMethod.GET)
	private List<MedicineVO> getInfoOfMedicine(@RequestParam("symptom") String sypmtomName) {
		return hospitalOpsService.getMedicineForSymptom(sypmtomName);
	}

	@RequestMapping(value = "/getInfoForMedicineForDisease", produces = "application/json", method = RequestMethod.GET)
	private List<MedicineVO> getMedicineForDisease(@RequestParam("disease") String diseaseName) {
		return hospitalOpsService.getMedicineForDisease(diseaseName);
	}

	@RequestMapping(value = "/getInfoForMedicine", produces = "application/json", method = RequestMethod.GET)
	private MedicineVO getMedicineForSymptom(@RequestParam("medicinename") String medicinename) {
		return hospitalOpsService.getMedicineInfoForName(medicinename);
	}

	@RequestMapping(value = "/getTokenForAuth", produces = "application/json", method = RequestMethod.GET)
	private ResponseEntity<String> getTokenForAuth(HttpServletResponse httpServletResponse,
			@RequestParam("username") String username, @RequestParam("password") String password) {
		if (!hospitalOpsService.checkRole(username, CommonConstants.ADMIN)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(CommonConstants.JWT_AUTH_GEN_FAILURE);
		}
		if (hospitalOpsService.validateUser(username, password)) {
			String authToken = JWTAuth.createJWT(String.valueOf(username), CommonConstants.ISSUER,
					CommonConstants.SUBJECT, CommonConstants.EXPIRY_TIME_AUTH);
			httpServletResponse.addHeader(CommonConstants.AUTHROIZATION,
					CommonConstants.BEARER + "=" + authToken.trim());
			return ResponseEntity.status(HttpStatus.OK).body(authToken);
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(CommonConstants.JWT_AUTH_GEN_FAILURE);
	}

	@RequestMapping(value = "/insertMedicine", method = RequestMethod.GET)
	private ResponseEntity<String> insertMedicine(@RequestParam("medicinename") String medicineName) {
		if (hospitalOpsService.insertMedicineToDB(medicineName)) {
			return ResponseEntity.status(HttpStatus.OK).body(CommonConstants.MEDICINE_INSERTED);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body("Trying to insert a medicine which is already there?");
	}

	@RequestMapping(value = "/updateMedicine", method = RequestMethod.POST)
	private ResponseEntity<String> updateMedicine(@RequestBody MedicineVO medicineVO) {
		if (hospitalOpsService.updateMedicine(medicineVO)) {
			return ResponseEntity.status(HttpStatus.OK).body("Medicine was updated into the DB");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body("You are not authorized to update as the medicine is not present in the database");
	}

	@RequestMapping(value = "/removeMedicine", method = RequestMethod.GET)
	private ResponseEntity<String> removeMedicine(@RequestParam("medicinename") String medicineName) {
		if (hospitalOpsService.removeMedicineFromDB(medicineName)) {
			return ResponseEntity.status(HttpStatus.OK).body("Medicine was Removed into the DB");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body("Trying to remove a medicine which is already there?");
	}

}
