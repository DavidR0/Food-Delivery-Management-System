package org.int32_t.BusinessLayer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class DeliveryService implements IDeliveryServiceProcessing{
    static Map<Order, Collection<MenuItem>> orders = new HashMap<>();
    static Map<Order, Collection<MenuItem>> completedOrders = new HashMap<>();
    static Collection<MenuItem> menuItems = new LinkedList<>();
    static List<PropertyChangeListener> listeners = new ArrayList<>();

    int currentOrder = 0;

    @Override
    public void createOrder(Collection<MenuItem> items, int clientId) {
        Order order = new Order(currentOrder,clientId,new Date());
        orders.put(order,items);
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
        List<MenuItem> list = new LinkedList<>();
        list.add(new BaseProduct(12,12,13,2,5,10,"Test1"));
        list.add(new BaseProduct(12,12,13,2,5,10,"Test5"));
        list.add(new BaseProduct(12,12,13,2,5,10,"Test6"));
        list.add(new BaseProduct(1,2,13,2,5,10,"Test2"));
        list.add(new BaseProduct(3,12,13,2,5,10,"Test3"));
        CompositeProduct item = new CompositeProduct(list,"Comp 1");
        list.add(item);
        return list;
    }
}
