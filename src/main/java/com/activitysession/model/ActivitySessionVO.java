package com.activitysession.model;

import java.sql.Date;
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
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.activity.model.ActivityVO;
import com.activityorder.model.ActivityOrderVO;

@Entity
@Table(name = "activity_session")
public class ActivitySessionVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "activity_session_id", updatable = false)
	private Integer activitySessionID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "activity_id", referencedColumnName = "activity_id")
	private ActivityVO activityVO;
	
	@Column(name = "activity_date")
	@NotNull(message="請選擇活動開始日期")	
	@Future(message="日期須在今日(不含)之後")
	private Date activityDate;
	
	@Column(name = "activity_time")
	private Byte activityTime;
	
	@Column(name = "activity_max_part")
	@NotNull(message = "請輸入活動人數上限")
	private Integer activityMaxPart;
	
//	@Column(name = "activity_min_part")
//	@NotNull(message = "請輸入活動人數下限")
//	private Integer activityMinPart;
	
	@Column(name = "activity_entered_status")
	private Byte activityEnteredStatus;
	
//	@Column(name = "activity_note")
//	private String activityNote;
	
	@Column(name = "entered_total")
	private Integer enteredTotal = 0;
	
	@Column(name = "entered_start")
	@NotNull(message="請選擇報名開始日期")	
//	@Future(message="日期須在今日(不含)之後")
	private Date enteredStart;
	
	@Column(name = "entered_end")
	@NotNull(message="請選擇報名結束日期")	
//	@Future(message="日期須在今日(不含)之後")
	private Date enteredEnd;
	
	@Column(name = "activity_session_status")
	private Boolean activitySessionStatus;
	
	@OneToMany(mappedBy = "activitySessionVO", cascade = CascadeType.ALL)
	@OrderBy("activity_order_id asc")
	private Set<ActivityOrderVO> activityOrders;

	public ActivitySessionVO() {
		super();
	}

	public Integer getActivitySessionID() {
		return activitySessionID;
	}

	public void setActivitySessionID(Integer activitySessionID) {
		this.activitySessionID = activitySessionID;
	}

	public ActivityVO getActivityVO() {
		return activityVO;
	}

	public void setActivityVO(ActivityVO activityVO) {
		this.activityVO = activityVO;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public Byte getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(Byte activityTime) {
		this.activityTime = activityTime;
	}

	public Integer getActivityMaxPart() {
		return activityMaxPart;
	}

	public void setActivityMaxPart(Integer activityMaxPart) {
		this.activityMaxPart = activityMaxPart;
	}

//	public Integer getActivityMinPart() {
//		return activityMinPart;
//	}
//
//	public void setActivityMinPart(Integer activityMinPart) {
//		this.activityMinPart = activityMinPart;
//	}

	public Byte getActivityEnteredStatus() {
		return activityEnteredStatus;
	}

	public void setActivityEnteredStatus(Byte activityEnteredStatus) {
		this.activityEnteredStatus = activityEnteredStatus;
	}

//	public String getActivityNote() {
//		return activityNote;
//	}
//
//	public void setActivityNote(String activityNote) {
//		this.activityNote = activityNote;
//	}

	public Integer getEnteredTotal() {
		return enteredTotal;
	}

	public void setEnteredTotal(Integer enteredTotal) {
		this.enteredTotal = enteredTotal;
	}

	public Date getEnteredStart() {
		return enteredStart;
	}

	public void setEnteredStart(Date enteredStart) {
		this.enteredStart = enteredStart;
	}

	public Date getEnteredEnd() {
		return enteredEnd;
	}

	public void setEnteredEnd(Date enteredEnd) {
		this.enteredEnd = enteredEnd;
	}

	public Boolean getActivitySessionStatus() {
		return activitySessionStatus;
	}

	public void setActivitySessionStatus(Boolean activitySessionStatus) {
		this.activitySessionStatus = activitySessionStatus;
	}

	public Set<ActivityOrderVO> getActivityOrders() {
		return activityOrders;
	}

	public void setActivityOrders(Set<ActivityOrderVO> activityOrders) {
		this.activityOrders = activityOrders;
	}

}
