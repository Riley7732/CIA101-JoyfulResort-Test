package com.activityphoto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.activity.model.ActivityVO;

@Entity
@Table(name = "activity_photo")
public class ActivityPhotoVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "activity_photo_id", updatable = false)
	private Integer activityPhotoID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "activity_id", referencedColumnName = "activity_id")
	private ActivityVO activityVO;
	
	@Column(name = "activity_photo", columnDefinition = "LONGBLOB")
	private byte[] activityPhoto;

	public ActivityPhotoVO() {
		super();
	}

	public Integer getActivityPhotoID() {
		return activityPhotoID;
	}

	public void setActivityPhotoID(Integer activityPhotoID) {
		this.activityPhotoID = activityPhotoID;
	}

	public ActivityVO getActivityVO() {
		return activityVO;
	}

	public void setActivityVO(ActivityVO activityVO) {
		this.activityVO = activityVO;
	}

	public byte[] getActivityPhoto() {
		return activityPhoto;
	}

	public void setActivityPhoto(byte[] activityPhoto) {
		this.activityPhoto = activityPhoto;
	}
	
}
