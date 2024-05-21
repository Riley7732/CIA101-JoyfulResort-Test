package com.activityorder.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.activitysession.model.ActivitySessionVO;

@Entity
@Table(name = "activity_order")
public class ActivityOrderVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "activity_order_id", updatable = false)
	private Integer activityOrderID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "activity_session_id", referencedColumnName = "activity_session_id")
	private ActivitySessionVO activitySessionVO;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "member_id", referencedColumnName = "member_id")
//	private Member member;
	
	@Column(name = "entered_number")
	@NotNull(message = "請輸入報名人數")
	private Integer enteredNumber;
	
//	@Column(name = "entered_child_number")
//	private Integer enteredChildNumber;
	
	@Column(name = "order_amount")
	@NotNull(message = "請確認報名場次及人數")
	private Integer orderAmount;
	
	@Column(name = "order_status")
	private Byte orderStatus = 1;
	
	@Column(name = "order_time")
	private Timestamp orderTime = new Timestamp(System.currentTimeMillis());
	
	@Column(name = "order_note")
	private String orderNote;
	
	@Column(name = "refund_status")
	private Byte refundStatus = 0;

	public ActivityOrderVO() {
		super();
	}

	public Integer getActivityOrderID() {
		return activityOrderID;
	}

	public void setActivityOrderID(Integer activityOrderID) {
		this.activityOrderID = activityOrderID;
	}

	public ActivitySessionVO getActivitySessionVO() {
		return activitySessionVO;
	}

	public void setActivitySessionVO(ActivitySessionVO activitySessionVO) {
		this.activitySessionVO = activitySessionVO;
	}

//	public Member getMember() {
//		return member;
//	}
//
//	public void setMember(Member member) {
//		this.member = member;
//	}

	public Integer getEnteredNumber() {
		return enteredNumber;
	}

	public void setEnteredNumber(Integer enteredNumber) {
		this.enteredNumber = enteredNumber;
	}

//	public Integer getEnteredChildNumber() {
//		return enteredChildNumber;
//	}
//
//	public void setEnteredChildNumber(Integer enteredChildNumber) {
//		this.enteredChildNumber = enteredChildNumber;
//	}

	public Integer getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Byte getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Byte orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderNote() {
		return orderNote;
	}

	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}

	public Byte getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Byte refundStatus) {
		this.refundStatus = refundStatus;
	}
	
}
