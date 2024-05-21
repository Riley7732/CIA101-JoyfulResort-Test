package com;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.activity.model.ActivityRepository;
import com.activitycategory.model.ActivityCategoryRepository;
import com.activityorder.model.ActivityOrderRepository;
import com.activityphoto.model.ActivityPhotoRepository;
import com.activitysession.model.ActivitySessionRepository;

import jedis.connectionpool.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@SpringBootApplication
public class Test_Application_CommandLineRunner implements CommandLineRunner {
	
	@Autowired
	ActivityCategoryRepository repository1;
	
	@Autowired
	ActivityRepository repository2;
	
	@Autowired
	ActivityPhotoRepository repository3;
	
	@Autowired
	ActivitySessionRepository repository4;
	
	@Autowired
	ActivityOrderRepository repository5;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	private static JedisPool pool = null;
	
	public static void main(String[] args) {
        SpringApplication.run(Test_Application_CommandLineRunner.class);
    }

    @Override
    public void run(String...args) throws Exception {
    	
//    	Optional<ActivityCategoryVO> optional1 = repository1.findById(1);
//    	ActivityCategoryVO activityCategoryVO = optional1.get();
//    	System.out.println("活動類別編號：" + activityCategoryVO.getActivityCategoryID() + " " + "活動類別名稱：" + activityCategoryVO.getActivityCategoryName());
    	
//    	Optional<ActivityVO> optional2 = repository2.findById(1);
//    	ActivityVO activityVO = optional2.get();
//    	System.out.println("活動編號：" + activityVO.getActivityID() + " " + "活動名稱：" + activityVO.getActivityName());

//    	Optional<ActivityPhotoVO> optional3 = repository3.findById(1);
//    	ActivityPhotoVO activityPhotoVO = optional3.get();
//    	System.out.println("活動圖片編號：" + activityPhotoVO.getActivityPhotoID() + " " + "活動編號：" + activityPhotoVO.getActivityVO().getActivityID());
    
//    	Optional<ActivitySessionVO> optional4 = repository4.findById(1);
//    	ActivitySessionVO activitySessionVO = optional4.get();
//    	System.out.println("活動場次編號：" + activitySessionVO.getActivitySessionID());
    	
//    	Optional<ActivityOrderVO> optional5 = repository5.findById(1);
//    	ActivityOrderVO activityOrderVO = optional5.get();
//    	System.out.println("活動訂單編號：" + activityOrderVO.getActivityOrderID());
    	
//    	pool = JedisUtil.getJedisPool();
//		Jedis jedis = pool.getResource();
//		System.out.println(jedis.ping());
//
//		jedis.close();
//		JedisUtil.shutdownJedisPool();
    	
    }

}
