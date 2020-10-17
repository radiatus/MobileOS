package com.radiatus.instaFram;

import androidx.paging.DataSource;

public class FlickrPhotoDaraSourceFactory extends DataSource.Factory<Integer, FlickrPhoto> {

    private final FlickrDataSource flickrDataSource;
    private FlickrPositionDataSource flickrPositionDataSource;
    public FlickrPhotoDaraSourceFactory(FlickrDataSource flickrDataSource) {
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