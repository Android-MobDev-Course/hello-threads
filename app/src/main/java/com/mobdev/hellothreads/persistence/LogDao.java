package com.mobdev.hellothreads.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mobdev.hellothreads.model.LogDescriptor;

import java.util.List;

/**
 * Created by Marco Picone (picone.m@gmail.com) 20/03/2020
 * Doom Dao Structure with available methods
 */
@Dao
public interface LogDao {

    @Query("SELECT * FROM logs ORDER BY id DESC")
    LiveData<List<LogDescriptor>> getAllLiveData();

    @Query("SELECT * FROM logs ORDER BY id DESC")
    List<LogDescriptor> getAll();

    @Query("SELECT * FROM logs WHERE id IN (:userIds)")
    List<LogDescriptor> loadAllByIds(int[] userIds);

    @Insert
    void insertAll(LogDescriptor... logs);

    @Delete
    void delete(LogDescriptor logDescriptor);
}