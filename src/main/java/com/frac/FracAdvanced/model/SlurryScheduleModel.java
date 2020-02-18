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
 * @author Vishal kumar 15 Feb
 *
 */

@Entity
@Table()

public class SlurryScheduleModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String time;
	private String proppMixSch;
	
 
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH },
			fetch=FetchType.LAZY)	
	@JoinColumn(name="Project_ID")
	private ProjectDetails pid;


 


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getProppMixSch() {
		return proppMixSch;
	}


	public void setProppMixSch(String proppMixSch) {
		this.proppMixSch = proppMixSch;
	}


	public ProjectDetails getPid() {
		return pid;
	}


	public void setPid(ProjectDetails pid) {
		this.pid = pid;
	}


	public SlurryScheduleModel(Integer id, String time, String proppMixSch, ProjectDetails pid) {
		super();
		this.id = id;
		this.time = time;
		this.proppMixSch = proppMixSch;
		this.pid = pid;
	}


	public SlurryScheduleModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "SlurryScheduleModel [id=" + id + ", time=" + time + ", proppMixSch=" + proppMixSch + ", pid=" + pid
				+ "]";
	}
	
  
	
}
