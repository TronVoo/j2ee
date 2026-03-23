package com.example.LAB05.model;

public class CartItem {

    private Product product;
    private Integer quantity;

    public CartItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubtotal() {
        if (product == null || product.getPrice() == null || quantity == null) {
            return 0.0;
        }
        return product.getPrice() * quantity;
    }
}
