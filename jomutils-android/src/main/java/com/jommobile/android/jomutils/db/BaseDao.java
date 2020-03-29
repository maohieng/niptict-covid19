package com.jommobile.android.jomutils.db;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

public interface BaseDao<T> {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReplaceAll(List<T> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertReplace(T entity);

    @Delete
    void deleteAll(List<T> entities);

    @Delete
    void delete(T entity);

}
