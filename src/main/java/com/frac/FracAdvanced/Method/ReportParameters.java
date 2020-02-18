package com.frac.FracAdvanced.Method;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frac.FracAdvanced.model.InjectionPlanModel;
import com.frac.FracAdvanced.model.PhasingParamModel;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.ReportMakingModel;
import com.frac.FracAdvanced.model.ReportParamModel;
import com.frac.FracAdvanced.model.ReservoirFluidModel;
import com.frac.FracAdvanced.model.ReservoirLithologyModel;
import com.frac.FracAdvanced.model.SlurryScheduleModel;
import com.frac.FracAdvanced.repository.FluidLibraryRepo;
import com.frac.FracAdvanced.repository.InjectionPlanRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.ProppantRepo;
import com.frac.FracAdvanced.repository.ReservoirFluidRepo;
import com.frac.FracAdvanced.repository.ReservoirLithologyRepo;
import com.frac.FracAdvanced.repository.SingleLayerInputRepo;
import com.frac.FracAdvanced.repository.SlurrySheduleRepo;
import com.frac.FracAdvanced.repository.WellDataRepo;
import com.frac.FracAdvanced.repository.reportMakingModelRepo;
import com.frac.FracAdvanced.service.InjectionPlanService;
import com.frac.FracAdvanced.service.PhasingParamService;
import com.frac.FracAdvanced.service.ReportMakingService;
import com.frac.FracAdvanced.service.ReportParamService;

import groovy.lang.Singleton;

/**
 * @author ShubhamGaur
 *
 */
@Component
public class ReportParameters {

	private static DecimalFormat df2 = new DecimalFormat("#.######");
	@Autowired
	private ReportParamService paramService;
	@Autowired
	private PhasingParamService phasingParamService;
	@Autowired
	private InjectionPlanService planService;
	@Autowired
	private ReportMakingService makingService;
	@Autowired
	private ReservoirFluidRepo fluidRepo;
	@Autowired
	private ProjectDetailRepo detailRepo;
	@Autowired
	private InjectionPlanRepo planRepo;
	@Autowired
	private FluidLibraryRepo flr;
	@Autowired
	HttpSession httpSession;
	@Autowired
	reportMakingModelRepo makingModelRepo;
	@Autowired
	SingleLayerInputRepo sInputRepo;
	
	@Autowired
	ReservoirLithologyRepo lithologyRepo;
	
	@Autowired
	ProppantRepo proppantRepo;
	  
	@Autowired
	InjectionPlanRepo injectionPlanRepo;  
	@Autowired
	FluidLibraryRepo fluidLibraryRepo;
	
	@Autowired
	WellDataRepo wellDataRepo;
	
	@Autowired
	SlurrySheduleRepo slurrySheduleRepo;

	public void SaveParam(Integer pid) {
		paramService.saveReportParam(pid, Treatment_design_Output( pid));
		paramService.saveReportParam1(pid, Formulae2(getPhasingDefault(pid)));
		paramService.saveReportParam2(pid, calcFromInjection(pid));
		paramService.saveReportParam3(pid, FractureMaxValue(pid));

	}

