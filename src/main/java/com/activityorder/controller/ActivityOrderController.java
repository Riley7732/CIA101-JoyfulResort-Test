package com.activityorder.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.activity.model.ActivityVO;
import com.activityorder.model.ActivityOrderService;
import com.activityorder.model.ActivityOrderVO;
import com.activitysession.model.ActivitySessionService;
import com.activitysession.model.ActivitySessionVO;
import com.redis.RedisService;

@Controller
@RequestMapping("/activityorder")
public class ActivityOrderController {
	
	@Autowired
	ActivityOrderService aoSvc;
	
	@Autowired
	ActivitySessionService asSvc;
	
	@Autowired
    private RedisService redisService;
	
	@PostMapping("listAll")
	public String getALL(ModelMap model) {
		List<ActivityOrderVO> list = aoSvc.getAll();
		model.addAttribute("activityOrderListData", list);
		model.addAttribute("getAll", true);
		return "backend/activityorder/listAllActivityOrder";
	}
	
	@PostMapping("listType")
	public String getALL(@RequestParam("activitySessionID") String activitySessionID, ModelMap model) {
		List<ActivityOrderVO> list = aoSvc.getActivityBySession(Integer.valueOf(activitySessionID));
		model.addAttribute("activityOrderListData", list);
		return "backend/activityorder/listAllActivityOrder";
	}
	
	@PostMapping("updatePage")
	public String updatePage(@RequestParam("activityOrderID") String activityOrderID, ModelMap model) {
		ActivityOrderVO activityOrderVO = aoSvc.getOneActivityOrder(Integer.valueOf(activityOrderID));
		model.addAttribute("activityOrderVO", activityOrderVO);
//		System.out.println(activityOrderVO.getActivitySessionVO().getActivitySessionID());
//		ActivitySessionVO activitySessionVO = activityOrderVO.getActivitySessionVO();
//		model.addAttribute("activitySessionVO", activitySessionVO);
		
		
		return "backend/activityorder/updateActivityOrder";
	}
	
	@PostMapping("update")
	public String update(@Valid ActivityOrderVO activityOrderVO, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("activityOrderVO", activityOrderVO);
			return "backend/activityorder/updateActivityOrder";
		}
		aoSvc.updateActivityOrder(activityOrderVO);
		
		// 如果訂單狀態為2(取消中)，即已完成，則從 Redis 中減少報名人數
		String activitySessionId = String.valueOf(activityOrderVO.getActivitySessionVO().getActivitySessionID());
        if (activityOrderVO.getOrderStatus() == 2 || activityOrderVO.getOrderStatus() == 3) {
            redisService.updateActivityOrder(activitySessionId, activityOrderVO.getEnteredNumber());
        }
		
		activityOrderVO = aoSvc.getOneActivityOrder(Integer.valueOf(activityOrderVO.getActivityOrderID()));
		model.addAttribute("activityOrderVO", activityOrderVO);
		return "backend/activityorder/listOneActivityOrder";
	}
	
	@PostMapping("add")
	public String addPage(ModelMap model) {
//		ActivitySessionVO activitySessionVO = asSvc.getOneActivitySession(Integer.valueOf(activitySessionID));
//		model.addAttribute("activitySessionVO", activitySessionVO);
//		System.out.println(activitySessionVO.getActivitySessionID());
		
		// 取得當前時間戳記
	    long currentTimeMillis = System.currentTimeMillis();
	    Timestamp orderTime = new Timestamp(currentTimeMillis);
	    // 將時間戳記設置到模型中
	    model.addAttribute("orderTime", orderTime);
		
	    ActivityOrderVO activityOrderVO = new ActivityOrderVO();	
		model.addAttribute("activityOrderVO", activityOrderVO);	
		return "backend/activityorder/addActivityOrder";
	}
	
	@PostMapping("insert")
	public String insert(@Valid ActivityOrderVO activityOrderVO, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			model.addAttribute("activityOrderVO", activityOrderVO);
			return "backend/activityorder/addActivityOrder";
		}
		
		// 獲取當前活動場次的報名人數
	    String activitySessionId = String.valueOf(activityOrderVO.getActivitySessionVO().getActivitySessionID());
//	    System.out.println(activitySessionId);
	    int currentTotal = redisService.getEnteredTotal(activitySessionId);
