package com.activityphoto.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ActivityPhotoRepository extends JpaRepository<ActivityPhotoVO, Integer> {
	
	@Transactional
	@Query(value = "SELECT * from activity_photo where activity_id =?1", nativeQuery = true)
	List<ActivityPhotoVO> findByActivity(Integer activityID);

}
