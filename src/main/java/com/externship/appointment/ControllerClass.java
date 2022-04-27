package com.externship.appointment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.externship.appointment.Appointment_storage.AppointmentDelete;
import com.externship.appointment.Appointment_storage.Appointment;
import com.externship.appointment.Appointment_storage.AppointmentRepository;
import com.externship.appointment.Doctor_storage.Doctor;
import com.externship.appointment.Doctor_storage.DoctorRepository;
import com.externship.appointment.Person_storage.Person;
import com.externship.appointment.Person_storage.PersonRepository;

@Controller
public class ControllerClass {
	
	int count=0;
	
	@Autowired
	PersonRepository personRepo;
	
	@Autowired
	DoctorRepository docRepo;
	
	@Autowired
	AppointmentRepository appRepo;
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@GetMapping("/registerdoc")
	public String registerdoc() {
		return "registerdoc";
	}

	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@PostMapping("/registered")
	public String registered(Person person) {
		personRepo.save(person);
		return "redirect:/";
	}

	@PostMapping("/registereddoc")
	public String registered(Doctor doctor) {
		docRepo.save(doctor);
		return "redirect:/";
	}
	
	@PostMapping("/authenticate")
	public String authenticate(Person person,HttpSession session) {
		if(personRepo.existsById(person.getEmail()) && personRepo.findById(person.getEmail()).get().getPassword().equals(person.getPassword())) {
			session.setAttribute("person", person.getEmail());
			return "redirect:/home";
			}
		return "redirect:/fail_login";
	}
	
	@PostMapping("/cancel")
	public String cancel(AppointmentDelete dApp) {
		appRepo.deleteById(dApp.getAppId());
		return "redirect:/userdetails";
	}
	
	@GetMapping("/home")
	public ModelAndView display(HttpSession session) {
		ModelAndView mav= new ModelAndView("fail_login");
		String email = null;
		
		
		if(session.getAttribute("person")!=null) {
			mav = new ModelAndView("home");
		email = (String) session.getAttribute("person");
		}
		
		mav.addObject("email",email);
		
		return mav;
		
		
	}
	
	@PostMapping("/assignment")
	public String submitted(Appointment app) {
		app.setAppId(count++);
		app.setStatus("Active");
		appRepo.save(app);
		
//		System.out.println(app.getEmail());
//		System.out.println(app.getDate());
//		System.out.println(docRepo.findById(app.getDocId()).get(0).getName());
		return "redirect:/docdetails";
	}
	
	@GetMapping("/docdetails")
	public ModelAndView DocDetails(HttpSession session) {
		
	    List<Doctor> doctors = new ArrayList<Doctor>();
		docRepo.findAll().forEach(doctors::add);
	    Map<String, Object> params = new HashMap<>();
	    
	    params.put("doctor", doctors);
	    params.put("email", session.getAttribute("person"));
	    
	    return new ModelAndView("doctorlist", params);
	}
	
	@GetMapping("/userdetails")
	public ModelAndView UserDetails(HttpSession session) {
		List<Appointment> apps = appRepo.findAllByEmail(session.getAttribute("person").toString());
		Map<String,Object> params = new HashMap<>();
		
		params.put("appointments", apps);
		params.put("email", session.getAttribute("person"));
		
		return new ModelAndView("appointed",params);
		
		
	}
	
	
	
	
}
