package com.radiatus.instaFram;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

public class FlickrViewModel extends AndroidViewModel {
    private Repository repository;

    public FlickrViewModel(@NonNull Application application)
    {
        super(application);
        repository = new Repository(application);
    }

    public void update(FlickrImage flickrImage) {
        repository.update(flickrImage);
    }

    public void updateOrInsert(FlickrImage flickrImage)
    {
        repository.updateOrInsert(flickrImage);
    }

    public LiveData<PagedList<FlickrImage>> getPagedListLiveData() {
        return repository.getPagedListLiveData();
    }

    public void insert(FlickrImage flickrImage) {
        repository.insert(flickrImage);
    }
}