package com.frac.FracAdvanced.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.metamodel.SetAttribute;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.SingleLayerInputModel;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.ReservoirLithologyRepo;
import com.frac.FracAdvanced.repository.SingleLayerInputRepo;
import com.frac.FracAdvanced.service.ReservoirLithologyService;

/**
 * @author Vishal Kumar
 *
 */
@Controller
@RequestMapping("/reservoirLithology")
public class ReservoirLithology {
	String map="view/reservoirLithology";
	
	List<Integer> numberOfRows;
	int numberRow;
	
	ReservoirLithologyService lithologyservice;
	@Autowired
    public void setReservoirLithologyService(ReservoirLithologyService x)
{
	this.lithologyservice=x;
}
	
	@Autowired
	ProjectDetailRepo projectDetailRepo ;
	@Autowired
	HttpSession session;
	@Autowired
	SingleLayerInputRepo singleLayerInputRepo;
	@Autowired
	ReservoirLithologyRepo lithologyRepo;
	 
	ProjectDetails detail;
	String bottomholePressure;
	String reservoirTemperature;
	String biotConstant;
	String tensileStress;
	String tectonicStress;
	String stressAnalysis;
	String FractureGrossHeight;
	
	@RequestMapping("/LithoFirstPageController")
	public String VeryFirstPage(Model model,@RequestParam("pid") Integer pid, HttpSession httpSession) {			
		if(lithologyservice.showList(pid).isEmpty()) {
			 detail = projectDetailRepo.findById(pid).orElse(null);
		     bottomholePressure=	singleLayerInputRepo.findByParamAndPid("Bottomhole Pressure", detail).get(0).getValue();
		     reservoirTemperature=	singleLayerInputRepo.findByParamAndPid("Reservoir Temperature", detail).get(0).getValue();
		     biotConstant=	singleLayerInputRepo.findByParamAndPid("Biot's Constant", detail).get(0).getValue();
		     tensileStress=	singleLayerInputRepo.findByParamAndPid("Tensile Stress", detail).get(0).getValue();
		     tectonicStress=	singleLayerInputRepo.findByParamAndPid("Tectonic Stress", detail).get(0).getValue();
		     stressAnalysis=	singleLayerInputRepo.findByParamAndPid("Stress Analysis", detail).get(0).getValue();
		     FractureGrossHeight=	singleLayerInputRepo.findByParamAndPid("Fracture Gross Height (md)", detail).get(0).getValue();
		    
		     model.addAttribute("biot", biotConstant);
			 model.addAttribute("tensile1", tensileStress);
			 model.addAttribute("tectonic1", tectonicStress);
			 model.addAttribute("pid", pid);
			 model.addAttribute("temp", reservoirTemperature);
			 model.addAttribute("pressure", bottomholePressure);
			 model.addAttribute("stressAnalysis", stressAnalysis);
			 model.addAttribute("FractureGrossHeight", FractureGrossHeight);
			return map+"/LithologyFirstPage";
			
	}else {
	    model.addAttribute("list", singleLayerInputRepo.findByPid(detail));
		model.addAttribute("pid", pid);
		model.addAttribute("temp", reservoirTemperature);
		model.addAttribute("pressure", bottomholePressure);
		model.addAttribute("stressAnalysis", stressAnalysis);
	    model.addAttribute("list", lithologyservice.showList(pid));
	    model.addAttribute("FractureGrossHeight", FractureGrossHeight);
	    //System.out.println(lithologyservice.showList(pid));
	return map+"/import";
}
		}
	
	
	@RequestMapping("/goToNextPage")
	public String goToNextPage(Model model,@RequestParam("pid") Integer pid, HttpSession httpSession) {	
		String pidString=pid.toString();
		model.addAttribute("pro_Id", pidString);
		return "wellData";
	}
 
