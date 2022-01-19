package hello.Mybatis.domain.delivery;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DeliveryRepository {
    void save(Delivery delivery);
    Delivery findId(String deliveryKey);
}
