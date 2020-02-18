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
import com.frac.FracAdvanced.model.ReservoirFluidModel;
import com.frac.FracAdvanced.repository.InjectionPlanRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.SingleLayerInputRepo;
import com.frac.FracAdvanced.service.InjectionPlanService;
import com.frac.FracAdvanced.service.ReservoirFluidService;
import com.frac.FracAdvanced.service.TreatmentDesignService;

/**
 * @author ShubhamGaur
 *
 */
@Controller
public class TreatmentDesign {
	
	String map="view/injectionPlan";
	
	@Autowired
	private InjectionPlanRepo repo; 
	
	@Autowired
	private ProjectDetailRepo prodetails;
	
	@Autowired
	private InjectionPlanService injectionservice;
	
	@Autowired
	TreatmentDesignService treatmentDservice;
	
	@Autowired
	HttpSession httpsession;
	
	
	int su = 0;
	//String map = "view/reservoirfluid1";
	@Autowired
	ReservoirFluidService reservoirFluid;
	


	@RequestMapping("/showOptimumFracture")
	public String show1(Model model, @RequestParam("pid") Integer pid) throws Exception {
 		treatmentDservice.setValueOfInjectionPlanOption(pid);
		List<ReservoirFluidModel> b1 =	treatmentDservice.getListMethod(pid);
		ArrayList<String> rfplist=	reservoirFluid.RfParamList("Optimum Fracture Design Input", pid);
		su = su + 1;
		if (su > 1) {
			model.addAttribute("su", "su");
		}
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		model.addAttribute("rfplist", rfplist);
		return "/view/injectionPlan/Optimum Fracture Design/TreatmentDesignView";
	}
	
	@RequestMapping("/updateTDValueController")
	public String updateRF(@RequestParam("pid") Integer pid, 
			@RequestParam("value") List<String> value,
			@RequestParam("ftype1") List<String> ftype, 
			Model model) throws Exception
	{
		String typeNmae = ftype.get(0);
		ArrayList<String> rfplist=	reservoirFluid.RfParamList(typeNmae, pid);
		List<ReservoirFluidModel> b1 = reservoirFluid.methodEditValue(pid, typeNmae, value);
		model.addAttribute("rfplist", rfplist);
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		return "/view/injectionPlan/Optimum Fracture Design/TreatmentDesignView";

	}
	
	
}