	public Map<String, String> Treatment_design_Output(Integer pid) {
		
		
		ReservoirLithologyModel rl= lithologyRepo.findBydetails(detailRepo.getOne(pid)).get(0);
		
	String fluidInjectionRate=	fluidRepo.findByParamAndDetails("Fluid injection rate (bpm)", detailRepo.getOne(pid)).get(0).getValue();
	String Finalproppantconcentration=	fluidRepo.findByParamAndDetails("Final proppant concentration (ppg)", detailRepo.getOne(pid)).get(0).getValue();
	String Fractureheight=	fluidRepo.findByParamAndDetails("Fracture height	(ft)", detailRepo.getOne(pid)).get(0).getValue();
	String Fracturehalflength =	fluidRepo.findByParamAndDetails("Fracture half-length (ft)", detailRepo.getOne(pid)).get(0).getValue();
	
	
//	String PoppantDiameter =	proppantRepo.findByParamAndDetails ("Poppant Diameter", detailRepo.getOne(pid)).get(0).getValue();
//	String xx =	proppantRepo.findByParamAndDetails ("Fracture half-length ", detailRepo.getOne(pid)).get(0).getValue();
		
	
	//double q = 40;  // injection rate from injection plan
	///double PC = 3;// max proppant conc from injection plan	
  //double FracHL = 1000; // Half Length from injection plan
	//double FracH = 100;// Height from injection plan
		double q =	Double. valueOf(fluidInjectionRate);
		double PC =	Double. valueOf(Finalproppantconcentration);
		double FracHL =	Double. valueOf(Fracturehalflength);
		double FracH =	Double. valueOf(Fractureheight);
		
		System.out.println("fluidInjectionRate"+fluidInjectionRate);
		System.out.println("Finalproppantconcentration"+Finalproppantconcentration);
		System.out.println("Fractureheight"+Fractureheight);
		System.out.println("Fracturehalflength"+Fracturehalflength);

		double rp;// calculated
		double Af;
		double W;
		
		// double Cl = 0.002;// leak off coefficient rate from Reservoir Tab
		//double payZoneThickness = 70; // Pay zone thickness from Reservoir Tab
		//double V = 0.25;  // poission ratio from Reservoir Tab
		//double G = 1200000; //calc from youngs modulus from the resrvoir tab
		
		double Cl = 	Double. valueOf(rl.getLeakoff());
		double payZoneThickness =	Double. valueOf(rl.getPayThickness());
	    double V = Double. valueOf(rl.getPoisonRatio());
        double G =Double. valueOf(rl.getShearModulus());
        double k =Double. valueOf(rl.getPerm());
         

        System.out.println("Cl............."+Cl);
        System.out.println("payZoneThickness"+payZoneThickness);
        System.out.println("V........"+V);
        System.out.println("G................"+G);
        
        
        
		double PropP = 0.32;  // Proppant porosity from Proppant Properties tab
		 double PropDensity = 165;  //Proppant Diameter from Proppant Properties tab
		 double Proppdia = 0.142;
		 
		//double PropD =Double. valueOf(PoppantDiameter);
		 
		//double viscosity = 1.5;// Fluid injection tab
		
	String FluidTypeSelected=	fluidLibraryRepo.findByProId(pid).get(0).getFluidTypeSelected();
	String ViscosityString= fluidLibraryRepo.findByPidFLAndTypeAndParameter(detailRepo.getOne(pid), FluidTypeSelected, "viscosity").get(0).getValue();
	double viscosity =	Double. valueOf(ViscosityString);
	 System.out.println("viscosity................"+viscosity);
     
	
	
	
	    double Tpad = 0.0; //calc
		//double k = 0.0; // from reservoir data
	//	double re = 1490; // from well data
	//	double rw = 0.38; // from well data

	String rw1=	wellDataRepo.findByParameterAndPid("wellbore Radius (rw)", detailRepo.getOne(pid)).get(0).getValue();
	String re1=	wellDataRepo.findByParameterAndPid("Drainage Radius (re)", detailRepo.getOne(pid)).get(0).getValue();
		
 
    double rw=	Double.parseDouble(rw1);
    double re=	Double.parseDouble(re1);
		
		
		
		
		double tf = 0;
		double Vpad11 = 0;
		double Vpad1 = 0;
		double Tpad3 = 0;
		double ProppMixSch=0.0;
		double Vinject = 0;
		double Vfract;
		double Effect = 0.0;
		double Expon = 0;
		double Kl = 1.50;
		double Kf;
		double t = 2;
		double Kl1 = 0;
		double k12 = 00.00;
		double Vinj = 0.0;
		double Expo = 0.0;
		double PadTimeForSlurry = 0.0;

		rp = payZoneThickness / FracH;
		Af = 2 * FracHL * FracH;
		W = 0.3 * ((Math.pow(q * viscosity * (1 - V) * FracHL / G, 0.25)) * (Math.PI / 4) * (0.59));
		W = W / 12;
		df2.setRoundingMode(RoundingMode.DOWN);
		W = Double.parseDouble(df2.format(W));
		System.out.println("this is Feet in width" + W);
		// W=0.005098;

		for (int i = 0; i <= 50; i++) {

			double ft = (q * 5.615 * t) - (Af * W) - (2 * Af * rp * Kl * Cl * Math.pow(t, 0.5));
			df2.setRoundingMode(RoundingMode.DOWN);
			ft = Double.parseDouble(df2.format(ft));

			double ft1 = ((q * 5.615)) - ((0.5) * (Kl * Cl * W * rp) / (Math.pow(t, 0.5)));
			df2.setRoundingMode(RoundingMode.DOWN);
			ft1 = Double.parseDouble(df2.format(ft1));

			double ti = t - (ft / ft1);
			ti = Double.parseDouble(df2.format(ti));
			t = ti;
			Vinj = q * t * 42;
			df2.setRoundingMode(RoundingMode.DOWN);
			Vinj = Double.parseDouble(df2.format(Vinj));
			Vinject = Vinj;

			double Vfrac = Af * W * 7.28;
			Vfract = Vfrac;

			double Eff = Vfrac / Vinj;
			df2.setRoundingMode(RoundingMode.DOWN);
			Eff = Double.parseDouble(df2.format(Eff));
			Effect = Eff;

			double Expo1 = (1 - Eff);
			double Expo2 = (1 + Eff);
			Expo = Expo1 / Expo2;
			df2.setRoundingMode(RoundingMode.DOWN);
			Expo = Double.parseDouble(df2.format(Expo));
			double Vpad = (Vinj * Expo);
			df2.setRoundingMode(RoundingMode.DOWN);
			Vpad = Double.parseDouble(df2.format(Vpad));
			double r = (3.14) * (1 - Eff);
			double tt = (8 * (Eff) / 3);
			double Kl2 = (tt + r);
			Kl1 = Kl2 / 2;
			df2.setRoundingMode(RoundingMode.DOWN);
			Kl1 = Double.parseDouble(df2.format(Kl1));

			if (k12 == Kl1) {
				Kf = Kl1;
				System.out.println("Final Kl  is :" + Kf);
				tf = Math.round(ti);
			 	System.out.println("Final tf  is :" + tf);  // tf - time to use to make slurry shedule graph one value on x axis
				Vinj = q * t * 42;
				df2.setRoundingMode(RoundingMode.DOWN);
				Vinj = Double.parseDouble(df2.format(Vinj));
				Vinject = Vinj;
				System.out.println("Final Vinject  is :" + Vinj);
				Vfrac = Af * W * 7.28;
				Vfract = Vfrac;
				System.out.println("Final Vfrac  is :" + Vfrac);
				Eff = Vfrac / Vinj;
				df2.setRoundingMode(RoundingMode.DOWN);
				Eff = Double.parseDouble(df2.format(Eff));
				Effect = Eff;
				System.out.println("Final Eff  is :" + Effect);
				Expo1 = (1 - Eff);
				Expo2 = (1 + Eff);
				Expo = Expo1 / Expo2;
				df2.setRoundingMode(RoundingMode.DOWN);
				Expo = Double.parseDouble(df2.format(Expo));
				System.out.println("Final Expo  is :" + Expo);
				Expon = Expo;
				System.out.println("Final Expo  is :" + Expo);
				Vpad = (Vinj * Expo);
				df2.setRoundingMode(RoundingMode.DOWN);
				Vpad = Double.parseDouble(df2.format(Vpad));
				System.out.println("Final Vpad  is :" + Vpad);
				Vpad11 = Vpad;
				System.out.println("Final Tpad  is :" + Vpad11);

				break;
			} else {
				k12 = Kl1;
				if (i == 0) {
					Vpad1 = Vpad;
					//System.out.println("First Vpad  is :" + Vpad1);
					double Tpad1 = Vpad1 / 42;
					Tpad = Tpad1 / q;
					df2.setRoundingMode(RoundingMode.DOWN);
					Tpad = Double.parseDouble(df2.format(Tpad));
					Tpad3 = Tpad;
					PadTimeForSlurry=Tpad;
					System.out.println("First Tpad  is :" + Tpad3);
				}
				continue;

			}
		}
		
		
		slurrySheduleRepo.deleteBypid(detailRepo.getOne(pid));
		for (double ts =PadTimeForSlurry ; ts <= tf; ts += 1) {
			double tsp = ts - Tpad3;
			double tsp1 = tf - Tpad3;
			double prop = tsp / tsp1;
			double pexp = Math.pow(prop, Expon);
			ProppMixSch = PC * pexp;  // y axis - many values
			
			SlurryScheduleModel scheduleModel= new SlurryScheduleModel();
			scheduleModel.setTime(String.valueOf(ts));
			scheduleModel.setProppMixSch(String.valueOf(ProppMixSch));
			scheduleModel.setPid(detailRepo.getOne(pid));
			slurrySheduleRepo.save(scheduleModel);
			System.out.println("The proponent Mix Scheduled is :" + ProppMixSch);
		}

		double ConPop = (PC / (1 + Expon));
		double PropWt = ConPop * (Vinject - Vpad11);
		double CP = (PropWt / (2 * FracHL * FracH));
		double PropFrac = (CP / ((1 - PropP) * PropDensity));
		double PropFracW = 12 * PropFrac;

		 
		df2.setRoundingMode(RoundingMode.DOWN);
		ConPop = Double.parseDouble(df2.format(ConPop));
		PropWt = Double.parseDouble(df2.format(PropWt));
		CP = Double.parseDouble(df2.format(CP));
		PropFrac = Double.parseDouble(df2.format(PropFrac));
		PropFracW = Double.parseDouble(df2.format(PropFracW));

		Map<String, String> map = new LinkedHashMap<>();
		

		
		map.put("Final Proppant Concentration (ppg)", Double.toString(ConPop));
		map.put("Proppant weight (lb/ft3)", Double.toString(PropWt));
		map.put("Propped Fracture Width(in)", Double.toString(PropFracW));
		map.put("Pumping Time(min)", Double.toString(tf));
		map.put("Fracture Area  (ft2)", Double.toString(Af));
		map.put("Openning time distribution factor", Double.toString(Kl1));
		map.put("Average Fracture Width (in)", Double.toString(W));
		map.put("Fluid injection rate", Double.toString(q));
		map.put("Injected Volume(gal)", Double.toString(Vinj));
		map.put("Fluid Efficiency", Double.toString(Effect));
		map.put("Nolte Exponent", Double.toString(Expo));
		map.put("Pad Time (min)", Double.toString(Tpad));
		map.put("Pad Volume(gal)", Double.toString(Vpad11));
		map.put("Final Proppant Concentration (ppg)", Double.toString(PC));
		map.put("Fracture volume (gal)", Double.toString(PC));
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		ProjectDetails details=	(ProjectDetails)httpSession.getAttribute("ProjectDetail");
  	    String unitType=details.getUnitType();
  	  if(unitType.equalsIgnoreCase("Field"))
		  { rw=rw*0.3;
		  re=re*0.3; //A=A*4046.86;
		 FracH=FracH*0.3048; G=G*6894.76; viscosity=viscosity/1000;
		 Proppdia=Proppdia*0.393701;
		  }
  	  
  	  
		String isTreatmentAnalysis=	sInputRepo.findByParamAndPid("InjectionPlan", detailRepo.getOne(pid)).get(0).getValue();

		// The Analysis part will work or not will deside here.....
		if(isTreatmentAnalysis.equalsIgnoreCase("TreatmentDesign")) {	
			
		// calculating 4 grah data

			//System.out.println("tf  in min............................"+tf);
			
			List<ReportMakingModel> reportmakingmodelList=makingModelRepo.findBypid(detailRepo.getOne(pid));
			if(reportmakingmodelList.size()>0)
			{makingModelRepo.deleteBypid(detailRepo.getOne(pid));
			makingModelRepo.flush(); }
			
			for(double td=0.5; td<=tf*60; td++)
			
		
			{
				System.out.println("TreatmentDesign");
				 ///Half Length Calc........................
			double LT1 = (q * (Math.pow(td, 0.5))) / ((3.14) * Cl * FracH);	
			
			//System.out.println("Length  in meter"+LT1);
			
			double	LT = (LT1) / 2;/// meter
	       // System.out.println("half Len  in meter"+LT);
			
			LT = LT * 3.28;// feet
			//System.out.println("half Len  in ft"+LT);
			
           
		
			// Width Calc..........
			
            W = 4 * (Math.pow(((2 * (1 - V) * viscosity * (Math.pow(q, 2))) / ((Math.pow(Math.PI, 3)) * G * Cl * FracH)), 0.25))
					* (Math.pow(td, 0.125)); /// width in meter
            //System.out.println("W before in meter"+W);
			W = W * 39.37;/// inch
			//System.out.println("W after in inch"+W);
			double wc = W * 0.0833;
			
			double pnet =  2.5 * (Math
					.pow(((Math.pow(G, 4) * viscosity * Math.pow(q, 2)) / (Math.pow((1 - V), 4) * (Math.pow(FracH, 6)))), 0.2))
					* Math.pow(td, 0.2);// pascal
			
			double	fy=0.32; ////dimensionless no units 
			double	dp=0.142;////cm unit

			double kf1 = ((Math.pow(Proppdia, 2) * (Math.pow(PropP, 3))) / (150 * (Math.pow((1 - PropP), 2))));
			double kf2 = kf1 * 101325000000.0;/// md
			
			double kf = kf2 * (wc);// md-feet
			//double k1 = Double.parseDouble(k);
			
			/////FCD Calc........
			double fcd = (kf2 * wc) / (k * LT);
					
				 	 

			// if else conditions for foi
			Double s = 0.0;
         	double foi = (Math.log10(re / rw)) / ((Math.log10(re / rw)) + (k*LT));
			
		/////change unit from metric to field before storing into database .................
         				 
					  if(unitType.equalsIgnoreCase("Field")) {
						  LT=LT*3.2;
						  W=W*0.39;
					  kf=kf*101325000000.0;
					  pnet=pnet*0.000145038; }
					 
				
						
			ReportMakingModel r1 = new ReportMakingModel();
			r1.setLT(LT);
			r1.setW(Double.toString(W));
			r1.setPid(detailRepo.getOne(pid));
			r1.setTime(Double.toString(td/60));
			r1.setConductivity(Double.toString(fcd));
			r1.setPnet(Double.toString(pnet));
			makingModelRepo.save(r1);

		}
			
			
			
		} 

		return map;
	}

