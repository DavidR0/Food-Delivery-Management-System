package org.int32_t.BusinessLayer;

import java.util.*;

public interface IDeliveryServiceProcessing {
    void createOrder(Collection<MenuItem> item, int clientId);
}
