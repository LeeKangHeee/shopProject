package hello.Mybatis.domain.member;

import hello.Mybatis.domain.orders.Order;
import jdk.jfr.Enabled;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString
public class Member {

    private Long memberId;
    private String memberName;
    private String address;
    private String loginId;
    private String LoginPass;
    private Long level;

    private List<Order> orders = new ArrayList<>();


}
