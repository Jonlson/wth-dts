package wth.dts.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String password;
    private Integer age;
    private String email;
    private String phone;
    private Integer type;
    private String address;
    private String gender;
    private String avatar;
    private String role;
    private String status;
    private Date createTime;
    private String updateTime;
    private String deleteTime;
    private String remark;
}
