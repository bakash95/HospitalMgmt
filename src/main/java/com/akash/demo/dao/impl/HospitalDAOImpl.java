package com.akash.demo.dao.impl;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(HospitalDAOImpl.class);

	private final JdbcTemplate jdbcTemplate;

	private final static String SECRET_KEY = "A331@262g2191n$c";

	@Autowired
	public HospitalDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public boolean validateUser(String userId, String password) {
		String encrypted = null;
		try {
			encrypted = HospitalDAOImpl.encryptPass(password);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException e2) {
			LOGGER.error(e2.getMessage(), e2);
		}
		String queryForUserPassValidation = "select * from employee where username = ? and password = ?";
		try {
			Employee employee = (Employee) jdbcTemplate.queryForObject(queryForUserPassValidation,
					new Object[] { userId, encrypted }, new BeanPropertyRowMapper(Employee.class));
			if (employee == null) {
				LOGGER.debug("Employee not valid..");
				return false;
			}
		} catch (EmptyResultDataAccessException e) {
			LOGGER.debug("Employee not valid..");
			LOGGER.error(e.getMessage(), e);
			return false;
		} catch (IncorrectResultSizeDataAccessException e1) {
			LOGGER.debug("Employee not valid..");
			LOGGER.error(e1.getMessage(), e1);
			return false;
		}
		LOGGER.debug("Employee is valid..proceeding with other process");
		return true;
	}

	public List<MedicineVO> getAllMedicineInfo() {
		String queryForAllMedicineInfo = "select * from medicine";
		List<MedicineVO> list = jdbcTemplate.query(queryForAllMedicineInfo, new RowMapper<MedicineVO>() {
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
		LOGGER.debug("List Of all Medicines {}", list);
		return list;

	}

	public MedicineVO getMedicineInfoForName(String medicineName) {
		String queryForMedicineInfoForName = "select * from medicine where name = ?";
		MedicineVO medicineVO = null;
		try {
			medicineVO = (MedicineVO) jdbcTemplate.queryForObject(queryForMedicineInfoForName,
					new Object[] { medicineName }, new RowMapper<MedicineVO>() {
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
			LOGGER.debug("Medicine is not present in DB");
			LOGGER.error(e.getMessage(), e);
			return medicineVO;
		} catch (IncorrectResultSizeDataAccessException e1) {
			LOGGER.debug("Medicine is not present in DB");
			LOGGER.error(e1.getMessage(), e1);
			return medicineVO;
		}
		LOGGER.debug("Medicine found {}", medicineVO);
		return medicineVO;
	}

	public List<MedicineVO> getMedicineForDisease(String diseaseName) {
		String queryForMedicineForDisease = "select * from medicine where disease = ?";
		List<MedicineVO> list = jdbcTemplate.query(queryForMedicineForDisease, new Object[] { diseaseName },
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
		LOGGER.debug("Medicines found based on Disease{}", list);
		return list;
	}

	public List<MedicineVO> getMedicineForsymptoms(String symptoms) {
		String queryForMedicineBySymptoms = "select * from medicine where symptoms = ?";
		List<MedicineVO> list = jdbcTemplate.query(queryForMedicineBySymptoms, new Object[] { symptoms },
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
		LOGGER.debug("Medicines found based on symptoms{}", list);
		return list;
	}

	public boolean insertMedicineToDB(String medicineName) {
		String queryForInsertMedicine = "insert into medicine values(?,?,?,?,?)";
		try {
			jdbcTemplate.execute(queryForInsertMedicine, new PreparedStatementCallback<Boolean>() {
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
			LOGGER.debug("Medicine was not inserted into DB");
			LOGGER.error(e.getMessage(), e);
			return false;
		}
		LOGGER.debug("Medicines was inserted into DB");
		return true;
	}

	public boolean register(String userName, String password, String rOLE_USER) {
		if (checkUserExists(userName)) {
			return false;
		}
		String queryForInsert = "insert into employee values(?,?,?)";
		try {
			jdbcTemplate.execute(queryForInsert, new PreparedStatementCallback<Boolean>() {
				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setString(1, userName);
					String encrypted = null;
					try {
						encrypted = HospitalDAOImpl.encryptPass(password);
					} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException
							| NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e1) {
						LOGGER.error(e1.getMessage(), e1);
					}
					if (encrypted != null) {
						ps.setString(2, encrypted);
					} else {
						ps.setString(2, password);
					}
					ps.setString(3, rOLE_USER);
					return ps.execute();
				}
			});
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	private boolean checkUserExists(String userName) {
		String queryForUserExistsCheck = "select * from employee where username = ?";
		try {
			jdbcTemplate.queryForObject(queryForUserExistsCheck, new Object[] { userName }, new RowMapper<Employee>() {
				@Override
				public Employee mapRow(ResultSet rs, int rownumber) throws SQLException {
					Employee e = new Employee();
					e.setUsername(rs.getString(1));
					String decrypted = null;
					try {
						decrypted = HospitalDAOImpl.decryptPass(rs.getString(2));
					} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException
							| NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e1) {
						LOGGER.error(e1.getMessage(), e1);
					}
					if (decrypted != null) {
						e.setPassword(rs.getString(2));
					} else {
						e.setPassword(rs.getString(2));
					}
					e.setRole(rs.getString(3));
					return e;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	public boolean checkRole(String username, String string) {
		String queryForRoleCheck = "select * from employee where username= ? and role=?";
		try {
			jdbcTemplate.queryForObject(queryForRoleCheck, new Object[] { username, string },
					new RowMapper<Employee>() {
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
			LOGGER.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	public boolean removeMedicineFromDB(String medicineName) {
		String queryForDeleteMedicine = "delete from medicine where name = ?";
		try {
			jdbcTemplate.execute(queryForDeleteMedicine, new PreparedStatementCallback<Boolean>() {
				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setString(1, medicineName);
					return ps.execute();
				}
			});
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
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
			LOGGER.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	private static String encryptPass(String password) throws NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("AES");
		Key key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encrypted = cipher.doFinal(password.getBytes());
		return new String(Base64.getEncoder().encode(encrypted));
	}

	private static String decryptPass(String encPass) throws NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("AES");
		Key key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encPass.getBytes()));
		return new String(decrypted);
	}

}
