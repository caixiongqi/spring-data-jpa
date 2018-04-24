package com.cxq.oa.springdatajpa.service;

import com.cxq.oa.springdatajpa.entity.User;
import com.cxq.oa.springdatajpa.repository.SpringDataJpaRepository;
import com.cxq.oa.springdatajpa.utils.JpaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpringDataJpaServiceImpl implements SpringDataJpaService {

    @Autowired
    private SpringDataJpaRepository springDataJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public User create(User user) {
        return springDataJpaRepository.save(user);
    }

    @Override
    public User getUserByUserId(String userId) {
        return springDataJpaRepository.getUserByUserId(userId);
    }

    @Override
    @Transactional
    public Integer deleteUserByUserId(String userId) {
        return springDataJpaRepository.deleteByUserId(userId);
    }

    @Override
    public Page<User> getUserPages(Pageable pageable) {
        return springDataJpaRepository.findAll(pageable);
    }

    @Override
    public Page<User> getUsersByUserName(final String userName, final String password, Pageable pageable) {
        Specification specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (null != userName) {
                    predicates.add(criteriaBuilder.equal(root.get("userName"), userName));
                }
                if (null != password) {
                    predicates.add(criteriaBuilder.equal(root.get("password"), password));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                //return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
        return springDataJpaRepository.findAll(specification, pageable);
    }

    @Override
    public Page<User> getUsersByUserNameWithEntityManager(String userName, String password, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        sql.append(" from User where 1=1 ");
        if (userName != null) {
            sql.append(" and userName = '" + userName + "'");
        }
        if (password != null) {
            sql.append(" and password = '" + password + "'");
        }
        return JpaUtil.queryForPage(entityManager, sql.toString(), pageable, User.class);
    }
}
