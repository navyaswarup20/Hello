package com.ecommerce.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.Role;

/**
 * @author Dhrubajoti Dey
 * This is basically a repository to perform crud operations inside the database.
 *
 */
@Repository
public interface RoleDao extends CrudRepository<Role, String> {

}
