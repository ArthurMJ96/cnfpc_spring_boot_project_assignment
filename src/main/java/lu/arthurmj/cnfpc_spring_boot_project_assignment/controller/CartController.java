package lu.arthurmj.cnfpc_spring_boot_project_assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.CartItem;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Product;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.CartService;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.InventoryService;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public String getCart(Model model) {
        List<CartItem> items = cartService.getItems();
        int totalItems = items.stream().mapToInt(CartItem::getQuantity).sum();
        model.addAttribute("items", items);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalAmount", cartService.getTotalAmount());
        model.addAttribute("isEmpty", cartService.isEmpty());
        return "pages/cart/cart";
    }

    @PostMapping("/add")
    public String addToCart(
            @Valid @ModelAttribute CartItem item,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Handle validation errors for quantity
            String message = result.getFieldError("quantity") != null
                    ? result.getFieldError("quantity").getDefaultMessage()
                    : "Invalid cart input.";
            redirectAttributes.addFlashAttribute("cartError", message);
            return "redirect:/cart";
        }

        Product product = productService.findById(item.getProductId());
        if (product == null) {
            throw new EntityNotFoundException("Product with ID '" + item.getProductId() + "' not found.");
        }

        int requested = Math.max(1, item.getQuantity());
        int available = inventoryService.getAvailable(product.getId());
        int currentInCart = cartService.getQuantityForProduct(product.getId());
        int desiredTotal = currentInCart + requested;

        // Handle out of stock scenario
        if (available <= 0) {
            redirectAttributes.addFlashAttribute("cartError", "Sorry, \"" + product.getName() + "\" is out of stock.");
            return "redirect:/cart";
        }

        // Adjust to maximum available stock
        if (desiredTotal > available) {
            int canAdd = Math.max(0, available - currentInCart);
            if (canAdd <= 0) {
                redirectAttributes.addFlashAttribute("cartError",
                        "Only " + available + " unit(s) of \"" + product.getName() + "\" available.");
                return "redirect:/cart";
            }
            cartService.addProduct(product, canAdd);
            redirectAttributes.addFlashAttribute("cartError",
                    "Stock limited: added " + canAdd + " unit(s) of \"" + product.getName() + "\".");
            return "redirect:/cart";
        }

        cartService.addProduct(product, requested);
        redirectAttributes.addFlashAttribute("cartSuccess", product.getName() + " added to cart.");
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(
            @Valid @ModelAttribute CartItem item,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Handle validation errors for quantity
            String message = result.getFieldError("quantity") != null
                    ? result.getFieldError("quantity").getDefaultMessage()
                    : "Invalid cart input.";
            redirectAttributes.addFlashAttribute("cartError", message);
            return "redirect:/cart";
        }

        Long productId = item.getProductId();
        int requested = item.getQuantity();
        int available = inventoryService.getAvailable(productId);

        // Remove item if out of stock
        if (available <= 0) {
            cartService.removeProduct(productId);
            redirectAttributes.addFlashAttribute("cartError", "Item removed: product is out of stock.");
            return "redirect:/cart";
        }

        // Adjust to maximum available stock
        if (requested > available) {
            cartService.updateQuantity(productId, available);
            redirectAttributes.addFlashAttribute("cartError",
                    "Quantity reduced to available stock (" + available + ").");
            return "redirect:/cart";
        }

        cartService.updateQuantity(productId, requested);
        redirectAttributes.addFlashAttribute("cartSuccess", "Cart updated.");
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeItem(
            @RequestParam @NotNull(message = "Product ID is required") Long productId,
            RedirectAttributes redirectAttributes) {
        cartService.removeProduct(productId);
        redirectAttributes.addFlashAttribute("cartSuccess", "Item removed from cart.");
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(RedirectAttributes redirectAttributes) {
        cartService.clear();
        redirectAttributes.addFlashAttribute("cartSuccess", "Cart cleared.");
        return "redirect:/cart";
    }
}
