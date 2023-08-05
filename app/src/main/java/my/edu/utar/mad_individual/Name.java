package my.edu.utar.mad_individual;

import android.os.Parcel;
import android.os.Parcelable;

public class Name implements Parcelable {

    private String itemName;
    private double itemPrice;

    public Name(String itemName, double itemPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public static final Creator<Name> CREATOR = new Creator<Name>() {
        @Override
        public Name createFromParcel(Parcel in) {
            String item_Name = in.readString();
            double item_Price = in.readDouble();
            return new Name(item_Name, item_Price);
        }

        @Override
        public Name[] newArray(int size) {
            return new Name[size];
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
