package com.cxq.oa.springdatajpa.web;

import com.cxq.oa.springdatajpa.entity.User;
import com.cxq.oa.springdatajpa.service.SpringDataJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "jpa")
public class SpringDataJpaController {

    @Autowired
    private SpringDataJpaService springDataJpaService;

    @RequestMapping(value = "/{userId}/create", method = RequestMethod.GET)
    public User create(@PathVariable(value = "userId") String userId, @RequestParam(name = "userName") String userName,
                       @RequestParam(name = "password") String password) {
        User user = new User(userId, userName, password);
        return springDataJpaService.create(user);
    }

    @RequestMapping(value = "/{userId}/delete", method = RequestMethod.GET)
    public Integer delete(@PathVariable(value = "userId") String userId) {
        return springDataJpaService.deleteUserByUserId(userId);
    }

    @RequestMapping(value = "/{userId}/get", method = RequestMethod.GET)
    public User getUserByUserId(@PathVariable(value = "userId") String userId) {
        return springDataJpaService.getUserByUserId(userId);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Page<User> getUserPages(@PageableDefault(value = 3, sort = { "userName"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return springDataJpaService.getUserPages(pageable);
    }

    @RequestMapping(value = "/{userName}/users", method = RequestMethod.GET)
    public Page<User> getUsersByUserName(@PathVariable(value = "userName") String userName,
                                         @RequestParam(name = "password") String password, @PageableDefault(value = 3, sort = { "userName"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return springDataJpaService.getUsersByUserNameWithEntityManager(userName, password, pageable);
    }
}
