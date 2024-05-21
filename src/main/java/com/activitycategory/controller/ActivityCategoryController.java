package com.activitycategory.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.activitycategory.model.ActivityCategoryService;
import com.activitycategory.model.ActivityCategoryVO;

@Controller
@RequestMapping("/activitycategory")
public class ActivityCategoryController {
	
	@Autowired
	ActivityCategoryService acSvc;
	
//	@PostMapping("listAll")
//	public String getALL(ModelMap model) {
//		List<ActivityCategoryVO> list = acSvc.getAll();
//		model.addAttribute("activityCategoryListData", list);
//		model.addAttribute("getAll", true);	//讓listAllActivityCategory可以Insert在activitycategory頁面中
//		return "backend/activitycategory/activitycategory";
//	}
	
	@PostMapping("listOne")
	public String getOne(@RequestParam("activityCategoryID") String activityCategoryID, ModelMap model) {
		ActivityCategoryVO activityCategoryVO = acSvc.getOneActivityCategory(Integer.valueOf(activityCategoryID));
		List<ActivityCategoryVO> list = acSvc.getAll();
		model.addAttribute("activityCategoryVO", activityCategoryVO);
		model.addAttribute("activityCategoryListData", list);
		model.addAttribute("getOne", true);
		return "backend/activitycategory/listOneActivityCategory";
	}
	
	@PostMapping("updatePage")
	public String updatePage(@RequestParam("activityCategoryID") String activityCategoryID, ModelMap model) {
		ActivityCategoryVO activityCategoryVO = acSvc.getOneActivityCategory(Integer.valueOf(activityCategoryID));
		model.addAttribute("activityCategoryVO", activityCategoryVO);
		return "backend/activitycategory/updateActivityCategory";
	}
	
	@PostMapping("update")
	public String update(@Valid ActivityCategoryVO activityCategoryVO, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("activityCategoryVO", activityCategoryVO);
			return "backend/activitycategory/updateActivityCategory";
		}
		acSvc.updateActivityCategory(activityCategoryVO);
		activityCategoryVO = acSvc.getOneActivityCategory(Integer.valueOf(activityCategoryVO.getActivityCategoryID()));
		model.addAttribute("activityCategoryVO", activityCategoryVO);
		return "backend/activitycategory/listOneActivityCategory";
	}
	
	@PostMapping("add")
	public String addPage(ModelMap model) {
		ActivityCategoryVO activityCategoryVO = new ActivityCategoryVO();
		model.addAttribute("activityCategoryVO", activityCategoryVO);
		return "backend/activitycategory/addActivityCategory";
	}
	
	@PostMapping("insert")
	public String insert(@Valid ActivityCategoryVO activityCategoryVO, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("activityCategoryVO", activityCategoryVO);
			return "backend/activitycategory/addActivityCategory";
		}
		acSvc.addActivityCategory(activityCategoryVO);
		List<ActivityCategoryVO> list = acSvc.getAll();
		model.addAttribute("activityCategoryListData", list);
		model.addAttribute("getAll", true);
		return "backend/activitycategory/listOneActivityCategory";
//		model.addAttribute("activityCategoryVO", activityCategoryVO);
//		return "backend/activitycategory/listAllActivityCategory";
	}

}
