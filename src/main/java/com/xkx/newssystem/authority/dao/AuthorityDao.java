package com.xkx.newssystem.authority.dao;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.xkx.newssystem.authority.domian.Authority;

import cn.itcast.jdbc.TxQueryRunner;

public class AuthorityDao {
	public List<Authority> getAllAuthorities() {
		QueryRunner qr = new TxQueryRunner();
		try {
			String sql = "SELECT * FROM Authority";
			return qr.query(sql, new BeanListHandler<Authority>(Authority.class));
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
