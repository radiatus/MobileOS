package com.radiatus.instaFram;

import android.util.Log;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.Extras;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.SearchParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.flickr4java.flickr.photos.SearchParameters.RELEVANCE;

public class FlickrDataSource {

    private final FlickrImageDAO db;
    private final PhotosInterface photos;
    private final SearchParameters params;
    private List<Photo> pageLoaded;
    private int pageForLoading;
    private int itemCountLoad = 0;

    public FlickrDataSource(LocalDataBase db) {
        this.db = db.flickrPhotoDAO();
        String apiKey = "1a5b083247293a901503309f2b120dcd";
        String sharedSecret = "4ecc5f2f994e135a";

        Flickr flickr = new Flickr(apiKey, sharedSecret, new REST());
        pageForLoading = -1;
        photos = flickr.getPhotosInterface();
        params = new SearchParameters();

        try {
            params.setMedia("photos");
        } catch (FlickrException e) {
            e.printStackTrace();
        }

        params.setSort(RELEVANCE);
        params.setRadius(10);
        params.setAccuracy(Flickr.ACCURACY_REGION);
        params.setExtras(Extras.MIN_EXTRAS);
        pageLoaded = new ArrayList<>();
    }

    public void setSearchTile(String tile) {
        params.setText(tile);
    }

    public void setGeo(String latitude, String longitude){
        params.setLatitude(latitude);
        params.setLongitude(longitude);
    }

    private Photo getPhotoByFlicker(int index) {
        try {
            int pageSize = 50;
            int needPage = index / pageSize;
            if (needPage != pageForLoading) {
                pageForLoading = needPage;
                pageLoaded = photos.search(params, pageSize, pageForLoading + 1);
                itemCountLoad = pageLoaded.size();
            }

            Log.w("FlData", "Data size: " + String.valueOf(pageLoaded.size()));
            Log.w("FlData", "page : " + String.valueOf(pageForLoading + 1));
            Log.w("FlData", "index:" + String.valueOf(index - needPage * pageSize));

            int needIndex = index - needPage * pageSize;

            if (needIndex < itemCountLoad)
                return pageLoaded.get(needIndex);

            return pageLoaded.get(itemCountLoad - 1);

        } catch (FlickrException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<FlickrImage> getDataByPage(int startPosition, int itemCount) {
        List<FlickrImage> flickrImages = new ArrayList<>();

        for (int iItem = 0; iItem < itemCount; iItem++) {
            Photo photo = getPhotoByFlicker(startPosition + iItem);
            assert photo != null;
            long id = Long.parseLong(photo.getId());

            FlickrImage flickrImage = Optional.ofNullable(db.getByID(id)).orElse(new FlickrImage(photo.getLargeUrl(), id, 0));

            flickrImage.setImageUrl(photo.getLargeUrl());
            flickrImages.add(flickrImage);
        }
        return flickrImages;
    }
}