	public List<String> getPhasingDefault(Integer pid) {
		List<PhasingParamModel> list1 = phasingParamService.showPhasing(pid);
		List<String> list = new ArrayList<String>();
		Double angle = 180.0; // angle is User Input
		PhasingParamModel model = new PhasingParamModel();
		for (int i = 0; i < list1.size(); i++) {
			if (Double.parseDouble(list1.get(i).getAngle()) == angle) {
				model = list1.get(i);
			}
		}
		list.add(angle.toString());
		list.add(model.getAlpha());
		list.add(model.getA1());
		list.add(model.getA2());
		list.add(model.getB1());
		list.add(model.getB2());
		list.add(model.getC1());
		list.add(model.getC2());
		return list;
	}

	// This method does not make any graph but used to make report - two parameters
	// of ==== FRACTURE GEOMETRY OUTPUTS
	// "Perforation Pressure Drop(psi) and Perforation Skin
	public Map<String, String> Formulae2(List<String> value) {

		Map<String, String> map = new LinkedHashMap<>();
		Double angle = 0.0, alpha = 0.0, a1 = 0.0, a2 = 0.0, b1 = 0.0, b2 = 0.0, c1 = 0.0, c2 = 0.0;

		int i = 0;
		if (value.size() > 0) {
			angle = Double.parseDouble(value.get(i++));
			alpha = Double.parseDouble(value.get(i++));
			a1 = Double.parseDouble(value.get(i++));
			a2 = Double.parseDouble(value.get(i++));
			b1 = Double.parseDouble(value.get(i++));
			b2 = Double.parseDouble(value.get(i++));
			c1 = Double.parseDouble(value.get(i++));
			c2 = Double.parseDouble(value.get(i));
		}

		Double perfSkin = 0.0, horizontalSkin = 0.0, verticalSkin = 0.0, dimenQuantity = 0.0;
		Double effectiveWellboreRad = 0.0, perfLen = 0.666667, wellboreRad = 0.328;
		Double dimenRad = 0.0, perfRad = 0.020833, a = 0.0, b = 0.0;
		Double dimenHeight = 0.0, perfHeight = 0.5, permRatio = 10.0;
		Double dimenWellboreRad = 0.0;
		Double perfFriction = 0.0, flowRate = 20.0, fluidDensity = 9.97464, perfNo = 30.0, perfDia = 0.375,
				dischargeCoeff = 0.0, visco = 99.79853;
		// Calculating Horizontal Skin

		if (angle == 0) {
			effectiveWellboreRad = perfLen / 4;
		} else {
			effectiveWellboreRad = alpha * (wellboreRad + perfLen);
		}
		horizontalSkin = Math.log(wellboreRad / effectiveWellboreRad);
		// Calculating Vertical Skin
		dimenRad = (perfRad * (1 + Math.sqrt((1 / permRatio)))) / (2 * perfHeight);
		a = (a1 * Math.log10(dimenRad)) + a2;
		b = (b1 * dimenRad) + b2;
		dimenHeight = (perfHeight / perfLen) * (Math.sqrt(permRatio));
		verticalSkin = (Math.pow(10, a)) * (Math.pow(dimenHeight, b - 1)) * (Math.pow(dimenRad, b));
		// Calculating Dimensionless Quantity
		dimenWellboreRad = wellboreRad / (perfLen + wellboreRad);
		dimenQuantity = c1 * (Math.exp(c2 * dimenWellboreRad));
		// Calculating Perforation Skin
		perfSkin = horizontalSkin + verticalSkin + dimenQuantity;
		map.put("Perforation Skin", perfSkin.toString());
		dischargeCoeff = Math.pow((1 - (Math.exp((-2.2 * perfDia) / (Math.pow(visco, 0.1))))), 0.4);
		perfFriction = (0.2369 * Math.pow(flowRate, 2) * fluidDensity)
				/ (Math.pow(perfNo, 2) * Math.pow(perfDia, 4) * Math.pow(dischargeCoeff, 2));
		map.put("Perforation Pressure Drop(psi)", perfFriction.toString());
		return map;
	}

