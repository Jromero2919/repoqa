package com.parasoft.bookstore2;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class CartManager {
    private static Map<Integer, List<Order>> cartIdToOrderMap =
        Collections.synchronizedMap(new ConcurrentHashMap<Integer, List<Order>>());
    private static AtomicInteger generatedNewCartId = new AtomicInteger(0);
    private int cartId;
    private List<Order> list;
    
    public CartManager() {
        //default implementation
    }
    
    public CartManager(int cartId) throws Exception {
        if (!cartIdToOrderMap.containsKey(cartId)) {
            throw new Exception("cartId: " +
                    cartId + " doesn't exist.");
        }
        setCartId(cartId);
        setItem(cartIdToOrderMap.get(cartId));
    }
    
    public void addNewItemToCart(Order order) {
        List<Order> list = new ArrayList<Order>();
        list.add(order);
        cartIdToOrderMap.put(generatedNewCartId.incrementAndGet(), list);
    }
    
    public Order addExistingItemToCart(int cartId, Order order) throws Exception {
        //cartId exists
        if (cartIdToOrderMap.containsKey(cartId)) {
            List<Order> list = cartIdToOrderMap.get(cartId);
            Iterator<Order> iterator = list.iterator();
            boolean found = false;
            while (iterator.hasNext()) {
                Order o = iterator.next();
                Book book = o.getBook();
                if (order.getBook().getProductInfo().getId() == book.getProductInfo().getId()) {
                    o.refreshTimestamp();
                    o.modifyCount(order.getQuantity());
                    return o;
                }
            }
            if (!found) {
                list.add(order);
                return order;
            }
        } else { //cartId doesn't exist
            throw new Exception("An order with Cart Id " + cartId + " does not exist!");
        }
        return null;
    }
    
    public Order updateExistingItem(int cartId, int itemId, int quantity) throws Exception {
        //cartId exists
        if (cartIdToOrderMap.containsKey(cartId)) {
            List<Order> list = cartIdToOrderMap.get(cartId);
            Iterator<Order> iterator = list.iterator();
            boolean found = false;
            while (iterator.hasNext()) {
                Order order = iterator.next();
                Book book = order.getBook();
                if (itemId == book.getProductInfo().getId()) {
                    if (quantity > book.getProductInfo().getStockQuantity()) {
                        throw new Exception("Did not update order with cartId " + 
                                cartId + ", " + quantity + " is greater than " +
                                "the quantity in stock: " + book.getProductInfo().getStockQuantity());
                    }
                    order.setQuantity(quantity);
                    order.refreshTimestamp();
                    return order;
                }
            }
            if(!found) {
                throw new Exception("Did not update order with cartId " + 
                        cartId + ", order does not exist.");
            }
        } else { //cartId doesn't exist
            throw new Exception("Did not update order with cartId " + cartId +
                    ", itemId " + itemId + " does not exist in the order.");
        }
        //should never get here
        return null;
    }
    
    public int getCartSize() {
        return cartIdToOrderMap.size();
    }
    
    public Map<Integer, List<Order>> getCart() {
        return cartIdToOrderMap;
    }
    
    public void removeEmptyMappings() {
        Iterator<Map.Entry<Integer, List<Order>>> itr = cartIdToOrderMap.entrySet().iterator();
        while (itr.hasNext()) {
            if (itr.next().getValue().isEmpty()) {
                itr.remove();
            }
        }
    }
    
    public boolean removeOrder(int cartId) {
        boolean found = false;
        if (cartIdToOrderMap.containsKey(cartId)) {
            cartIdToOrderMap.remove(cartId);
            found = true;
        }
        return found;
    }
    
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }
    
    public int getCartId() {
        return cartId;
    }
    
    public void setItem(List<Order> list) {
        this.list = list;
    }
    
    public List<Order> getItem() {
        return list;
    }
    
    public int getStaticCart_Id() {
        return generatedNewCartId.get();
    }
}