package com.sofas.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sofas.app.bean.ItemsDto;
import com.sofas.app.bean.Items_ReviewDto;

@Repository
public class ItemsDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int result = 0;
	CommonDao commonDao = null;
	
	public int itemsInsert(ItemsDto itemsDto) {
		try {
			String sql = "INSERT INTO items VALUE(null, ?, ?, ?, ?, ?, ?, ?, ?, default, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, itemsDto.getItems_name());
			pstmt.setInt(2, itemsDto.getPrice());
			pstmt.setInt(3, itemsDto.getStock());
			pstmt.setString(4, itemsDto.getItems_img());
			pstmt.setString(5, itemsDto.getItems_info1());
			pstmt.setString(6, itemsDto.getItems_info2());
			pstmt.setString(7, itemsDto.getItems_info3());
			pstmt.setString(8, itemsDto.getItems_info4());
			pstmt.setString(9, itemsDto.getItems_category());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			commonDao.freeConnection(pstmt, conn);
		}
		
		return result;
	}
	
	public Vector<ItemsDto> getItemsSelectAll() {
		Vector<ItemsDto> itemsV = new Vector<>();
		try {
			String sql = "select * from items order by items_idx desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ItemsDto itemsDto = new ItemsDto();
				itemsDto.setItems_idx(rs.getInt(1));
				itemsDto.setItems_name(rs.getString(2));
				itemsDto.setPrice(rs.getInt(3));
				itemsDto.setStock(rs.getInt(4));
//				itemsDto.setItems_img(rs.getString(5));
//				itemsDto.setItems_info1(rs.getString(6));
//				itemsDto.setItems_info2(rs.getString(7));
//				itemsDto.setItems_info3(rs.getString(8));
//				itemsDto.setItems_info4(rs.getString(9));
				itemsDto.setSales_cnt(rs.getInt(10));
				itemsDto.setItems_category(rs.getString(11));
				itemsV.add(itemsDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			commonDao.freeConnection(rs, pstmt, conn);
		}
		
		return itemsV;
	}
	
	public ItemsDto getItemsSelect(String idx) {
		ItemsDto itemsDto = new ItemsDto();
		try {
			String sql = "select * from items where items_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, idx);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				itemsDto.setItems_idx(rs.getInt(1));
				itemsDto.setItems_name(rs.getString(2));
				itemsDto.setPrice(rs.getInt(3));
				itemsDto.setStock(rs.getInt(4));
				itemsDto.setItems_img(rs.getString(5));
				itemsDto.setItems_info1(rs.getString(6));
				itemsDto.setItems_info2(rs.getString(7));
				itemsDto.setItems_info3(rs.getString(8));
				itemsDto.setItems_info4(rs.getString(9));
				itemsDto.setSales_cnt(rs.getInt(10));
				itemsDto.setItems_category(rs.getString(11));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			commonDao.freeConnection(rs, pstmt, conn);
		}
		return itemsDto;
	}
	
	public int itemsUpdate(ItemsDto itemsDto) {
		try {
			String sql = "update items set items_name=?, price=?, stock=?, items_img=?, items_info1=?, items_info2=?, items_info3=?, items_info4=?, items_category=? where items_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, itemsDto.getItems_name());
			pstmt.setInt(2, itemsDto.getPrice());
			pstmt.setInt(3, itemsDto.getStock());
			pstmt.setString(4, itemsDto.getItems_img());
			pstmt.setString(5, itemsDto.getItems_info1());
			pstmt.setString(6, itemsDto.getItems_info2());
			pstmt.setString(7, itemsDto.getItems_info3());
			pstmt.setString(8, itemsDto.getItems_info4());
			pstmt.setString(9, itemsDto.getItems_category());
			pstmt.setInt(10, itemsDto.getItems_idx());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			commonDao.freeConnection(pstmt, conn);
		}
		return result;
	}
	
	public int itemsDelete(String idx) {
		try {
			String sql = "delete from items where items_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, idx);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			commonDao.freeConnection(pstmt, conn);
		}
		return result;
	}
	
	public List<Items_ReviewDto> SelectItemsList() {	
		return sqlSession.selectList("com.sofas.items.selectItemsList");
	}
	
	public List<Items_ReviewDto> SelectBestItem() {
		return sqlSession.selectList("com.sofas.items.selectBestItem");
	}
	
	public Items_ReviewDto getItemInfo(int idx) {
		return sqlSession.selectOne("com.sofas.items.getItemInfo");
	}
	
	public int sales_cntUpdate(int items_idx, int sales_cnt) {
		try {
			String sql = "update items set sales_cnt=sales_cnt+? where items_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sales_cnt);
			pstmt.setInt(2, items_idx);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			commonDao.freeConnection(pstmt, conn);
		}
		return result;
	}
	
}
