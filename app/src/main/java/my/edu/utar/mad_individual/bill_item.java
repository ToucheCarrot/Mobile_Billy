package my.edu.utar.mad_individual;

import android.os.Parcel;
import android.os.Parcelable;

public class bill_item implements Parcelable {

    private String itemName;
    private double itemPrice;

    public bill_item(String itemName, double itemPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public static final Creator<bill_item> CREATOR = new Creator<bill_item>() {
        @Override
        public bill_item createFromParcel(Parcel in) {
            String item_Name = in.readString();
            double item_Price = in.readDouble();
            return new bill_item(item_Name, item_Price);
        }

        @Override
        public bill_item[] newArray(int size) {
            return new bill_item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeDouble(itemPrice);
    }

}
