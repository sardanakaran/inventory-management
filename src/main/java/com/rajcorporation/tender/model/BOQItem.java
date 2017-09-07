package com.rajcorporation.tender.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class BOQItem extends Changeable implements Cloneable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	Long id;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "material_item_id")
	MaterialItem item;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "boq_id")
	@JsonIgnore
	BOQ boq;

	@OneToMany(mappedBy = "boqItem", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	List<DataInspection> dataInspection = new ArrayList<>();

	int boqVersion;
	String procuredBy;
	Long quantity;
	double supplyExWorksPrice;
	double supplyTaxes;
	double perUnitSupplyCost;
	double totalSupplyCost;
	double errectionCost;
	double errectionCostWithTaxes;

	@JsonProperty
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

	public BOQItem withDataInspection(DataInspection inspection) {
		inspection.setBoqItem(this);
		dataInspection.add(inspection);
		return this;
	}

	public BOQItem withBOQ(BOQ boq) {
		this.boq = boq;
		return this;
	}

	public void setPerUnitSupplyCost(double perUnitSupplyCost) {
		this.perUnitSupplyCost = perUnitSupplyCost;
	}

	public void setTotalSupplyCost(double totalSupplyCost) {
		this.totalSupplyCost = totalSupplyCost;
	}

	@Override
	protected BOQItem clone() {
		BOQItem clone = new BOQItem();
		if (item != null)
			clone.setItem(item.clone());

		clone.setErrectionCost(errectionCost);
		clone.setProcuredBy(procuredBy);
		clone.setQuantity(quantity);
		clone.setSupplyExWorksPrice(supplyExWorksPrice);
		clone.setSupplyTaxes(supplyTaxes);
		clone.setErrectionCostWithTaxes(errectionCostWithTaxes);
		clone.setTotalSupplyCost(totalSupplyCost);

		return clone;
	}

}
