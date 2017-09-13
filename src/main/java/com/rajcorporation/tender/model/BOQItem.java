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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "boq_id")
	@JsonIgnore
	BOQ boq;

	@OneToMany(mappedBy = "boqItem", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<DataInspection> dataInspection = new ArrayList<>();

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "material_item_id")
	MaterialItem item; // columns 2 & 4; UI needed? YES
	Long quantity; // column 3; UI needed? YES
	double supplyExWorksPrice; // column 5; UI needed? YES

	@JsonIgnore
	double totalExWorksAmt; // Column 6; UI needed? NO. Auto-Calculated
							// [supplyExWorksPrice X quantity]
	double freightInsuranceAndCartageAmtPerUnit; // Column 7; UI needed? YES

	@JsonIgnore
	double totalFreightInsuranceAndCartageAmt; // Column 8; UI needed? NO Need
												// not be supplied by the UI
												// [freightInsuranceAndCartageAmt
												// X quantity]
	double erectionCostWithTaxesPerUnit;// Column 9; UI needed? YES

	@JsonIgnore
	double totalErectionCostWithTaxes;// Column 10; UI needed? NO.
										// Auto-calculated
										// [erectionCostWithTaxesPerUnit X
										// quantity]

	@JsonIgnore
	double totalAmtPerUnit; // Column 11; UI needed? NO. Auto-calculated
							// [supplyExWorksPrice +
							// freightInsuranceAndCartageAmtPerUnit+
							// erectionCostWithTaxesPerUnit+taxesAndDutyPerUnit]
	double taxesAndDutyPerUnit; // Column 12; UI needed? YES

	@JsonIgnore
	double totalTaxesAndDuty; // Column 13; UI needed? NO. Auto-calculated
								// [taxesAndDutyPerUnit X quantity]

	@JsonIgnore
	double totalAmount; // Column 14; UI needed? NO. Auto-calculated
						// [totalAmtPerUnit X quantity]

	@JsonProperty
	public Long getId() {
		return id;
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

	public List<DataInspection> getDataInspection() {
		return dataInspection;
	}

	public void setDataInspection(List<DataInspection> dataInspection) {
		this.dataInspection = dataInspection;
	}

	@JsonProperty
	public double getTotalExWorksAmt() {
		return totalExWorksAmt = supplyExWorksPrice * quantity;
	}

	public double getFreightInsuranceAndCartageAmtPerUnit() {
		return freightInsuranceAndCartageAmtPerUnit;
	}

	public void setFreightInsuranceAndCartageAmtPerUnit(double freightInsuranceAndCartageAmtPerUnit) {
		this.freightInsuranceAndCartageAmtPerUnit = freightInsuranceAndCartageAmtPerUnit;
	}

	@JsonProperty
	public double getTotalFreightInsuranceAndCartageAmt() {
		return totalFreightInsuranceAndCartageAmt = freightInsuranceAndCartageAmtPerUnit * quantity;
	}

	public double getErectionCostWithTaxesPerUnit() {
		return erectionCostWithTaxesPerUnit;
	}

	public void setErectionCostWithTaxesPerUnit(double erectionCostWithTaxesPerUnit) {
		this.erectionCostWithTaxesPerUnit = erectionCostWithTaxesPerUnit;
	}

	@JsonProperty
	public double getTotalErectionCostWithTaxes() {
		return totalErectionCostWithTaxes = erectionCostWithTaxesPerUnit * quantity;
	}

	@JsonProperty
	public double getTotalAmtPerUnit() {
		return totalAmtPerUnit = (supplyExWorksPrice + freightInsuranceAndCartageAmtPerUnit
				+ erectionCostWithTaxesPerUnit + taxesAndDutyPerUnit);
	}

	public double getTaxesAndDutyPerUnit() {
		return taxesAndDutyPerUnit;
	}

	public void setTaxesAndDutyPerUnit(double taxesAndDutyPerUnit) {
		this.taxesAndDutyPerUnit = taxesAndDutyPerUnit;
	}

	@JsonProperty
	public double getTotalTaxesAndDuty() {
		return totalTaxesAndDuty = taxesAndDutyPerUnit * quantity;
	}

	@JsonProperty
	public double getTotalAmount() {
		return totalAmount = getTotalAmtPerUnit() * quantity;
	}

	@PrePersist
	@PreUpdate
	public void setAllTotals() {
		// Getters are essentially setters for us; however, we'll revisit this
		// one day to save some stack calls[pointless pretty much]
		getTotalAmount();
		getTotalAmtPerUnit();
		getTotalErectionCostWithTaxes();
		getTotalExWorksAmt();
		getTotalFreightInsuranceAndCartageAmt();
		getTotalTaxesAndDuty();
	}

	@Override
	protected BOQItem clone() {
		BOQItem clone = new BOQItem();
		if (item != null)
			clone.setItem(item.clone());

		clone.setQuantity(quantity);
		clone.setSupplyExWorksPrice(supplyExWorksPrice);
		clone.setErectionCostWithTaxesPerUnit(erectionCostWithTaxesPerUnit);
		clone.setTaxesAndDutyPerUnit(taxesAndDutyPerUnit);
		clone.setFreightInsuranceAndCartageAmtPerUnit(freightInsuranceAndCartageAmtPerUnit);

		return clone;
	}

}
