package com.frac.FracAdvanced.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.frac.FracAdvanced.Method.CreateDefaultData;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.service.ChangeUnitService;
import com.frac.FracAdvanced.service.PhasingParamService;
import com.frac.FracAdvanced.service.ProjectDetailsService;

/**
 * To make report -  go to generatereport methos of Graph Controller

Run simulater controller call
Run simulater service - ReportParameter method is called by
ReportParameter method calculate and put data in ReportPAram model

2- this model has all data and data is exteacted ftom it using =GenerateReportService
 * @author Vishal kumar
 *
 */
@Controller
public class Application {
	
	/*
	 * @Autowired ChangeUnitService changeUnitService;
	 */
	private ChangeUnitService changeUnitService; 
	
	//////by type---- setter method 
	/*
	 * @Autowired(required = false) public void
	 * setChangeUnitService(ChangeUnitService changeUnitService) {
	 * this.changeUnitService= changeUnitService; }
	 */
	  ////// by name......constructor
	  @Autowired(required = false) 
	  public Application(ChangeUnitService aw)
	  {
		  this.changeUnitService=aw;
		  
	  }
	
	/*
	 * @Autowired(required = false) private void
	 * AutowiredController(ChangeUnitService changeUnitService) {
	 * this.changeUnitService=changeUnitService; }
	 */
	
	
	@Autowired
	ProjectDetailRepo repo;
	@Autowired
	CreateDefaultData defaultdata;
	@Autowired
	PhasingParamService phasingservice; 
	@Autowired
	ProjectDetailsService projectservice;
	@RequestMapping("/login")
	public String login(Model model) {
		return "view/login_logout/login";
	}
	
	@RequestMapping("/loginFail")
	public String loginFail(Model model) {
		model.addAttribute("fail","true");
		return "view/login_logout/login";
	}
	@RequestMapping("/logoutController")
	public String logout(Model model) {
		return "view/login_logout/logout";
	}
	
	@RequestMapping("/signupController")
	public String signup(Model model) {
		return "view/login_logout/Signup";
	}
		
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("list", repo.findAll());
		return "projectDetails/list";
	}
	
	@RequestMapping("/create")
	public String create() {
		return "projectDetails/create";
	}
	@RequestMapping("/showDetail")
	public String showProject(Model model) {
		ProjectDetails detail=new ProjectDetails();
		model.addAttribute("ProjectDetail", detail);
		return "projectDetails/projectDetail";
	}
	
	
	
	@RequestMapping("/accessNotAllowed")
	public String noAccess(Model model)
	{
		return "/view/login_logout/AccessDenied";
	}
	
	@RequestMapping("/detail")
	public String saveProject(Model model,@RequestParam Map<String,String> requestparams,
			RedirectAttributes attributes, HttpSession httpSession)throws Exception {
		
	
		/*
		 * httpSession.setAttribute("pressure", "0.0" );
		 * httpSession.setAttribute("temp", "0.0" );
		 * httpSession.setAttribute("stressAS","no" );
		 */
		
		ProjectDetails detail=new ProjectDetails();
		detail.setProjectName(requestparams.get("projectName"));
		detail.setWellName(requestparams.get("wellName"));
		detail.setCompanyName(requestparams.get("companyName"));
		detail.setUnitType(requestparams.get("unit"));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		detail.setDateCreated(dtf.format(now).toString()+" IST");
		repo.save(detail);
		defaultdata.setDefault(detail.getId());
		defaultdata.saveWellData(detail.getId());
		defaultdata.saveFluidLibraryDefault(detail.getId());			
		defaultdata.saveReservoirFluidDefault(detail.getId());
		defaultdata.saveSingleLayerMethod(detail.getId());
		phasingservice.savePhasingDefault(detail.getId());
		attributes.addFlashAttribute("ProjectDetail", detail);
		return "redirect:/list";
	}
	
	@RequestMapping("/showlist/{pid}")
	public String show(@PathVariable("pid") Integer pid,RedirectAttributes attributes) {
		attributes.addFlashAttribute("ProjectDetail", repo.findById(pid));
		return "redirect:/list";
	}
	
	@RequestMapping("/deleteproject/{pid}")
	public String delete(@PathVariable("pid") Integer pid) {
		repo.deleteById(pid);
		repo.flush();
		return "redirect:/";
	}
	
	@RequestMapping("/projdetailPrevbt")
	public String prevButton(){
		return "redirect:/";
	}
	@RequestMapping("/changeUnit2")
	public String changeunit12(@RequestParam("uType") String uType) throws Exception  {
		
		changeUnitService.changeReservoirFluidProperties(uType);
		changeUnitService.changeReservoirLithology(uType);
		changeUnitService.changeStressAnalysis(uType);
	    changeUnitService.changeFluidLibrary(uType);
	    changeUnitService.changeMiniFracUnit(uType);

		changeUnitService.convertUnit(uType);
		
 		return "redirect:/list";
	}
	@PostMapping("/searchbyname")
	@ResponseBody
	public List<ProjectDetails> searchByName(@RequestParam("name") String name,Model model) {
		model.addAttribute("list", projectservice.searchByProjectName(name));
		return projectservice.searchByProjectName(name);
	}
}