package com.example.linton.foodhygiene;

/**
 * Created by linton on 17/02/2018.
 */

public class restaraunt {



    String businessName;
    String address1;
    String address2;
    String postcode;
    int hygRating;

    public restaraunt(String businessName, String address1, String address2, String postcode, int hygRating) {
        this.businessName = businessName;
        this.address1 = address1;
        this.address2 = address2;
        this.postcode = postcode;
        this.hygRating = hygRating;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String adress2) {
        this.address2 = adress2;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getHygRating() {
        return hygRating;
    }

    public void setHygRating(int hygRating) {
        this.hygRating = hygRating;
    }
}
