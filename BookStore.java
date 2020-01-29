/*
Name:  Barath Tirumala
Course: CNT 4714 – Spring 2020 
Assignment title: Project 1 – Event-driven Enterprise Simulation 
Date: Sunday January 26, 2020 
*/ 

import java.text.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class BookStore extends JFrame {

    String inventoryFile = "inventory.txt";
    ArrayList<Book> inventory;
    Order order = new Order();
    JButton processBut = new JButton("Process Item #1");// need to update item #
    JButton confirmBut = new JButton("Confirm Item #1");// need to update item #
    JButton viewBut = new JButton("View Order");
    JButton finishBut = new JButton("Finish Order");
    JButton newBut = new JButton("New Order");
    JButton quitBut = new JButton("Exit");
    JLabel numitems = new JLabel("Enter number of items in this order:");
    JLabel subtotallabel = new JLabel("Order subtotal for 0 item(s):");
    JLabel bIDlabel = new JLabel("Enter book ID for item #1:");
    JLabel quantitylabel = new JLabel("Enter quantitiy for item #1:");
    JLabel infolabel = new JLabel("Item #1 info:");

    JTextField numItemsText = new JTextField();
    JTextField bIDtext = new JTextField();
    JTextField numBtext = new JTextField();
    JTextField infotext = new JTextField();
    JTextField totaltext = new JTextField();
    private static DecimalFormat df = new DecimalFormat("0.00");
    public BookStore() throws FileNotFoundException
    {
        this.getInventoryFromFile();

        JPanel p1 = new JPanel(new GridLayout(5,2));
        p1.setBackground(Color.lightGray);
        p1.add(numitems);
        p1.add(numItemsText);
        p1.add(bIDlabel);
        p1.add(bIDtext);
        p1.add(quantitylabel);
        p1.add(numBtext);
        p1.add(infolabel);
        p1.add(infotext);
        p1.add(subtotallabel);
        p1.add(totaltext);
        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p2.setBackground(Color.black);
        p2.add(processBut);
        p2.add(confirmBut);
        p2.add(viewBut);
        p2.add(finishBut);
        p2.add(newBut);
        p2.add(quitBut);

        add(p1, BorderLayout.NORTH);
        add(p2, BorderLayout.SOUTH);
        this.confirmBut.setEnabled(false);
        this.viewBut.setEnabled(false);
        this.finishBut.setEnabled(false);
        this.totaltext.setEnabled(false);
        this.infotext.setEnabled(false);

        quitBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        finishBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    order.printTransactions();
                    JOptionPane.showMessageDialog(null, order.getFinishOrder());

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                BookStore.super.dispose();
            }
        });


        confirmBut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int numOfItemsInOrder = Integer.parseInt(numItemsText.getText());
                int bookID = Integer.parseInt(bIDtext.getText());
                int quantityOfItem = Integer.parseInt(numBtext.getText());

                if(numOfItemsInOrder > order.getmaxItems())
                    System.out.println("went over qantity");

                order.setcurNumItems(quantityOfItem);
                order.setTotalItems(order.getTotalItems() + 1);

                JOptionPane.showMessageDialog(null, "Item #" + order.getTotalItems() + " accepted");

                order.prepareTransaction();

                order.addToViewOrder(infotext.getText());

                processBut.setEnabled(true);
                viewBut.setEnabled(true);
                finishBut.setEnabled(true);
                confirmBut.setEnabled(false);
                numItemsText.setEnabled(false);

                processBut.setText("Process Item #" + (order.getTotalItems() + 1));
                confirmBut.setText("Confirm Item #" + (order.getTotalItems() + 1));

                bIDtext.setText("");
                numBtext.setText("");
                totaltext.setText("$" +  new DecimalFormat("#0.00").format(order.getsubtotal()));

                subtotallabel.setText("Order subtotal for " + order.getcurNumItems() + " item(s)");
                bIDlabel.setText("Enter Book ID for Item #" + (order.getTotalItems() + 1) + ":");
                quantitylabel.setText("Enter quantity for Item #" + (order.getTotalItems() + 1) + ":");
                if(order.getcurNumItems() < order.getmaxItems())
                    infolabel.setText("Item #" + (order.getTotalItems() + 1) + " info:");

                if(order.getcurNumItems() >= order.getmaxItems()) {
                    bIDlabel.setVisible(false);
                    quantitylabel.setVisible(false);
                    processBut.setEnabled(false);
                    confirmBut.setEnabled(false);
                }
            }
        });

        
        processBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int numOfItemsInOrder = Integer.parseInt(numItemsText.getText());
                int bookID = Integer.parseInt(bIDtext.getText());
                int quantityOfItem = Integer.parseInt(numBtext.getText());

                if(order.getmaxItems() == -1 && numOfItemsInOrder > 0) {
                    order.setmaxItems(numOfItemsInOrder);
                    numItemsText.setEnabled(false);
                }
                int bookIndex = search(bookID);
                if(bookIndex != -1)
                {

                    Book foundBook = inventory.get(bookIndex);
                    order.setItemInfo(foundBook.getbID() + "", foundBook.getTitle(), foundBook.getPrice() + "", quantityOfItem + "", order.getDiscountPercentage(quantityOfItem) + "", order.getdiscount(quantityOfItem, foundBook.getPrice()) + "");
                    String bookInfo = foundBook.getbID() + foundBook.getTitle() +  " $" + foundBook.getPrice() + " " + quantityOfItem + " " + order.getDiscountPercentage(quantityOfItem) + "% " + df.format(order.getdiscount(quantityOfItem, foundBook.getPrice()));
                    infotext.setText(bookInfo);
                    confirmBut.setEnabled(true);
                    processBut.setEnabled(false);
                    order.setsubtotal(quantityOfItem, foundBook.getPrice());
                    infotext.setEnabled(false);
                    totaltext.setEnabled(false);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Book ID " + bookID + " not in file.");
                }
            }
        });

        viewBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, order.getViewOrder());
            }
        });

        newBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookStore.super.dispose();
                try {
                    BookStore.main(null);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

    public int search(int BookID) {
        for(int i = 0; i < this.inventory.size(); i++) {
            Book currentBook = inventory.get(i);
            if(currentBook.getbID() == BookID)
                return i;
        }
        return -1;
    }

    public void getInventoryFromFile() throws FileNotFoundException {
        this.inventory = new ArrayList<Book>();
        File file = new File("inventory.txt");
        Scanner textFile = new Scanner(file);

        while (textFile.hasNextLine()) {
            String book = textFile.nextLine();
            String[] bookInfo = book.split(",");
            Book currentBook = new Book();
            currentBook.setbID(Integer.parseInt(bookInfo[0]));

            currentBook.setTitle(bookInfo[1]);

            currentBook.setPrice(Double.parseDouble(bookInfo[2]));
            inventory.add(currentBook);
        }

        textFile.close();
        for (int i = 0; i < inventory.size(); i++) {
            Book current = inventory.get(i);
            System.out.println(current.getbID() + ", " + current.getTitle() + ", " + current.getPrice());
        }
    }


    public ArrayList<Book> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Book> inventory) {
        this.inventory = inventory;
    }


    public static void main(String[] args) throws FileNotFoundException {
        BookStore frame = new BookStore();
        frame.pack(); 
        frame.setTitle("Book Store");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}