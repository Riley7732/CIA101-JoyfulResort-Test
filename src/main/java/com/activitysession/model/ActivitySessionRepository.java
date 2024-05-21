package com.activitysession.model;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ActivitySessionRepository extends JpaRepository<ActivitySessionVO, Integer> {
	
	@Transactional
	@Query(value = "SELECT * from activity_session where activity_date =?1", nativeQuery = true)
	List<ActivitySessionVO> findByDate(Date activityDate);

}
