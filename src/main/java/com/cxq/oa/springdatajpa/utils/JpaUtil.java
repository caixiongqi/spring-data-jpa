package com.cxq.oa.springdatajpa.utils;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class JpaUtil {
	public static <T>Page<T> queryForPage(EntityManager entityManager, String baseSql, Pageable pageable, Class<T> classes) {
		Query query = entityManager.createQuery(baseSql, classes);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		List<T> result = query.getResultList();
		String countSql;
		if (baseSql.toLowerCase().trim().indexOf("select") == 0) {
			countSql = baseSql.replaceFirst("select +.+? +from", "select count(*) from");
		} else {
			countSql = "select count(*) " + baseSql;
		}
		Query countQuery = entityManager.createQuery(countSql, Long.class);
		Long count = (Long)countQuery.getSingleResult();

		return new PageImpl<T>(result, pageable, count.intValue());
	}
}
