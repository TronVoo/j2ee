package com.example.LAB05.service;

import com.example.LAB05.model.CartItem;
import com.example.LAB05.model.Order;
import com.example.LAB05.model.OrderDetail;
import com.example.LAB05.model.Product;
import com.example.LAB05.repository.OrderRepository;
import com.example.LAB05.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CartService {

    private static final String CART_SESSION_KEY = "CART";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @SuppressWarnings("unchecked")
    private Map<Integer, Integer> getOrCreateCart(HttpSession session) {
        Object cartObject = session.getAttribute(CART_SESSION_KEY);
        if (cartObject == null) {
            Map<Integer, Integer> cart = new LinkedHashMap<>();
            session.setAttribute(CART_SESSION_KEY, cart);
            return cart;
        }
        return (Map<Integer, Integer>) cartObject;
    }

    public void addToCart(HttpSession session, Integer productId, Integer quantity) {
        if (productId == null) {
            return;
        }

        int qty = (quantity == null || quantity <= 0) ? 1 : quantity;

        if (productRepository.findById(productId).isEmpty()) {
            return;
        }

        Map<Integer, Integer> cart = getOrCreateCart(session);
        cart.put(productId, cart.getOrDefault(productId, 0) + qty);
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void updateQuantity(HttpSession session, Integer productId, Integer quantity) {
        if (productId == null) {
            return;
        }

        Map<Integer, Integer> cart = getOrCreateCart(session);

        if (quantity == null || quantity <= 0) {
            cart.remove(productId);
        } else {
            cart.put(productId, quantity);
        }

        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void removeItem(HttpSession session, Integer productId) {
        if (productId == null) {
            return;
        }

        Map<Integer, Integer> cart = getOrCreateCart(session);
        cart.remove(productId);
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public List<CartItem> getCartItems(HttpSession session) {
        Map<Integer, Integer> cart = getOrCreateCart(session);

        if (cart.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Integer, Product> productMap = cart.keySet()
            .stream()
            .map(productId -> productRepository.findById(Objects.requireNonNull(productId)).orElse(null))
            .filter(Objects::nonNull)
            .collect(Collectors.toMap(Product::getId, Function.identity()));

        List<CartItem> items = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Product product = productMap.get(entry.getKey());
            if (product != null) {
                items.add(new CartItem(product, entry.getValue()));
            }
        }

        return items;
    }

    public Double getTotalAmount(HttpSession session) {
        return getCartItems(session)
                .stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    public Integer getTotalQuantity(HttpSession session) {
        return getOrCreateCart(session)
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public void clearCart(HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }

    public Order checkout(HttpSession session) {
        List<CartItem> items = getCartItems(session);

        if (items.isEmpty()) {
            return null;
        }

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setIsPaid(false);

        List<OrderDetail> orderDetails = new ArrayList<>();
        double totalAmount = 0.0;

        for (CartItem item : items) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setPrice(item.getProduct().getPrice());
            detail.setQuantity(item.getQuantity());
            orderDetails.add(detail);

            totalAmount += detail.getPrice() * detail.getQuantity();
        }

        order.setOrderDetails(orderDetails);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        clearCart(session);

        return savedOrder;
    }

    public Order getOrderById(Long id) {
        if (id == null) {
            return null;
        }
        return orderRepository.findById(id).orElse(null);
    }
}
