package com.luv2code.springmvc.repository;

import com.luv2code.springmvc.models.HistoryGrade;
import com.luv2code.springmvc.models.ScienceGrade;
import org.springframework.data.repository.CrudRepository;

public interface ScienceGradesDao extends CrudRepository<ScienceGrade,Integer> {

    Iterable<ScienceGrade> findScienceGradeByStudentId(int id);
}
