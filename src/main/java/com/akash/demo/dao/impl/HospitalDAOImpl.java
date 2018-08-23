package com.akash.demo.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
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
		List<MedicineVO> list = jdbcTemplate.query(insq, new RowMapper<MedicineVO>() {
			@Override
			public MedicineVO mapRow(ResultSet rs, int rownumber) throws SQLException {
				MedicineVO e = new MedicineVO();
				e.setMedicineName(rs.getString(1));
				e.setDisease(rs.getString(2));
				e.setSymptomsOfDisease(rs.getString(3));
				e.setAfterOrBeforeFood(rs.getString(4));
				e.setTiming(rs.getString(5));
				return e;
			}
		});
		return list;

	}

	public MedicineVO getMedicineInfoForName(String medicineName) {
		String insq = "select * from medicine where name = ?";
		MedicineVO medicineVO = null;
		try {
			medicineVO = (MedicineVO) jdbcTemplate.queryForObject(insq, new Object[] { medicineName },
					new RowMapper<MedicineVO>() {
						@Override
						public MedicineVO mapRow(ResultSet rs, int rownumber) throws SQLException {
							MedicineVO e = new MedicineVO();
							e.setMedicineName(rs.getString(1));
							e.setDisease(rs.getString(2));
							e.setSymptomsOfDisease(rs.getString(3));
							e.setAfterOrBeforeFood(rs.getString(4));
							e.setTiming(rs.getString(5));
							return e;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			return medicineVO;
		} catch (IncorrectResultSizeDataAccessException e1) {
			return medicineVO;
		}

		return medicineVO;
	}

	public List<MedicineVO> getMedicineForDisease(String diseaseName) {
		String insq = "select * from medicine where disease = ?";
		List<MedicineVO> list = jdbcTemplate.query(insq, new Object[] { diseaseName }, new RowMapper<MedicineVO>() {
			@Override
			public MedicineVO mapRow(ResultSet rs, int rownumber) throws SQLException {
				MedicineVO e = new MedicineVO();
				e.setMedicineName(rs.getString(1));
				e.setDisease(rs.getString(2));
				e.setSymptomsOfDisease(rs.getString(3));
				e.setAfterOrBeforeFood(rs.getString(4));
				e.setTiming(rs.getString(5));
				return e;
			}
		});
		return list;
	}

	public List<MedicineVO> getMedicineForsymptoms(String symptoms) {
		String insq = "select * from medicine where symptoms = ?";
		List<MedicineVO> list = jdbcTemplate.query(insq, new Object[] { symptoms }, new RowMapper<MedicineVO>() {
			@Override
			public MedicineVO mapRow(ResultSet rs, int rownumber) throws SQLException {
				MedicineVO e = new MedicineVO();
				e.setMedicineName(rs.getString(1));
				e.setDisease(rs.getString(2));
				e.setSymptomsOfDisease(rs.getString(3));
				e.setAfterOrBeforeFood(rs.getString(4));
				e.setTiming(rs.getString(5));
				return e;
			}
		});
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
			jdbcTemplate.queryForObject(insq, new Object[] { userName }, new RowMapper<Employee>() {
				@Override
				public Employee mapRow(ResultSet rs, int rownumber) throws SQLException {
					Employee e = new Employee();
					e.setUsername(rs.getString(1));
					e.setPassword(rs.getString(2));
					e.setRole(rs.getString(3));
					return e;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		return true;
	}

	public boolean checkRole(String username, String string) {
		String sql = "select * from employee where username= ? and role=?";
		try {
			jdbcTemplate.queryForObject(sql, new Object[] { username, string }, new RowMapper<Employee>() {
				@Override
				public Employee mapRow(ResultSet rs, int rownumber) throws SQLException {
					Employee e = new Employee();
					e.setUsername(rs.getString(1));
					e.setPassword(rs.getString(2));
					e.setRole(rs.getString(3));
					return e;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		return true;
	}

	public boolean removeMedicineFromDB(String medicineName) {
		String sql = "delete from medicine where name = ?";
		try {
			jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {
				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setString(1, medicineName);
					return ps.execute();
				}
			});
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean updateMedicine(MedicineVO medicineVO) {
		String updateQuery = "update medicine set disease = ?,symptoms =?,bfrafr=?,timing=? where name=?";
		try {
			int rowCount = jdbcTemplate.update(updateQuery, medicineVO.getDisease(), medicineVO.getSymptomsOfDisease(),
					medicineVO.getAfterOrBeforeFood(), medicineVO.getTiming(), medicineVO.getMedicineName());
			if (rowCount == 0) {
				System.out.println(rowCount);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
