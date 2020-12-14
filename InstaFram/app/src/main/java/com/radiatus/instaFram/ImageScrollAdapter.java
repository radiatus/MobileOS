package com.radiatus.instaFram;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.radiatus.instaFram.databinding.ImageViewBinding;

public class ImageScrollAdapter extends PagedListAdapter<FlickrPhoto, ImageScrollAdapter.ImageViewHolder> {

    private final FlickrViewModel model;

    public ImageScrollAdapter(FlickrViewModel model, DiffUtil.ItemCallback<FlickrPhoto> diffUtilCallback) {
        super(diffUtilCallback);
        this.model = model;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ImageViewBinding imageFlickerViewBinding = ImageViewBinding.inflate(layoutInflater, parent, false);
        return new ImageViewHolder(imageFlickerViewBinding, model);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        FlickrPhoto photo = new FlickrPhoto(getItem(position));
        Log.w("itemCount", String.valueOf(getItemCount()));

        holder.bind(photo);
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        private final ImageViewBinding imageFlickerViewBinding;
        private final FlickrViewModel model;
        private FlickrPhoto photo;

        private void bind(FlickrPhoto photo){
            this.photo = photo;
            imageFlickerViewBinding.setFlickrPhoto(photo);
        }

        public ImageViewHolder(ImageViewBinding imageFlickerViewBinding, FlickrViewModel model) {
            super(imageFlickerViewBinding.getRoot());

            this.model = model;

            this.imageFlickerViewBinding = imageFlickerViewBinding;
            imageFlickerViewBinding.setLikeClick(this::likeClickListener);
        }

        private void likeClickListener(View v)
        {
            photo.clickLike();
            model.updateOrInsert(photo);
        }
    }
}