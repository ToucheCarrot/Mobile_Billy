package my.edu.utar.mad_individual;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.HistoryViewHolder> {

    private List<my.edu.utar.mad_individual.HistoryItem> historyItems;


    public SliderAdapter(List<my.edu.utar.mad_individual.HistoryItem> historyItems)
    {
        this.historyItems = historyItems;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_history_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        my.edu.utar.mad_individual.HistoryItem item = historyItems.get(position);
        String text = "Total Amount: RM " + item.getTotalAmount() + "\n"
                + "People: " + item.getNumOfPeople() + "\n";

        if (item.getNameItems() != null) {
            for (Name name : item.getNameItems()) {
                text += "\n" + name.getItemName() + ": RM" + name.getItemPrice();
            }
        }

        if (item.getPerson() != null) {
            for (Person person : item.getPerson()) {
                text += "\n" + person.getName() + " (" + person.getPercentage() + "%) : RM" + person.getAmount();
            }
        }

        if (item.getBillItems() != null) {
            for (bill_item items : item.getBillItems()) {
                text += "\n" + items.getItemName() + ": RM" + items.getItemPrice();
            }
        }

        text += "\n\nDate: " + item.getDate();
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_item);
        }
    }

    public class HistoryItem {
        private double totalAmount;
        private int numOfPeople;
        private ArrayList<Name> nameItems;
        private ArrayList<Person> person;
        private ArrayList<bill_item> billItems;
        private String date;

        public HistoryItem(double totalAmount, int numOfPeople, ArrayList<Name> nameItems ,ArrayList<Person> person, ArrayList<bill_item> billItems, String date) {
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
}



