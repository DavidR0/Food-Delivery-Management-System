package org.int32_t.BusinessLayer;

import org.int32_t.DataLayer.FileWriter;
import org.int32_t.DataLayer.Serializator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
* Class IDeliveryServiceProcessing implementation
* @invariant isWellFormed()
* */
public class DeliveryService implements IDeliveryServiceProcessing{
    private static Map<Order, Collection<MenuItem>> orders = new HashMap<>();
    private static Map<Order, Collection<MenuItem>> completedOrders = new HashMap<>();
    private static List<MenuItem> menuItems = new LinkedList<>();
    private static List<PropertyChangeListener> listeners = new ArrayList<>();
    private static boolean deserializedObjects = false;
    private static final String ordersFileName = "ordersObj.txt";
    private static final String menuFileName = "menuObj.txt";
    private static final String finishedOrdersFileName = "finishedOrdersObj.txt";
    static private int currentOrder = 0;

    public DeliveryService() {
        assert isWellFormed();

        if(!deserializedObjects){
            System.out.println("Getting DeliveryService Objects");
            //Load orders
            Serializator<Map<Order, Collection<MenuItem>>> sr = new Serializator<>();
            orders = sr.deserialize(orders,ordersFileName);
            //Load finished orders
            completedOrders = sr.deserialize(completedOrders,finishedOrdersFileName);
            //Load Menu
            Serializator<List<MenuItem>> sr1 = new Serializator<>();
            menuItems = sr1.deserialize(menuItems,menuFileName);
            deserializedObjects = true;
        }

    }

    protected boolean isWellFormed(){
        if(orders == null) return false;
        if(completedOrders == null) return false;
        if(menuItems == null) return false;
        if(currentOrder < 0) return false;
        if(listeners == null) return false;
        return true;
    }


    @Override
    public void createOrder(Collection<MenuItem> items, int clientId) {
        assert items != null && clientId > 0;

        Order order = new Order(currentOrder,clientId,new Date());
        Collection<MenuItem> buff = new LinkedList<MenuItem>(items);

        StringBuilder orderBill = new StringBuilder();
        buff.forEach(n->{
            orderBill.append(n.getTitle()).append("    ").append(n.getPrice()).append("$\n");
        });

        new FileWriter("BillOrder"+clientId+currentOrder+".txt",orderBill.toString());

        orders.put(order,buff);
        Serializator<Map<Order, Collection<MenuItem>>> sr = new Serializator<>();
        sr.toSerial(orders,ordersFileName);

        currentOrder++;

        notifyListeners(items);
    }

    private void notifyListeners(Collection<MenuItem> item){
        for(PropertyChangeListener lst : listeners){
            lst.propertyChange(new PropertyChangeEvent(this, "NewOrder",null, item));
        }
    }

    public void subscribeListener(PropertyChangeListener listener){
        assert listener != null;

        listeners.add(listener);
    }

    public void removeOrder(Order order){
        assert order != null;

        completedOrders.put(order,orders.get(order));
        orders.remove(order);
        Serializator<Map<Order, Collection<MenuItem>>> sr = new Serializator<>();
        sr.toSerial(orders,ordersFileName);
        sr.toSerial(completedOrders,finishedOrdersFileName);

        notifyListeners(null);
    }

    public Map<Order, Collection<MenuItem>> getOrders(){
        assert orders != null && orders.size() > 0;
        return orders;
    }

    public static Map<Order, Collection<MenuItem>> getCompletedOrders(){ return completedOrders; }


    private static final Function<String, MenuItem> mapToItem = (line) -> {
        String[] p = line.split(",");// a CSV has comma separated lines
        return new BaseProduct(Float.parseFloat(p[1]),Integer.parseInt(p[2]),Integer.parseInt(p[3]),Integer.parseInt(p[4]),Integer.parseInt(p[5]),Integer.parseInt(p[6]),p[0]);
    };

    public static void loadMenuFromCSV(){
        try{

            Predicate<MenuItem> itemFilter = n -> (!menuItems.contains(n));

            List<MenuItem> inputList = new ArrayList<>();
            File inputF = new File("src/main/java/org/int32_t/Resources/products.csv");
            InputStream inputFS = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

            // Read the csv
            inputList = br.lines().distinct().skip(1).map(mapToItem).distinct().collect(Collectors.toList());
            br.close();
            // Update products with only new items from csv
            menuItems.addAll(inputList.stream().filter(itemFilter).collect(Collectors.toList()));
            System.out.println("Size: " + menuItems.size());
            Serializator<List<MenuItem>> sr1 = new Serializator<>();
            sr1.toSerial(menuItems,menuFileName);


        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e);
        }

    }

    public void addToMenu(MenuItem productToAdd){
        assert productToAdd != null;

        menuItems.add(productToAdd);
        Serializator<List<MenuItem>> sr1 = new Serializator<>();
        sr1.toSerial(menuItems,menuFileName);
    }

    public void deleteFromMenu(MenuItem productToDelete){
        assert productToDelete != null;

        menuItems.remove(productToDelete);
        Serializator<List<MenuItem>> sr1 = new Serializator<>();
        sr1.toSerial(menuItems,menuFileName);
    }


    public static List<MenuItem> getMenu(){
        return menuItems;
    }
}
