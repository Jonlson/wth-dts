package wth.dts.business.impl;

import com.alibaba.fastjson.JSON;



import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import wth.dts.bean.EsDocUser;
import wth.dts.business.BizEsDocUserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BizEsDocUserServiceImpl implements BizEsDocUserService {


    @Resource
    private UserService userService;


    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    /**
     * 同步ES数据
     */
    @Override
    public void execute(String string, String string1) {
        User user = userService.getById(Long.valueOf(userId));
        if (user != null) {
            

            List<userGoods> userGoodsList = userGoodsService.getByuserId(Long.valueOf(userId));
            List<String> goodsCodeList = CollectionUtils.isEmpty(userGoodsList) ? new ArrayList<>() : userGoodsList.stream().map(userGoods::getGoodsCode).distinct().collect(Collectors.toList());
            List<Goods> GoodsList = GoodsService.getListByIdAndCodes(user.getId(), goodsCodeList);
            List<String> goodsNameList = GoodsList.stream().map(Goods::getName).distinct().collect(Collectors.toList());

            userRefund userRefund1 = userRefundService.getById(user.getId());
            EsDocUser docUser = DocEsuser.builder()
                    .id(userId)
                    .type(user.getType())
                    .status(user.getStatus() == 3 ? 1 : user.getStatus())
                    .empty(user.getEmpty() != 0)
                    .goodsNamesText(CollectionUtils.isEmpty(goodsNameList) ? null : String.join(",", goodsNameList))
                    .userId(user.getUserId())
                    .userTel(user.getUserTel())
                    .createTime(user.getCreateTime())
                    .build();
            elasticsearchRestTemplate.save(EsDocUser.class, docUser);
        }
    }
}
