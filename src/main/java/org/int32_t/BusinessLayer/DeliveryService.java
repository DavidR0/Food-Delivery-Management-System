package org.int32_t.BusinessLayer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class DeliveryService implements IDeliveryServiceProcessing{
    static Map<Order, Collection<MenuItem>> orders = new HashMap<>();
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
        orders.remove(order);
        notifyListeners(null);
    }

    public Map<Order, Collection<MenuItem>> getOrders(){
        return orders;
    }
}
