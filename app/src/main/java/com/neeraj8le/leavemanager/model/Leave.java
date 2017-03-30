package com.neeraj8le.leavemanager.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Leave implements Parcelable {

    private long employeeId;
    private long supervisorId;
    private String leaveType;
    private String leaveReason;
    private String fromDate;
    private String toDate;
    private long leaveStatus; // 0 --> on hold, 1 --> accepted, 2 --> rejected
    private String applicationDate;

    public Leave(long employeeId, long supervisorId, String leaveType, String leaveReason, String fromDate, String toDate, long leaveStatus, String applicationDate) {
        this.employeeId = employeeId;
        this.supervisorId = supervisorId;
        this.leaveType = leaveType;
        this.leaveReason = leaveReason;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.leaveStatus = leaveStatus;
        this.applicationDate = applicationDate;
    }

    protected Leave(Parcel in) {
        employeeId = in.readLong();
        supervisorId = in.readLong();
        leaveType = in.readString();
        leaveReason = in.readString();
        fromDate = in.readString();
        toDate = in.readString();
        leaveStatus = in.readLong();
        applicationDate = in.readString();
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

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(long supervisorId) {
        this.supervisorId = supervisorId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(employeeId);
        dest.writeLong(supervisorId);
        dest.writeString(leaveType);
        dest.writeString(leaveReason);
        dest.writeString(fromDate);
        dest.writeString(toDate);
        dest.writeLong(leaveStatus);
        dest.writeString(applicationDate);
    }
}
