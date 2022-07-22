package com.saeyan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.saeyan.dto.ProductVO;

import util.DBManager;

public class ProductDAO {
	//싱글톤패턴 정의
	private ProductDAO() {
	}
	private static ProductDAO instance = new ProductDAO();
	
	public static ProductDAO getInstance() {
		return instance;
	}
	
	public List<ProductVO> selectAllProducts() {
		
		List<ProductVO> list = new ArrayList<ProductVO>();
		
		String sql = "select* from product order by code desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ProductVO pVo = new ProductVO();
				pVo.setCode(rs.getInt("code"));
				pVo.setName(rs.getString("name"));
				pVo.setPrice(rs.getInt("price"));
				pVo.setPictureUrl(rs.getString("pictureUrl"));
				pVo.setDescription(rs.getString("description"));
				list.add(pVo);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn,pstmt,rs);
		}
		return list;
	}
	
	//p.568
	public void insertProduct(ProductVO pVo) {
		String sql = "insert into product values(product_seq.nextval,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pVo.getName());
			pstmt.setInt(2, pVo.getPrice());
			pstmt.setString(3, pVo.getPictureUrl());
			pstmt.setString(4, pVo.getDescription());
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
	}
	
	public ProductVO selectProductByCode(String code) {
		String sql = "select* from product where code=?";
		ProductVO pVo = null;
		
		try {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = DBManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, code);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					pVo = new ProductVO();
					pVo.setCode(rs.getInt("code"));
					pVo.setName(rs.getString("name"));
					pVo.setPrice(rs.getInt("price"));
					pVo.setPictureUrl(rs.getString("pictureUrl"));
					pVo.setDescription(rs.getString("description"));
				}
			}catch(Exception e) {
				e.printStackTrace();
				
			}finally {
				DBManager.close(conn, pstmt, rs);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return pVo;
	}
}