//	    System.out.println(currentTotal);
	    int enteredNumber = activityOrderVO.getEnteredNumber();
	    // 獲取當前活動場次的報名人數上限
	    ActivitySessionVO asVO = asSvc.getOneActivitySession(Integer.valueOf(activitySessionId));
	    int maxTotal = asVO.getActivityMaxPart();

	    // 檢查報名人數是否已滿
	    if (currentTotal + enteredNumber > maxTotal) {
	        // 已滿，不能新增
	        // 返回錯誤信息或重定向到適當的頁面
	    	int leftTotal = maxTotal - currentTotal;
	        model.addAttribute("errorMessage", "報名人數剩 " + leftTotal + " 人");
	        return "backend/activityorder/addActivityOrder"; // 需要創建一個錯誤頁面或適當處理
	    }
		
		aoSvc.addActivityOrder(activityOrderVO);
		
		// 更新 Redis 中的報名人數
        String activitySession = String.valueOf(activityOrderVO.getActivitySessionVO().getActivitySessionID());
        redisService.addActivityOrder(activitySession, activityOrderVO.getEnteredNumber());
		
		ActivityOrderVO activityOrder = aoSvc.getOneActivityOrder(Integer.valueOf(activityOrderVO.getActivityOrderID()));
		model.addAttribute("activityOrderVO", activityOrder);
		List<ActivityOrderVO> list = aoSvc.getAll();
		model.addAttribute("activityOrderListData", list);
		
		return "backend/activityorder/listOneActivityOrder";
	}
	
	@PostMapping("getActivityPrice")
	public void getActivityPrice(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("application/json; charset=UTF-8");
		
		String activitySessionID = req.getParameter("activitySessionID");
//		System.out.println(activitySessionID);
		
		String enteredNumber = req.getParameter("enteredNumber");
//		System.out.println(enteredNumber);
		
		ActivitySessionVO asVO = asSvc.getOneActivitySession(Integer.valueOf(activitySessionID));
		Integer acPrice = asVO.getActivityVO().getActivityPrice();
		Integer activityPrice = acPrice * (Integer.valueOf(enteredNumber));
//		System.out.println(activityPrice);
		// 把金額回傳到前端
		JSONObject obj = new JSONObject();
		obj.put("activityPrice", activityPrice);
		res.getWriter().print(obj);
	}
	
	@PostMapping("getLeftTotal")
	public void getLeftTotal(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("application/json; charset=UTF-8");
		
		String activitySessionID = req.getParameter("activitySessionID");
//		System.out.println(activitySessionID);
		
		// 獲取當前活動場次的剩餘報名人數
	    int currentTotal = redisService.getEnteredTotal(activitySessionID);
	    // 獲取當前活動場次的報名人數上限
	    ActivitySessionVO asVO = asSvc.getOneActivitySession(Integer.valueOf(activitySessionID));
	    int maxTotal = asVO.getActivityMaxPart();
	    
	    int leftTotal = maxTotal - currentTotal;
//	    System.out.println(leftTotal);
	    // 把剩餘報名人數回傳到前端
	    JSONObject obj = new JSONObject();
		obj.put("leftTotal", leftTotal);
		res.getWriter().print(obj);
		
	}
	
	// 讓activity裡也能取得activitysession的資訊，可以在html中使用
	@ModelAttribute("activitySessionListData")
	protected List<ActivitySessionVO> referenceActivitySessionListData() {
		List<ActivitySessionVO> list = asSvc.getAll();
		return list;
	}
	
	// ================================================================================
	
	@PostMapping("addOrder")
	public String addOrder(ModelMap model) {
//		ActivitySessionVO activitySessionVO = asSvc.getOneActivitySession(Integer.valueOf(activitySessionID));
//		model.addAttribute("activitySessionVO", activitySessionVO);
//		System.out.println(activitySessionVO.getActivitySessionID());
		
		// 取得當前時間戳記
	    long currentTimeMillis = System.currentTimeMillis();
	    Timestamp orderTime = new Timestamp(currentTimeMillis);
	    // 將時間戳記設置到模型中
	    model.addAttribute("orderTime", orderTime);
		
	    ActivityOrderVO activityOrderVO = new ActivityOrderVO();	
		model.addAttribute("activityOrderVO", activityOrderVO);	
		return "front-end/joyfulresortactivity/participate";
	}
	
	@PostMapping("insertOrder")
	public String insertOrder(@Valid ActivityOrderVO activityOrderVO, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			model.addAttribute("activityOrderVO", activityOrderVO);
			return "front-end/joyfulresortactivity/participate";
		}
		
		// 獲取當前活動場次的報名人數
	    String activitySessionId = String.valueOf(activityOrderVO.getActivitySessionVO().getActivitySessionID());
//	    System.out.println(activitySessionId);
	    int currentTotal = redisService.getEnteredTotal(activitySessionId);
//	    System.out.println(currentTotal);
	    int enteredNumber = activityOrderVO.getEnteredNumber();
	    // 獲取當前活動場次的報名人數上限
	    ActivitySessionVO asVO = asSvc.getOneActivitySession(Integer.valueOf(activitySessionId));
	    int maxTotal = asVO.getActivityMaxPart();

	    // 檢查報名人數是否已滿
	    if (currentTotal + enteredNumber > maxTotal) {
	        // 已滿，不能新增
	        // 返回錯誤信息或重定向到適當的頁面
	    	int leftTotal = maxTotal - currentTotal;
	        model.addAttribute("errorMessage", "報名人數剩 " + leftTotal + " 人");
	        return "front-end/joyfulresortactivity/participate"; // 需要創建一個錯誤頁面或適當處理
	    }
		
		aoSvc.addActivityOrder(activityOrderVO);
		
		// 更新 Redis 中的報名人數
        String activitySession = String.valueOf(activityOrderVO.getActivitySessionVO().getActivitySessionID());
        redisService.addActivityOrder(activitySession, activityOrderVO.getEnteredNumber());
		
		ActivityOrderVO activityOrder = aoSvc.getOneActivityOrder(Integer.valueOf(activityOrderVO.getActivityOrderID()));
		model.addAttribute("activityOrderVO", activityOrder);
		List<ActivityOrderVO> list = aoSvc.getAll();
		model.addAttribute("activityOrderListData", list);
		
		return "backend/activityorder/listOneActivityOrder";
	}

}
