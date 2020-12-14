package com.radiatus.instaFram;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FlickrImageDAO {
    @Query("select * from FlickrImage")
    List<FlickrImage> getAll();

    @Query("select * from FlickrImage where id =:id")
    FlickrImage getByID(long id);

    @Insert
    void insert(FlickrImage flickrImage);

    @Update
    void update(FlickrImage flickrImage);

    @Delete
    void delete(FlickrImage flickrImage);
}
