package com.activityphoto.model;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("apSvc")
public class ActivityPhotoService {
	
	@Autowired
	ActivityPhotoRepository repository;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public void addActivityPhoto(ActivityPhotoVO activityPhotoVO) {
		repository.save(activityPhotoVO);
	}
	
	public void updateActivityPhoto(ActivityPhotoVO activityPhotoVO) {
		repository.save(activityPhotoVO);
	}
	
	public ActivityPhotoVO getOneActivityPhoto(Integer activityPhotoID) {
		Optional<ActivityPhotoVO> optional = repository.findById(activityPhotoID);
		return optional.orElse(null);
	}
	
	public List<ActivityPhotoVO> getAll() {
		return repository.findAll();
	}
	
	public List<ActivityPhotoVO> getPhotoByActivity(Integer activityID){
		return repository.findByActivity(activityID);
	}

}
