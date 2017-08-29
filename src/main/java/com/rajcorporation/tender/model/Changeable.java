/**
 * 
 */
package com.rajcorporation.tender.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.security.core.context.SecurityContextHolder;

import com.rajcorporation.tender.security.auth.model.User;

/**
 * @author Karan
 *
 */
@MappedSuperclass
public abstract class Changeable {

	String createdBy;

	public Date createdDateTime;

	String modifiedBy;

	public Date modifiedDateTime;

	@PrePersist
	public void createdDateTime() {
		this.createdDateTime = this.modifiedDateTime = new Date();
		User user = getLoggedInUser();
		this.createdBy = user.getUsername();
		this.modifiedBy = user.getUsername();
	}

	private User getLoggedInUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	@PreUpdate
	public void modifiedTime() {
		this.modifiedDateTime = new Date();
		User user = getLoggedInUser();
		this.modifiedBy = user.getUsername();
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public Date getModifiedDateTime() {
		return modifiedDateTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

}
