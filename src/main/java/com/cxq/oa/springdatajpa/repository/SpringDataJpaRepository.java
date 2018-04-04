package com.cxq.oa.springdatajpa.repository;

import com.cxq.oa.springdatajpa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataJpaRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    /**
     * 派生查询方法
     */
    User findUserByUserId(String userId);

    /**
     * 派生删除方法
     */
    Integer deleteByUserId(String userId);

    /**
     * sql查询方法，nativeQuery = true
     */
    @Query(value = " select * from user where user_id = ?1", nativeQuery = true)
    User getUserByUserId(String userId);

}
