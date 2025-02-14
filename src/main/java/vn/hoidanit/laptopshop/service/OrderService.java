package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository){
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Order> fetchOrders(){
        return this.orderRepository.findAll();
    }

    public Order getOrderById(long id){
        return this.orderRepository.findById(id).get();
    }

    public List<Order> fetchOrdersByUser(User user){
        return this.orderRepository.findAllByUser(user);
    }

    public void updateOrder(Order order){
        //note: phải tìm oder theo id, rồi lưu order vừa tìm được, chứ ko thể lưu trực tiếp order truyền từ
        // view lên, order đó nó ko đầy đủ all thông tin được, vì một số attr trong form nó ko gửi lên
        Optional<Order> orderOptional = this.orderRepository.findById(order.getId());
        if (orderOptional.isPresent()) {
            Order currentOrder = orderOptional.get();
            currentOrder.setStatus(order.getStatus());
            this.orderRepository.save(currentOrder);
        }
    }

    public void deleteOrderById(long id){
        Optional<Order> orderOptional = this.orderRepository.findById(id);
        if (orderOptional.isPresent()){
            List<OrderDetail> orderDetails = orderOptional.get().getOrderDetails();

            //delete order detail
            if (orderDetails != null){
                for (OrderDetail orderDetail : orderDetails){
                    this.orderDetailRepository.delete(orderDetail);
                }
            }

            //delete order
            this.orderRepository.delete(orderOptional.get());
        }
    }

    public long countOrders(){
        return this.orderRepository.count();
    }
}
