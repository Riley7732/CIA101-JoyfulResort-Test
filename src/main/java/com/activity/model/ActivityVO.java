package com.activity.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.activitycategory.model.ActivityCategoryVO;
import com.activityphoto.model.ActivityPhotoVO;
import com.activitysession.model.ActivitySessionVO;

@Entity
@Table(name = "activity")
public class ActivityVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "activity_id", updatable = false)
	private Integer activityID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "activity_category_id", referencedColumnName = "activity_category_id")
	private ActivityCategoryVO activityCategoryVO;
	
	@Column(name = "activity_name")
	@NotEmpty(message="請填寫活動名稱")
	private String activityName;
	
	@Column(name = "activity_price")
	@NotNull(message="請填寫活動價格")
	private Integer activityPrice;
	
	@Column(name = "activity_info")
	private String activityInfo;
	
//	@Column(name = "activity_notice")
//	private String activityNotice;
	
	@Column(name = "activity_status")
	private Boolean activityStatus;
	
	@OneToMany(mappedBy = "activityVO", cascade = CascadeType.ALL)
	@OrderBy("activity_photo_id asc")
	private Set<ActivityPhotoVO> activityPhotos;
	
	@OneToMany(mappedBy = "activityVO", cascade = CascadeType.ALL)
	@OrderBy("activity_session_id asc")
	private Set<ActivitySessionVO>  activitySessions;

	public ActivityVO() {
		super();
	}

	public Integer getActivityID() {
		return activityID;
	}

	public void setActivityID(Integer activityID) {
		this.activityID = activityID;
	}

	public ActivityCategoryVO getActivityCategoryVO() {
		return activityCategoryVO;
	}

	public void setActivityCategoryVO(ActivityCategoryVO activityCategoryVO) {
		this.activityCategoryVO = activityCategoryVO;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Integer getActivityPrice() {
		return activityPrice;
	}

	public void setActivityPrice(Integer activityPrice) {
		this.activityPrice = activityPrice;
	}

	public String getActivityInfo() {
		return activityInfo;
	}

	public void setActivityInfo(String activityInfo) {
		this.activityInfo = activityInfo;
	}

//	public String getActivityNotice() {
//		return activityNotice;
//	}
//
//	public void setActivityNotice(String activityNotice) {
//		this.activityNotice = activityNotice;
//	}

	public Boolean getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(Boolean activityStatus) {
		this.activityStatus = activityStatus;
	}

	public Set<ActivityPhotoVO> getActivityPhotos() {
		return activityPhotos;
	}

	public void setActivityPhotos(Set<ActivityPhotoVO> activityPhotos) {
		this.activityPhotos = activityPhotos;
	}

	public Set<ActivitySessionVO> getActivitySessions() {
		return activitySessions;
	}

	public void setActivitySessions(Set<ActivitySessionVO> activitySessions) {
		this.activitySessions = activitySessions;
	}
	
}
