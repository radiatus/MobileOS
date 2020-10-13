package com.example.instafram;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.SearchParameters;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>{

    PhotoList<Photo> photos;
    Context context;

    public ImagesAdapter(PhotoList<Photo> photosData) {
        photos = photosData;
    }

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.image_layout;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        ImagesViewHolder viewHolder = new ImagesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder holder, int position) {

//        holder.bind(photos.get(position).getSmallUrl());
        holder.bind(photos.get(position).getLargeUrl());
        Log.i("url", String.format("Photos in adapter: %s", photos.size()));
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    class ImagesViewHolder extends RecyclerView.ViewHolder {

        ImageView listUrlsView;
        TextView text;

        public ImagesViewHolder(@NonNull View itemView) {
            super(itemView);

            listUrlsView = itemView.findViewById(R.id.imageUrl);
            text = itemView.findViewById(R.id.likeImage);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int positionIndex = getAdapterPosition();

                    text.setText("text");
                    return true;
                }
            });
        }

        void bind(String urlImage){
            Picasso.get().load(urlImage).resize(900,900).centerInside().into(listUrlsView);
            text.setText(String.valueOf(0));
        }
    }
}
