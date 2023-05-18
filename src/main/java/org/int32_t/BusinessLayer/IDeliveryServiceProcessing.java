package org.int32_t.BusinessLayer;

import java.beans.PropertyChangeListener;
import java.util.*;

public interface IDeliveryServiceProcessing {
    /**
     * @pre item != null && item.size() > 0 && clientId >= 0
     * @param item
     * @param clientId
     */
    void createOrder(Collection<MenuItem> item, int clientId);

    /**
     * @pre listener != null
     * @post
     * */
    void subscribeListener(PropertyChangeListener listener);

    /**
     * @pre order != null
     * @post
     * */
    void removeOrder(Order order);

    /**
     * @pre productToDelete != null
     * @post
     * */
    void deleteFromMenu(MenuItem productToDelete);

    /**
     * @pre productToAdd != null
     * @post
     * */
    void addToMenu(MenuItem productToAdd);

    /**
     * @pre
     * @post Map<Order, Collection<MenuItem>> != null
     * */
    Map<Order, Collection<MenuItem>> getOrders();
}
