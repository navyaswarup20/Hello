package com.ecommerce.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.User;

import java.util.List;

/**
 * @author Jaspreet Kaur Saluja
 * This is basically a repository to perform crud operations inside the database.
 *
 */
@Repository
public interface CartDao extends CrudRepository<Cart, Integer > {
    public List<Cart> findByUser(User user);
}
