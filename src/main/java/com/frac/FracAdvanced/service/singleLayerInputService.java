package com.frac.FracAdvanced.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.SingleLayerInputRepo;

@Service("slinputService")
public class singleLayerInputService {

	
	@Autowired
	HttpSession httpSession;
	@Autowired
	SingleLayerInputRepo inputRepo;
	@Autowired
	ProjectDetailRepo detailRepo;
	
	public String getSalarySchedule()
	{
		System.out.println(httpSession.getAttribute("PDId"));
 int pid=	(Integer)httpSession.getAttribute("PDId");
 detailRepo.getOne(pid);
	String SalarySchedule=	inputRepo.findByParamAndPid("salary Schedule Graph", detailRepo.getOne(pid)).get(0).getValue();
		return SalarySchedule;
	}
	
	
	
	public String getInjectionPlanOption(Integer pid)
	{
		
  String	injectionplanTwoOption =inputRepo.findByParamAndPid("InjectionPlan", detailRepo.getOne(pid)).get(0).getValue();
		return injectionplanTwoOption;
	}
}