	@RequestMapping("/alwaysFirstPageController")
	public String alwaysFirstPage(Model model,@RequestParam("pid") Integer pid, HttpSession httpSession) {			
		
			 detail = projectDetailRepo.findById(pid).orElse(null);
		         
		     bottomholePressure=	singleLayerInputRepo.findByParamAndPid("Bottomhole Pressure", detail).get(0).getValue();
		     reservoirTemperature=	singleLayerInputRepo.findByParamAndPid("Reservoir Temperature", detail).get(0).getValue();
		     biotConstant=	singleLayerInputRepo.findByParamAndPid("Biot's Constant", detail).get(0).getValue();
		     tensileStress=	singleLayerInputRepo.findByParamAndPid("Tensile Stress", detail).get(0).getValue();
		     tectonicStress=	singleLayerInputRepo.findByParamAndPid("Tectonic Stress", detail).get(0).getValue();
		     stressAnalysis=	singleLayerInputRepo.findByParamAndPid("Stress Analysis", detail).get(0).getValue();
		     FractureGrossHeight=	singleLayerInputRepo.findByParamAndPid("Fracture Gross Height (md)", detail).get(0).getValue();
			   
		     model.addAttribute("biot", biotConstant);
			 model.addAttribute("tensile1", tensileStress);
			 model.addAttribute("tectonic1", tectonicStress);
			 
			 model.addAttribute("pid", pid);
			 model.addAttribute("temp", reservoirTemperature);
			 model.addAttribute("pressure", bottomholePressure);
			 model.addAttribute("stressAnalysis", stressAnalysis);
			 model.addAttribute("FractureGrossHeight", FractureGrossHeight);
			return map+"/LithologyFirstPage";
			
	}
	
	@RequestMapping("/showLithology")
	public String show(Model model,@RequestParam("pid") Integer pid, HttpSession httpSession) {			
		
	//	if(lithologyservice.showList(pid).isEmpty()) {
			 detail = projectDetailRepo.findById(pid).orElse(null);
		     bottomholePressure=	singleLayerInputRepo.findByParamAndPid("Bottomhole Pressure", detail).get(0).getValue();
		     reservoirTemperature=	singleLayerInputRepo.findByParamAndPid("Reservoir Temperature", detail).get(0).getValue();
		     biotConstant=	singleLayerInputRepo.findByParamAndPid("Biot's Constant", detail).get(0).getValue();
		     tensileStress=	singleLayerInputRepo.findByParamAndPid("Tensile Stress", detail).get(0).getValue();
		     tectonicStress=	singleLayerInputRepo.findByParamAndPid("Tectonic Stress", detail).get(0).getValue();
		     stressAnalysis=	singleLayerInputRepo.findByParamAndPid("Stress Analysis", detail).get(0).getValue();
		     FractureGrossHeight=	singleLayerInputRepo.findByParamAndPid("Fracture Gross Height (md)", detail).get(0).getValue();
			 model.addAttribute("biot", biotConstant);
			 model.addAttribute("tensile1", tensileStress);
			 model.addAttribute("tectonic1", tectonicStress);
			 model.addAttribute("pid", pid);
			 model.addAttribute("temp", reservoirTemperature);
			 model.addAttribute("pressure", bottomholePressure);
			 model.addAttribute("stressAnalysis", stressAnalysis);
			 model.addAttribute("FractureGrossHeight", FractureGrossHeight);
			return map+"/import";
		/*}else {
			    model.addAttribute("list", singleLayerInputRepo.findByPid(detail));
				model.addAttribute("pid", pid);
				model.addAttribute("temp", reservoirTemperature);
				model.addAttribute("pressure", bottomholePressure);
				model.addAttribute("stressAnalysis", stressAnalysis);
			    model.addAttribute("list", lithologyservice.showList(pid));
			return map+"/showlist";
		}*/
	}
	
	
	//////////// controller for 5 single values...................
	@RequestMapping("/controller5value")
	public String BottomholePressure(Model model,@RequestParam("pid") Integer pid,
			@RequestParam("FractureGrossHeight1") String FractureGrossHeight1,
			@RequestParam("tectonicStress1") String tectonicStress1,
			@RequestParam("bottomholePressure1") String bottomholePressure1,
			@RequestParam("reservoirTemperature1") String reservoirTemperature1,
			@RequestParam("tensileStress1") String tensileStress1,
			@RequestParam("BiotConstant1") String BiotConstant1
				) {
		lithologyservice.setFiveValues(BiotConstant1, tensileStress1, reservoirTemperature1,
				bottomholePressure1, tectonicStress1,FractureGrossHeight1,pid);
	     stressAnalysis=	singleLayerInputRepo.findByParamAndPid("Stress Analysis", detail).get(0).getValue();
 	
		    model.addAttribute("pid", pid);
		    model.addAttribute("temp", reservoirTemperature1);
		    model.addAttribute("pressure", bottomholePressure1);
		    model.addAttribute("biot", BiotConstant1);
		    model.addAttribute("tensile1", tensileStress1);
		    model.addAttribute("tectonic1", tectonicStress1);
		    model.addAttribute("stressAnalysis", stressAnalysis);
		    model.addAttribute("list", lithologyservice.showList(pid));
		    model.addAttribute("FractureGrossHeight", FractureGrossHeight);
			return map+"/import";
		}
		
