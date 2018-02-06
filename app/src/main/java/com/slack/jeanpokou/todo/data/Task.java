package com.slack.jeanpokou.todo.data;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;

import java.util.UUID;

@Entity(tableName = "tasks")
public class Task {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "entryId")
    private final String mId;

    @Nullable
    @ColumnInfo(name = "title")
    private final String mTitle;

    @Nullable
    @ColumnInfo(name = "description")
    private final String mDescription;

    @ColumnInfo(name = "completed")
    private boolean mCompleted;

    /**
     * @param id
     * @param title
     * @param description
     * @param completed
     */

    public Task(@NonNull String id, @Nullable String title, @Nullable String description, boolean completed) {

        this.mId = id;
        this.mTitle = title;
        this.mDescription = description;
        this.mCompleted = completed;
    }

    /**
     *
     * @param title
     * @param description
     * @param completed
     */
    @Ignore
    public Task(@Nullable String title, @Nullable String description, boolean completed) {
        this(UUID.randomUUID().toString(),title,description,completed);
    }

    /**
     *
     * @param title
     * @param description
     */
    @Ignore
    public Task(@Nullable String title, @Nullable String description) {
        this(UUID.randomUUID().toString(),title,description,false);
    }



    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    public boolean isCompleted() {
        return mCompleted;
    }


    @Override
    public String toString() {
        return "Task with title" + mTitle;
    }

    @Override
    public int hashCode() {
        return  Objects.hashCode(mId,mTitle,mDescription);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if(other == null || getClass() != other.getClass()) return false;
        Task otherTask = (Task) other;
        return Objects.equal(mId,otherTask)  &&
                Objects.equal(mTitle,otherTask) &&
                Objects.equal(mDescription,otherTask);
    }

}
