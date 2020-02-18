/**
 * 
 */
package com.frac.FracAdvanced.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.frac.FracAdvanced.model.ReservoirFluidModel;
import com.frac.FracAdvanced.service.ReservoirFluidService;

/**
 * @author ShubhamGaur
 *
 */
@Controller
@RequestMapping("/reservoirFluid1")
public class ReservoirFluid1 {

	int su = 0;
	String map = "view/reservoirfluid1";
	@Autowired
	ReservoirFluidService reservoirFluid;

	@RequestMapping("/showreservoir1")
	public String show1(Model model, @RequestParam("pid") Integer pid) throws Exception {
		List<ReservoirFluidModel> b1 = reservoirFluid.getListMethod(pid, "Reservoir properties", "Gas Well");
		ArrayList<String> rfplist=	reservoirFluid.RfParamList("Reservoir properties", pid);
		su = su + 1;
		if (su > 1) {
			model.addAttribute("su", "su");
		}
	
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		model.addAttribute("rfplist", rfplist);
		return "/view/reservoirFluid2/showlist";
	}

	@RequestMapping("/showRFSecondController")
	public String changeTypeOfFluid(@RequestParam("pid") Integer pid1, @RequestParam("fluidT") String ftype,
			@RequestParam("radioValue") String radioValue, Model model, HttpSession httpSession) throws Exception {
		List<ReservoirFluidModel> b1 = reservoirFluid.getListMethod(pid1, ftype, radioValue);
		ArrayList<String> rfplist=	reservoirFluid.RfParamList(ftype, pid1);

		model.addAttribute("pid", pid1);
		model.addAttribute("list", b1);
		model.addAttribute("rfplist", rfplist);
		httpSession.setAttribute("contollerFType", ftype);

		return "/view/reservoirFluid2/showlist";
	}

	@RequestMapping("/updateValueController")
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
		return "/view/reservoirFluid2/showlist";

	}
	@RequestMapping("/updateRadioWellTypeController")
	public String updateRadioWellData(@RequestParam("pid") Integer pid,
			@RequestParam("welltypeaq") String wellTypeSelected,
			RedirectAttributes redirectAttribute,
			//@RequestParam("fluidT") String ftype,
			Model model) throws Exception 
	{
		
		reservoirFluid.methodEditWellTypeSelected(pid, wellTypeSelected );
		List<ReservoirFluidModel> b1 = reservoirFluid.getListMethod(pid, "Reservoir properties", wellTypeSelected); // removed "Optimum
		ArrayList<String> rfplist=	reservoirFluid.RfParamList("Reservoir properties",pid);
																							// Fracture Design
																									// Input" from here
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		model.addAttribute("wellTypeSelected", wellTypeSelected);
		model.addAttribute("rfplist", rfplist);
		return "/view/reservoirFluid2/showlist";
	}

}
