package com.frac.FracAdvanced.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.ReportParamModel;
import com.frac.FracAdvanced.model.SlurryScheduleModel;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.ReportParamRepo;
import com.frac.FracAdvanced.repository.SlurrySheduleRepo;
/**
 * @author ShubhamGaur
 * vishal kumar modified on 17 feb added slurry graph report
 *
 */

import io.micrometer.core.ipc.http.HttpSender.Method;
@Component
public class GenerateReportService {

		@Autowired
		ProjectDetailRepo detailRepo;
		@Autowired
		ReportParamRepo paramRepo;
		
		@Autowired
		SlurrySheduleRepo slurrySheduleRepo;
		
		
		public Map<String,String> reportProjectData(Integer pid) {
			Map<String,String> map=new LinkedHashMap<>();
			ProjectDetails details=detailRepo.findById(pid).orElse(null);
			map.put("Project Name",details.getProjectName());
			map.put("Well Name",details.getWellName());
			map.put("Company Name",details.getCompanyName());
			map.put("Date Created",details.getDateCreated());
		return map;
		}
		
	public Map<String,String> fracGeomOutData(Integer pid) {
		Map<String,String> map=new LinkedHashMap<>();
		ProjectDetails details=detailRepo.findById(pid).orElse(null);
		List<ReportParamModel> list1=paramRepo.findBydetails(details);
		for(int i=0;i<list1.size();i++) {
			if         (list1.get(i).getParam().equalsIgnoreCase("Max Created Fracture Length(ft)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Average Fracture Width(Inch)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Propped Width(Inch)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Total Injection Volume(bbl)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Total Pad Volume(bbl)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Total Gel Volume(bbl)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Total Cross Link Volume(bbl)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Max Proppant Concentration(ppg)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Net Pressure At End Of Pumping(psi)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Perforation Skin")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Perforation Pressure Drop(psi)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			}
		}
	return map;
	}
	
	public Map<String,String> optimumDesignOutData(Integer pid) {
		Map<String,String> map=new LinkedHashMap<>();
		ProjectDetails details=detailRepo.findById(pid).orElse(null);
		List<ReportParamModel> list1=paramRepo.findBydetails(details);
		for(int i=0;i<list1.size();i++) {
			if (list1.get(i).getParam().equalsIgnoreCase("Final Proppant Concentration (ppg)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Proppant weight (lb/ft3)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Propped Fracture Width(in)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Pumping Time(min)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Fracture Area  (ft2)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Openning time distribution factor")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Average Fracture Width (in)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Fluid injection rate")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			}else if (list1.get(i).getParam().equalsIgnoreCase("Injected Volume(gal)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			}else if (list1.get(i).getParam().equalsIgnoreCase("Fluid Efficiency")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			}else if (list1.get(i).getParam().equalsIgnoreCase("Nolte Exponent")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			}else if (list1.get(i).getParam().equalsIgnoreCase("Fluid Efficiency")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			}else if (list1.get(i).getParam().equalsIgnoreCase("Pad Time (min)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			}else if (list1.get(i).getParam().equalsIgnoreCase("Pad Volume(gal)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			}else if (list1.get(i).getParam().equalsIgnoreCase("Final Proppant Concentration (ppg)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			}else if (list1.get(i).getParam().equalsIgnoreCase("Fracture volume (gal)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			}
		}
		
	return map;
	}
	
	public List<SlurryScheduleModel> slurrySheduleMethod(Integer pid) {
		List<SlurryScheduleModel> s= slurrySheduleRepo.findByPid(detailRepo.getOne(pid));
		
		return s;
		
	}
}
