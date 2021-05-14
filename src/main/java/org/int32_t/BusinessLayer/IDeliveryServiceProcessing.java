package org.int32_t.BusinessLayer;

import java.beans.PropertyChangeListener;
import java.util.*;

/*TODO pre post conditions*/

public interface IDeliveryServiceProcessing {
    void createOrder(Collection<MenuItem> item, int clientId);
    void subscribeListener(PropertyChangeListener listener);
    void removeOrder(Order order);
    Map<Order, Collection<MenuItem>> getOrders();
}
