package com.radiatus.instaFram;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class FlickrImageCompareCallback extends DiffUtil.ItemCallback<FlickrImage> {

    @Override
    public boolean areItemsTheSame(@NonNull FlickrImage oldItem, @NonNull FlickrImage newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull FlickrImage oldItem, @NonNull FlickrImage newItem) {
        return oldItem.getIsLike() == newItem.getIsLike();
    }
}
