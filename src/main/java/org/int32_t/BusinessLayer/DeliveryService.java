package org.int32_t.BusinessLayer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class DeliveryService implements IDeliveryServiceProcessing{
    static Map<Order, Collection<MenuItem>> orders = new HashMap<>();
    static Map<Order, Collection<MenuItem>> completedOrders = new HashMap<>();
    static List<MenuItem> menuItems = new LinkedList<>();
    static List<PropertyChangeListener> listeners = new ArrayList<>();

    static private int currentOrder = 0;

    @Override
    public void createOrder(Collection<MenuItem> items, int clientId) {
        Order order = new Order(currentOrder,clientId,new Date());
        Collection<MenuItem> buff = new LinkedList<MenuItem>(items);

        orders.put(order,buff);
        currentOrder++;

        notifyListeners(items);
    }

    private void notifyListeners(Collection<MenuItem> item){
        for(PropertyChangeListener lst : listeners){
            lst.propertyChange(new PropertyChangeEvent(this, "NewOrder",null, item));
        }
    }

    public void subscribeListener(PropertyChangeListener listener){
        listeners.add(listener);
    }

    public void removeOrder(Order order){
        completedOrders.put(order,orders.get(order));
        orders.remove(order);
        notifyListeners(null);
    }

    public Map<Order, Collection<MenuItem>> getOrders(){
        return orders;
    }

    public static List<MenuItem> getMenu(){
        menuItems.add(new BaseProduct(12,12,13,2,5,10,"Test1"));
        menuItems.add(new BaseProduct(12,12,13,2,5,10,"Test2"));
        menuItems.add(new BaseProduct(12,12,13,2,5,10,"Test3"));
        menuItems.add(new BaseProduct(1,2,13,2,5,10,"Test4"));
        menuItems.add(new BaseProduct(3,12,13,2,5,10,"Test5"));
        menuItems.add(new BaseProduct(3,12,13,2,5,10,"Test6"));
        menuItems.add(new BaseProduct(3,12,13,2,5,10,"Test7"));
        menuItems.add(new BaseProduct(3,12,13,2,5,10,"Test8"));
        menuItems.add(new BaseProduct(3,12,13,2,5,10,"Test9"));
        menuItems.add(new BaseProduct(3,12,13,2,5,10,"Test10"));
        menuItems.add(new BaseProduct(3,12,13,2,5,10,"Test11"));
        menuItems.add(new BaseProduct(3,12,13,2,5,10,"Test12"));
        CompositeProduct item = new CompositeProduct((List<MenuItem>) menuItems,"Comp 1");
        menuItems.add(item);
        return menuItems;
    }
}