	/*
	 * @RequestMapping("/reservoir_Temperature") public String ReservoirTemperature
	 * (Model model,@RequestParam("pid") Integer pid, HttpSession
	 * httpSession,@RequestParam("temp") String temp) {
	 * 
	 * model.addAttribute("pid", pid); model.addAttribute("temp", temp);
	 * httpSession.setAttribute("temp", temp); int
	 * pressure=(Integer)httpSession.getAttribute("pressure");
	 * model.addAttribute("pressure", pressure); return map+"/import";
	 * 
	 * 
	 * }
	 */
	//////////////////////////////////////////		/////////////////////
	
	@RequestMapping("/rows")
	public String rows(Model model,@RequestParam("pid") Integer pid,@RequestParam("no") Integer number, RedirectAttributes attributes) 
	{
		numberOfRows=lithologyservice.showRows(number);
	 numberRow=	lithologyservice.showRows(number).get(lithologyservice.showRows(number).size()-1);
		attributes.addFlashAttribute("listOfRows", numberOfRows);
		attributes.addFlashAttribute("number", numberRow);
		attributes.addAttribute("pid", pid);
		return "redirect:/reservoirLithology/showLithology";
	}
	
	@RequestMapping("/savelithology")
	public String saveLithology(Model model,@RequestParam("pid") Integer pid,
			@RequestParam("rl_input") List<String> input) {
        ProjectDetails details=projectDetailRepo.findById(pid).orElse(null);
		lithologyRepo.deleteByDetail(details);
		lithologyservice.saveLithology(pid, input);
	   // stressAnalysis=	singleLayerInputRepo.findByParamAndPid("Stress Analysis", detail).get(0).getValue();
		model.addAttribute("stressAnalysis", stressAnalysis);
		model.addAttribute("pid", pid);
		model.addAttribute("list", lithologyservice.showList(pid));
		return map+"/import";
	}
	
	
	@RequestMapping("/addRow")
	public String addRow(Model model,@RequestParam("pid") Integer pid,
			@RequestParam("addRowName") String rowNumber) {
        ProjectDetails details=projectDetailRepo.findById(pid).orElse(null);
	    stressAnalysis=	singleLayerInputRepo.findByParamAndPid("Stress Analysis", detail).get(0).getValue();
		model.addAttribute("stressAnalysis", stressAnalysis);
		model.addAttribute("pid", pid);
		model.addAttribute("list", lithologyservice.showList(pid));
		return map+"/import";
	}
	
