package com.frac.FracAdvanced.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table

public class ReportMakingModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private double LT;		
	private String W;
	private String time;
	private String conductivity;
	private String pnet;
	private String ProppMixSch;
	
	


	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH },
			fetch=FetchType.LAZY)	
	@JoinColumn(name="Project_ID")
	private ProjectDetails pid;
	

	public String getPnet() {
		return pnet;
	}

	public void setPnet(String pnet) {
		this.pnet = pnet;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}


	public String getConductivity() {
		return conductivity;
	}


	public void setConductivity(String conductivity) {
		this.conductivity = conductivity;
	}


	public double getLT() {
		return LT;
	}


	public void setLT(double lT) {
		LT = lT;
	}


	public String getW() {
		return W;
	}


	public void setW(String w) {
		W = w;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public ProjectDetails getPid() {
		return pid;
	}


	public void setPid(ProjectDetails pid) {
		this.pid = pid;
	}
	
	

	public String getProppMixSch() {
		return ProppMixSch;
	}

	public void setProppMixSch(String proppMixSch) {
		ProppMixSch = proppMixSch;
	}



	public ReportMakingModel(Integer id, double lT, String w, String time, String conductivity, String pnet,
			String proppMixSch, ProjectDetails pid) {
		super();
		this.id = id;
		LT = lT;
		W = w;
		this.time = time;
		this.conductivity = conductivity;
		this.pnet = pnet;
		ProppMixSch = proppMixSch;
		this.pid = pid;
	}

	public ReportMakingModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ReportMakingModel [id=" + id + ", LT=" + LT + ", W=" + W + ", time=" + time + ", conductivity="
				+ conductivity + ", pnet=" + pnet + ", ProppMixSch=" + ProppMixSch + ", pid=" + pid + "]";
	}


 

	
	
}
