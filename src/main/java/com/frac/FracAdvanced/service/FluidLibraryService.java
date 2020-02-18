package com.frac.FracAdvanced.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.frac.FracAdvanced.model.FluidLibraryModel;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.repository.FluidLibraryRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;

import java.util.*;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
@Component
public class FluidLibraryService {
	
	@Autowired
	FluidLibraryRepo flr;
	@Autowired
	ProjectDetailRepo pdr;
	@Autowired
	HttpSession httpsession;
	
	int pid;
	public List<FluidLibraryModel> method1(String ftype) throws Exception
	{	pid=(Integer)httpsession.getAttribute("PDId");
		flr.findByProId(pid).get(0).setFluidTypeSelected(ftype);;
		flr.save(flr.findByProId(pid).get(0));
		ArrayList<FluidLibraryModel> a1= new ArrayList<FluidLibraryModel>();	
		List<FluidLibraryModel> x12=flr.findByProId(pid);			
		for(int i=0;i<x12.size();i++) {		
		if((x12.get(i).getType()).equalsIgnoreCase(ftype))
		{
        a1.add(x12.get(i));			
             }}	
		return a1;
	}	
	public ArrayList<String> method2GetFluidType()
	{  Set<String> uniqueTypeSet= new HashSet<String>();
		List<FluidLibraryModel> x12=flr.findByProId(pid);
		for(int i=0;i<x12.size();i++) {		
			String uniqueList=   x12.get(i).getType();
			uniqueTypeSet.add(uniqueList);		}
		ArrayList<String> fluidTypeList=new ArrayList<String>(uniqueTypeSet);
		return fluidTypeList;
		
	}
	
	
	public void methodremoveFluidFromLibrary(String type)
	{
		
		List<FluidLibraryModel> x12=flr.findByProId(pid);
		for(int i=0;i<x12.size();i++) {		
			if(type.equalsIgnoreCase("Surface Crosslink")||type.equalsIgnoreCase("Linear Gel"))
			{}
			else if(x12.get(i).getType().equalsIgnoreCase(type))
			{
				int id1= x12.get(i).getId();
				flr.deleteById(id1);
			}			
		}}
	
	
	
	public List<FluidLibraryModel> methodEdit(String pid, String ftype, List<String> value, List<String> parameter) throws Exception
	{		
		ArrayList<FluidLibraryModel> a1= new ArrayList<FluidLibraryModel>();
		int pid1= Integer.parseInt(pid);
		List<FluidLibraryModel> x12=flr.findByProId(pid1);	
		int i1=0;	
		for(int i=0;i<x12.size();i++) {			 
			if((x12.get(i).getType()).equals(ftype))
		{				
      x12.get(i).setParameter(parameter.get(i1));
      x12.get(i).setValue(value.get(i1));
      flr.save(x12.get(i));               
			i1=i1+1;
			
             }	/*if((i1>0)) {break;}*/	}
		//get the value
		List<FluidLibraryModel> x123=flr.findByProId(pid1);
         for(int i=0;i<x123.size();i++) {			
			if((x123.get(i).getType()).equals(ftype))
		{				
               a1.add(x123.get(i));			
             }		}
		return a1;
	}
	
/// calculating          
	
