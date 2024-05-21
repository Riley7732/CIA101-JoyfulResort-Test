package com;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.activity.model.ActivityService;
import com.activity.model.ActivityVO;
import com.activitycategory.model.ActivityCategoryService;
import com.activitycategory.model.ActivityCategoryVO;
import com.activityorder.model.ActivityOrderService;
import com.activityorder.model.ActivityOrderVO;
import com.activityphoto.model.ActivityPhotoService;
import com.activityphoto.model.ActivityPhotoVO;
import com.activitysession.model.ActivitySessionService;
import com.activitysession.model.ActivitySessionVO;

@Controller
public class IndexController_inSpringBoot {
	
	@Autowired
	ActivityCategoryService acSvc;
	
	@Autowired
	ActivityService aSvc;
	
	@Autowired
	ActivityPhotoService apSvc;
	
	@Autowired
	ActivitySessionService asSvc;
	
	@Autowired
	ActivityOrderService aoSvc;
	
	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}
	
	// ======================================== 前台首頁 ======================================== //
		@GetMapping("/front_main_page")
		public String frontMainPage(Model model) {
			return "front-end/test";
		}
	
	// ======================================== 後台首頁 ======================================== //
	@GetMapping("/main_page")
	public String mainPage(Model model) {
		return "backend/main_page";
	}
	
	// ============================== ============================== ============================== //
	
	
	// ======================================== 前台頁面 ======================================== //
	@GetMapping("/activityinfo")
	public String activityInfo(Model model) {
		return "front-end/joyfulresortactivity/activityinfo";
	}
	
	@GetMapping("/joyfulactivity")
	public String joyfulActivity(Model model) {
		return "front-end/joyfulresortactivity/joyfulactivity";
	}
	
	@GetMapping("/participate")
	public String participate(Model model) {
		return "front-end/joyfulresortactivity/participate";
	}
	
	
	// ============================== ============================== ============================== //
	
	
	// ======================================== 後台控制器 ======================================== //
	@GetMapping("/activitycategory/activitycategory")
	public String activityCategory(Model model) {
		return "backend/activitycategory/activitycategory";
	}
	
	@GetMapping("/activity/activity")
	public String activity(Model model) {
		return "backend/activity/activity";
	}

	@GetMapping("/activityphoto/activityphoto")
	public String activityPhoto(Model model) {
		return "backend/activityphoto/activityphoto";
	}
	
	@GetMapping("/activitysession/activitysession")
	public String activitySession(Model model) {
		return "backend/activitysession/activitysession";
	}
	
	@GetMapping("/activityorder/activityorder")
	public String activityOrder(Model model) {
		return "backend/activityorder/activityorder";
	}
	
	// ============================== 讓GetMapping的頁面載入資料 ============================== //
	
	@ModelAttribute("activityCategoryListData")
	protected List<ActivityCategoryVO> referenceActivityCategoryListData(Model model) {
    	List<ActivityCategoryVO> activityCategoryListData = acSvc.getAll();
		return activityCategoryListData;
	}
	
	@ModelAttribute("activityListData")
	protected List<ActivityVO> referenceActivityListData(Model model) {
    	List<ActivityVO> activityListData = aSvc.getAll();
		return activityListData;
	}
	
	@ModelAttribute("activityPhotoListData")
	protected List<ActivityPhotoVO> referenceActivityPhotoListData(Model model) {
    	List<ActivityPhotoVO> activityPhotoVOListData = apSvc.getAll();
		return activityPhotoVOListData;
	}
	
	@ModelAttribute("activitySessionListData")
	protected List<ActivitySessionVO> referenceActivitySessionListData(Model model) {
    	List<ActivitySessionVO> activitySessionListData = asSvc.getAll();
		return activitySessionListData;
	}
	
	@ModelAttribute("activityOrderListData")
	protected List<ActivityOrderVO> referenceActivityOrderListData(Model model) {
    	List<ActivityOrderVO> activityOrderListData = aoSvc.getAll();
		return activityOrderListData;
	}
	// ============================== ============================== ============================== //

	
}
