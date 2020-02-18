package com.frac.FracAdvanced.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.frac.FracAdvanced.model.InjectionPlanModel;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.repository.InjectionPlanRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.SingleLayerInputRepo;
import com.frac.FracAdvanced.service.InjectionPlanService;
import com.frac.FracAdvanced.service.TreatmentDesignService;

/**
 * @author ShubhamGaur
 *
 */
@Controller
@RequestMapping(value="injectionPlan")
public class InjectionPlan {
	
	String map="view/injectionPlan";
	
	@Autowired
	private InjectionPlanRepo repo; 
	
	@Autowired
	private ProjectDetailRepo prodetails;
	
	@Autowired
	private InjectionPlanService injectionservice;
	
	@Autowired
	HttpSession httpsession;
	
	@Autowired
	SingleLayerInputRepo singlLR;
	
	@Autowired
	TreatmentDesignService treatmentDesignService;
	
	
	@RequestMapping("/selectFromOption")
	public String select(Model model,@RequestParam("pid") String pid) {
		
		Integer pid1= Integer.valueOf(pid);
		//treatmentDesignService.setValueOfInjectionPlanOptionAsAnalysis(pid1);
	    String	injectionplanTwoOption =singlLR.findByParamAndPid("InjectionPlan", prodetails.getOne(pid1)).get(0).getValue();
		model.addAttribute("pid",pid);
		model.addAttribute("injectionplanTwoOption",injectionplanTwoOption);
		return map+"/selectFromOptions";
		
	}
	
	@RequestMapping("/showinjection")
	public String show(Model model,@RequestParam("pid") Integer pid) {
		
		Integer pid1= Integer.valueOf(pid);
		 treatmentDesignService.setValueOfInjectionPlanOptionAsAnalysis(pid1);
	 
		if(injectionservice.showInjectionPlan(pid).isEmpty()) {
		model.addAttribute("pid",pid);
		return map+"/import";
		}
		else {
			model.addAttribute("pid",pid);
			model.addAttribute("list",injectionservice.showInjectionPlan(pid));
			return map+"/showlist";
		}
	}
	
	//save value from import page
	
	@RequestMapping("/saveinjection")
	public String saveinjection(Model model,@RequestParam("ip_imppid") Integer pid,
			@RequestParam("ip_impinput") List<String> input) {
		injectionservice.saveInjectionPlan(pid, input);
		model.addAttribute("pid", pid);
		model.addAttribute("list", injectionservice.showInjectionPlan(pid));
		return map+"/import";
	}
	
	@RequestMapping("/deleteinjection")
	public String deleteinjection(Model model,@RequestParam("pid") Integer pid,@RequestParam("id") Integer id) {
		injectionservice.deleteInjection(pid, id);
		return map+"/import";
	}
	
	@RequestMapping("/showlist")
	public String showlist(Model model,@RequestParam("ip_imppid") Integer pid,
			@RequestParam("ip_impinput") List<String> input) {
		injectionservice.saveInjectionPlan(pid, input);
		model.addAttribute("pid", pid);
		model.addAttribute("list", injectionservice.showInjectionPlan(pid));
		return map+"/showlist";
	}
	
	@RequestMapping("/showupdate")
	public String showupdte(Model model,@RequestParam("pid") Integer pid) {
		model.addAttribute("pid",pid);
		model.addAttribute("list",injectionservice.showInjectionPlan(pid));
		return map+"/update";
	}
	
	@RequestMapping("/deleteinjection2")
	public String deleteinjection2(Model model,@RequestParam("pid") Integer pid,@RequestParam("id") Integer id) {
		repo.deleteById(id);
		ProjectDetails details = prodetails.findById(pid).orElse(null);
		List<InjectionPlanModel> list= repo.findBydetails(details);
		model.addAttribute("pid",pid);
		model.addAttribute("list",list);
		return map+"/update";
	}
	
//	from update page to update values....
	@RequestMapping("/saveupdate")
	public String saveupdate(Model model,@RequestParam("ip_uppid") Integer pid,@RequestParam("id") String id,
			@RequestParam("ip_upinput") List<String> input) {
		injectionservice.SaveUpdate(pid, input,id);
		model.addAttribute("pid", pid);
		model.addAttribute("list", injectionservice.showInjectionPlan(pid));
		return map+"/showlist";
	}
	
	@RequestMapping(value = "/uploadfile",method = RequestMethod.POST)
	public String uploadFile(Model model,@RequestParam("pid") Integer pid,
			@RequestParam("ip_impfile") MultipartFile file) throws Exception {
		injectionservice.readFile(pid, file);
		model.addAttribute("pid", pid);
		model.addAttribute("list", injectionservice.showInjectionPlan(pid));
		return map+"/showlist";
	}
	
	@RequestMapping(value = "/delInjectionfield",method = RequestMethod.GET)
	public String uploadFile(@RequestParam("id") Integer id, Model model) throws Exception {
		injectionservice.deleteField(id);
		ProjectDetails details=(ProjectDetails)httpsession.getAttribute("ProjectDetail");
		int pid =details.getId();
		model.addAttribute("pid", pid);
		model.addAttribute("list", injectionservice.showInjectionPlan(pid));
		return map+"/update";
	}
		
	@RequestMapping(value = "/deleteIfieldimport",method = RequestMethod.GET)
	public String uploadFileimport(@RequestParam("id") Integer id, Model model) throws Exception {
		injectionservice.deleteField(id);
		ProjectDetails details=(ProjectDetails)httpsession.getAttribute("ProjectDetail");
		int pid =details.getId();
		model.addAttribute("pid", pid);
		model.addAttribute("list", injectionservice.showInjectionPlan(pid));
		return map+"/import";
	}
}