	public List<FluidLibraryModel> methodCalculateViscosity( float k,float neta, float gama, String ftype) throws Exception
	{		
		double viscosity=47880*k*(Math.pow(gama, (neta-1)));
	    double rounded = Math. round(viscosity * 100.0) / 100.0;
		
		
		
		ArrayList<FluidLibraryModel> a1= new ArrayList<FluidLibraryModel>();
		List<FluidLibraryModel> x12=flr.findByProId(pid);	
		int i1=0;	
		for(int i=0;i<x12.size();i++) {			
			if((x12.get(i).getType()).equals(ftype))
		{				   
      x12.get(i).setValue(String.valueOf(rounded));
      flr.save(x12.get(i));               
			i1=i1+1;			
			break;  }		}
		//get the value
		List<FluidLibraryModel> x123=flr.findByProId(pid);
         for(int i=0;i<x123.size()-1;i++) {			
			if((x123.get(i).getType()).equals(ftype))
		{				
               a1.add(x123.get(i));			
             }		}
		return a1;
	}
	/// user methods
	public List<FluidLibraryModel> userMethod(List<String> parameter,List<String> value,  String ftype) throws Exception
	{				
		ArrayList<FluidLibraryModel> a1= new ArrayList<FluidLibraryModel>();
		ProjectDetails pd1=	pdr.getOne(pid);
     	for(int i=0; i<parameter.size();i++)
		{FluidLibraryModel g1=new FluidLibraryModel();
		g1.setType(ftype);
		g1.setPidFL(pd1);
		g1.setParameter(parameter.get(i));
		g1.setValue(value.get(i));
		flr.save(g1);
		}
		//get the value
		List<FluidLibraryModel> x123=flr.findByProId(pid);
         for(int i=0;i<x123.size()-1;i++) {			
			if((x123.get(i).getType()).equals(ftype))
		{				
               a1.add(x123.get(i));			
             }		}
		return a1;
	}
	
	
	public List<FluidLibraryModel> methodEditNewtonian(String pid, String ftype, List<String> value, List<String> parameter) throws Exception
	{		
		ArrayList<FluidLibraryModel> a1= new ArrayList<FluidLibraryModel>();
		int pid1= Integer.parseInt(pid);
		List<FluidLibraryModel> x12=flr.findByProId(pid1);	
		int i1=0;	
		for(int i=0;i<x12.size();i++) {			 
			if((x12.get(i).getType()).equals(ftype))
		{		
      x12.get(i).setParameter(parameter.get(i1));
      x12.get(i).setValue(value.get(i1));
      flr.save(x12.get(i));               
			i1=i1+1;
			
             }	if((i1>0)) {break;}	}
		//get the value
		List<FluidLibraryModel> x123=flr.findByProId(pid1);
         for(int i=0;i<x123.size();i++) {			
			if((x123.get(i).getType()).equals(ftype))
		{				
               a1.add(x123.get(i));			
             }		}
		return a1;
	}
	public List<FluidLibraryModel> userMethod2( String value,  String ftype) throws Exception
	{		
		
		ArrayList<FluidLibraryModel> a1= new ArrayList<FluidLibraryModel>();
		List<FluidLibraryModel> x12=flr.findByProId(pid);	
		int i1=0;	
		for(int i=0;i<x12.size();i++) {			
			if((x12.get(i).getType()).equals(ftype))
		{				    
      x12.get(i).setValue(value);
      flr.save(x12.get(i));               
			i1=i1+1;			
             }		}
		//get the value
		List<FluidLibraryModel> x123=flr.findByProId(pid);
         for(int i=0;i<x123.size()-1;i++) {			
			if((x123.get(i).getType()).equals(ftype))
		{				
               a1.add(x123.get(i));			
             }		}
		return a1;
	}
	
		
	public ArrayList<String> fluidUnitList() throws Exception
	{ArrayList<String> aList=null;
	ProjectDetails details=(ProjectDetails)httpsession.getAttribute("ProjectDetail");
	ProjectDetails p= pdr.findById(details.getId()).orElse(details);
	String uTypeDataBase=	p.getUnitType();
	if(uTypeDataBase.equalsIgnoreCase("Field"))
	
	{	
		String [] a= {"(cp)",""," (ib/gal)","(F)"};
		aList = new ArrayList<String>(Arrays.asList(a));}	
			
	else if (uTypeDataBase.equalsIgnoreCase("Metric"))
	{	String [] a= {"(Pa.s)","","(Kg/m3)","(C)"};
	 aList = new ArrayList<String>(Arrays.asList(a));}

    return aList;
	}	
	
	
}
