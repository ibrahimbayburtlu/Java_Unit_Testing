package com.luv2code.springmvc.controller;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.Gradebook;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
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
		m.addAttribute("students", collegeStudents);
		return "index";
	}

	@PostMapping(value = "/")
	public String createStudent(@ModelAttribute("student") CollegeStudent student, Model m) {
		studentAndGradeService.createStudent(student.getFirstname(), student.getLastname(), student.getEmailAddress());

		Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradeBook();
		m.addAttribute("students", collegeStudents);
		return "index";
	}

	@GetMapping("delete/student/{id}")
	public String deleteStudent(@PathVariable int id, Model m) {

		if (!studentAndGradeService.checkIfStudentIsNull(id)) {
			return "error";
		}
		studentAndGradeService.deleteStudent(id);
		Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradeBook();
		m.addAttribute("students", collegeStudents);
		return "index";
	}


	@GetMapping("/studentInformation/{id}")
	public String studentInformation(@PathVariable int id, Model m) {

		if (!studentAndGradeService.checkIfStudentIsNull(id)) {
			return "error";
		}

		GradebookCollegeStudent studentEntity = studentAndGradeService.studentInformation(id);

		m.addAttribute("student", studentEntity);


		studentAndGradeService.configureStudentInformationModel(id,m);


		return "studentInformation";
	}

	@PostMapping(value = "/grades")
	public String createGrade(@RequestParam("grade") double grade,
							  @RequestParam("gradeType") String gradeType,
							  @RequestParam("studentId") int studentId,
							  Model m) {
		if (!studentAndGradeService.checkIfStudentIsNull(studentId)) {
			return "error";
		}

		boolean success = studentAndGradeService.createGrade(grade, studentId, gradeType);

		if (!success) {
			return "error";
		}

		studentAndGradeService.configureStudentInformationModel(studentId,m);

		return "studentInformation";
	}
}
