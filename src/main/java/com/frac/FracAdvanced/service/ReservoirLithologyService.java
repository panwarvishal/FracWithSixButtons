/**
 * 
 */
package com.frac.FracAdvanced.service;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.ReservoirLithologyModel;
import com.frac.FracAdvanced.model.SingleLayerInputModel;
import com.frac.FracAdvanced.model.StressAnalysisModel;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.ReservoirFluidRepo;
import com.frac.FracAdvanced.repository.ReservoirLithologyRepo;
import com.frac.FracAdvanced.repository.SingleLayerInputRepo;

/**
 * @author Vishal Kumar
 *
 */
@Service
public class ReservoirLithologyService {
	
	@Autowired
	private ReservoirLithologyRepo lithologyRepo;
	/*
	 * @Autowired private ReservoirLithologyVerticleRepo ReservoirLithologyService;
	 */	@Autowired
	private ProjectDetailRepo detailRepo;
	@Autowired
	ReservoirFluidRepo fluidRepo;
	@Autowired
	HttpSession httpSession;
	
	@Autowired
	SingleLayerInputRepo singleLayerInputRepo;
	
	public void setFiveValues(String BiotConstant1,String tensileStress1,String reservoirTemperature1,String bottomholePressure1,String tectonicStress1, String FractureGrossHeight1,int pid)
	{       
		ProjectDetails details=detailRepo.findById(pid).orElse(null);
		
		SingleLayerInputModel Bottomhole= singleLayerInputRepo.findByParamAndPid("Bottomhole Pressure", details).get(0);		
		SingleLayerInputModel Temperature=	singleLayerInputRepo.findByParamAndPid("Reservoir Temperature", details).get(0);		
		SingleLayerInputModel Constant=	singleLayerInputRepo.findByParamAndPid("Biot's Constant", details).get(0);
		SingleLayerInputModel Tensile=	singleLayerInputRepo.findByParamAndPid("Tensile Stress", details).get(0);
		SingleLayerInputModel Tectonic=	singleLayerInputRepo.findByParamAndPid("Tectonic Stress", details).get(0);
		SingleLayerInputModel FractureGrossH=	singleLayerInputRepo.findByParamAndPid("Fracture Gross Height (md)", details).get(0);
		
		Bottomhole.setValue(bottomholePressure1);
		Temperature.setValue(reservoirTemperature1);
		Constant.setValue(BiotConstant1);
		Tensile.setValue(tensileStress1);
		Tectonic.setValue(tectonicStress1);
		FractureGrossH.setValue(FractureGrossHeight1);
		
		singleLayerInputRepo.save(Bottomhole);
		singleLayerInputRepo.save(Temperature);
		singleLayerInputRepo.save(Constant);
		singleLayerInputRepo.save(Tensile);
		singleLayerInputRepo.save(Tectonic);
		singleLayerInputRepo.save(FractureGrossH);

	}
	
	
	
	
	public List<Integer> showRows(Integer number){
		List<Integer> list=new ArrayList<Integer>();
		for(int i=1;i<=number;i++) {
			System.out.println(i);
			list.add(i);
		}
		return list;
	}
	
	public List<ReservoirLithologyModel> showList(Integer pid){
		ProjectDetails details=detailRepo.findById(pid).orElse(null);
		List<ReservoirLithologyModel> list=lithologyRepo.findBydetails(details);
		return list;
	}

	public String sherModulus(String youngModulus, String poissionRatio)
	{
		double sherModu= ((Double.parseDouble(youngModulus))/(2*(1+Double.parseDouble(poissionRatio))));
			 

		return String.valueOf(sherModu);
	}
	public void saveLithology(Integer pid,List<String> input) {
		ProjectDetails details=detailRepo.findById(pid).orElse(null);
		List<ReservoirLithologyModel> list=new ArrayList<>();
		ReservoirLithologyModel lithologyModel=new ReservoirLithologyModel();
		int k=0;
		
		for(int i=0;i<input.size()/14;i++) {
			
			Integer a=i;
				lithologyModel=new ReservoirLithologyModel();
				lithologyModel.setDetails(details);
				lithologyModel.setZone(input.get(k));k++;
				lithologyModel.setFromMd(input.get(k));k++;
				lithologyModel.setToMd(input.get(k));k++;
				lithologyModel.setTvd(input.get(k));k++;
				lithologyModel.setPayThickness(input.get(k));k++;
				lithologyModel.setReservoirPressure(input.get(k));k++;
				lithologyModel.setPerm(input.get(k));k++;
				lithologyModel.setPoro(input.get(k));k++;
				lithologyModel.setYoungs(input.get(k));k++;
				lithologyModel.setPoisonRatio(input.get(k));k++;
				lithologyModel.setLeakoff(input.get(k));k++;
				lithologyModel.setSpurtLossCoefficient(input.get(k));k++;
				lithologyModel.setPorePressure(input.get(k));k++;
				lithologyModel.setDensity(input.get(k));k++;
				lithologyModel.setOrderOfInput(a.toString());
				lithologyModel.setShearModulus(sherModulus(input.get(i+8),input.get(i+9)));
 
				list.add(lithologyModel);
		}
		
		
		
		lithologyRepo.saveAll(list);
		//ReservoirLithologyModelVerticle lithologyModelVerticle= new ReservoirLithologyModelVerticle();
		//lithologyModelVerticle.setrLVerticle(details);
		//lithologyModelVerticle.set
		
	}
	
