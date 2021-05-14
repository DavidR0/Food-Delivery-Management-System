package org.int32_t.BusinessLayer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


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

        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e);
        }

    }

    public void addToMenu(MenuItem productToAdd){
        menuItems.add(productToAdd);
    }

    public void deleteFromMenu(MenuItem productToDelete){
        menuItems.remove(productToDelete);
    }


    public static List<MenuItem> getMenu(){
        return menuItems;
    }
}
