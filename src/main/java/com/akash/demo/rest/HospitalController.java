package com.akash.demo.rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping(value = "/info/getAllMedicineInfo", produces = "application/json", method = RequestMethod.GET)
	private List<MedicineVO> getAllMedicineInfo() {
		return hospitalOpsService.getAllMedicineInfo();
	}

	@RequestMapping(value = "/view/getInfoForMedicine/{medicineName}", produces = "application/json", method = RequestMethod.GET)
	private MedicineVO getInfoOfMedicine(@PathVariable String medicineName) {
		return hospitalOpsService.getMedicineInfoForName(medicineName);
	}

	@RequestMapping(value = "/view/getInfoForMedicine/{disease}", produces = "application/json", method = RequestMethod.GET)
	private List<MedicineVO> getMedicineForDisease(@PathVariable String diseaseName) {
		return hospitalOpsService.getMedicineForDisease(diseaseName);
	}

	@RequestMapping(value = "/view/getInfoForMedicine/{symptoms}", produces = "application/json", method = RequestMethod.GET)
	private List<MedicineVO> getMedicineForSymptom(@PathVariable String symptom) {
		return hospitalOpsService.getMedicineForSymptom(symptom);
	}

	@RequestMapping(value = "/getTokenForAuth", method = RequestMethod.GET)
	private ResponseEntity<String> getAllMedicineInfo(HttpServletResponse httpServletResponse,
			@RequestParam("username") String username, @RequestParam("password") String password) {
		if (!hospitalOpsService.checkRole(username, "ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(CommonConstants.JWT_AUTH_GEN_FAILURE);
		}
		if (hospitalOpsService.validateUser(username, password)) {
			httpServletResponse.addHeader(CommonConstants.AUTHROIZATION,
					CommonConstants.BEARER + "=" + JWTAuth.createJWT(String.valueOf(username), CommonConstants.ISSUER,
							CommonConstants.SUBJECT, CommonConstants.EXPIRY_TIME_AUTH));
			return ResponseEntity.status(HttpStatus.OK).body(CommonConstants.JWT_AUTH_GEN_SUCCESS);
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(CommonConstants.JWT_AUTH_GEN_FAILURE);
	}

	@RequestMapping(value = "/admin/insertMedicine", method = RequestMethod.GET)
	private ResponseEntity<String> insertMedicine(String medicineName) {
		if (hospitalOpsService.insertMedicineToDB(medicineName)) {
			return ResponseEntity.status(HttpStatus.OK).body("Medicine was inserted into the DB");
		}
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("System Unavailable");
	}

	@RequestMapping(value = "/admin/removeMedicine", method = RequestMethod.GET)
	private ResponseEntity<String> removeMedicine(String medicineName) {
		if (hospitalOpsService.removeMedicineFromDB(medicineName)) {
			return ResponseEntity.status(HttpStatus.OK).body("Medicine was Removed into the DB");
		}
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("System Unavailable");
	}

}
