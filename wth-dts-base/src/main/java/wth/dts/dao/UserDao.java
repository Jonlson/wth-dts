package wth.dts.dao;

import wth.dts.entity.User;

public interface UserDao {
    User getById(Long id);
}