	public void saveEdit(Integer pid, List<String> input) {
		ProjectDetails details=detailRepo.findById(pid).orElse(null);
		List<ReservoirLithologyModel> list=lithologyRepo.findBydetails(details);
		List<ReservoirLithologyModel> list1=new ArrayList<>();
		int k=0;
		for(int i=0;i<list.size();i++) {
			ReservoirLithologyModel lithologyModel=list.get(i);
			lithologyModel.setFromMd(input.get(k));k++;
			lithologyModel.setToMd(input.get(k));k++;
			lithologyModel.setTvd(input.get(k));k++;
			lithologyModel.setPayThickness(input.get(k));k++;
			
			lithologyModel.setReservoirPressure(input.get(k));k++;
			lithologyModel.setPerm(input.get(k));k++;
			lithologyModel.setPoro(input.get(k));k++;
			lithologyModel.setYoungs(input.get(k));k++;
			lithologyModel.setPoisonRatio(input.get(k));k++;
			lithologyModel.setLeakoff(input.get(k));k++;
			lithologyModel.setSpurtLossCoefficient(input.get(k));k++;
			lithologyModel.setPorePressure(input.get(k));k++;
			lithologyModel.setDensity(input.get(k));k++;
			lithologyModel.setShearModulus(sherModulus(input.get(i+8),input.get(i+9)));
			 
			list1.add(lithologyModel);
	}
		lithologyRepo.saveAll(list1);
	}
	
	
	public void readFileLithology(Integer pid, MultipartFile file) throws Exception {
		ProjectDetails details=detailRepo.findById(pid).orElse(null);
		
		lithologyRepo.deleteByDetail(details);
		
		String fileName = file.getOriginalFilename();
		if (fileName.endsWith("txt")) {
			// **************** read a txt file here **********************
		} else if (fileName.endsWith("xlsx")) {
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			Integer sheetNumber=0;
			for(int i=0;i<workbook.getNumberOfSheets();i++) {
				if(workbook.getSheetName(i).replaceAll("\\s+", "").equalsIgnoreCase("reservoir lithology")) {
					sheetNumber=i;
				}
			}
			Row row;
			Cell c;
			
			int FromMD = 0, Tomd = 0, TVD =0, PayThickness =0, ReservoirPressure = 0, Permebility = 0;
			int Porosity = 0, Youngmodulus = 0, PoissonRatio = 0, LeakoffC = 0;
			int SpurtLossC = 0, PorePressure = 0, density = 0 ;
			List<String> FromMDs = new ArrayList<String>();
			List<String> Tomds = new ArrayList<String>();
			List<String> TVDs = new ArrayList<String>();
			List<String> PayThicknesss = new ArrayList<String>();
			
			List<String> ReservoirPressures = new ArrayList<String>();
			List<String> Permebilitys = new ArrayList<String>();
			List<String> Porositys = new ArrayList<String>();
			List<String> Youngmoduluss = new ArrayList<String>();
			List<String> PoissonRatios = new ArrayList<String>();
			List<String> LeakoffCs = new ArrayList<String>();
			List<String> SpurtLossCs = new ArrayList<String>();
			List<String> PorePressures = new ArrayList<String>();
			List<String> densitys = new ArrayList<String>();
			
			
			XSSFSheet worksheet = workbook.getSheetAt(sheetNumber);
			for (int i = worksheet.getFirstRowNum(); i < worksheet.getLastRowNum(); i++) {
				row = worksheet.getRow(i);
				if (row != null) {
					for (int j = 0; j < row.getLastCellNum(); j++) {
						c = row.getCell(j);
						
						if (c != null && i == 0) {
							if (c.toString().equalsIgnoreCase("From MD")) {
								FromMD = j;
							} else if (c.toString().equalsIgnoreCase("To md")) {
								Tomd = j;
							}else if (c.toString().equalsIgnoreCase("TVD")) {
								TVD = j;
							}else if (c.toString().equalsIgnoreCase("Pay Thickness")) {
								PayThickness = j;
							} else if (c.toString().equalsIgnoreCase("Reservoir Pressure")) {
								ReservoirPressure = j;
							} else if (c.toString().equalsIgnoreCase("Permebility Pressure")) {
								Permebility = j;
							}else if (c.toString().equalsIgnoreCase("Porosity")) {
								Porosity = j;
							}else if (c.toString().equalsIgnoreCase("Young'S modulus")) {
								Youngmodulus = j;
							}else if (c.toString().equalsIgnoreCase("Poisson Ratio")) {
								PoissonRatio = j;
							}else if (c.toString().equalsIgnoreCase("Leak off Coefficient")) {
								LeakoffC = j;
							}else if (c.toString().equalsIgnoreCase("Spurt Loss  Coefficent")) {
								SpurtLossC = j;
							}else if (c.toString().equalsIgnoreCase("Pore Pressure")) {
								PorePressure = j;
							}else if (c.toString().equalsIgnoreCase("Density")) {
								density = j;
							}
						} else if (c != null) {
							
							if (j == FromMD && !c.toString().equals("")) {
								FromMDs.add(String.valueOf(c.getNumericCellValue()));
							} else if (j == Tomd && !c.toString().equals("")) {
								Tomds.add(String.valueOf(c.getNumericCellValue()));
							} else if (j == TVD && !c.toString().equals("")) {
								TVDs.add(String.valueOf(c.getNumericCellValue()));
							}else if (j == PayThickness && !c.toString().equals("")) {
								PayThicknesss.add(String.valueOf(c.getNumericCellValue()));
							}else if (j == ReservoirPressure && !c.toString().equals("")) {
								ReservoirPressures.add(String.valueOf(c.getNumericCellValue()));
							} else if (j == Permebility && !c.toString().equals("")) {
								Permebilitys.add(String.valueOf(c.getNumericCellValue()));
							}else if (j == Porosity && !c.toString().equals("")) {
								Porositys.add(String.valueOf(c.getNumericCellValue()));
							}else if (j == Youngmodulus && !c.toString().equals("")) {
								Youngmoduluss.add(String.valueOf(c.getNumericCellValue()));
							}else if (j == PoissonRatio && !c.toString().equals("")) {
								PoissonRatios.add(String.valueOf(c.getNumericCellValue()));
							}else if (j == LeakoffC && !c.toString().equals("")) {
								LeakoffCs.add(String.valueOf(c.getNumericCellValue()));
							}else if (j == SpurtLossC && !c.toString().equals("")) {
								SpurtLossCs.add(String.valueOf(c.getNumericCellValue()));
							}else if (j == PorePressure && !c.toString().equals("")) {
								PorePressures.add(String.valueOf(c.getNumericCellValue()));
							}else if (j == density && !c.toString().equals("")) {
								densitys.add(String.valueOf(c.getNumericCellValue()));
							}
						}
					}
				}
			}
			workbook.close();
			
			saveByFile(pid, FromMDs, Tomds, TVDs, PayThicknesss, ReservoirPressures, Permebilitys, Porositys, Youngmoduluss, PoissonRatios, LeakoffCs, SpurtLossCs, PorePressures, densitys );
		}
	}

