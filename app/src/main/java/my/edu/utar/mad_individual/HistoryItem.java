package my.edu.utar.mad_individual;

import java.util.ArrayList;

public class HistoryItem {
    private double totalAmount;
    private int numOfPeople;
    private ArrayList<Name> nameItems;
    private ArrayList<Person> person;
    private ArrayList<bill_item> billItems;
    private String date;

    public HistoryItem(double totalAmount, int numOfPeople, ArrayList<Name> nameItems,
                       ArrayList<Person> person, ArrayList<bill_item> billItems, String date)
    {
        this.totalAmount = totalAmount;
        this.numOfPeople = numOfPeople;
        this.nameItems = nameItems;
        this.person = person;
        this.billItems = billItems;
        this.date = date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public ArrayList<Name> getNameItems() { return nameItems; }

    public ArrayList<Person> getPerson() { return person; }

    public ArrayList<bill_item> getBillItems() { return billItems; }

    public String getDate() {
        return date;
    }
}

