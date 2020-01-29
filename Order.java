import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;

public class Order {
    private int curNumItems = 0;
    private double subtotal = 0;
    private double total = 0;
    private int totalItems = 0;
    private int maxItems = -1;
    private String filename = "transactions.txt";
    private ArrayList<String> items = new ArrayList<>(); //all confirmed items
    private StringBuilder viewOrder = new StringBuilder();
    private StringBuilder finishOrder = new StringBuilder();

    File file = new File(filename);
    String[] itemInfo = new String[6];

    
    public String getViewOrder() {
        return this.viewOrder.toString();
    }

    public void addToViewOrder(String order) {
        viewOrder.append(this.getTotalItems() + ". " + order);
        viewOrder.append(System.getProperty("line.separator"));
    }

    public String[] getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(String bID, String title, String price, String numItems, String discountPercentage, String discount) {
        itemInfo[0] = bID;
        itemInfo[1] = title;
        itemInfo[2] = price;
        itemInfo[3] = numItems;
        itemInfo[4] = discountPercentage;
        itemInfo[5] = discount;
    }

    public double getdiscount(int quantity, double bookPrice) {

        if(quantity >= 1 && quantity <= 4 )
            return (quantity * bookPrice); 
        if(quantity >= 5 && quantity <= 9)
            return .90 * (quantity * bookPrice);
        if(quantity >= 10 && quantity <= 14)
            return .85 * (quantity * bookPrice);
        if(quantity >= 15)
            return .80 * (quantity * bookPrice);

        return 0.0;
    }

    public int getDiscountPercentage(int quantity) {
        if(quantity >= 1 && quantity <= 4 )
            return 0;
        if(quantity >= 5 && quantity <= 9)
            return 10;
        if(quantity >= 10 && quantity <= 14)
            return 15; 
        if(quantity >= 15)
            return 20;
        return 0;
    }

    public String viewOrder() {
        return filename;

    }

    public void prepareTransaction() {
        String lineItem = new String();
        for(int i = 0; i< this.itemInfo.length; i++){
            lineItem += this.itemInfo[i] + ", ";
        }
        items.add(lineItem);
    }

    public void printTransactions() throws IOException {
        Calendar calendar= Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat permutation = new SimpleDateFormat("yyMMddyyHHmm");
        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a z");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

        this.setFinishOrder(dateFormat.format(date), time.format(date));

        if(file.exists() == false) {
            file.createNewFile();
        }

        PrintWriter outputStream = new PrintWriter(new FileWriter(filename, true));

        for(int i = 0; i< this.items.size(); i++){
            outputStream.append(permutation.format(date) + ", ");
            String lineItem = this.items.get(i);
            outputStream.append(lineItem);
            outputStream.append(dateFormat.format(date) + ", ");
            outputStream.append(time.format(date));
            outputStream.println();
        }

        outputStream.flush();
        outputStream.close();
    }

    public void setFinishOrder(String date, String time) {
        this.settotal();
        this.finishOrder.append("Date: " + date + " " + time);

        this.finishOrder.append(System.getProperty("line.separator"));
        this.finishOrder.append(System.getProperty("line.separator"));

        this.finishOrder.append("Number of line items: " + this.getTotalItems());

        this.finishOrder.append(System.getProperty("line.separator"));
        this.finishOrder.append(System.getProperty("line.separator"));
        this.finishOrder.append("Item# /ID / Price / Qty / Disc %/ Subtotal");

        this.finishOrder.append(System.getProperty("line.separator"));
        this.finishOrder.append(System.getProperty("line.separator"));
        this.finishOrder.append(this.getViewOrder());
        
        this.finishOrder.append(System.getProperty("line.separator"));
        this.finishOrder.append(System.getProperty("line.separator"));
        this.finishOrder.append("Order subtotal:   $" + new DecimalFormat("#0.00").format(this.getsubtotal()));

        this.finishOrder.append(System.getProperty("line.separator"));
        this.finishOrder.append(System.getProperty("line.separator"));
        this.finishOrder.append("Tax rate:     6%");

        this.finishOrder.append(System.getProperty("line.separator"));
        this.finishOrder.append(System.getProperty("line.separator"));
        this.finishOrder.append("Order total:      $" + new DecimalFormat("#0.00").format(this.gettotal()));

        this.finishOrder.append(System.getProperty("line.separator"));
        this.finishOrder.append(System.getProperty("line.separator"));
        this.finishOrder.append("Thanks for shopping at the Ye Olde Book Shoppe!");


    }

    public String getFinishOrder() {
        return this.finishOrder.toString();
    }


    public int getcurNumItems() {
        return curNumItems;
    }
    public void setcurNumItems(int curNumItems) {
        this.curNumItems = this.curNumItems + curNumItems;
    }
    public double getsubtotal() {
        return subtotal;
    }
    public void setsubtotal(int quantity, double bookPrice) {
        this.subtotal = this.subtotal + this.getdiscount(quantity, bookPrice);
    }
    public int getTotalItems() {
        return totalItems;
    }
    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
    public int getmaxItems() {
        return maxItems;
    }
    public void setmaxItems(int maxItems) {
        this.maxItems = maxItems;
    }
    public double gettotal() {
        return total;
    }

    public void settotal() {
        this.total = this.subtotal + (.06 * this.subtotal);
    }

}