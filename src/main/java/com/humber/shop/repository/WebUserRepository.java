package com.humber.shop.repository;

import com.humber.shop.model.WebUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebUserRepository extends MongoRepository<WebUser, String> {
}