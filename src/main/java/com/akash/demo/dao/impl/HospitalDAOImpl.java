package com.akash.demo.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import com.akash.demo.vo.Employee;
import com.akash.demo.vo.MedicineVO;

@Repository
public class HospitalDAOImpl {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public HospitalDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public boolean validateUser(String userId, String password) {
		String insq = "select * from employee where username = ? and password = ?";
		try {
			Employee employee = (Employee) jdbcTemplate.queryForObject(insq, new Object[] { userId, password },
					new BeanPropertyRowMapper(Employee.class));
			if (employee == null) {
				return false;
			}
		} catch (EmptyResultDataAccessException e) {
			return false;
		} catch (IncorrectResultSizeDataAccessException e1) {
			return false;
		}
		return true;
	}

	public List<MedicineVO> getAllMedicineInfo() {
		String insq = "select * from medicine";
		List<MedicineVO> list = jdbcTemplate.queryForList(insq, MedicineVO.class);
		return list;
	}

	public MedicineVO getMedicineInfoForName(String medicineName) {
		String insq = "select * from medicine where medicinename = ?";
		MedicineVO medicine = (MedicineVO) jdbcTemplate.queryForObject(insq, new Object[] { medicineName },
				new BeanPropertyRowMapper(MedicineVO.class));
		return medicine;
	}

	public List<MedicineVO> getMedicineForDisease(String diseaseName) {
		String insq = "select * from medicine where disease = ?";
		List<MedicineVO> list = jdbcTemplate.queryForList(insq, new Object[] { diseaseName }, MedicineVO.class);
		return list;
	}

	public List<MedicineVO> getMedicineForSymptom(String symptom) {
		String insq = "select * from medicine where synptom = ?";
		List<MedicineVO> list = jdbcTemplate.queryForList(insq, new Object[] { symptom }, MedicineVO.class);
		return list;
	}

	public boolean insertMedicineToDB(String medicineName) {
		String insq = "insert into medicine values(?,?,?,?,?)";
		try {
			jdbcTemplate.execute(insq, new PreparedStatementCallback<Boolean>() {
				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setString(1, medicineName);
					ps.setString(2, "");
					ps.setString(3, "");
					ps.setString(4, "");
					ps.setString(5, "");
					return ps.execute();
				}
			});
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean register(String userName, String password, String rOLE_USER) {
		if (checkUserExists(userName)) {
			return false;
		}
		String insq = "insert into employee values(?,?,?)";
		try {
			jdbcTemplate.execute(insq, new PreparedStatementCallback<Boolean>() {
				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setString(1, userName);
					ps.setString(2, password);
					ps.setString(3, rOLE_USER);
					return ps.execute();
				}
			});
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private boolean checkUserExists(String userName) {
		String insq = "select * from employee where username = ?";
		try {
			jdbcTemplate.queryForObject(insq, new Object[] { userName }, Employee.class);
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		return true;
	}

	public boolean checkRole(String username, String string) {
		return true;
	}

}
