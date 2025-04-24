package wth.dts.business.impl;

import com.alibaba.fastjson.JSON;



import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import org.springframework.stereotype.Service;
import wth.dts.bean.EsDocUser;
import wth.dts.business.BizEsDocUserService;
import wth.dts.entity.User;
import wth.dts.service.UserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BizEsDocUserServiceImpl implements BizEsDocUserService {


    @Resource
    private UserService userService;


    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    /**
     * 同步ES数据
     */
    @Override
    public void execute(String userId, String value) {
        User user = userService.getById(Long.valueOf(userId));
        if (user != null) {
            EsDocUser docUser = EsDocUser.builder()
                    .id(userId)
                    .type(user.getType())
                    .status(Integer.valueOf(user.getStatus()))
                    .userId(Long.valueOf(user.getId()))
                    .userTel(Long.valueOf(user.getPhone()))
                    .createTime(user.getCreateTime())
                    .build();
            elasticsearchRestTemplate.save(EsDocUser.class, docUser);
        }
    }
}
