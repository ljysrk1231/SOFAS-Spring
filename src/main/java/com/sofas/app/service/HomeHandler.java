package com.sofas.app.service;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sofas.app.dao.ItemsDao;
import com.sofas.app.dto.Items_ReviewDto;


public class HomeHandler implements CommonHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ItemsDao itemsDao = new ItemsDao();
		Vector<Items_ReviewDto> itemVe = itemsDao.SelectBestItem();
		request.setAttribute("Items_ReviewDto", itemVe);
		
		return "/home.jsp";
	}
}
