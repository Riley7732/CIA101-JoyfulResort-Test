package com.activityphoto.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.activity.model.ActivityService;
import com.activity.model.ActivityVO;
import com.activityphoto.model.ActivityPhotoService;
import com.activityphoto.model.ActivityPhotoVO;

@Controller
@RequestMapping("/activityphoto")
public class ActivityPhotoController {
	
	@Autowired
	ActivityPhotoService apSvc;
	
	@Autowired
	ActivityService aSvc;
	
	@PostMapping("listAll")
	public String getALL(ModelMap model) {
		List<ActivityPhotoVO> list = apSvc.getAll();
		model.addAttribute("activityPhotoListData", list);
		model.addAttribute("getAll", true);
		return "backend/activityphoto/activityphoto";
	}
	
//	@PostMapping("listOne")
//	public String getOne(@RequestParam("activityPhotoID") String activityPhotoID, ModelMap model) {
//		ActivityPhotoVO activityPhotoVO = apSvc.getOneActivityPhoto(Integer.valueOf(activityPhotoID));
//		List<ActivityPhotoVO> list = apSvc.getAll();
//		model.addAttribute("activityPhotoListData", list);
//		model.addAttribute("activityPhotoVO", activityPhotoVO);
//		model.addAttribute("getOne", true);
//		return "backend/activityphoto/activityphoto";
//	}
	
	@PostMapping("listType")
	public String getALL(@RequestParam("activityID") String activityID, ModelMap model) {
		List<ActivityPhotoVO> list = apSvc.getPhotoByActivity(Integer.valueOf(activityID));
		model.addAttribute("activityPhotoListData", list);
		
		if (list.isEmpty()) {
			model.addAttribute("errorMessage", "查無資料");
			return "backend/activityphoto/activityphoto";
		}
		
		return "backend/activityphoto/listAllActivityPhoto";
	}
	
	@PostMapping("updatePage")
	public String updatePage(@RequestParam("activityPhotoID") String activityPhotoID, ModelMap model) {
		ActivityPhotoVO activityPhotoVO = apSvc.getOneActivityPhoto(Integer.valueOf(activityPhotoID));
		model.addAttribute("activityPhotoVO", activityPhotoVO);
		return "backend/activityphoto/updateActivityPhoto";
	}
	
	@PostMapping("update")
	public String update(@Valid ActivityPhotoVO activityPhotoVO, BindingResult result, ModelMap model,
			@RequestParam("activityPhoto") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中activityPhoto欄位的FieldError紀錄
		result = removeFieldError(activityPhotoVO, result, "activityPhoto");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
			byte[] upFiles = apSvc.getOneActivityPhoto(activityPhotoVO.getActivityPhotoID()).getActivityPhoto();
			activityPhotoVO.setActivityPhoto(upFiles);
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] upFiles = multipartFile.getBytes();
				activityPhotoVO.setActivityPhoto(upFiles);
			}
		}
		if (result.hasErrors()) {
			return "backend/activityphoto/updateActivityPhoto";
		}
		/*************************** 2.開始修改資料 *****************************************/
		apSvc.updateActivityPhoto(activityPhotoVO);;
		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		activityPhotoVO = apSvc.getOneActivityPhoto(Integer.valueOf(activityPhotoVO.getActivityPhotoID()));
		model.addAttribute("activityPhotoVO", activityPhotoVO);
//		List<ActivityPhotoVO> list = apSvc.getAll();
//		model.addAttribute("activityPhotoListData", list);
//		model.addAttribute("getAll", true);
//		return "backend/activityphoto/activityphoto";
		return "backend/activityphoto/listOneActivityPhoto";
	}
	
	@PostMapping("add")
	public String addPage(ModelMap model) {
		ActivityPhotoVO activityPhotoVO = new ActivityPhotoVO();
		model.addAttribute("activityPhotoVO", activityPhotoVO);
		return "backend/activityphoto/addActivityPhoto";
	}
	
	@PostMapping("insert")
	public String insert(@Valid ActivityPhotoVO activityPhotoVO, BindingResult result, ModelMap model,
			@RequestParam("activityPhoto") MultipartFile[] parts) throws IOException {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中activityPhoto欄位的FieldError紀錄 --> 見第 行
		result = removeFieldError(activityPhotoVO, result, "activityPhoto");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			model.addAttribute("errorMessage", "請上傳活動照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				activityPhotoVO.setActivityPhoto(buf);
			}
		}
		if (result.hasErrors() || parts[0].isEmpty()) {
			return "backend/activityphoto/addActivityPhoto";
		}
		/*************************** 2.開始新增資料 *****************************************/
		apSvc.addActivityPhoto(activityPhotoVO);;
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		
		activityPhotoVO = apSvc.getOneActivityPhoto(Integer.valueOf(activityPhotoVO.getActivityPhotoID()));
		model.addAttribute("activityPhotoVO", activityPhotoVO);
		List<ActivityPhotoVO> list = apSvc.getAll();
		model.addAttribute("activityPhotoListData", list);
//		model.addAttribute("getAll", true);
//		return "backend/activityphoto/activityphoto";
		return "backend/activityphoto/listOneActivityPhoto";
	}
	
	@ModelAttribute("activityListData")
	protected List<ActivityVO> referenceActivityListData() {
		List<ActivityVO> list = aSvc.getAll();
		return list;
	}
	
	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(ActivityPhotoVO activityPhotoVO, BindingResult result,
			String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
		result = new BeanPropertyBindingResult(activityPhotoVO, "activityPhotoVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
	
}
