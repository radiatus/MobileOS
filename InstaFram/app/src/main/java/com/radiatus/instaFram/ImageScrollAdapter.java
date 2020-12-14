package com.radiatus.instaFram;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.radiatus.instaFram.databinding.ImageViewBinding;

public class ImageScrollAdapter extends PagedListAdapter<FlickrImage, ImageScrollAdapter.ImageViewHolder> {

    private final FlickrViewModel model;

    public ImageScrollAdapter(FlickrViewModel model, DiffUtil.ItemCallback<FlickrImage> diffUtilCallback) {
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
        FlickrImage photo = new FlickrImage(getItem(position));

        holder.bind(photo);
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        private final ImageViewBinding imageFlickerViewBinding;
        private final FlickrViewModel model;
        private FlickrImage photo;

        private void bind(FlickrImage image){
            this.photo = image;
            imageFlickerViewBinding.setFlickrImage(image);
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