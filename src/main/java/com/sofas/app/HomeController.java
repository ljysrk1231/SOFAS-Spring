package com.sofas.app;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sofas.app.bean.MemberDto;
import com.sofas.app.dao.ItemsDao;
import com.sofas.app.dao.MemberDao;
import com.sofas.app.dao.ReviewDao;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	ItemsDao itemsDao;

	@Autowired
	ReviewDao reviewDao;
	


	@RequestMapping("Home.do")
	public String home(Model model) {
		model.addAttribute("Items_ReviewDto", itemsDao.SelectBestItem());
		return "home";
	}
	
	@RequestMapping("Sofalist.do")
	public String sofalist(Model model) {
		model.addAttribute("Items_ReviewDto", itemsDao.SelectItemsList());
		return "sofalist";
	}
	
	@RequestMapping("Acclist.do")
	public String acclist(Model model) {
		model.addAttribute("Items_ReviewDto", itemsDao.SelectItemsList());
		return "acclist";
	}
	
	@RequestMapping("Stoollist.do")
	public String stoollist(Model model) {
		model.addAttribute("Items_ReviewDto", itemsDao.SelectItemsList());
		return "stoollist";
	}
	
	@RequestMapping("ItemPage.do")
	public String itempage(Model model, int items_idx) {
		model.addAttribute("items_infoDto", itemsDao.getItemInfo(items_idx));
		model.addAttribute("Items_ReviewDto", itemsDao.SelectBestItem());
		model.addAttribute("items_Rdto", reviewDao.getReviewInfo(items_idx));
		
		return "itempage";
	}
	
///////////////////////////////////////////////
	@Autowired
	MemberDao memberDao;
	
	@RequestMapping("SignUp.do")
	public String signUp() {
		return "signUp";
	}
	
	@RequestMapping("SignUpProc.do")
	public String signUpProc(Model model, HashMap<String, Object> map) {
		int result = memberDao.insertMember(map);	
		if(result <= 0) {
			model.addAttribute("err", 0);
			return "signUp";
		} 
		return "signUp_success";
	}
	
	@RequestMapping("Login.do")
	public String login() {
		return "login";
	}

	@RequestMapping("LoginProc.do")
	public String loginProc(Model model, String id, String pw, HttpSession session) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id",id);
		map.put("pw",pw);
		MemberDto dto = memberDao.loginMember(map);
		
		if(dto.getMember_idx() <= 0) {
			model.addAttribute("err", 0);
			return "login";
		} 
		session.setAttribute("memberInfo", dto);
		return "redirect:/Home.do";
	}
	
	@RequestMapping("Logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/Home.do";
	}
	
	@RequestMapping(value="AjaxProc.do", method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> ajaxProc(@RequestBody HashMap<String, Object> requestMap) {
		HashMap<String, Object> map = null;
		if(requestMap.get("pg").equals("signUp")) {
			int cnt = memberDao.checkIdData(requestMap.get("id")+"");
			map = new HashMap<String, Object>();
			map.put("cnt", cnt);
		} else if(requestMap.get("pg").equals("login")) {
			HashMap<String, Object> checkMemberMap = new HashMap<String, Object>();
			checkMemberMap.put("id",requestMap.get("id"));
			checkMemberMap.put("pw",requestMap.get("pw"));
			map = memberDao.checkMemberData(checkMemberMap);
		} else if(requestMap.get("pg").equals("pw_check")) {
			int cnt = memberDao.checkPwData(requestMap);
			map = new HashMap<String, Object>();
			map.put("cnt", cnt);
		} 
		return map;				
	}
/////////////////////////////////////////////
	
}