	// to change the injection plan button select fluid type - it is not about any
	// graph but its four values are shown in report -
	// ------FRACTURE GEOMETRY OUTPUTS----

	// Total Pad Volume(bbl) 0.0
//	Total Gel Volume(bbl) 0.0
//	Total Cross link Volume(bbl) 
	// Total Injecon Volume(bbl) NaN
// we need this
	public Map<String, String> calcFromInjection(Integer pid) {
		List<InjectionPlanModel> list = planService.showInjectionPlan(pid);
		Double totGel = 0.0, totPad = 0.0, totCrossLink = 0.0, maxppg = 0.0;
		Double avgppg = 0.0;
		Map<String, String> map = new LinkedHashMap<>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getFluidtype().equalsIgnoreCase("cross link")) {
				totCrossLink += Double.parseDouble(list.get(i).getCleanvol());
				if (list.get(i).getStagetype().equalsIgnoreCase("pad")) {
					totPad += Double.parseDouble(list.get(i).getCleanvol());
				}
			} else if (list.get(i).getFluidtype().equalsIgnoreCase("linear gel")) {
				totGel += Double.parseDouble(list.get(i).getCleanvol());
				if (list.get(i).getStagetype().equalsIgnoreCase("pad")) {
					totPad += Double.parseDouble(list.get(i).getCleanvol());
				}
			} else if (list.get(i).getStagetype().equalsIgnoreCase("pad")) {
				totPad += Double.parseDouble(list.get(i).getCleanvol());
			}
			avgppg = (Double.parseDouble(list.get(i).getBegprop()) + Double.parseDouble(list.get(i).getEndprop()))
					/ 2.0;
			if (maxppg < avgppg) {
				maxppg = avgppg;
			}
		}
		map.put("Total Pad Volume(bbl)", totPad.toString());
		map.put("Total Gel Volume(bbl)", totGel.toString());
		map.put("Total Cross link Volume(bbl)", totCrossLink.toString());
		map.put("Max Proppant Concentration(ppg)", maxppg.toString());
		return map;
	}
	// it is not about any kind of graph - but its four values are shown in report -
	// FRACTURE GEOMETRY OUTPUTS---
	// Max Created Fracture Length() 0.0
//	Average Fracture Width(Inch) NaN
//	Net Pressure At End Of Pumping(psi) 0.0

	public Map<String, String> FractureMaxValue(Integer pid) {
		List<ReportMakingModel> list = makingService.getVlaueOfReport(pid);
		Double maxFracLen = 0.0, avgFracWidth = 0.0, endNetPress = 0.0;
		Map<String, String> map = new LinkedHashMap<>();
		if (list.size() > 0) {
			maxFracLen = list.get(list.size() - 1).getLT();
			endNetPress = Double.parseDouble(list.get(list.size() - 1).getPnet());
		}
		Double widthSum = 0.0;
		for (int i = 0; i < list.size(); i++) {
			widthSum += Double.parseDouble(list.get(i).getW());
		}
		avgFracWidth = widthSum / list.size();
		map.put("Max Created Fracture Length(ft)", maxFracLen.toString());
		map.put("Average Fracture Width(Inch)", avgFracWidth.toString());
		map.put("Net Pressure At End Of Pumping(psi)", endNetPress.toString());
		return map;
	}

}
