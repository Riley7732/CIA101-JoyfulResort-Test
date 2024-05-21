package com.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jedis.connectionpool.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

	private final JedisPool pool = JedisUtil.getJedisPool();
	private Jedis jedis;

	@Autowired
	public RedisService() {
		this.jedis = pool.getResource();
	}

	// 新增活動訂單時更新 Redis 中的報名人數
	public void addActivityOrder(String activitySessionId, int enteredNumber) {
		jedis.hincrBy("activitySession:" + activitySessionId, "enteredNumber", enteredNumber);
	}

	// 根據活動場次減少報名人數
	public void updateActivityOrder(String activitySessionId, int enteredNumber) {
		String key = "activitySession:" + activitySessionId;
		jedis.hincrBy(key, "enteredNumber", -enteredNumber);
	}

	// 從 Redis 中獲取當前活動場次的報名人數
	public int getEnteredTotal(String activitySessionId) {
		String key = "activitySession:" + activitySessionId;
		String field = "enteredNumber";
		String total = jedis.hget(key, field);
//		System.out.println(total);
		return total != null ? Integer.parseInt(total) : 0;
	}

}
