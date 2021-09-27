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

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	ItemsDao itemsDao;
	

	@RequestMapping("Home.do")
	public String home(Model model) {
		model.addAttribute("Items_ReviewDto", itemsDao.SelectBestItem());
		return "home";
	}
	
///////////////////////////////////////////////
	@Autowired
	MemberDao memberDao;
	
	@RequestMapping("Login.do")
	public String login() {

		return "login";
	}

	@RequestMapping("LoginProc.do")
	public String loginProc(String id, String pw, HttpSession session) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id",id);
		map.put("pw",pw);
		MemberDto dto = memberDao.loginMember(map);
		if(dto != null) {
			session.setAttribute("userInfo", dto);
		}
		return "redirect:/Home.do";
	}
	
	@RequestMapping("Logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/Home.do";
	}
	
	@RequestMapping(value="AjaxProc.do", method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> ajaxProc(@RequestBody HashMap<String, String> requestMap) {
		HashMap<String, Object> map = null;
		if(requestMap.get("pg").equals("signUp")) {
		//	map.put("res", memberDao.checkMemberData(id, pw));
		} else if(requestMap.get("pg").equals("login")) {
			HashMap<String, String> checkMemberMap = new HashMap<String, String>();
			checkMemberMap.put("id",requestMap.get("id"));
			checkMemberMap.put("pw",requestMap.get("pw"));
			map = memberDao.checkMemberData(checkMemberMap);
		} else if(requestMap.get("pg").equals("pw_check")) {
		//	map.put("res", memberDao.checkPwData(idx, pw));
		} 
	
		
		return map;
				
	}
/////////////////////////////////////////////
	
}
