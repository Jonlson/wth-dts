package wth.dts.service.impl;

import org.springframework.stereotype.Service;
import wth.dts.dao.UserDao;
import wth.dts.entity.User;
import wth.dts.service.UserService;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;
    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }
}
