package com.mobdev.hellothreads.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mobdev.hellothreads.model.LogDescriptor;

/**
 * Created by Marco Picone (picone.m@gmail.com) 20/03/2020
 * Room DAO for Log Data Structure
 */
@Database(entities = {LogDescriptor.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LogDao logDao();
}