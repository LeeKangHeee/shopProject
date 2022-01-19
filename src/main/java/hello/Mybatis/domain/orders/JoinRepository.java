package hello.Mybatis.domain.orders;

import hello.Mybatis.domain.orders.Order;
import hello.Mybatis.domain.orders.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface JoinRepository {
    List<Order> findJoinAll(Long memberId, OrderStatus orderStatus);
}
