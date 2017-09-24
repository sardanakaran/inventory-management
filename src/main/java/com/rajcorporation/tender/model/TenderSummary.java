/**
 * 
 */
package com.rajcorporation.tender.model;

/**
 * @author Karan
 *
 */
public class TenderSummary {
	int numberOfTotalDataInspectionInstances;
	int numberOfFailedDataInspectionInstances;
	int numberOfPassedDataInspectionInstances;
	int numberOfFilesAttached;
	int numberOfBoqItems;
	double totalCost;
	Tender tender;

	public int getNumberOfTotalDataInspectionInstances() {
		return numberOfTotalDataInspectionInstances;
	}

	public void setNumberOfTotalDataInspectionInstances(int numberOfTotalDataInspectionInstances) {
		this.numberOfTotalDataInspectionInstances = numberOfTotalDataInspectionInstances;
	}

	public int getNumberOfFilesAttached() {
		return numberOfFilesAttached;
	}

	public void setNumberOfFilesAttached(int numberOfFilesAttached) {
		this.numberOfFilesAttached = numberOfFilesAttached;
	}

	public int getNumberOfBoqItems() {
		return numberOfBoqItems;
	}

	public void setNumberOfBoqItems(int numberOfBoqItems) {
		this.numberOfBoqItems = numberOfBoqItems;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public Tender getTender() {
		return tender;
	}

	public void setTender(Tender tender) {
		this.tender = tender;
	}

	public int getNumberOfFailedDataInspectionInstances() {
		return numberOfFailedDataInspectionInstances;
	}

	public void setNumberOfFailedDataInspectionInstances(int numberOfFailedDataInspectionInstances) {
		this.numberOfFailedDataInspectionInstances = numberOfFailedDataInspectionInstances;
	}

	public int getNumberOfPassedDataInspectionInstances() {
		return numberOfPassedDataInspectionInstances;
	}

	public void setNumberOfPassedDataInspectionInstances(int numberOfPassedDataInspectionInstances) {
		this.numberOfPassedDataInspectionInstances = numberOfPassedDataInspectionInstances;
	}

}
