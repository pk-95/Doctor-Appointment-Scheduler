package com.externship.appointment.Appointment_storage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService implements CustomRepository,CustomTwo{
	@Autowired
	private JdbcTemplate jtm;
	
	public List<Appointment> findAllByEmail(String email) {
		String sql="select * from Appointment where email="+email;
		return jtm.query(sql, new BeanPropertyRowMapper<>(Appointment.class));
	}
	
	public List<Appointment> findByDocId(String DocId) {
		String sql="select * from Appointment where DocId="+DocId;
		return jtm.query(sql, new BeanPropertyRowMapper<>(Appointment.class));
	}
}
