/**
 * @author Vishal Kumar
 *
 */
package com.frac.FracAdvanced.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.ReservoirFluidModel;
import com.frac.FracAdvanced.model.WellDataModel;
import com.frac.FracAdvanced.service.WellDataService;

@Controller
public class WellData {
	 @Autowired WellDataService service;
	
	
	/*
	 * WellDataService service;
	 * 
	 * @Required public WellDataService getService( ) {return service;} public void
	 * setGetService(WellDataService x) { this.service=x; }
	 */
	
	
	@Autowired
	HttpSession httpSession;
		@RequestMapping("/wellData")
		public String show(@RequestParam("pro_Id") String pid,
				           Model model) throws Exception
		{
		                   List<WellDataModel> list4=service.wellUpdateMethod1(pid);		                   			
		                   model.addAttribute("list",list4);
		                   model.addAttribute("pid",pid);		                 
		                   return "view/WellDataFolder/WellDataHtml23Jan";		                   
			               }						
		@RequestMapping("/updateWellData")
		public String updateRF(@RequestParam("pid") Integer pid, 
				@RequestParam("value") List<String> value,
				Model model) throws Exception
		{						
	 		 List<WellDataModel> list4=service.wellUpdateMethod(pid.toString(), value);
            model.addAttribute("list",list4);
            model.addAttribute("pid",pid);
            return "view/WellDataFolder/WellDataHtml23Jan";
		}
		
		
		
	/*
	 * @RequestMapping("/updateWellDatas") public String update(Model model,
	 * 
	 * @RequestParam("pid") String pid,
	 * 
	 * @RequestParam("name1") List<String> value,
	 * 
	 * @RequestParam("wellType") String wellType,
	 * 
	 * @RequestParam("completionType") String completionType) throws Exception {
	 * List<WellDataModel> list4=service.wellUpdateMethod(pid, completionType,
	 * wellType, value); model.addAttribute("list",list4);
	 * model.addAttribute("pid",pid);
	 * 
	 * ProjectDetails details=
	 * (ProjectDetails)httpSession.getAttribute("ProjectDetail");
	 * details.getUnitType(); if(details.getUnitType().equalsIgnoreCase("Metric"))
	 * {return "view/WellDataFolder/GetDataMetric";} else
	 * if(details.getUnitType().equalsIgnoreCase("Field")) { return
	 * "view/WellDataFolder/GetData";} return null; }
	 * 
	 * @RequestMapping("/updateWellData2") public String wellUpdate2(Model model,
	 * 
	 * @RequestParam("pid") String pid,
	 * 
	 * @RequestParam("name1") List<String> List4,
	 * 
	 * @RequestParam("wellType") String wellType,
	 * 
	 * @RequestParam("completionType") String completionType) throws Exception {
	 * List<WellDataModel> list5=service.wellUpdateMethod1(pid);
	 * model.addAttribute("list", list5); model.addAttribute("pid",pid);
	 * ProjectDetails details=
	 * (ProjectDetails)httpSession.getAttribute("ProjectDetail");
	 * details.getUnitType(); if(details.getUnitType().equalsIgnoreCase("Metric"))
	 * {return "view/WellDataFolder/wellDataShowMetric";} else
	 * if(details.getUnitType().equalsIgnoreCase("Field")) { return
	 * "view/WellDataFolder/wellDataShow";} return null; }
	 */
		
}