	@RequestMapping("/savelithology2Form")
	public String saveLithology2(Model model,@RequestParam("pid") Integer pid,
			@RequestParam("rl_input") List<String> input) {
		
		 detail = projectDetailRepo.findById(pid).orElse(null);
		 lithologyservice.saveLithology(pid, input);
	     stressAnalysis=	singleLayerInputRepo.findByParamAndPid("Stress Analysis", detail).get(0).getValue();
	     bottomholePressure=	singleLayerInputRepo.findByParamAndPid("Bottomhole Pressure", detail).get(0).getValue();
	     reservoirTemperature=	singleLayerInputRepo.findByParamAndPid("Reservoir Temperature", detail).get(0).getValue();
	     biotConstant=	singleLayerInputRepo.findByParamAndPid("Biot's Constant", detail).get(0).getValue();
	     tensileStress=	singleLayerInputRepo.findByParamAndPid("Tensile Stress", detail).get(0).getValue();
	     tectonicStress=	singleLayerInputRepo.findByParamAndPid("Tectonic Stress", detail).get(0).getValue();
	     stressAnalysis=	singleLayerInputRepo.findByParamAndPid("Stress Analysis", detail).get(0).getValue();
	     FractureGrossHeight=	singleLayerInputRepo.findByParamAndPid("Fracture Gross Height (md)", detail).get(0).getValue();
		   
	        model.addAttribute("biot", biotConstant);
		    model.addAttribute("tensile1", tensileStress);
		    model.addAttribute("tectonic1", tectonicStress);
		    model.addAttribute("stressAnalysis", stressAnalysis);
		    model.addAttribute("temp", reservoirTemperature);
		    model.addAttribute("pressure", bottomholePressure);
		    model.addAttribute("stressAnalysis", stressAnalysis);
		    model.addAttribute("pid", pid);
		    model.addAttribute("list", lithologyservice.showList(pid));
		    model.addAttribute("FractureGrossHeight", FractureGrossHeight);
		
		return map+"/import";
	}
	
	
	@RequestMapping("/showedit")
	public String showEdit(Model model,@RequestParam("pid") Integer pid) {
		 detail = projectDetailRepo.findById(pid).orElse(null);
	     stressAnalysis=	singleLayerInputRepo.findByParamAndPid("Stress Analysis", detail).get(0).getValue();

		model.addAttribute("pid", pid);
		model.addAttribute("list", lithologyservice.showList(pid));
		model.addAttribute("stressAnalysis", stressAnalysis);
		return map+"/edit";
	}
	
	@RequestMapping("saveupdate")
	public String saveUpdate(Model model,@RequestParam("pid") Integer pid,@RequestParam("rl_input") List<String> input) {
		lithologyservice.saveEdit(pid, input);
	     stressAnalysis=	singleLayerInputRepo.findByParamAndPid("Stress Analysis", detail).get(0).getValue();
			model.addAttribute("stressAnalysis", stressAnalysis);

		model.addAttribute("pid", pid);
		model.addAttribute("list", lithologyservice.showList(pid));
		return map+"/showlist";
	}
	
	@RequestMapping("/stressAController")
	public String stressA(Model model,
			@RequestParam("pid") Integer pid) {
		
	    SingleLayerInputModel stressAnalysis1=	singleLayerInputRepo.findByParamAndPid("Stress Analysis", detail).get(0);
	    
		
		if(  stressAnalysis1.getValue().equalsIgnoreCase("no"))
		{
			    stressAnalysis1.setValue("yes");
			    singleLayerInputRepo.save(stressAnalysis1);
				model.addAttribute("stressAnalysis", stressAnalysis1.getValue());
	}
		else
		{	   stressAnalysis1.setValue("no");
		        model.addAttribute("stressAnalysis", stressAnalysis1.getValue());
			    singleLayerInputRepo.save(stressAnalysis1);
	}		    
		 bottomholePressure=	singleLayerInputRepo.findByParamAndPid("Bottomhole Pressure", detail).get(0).getValue();
	     reservoirTemperature=	singleLayerInputRepo.findByParamAndPid("Reservoir Temperature", detail).get(0).getValue();
	     biotConstant=	singleLayerInputRepo.findByParamAndPid("Biot's Constant", detail).get(0).getValue();
	     tensileStress=	singleLayerInputRepo.findByParamAndPid("Tensile Stress", detail).get(0).getValue();
	     tectonicStress=	singleLayerInputRepo.findByParamAndPid("Tectonic Stress", detail).get(0).getValue();
	     stressAnalysis=	singleLayerInputRepo.findByParamAndPid("Stress Analysis", detail).get(0).getValue();
	     FractureGrossHeight=	singleLayerInputRepo.findByParamAndPid("Fracture Gross Height (md)", detail).get(0).getValue();
		   
	        model.addAttribute("biot", biotConstant);
		    model.addAttribute("tensile1", tensileStress);
		    model.addAttribute("tectonic1", tectonicStress);
		    model.addAttribute("stressAnalysis", stressAnalysis);
		    model.addAttribute("temp", reservoirTemperature);
		    model.addAttribute("pressure", bottomholePressure);
		    model.addAttribute("pid", pid);
		    model.addAttribute("FractureGrossHeight", FractureGrossHeight);
	    	return map+"/LithologyFirstPage";
	}
	
