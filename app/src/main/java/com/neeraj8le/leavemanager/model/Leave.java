package com.neeraj8le.leavemanager.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Leave implements Parcelable {

    private long id;
    private String employee;
    private String supervisor;
    private String leaveType;
    private String leaveReason;
    private String fromDate;
    private String toDate;
    private long leaveStatus; // 0 --> on hold, 1 --> accepted, 2 --> rejected
    private String applicationDate;
    private String token;

    public Leave(){}

    public Leave(String employee, String supervisor, String leaveType, String leaveReason, String fromDate, String toDate, long leaveStatus, String applicationDate, long id, String token) {
        this.employee = employee;
        this.supervisor = supervisor;
        this.leaveType = leaveType;
        this.leaveReason = leaveReason;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.leaveStatus = leaveStatus;
        this.applicationDate = applicationDate;
        this.id = id;
        this.token = token;
    }


    protected Leave(Parcel in) {
        employee = in.readString();
        supervisor = in.readString();
        leaveType = in.readString();
        leaveReason = in.readString();
        fromDate = in.readString();
        toDate = in.readString();
        leaveStatus = in.readLong();
        applicationDate = in.readString();
        id = in.readLong();
        token = in.readString();
    }

    public static final Creator<Leave> CREATOR = new Creator<Leave>() {
        @Override
        public Leave createFromParcel(Parcel in) {
            return new Leave(in);
        }

        @Override
        public Leave[] newArray(int size) {
            return new Leave[size];
        }
    };

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employeeId) {
        this.employee = employeeId;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public long getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(long leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(employee);
        dest.writeString(supervisor);
        dest.writeString(leaveType);
        dest.writeString(leaveReason);
        dest.writeString(fromDate);
        dest.writeString(toDate);
        dest.writeLong(leaveStatus);
        dest.writeString(applicationDate);
        dest.writeLong(id);
        dest.writeString(token);
    }
}
