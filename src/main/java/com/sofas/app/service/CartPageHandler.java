package com.sofas.app.service;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sofas.app.dao.ItemsDao;
import com.sofas.app.dto.CartInfoDto;
import com.sofas.app.dto.ItemsDto;
import com.sofas.app.dto.MemberDto;

public class CartPageHandler implements CommonHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MemberDto memberInfo = (MemberDto) request.getSession().getAttribute("memberInfo");		
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		if(memberInfo != null) {
			for (Cookie c : cookies) { // for������ �迭 �ȿ� �ִ� ��Ű�� �˻�
				if (c.getName().equals(memberInfo.getMember_idx()+"")) { // member_idx�� �̸��� ���� ��Ű�� ������ ����
					cookie = c;
				}
			}
		}
				
		if(cookie != null) {
			Vector<CartInfoDto> infoVec = new Vector<>();
			String temp[] = cookie.getValue().split("//");
			String itemIdx="", itemCnt="";
			
			for(int i=0; i<temp.length; i++) {
				itemIdx = temp[i].split("C")[0];
				itemCnt = temp[i].split("C")[1];
				ItemsDto dto = new ItemsDao().getItemsSelect(itemIdx);
				CartInfoDto cartDto = new CartInfoDto();
				cartDto.setItems_idx(dto.getItems_idx());
				cartDto.setItems_name(dto.getItems_name());
				cartDto.setPrice(dto.getPrice());
				cartDto.setStock(dto.getStock());
				cartDto.setItems_img(dto.getItems_img());
				cartDto.setItems_category(dto.getItems_category());
				cartDto.setQuantity(Integer.parseInt(itemCnt));
				infoVec.add(cartDto);
			}	
			request.setAttribute("itemInfo", infoVec);
		}
		
		return "/cart.jsp";
	}
}
