package com.example.gav.taskmanager.features.tasklist;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class FinishTask implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int day;
    private int count;

    public FinishTask(int day, int count) {
        this.day = day;
        this.count = count;
    }

    protected FinishTask(Parcel in) {
        id = in.readInt();
        day = in.readInt();
        count = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(day);
        dest.writeInt(count);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FinishTask> CREATOR = new Creator<FinishTask>() {
        @Override
        public FinishTask createFromParcel(Parcel in) {
            return new FinishTask(in);
        }

        @Override
        public FinishTask[] newArray(int size) {
            return new FinishTask[size];
        }
    };

    public int getDay() {
        return day;
    }

    public int getCount() {
        return count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
