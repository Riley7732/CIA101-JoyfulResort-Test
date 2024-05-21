package com.activity.model;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("aSvc")
public class ActivityService {
	
	@Autowired
	ActivityRepository repository;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public void addActivity(ActivityVO activityVO) {
//		System.out.println(activityVO.getActivityCategoryVO().getActivityCategoryName());
		
		repository.save(activityVO);
	}
	
	public void updateActivity(ActivityVO activityVO) {
		repository.save(activityVO);
	}
	
	public ActivityVO getOneActivity(Integer activityID) {
		Optional<ActivityVO> optional = repository.findById(activityID);
		return optional.orElse(null);
	}
	
	public List<ActivityVO> getAll(){
		List<ActivityVO> list =repository.findAll();
//		for(ActivityVO ac :list) {
//			System.out.print(ac.getActivityCategoryVO().getActivityCategoryName());
//		}
		return list;
	}
	
	public List<ActivityVO> getActivityByCategory(Integer activityCategoryID){
		return repository.findByCategory(activityCategoryID);
	}

}
