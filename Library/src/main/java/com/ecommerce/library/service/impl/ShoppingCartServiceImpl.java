package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.CartItemDto;
import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.dto.ShoppingCartDto;
import com.ecommerce.library.model.CartItem;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.repository.CartItemRepository;
import com.ecommerce.library.repository.ShoppingCartRepository;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ShoppingCart addItemToCart(ProductDto productDto, int quantity, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getCart();

        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }
        Set<CartItem> cartItemList = shoppingCart.getCartItems();
        CartItem cartItem = find(cartItemList, productDto.getId());
        Product product = transfer(productDto);
        double unitPrice = productDto.getCostPrice();

        int itemQuantity = 0;

        if (cartItemList == null) {
            cartItemList = new HashSet<>();
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCart(shoppingCart);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(unitPrice);
                cartItemList.add(cartItem);
                cartItemRepository.save(cartItem);
            } else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
                cartItemRepository.save(cartItem);
            }
        } else {
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCart(shoppingCart);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(unitPrice);
                cartItemList.add(cartItem);
                cartItemRepository.save(cartItem);
            } else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
                cartItemRepository.save(cartItem);
            }
        }
        shoppingCart.setCartItems(cartItemList);

        double totalPrice = totalPrice(shoppingCart.getCartItems());
        int totalItem = totalItem(shoppingCart.getCartItems());

        shoppingCart.setTotalPrice(totalPrice);
        shoppingCart.setTotalItems(totalItem);
        shoppingCart.setCustomer(customer);


        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart updateCart(ProductDto productDto, int quantity, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getCart();
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        CartItem cartItem = find(cartItems, productDto.getId());
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        shoppingCart.setCartItems(cartItems);
        int totalItem = totalItem(cartItems);
        double totalPrice = totalPrice(cartItems);
        shoppingCart.setTotalPrice(totalPrice);
        shoppingCart.setTotalItems(totalItem);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ShoppingCart removeItemFromCart(ProductDto productDto, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getCart();
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        CartItem cartItem = find(cartItems, productDto.getId());
        cartItems.remove(cartItem);
        cartItemRepository.delete(cartItem);
        double totalPrice = totalPrice(cartItems);
        int totalItem = totalItem(cartItems);
        shoppingCart.setCartItems(cartItems);
        shoppingCart.setTotalItems(totalItem);
        shoppingCart.setTotalPrice(totalPrice);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartDto addItemToCartSession(ShoppingCartDto cartDto, ProductDto productDto, int quantity) {
        CartItemDto cartItemDto = findInDto(cartDto, productDto.getId());
        if (cartDto == null) {
            cartDto = new ShoppingCartDto();
        }
        Set<CartItemDto> cartItems = cartDto.getCartItems();
        double unitPrice = productDto.getCostPrice();
        int itemQuantity = 0;
        if (cartItems == null) {
            cartItems = new HashSet<>();
            if (cartItemDto == null) {
                cartItemDto = new CartItemDto();
                cartItemDto.setProduct(productDto);
                cartItemDto.setCart(cartDto);
                cartItemDto.setQuantity(quantity);
                cartItemDto.setUnitPrice(unitPrice);
                cartItems.add(cartItemDto);
                System.out.println("add");
            } else {
                itemQuantity = cartItemDto.getQuantity() + quantity;
                cartItemDto.setQuantity(itemQuantity);
            }
        } else {
            if (cartItemDto == null) {
                cartItemDto = new CartItemDto();
                cartItemDto.setProduct(productDto);
                cartItemDto.setCart(cartDto);
                cartItemDto.setQuantity(quantity);
                cartItemDto.setUnitPrice(unitPrice);
                cartItems.add(cartItemDto);
                System.out.println("add");
            } else {
                itemQuantity = cartItemDto.getQuantity() + quantity;
                cartItemDto.setQuantity(itemQuantity);
            }
        }
        cartDto.setCartItems(cartItems);
        double totalPrice = totalPriceDto(cartItems);
        int totalItem = totalItemDto(cartItems);
        cartDto.setTotalItems(totalItem);
        cartDto.setTotalPrice(totalPrice);
        System.out.println(cartDto.getTotalItems());
        System.out.println(cartDto.getTotalPrice());
        System.out.println("success");
        return cartDto;
    }

    @Override
    public ShoppingCartDto updateCartSession(ShoppingCartDto cartDto, ProductDto productDto, int quantity) {
        Set<CartItemDto> cartItems = cartDto.getCartItems();
        CartItemDto item = findInDto(cartDto, productDto.getId());
        int itemQuantity = item.getQuantity() + quantity;
        int totalItem = totalItemDto(cartItems);
        double totalPrice = totalPriceDto(cartItems);
        item.setQuantity(itemQuantity);
        cartDto.setCartItems(cartItems);
        cartDto.setTotalPrice(totalPrice);
        cartDto.setTotalItems(totalItem);
        System.out.println(cartDto.getTotalItems());
        return cartDto;
    }

    @Override
    public ShoppingCartDto removeItemFromCartSession(ShoppingCartDto cartDto, ProductDto productDto, int quantity) {
        Set<CartItemDto> cartItems = cartDto.getCartItems();
        CartItemDto itemDto = findInDto(cartDto, productDto.getId());
        cartItems.remove(itemDto);
        double totalPrice = totalPriceDto(cartItems);
        int totalItem = totalItemDto(cartItems);
        cartDto.setCartItems(cartItems);
        cartDto.setTotalPrice(totalPrice);
        cartDto.setTotalItems(totalItem);
        System.out.println(cartDto.getTotalItems());
        return cartDto;
    }

    @Override
    public ShoppingCart combineCart(ShoppingCartDto cartDto, ShoppingCart cart) {
        if (cart == null) {
            cart = new ShoppingCart();
        }
        Set<CartItem> cartItems = cart.getCartItems();
        if (cartItems == null) {
            cartItems = new HashSet<>();
        }
        Set<CartItem> cartItemList = convertCartItem(cartDto.getCartItems(), cart);
        cartItems.addAll(cartItemList);
        double totalPrices = totalPrice(cartItems);
        int totalItems = totalItem(cartItems);
        cart.setTotalItems(totalItems);
        cart.setCartItems(cartItems);
        cart.setTotalPrice(totalPrices);
        return cart;
    }

    @Override
    @Transactional
    public void deleteCartById(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id);
        if (!ObjectUtils.isEmpty(shoppingCart) && !ObjectUtils.isEmpty(shoppingCart.getCartItems())) {
            cartItemRepository.deleteAll(shoppingCart.getCartItems());
        }
        shoppingCart.getCartItems().clear();
        shoppingCart.setTotalItems(0);
        shoppingCart.setTotalPrice(0);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getCart(String username) {
        Customer customer = customerService.findByUsername(username);
        return customer.getCart();
    }

    private CartItemDto findInDto(ShoppingCartDto shoppingCartDto, long productId) {
        return shoppingCartDto.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findAny()
                .orElse(null);
    }


    private CartItem find(Set<CartItem> cartItems, long productId) {
        return cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findAny()
                .orElse(null);
    }

//    private int totalItem(Set<CartItem> cartItems) {
//        AtomicInteger totalItem = new AtomicInteger();
//        cartItems.forEach(item -> totalItem.addAndGet(item.getQuantity()));
//        return totalItem.get();
//    }

    private int totalItem(Set<CartItem> cartItems) {
        int totalItem = 0;
        for (CartItem cartItem : cartItems) {
            totalItem += cartItem.getQuantity();
        }
        return totalItem;
    }

    private double totalPrice(Set<CartItem> cartItems) {
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getUnitPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }

    private int totalItemDto(Set<CartItemDto> cartItems) {
        int totalItem = 0;
        for (CartItemDto cartItem : cartItems) {
            totalItem += cartItem.getQuantity();
        }
        return totalItem;
    }

    private double totalPriceDto(Set<CartItemDto> cartItems) {
        double totalPrice = 0.0;
        for (CartItemDto cartItem : cartItems) {
            totalPrice += cartItem.getUnitPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }

    private Set<CartItem> convertCartItem(Set<CartItemDto> cartItemDtos, ShoppingCart cart) {
        HashSet<CartItem> cartItems = new HashSet<>();
        for (CartItemDto cartItemDto : cartItemDtos) {
            CartItem cartItem = new CartItem();
            cartItem.setQuantity(cartItemDto.getQuantity());
            cartItem.setProduct(transfer(cartItemDto.getProduct()));
            cartItem.setCart(cart);
            cartItem.setUnitPrice(cartItemDto.getUnitPrice());
            cartItem.setId(cartItemDto.getId());
            cartItems.add(cartItem);
        }
        return cartItems;

    }

    private Product transfer(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }


}

