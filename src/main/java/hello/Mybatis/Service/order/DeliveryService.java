package hello.Mybatis.Service.order;

import hello.Mybatis.domain.delivery.Delivery;
import hello.Mybatis.domain.delivery.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    public void save(Delivery delivery){
        deliveryRepository.save(delivery);
    }

    public Delivery findDeliveryId(String deliveryKey){
        Delivery id = deliveryRepository.findId(deliveryKey);
        System.out.println("id = " + id);
        return id;
    }
}
