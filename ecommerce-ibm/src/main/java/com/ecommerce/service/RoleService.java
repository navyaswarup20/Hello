package com.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.RoleDao;
import com.ecommerce.entity.Role;

/**
 * @author Dhrubajoti Dey
 * In this class, I have created a functionality of creating a new role which can be admin or user and then storing it in roleDao which will ultimately be added in our database.
 *
 */
@Service
public class RoleService {
	
	@Autowired
	private RoleDao roleDao;
	
	public Role createNewRole(Role role) {
		 
		return roleDao.save(role);
	}

}
