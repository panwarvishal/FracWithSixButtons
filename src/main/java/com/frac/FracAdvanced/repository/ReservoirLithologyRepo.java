/**
 * 
 */
package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.ReservoirLithologyModel;

/**
 * @author Vishal
 *
 */
public interface ReservoirLithologyRepo extends JpaRepository<ReservoirLithologyModel, Integer> {
	@Query("select t from ReservoirLithologyModel t where t.details=:details order by t.id")
	List<ReservoirLithologyModel> findBydetails(ProjectDetails details);
	
	
	
	@Transactional
	@Modifying
	@Query("DELETE FROM ReservoirLithologyModel t where t.details = :details") 
    void deleteByDetail(@Param("details") ProjectDetails details);

	
}
