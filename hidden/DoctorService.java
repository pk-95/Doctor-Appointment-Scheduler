package com.externship.appointment.Doctor_storage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DoctorService implements DoctorRepository{
	@Autowired
	private JdbcTemplate jtm;
	
	@Override
	public List<Doctor> findAll(){
		String sql="Select * from Doctor";
		return jtm.query(sql, new BeanPropertyRowMapper<>(Doctor.class));
	}
	
	public List<Doctor> findById(String Id) {
		String sql="select * from Doctor where id="+Id;
		return jtm.query(sql, new BeanPropertyRowMapper<>(Doctor.class));
	}
	
}
