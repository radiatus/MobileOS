package com.radiatus.instaFram;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {FlickrImage.class}, version = 2)
public abstract class LocalDataBase extends RoomDatabase {
    public abstract FlickrImageDAO flickrPhotoDAO();

}
