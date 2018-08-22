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

	@RequestMapping(value = "/getAllMedicineInfo", produces = "application/json", method = RequestMethod.GET)
	private List<MedicineVO> getAllMedicineInfo() {
		return hospitalOpsService.getAllMedicineInfo();
	}

	@RequestMapping(value = "/getInfoForMedicine/{medicineName}", produces = "application/json", method = RequestMethod.GET)
	private MedicineVO getInfoOfMedicine(@PathVariable String medicineName) {
		return hospitalOpsService.getMedicineInfoForName(medicineName);
	}

	@RequestMapping(value = "/getInfoForMedicine/{disease}", produces = "application/json", method = RequestMethod.GET)
	private List<MedicineVO> getMedicineForDisease(@PathVariable String diseaseName) {
		return hospitalOpsService.getMedicineForDisease(diseaseName);
	}

	@RequestMapping(value = "/getInfoForMedicine/{symptoms}", produces = "application/json", method = RequestMethod.GET)
	private List<MedicineVO> getMedicineForSymptom(@PathVariable String symptom) {
		return hospitalOpsService.getMedicineForSymptom(symptom);
	}

	@RequestMapping(value = "/getTokenForAuth", produces = "application/json", method = RequestMethod.GET)
	private ResponseEntity<String> getAllMedicineInfo(HttpServletResponse httpServletResponse,
			@RequestParam("userId") String userId, @RequestParam("password") String password) {
		if (hospitalOpsService.validateUser(userId, password)) {
			httpServletResponse.addHeader(CommonConstants.AUTHROIZATION,
					CommonConstants.BEARER + JWTAuth.createJWT(String.valueOf(userId), "JWT Controller",
							"Issued for Auth with expiry of 8hrs", CommonConstants.EXPIRY_TIME_AUTH));
			return ResponseEntity.status(HttpStatus.OK).body(CommonConstants.JWT_AUTH_GEN_SUCCESS);
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(CommonConstants.JWT_AUTH_GEN_FAILURE);
	}

}
