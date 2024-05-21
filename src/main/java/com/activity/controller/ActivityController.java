package com.activity.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.activity.model.ActivityService;
import com.activity.model.ActivityVO;
import com.activitycategory.model.ActivityCategoryService;
import com.activitycategory.model.ActivityCategoryVO;

@Controller
@RequestMapping("/activity")
public class ActivityController {
	
	@Autowired
	ActivityService aSvc;
	
	@Autowired
	ActivityCategoryService acSvc;
	
//	@PostMapping("listAll")
//	public String getALL(ModelMap model) {
//		List<ActivityVO> list = aSvc.getAll();
//		model.addAttribute("activityListData", list);
//		// 讓th:if="${getAll}"得到true，可以insert在查詢的同一頁面
//		model.addAttribute("getAll", true);
//		return "backend/activity/activity";
//	}
	
	@PostMapping("listOne")
	public String getOne(@RequestParam("activityID") String activityID, ModelMap model) {
		ActivityVO activityVO = aSvc.getOneActivity(Integer.valueOf(activityID));
		model.addAttribute("activityVO", activityVO);
		// 查詢單一活動後，查詢選單才能保留資料
		List<ActivityVO> list = aSvc.getAll();
		model.addAttribute("activityListData", list);
		model.addAttribute("getOne", true);
		return "backend/activity/listOneActivity";
	}
	
	@PostMapping("listType")
	public String getALL(@RequestParam("activityCategoryID") String activityCategoryID, ModelMap model) {
		List<ActivityVO> list = aSvc.getActivityByCategory(Integer.valueOf(activityCategoryID));
		model.addAttribute("activityListData", list);
		return "backend/activity/listAllActivity";
	}
	
	@PostMapping("updatePage")
	public String updatePage(@RequestParam("activityID") String activityID, ModelMap model) {
		ActivityVO activityVO = aSvc.getOneActivity(Integer.valueOf(activityID));
		model.addAttribute("activityVO", activityVO);
		return "backend/activity/updateActivity";
	}
	
	@PostMapping("update")
	public String update(@Valid ActivityVO activityVO, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("activityVO", activityVO);
			return "backend/activity/updateActivity";
		}
		aSvc.updateActivity(activityVO);
		activityVO = aSvc.getOneActivity(Integer.valueOf(activityVO.getActivityID()));
		model.addAttribute("activityVO", activityVO);
		return "backend/activity/listOneActivity";
	}
	
	@PostMapping("add")
	public String addPage(ModelMap model) {
		ActivityVO activityVO = new ActivityVO();
		model.addAttribute("activityVO", activityVO);
		
		ActivityCategoryVO activityCategoryVO = new ActivityCategoryVO();
		model.addAttribute("activityCategoryVO", activityCategoryVO);
		
		return "backend/activity/addActivity";
	}
	
	@PostMapping("insert")
	public String insert(@Valid ActivityVO activityVO, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("activityVO", activityVO);
			return "backend/activity/addActivity";
		}
		aSvc.addActivity(activityVO);
		
		ActivityVO activity = aSvc.getOneActivity(Integer.valueOf(activityVO.getActivityID()));
		model.addAttribute("activityVO", activity);
		List<ActivityVO> list = aSvc.getAll();
		model.addAttribute("activityListData", list);
//		model.addAttribute("activityCategoryVO", new ActivityCategoryVO());
//		List<ActivityCategoryVO> aclist = acSvc.getAll();
//		model.addAttribute("activityCategoryListData", aclist);
//		for (ActivityCategoryVO vo : aclist) {
//			System.out.print(vo.getActivityCategoryName());
//		}
//		for(ActivityVO ac :list) {
//			System.out.print(ac.getActivityCategoryVO().getActivityCategoryName());
//		}
//		model.addAttribute("getOne", true);
//		return "backend/activity/activity";
		return "backend/activity/listOneActivity";
	}
	
	// 讓activity裡也能取得activitycategory的資訊，可以在html中使用
	@ModelAttribute("activityCategoryListData")
	protected List<ActivityCategoryVO> referenceActivityCategoryListData() {
		List<ActivityCategoryVO> list = acSvc.getAll();
		return list;
	}

}
