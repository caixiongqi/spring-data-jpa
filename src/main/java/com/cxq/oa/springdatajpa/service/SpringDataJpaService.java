package com.cxq.oa.springdatajpa.service;

import com.cxq.oa.springdatajpa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SpringDataJpaService {

    User create(User user);

    User getUserByUserId(String userId);

    Integer deleteUserByUserId(String userId);

    Page<User> getUserPages(Pageable pageable);

    /**
     * 使用Specification条件查询，repository需要继承JpaSpecificationExecutor接口
     */
    Page<User> getUsersByUserName(String userName, String password, Pageable pageable);

    /**
     * 使用EntityManager分页查询
     */
    Page<User> getUsersByUserNameWithEntityManager(String userName, String password, Pageable pageable);
}
