package com.activitycategory.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.activity.model.ActivityVO;

@Entity
@Table(name = "activity_category")
public class ActivityCategoryVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "activity_category_id", updatable = false)
	private Integer activityCategoryID;

	@Column(name = "activity_category_name")
	@NotEmpty(message="請填寫活動類別名稱")
	private String activityCategoryName;

	@Column(name = "activity_category_info")
	private String activityCategoryInfo;
	
	@OneToMany(mappedBy = "activityCategoryVO", cascade = CascadeType.ALL)
	@OrderBy("activity_id asc")
	private Set<ActivityVO> activities;

	public ActivityCategoryVO() {
		super();
	}

	public Integer getActivityCategoryID() {
		return activityCategoryID;
	}

	public void setActivityCategoryID(Integer activityCategoryID) {
		this.activityCategoryID = activityCategoryID;
	}

	public String getActivityCategoryName() {
		return activityCategoryName;
	}

	public void setActivityCategoryName(String activityCategoryName) {
		this.activityCategoryName = activityCategoryName;
	}

	public String getActivityCategoryInfo() {
		return activityCategoryInfo;
	}

	public void setActivityCategoryInfo(String activityCategoryInfo) {
		this.activityCategoryInfo = activityCategoryInfo;
	}

	public Set<ActivityVO> getActivities() {
		return activities;
	}

	public void setActivities(Set<ActivityVO> activities) {
		this.activities = activities;
	}
	
	
}
