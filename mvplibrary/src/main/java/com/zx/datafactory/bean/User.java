package com.zx.datafactory.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Name: User
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-12 14:02
 */
public class User implements Parcelable {
    private String userName;
    private String phoneNum;
    private String psw;

    private String timestamp;
    private String number;
    private String signature;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public User(String userName, String psw) {
        this.userName = userName;
        this.psw = psw;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.phoneNum);
        dest.writeString(this.psw);
        dest.writeString(this.timestamp);
        dest.writeString(this.number);
        dest.writeString(this.signature);
    }

    protected User(Parcel in) {
        this.userName = in.readString();
        this.phoneNum = in.readString();
        this.psw = in.readString();
        this.timestamp = in.readString();
        this.number = in.readString();
        this.signature = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
