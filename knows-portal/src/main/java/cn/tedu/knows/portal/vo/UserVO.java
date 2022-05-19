package cn.tedu.knows.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
//下面注解当前类支持链式set赋值
@Accessors(chain = true)
public class UserVO implements Serializable {
    private Integer id;
    private String userName;
    private String nickName;

    // 问题数
    private int questions;
    // 收藏数
    private int collections;
}
