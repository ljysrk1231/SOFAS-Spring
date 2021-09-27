package com.sofas.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sofas.app.bean.MemberDto;


@Repository
public class MemberDao {
	@Autowired
	private SqlSession sqlSession;
	
	
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int result = 0;
	CommonDao commonDao = null;
/*
	public MemberDao() {
		commonDao = new CommonDao();
		try {
			conn = commonDao.pool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
*/
	
	
	public int insertMember(Map<String, Object> map) {
		   // 값을 여러개 받아와야 할 경우에는 Map 으로 처리
		return sqlSession.insert("com.sofas.member.insertData", map);
	}
	
	public MemberDto loginMember(Map<String, String> map) {
		return sqlSession.selectOne("com.sofas.member.loginData", map);
	}
	
	public HashMap<String, Object> checkMemberData(Map<String, String> map) {
		return sqlSession.selectOne("com.sofas.member.checkMemberData", map);
	}
	
	
	
	
	
	
	   
	// 회占쏙옙占쏙옙占쏙옙
	public int insertMemberData(MemberDto dto) {
		try {
			String sql = "insert into member values(null,?,?,?,?,?,?,default,default,default,now())";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPw());
			pstmt.setString(3, dto.getMember_name());
			pstmt.setString(4, dto.getEmail());
			pstmt.setString(5, dto.getAddress());
			pstmt.setString(6, dto.getPhone());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			commonDao.freeConnection(pstmt, conn);
		}
		return result;
	}
/*
	// 占싸깍옙占쏙옙
	public MemberDto loginMember(String id, String pw) {
		MemberDto dto = new MemberDto();
		try {
			String sql = "select * from member where id=? and pw=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto.setMember_idx(rs.getInt(1));
				dto.setId(rs.getString(2));
				dto.setMember_name(rs.getString(4));
				dto.setEmail(rs.getString(5));
				dto.setAddress(rs.getString(6));
				dto.setPhone(rs.getString(7));
				dto.setGrade(rs.getInt(8));
				dto.setLv(rs.getInt(9));
				dto.setPoint(rs.getInt(10));
				dto.setRegdate(rs.getString(11));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			commonDao.freeConnection(rs, pstmt, conn);
		}
		return dto;
	}
	*/
	// 회占쏙옙占쏙옙占쏙옙
	public MemberDto selectMemberData(int idx) {
		MemberDto dto = new MemberDto();
		try {
			String sql = "select * from member where member_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto.setMember_idx(rs.getInt(1));
				dto.setId(rs.getString(2));
				dto.setMember_name(rs.getString(4));
				dto.setEmail(rs.getString(5));
				dto.setAddress(rs.getString(6));
				dto.setPhone(rs.getString(7));
				dto.setGrade(rs.getInt(8));
				dto.setLv(rs.getInt(9));
				dto.setPoint(rs.getInt(10));
				dto.setRegdate(rs.getString(11));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			commonDao.freeConnection(rs, pstmt, conn);
		}
		return dto;
	}
	
	// 회占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙
	public int updateMemberData(MemberDto dto) {
		try {
			String sql = "update member set member_name=?,phone=?,email=?,address=?,pw="
						+ "case "
						+ "when ?!='' then ? "
						+ "else pw "
						+ "end "
						+ "where member_idx=?";			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getMember_name());
			pstmt.setString(2, dto.getPhone());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getAddress());
			pstmt.setString(5, dto.getPw());
			pstmt.setString(6, dto.getPw());
			pstmt.setInt(7, dto.getMember_idx());	
			result = pstmt.executeUpdate();					
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			commonDao.freeConnection(pstmt, conn);
		}
		return result;
	}
	
	
/*	
	// 占쏙옙占싱듸옙 占쌩븝옙확占쏙옙 Ajax
	public JSONObject checkIdData(String id) {
		JSONObject json = new JSONObject();
		int rtn = 0;
		try {
			String sql = "select count(member_idx) from member where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			// count 占쏙옙 1 占싱몌옙 (0占쏙옙占쏙옙 크占쏙옙) 占쌔댐옙 占쏙옙占싱듸옙 占쏙옙占쏙옙占싼다댐옙 占실뱄옙
			if (rs.next()) {
				if (rs.getInt(1) > 0) {
					rtn = 1; // 占쏙옙占쌀곤옙
				}
				json.put("rtn", rtn);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			commonDao.freeConnection(rs, pstmt, conn);
		}
		return json;
	}

	// 占싸깍옙占쏙옙 - 占쏙옙占싱듸옙,占쏙옙橘占싫�, lv 확占쏙옙 Ajax
	public JSONObject checkMemberData(String id, String pw) {
		JSONObject json = new JSONObject();
		int rtn = 0;
		try {
			String sql = "select count(member_idx),lv from member where id=? and pw=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (rs.getInt(1) > 0) {
					rtn = 1; // 占싸깍옙占쏙옙 占쏙옙占쏙옙 占쏙옙치
				}
				json.put("rtn", rtn);
				json.put("rtn_lv", rs.getInt(2));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			commonDao.freeConnection(rs, pstmt, conn);
		}
		return json;
	}
	
	// 占쏙옙橘占싫� 확占쏙옙 Ajax
	public JSONObject checkPwData(int idx, String pw) {
		JSONObject json = new JSONObject();
		int rtn = 0;
		try {
			String sql = "select count(member_idx) from member where member_idx=? and pw=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if(rs.getInt(1) > 0) {
					rtn = 1;
				}
				json.put("rtn", rtn);					
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			commonDao.freeConnection(rs, pstmt, conn);
		}
		return json;
	}	
*/ 	 
	
	// 회占쏙옙탈占쏙옙
	public int withdrawalMember(int idx) {
		try {
			String sql = "update member set lv=2 where member_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			result = pstmt.executeUpdate();					
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			commonDao.freeConnection(pstmt, conn);
		}
		return result;
	}
	
	public Vector<MemberDto> getMemberList() {
		Vector<MemberDto> memberV = new Vector<>();
		try {
			String sql = "select * from member order by member_idx desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberDto memberDto = new MemberDto();
				memberDto.setMember_idx(rs.getInt(1));
				memberDto.setId(rs.getString(2));
				memberDto.setMember_name(rs.getString(4));
//				memberDto.setEmail(rs.getString(5));
//				memberDto.setAddress(rs.getString(6));
//				memberDto.setPhone(rs.getString(7));
				memberDto.setGrade(rs.getInt(8));
				memberDto.setLv(rs.getInt(9));
//				memberDto.setPoint(rs.getInt(10));
				memberDto.setRegdate(rs.getString(11));
				memberV.add(memberDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			commonDao.freeConnection(rs, pstmt, conn);
		}
		return memberV;
	}
	
	public int memberBlock(String idx) {
		try {
			String sql = "update member set lv=1 where member_idx=?";
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


}
