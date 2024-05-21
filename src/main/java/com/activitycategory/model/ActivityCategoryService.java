package com.activitycategory.model;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("acSvc")
public class ActivityCategoryService {
	
	@Autowired
	ActivityCategoryRepository repository;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public void addActivityCategory(ActivityCategoryVO activityCategoryVO) {
		repository.save(activityCategoryVO);
	}
	
	public void updateActivityCategory(ActivityCategoryVO activityCategoryVO) {
		repository.save(activityCategoryVO);
	}
	
	public ActivityCategoryVO getOneActivityCategory(Integer activityCategoryID) {
		Optional<ActivityCategoryVO> optional = repository.findById(activityCategoryID);
		return optional.orElse(null);
	}
	
	public List<ActivityCategoryVO> getAll() {
		return repository.findAll();
	}

}
