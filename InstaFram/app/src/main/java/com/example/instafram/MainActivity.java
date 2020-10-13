package com.example.instafram;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.SearchParameters;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MainActivity extends AppCompatActivity {

//    Thread thread = new Thread(new Runnable() {
//        @Override
//        public void run() {
//
//        }
//    });


    // Store a member variable for the listener
//    private EndlessRecyclerViewScrollListener scrollListener;

    public void loadNextDataFromApi(int offset) {

//        Thread t = new Thread(() -> loadData(1));
//        t.start();

        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }

    private RecyclerView urlList;
    private ImagesAdapter imagesAdapter;
    private LinearLayoutManager layoutManager;
    private Boolean isScrolling = false;
    private int currentItems, totalItems, scrollOutItems;
    private PhotoList<Photo> photos;
    private int pageOfImage = 1;
    private ProgressBar progressBar;
    private FusedLocationProviderClient fusedLocationClient;
    String lar;
    String lon;

    @SuppressLint("MissingPermission")
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
                lar = "51.656091";
                lon = "39.206136";

            fusedLocationClient = LocationServices
                    .getFusedLocationProviderClient(this);

//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                lar = "51.656091";
//                lon = "39.206136";
//            }
//            fusedLocationClient.getLastLocation()
//                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            // Got last known location. In some rare situations this can be null.
//                            if (location != null) {
//                                lar = String.valueOf(location.getLatitude());
//                                lon = String.valueOf(location.getLongitude());
//                            }
//                        }
//                    });

//            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            double longitude = location.getLongitude();
//            double latitude = location.getLatitude();

            searchParameters.setLatitude(lar);
            searchParameters.setLongitude(lon);
            Log.i("location", String.format("lar: %s", lar));
            Log.i("location", String.format("lon: %s", lon));
            searchParameters.setRadius(3); // Km around the given location where to search pictures
            searchParameters.setSort(SearchParameters.RELEVANCE);
            searchParameters.setAccuracy(Flickr.ACCURACY_REGION);

//            photos = flickrClient.getPhotosInterface().searchInterestingness(searchParameters, positions, page);
            photos = flickrClient.getPhotosInterface().search(searchParameters, positions, page);

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlList = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        urlList.setLayoutManager(layoutManager);
        urlList.setHasFixedSize(true);


//        Thread t = new Thread(() -> loadData(20, 1));
//        t.start();

        Callable task = () -> loadData(100, pageOfImage);
        pageOfImage = pageOfImage + 1;
        FutureTask<PhotoList<Photo>> future = new FutureTask<>(task);
        new Thread(future).start();

        try {
            photos = future.get();
            imagesAdapter = new ImagesAdapter(photos);
            urlList.setAdapter(imagesAdapter);
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.e("url" , "Govno1");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e("url" , "Govno2");
        }

        urlList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();
                scrollOutItems = layoutManager.findFirstVisibleItemPosition();
                Log.i("url", String.format("Photos in currentItems: %s", currentItems));
                Log.i("url", String.format("Photos in totalItems: %s", totalItems));
                Log.i("url", String.format("Photos in scrollOutItems: %s", scrollOutItems));


                if (isScrolling && (currentItems + scrollOutItems == totalItems)){
                    isScrolling = false;
//                    Thread t = new Thread(() -> fetchData());
//                    t.start();
                    fetchData();
                    Log.i("url", String.format("Photos in activity: %s", photos.size()));
                    imagesAdapter.notifyDataSetChanged();
                }
            }
        });
//        RecyclerView rvItems = (RecyclerView) findViewById(R.id.recyclerview);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        rvItems.setLayoutManager(linearLayoutManager);
        // Retain an instance so that you can call `resetState()` for fresh searches
//        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                // Triggered only when new data needs to be appended to the list
//                // Add whatever code is needed to append new items to the bottom of the list
//                loadNextDataFromApi(page);
//            }
//        };
        // Adds the scroll listener to RecyclerView
//        rvItems.addOnScrollListener(scrollListener);

//        // Lookup the recyclerview in activity layout
//        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
//
//        // Initialize contacts
//        contacts = Contact.createContactsList(20);
//        // Create adapter passing in the sample user data
//        ContactsAdapter adapter = new ContactsAdapter(contacts);
//        // Attach the adapter to the recyclerview to populate items
//        rvContacts.setAdapter(adapter);
//        // Set layout manager to position the items
//        rvContacts.setLayoutManager(new LinearLayoutManager(this));
//        // That's all!



//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//        Thread t = new Thread(() -> loadData(1));
//        t.start();
    }

    private void fetchData() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                PhotoList<Photo> newPhoto = loadData(10, pageOfImage);
//                pageOfImage = pageOfImage + 1;
//                for (int i = 0; i < newPhoto.size(); ++i){
//                    photos.add(newPhoto.get(i));
//                    imagesAdapter.notifyDataSetChanged();
////                progressBar.setVisibility(View.GONE);
//                }
//            }
//        }, 2000);
//        progressBar.setVisibility(View.VISIBLE);

        Callable task = () -> loadData(100, pageOfImage);
        pageOfImage = pageOfImage + 1;
        FutureTask<PhotoList<Photo>> future = new FutureTask<>(task);
        new Thread(future).start();

        try {
            PhotoList<Photo> newPhoto = future.get();
            for (int i = 0; i < newPhoto.size(); ++i){
                photos.add(newPhoto.get(i));
//                imagesAdapter.notifyDataSetChanged();
//                progressBar.setVisibility(View.GONE);
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.e("url" , "Govno1");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e("url" , "Govno2");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}