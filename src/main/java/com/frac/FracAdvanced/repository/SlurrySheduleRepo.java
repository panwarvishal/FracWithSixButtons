package com.frac.FracAdvanced.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.frac.FracAdvanced.model.OutputStressModel;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.SlurryScheduleModel;

public interface SlurrySheduleRepo extends JpaRepository<SlurryScheduleModel, Integer>  {
	@Query
    ("select t from SlurryScheduleModel t where t.pid=:detail order by t.id")
	List<SlurryScheduleModel> findByPid(ProjectDetails detail);
	
		
	
	
	@Transactional
	 List<SlurryScheduleModel> deleteBypid(ProjectDetails details);

}
