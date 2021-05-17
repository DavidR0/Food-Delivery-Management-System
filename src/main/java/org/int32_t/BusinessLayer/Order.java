package org.int32_t.BusinessLayer;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable {
    private int orderID;
    private int clientID;
    private Date date;
    public Order(int orderID, int clientID, Date date) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.date = date;
    }
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderID == order.orderID && clientID == order.clientID && date.equals(order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, clientID, date);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", clientID=" + clientID +
                ", date=" + date +
                '}';
    }
}