	public void saveByFile(Integer pid,List<String>	FromMDs, List<String> Tomds, List<String> TVDs, List<String> PayThicknesss, List<String> ReservoirPressures, List<String> Permebilitys, List<String> Porositys, List<String> Youngmoduluss, 
			List<String> PoissonRatios, List<String> LeakoffCs, List<String> SpurtLossCs, List<String> PorePressures, List<String> densitys)
	{
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<ReservoirLithologyModel> list = new ArrayList<ReservoirLithologyModel>();
		for (int i = 0; i < FromMDs.size(); i++) {
			String zone= String.valueOf(i);
			ReservoirLithologyModel lithologyModel=new ReservoirLithologyModel();
			lithologyModel.setDetails(detail);
			lithologyModel.setZone(zone);
			lithologyModel.setFromMd(FromMDs.get(i));
			lithologyModel.setToMd(Tomds.get(i));
			lithologyModel.setTvd(TVDs.get(i));
			lithologyModel.setPayThickness(PayThicknesss.get(i));
			
			lithologyModel.setReservoirPressure(ReservoirPressures.get(i));
			lithologyModel.setPerm(Permebilitys.get(i));
			lithologyModel.setPoro(Porositys.get(i));
			lithologyModel.setYoungs(Youngmoduluss.get(i));
			lithologyModel.setPoisonRatio(PoissonRatios.get(i));
			lithologyModel.setLeakoff(LeakoffCs.get(i));
			lithologyModel.setSpurtLossCoefficient(SpurtLossCs.get(i));
			lithologyModel.setPorePressure(PorePressures.get(i));
			lithologyModel.setDensity(densitys.get(i));
			 
			list.add(lithologyModel);
		}
		lithologyRepo.saveAll(list);
	}
	
}
