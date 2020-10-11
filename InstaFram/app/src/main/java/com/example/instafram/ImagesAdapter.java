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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>{

    private int numberItems;
    PhotoList<Photo> photos;

    public ImagesAdapter(int numberOfItems) {
        numberItems = numberOfItems;

        Callable task = () -> loadData(numberOfItems, 1);
        FutureTask<PhotoList<Photo>> future = new FutureTask<>(task);
        new Thread(future).start();

        try {
            photos = future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.e("url" , "Govno1");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e("url" , "Govno2");
        }
    }

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
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
    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    private PhotoList<Photo> loadData(int positions, int page) {
        try {
            String apiKey = "1a5b083247293a901503309f2b120dcd";
            String sharedSecret = "4ecc5f2f994e135a";
            REST rest = new REST();
            Flickr flickrClient = new Flickr(apiKey, sharedSecret, rest);


            PhotoList<Photo> photos;
            SearchParameters searchParameters = new SearchParameters();
            searchParameters.setMedia("photos"); // One of "photos", "videos" or "all"
            searchParameters.setPrivacyFilter(1);
            searchParameters.setLatitude("51.656091");
            searchParameters.setLongitude("39.206136");
            searchParameters.setRadius(10); // Km around the given location where to search pictures
            searchParameters.setSort(SearchParameters.RELEVANCE);
            searchParameters.setAccuracy(Flickr.ACCURACY_REGION);

            photos = flickrClient.getPhotosInterface().searchInterestingness(searchParameters, positions, page);

            for (int i = 0; i < photos.size(); ++i) {
                Photo photo = photos.get(i);

                Log.i("url", String.format("Title: %s", photo.getTitle()));
                Log.i("url", String.format("Media: %s", photo.getMedia()));
                Log.i("url", String.format("Original Small URL: %s", photo.getSmallUrl()));
                Log.i("url", String.format("Original Small URL: %s", photo.getLargeUrl()));
            }

            return photos;

        } catch (FlickrException e) {
            e.printStackTrace();
            Log.e("url" , "Govno");
            return null;
        }
    }

    class ImagesViewHolder extends RecyclerView.ViewHolder {

        ImageView listUrlsView;

        public ImagesViewHolder(@NonNull View itemView) {
            super(itemView);

            listUrlsView = itemView.findViewById(R.id.imageUrl);
        }

        void bind(String urlImage){
            Picasso.get().load(urlImage).resize(1000,1000).centerInside().into(listUrlsView);
        }
    }
}
