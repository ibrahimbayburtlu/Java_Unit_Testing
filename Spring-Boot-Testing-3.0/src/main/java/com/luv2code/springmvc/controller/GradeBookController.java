package com.luv2code.springmvc.controller;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.Gradebook;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradeBookController {

	@Autowired
	private Gradebook gradebook;

	@Autowired
	private StudentAndGradeService studentAndGradeService;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getStudents(Model m) {
		Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradeBook();
		m.addAttribute("students",collegeStudents);
		return "index";
	}


	@GetMapping("/studentInformation/{id}")
		public String studentInformation(@PathVariable int id, Model m) {
		return "studentInformation";
		}

}
