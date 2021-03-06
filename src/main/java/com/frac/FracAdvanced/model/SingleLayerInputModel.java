package com.frac.FracAdvanced.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SingleLayerInputModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String param;
	private String value;
	
	
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH },
	fetch=FetchType.LAZY)
@JoinColumn(name="pId", nullable=true)
private ProjectDetails pid;	
	

	public ProjectDetails getPid() {
		return pid;
	}
	public void setPid(ProjectDetails pid) {
		this.pid = pid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}



	@Override
	public String toString() {
		return "SingleLayerInputModel [id=" + id + ", param=" + param + ", value=" + value + ", pid=" + pid + "]";
	}
	public SingleLayerInputModel(Integer id, String param, String value, ProjectDetails pid) {
		super();
		this.id = id;
		this.param = param;
		this.value = value;
		this.pid = pid;
	}
	public SingleLayerInputModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	

}
