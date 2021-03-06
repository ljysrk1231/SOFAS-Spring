package com.sofas.app.service;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sofas.app.bean.MemberDto;
import com.sofas.app.bean.Orderlist_joinDto;
import com.sofas.app.bean.Qna_board_joinDto;
import com.sofas.app.dao.MemberDao;
import com.sofas.app.dao.OrderlistDao;
import com.sofas.app.dao.Qna_boardDao;


public class MyPageHandler implements CommonHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pg = request.getParameter("pg");
		String uri = "/mypage.jsp";

		if (pg == null || pg.equals("account")) {
			
		} else if (pg.equals("orderlist")) {
			MemberDto memberDto = (MemberDto) request.getSession().getAttribute("memberInfo");
			int member_idx = memberDto.getMember_idx();
			OrderlistDao orderDao = new OrderlistDao();
			Vector<Orderlist_joinDto> orderV = orderDao.myOrderList(member_idx);
			request.setAttribute("orderlist", orderV);
		} else if (pg.equals("orderDetails")) {
			int order_num = Integer.parseInt(request.getParameter("order_num"));
			OrderlistDao orderDao = new OrderlistDao();
			Vector<Orderlist_joinDto> orderV = orderDao.myOrderInfo(order_num);
			request.setAttribute("orderinfo", orderV);
			
		} else if (pg.equals("order_state_update")) {	
			
			int type = Integer.parseInt(request.getParameter("type"));
			int order_num =  Integer.parseInt(request.getParameter("order_num"));
			
			OrderlistDao orderDao = new OrderlistDao();
			int result = orderDao.myStateUpdate(order_num, type);
			
			if(result > 0) {
				uri = "MyPage.do?pg=orderlist";				
			}else {
				uri = "MyPage.do?pg=orderlist&err=0";
			}
			
			
		} else if (pg.equals("qnalist")) {
			MemberDto memberDto = (MemberDto) request.getSession().getAttribute("memberInfo");
			Qna_boardDao qnaDao = new Qna_boardDao();
			Vector<Qna_board_joinDto> qnaV = qnaDao.myQboard(memberDto.getMember_idx());
			request.setAttribute("qnalist", qnaV);

		} else if (pg.equals("updateAccount")) {

		} else if (pg.equals("updateAccount_proc")) {
			uri = updateAccount_proc(request, response);
		} else if (pg.equals("review")) {
			request.getSession().setAttribute("rvwrite_chk", false);
		}

		return uri;
	}

	private String updateAccount_proc(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MemberDao dao = new MemberDao();

		int idx = Integer.parseInt(request.getParameter("idx"));
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		String email = request.getParameter("email");
		String postcode = request.getParameter("postcode");
		String roadAddress = request.getParameter("roadAddress");
		String detailAddress = request.getParameter("detailAddress");
		String address = postcode + "//" + roadAddress + "//" + detailAddress;
		String pw = request.getParameter("new_pw");

		MemberDto dto = new MemberDto();
		dto.setMember_idx(idx);
		dto.setMember_name(name);
		dto.setPhone(tel);
		dto.setEmail(email);
		dto.setAddress(address);
		dto.setPw(pw);
		int updateResult = dao.updateMemberData(dto);

		String uri = "/MyPage.do?pg=account";
		MemberDao memberDataDao = new MemberDao();
		MemberDto memberDataDto = memberDataDao.selectMemberData(idx);
		request.getSession().setAttribute("memberInfo", memberDataDto);

		if (updateResult == 0) {
			uri = "/MyPage.do?pg=account&err=0";
		}
		return uri;
	}
}
