package com.radiatus.instaFram;

import androidx.paging.DataSource;

public class FlickrImageDataSourceFactory extends DataSource.Factory<Integer, FlickrImage> {

    private final FlickrDataSource flickrDataSource;
    private FlickrPositionDataSource flickrPositionDataSource;

    public FlickrImageDataSourceFactory(FlickrDataSource flickrDataSource) {
        this.flickrDataSource = flickrDataSource;
        this.flickrPositionDataSource = new FlickrPositionDataSource(flickrDataSource);
    }

    public void invalidate()
    {
        flickrPositionDataSource.invalidate();
    }

    @Override
    public DataSource create() {
        flickrPositionDataSource =  new FlickrPositionDataSource(flickrDataSource);
        return flickrPositionDataSource;
    }
}