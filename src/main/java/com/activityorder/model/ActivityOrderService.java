package com.activityorder.model;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.activity.model.ActivityVO;

@Service("aoSvc")
public class ActivityOrderService {
	
	@Autowired
	ActivityOrderRepository repository;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public void addActivityOrder(ActivityOrderVO activityOrderVO) {
		repository.save(activityOrderVO);
	}
	
	public void updateActivityOrder(ActivityOrderVO activityOrderVO) {
		repository.save(activityOrderVO);
	}
	
	public ActivityOrderVO getOneActivityOrder(Integer activityOrderID) {
		Optional<ActivityOrderVO> optional = repository.findById(activityOrderID);
		return optional.orElse(null);
	}
	
	public List<ActivityOrderVO> getAll(){
		return repository.findAll();
	}
	
	public List<ActivityOrderVO> getActivityBySession(Integer activitySessionID){
		return repository.findBySession(activitySessionID);
	}

}
