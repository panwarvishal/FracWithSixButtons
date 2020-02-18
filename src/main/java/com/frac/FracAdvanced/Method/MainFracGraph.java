/**
 * 
 */
package com.frac.FracAdvanced.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frac.FracAdvanced.model.ReportMakingModel;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.SingleLayerInputRepo;
import com.frac.FracAdvanced.repository.reportMakingModelRepo;
import com.frac.FracAdvanced.service.MainFracGraphService;

import java.util.*;

import javax.servlet.http.HttpSession;

/**
 * @author ShubhamGaur
 *
 */
@Component
public class MainFracGraph {
	
	@Autowired
	MainFracGraphService fracGraphService;
	
	@Autowired
	reportMakingModelRepo makingModelRepo;
	
	@Autowired
	HttpSession httpSession;
	
	@Autowired
	ProjectDetailRepo detailRepo;
	
	@Autowired
	SingleLayerInputRepo  inputRepo;
	
	public void SaveMainFrac(Integer pid) {
		
	String fractureGrossHeight1=	inputRepo.findByParamAndPid("Fracture Gross Height (md)", detailRepo.getOne(pid)).get(0).getValue();
		
	double fractureGrossHeight= Double.parseDouble(fractureGrossHeight1);
	
	 
List<ReportMakingModel>	list=	makingModelRepo.findBypid(detailRepo.getOne(pid));
ArrayList<String> halfLengthList= new ArrayList<String>();
ArrayList<String> widthList= new ArrayList<String>();
ArrayList<String> timeList= new ArrayList<String>();

for (int i = 0; i < list.size(); i++) {
	halfLengthList.add(Double.toString(list.get(i).getLT()));
	widthList.add( list.get(i).getW());	
	timeList.add( list.get(i).getTime());	
}

fractureGrossHeightNegativePositiv(timeList.get(timeList.size()-1),fractureGrossHeight);

		//String[ ] arr1= {"218","299","363","397","487","607","688","761","833"};
		//String[ ] arr2= {"0.00616","0.00699","0.00756","0.00784","0.00850","0.00928","0.00976","0.01016","0.01054"};
		
        List<String> valuesY=MainFracCalc(halfLengthList, widthList).get("Y");
		List<String> valuesX=MainFracCalc(halfLengthList, widthList).get("X");
	 
		fracGraphService.saveFracGraph(pid, valuesX,valuesY);
	}
	
	
	public int fractureGrossHeightNegativePositiv(String time, Double grosfractureheight)
	{
		
	double	y=grosfractureheight/Double.parseDouble(time);
		
	for(int i=0; i<y;i++)
	{
		
		
	}
	
		return 12;
	}
	
	
	
	
	
	
	
	public Map<String,List<String>> MainFracCalc(ArrayList<String> length,ArrayList<String> width) {
		Map<String,List<String>> map=new LinkedHashMap<>();
		List<String> finalYList=new ArrayList<>();
		List<String> finalXList=new ArrayList<>();
		for(int i=0;i<length.size();i++) {
			Double x=0.0,y=0.0;
			List<String> ys=new ArrayList<String>();
			List<String> xs=new ArrayList<String>();
			List<String> ysRev=new ArrayList<String>();
			List<String> xsRev=new ArrayList<String>();
			while(x<Double.parseDouble(length.get(i))) {
				Double a=Double.parseDouble(width.get(i));
				Double b=Double.parseDouble(length.get(i));
			//	y=Math.pow((((Math.pow(a, 2)*Math.pow(b, 2))-(Math.pow(x, 2)*Math.pow(a, 2)))/Math.pow(b, 2)), 0.5);
			
				y=Math.pow((((Math.pow(a, 2)*Math.pow(b, 2))-(Math.pow(x, 2)*Math.pow(a, 2)))/Math.pow(b, 2)), 0.5);
					
				ys.add(y.toString());
				xs.add(x.toString());
				x+=30.0;
			}
			xs.add(x.toString());
			ys.add("0");
			for(int j=ys.size()-1;j>=0;j--) {
				ysRev.add("-"+ys.get(j));
			}
			for(int j=xs.size()-1;j>=0;j--) {
				xsRev.add(xs.get(j));
			}
			finalYList.addAll(ys);
			finalXList.addAll(xs);
			finalYList.addAll(ysRev);
			finalXList.addAll(xsRev);
		}
		map.put("Y",finalYList);
		map.put("X",finalXList);
		return map;
	}
	

	
}
