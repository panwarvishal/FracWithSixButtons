package com.frac.FracAdvanced.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.model.ReservoirLithologyModel;
import com.frac.FracAdvanced.model.SingleLayerInputModel;
import com.frac.FracAdvanced.repository.FluidLibraryRepo;
import com.frac.FracAdvanced.repository.InjectionPlanRepo;
import com.frac.FracAdvanced.repository.OutputWellForcastRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.ProppantRepo;
import com.frac.FracAdvanced.repository.ReservoirFluidRepo;
import com.frac.FracAdvanced.repository.ReservoirLithologyRepo;
import com.frac.FracAdvanced.repository.SingleLayerInputRepo;
import com.frac.FracAdvanced.repository.StressAnalysisRepo;
import com.frac.FracAdvanced.repository.reportMakingModelRepo;
@Service
public class AverageFfLithologyValues {
	
	@Autowired
	ReservoirFluidRepo rfr;
	@Autowired
	ReservoirFluidService reservoirFluidService;
	@Autowired
	ProjectDetailRepo pdr;
	@Autowired
	InjectionPlanRepo iplanr;
	@Autowired
	reportMakingModelRepo reportmmr;
	@Autowired
	FluidLibraryRepo fluidLR;
	@Autowired
	ProppantRepo propantr;
	@Autowired
	OutputWellForcastRepo outputWellForcastRepo;
	@Autowired
	ReservoirLithologyRepo lithologyRepo;
	@Autowired
	StressAnalysisRepo stressRepo;
	@Autowired
	HttpSession httpSession;
	@Autowired
	SingleLayerInputRepo singleLayerInputRepo;
	
	
	public void methodAverageVlauesForLithology1(int pid)
	{
		double permabilityAll=0.0;
		double poroAll=0.0;
		double paythickAll=0.0;
		double rPressureAll=0.0;
		double youngModulusAll=0.0;
		double pRatioAll=0.0;
		double leakAll=0.0;
		double spurtAll=0.0;
	
	   List<ReservoirLithologyModel> listLitho= lithologyRepo.findBydetails(pdr.getOne(pid));
	    int length= lithologyRepo.findBydetails(pdr.getOne(pid)).size();
	    int lithologyList=0;
	    for(  lithologyList=0; lithologyList<length;lithologyList++)
	    {
	       String permability=  listLitho.get(lithologyList).getPerm() ;
	       String poro=  listLitho.get(lithologyList). getPoro();
	       String paythick=  listLitho.get(lithologyList).getPayThickness();
	       String rPressure=  listLitho.get(lithologyList).getReservoirPressure();
	       String youngModulus=  listLitho.get(lithologyList). getYoungs();
	       String pRatio=  listLitho.get(lithologyList). getPoisonRatio();
	       String leak=  listLitho.get(lithologyList). getLeakoff();
	       String spurt=  listLitho.get(lithologyList). getSpurtLossCoefficient();
	       
	       double  permabilityint=Double.parseDouble(permability);
	       double  poroint=Double.parseDouble(poro);
	       double  paythickint=Double.parseDouble(paythick);
	       double  rPressureint=Double.parseDouble(rPressure);
	       double  youngModulusint=Double.parseDouble(youngModulus);
	       double  pRatioint=Double.parseDouble(pRatio);
	       double  leakint=Double.parseDouble(leak);
	       double  spurtint=Double.parseDouble(spurt);
	       
	       	       
	       permabilityAll= permabilityAll+permabilityint;
	       poroAll= poroAll+poroint;
	       paythickAll= paythickAll+paythickint;
	       rPressureAll= rPressureAll+rPressureint;
	       youngModulusAll= youngModulusAll+youngModulusint;
	       pRatioAll= pRatioAll+pRatioint;
	       leakAll= leakAll+leakint;
	       spurtAll= spurtAll+spurtint;
	       
	   //    System.out.println("permabilityAll"+pRatioAll); 	
	    }
	    
	  //  System.out.println("permabilityAll"+pRatioAll);
	  //  System.out.println("lithologyList"+lithologyList);
	    
	     permabilityAll =   permabilityAll/lithologyList;
	     poroAll =   poroAll/lithologyList;
	     paythickAll =   paythickAll/lithologyList;
	     rPressureAll =   rPressureAll/lithologyList;
	     youngModulusAll =   youngModulusAll/lithologyList;
	     pRatioAll =   pRatioAll/lithologyList;
	     leakAll =   leakAll/lithologyList;
	     spurtAll =   spurtAll/lithologyList;
	     
SingleLayerInputModel permabilityAllD= singleLayerInputRepo.findByParamAndPid("permabilityAll", pdr.getOne(pid)).get(0);
SingleLayerInputModel poroAllD= singleLayerInputRepo.findByParamAndPid("poroAll", pdr.getOne(pid)).get(0);
SingleLayerInputModel paythickAllD= singleLayerInputRepo.findByParamAndPid("paythickAll", pdr.getOne(pid)).get(0);
SingleLayerInputModel rPressureAllD= singleLayerInputRepo.findByParamAndPid("rPressureAll", pdr.getOne(pid)).get(0);
SingleLayerInputModel youngModulusAllD= singleLayerInputRepo.findByParamAndPid("youngModulusAll", pdr.getOne(pid)).get(0);
SingleLayerInputModel pRatioAllD= singleLayerInputRepo.findByParamAndPid("pRatioAll", pdr.getOne(pid)).get(0);
SingleLayerInputModel leakAllD= singleLayerInputRepo.findByParamAndPid("leakAll", pdr.getOne(pid)).get(0);
SingleLayerInputModel spurtAllD= singleLayerInputRepo.findByParamAndPid("spurtAll", pdr.getOne(pid)).get(0);

permabilityAllD.setValue(Double. toString(permabilityAll));  
poroAllD.setValue(Double. toString(poroAll));
paythickAllD.setValue(Double. toString(paythickAll));
rPressureAllD.setValue(Double. toString(rPressureAll));
youngModulusAllD.setValue(Double. toString(youngModulusAll));
pRatioAllD.setValue(Double. toString(pRatioAll));
leakAllD.setValue(Double. toString(leakAll));
spurtAllD.setValue(Double. toString(spurtAll));




singleLayerInputRepo.save(permabilityAllD);
singleLayerInputRepo.save(poroAllD);
singleLayerInputRepo.save(paythickAllD);
singleLayerInputRepo.save(rPressureAllD);
singleLayerInputRepo.save(youngModulusAllD);
singleLayerInputRepo.save(pRatioAllD);
singleLayerInputRepo.save(leakAllD);
singleLayerInputRepo.save(spurtAllD);

	}
}
