package com.frac.FracAdvanced.model;

import java.io.Serializable;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ShubhamGaur
 *
 */
@Entity
@Table(name="OutputMiniFrac")
public class OutputMiniFrac implements Serializable {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="id", nullable=true)
		private Integer id;
		@Column(name="Gtd", nullable=true)
		private String Gtd;
		@Column(name="dpdG", nullable=true)
		private String dpdG;
		@Column(name="GdpdG", nullable=true)
		private String GdpdG;
		
		
		@Column(name="pressure", nullable=true)
		private String pressure;
		 
		@Column(name="gFunction", nullable=true)
		private String gFunction;
		
		
		@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE },
				fetch=FetchType.LAZY)
		@JoinColumn(name="projectId", nullable=true)
		private ProjectDetails prodetails;
		
		public int getProId() {
			return prodetails.getId();
		}
		
		@JsonIgnore
		public ProjectDetails getProdetails() {
			return prodetails;
		}
		public void setProdetails(ProjectDetails prodetails) {
			this.prodetails = prodetails;
		}
		public String getGtd() {
			return Gtd;
		}

		public String getPressure() {
			return pressure;
		}

		public void setPressure(String pressure) {
			this.pressure = pressure;
		}

		public String getgFunction() {
			return gFunction;
		}

		public void setgFunction(String gFunction) {
			this.gFunction = gFunction;
		}

	
		public OutputMiniFrac(Integer id, String gtd, String dpdG, String gdpdG, String pressure, String gFunction,
				ProjectDetails prodetails) {
			super();
			this.id = id;
			Gtd = gtd;
			this.dpdG = dpdG;
			GdpdG = gdpdG;
			this.pressure = pressure;
			this.gFunction = gFunction;
			this.prodetails = prodetails;
		}

		public OutputMiniFrac() {
			super();
		}
		

		@Override
		public String toString() {
			return "OutputMiniFrac [id=" + id + ", Gtd=" + Gtd + ", dpdG=" + dpdG + ", GdpdG=" + GdpdG + ", pressure="
					+ pressure + ", gFunction=" + gFunction + ", prodetails=" + prodetails + "]";
		}

		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public void setGtd(String gtd) {
			Gtd = gtd;
		}
		public String getDpdG() {
			return dpdG;
		}
		public void setDpdG(String dpdG) {
			this.dpdG = dpdG;
		}
		public String getGdpdG() {
			return GdpdG;
		}
		public void setGdpdG(String gdpdG) {
			GdpdG = gdpdG;
		}
		
}
