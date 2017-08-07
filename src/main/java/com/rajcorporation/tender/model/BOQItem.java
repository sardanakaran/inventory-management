package com.rajcorporation.tender.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class BOQItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@OneToOne(targetEntity = Agreement.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "item_id")
	MaterialItem item;
	Long tenderId;
	int tenderVersion;
	String procuredBy;
	Long quantity;
	double supplyExWorksPrice;
	double supplyTaxes;
	double perUnitSupplyCost;
	double totalSupplyCost;
	double errectionCost;
	double errectionCostWithTaxes;

	public Long getId() {
		return id;
	}

	public MaterialItem getMaterialItem() {
		return item;
	}

	public void setMaterialItem(MaterialItem item) {
		this.item = item;
	}

	public Long getTenderId() {
		return tenderId;
	}

	public void setTenderId(Long tenderId) {
		this.tenderId = tenderId;
	}
	
	public int getTenderVersion() {
		return tenderVersion;
	}

	public void setTenderVersion(int tenderVersion) {
		this.tenderVersion = tenderVersion;
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

}
