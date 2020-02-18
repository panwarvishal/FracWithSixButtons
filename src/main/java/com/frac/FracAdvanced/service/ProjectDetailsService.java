/**
 * 
 */
package com.frac.FracAdvanced.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;

/**
 * @author ShubhamGaur
 *
 */
@Service
public class ProjectDetailsService {

	@Autowired
	ProjectDetailRepo detailRepo;
	
	public List<ProjectDetails> searchByProjectName(String name){
		if(name.equalsIgnoreCase("")) {
			return detailRepo.findAll();
		}else if(!name.equalsIgnoreCase("")){
		return detailRepo.searchByName(name);
		}
		return null;
	}
}
