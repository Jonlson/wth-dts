package wth.dts.service;

import org.springframework.stereotype.Service;
import wth.dts.entity.User;

@Service
public interface UserService {
    User getById(Long aLong);
}
