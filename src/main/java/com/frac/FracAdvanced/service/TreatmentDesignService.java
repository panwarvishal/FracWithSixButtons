package com.frac.FracAdvanced.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.model.ReservoirFluidModel;
import com.frac.FracAdvanced.model.SingleLayerInputModel;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.ReservoirFluidRepo;
import com.frac.FracAdvanced.repository.SingleLayerInputRepo;
@Service
public class TreatmentDesignService {
	@Autowired
	ReservoirFluidRepo fluidRepo;
	@Autowired
	ProjectDetailRepo detailRepo;
	
	@Autowired
	SingleLayerInputRepo singleLIR;
	
	
	public java.util.List<ReservoirFluidModel> getListMethod(int pid)
			throws Exception {
		ArrayList<ReservoirFluidModel> a1 = new ArrayList<ReservoirFluidModel>();
		java.util.List<ReservoirFluidModel> x12 = fluidRepo.findByFluidtypeAndDetails("Optimum Fracture Design Input", detailRepo.getOne(pid));
		return x12;		
	}


	public void setValueOfInjectionPlanOption(Integer pid )
	{
		 
	SingleLayerInputModel s1=	singleLIR.findByParamAndPid("InjectionPlan", detailRepo.getOne(pid)).get(0);
	s1.setValue("TreatmentDesign");
	singleLIR.save(s1);
	}
	
	
	
	
	public void setValueOfInjectionPlanOptionAsAnalysis(Integer pid )
	{
	SingleLayerInputModel s2=	singleLIR.findByParamAndPid("InjectionPlan", detailRepo.getOne(pid)).get(0);
	s2.setValue("TreatmentAnalysis");
	singleLIR.save(s2);
	}
}
