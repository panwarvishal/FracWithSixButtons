package com.frac.FracAdvanced.service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.model.FluidLibraryModel;
import com.frac.FracAdvanced.model.InjectionPlanModel;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.ReportMakingModel;
import com.frac.FracAdvanced.model.ReservoirLithologyModel;
import com.frac.FracAdvanced.model.SingleLayerInputModel;
import com.frac.FracAdvanced.model.StressAnalysisModel;
import com.frac.FracAdvanced.repository.FluidLibraryRepo;
import com.frac.FracAdvanced.repository.InjectionPlanRepo;
import com.frac.FracAdvanced.repository.OutputWellForcastRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.ProppantRepo;
import com.frac.FracAdvanced.repository.ReservoirFluidRepo;
import com.frac.FracAdvanced.repository.ReservoirLithologyRepo;
import com.frac.FracAdvanced.repository.SingleLayerInputRepo;
import com.frac.FracAdvanced.repository.StressAnalysisRepo;
import com.frac.FracAdvanced.repository.WellDataRepo;
import com.frac.FracAdvanced.repository.reportMakingModelRepo;

/**
 * @author ShubhamGaur
 *
 */
@Service
public class ReportMakingService {

	@Autowired
	ReservoirFluidRepo rfr;
	@Autowired
	ReservoirFluidService reservoirFluidService;
	@Autowired
	ProjectDetailRepo pdr;
	@Autowired
	InjectionPlanRepo iplanr;
	@Autowired
	reportMakingModelRepo reportmmr;
	@Autowired
	FluidLibraryRepo fluidLR;
	@Autowired
	ProppantRepo propantr;
	@Autowired
	OutputWellForcastRepo outputWellForcastRepo;
	@Autowired
	ReservoirLithologyRepo lithologyRepo;
	@Autowired
	StressAnalysisRepo stressRepo;
	@Autowired
	HttpSession httpSession;
	
	@Autowired
	SingleLayerInputRepo sLayerInputRepo;
	
