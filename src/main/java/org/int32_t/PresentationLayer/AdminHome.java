package org.int32_t.PresentationLayer;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.int32_t.BusinessLayer.*;
import org.int32_t.DataLayer.FileWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AdminHome extends StackPane{

    private int pageMultiplier = 0;
    private final int nrElementsPerPage = 10;
    private List<MenuItem> itemsList = new LinkedList<>();

    @FXML
    private JFXTextField startHour;

    @FXML
    private JFXTextField endHour;

    @FXML
    private JFXCheckBox selectOp1;

    @FXML
    private JFXCheckBox selectOp2;

    @FXML
    private JFXTextField nrOrders;

    @FXML
    private JFXTextField orderValue;

    @FXML
    private JFXTextField amount1;

    @FXML
    private JFXCheckBox selectOp3;

    @FXML
    private DatePicker datePicker;

    @FXML
    private JFXCheckBox selectOp4;

    @FXML
    private StackPane root;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private VBox productsList;

    @FXML
    private JFXTextField keyword;

    @FXML
    private JFXTextField rating;

    @FXML
    private JFXTextField nrCalories;

    @FXML
    private JFXTextField proteins;

    @FXML
    private JFXTextField fats;

    @FXML
    private JFXTextField sodium;

    @FXML
    private JFXTextField price;

    @FXML
    void logout(ActionEvent event) {
        productsList.getChildren().clear();
        itemsList.clear();

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(event1 -> {

            try {
                loadSignIn();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        fadeTransition.play();
    }

    private void loadSignIn() throws IOException {
        Stage thisStage = (Stage) rootPane.getScene().getWindow();
        thisStage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../PresentationLayer/loginAdmin.fxml"))), 1123, 721));
    }



    @FXML
    void nextPage(ActionEvent event) {
        if (pageMultiplier * (nrElementsPerPage - 1) < itemsList.size()) {
            pageMultiplier++;
            updateView(itemsList);
        }
    }

    @FXML
    void previousPage(ActionEvent event) {
        if (pageMultiplier >= 1) {
            pageMultiplier--;
            updateView(itemsList);
        }
    }

    @FXML
    public void initialize(){
        itemsList.addAll(DeliveryService.getMenu());
        updateView(DeliveryService.getMenu());
        rootPane.setOpacity(0);
        fadeIn();
    }

    private void fadeIn(){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    @FXML
    void loadCSV(ActionEvent event) {
        DeliveryService.loadMenuFromCSV();
        itemsList.clear();
        itemsList.addAll(DeliveryService.getMenu());
        updateView(DeliveryService.getMenu());
    }


    @FXML
    public void newProduct(ActionEvent actionEvent) {
        NewProductAdmin diag = new NewProductAdmin();
        JFXDialog dialog = new JFXDialog(root, diag, JFXDialog.DialogTransition.CENTER);
        dialog.show();
        diag.setDiag(dialog);
    }

    private void updateView(List<MenuItem> orders) {

        List<MenuItemView> filterItemsListView = new LinkedList<>();
        //Update the view with the new changes
        for (int i = pageMultiplier * nrElementsPerPage; i < (pageMultiplier + 1) * nrElementsPerPage && i < orders.size(); ++i) {
            MenuItem entry = orders.get(i);
            BaseProduct base = (BaseProduct) entry;
            filterItemsListView.add(new MenuItemView(this,null, null, null, null, base, false));

        }
        filterItemsListView.forEach(n->n.setBtnText("Edit"));
        productsList.getChildren().setAll(filterItemsListView);
    }

    @FXML
    public void filterItems(ActionEvent actionEvent) {
        Predicate<MenuItem> priceFilter = n -> (this.price.getText().isEmpty() || n.getPrice() <= Integer.parseInt(this.price.getText()));
        Predicate<MenuItem> fatFilter = n -> (this.fats.getText().isEmpty() || n.getFat() <= Integer.parseInt(this.fats.getText()));
        Predicate<MenuItem> sodiumFilter = n -> (this.sodium.getText().isEmpty() || n.getSodium() <= Integer.parseInt(this.sodium.getText()));
        Predicate<MenuItem> ratingFilter = n -> (this.rating.getText().isEmpty() || n.getRating() >= Float.parseFloat(this.rating.getText()));
        Predicate<MenuItem> nrCaloriesFilter = n -> (this.nrCalories.getText().isEmpty() || n.getCalories() <= Integer.parseInt(this.nrCalories.getText()));
        Predicate<MenuItem> proteinsFilter = n -> (this.proteins.getText().isEmpty() || n.getProtein() <= Integer.parseInt(this.proteins.getText()));
        Predicate<MenuItem> keywordFilter = n -> (this.keyword.getText().isEmpty() || n.getTitle().toLowerCase().contains(this.keyword.getText().toLowerCase()));

        itemsList = DeliveryService.getMenu().stream().filter(priceFilter).filter(fatFilter).filter(keywordFilter).filter(sodiumFilter).filter(ratingFilter).filter(nrCaloriesFilter).filter(proteinsFilter).collect(Collectors.toList());
        pageMultiplier = 0;
        updateView(itemsList);
    }


    public void editProductDiag(MenuItem item){
        EditProductAdmin diag = new EditProductAdmin(this,item);
        JFXDialog dialog = new JFXDialog(root, diag, JFXDialog.DialogTransition.CENTER);
        diag.setDiag(dialog);
        dialog.show();
    }

    public void refresh(ActionEvent actionEvent) {
       filterItems(null);
    }

    @FXML
    void newCompositeProduct(ActionEvent event) {
        AdminCompositeProduct diag = new AdminCompositeProduct();
        JFXDialog dialog = new JFXDialog(root, diag, JFXDialog.DialogTransition.CENTER);
        diag.setDiag(dialog);
        dialog.show();

    }

    @FXML
    void generateReport(ActionEvent event) {
        Map<Order, Collection<MenuItem>> completedOrders = DeliveryService.getCompletedOrders();
        StringBuilder report = new StringBuilder();
        report.append("Start of Report: \n");

        if(selectOp1.isSelected()){
            if(!startHour.getText().isEmpty() && !endHour.getText().isEmpty()){
                //Generate report 1
                report.append( "Option 1 : \n");
                Predicate<Map.Entry<Order, Collection<MenuItem>>> dateFilter = n -> (Integer.parseInt(startHour.getText()) <= n.getKey().getDate().getHours() && n.getKey().getDate().getHours() <= Integer.parseInt(endHour.getText()));

                StringBuilder finalReport = new StringBuilder();
                completedOrders.entrySet().stream().filter(dateFilter).forEach(n->{
                    n.getValue().forEach(m->{
                        finalReport.append(m.getTitle()).append("\n");
                    });
                });

                report.append(finalReport.toString()).append("\n");
            }
        }

        if(selectOp2.isSelected()) {
            //Generate report 2
            if(!amount1.getText().isEmpty()) {
                report.append("Option 2 : \n");
                Predicate<Map.Entry<String, Integer>> amountFilter = n -> (n.getValue() >= Integer.parseInt(amount1.getText()));
                Map<String, Integer> numberTimesOrdered = new HashMap<>();

                //See how many times each item was ordered
                completedOrders.forEach((key, value) -> value.forEach(m -> {
                    numberTimesOrdered.merge(m.getTitle(), 1, Integer::sum);
                }));
                //create the string report
                StringBuilder finalReport = new StringBuilder();
                numberTimesOrdered.entrySet().stream().filter(amountFilter).forEach(n->{
                    finalReport.append(n.getKey()).append("     ").append(n.getValue()).append("x\n");
                });



                //See how many times each item was ordered
//                for (Map.Entry<Order, Collection<MenuItem>> entry : completedOrders.entrySet()) {
//                    for (MenuItem item : entry.getValue()) {
//                        numberTimesOrdered.merge(item.getTitle(), 1, Integer::sum);
//                    }
//                }
//
//                for (Map.Entry<String, Integer> entry : numberTimesOrdered.entrySet()) {
//                    if (entry.getValue() >= Integer.parseInt(amount1.getText())) {
//                        report += entry.getKey() +"    " +entry.getValue() + "x\n";
//                    }
//                }
                report.append(finalReport).append("\n");
            }
        }

        if(selectOp3.isSelected()){
            if(!nrOrders.getText().isEmpty() && !orderValue.getText().isEmpty()){
                //Generate report 3
                report.append("Option 3 : \n");
                Predicate<Map.Entry<Order, Integer>> amountFilter = n -> (n.getValue() >= Integer.parseInt(this.orderValue.getText()));

                Map<Order, Integer> numberTimesOrdered = new HashMap<>();
                StringBuilder finalReport = new StringBuilder();

                completedOrders.forEach((key, value) -> value.forEach(m -> {
                    numberTimesOrdered.merge(key, m.getPrice(), Integer::sum);
                }));

                numberTimesOrdered.entrySet().stream().filter(amountFilter).forEach(n->{
                    finalReport.append(n.getKey().getClientID()).append("\n");
                });

//                //Count the number of orders per customer whose order is larger then a specific amount
//                for (Map.Entry<Order, Collection<MenuItem>> entry : completedOrders.entrySet()) {
//                    int orderValue = 0;
//                    for (MenuItem item : entry.getValue()) {
//                        orderValue += item.getPrice();
//                    }
//                    if(orderValue >= Integer.parseInt(this.orderValue.getText())) {
//                        numberTimesOrdered.merge(entry.getKey().getClientID(), 1, Integer::sum);
//                    }
//                }
//
//                for (Map.Entry<Integer, Integer> entry : numberTimesOrdered.entrySet()) {
//                    if(entry.getValue() >= Integer.parseInt(nrOrders.getText())){
//                        report += entry.getKey() + "\n";
//                    }
//                }
//
                report.append(finalReport).append("\n");
            }
        }

        if(selectOp4.isSelected()) {
            if (datePicker.getValue() != null) {
                //Generate report 4
                report.append("Option 4 : \n");
                Map<String, Integer> numberTimesOrdered = new HashMap<>();
                Predicate<Map.Entry<Order, Collection<MenuItem>>> dateFilter = n -> (convertToLocalDateViaSqlDate(n.getKey().getDate()).compareTo(datePicker.getValue()) == 0);
                StringBuilder finalReport = new StringBuilder();


                completedOrders.entrySet().stream().filter(dateFilter).forEach(m -> {
                    m.getValue().forEach(n->{
                        numberTimesOrdered.merge(n.getTitle(), 1, Integer::sum);
                    });
                });

                numberTimesOrdered.forEach((key, value) -> finalReport.append(key).append("     ").append(value).append("x\n"));

//
//                //See how many times each item was ordered
//                for (Map.Entry<Order, Collection<MenuItem>> entry : completedOrders.entrySet()) {
//                    if(convertToLocalDateViaSqlDate(entry.getKey().getDate()).compareTo(datePicker.getValue()) == 0) {
//                        for (MenuItem item : entry.getValue()) {
//                            numberTimesOrdered.merge(item.getTitle(), 1, Integer::sum);
//                        }
//                    }
//                }
//
//
//                for (Map.Entry<String, Integer> entry : numberTimesOrdered.entrySet()) {
//                        report += entry.getKey() + "      "+ entry.getValue() +"x\n";
//                }
//
//
                report.append(finalReport).append("\n");
            }
        }

       // System.out.println(report);
        String fName = "report" + String.valueOf(new Date().getTime()) + ".txt";
        new FileWriter(fName,report.toString());
    }

    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }
}
