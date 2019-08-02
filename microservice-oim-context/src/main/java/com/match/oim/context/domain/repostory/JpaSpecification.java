package com.match.oim.context.domain.repostory;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangchao
 * @Date 2019/5/28 9:17
 * @Version v1.0
 */
public class JpaSpecification {


    public static Specification getSpecification(String searchBy, String keyWord){
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> pr = new ArrayList<>();
                if (!StringUtils.isEmpty(searchBy)) {
                    pr.add(cb.like(root.get(searchBy).as(String.class), "%" + keyWord + "%"));
                }
                return cb.and(pr.toArray(new Predicate[pr.size()]));
            }
        };
    }

    public static Specification getSpecification(List<String> searchBys, String keyWord){
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> pr = new ArrayList<>();
                for (String searchBy : searchBys) {
                    pr.add( cb.like(root.get(searchBy).as(String.class), "%" + keyWord + "%"));
                }
                return cb.or(pr.toArray(new Predicate[pr.size()]));
            }
        };
    }


}
