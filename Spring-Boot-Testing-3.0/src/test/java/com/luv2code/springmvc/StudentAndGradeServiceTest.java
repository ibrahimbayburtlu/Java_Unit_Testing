package com.luv2code.springmvc;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.repository.HistoryGradeDao;
import com.luv2code.springmvc.repository.MathGradesDao;
import com.luv2code.springmvc.repository.ScienceGradesDao;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {


    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private MathGradesDao mathGradesDao;

    @Autowired
    private ScienceGradesDao scienceGradeDao;


    @Autowired
    private HistoryGradeDao historyGradeDao;

    @Autowired
    private JdbcTemplate jdbc;

    @BeforeEach
    public void setupDatabase(){
        jdbc.execute("insert into student(id,firstname,lastname,email_address)" + "values (1,'Eric','Roby','eric.roby@luv2code_school.com')");

        jdbc.execute("insert into math_grade(id,student_id,grade) values (1,1,100.0)");

        jdbc.execute("insert into  science_grade(id,student_id,grade) values (1,1,100.00)");

        jdbc.execute("insert into history_grade(id,student_id,grade) values (1,1,100.00)");
    }

    @Test
    public void isStudentNullCheck(){
        assertTrue(studentService.checkIfStudentIsNull(1));

        assertFalse(studentService.checkIfStudentIsNull(0));
    }

    @Test
    public void deleteStudentService(){
        Optional<CollegeStudent> deletedCollegeStudent = studentDao.findById(1);
        Optional<MathGrade> deleteMathGrade = mathGradesDao.findById(1);
        Optional<HistoryGrade> deletedHistoryGrade = historyGradeDao.findById(1);
        Optional<ScienceGrade> deletedScienceGrade = scienceGradeDao.findById(1);

        assertTrue(deletedCollegeStudent.isPresent(),"Return true");

        assertTrue(deleteMathGrade.isPresent());
        assertTrue(deletedHistoryGrade.isPresent());
        assertTrue(deletedScienceGrade.isPresent());

        studentService.deleteStudent(1);


        deletedCollegeStudent = studentDao.findById(1);
        deleteMathGrade = mathGradesDao.findById(1);
        deletedScienceGrade = scienceGradeDao.findById(1);
        deletedHistoryGrade = historyGradeDao.findById(1);

        assertFalse(deletedCollegeStudent.isPresent());
        assertFalse(deleteMathGrade.isPresent());
        assertFalse(deletedScienceGrade.isPresent());
        assertFalse(deletedHistoryGrade.isPresent());

    }

    @Sql("/insertData.sql")
    @Test
    public void getGradeBookService(){
        Iterable<CollegeStudent> iterableCollegeStudents = studentService.getGradeBook();

        List<CollegeStudent> collegeStudents = new ArrayList<>();

        for (CollegeStudent collegeStudent : iterableCollegeStudents){
            collegeStudents.add(collegeStudent);
        }

        assertEquals(5,collegeStudents.size());

    }

    @Test
    public void createStudentService(){

        studentService.createStudent("Chad","Darby","chad.darby@luv2code_school.com");

        CollegeStudent student = studentDao.findByEmailAddress("chad.darby@luv2code_school.com");

        assertEquals("chad.darby@luv2code_school.com",student.getEmailAddress(),"find by email");
    }

    @Test
    public void createGradeService(){
        // Create the grade
        assertTrue(studentService.createGrade(80.50,1,"math"));
        assertTrue(studentService.createGrade(80.50,1,"science"));
        assertTrue(studentService.createGrade(80.50, 1,"history"));

        // get all grades with studentId
        Iterable<MathGrade> mathGrades = mathGradesDao.findMathGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradeDao.findScienceGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradeDao.findHistoryGradeByStudentId(1);

        // Verify there is grades
        assertTrue(((Collection<MathGrade>) mathGrades).size() == 2,"Student has math grades");
        assertTrue(((Collection<ScienceGrade>) scienceGrades).size() == 2,"Student has science grades");
        assertTrue(((Collection<HistoryGrade>) historyGrades).size() == 2,"Student has history grades");

    }

    @Test
    public void createGradeServiceReturnFalse(){
        assertFalse(studentService.createGrade(105,1,"math"));
        assertFalse(studentService.createGrade(-5,1,"math"));
        assertFalse(studentService.createGrade(80.50,2,"math"));
        assertFalse(studentService.createGrade(80.50,1,"literature"));
    }

    @Test
    public void deleteGradeService(){
        assertEquals(1,studentService.deleteGrade(1,"math"),"returns student id after delete");

        assertEquals(1,studentService.deleteGrade(1,"science"),"returns student id after delete");

        assertEquals(1,studentService.deleteGrade(1,"history"),"returns student id after delete");

    }

    @Test
    public void deleteGradeServiceReturnStudentIdOfZero(){
        assertEquals(0,studentService.deleteGrade(0,"science"),"No student should have 0 id");

        assertEquals(0,studentService.deleteGrade(1,"literature"),"No student should have a literature class");
    }

    @Test
    public void studentInformation(){

        GradebookCollegeStudent gradebookCollegeStudent = studentService.studentInformation(1);

        assertNotNull(gradebookCollegeStudent);
        assertEquals(1,gradebookCollegeStudent.getId());
        assertEquals("Eric",gradebookCollegeStudent.getFirstname());
        assertEquals("Roby",gradebookCollegeStudent.getLastname());
        assertEquals("eric.roby@luv2code_school.com",gradebookCollegeStudent.getEmailAddress());
        assertTrue(gradebookCollegeStudent.getStudentGrades().getMathGradeResults().size() == 1);
        assertTrue(gradebookCollegeStudent.getStudentGrades().getScienceGradeResults().size() == 1);
        assertTrue(gradebookCollegeStudent.getStudentGrades().getHistoryGradeResults().size() == 1);


    }

    @Test
    public void studentInformationServiceReturnAll(){

        GradebookCollegeStudent gradebookCollegeStudent = studentService.studentInformation(0);

        assertNull(gradebookCollegeStudent);
    }

    @AfterEach
    public void setupAfterTransaction(){
        jdbc.execute("DELETE From student");

        jdbc.execute("DELETE  FROM math_grade");

        jdbc.execute("DELETE FROM science_grade");

        jdbc.execute("DELETE FROM history_grade");
    }

}
