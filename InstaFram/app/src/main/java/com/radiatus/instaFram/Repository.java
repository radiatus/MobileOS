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
    private final LiveData< PagedList<FlickrImage>> pagedListLiveData;
    private final FlickrImageDataSourceFactory dataSourceFactory;

    public Repository(@NonNull Application application)
    {
        dataBase = Room.databaseBuilder(application,
                LocalDataBase.class, "database")
                .fallbackToDestructiveMigrationFrom(2)
                .build();

        FlickrDataSource dao = new FlickrDataSource(dataBase);
        dao.setSearchTile("Voronezh");

        dataSourceFactory = new FlickrImageDataSourceFactory(dao);

        PagedList.Config config = new PagedList.Config
                .Builder()
                .setMaxSize(200)
                .setEnablePlaceholders(false)
                .setPageSize(10)
                .build();

        pagedListLiveData = new LivePagedListBuilder<>(dataSourceFactory, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build();
    }

    public LiveData<PagedList<FlickrImage>> getPagedListLiveData() {
        return pagedListLiveData;
    }

    public void updatePageList() {
        dataSourceFactory.invalidate();
    }

    public void insert(FlickrImage flickrImage) {
        Runnable task = () -> {
            dataBase.flickrPhotoDAO().insert(flickrImage);
            updatePageList();
        };
        startTread(task);
    }

    public void update(FlickrImage flickrImage)  {
        Runnable task = () -> {
            dataBase.flickrPhotoDAO().update(flickrImage);
            updatePageList();
        };
        startTread(task);
    }


    public void updateOrInsert(FlickrImage flickrImage)
    {
        Runnable task = () -> {
            if(dataBase.flickrPhotoDAO().getByID(flickrImage.getId()) == null)
                dataBase.flickrPhotoDAO().insert(flickrImage);
            else
                dataBase.flickrPhotoDAO().update(flickrImage);
            updatePageList();
        };
        startTread(task);
    }

    private void startTread(Runnable run)
    {
        new Thread(run).start();
    }
}
