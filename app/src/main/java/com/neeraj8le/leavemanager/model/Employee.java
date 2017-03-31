package com.neeraj8le.leavemanager.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Employee implements Parcelable{

    private long id;
    private String name;
    private String departmentName;
    private String designation;
    private String phoneNumber;
    private String email;
    private long supervisorId;

    public Employee(long id, String name, String departmentName, String designation, String phoneNumber, String email, long supervisorId) {
        this.id = id;
        this.name = name;
        this.departmentName = departmentName;
        this.designation = designation;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.supervisorId = supervisorId;
    }

    protected Employee(Parcel in) {
        id = in.readLong();
        name = in.readString();
        departmentName = in.readString();
        designation = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
        supervisorId = in.readLong();
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(long supervisorId) {
        this.supervisorId = supervisorId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(departmentName);
        dest.writeString(designation);
        dest.writeString(phoneNumber);
        dest.writeString(email);
        dest.writeLong(supervisorId);
    }
}