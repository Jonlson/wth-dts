package wth.dts.bean;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Document(indexName = "doc_user", createIndex = false)
public class EsDocUser {

    @Id
    @Field(name = "id")
    private String id;

    @Field(name = "type", type = FieldType.Integer)
    private Integer type;


    @Field(name = "status", type = FieldType.Integer)
    private Integer status;

    @Field(name = "empty", type = FieldType.Boolean)
    private Boolean empty;


    @Field(name = "opt_plat", type = FieldType.Integer)
    private Integer optPlat;



    @Field(name = "transaction_id", type = FieldType.Keyword)
    private String transactionId;

    @Field(name = "goods_names_text", type = FieldType.Text)
    private String goodsNamesText;

    @Field(name = "user_id", type = FieldType.Long)
    private Long userId;

    @Field(name = "user_tel", type = FieldType.Keyword)
    private Long userTel;
    //商品编号->85码
    @Field(name = "codes", type = FieldType.Keyword)
    private List<String> codes;
    //商品名称
    @Field(name = "goods_codes", type = FieldType.Keyword)
    private List<String> goodsCodes;

    @Field(name = "create_time", type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Field(name = "pay_time", type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;
}







