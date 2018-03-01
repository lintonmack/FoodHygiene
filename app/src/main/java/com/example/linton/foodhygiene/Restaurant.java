package com.example.linton.foodhygiene;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by linton on 21/02/2018.
 */

public class Restaurant implements Serializable{

//    public String id;
    public String businessName;
    String addressLine1;
    String addressLine2;
    String addressLine3;
    String postCode;
    String ratingValue;
    String ratingDate;
    String longitude;
    String latitude;
    String distanceKM;

    public Restaurant(String businessName, String addressLine1, String addressLine2, String addressLine3, String postCode, String ratingValue, String ratingDate, String longitude, String latitude, String distanceKM) {
//        this.id = id;
        this.businessName = businessName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.postCode = postCode;
        this.ratingValue = ratingValue;
        this.ratingDate = ratingDate;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distanceKM = distanceKM;
    }

    public Restaurant(String businessName, String addressLine1, String addressLine2, String addressLine3, String postCode, String ratingValue, String ratingDate, String longitude, String latitude) {
        this.businessName = businessName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.postCode = postCode;
        this.ratingValue = ratingValue;
        this.ratingDate = ratingDate;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getRatingValue() {

        return ratingValue;
    }

    public String getDistanceKM() {
        return distanceKM;
    }

    public void setDistanceKM(String distanceKM) {
        this.distanceKM = distanceKM;
    }


    public int getHygRatingImageDrawable(){
        int hygRating = R.drawable.exemptimg;

        if(ratingValue.equals("-1")){
            hygRating = R.drawable.exemptimg;
        }
        else if(ratingValue.equals("0")){
            hygRating = R.drawable.hygrating0;
        }
        else if(ratingValue.equals("1")){
            hygRating = R.drawable.hygrating1;
        }
        else if(ratingValue.equals("2")){
            hygRating = R.drawable.hygrating2;
        }
        else if(ratingValue.equals("3")){
            hygRating = R.drawable.hygrating3;
        }
        else if(ratingValue.equals("4")){
            hygRating = R.drawable.hygrating4;
        }
        else if(ratingValue.equals("5")){
            hygRating = R.drawable.hygrating5;
        }
        return hygRating;
    }


    public void setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(String ratingDate) {
        this.ratingDate = ratingDate;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "businessName='" + businessName + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", addressLine3='" + addressLine3 + '\'' +
                ", postCode='" + postCode + '\'' +
                ", ratingValue='" + ratingValue + '\'' +
                ", ratingDate='" + ratingDate + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
