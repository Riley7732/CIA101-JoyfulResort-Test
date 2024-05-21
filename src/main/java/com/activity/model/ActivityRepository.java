package com.activity.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ActivityRepository extends JpaRepository<ActivityVO, Integer> {
	
	@Transactional
	@Query(value = "SELECT * from activity where activity_category_id =?1", nativeQuery = true)
	List<ActivityVO> findByCategory(Integer activityCategoryID);

}
