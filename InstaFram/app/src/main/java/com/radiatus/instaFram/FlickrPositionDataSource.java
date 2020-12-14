package com.radiatus.instaFram;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import java.util.List;

public class FlickrPositionDataSource extends PositionalDataSource<FlickrImage> {
    private FlickrDataSource dao;

    FlickrPositionDataSource(FlickrDataSource dao)
    {
        this.dao = dao;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<FlickrImage> callback) {
        Log.d("loadData", "loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                ", requestedLoadSize = " + params.requestedLoadSize);

        List<FlickrImage> data = dao.getDataByPage(params.requestedStartPosition,  params.requestedLoadSize);
        callback.onResult(data, params.requestedStartPosition);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<FlickrImage> callback) {
        Log.d("loadData", "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);

        List<FlickrImage> data = dao.getDataByPage(params.startPosition, params.loadSize);
        callback.onResult(data);
    }
}
