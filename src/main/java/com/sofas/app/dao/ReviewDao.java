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

import com.sofas.app.bean.ReviewDto;
import com.sofas.app.bean.Review_ListDto;

@Repository
public class ReviewDao {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int result = 0;
	CommonDao commonDao = null;
	
	@Autowired
	private SqlSession sqlSession;

	public List<Review_ListDto> getReviewInfo(int idx) {
		return sqlSession.selectList("com.sofas.review.getReviewInfo");
	}
	
	public int insertReview(ReviewDto rvDto){
		try{
			String sql = "insert into review values (null, ?, ?, now(), ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rvDto.getItems_idx());
			pstmt.setInt(2, rvDto.getMember_idx());
			pstmt.setString(3, rvDto.getReview_content());
			pstmt.setString(4, rvDto.getReview_img());
			pstmt.setInt(5, rvDto.getReview_star());
			result = pstmt.executeUpdate();
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally {
			commonDao.freeConnection(pstmt, conn);
		}
		return result;
	}
	
}
