package com.luv2code.springmvc.service;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {

    @Autowired
    private StudentDao studentDao;

    public void createStudent(String firstName,String lastName,String emailAddress){
        CollegeStudent student = new CollegeStudent(firstName,lastName,emailAddress);
        student.setId(0);
        studentDao.save(student);
    }
    // checkIfStudentIsNull

    public boolean checkIfStudentIsNull(int id){
        Optional<CollegeStudent> student = studentDao.findById(id);
        return student.isPresent();
    }

    public void deleteStudent(int id){
        Optional<CollegeStudent> student = studentDao.findById(id);
        if (student.isPresent()) {
            studentDao.deleteById(id);
        }
    }

    public Iterable<CollegeStudent> getGradeBook(){
        Iterable<CollegeStudent> collegeStudents = studentDao.findAll();
        return collegeStudents;
    }

}
