package com.frac.FracAdvanced.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.frac.FracAdvanced.Method.InputFile;
import com.frac.FracAdvanced.model.MiniFracModel;
import com.frac.FracAdvanced.repository.MiniFracRepo;
import com.frac.FracAdvanced.service.MiniFracService;
/**
 * Author Vishal
 *
 */
@Controller
public class MiniFrac {
	
	String map="view/minifrac";
	
	@Autowired
	private MiniFracRepo fracRepo;
	@Autowired
	private MiniFracService fracservice; 
	
	@Autowired
	HttpSession httpsession;
	 
	
	@RequestMapping("/miniFrac")
	public String Show(Model model, @RequestParam("pro_Id") Integer pid) {
		List<MiniFracModel> list=fracRepo.findByProId(pid);
		if(list.isEmpty()) {
			model.addAttribute("hiddenId", pid);
			return map+"/import";
		}else
		{	
			model.addAttribute("list", list);
			model.addAttribute("hiddenId", pid);
			return map+"/showlist";
		}
	}
	
	@PostMapping("/upload")
	public String upload(Model model,@RequestParam("files") MultipartFile file,
			@RequestParam("pumptime") Double pumptime,
			@RequestParam("pid") int pid)throws Exception {
		List<MiniFracModel> minifracList = InputFile.process(file,pumptime,pid);
		model.addAttribute("list", minifracList);
		model.addAttribute("hiddenId", pid);
		return map+"/showlist";
	}
	
	
	@RequestMapping("/reverseToShowList")
	public String reverseToShowList(Model model,
			@RequestParam("pid") int pid)throws Exception {
		List<MiniFracModel> minifracList = fracservice.showfields(pid);
		model.addAttribute("list", minifracList);
		model.addAttribute("hiddenId", pid);
		return map+"/showlist";
	}
	
	
	  @RequestMapping("/importPage") public String importPage(Model model,
	  @RequestParam("pid") int pid)throws Exception { List<MiniFracModel>
	  minifracList = fracservice.showfields(pid); model.addAttribute("list",
	 minifracList); model.addAttribute("hiddenId", pid); return map+"/import"; }
	 
	
	@RequestMapping("/update")
	public String update(@RequestParam("pid") Integer pid,Model model) {
		List<MiniFracModel> list=fracRepo.findByProId(pid);
		model.addAttribute("list", list);
		model.addAttribute("hiddenId", pid);
		model.addAttribute("pumptime", fracservice.showfields(pid).get(0).getPumptime());
		return map+"/update";
	}
	
	
	
	
	
	////////////
	
	/*
	 * @RequestMapping("/save") public String save(Model model,@RequestParam("pid")
	 * Integer pid,@RequestParam("sa_imphiddenid") List<Integer> id,
	 * 
	 * @RequestParam("pumptime") String pumptime,@RequestParam("pressure")
	 * List<String> pressure,
	 * 
	 * @RequestParam("time") List<String> time){
	 * pid=(Integer)httpsession.getAttribute("PDId"); fracservice.updateValues(id,
	 * pressure, time, pumptime); return "redirect:/update"; }
	 */
	
	@RequestMapping("/save")
	public String save(Model model,
			@RequestParam("pid") Integer pid,
			@RequestParam("sa_imphiddenid") List<Integer> id,
			@RequestParam("pumptime") String pumptime,
			@RequestParam("pressure") List<String> pressure,
			@RequestParam("time") List<String> time,
			RedirectAttributes attributes){		
		attributes.addFlashAttribute("pid", pid);
		fracservice.updateValues(id, pressure, time, pumptime);
		List<MiniFracModel> list=fracRepo.findByProId(pid);
		model.addAttribute("list", list);
		model.addAttribute("hiddenId", pid);
		model.addAttribute("pumptime", fracservice.showfields(pid).get(0).getPumptime());
		return map+"/update";
	}
		
	@RequestMapping("/delete")
	public String delete(@RequestParam("id") Integer id,@RequestParam("pid") Integer pid,Model model) {
		fracRepo.deleteById(id);
		List<MiniFracModel> list=fracRepo.findByProId(pid);
			if(list.size()>0)
	{model.addAttribute("list", list);
	model.addAttribute("hiddenId", pid);
	model.addAttribute("pumptime", fracservice.showfields(pid).get(0).getPumptime());
	return map+"/update";}
		else
		{model.addAttribute("hiddenId", pid);
		return map+"/import";}
	}
	
	
	/////////////////
	

	

	
	@RequestMapping("/addfield")
	public String addfield(Model model,@RequestParam("pid") Integer pid,@RequestParam("mf_impinput") List<String> value) {
		fracservice.savefield(pid, value);
		model.addAttribute("hiddenId", pid);
		model.addAttribute("pumptime", fracservice.showfields(pid).get(0).getPumptime());
		model.addAttribute("list", fracservice.showfields(pid));
		return map+"/import";
	}
}