	@Autowired
	WellDataRepo  wellDataRepo;
	
	
	double permabilityAll;
	double leakoffceofficientAll;
	double sherModulusAll;
	double poissonRatioAll;
	  double  permabilityint;
	  String isTreatmentAnalysis;	
	  List<InjectionPlanModel> treatmentanalysis;
	ReportMakingModel m1 = new ReportMakingModel();
	public void calculate_WidthOfFluidLost(Integer pid) {
	}
	public Map<String, String> method_fractureWidthAndConductivity(int pid) {
		double LT = 0.0;
		double W = 0.0;
		String SS = "";
		 
		ArrayList<String> arraylist = new ArrayList<String>();
		Map<String, String> mapList = new HashMap<String, String>();
		Set<String> arraySet = new HashSet<String>();
	    isTreatmentAnalysis=	sLayerInputRepo.findByParamAndPid("InjectionPlan", pdr.getOne(pid)).get(0).getValue();
		// The Analysis part will work or not will deside here.....
		if(isTreatmentAnalysis.equalsIgnoreCase("TreatmentAnalysis")) {	
		  treatmentanalysis = iplanr.findBydetails(pdr.getOne(pid)); // Rate(BPM) and t
		if (true == treatmentanalysis.isEmpty()) {
			mapList.put("No value Entered In Injection Plan Button", "Injection Plan");
		}
		}
		
		
		
	   List<ReservoirLithologyModel>	lith=lithologyRepo.findBydetails(pdr.getOne(pid));
	   if (true == lith.isEmpty()) {
		mapList.put("No value Entered In Reservoir Lithology Button", "Reservoir Lithology");

	   }
		/*
		 * Dhanraj changed Effective radius to Drainage radius in the Well Data tab 24
		 * jan
		 */
	   String re1 = wellDataRepo.findByParameterAndPid("Drainage Radius (re)", pdr.getOne(pid)).get(0).getValue();
		if (re1 == null | re1.isEmpty() | re1.matches("^[a-zA-Z]*$")) {
			mapList.put("Drainage Radius (re) From well ", "WellData");

		}
		   String rw1 = wellDataRepo.findByParameterAndPid("wellbore Radius (rw)", pdr.getOne(pid)).get(0).getValue();
		if (("0.0".equalsIgnoreCase(rw1)) | rw1.isEmpty() | rw1.matches("^[a-zA-Z]*$")) {
			SS = SS + "  Well Bore Radius(feet) From Reservoir Fluid Properties,   ";
			mapList.put("Well Bore Radius(feet) From WellData", "WellData");
		}

      String h1=	sLayerInputRepo.findByParamAndPid("Fracture Gross Height (md)",pdr.getOne(pid)).get(0).getValue();
     	if ("0.0".equalsIgnoreCase(h1) | h1.isEmpty() | h1.matches("^[a-zA-Z]*$")) {
		mapList.put("Fracture Gross Height (md) From Reservoir Data", "Reservoir Lithology");
	}
     	
     	
  
	    	String u1 = fluidLR.findByProId(pid).get(0).getFluidTypeSelected();
	    	List<FluidLibraryModel> f12 = fluidLR.findByPidFLAndType(pdr.getOne(pid), u1);
	    	if (true == f12.isEmpty()) {
			mapList.put("The fluid is not selectted from Fluid Library", "Fluid Library");
		}
	    	String dp1 = propantr.findByParamAndDetails("Poppant Diameter", pdr.getOne(pid)).get(0).getValue();
	    	if (dp1 == null | dp1.isEmpty() | dp1.matches("^[a-zA-Z]*$")) {
			mapList.put("Poppant Diameter From Proppant Properties", "Poppant Properties");
		}

	    	String fy1 = propantr.findByParamAndDetails("Specific Gravity", pdr.getOne(pid)).get(0).getValue();
		    if (fy1 == null | fy1.isEmpty() | fy1.matches("^[a-zA-Z]*$")) {
			arraylist.add("Specific Gravity From Poppant Properties");
			arraySet.add("Poppant Properties");
			mapList.put("Specific Gravity From Proppant Properties", "Poppant Properties");
		}
		if (mapList.size() > 0) {
			return mapList;
		}
	//////////////	get data from lithology tab and get theeir average
	    List<ReservoirLithologyModel> listLitho= lithologyRepo.findBydetails(pdr.getOne(pid));
	    int length= lithologyRepo.findBydetails(pdr.getOne(pid)).size();
	    int lithologyList=0;
	    double leakoffceofficientint=0;
	    double   sherModulusint=0;
	    double poissonRatioint=0;
	    
	    for(  lithologyList=0; lithologyList<length;lithologyList++)
	    
	    {
	       String permability=  listLitho.get(lithologyList).getPerm() ;
	         permabilityint= Double. parseDouble(permability);
	         permabilityAll= permabilityAll+permabilityint;
	       
	       String leakoffceofficient=  listLitho.get(lithologyList).getLeakoff() ;
	        leakoffceofficientint=  Double. parseDouble(leakoffceofficient);
	       leakoffceofficientAll= leakoffceofficientAll+leakoffceofficientint;
	    
	       String sherModulus=  listLitho.get(lithologyList).getShearModulus() ;
	         sherModulusint=  Double. parseDouble(sherModulus);
	       sherModulusAll= sherModulusAll+sherModulusint;
	       
	       String poissonRatio=  listLitho.get(lithologyList).getPoisonRatio() ;
	         poissonRatioint=   Double. parseDouble(poissonRatio);
	       poissonRatioAll= poissonRatioAll+poissonRatioint;
	     
	    }

	    double k= permabilityint;
		 
  		double rw = Double.parseDouble(rw1);
		double re = Double.parseDouble(re1);
		double h = Double.parseDouble("100");
		double V =  poissonRatioint;
		double G =sherModulusint;
		String u12 = f12.get(0).getValue();
		double u = (Double.parseDouble(u12)) ;
		double dp = Double.parseDouble(dp1) * 2.54;
		double fy = Double.parseDouble(fy1);
		///// change the unit from field to metric............
		 
	    ProjectDetails details=	(ProjectDetails)httpSession.getAttribute("ProjectDetail");
	    String unitType=details.getUnitType();
		  if(unitType.equalsIgnoreCase("Field"))
		  { rw=rw*0.3; re=re*0.3; //A=A*4046.86;
		 h=h*0.3048; G=G*6894.76; u=u/1000; dp=dp*0.393701;
		  }
		
		
	String isTreatmentAnalysis=	sLayerInputRepo.findByParamAndPid("InjectionPlan", pdr.getOne(pid)).get(0).getValue();

	// The Analysis part will work or not will deside here.....
	if(isTreatmentAnalysis.equalsIgnoreCase("TreatmentAnalysis")) {	
		
		System.out.println("TreatmentAnalysis.....................");
		
		List<ReportMakingModel> reportmakingmodelList=reportmmr.findBypid(pdr.getOne(pid));
		if(reportmakingmodelList.size()>0)
		{reportmmr.deleteBypid(pdr.getOne(pid));
		reportmmr.flush(); }
		
		if (String.valueOf(leakoffceofficientAll).equals("null")) {
			    for (int i = 0; i < treatmentanalysis.size(); i++) {
				String q1 = treatmentanalysis.get(i).getRate();
				double qi = Double.parseDouble(q1) / 377.89;
				String t1 = treatmentanalysis.get(i).getSteptime();
				double td = Double.parseDouble(t1)*60; // changing time into metric // 30 january
		//iteration according to step time
				
				for(int t=0; t<2; t++)
				{
				double LT1 = 0.68 * (Math.pow(((G * Math.pow(qi, 3)) / ((1 - V) * u * Math.pow(h, 4))), (1 / 5)))
						* Math.pow(t, (4 / 5));
				LT = (LT1) / 2;
				LT = LT * 3.28;// feet
				W = 2.5 * (Math.pow((((1 - V) * u * Math.pow(qi, 2)) / G * h), (1 / 5))) * Math.pow(t, (1 / 5));
				W = W * 39.37;/// inch
				double kf = ((Math.pow(dp, 2) * (Math.pow(fy, 3))) / (150 * (Math.pow((1 - fy), 2)))) * (W);

				double pnet = 2.5 * (Math.pow(
						((Math.pow(G, 4) * u * Math.pow(qi, 2)) / (Math.pow((1 - V), 4) * (Math.pow(LT, 6)))), 0.2))
						* Math.pow(t, 0.2);
				pnet = pnet * 0.000145; /// Psi
/////change unit from metric to field befor storing.................
						
						 if(unitType.equalsIgnoreCase("Field"))
						{ 
					    LT=LT*3.2; 
						W=W*0.39;
						kf=kf*101325000000.0;
						pnet=pnet*0.000145038; }
						
					
					// System.out.println(LT); System.out.println(W); System.out.println(kf);
					// System.out.println(pnet);
					 
				
				ReportMakingModel r1 = new ReportMakingModel();
				r1.setLT(LT);
				r1.setW(Double.toString(W));
				r1.setPid(pdr.getOne(pid));
				r1.setTime(Double.toString(t));
				r1.setConductivity(Double.toString(kf));
				r1.setPnet(Double.toString(pnet));
				reportmmr.save(r1);
			}}
			return mapList;
		} else {
			
			for (int i = 0; i < treatmentanalysis.size(); i++) {
				String q1 = treatmentanalysis.get(i).getRate();
				double qi = Double.parseDouble(q1) / 377.89;
				String t1 = treatmentanalysis.get(i).getSteptime();/// min
				double tmin = Double.parseDouble(t1);
				double tsec = tmin*60;
				// round upto 3 decimal numbers...
				DecimalFormat df = new DecimalFormat("#.###");
				df.setRoundingMode(RoundingMode.CEILING);
				
				
				//double C = leakoffceofficientAll;
					
				double C = leakoffceofficientint;	
	//iteration according to step time
				for(double t=0.5; t<=tsec; t++)
				{
					//// Half Length Calc........................
				double LT1 = (qi * (Math.pow(t, 0.5))) / ((3.14) * C * h);	
				
				//System.out.println("Length  in meter"+LT1);
				
				LT = (LT1) / 2;/// meter
			//	System.out.println("half Len  in meter"+LT);
				
				LT = LT * 3.28;// feet
			//	System.out.println("half Len  in ft"+LT);
				
               
			
				//// Width Calc..........
				
                W = 4 * (Math.pow(((2 * (1 - V) * u * (Math.pow(qi, 2))) / ((Math.pow(Math.PI, 3)) * G * C * h)), 0.25))
						* (Math.pow(t, 0.125)); /// width in meter
               // System.out.println("W before in meter"+W);
				W = W * 39.37;/// inch
				//System.out.println("W after in meter"+W);
				double wc = W * 0.0833;
				
				double pnet = 0.000145 * 2.5 * (Math
						.pow(((Math.pow(G, 4) * u * Math.pow(qi, 2)) / (Math.pow((1 - V), 4) * (Math.pow(h, 6)))), 0.2))
						* Math.pow(t, 0.2);// pascal
				
				fy=0.32; ////dimensionless no units 
				dp=0.142;////cm unit

				double kf1 = ((Math.pow(dp, 2) * (Math.pow(fy, 3))) / (150 * (Math.pow((1 - fy), 2))));
				double kf2 = kf1 * 101325000000.0;/// md
				
				double kf = kf2 * (wc);// md-feet
				//double k = Double.parseDouble(k1);
				
				/////FCD Calc........
				double fcd = (kf2 * wc) / (k * LT);
						
					 	 

				// if else conditions for foi
				Double s = 0.0;
             	double foi = (Math.log10(re / rw)) / ((Math.log10(re / rw)) + (k*LT));
				
			/////change unit from metric to field before storing.................
				
						/*
						 * if(unitType.equalsIgnoreCase("Field")) { LT=LT*3.2; W=W*0.39;
						 * kf=kf*101325000000.0; pnet=pnet*0.000145038; }
						 */
					
							
				ReportMakingModel r1 = new ReportMakingModel();
				r1.setLT(LT);
				r1.setW(Double.toString(W));
				r1.setPid(pdr.getOne(pid));
				r1.setTime(Double.toString(t/60));
				r1.setConductivity(Double.toString(fcd));
				r1.setPnet(Double.toString(pnet));
				reportmmr.save(r1);

			}
				}
			return mapList;
		}}
	return mapList;
	}

	public List<ReportMakingModel> getVlaueOfReport(Integer pid) {
		List<ReportMakingModel> a1 = reportmmr.findBypid(pdr.getOne(pid));
		return a1;
	}
	
	
	
}
