package com.frac.FracAdvanced.controller;


import java.util.Map;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.frac.FracAdvanced.model.ProjectDetails;
import org.springframework.beans.factory.annotation.Autowired;
import com.frac.FracAdvanced.repository.MiniFracRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.SingleLayerInputRepo;
import com.frac.FracAdvanced.service.ReportParamService;
import com.frac.FracAdvanced.service.TreatmentDesignService;
import com.frac.FracAdvanced.service.singleLayerInputService;

/**
 * @author Vishal
 *
 */
@Controller
@SessionAttributes(value = {"ProjectDetail"})
public class Navinder {
	
	@Autowired
	MiniFracRepo service;
	@Autowired
	ReportParamService reportparamservice; 
	@Autowired
	ProjectDetailRepo prodetails;
	@Autowired
	singleLayerInputService sLService;
	
	@Autowired
	SingleLayerInputRepo singleLayerInputRepo;
	@Autowired
	TreatmentDesignService treatmentDesignService;
	
	@RequestMapping("/list")
	public String show(@ModelAttribute("ProjectDetail") ProjectDetails details,HttpSession session,Model model){
		session.setAttribute("ProjectDetail", details);
		session.setAttribute("PDId", details.getId());

		model.addAttribute("doneSim", reportparamservice.simulationDone(details.getId()));
		session.setAttribute("unitType", prodetails.findById(details.getId()).get().getUnitType());
		return "projectDetails/projectDetail";
	}
	
	@RequestMapping("/showgraphs")
	public String graphView(@ModelAttribute("ProjectDetail") ProjectDetails details,HttpSession session,Model model) {
		session.setAttribute("ProjectDetail", details);
		
		//Integer pid1= Integer.valueOf(pid);
		// treatmentDesignService.setValueOfInjectionPlanOptionAsAnalysis(details);
		 String	injectionplanTwoOption = singleLayerInputRepo.findByParamAndPid("InjectionPlan", details).get(0).getValue();
		
		 
			model.addAttribute("injectionplanTwoOption",injectionplanTwoOption);
		
		
		model.addAttribute("salarySchedule", sLService.getSalarySchedule());
		
		return "view/showgraph";
	}
	
	
	/*
	 * @RequestMapping("/ban") public String show(@ModelAttribute("kjl") int tdfa,
	 * RedirectAttributes ads ) { return "hajh";
	 * 
	 * }
	 */
	
	
	
}
