package lu.arthurmj.cnfpc_spring_boot_project_assignment.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.CartItem;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Product;

@Service
@SessionScope
public class CartService {

    private final Map<Long, CartItem> items = new LinkedHashMap<>();

    public void addProduct(Product product, int quantity) {
        if (product == null || product.getId() == null) {
            return;
        }

        int safeQty = Math.max(1, quantity);
        CartItem existing = items.get(product.getId());
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + safeQty);
            return;
        }

        CartItem item = new CartItem();
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setPrice(product.getPrice() != null ? product.getPrice() : 0);
        item.setImageUrl(product.getImages().isEmpty() ? null : product.getImages().get(0));
        item.setQuantity(safeQty);
        items.put(product.getId(), item);
    }

    public int getQuantityForProduct(Long productId) {
        CartItem item = items.get(productId);
        return item == null ? 0 : item.getQuantity();
    }

    public void updateQuantity(Long productId, int quantity) {
        CartItem item = items.get(productId);
        if (item == null) {
            return;
        }

        if (quantity <= 0) {
            items.remove(productId);
            return;
        }

        item.setQuantity(quantity);
    }

    public void removeProduct(Long productId) {
        items.remove(productId);
    }

    public void clear() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items.values());
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getTotalAmount() {
        return items.values().stream()
                .mapToInt(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public int getTotalQuantity() {
        return items.values().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}