	@RequestMapping(value = "/uploadfile",method = RequestMethod.POST)
	public String uploadFileLithology(Model model, @RequestParam("sa_impfile") MultipartFile file,
			@RequestParam("pid") Integer pid, RedirectAttributes attributes) throws Exception {

		
		    lithologyservice.readFileLithology(pid, file);
		    bottomholePressure=	singleLayerInputRepo.findByParamAndPid("Bottomhole Pressure", detail).get(0).getValue();
		     reservoirTemperature=	singleLayerInputRepo.findByParamAndPid("Reservoir Temperature", detail).get(0).getValue();
		     biotConstant=	singleLayerInputRepo.findByParamAndPid("Biot's Constant", detail).get(0).getValue();
		     tensileStress=	singleLayerInputRepo.findByParamAndPid("Tensile Stress", detail).get(0).getValue();
		     tectonicStress=	singleLayerInputRepo.findByParamAndPid("Tectonic Stress", detail).get(0).getValue();
		     stressAnalysis=	singleLayerInputRepo.findByParamAndPid("Stress Analysis", detail).get(0).getValue();
		     FractureGrossHeight=	singleLayerInputRepo.findByParamAndPid("Fracture Gross Height (md)", detail).get(0).getValue();
			   
		     model.addAttribute("biot", biotConstant);
			    model.addAttribute("tensile1", tensileStress);
			    model.addAttribute("tectonic1", tectonicStress);
			    model.addAttribute("stressAnalysis", stressAnalysis);
			    model.addAttribute("temp", reservoirTemperature);
			    model.addAttribute("pressure", bottomholePressure);
			    model.addAttribute("pid", pid);
			model.addAttribute("list", lithologyservice.showList(pid));
			model.addAttribute("FractureGrossHeight", FractureGrossHeight);
			return map+"/import";
	}
	
	
	@RequestMapping(value = "/deleteController" )
	public String deleteController1(Model model,@RequestParam("id") Integer id,
			@RequestParam("pid") Integer pid ) throws Exception {

		lithologyRepo.deleteById(id);
		detail = projectDetailRepo.findById(pid).orElse(null);
		    stressAnalysis=	singleLayerInputRepo.findByParamAndPid("Stress Analysis", detail).get(0).getValue();
		      bottomholePressure=	singleLayerInputRepo.findByParamAndPid("Bottomhole Pressure", detail).get(0).getValue();
		     reservoirTemperature=	singleLayerInputRepo.findByParamAndPid("Reservoir Temperature", detail).get(0).getValue();
		     biotConstant=	singleLayerInputRepo.findByParamAndPid("Biot's Constant", detail).get(0).getValue();
		     tensileStress=	singleLayerInputRepo.findByParamAndPid("Tensile Stress", detail).get(0).getValue();
		     tectonicStress=	singleLayerInputRepo.findByParamAndPid("Tectonic Stress", detail).get(0).getValue();
		     stressAnalysis=	singleLayerInputRepo.findByParamAndPid("Stress Analysis", detail).get(0).getValue();
		     FractureGrossHeight=	singleLayerInputRepo.findByParamAndPid("Fracture Gross Height (md)", detail).get(0).getValue();
			    
		     model.addAttribute("biot", biotConstant);
			    model.addAttribute("tensile1", tensileStress);
			    model.addAttribute("tectonic1", tectonicStress);
			    model.addAttribute("stressAnalysis", stressAnalysis);
			    model.addAttribute("temp", reservoirTemperature);
			    model.addAttribute("pressure", bottomholePressure);
			    model.addAttribute("stressAnalysis", stressAnalysis);
			    model.addAttribute("pid", pid);
			    model.addAttribute("list", lithologyservice.showList(pid));
			    model.addAttribute("FractureGrossHeight", FractureGrossHeight);
			    return map+"/import";
	}
	
}
