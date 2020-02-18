/**
 * 
 */
package com.frac.FracAdvanced.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author vishal
 *
 */
@Entity
@Table(name = "lithology")
public class ReservoirLithologyModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String zone;
	private String tvd;
	private String fromMd;
	private String toMd;
	private String payThickness;
	
	private String reservoirPressure; 
	private String perm;
	private String poro;
	private String leakoff;
	private String youngs;
	private String shearModulus;
	//private String reservoirTemperature;
	//private String bottomholePressure;
	private String poisonRatio;
	private String porePressure;
	private String density;
	private String spurtLossCoefficient;
	private String orderOfInput;

	
	/*
	 * public String getFracstage() { return fracstage; } public void
	 * setFracstage(String fracstage) { this.fracstage = fracstage; }
	 */
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH },
			fetch=FetchType.LAZY)
	@JoinColumn(name="pId", nullable=true)
	private ProjectDetails details;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTvd() {
		return tvd;
	}
	public void setTvd(String tvd) {
		this.tvd = tvd;
	}

	
	public String getFromMd() {
		return fromMd;
	}
	public void setFromMd(String fromMd) {
		this.fromMd = fromMd;
	}
	public String getToMd() {
		return toMd;
	}
	public void setToMd(String toMd) {
		this.toMd = toMd;
	}
 
	public String getReservoirPressure() {
		return reservoirPressure;
	}
	public void setReservoirPressure(String reservoirPressure) {
		this.reservoirPressure = reservoirPressure;
	}

	public String getShearModulus() {
		return shearModulus;
	}
	public void setShearModulus(String shearModulus) {
		this.shearModulus = shearModulus;
	}
	public String getPerm() {
		return perm;
	}
	public void setPerm(String perm) {
		this.perm = perm;
	}
	public String getPoro() {
		return poro;
	}
	public void setPoro(String poro) {
		this.poro = poro;
	}
	public String getLeakoff() {
		return leakoff;
	}
	public void setLeakoff(String leakoff) {
		this.leakoff = leakoff;
	}
	public String getYoungs() {
		return youngs;
	}
	public void setYoungs(String youngs) {
		this.youngs = youngs;
	}
	public ProjectDetails getDetails() {
		return details;
	}
	public void setDetails(ProjectDetails details) {
		this.details = details;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}


	
	public String getPoisonRatio() {
		return poisonRatio;
	}
	public void setPoisonRatio(String poisonRatio) {
		this.poisonRatio = poisonRatio;
	}
	public String getPorePressure() {
		return porePressure;
	}
	public void setPorePressure(String porePressure) {
		this.porePressure = porePressure;
	}
	public String getDensity() {
		return density;
	}
	public void setDensity(String density) {
		this.density = density;
	}
	public String getSpurtLossCoefficient() {
		return spurtLossCoefficient;
	}
	public void setSpurtLossCoefficient(String spurtLossCoefficient) {
		this.spurtLossCoefficient = spurtLossCoefficient;
	}
	 
	public String getPayThickness() {
		return payThickness;
	}
	public void setPayThickness(String payThickness) {
		this.payThickness = payThickness;
	}

	 
	public ReservoirLithologyModel() {
		super();
	}
	public String getOrderOfInput() {
		return orderOfInput;
	}
	public void setOrderOfInput(String orderOfInput) {
		this.orderOfInput = orderOfInput;
	}
	public ReservoirLithologyModel(Integer id, String zone, String tvd, String fromMd, String toMd, String payThickness,
			String reservoirPressure, String perm, String poro, String leakoff, String youngs, String shearModulus,
			String poisonRatio, String porePressure, String density, String spurtLossCoefficient, String orderOfInput,
			ProjectDetails details) {
		super();
		this.id = id;
		this.zone = zone;
		this.tvd = tvd;
		this.fromMd = fromMd;
		this.toMd = toMd;
		this.payThickness = payThickness;
		this.reservoirPressure = reservoirPressure;
		this.perm = perm;
		this.poro = poro;
		this.leakoff = leakoff;
		this.youngs = youngs;
		this.shearModulus = shearModulus;
		this.poisonRatio = poisonRatio;
		this.porePressure = porePressure;
		this.density = density;
		this.spurtLossCoefficient = spurtLossCoefficient;
		this.orderOfInput = orderOfInput;
		this.details = details;
	}
	@Override
	public String toString() {
		return "ReservoirLithologyModel [id=" + id + ", zone=" + zone + ", tvd=" + tvd + ", fromMd=" + fromMd
				+ ", toMd=" + toMd + ", payThickness=" + payThickness + ", reservoirPressure=" + reservoirPressure
				+ ", perm=" + perm + ", poro=" + poro + ", leakoff=" + leakoff + ", youngs=" + youngs
				+ ", shearModulus=" + shearModulus + ", poisonRatio=" + poisonRatio + ", porePressure=" + porePressure
				+ ", density=" + density + ", spurtLossCoefficient=" + spurtLossCoefficient + ", orderOfInput="
				+ orderOfInput + ", details=" + details + "]";
	}


	
	
	
	
	
}
