package com.rajcorporation.tender.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class BOQItem {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	MaterialItem item;

	Long tenderId;
	int boqVersion;
	String procuredBy;
	Long quantity;
	double supplyExWorksPrice;
	double supplyTaxes;
	double perUnitSupplyCost;
	double totalSupplyCost;
	double errectionCost;
	double errectionCostWithTaxes;

	public Long getTenderId() {
		return tenderId;
	}

	public void setTenderId(Long tenderId) {
		this.tenderId = tenderId;
	}	
	
//	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "boqItem")
//	List<DataInspection> dataInspection = new ArrayList<>();
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "boq_id")
//	@JsonIgnore
//	BOQ boq;
//
//	public BOQItem addDataInspection(DataInspection inspection) {
//		dataInspection.add(inspection);
//		inspection.setBoqItem(this);
//		return this;
//	}

	public Long getId() {
		return id;
	}

	public MaterialItem getMaterialItem() {
		return item;
	}

	public void setMaterialItem(MaterialItem item) {
		this.item = item;
	}

	public int getBoqVersion() {
		return boqVersion;
	}

	public void setBoqVersion(int boqVersion) {
		this.boqVersion = boqVersion;
	}

	public String getProcuredBy() {
		return procuredBy;
	}

	public void setProcuredBy(String procuredBy) {
		this.procuredBy = procuredBy;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public double getSupplyExWorksPrice() {
		return supplyExWorksPrice;
	}

	public void setSupplyExWorksPrice(double supplyExWorksPrice) {
		this.supplyExWorksPrice = supplyExWorksPrice;
	}

	public double getSupplyTaxes() {
		return supplyTaxes;
	}

	public void setSupplyTaxes(double supplyTaxes) {
		this.supplyTaxes = supplyTaxes;
	}

	public double getPerUnitSupplyCost() {
		return supplyExWorksPrice + supplyTaxes;
	}

	public double getTotalSupplyCost() {
		return quantity * (supplyExWorksPrice + supplyTaxes);
	}

	public double getErrectionCost() {
		return errectionCost;
	}

	public void setErrectionCost(double errectionCost) {
		this.errectionCost = errectionCost;
	}

	public double getErrectionCostWithTaxes() {
		return errectionCostWithTaxes;
	}

	public void setErrectionCostWithTaxes(double errectionCostWithTaxes) {
		this.errectionCostWithTaxes = errectionCostWithTaxes;
	}

	public double getPerUnitErrectionCostWithTaxes() {
		return errectionCost + errectionCostWithTaxes;
	}

	public double getTotalErrectionCostWithTaxes() {
		return quantity * (errectionCost + errectionCostWithTaxes);
	}

	public MaterialItem getItem() {
		return item;
	}

	public void setItem(MaterialItem item) {
		this.item = item;
	}

//	public List<DataInspection> getDataInspection() {
//		return dataInspection;
//	}
//
//	public void setDataInspection(List<DataInspection> dataInspection) {
//		this.dataInspection = dataInspection;
//	}
//
//	public BOQ getBoq() {
//		return boq;
//	}
//
//	public void setBoq(BOQ boq) {
//		this.boq = boq;
//	}

//	public void setId(Long id) {
//		this.id = id;
//	}

	public void setPerUnitSupplyCost(double perUnitSupplyCost) {
		this.perUnitSupplyCost = perUnitSupplyCost;
	}

	public void setTotalSupplyCost(double totalSupplyCost) {
		this.totalSupplyCost = totalSupplyCost;
	}

}
