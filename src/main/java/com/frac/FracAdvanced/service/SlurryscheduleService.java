package com.frac.FracAdvanced.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.model.SlurryScheduleModel;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.SlurrySheduleRepo;

@Service
public class SlurryscheduleService {

	@Autowired
	SlurrySheduleRepo slurrySheduleRepo;
	@Autowired
 ProjectDetailRepo projectDetailRepo;
	
public List<SlurryScheduleModel>	slurryScheduleGraphMethod(Integer pid)
	{
List<SlurryScheduleModel> sl=	slurrySheduleRepo.findByPid(projectDetailRepo.getOne(pid));
	return sl;
	}

}
