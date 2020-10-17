package com.radiatus.instaFram;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.room.Room;

import java.util.concurrent.Executors;

public class Repository {

    private final LocalDataBase dataBase;
    private final LiveData< PagedList<FlickrPhoto>> pagedListLiveData;
    private final FlickrPhotoDaraSourceFactory dataSourceFactory;

    public Repository(@NonNull Application application)
    {
        dataBase = Room.databaseBuilder(application,
                LocalDataBase.class, "database")
                .fallbackToDestructiveMigrationFrom(1) // BD version
                .build();

        FlickrDataSource dao = new FlickrDataSource(dataBase);
        dao.setSearchTile("Voronezh"); // Search Voronezh image

        dataSourceFactory = new FlickrPhotoDaraSourceFactory(dao);

        PagedList.Config config = new PagedList.Config
                .Builder()
                .setMaxSize(400)
                .setEnablePlaceholders(false)
                .setPageSize(20)
                .build();

        pagedListLiveData = new LivePagedListBuilder<>(dataSourceFactory, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor()) //Load data from another thread
                .build();
    }

    public LiveData<PagedList<FlickrPhoto>> getPagedListLiveData() {
        return pagedListLiveData;
    }

    public void updatePageList() {
        dataSourceFactory.invalidate();
    }

    public void insert(FlickrPhoto flickrPhoto) {
        Runnable task = () -> {
            dataBase.flickrPhotoDAO().insert(flickrPhoto);
            updatePageList();
        };
        startTread(task);
    }

    public void update(FlickrPhoto flickrPhoto)  {
        Runnable task = () -> {
            dataBase.flickrPhotoDAO().update(flickrPhoto);
            updatePageList();
        };
        startTread(task);
    }


    public void updateOrInsert(FlickrPhoto flickrPhoto)
    {
        Runnable task = () -> {
            if(dataBase.flickrPhotoDAO().getByID(flickrPhoto.getId()) == null)
                dataBase.flickrPhotoDAO().insert(flickrPhoto);
            else
                dataBase.flickrPhotoDAO().update(flickrPhoto);
            updatePageList();
        };
        startTread(task);
    }

    private void startTread(Runnable run)
    {
        new Thread(run).start();
    }
}
