package my.edu.utar.mad_individual;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    private String name;
    private int percentage;
    private double amount;

    public Person(String name, int percentage, double amount) {
        this.name = name;
        this.percentage = percentage;
        this.amount = amount;
    }

    protected Person(Parcel in) {
        name = in.readString();
        percentage = in.readInt();
        amount = in.readDouble();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getPercentage() {
        return percentage;
    }

    public double getAmount() { return amount; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(percentage);
        dest.writeDouble(amount);
    }
}

