package com.activitysession.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.activityorder.model.ActivityOrderVO;

import hibernate.util.CompositeQuery.HibernateUtilCompositeQueryActivitySession;

@Service("asSvc")
public class ActivitySessionService {
	
	@Autowired
	ActivitySessionRepository repository;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addActivitySession(ActivitySessionVO activitySessionVO) {
		repository.save(activitySessionVO);
	}
	
	public void updateActivitySession(ActivitySessionVO activitySessionVO) {
		repository.save(activitySessionVO);
	}
	
	public ActivitySessionVO getOneActivitySession(Integer activitySessionID) {
		Optional<ActivitySessionVO> optional = repository.findById(activitySessionID);
		return optional.orElse(null);
	}
	
	public List<ActivitySessionVO> getAll(){
		return repository.findAll();
	}
	
	public List<ActivitySessionVO> getAll(Map<String, String[]> map) {
		return HibernateUtilCompositeQueryActivitySession.getAllC(map,sessionFactory.openSession());
	}
	
	public List<ActivitySessionVO> getAllByDate(Date activityDate){
		return repository.findByDate(activityDate);
	}
}
