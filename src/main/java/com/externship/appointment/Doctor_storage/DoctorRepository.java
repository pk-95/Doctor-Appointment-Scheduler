package com.externship.appointment.Doctor_storage;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends CrudRepository<Doctor,String>{
	//List<Doctor> findAll();
	//Optional<Doctor> findById(String Id);

	
}
