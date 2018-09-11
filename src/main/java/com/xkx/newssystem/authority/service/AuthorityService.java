package com.xkx.newssystem.authority.service;

import java.util.List;

import com.xkx.newssystem.authority.dao.AuthorityDao;
import com.xkx.newssystem.authority.domian.Authority;

public class AuthorityService {
	private AuthorityDao authorityDao = new AuthorityDao();
	public List<Authority> getAllAuthorities() {
		return authorityDao.getAllAuthorities();
	}
}
