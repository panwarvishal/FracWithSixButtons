package com.frac.FracAdvanced.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frac.FracAdvanced.model.FluidLibraryModel;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.service.FluidLibraryService;

import io.micrometer.core.ipc.http.HttpSender.Method;

@Controller
public class FluidLibrary {
	@Autowired
	FluidLibraryService fls;
	@Autowired
	HttpSession httpsession;
	
	int pid;
	@RequestMapping("/showFLFirstController")
	public String showFL(
			           Model model) throws Exception
	{		
		 pid=(Integer)httpsession.getAttribute("PDId");
		List<FluidLibraryModel> b1=fls.method1("Linear Gel");	
		         ArrayList<String> fluidTypeList =fls.method2GetFluidType();
		         model.addAttribute("fluidTypeList", fluidTypeList);
		     	model.addAttribute("flUnitList", fls.fluidUnitList());
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);			
		return "/view/FluidLibrary/Show";
		
	}
	//buttonValue
	@RequestMapping("/showFLSecondController")
	public String newtonian(@RequestParam("pid") String pid,
			              @RequestParam("fluidT") String ftype,
			              @RequestParam("buttonValue") String buttonValue,
			              Model model) throws Exception
	{
		List<FluidLibraryModel> b1=fls.method1(ftype);	
		ArrayList<String> fluidTypeList =fls.method2GetFluidType();
        model.addAttribute("fluidTypeList", fluidTypeList);
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		model.addAttribute("buttonValue1", buttonValue);
		return "/view/FluidLibrary/Show";
		
	}
	
	
	@RequestMapping("/showFLThirdController")
	public String showFL3(@RequestParam("pid") String pid,		              
			              @RequestParam("parameter") List<String> parameter,
			              @RequestParam("value") List<String> value,
			              @RequestParam("ftype1") List<String> ftype,
			              Model model) throws Exception
	{
		String typeNmae=ftype.get(0);		
		List<FluidLibraryModel> b1=fls.methodEdit(pid,typeNmae,value, parameter);	
		ArrayList<String> fluidTypeList =fls.method2GetFluidType();
        model.addAttribute("fluidTypeList", fluidTypeList);
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		return "/view/FluidLibrary/Show";
		
	}
	
	
	
	@RequestMapping("/systemNewtonian")
	public String showFL4(@RequestParam("pid") String pid,		              
			              @RequestParam("parameter") List<String> parameter,
			              @RequestParam("value") List<String> value,
			              @RequestParam("ftype1") List<String> ftype,
			              Model model) throws Exception
	{
		String typeNmae=ftype.get(0);	
		List<FluidLibraryModel> b1=fls.methodEditNewtonian(pid,typeNmae,value, parameter);	
		ArrayList<String> fluidTypeList =fls.method2GetFluidType();
        model.addAttribute("fluidTypeList", fluidTypeList);
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		return "/view/FluidLibrary/Show";
		
	}
	
	
	@RequestMapping("/showFLFifthController")
	public String showFL5(		              
			              @RequestParam("k") float k,
			              @RequestParam("neta") float neta,
			              @RequestParam("gama") float gama,
			              @RequestParam("ftype1") String ftype,
			              Model model) throws Exception
	{
		List<FluidLibraryModel> b1=fls.methodCalculateViscosity(k,neta, gama,ftype);	
		ArrayList<String> fluidTypeList =fls.method2GetFluidType();
		
        model.addAttribute("fluidTypeList", fluidTypeList);
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		
		return "/view/FluidLibrary/show";
		
	}
	
	@RequestMapping("/showFLSixthController")
	public String showFL6(		              
		                  @RequestParam("parameter") List<String> parameter,
                          @RequestParam("valuev") List<String> value,
                          @RequestParam("ftypevalue") String ftype,
			              Model model) throws Exception
	{				
		List<FluidLibraryModel> b1=fls.userMethod(parameter,value,ftype);	
		ArrayList<String> fluidTypeList =fls.method2GetFluidType();
        model.addAttribute("fluidTypeList", fluidTypeList);
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		return "/view/FluidLibrary/Show";
		
	}
	
	
	@RequestMapping("/showFLseventhController")
	public String showFL7(	              		                 
                          @RequestParam("value") String value,
                          @RequestParam("ftype10") String ftype,
			              Model model) throws Exception
	{	
		List<FluidLibraryModel> b1=fls.userMethod2(value,ftype);	
		ArrayList<String> fluidTypeList =fls.method2GetFluidType();
        model.addAttribute("fluidTypeList", fluidTypeList);
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		return "/view/FluidLibrary/Show";
		
	}
	
	
	@RequestMapping("/showFLeighthController")
	public String showFL8(		              
			              @RequestParam("k") float k,
			              @RequestParam("neta") float neta,
			              @RequestParam("gama") float gama,
			              @RequestParam("ftype10") String ftype,
			              Model model) throws Exception
	{			
		List<FluidLibraryModel> b1=fls.methodCalculateViscosity(k,neta, gama,ftype);
		ArrayList<String> fluidTypeList =fls.method2GetFluidType();
        model.addAttribute("fluidTypeList", fluidTypeList);
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		return "/view/FluidLibrary/Show";
		
	}
	@RequestMapping("/removeFluidFromLibrary")
	public String showFL9(		              			              
			              @RequestParam("fluidT") String ftype,
			              Model model) throws Exception
	{					
		fls.methodremoveFluidFromLibrary(ftype);
		List<FluidLibraryModel> b1=fls.method1("Linear Gel");
		ArrayList<String> fluidTypeList =fls.method2GetFluidType();
        model.addAttribute("fluidTypeList", fluidTypeList);
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		return "/view/FluidLibrary/Show";
		
	}

 
}


