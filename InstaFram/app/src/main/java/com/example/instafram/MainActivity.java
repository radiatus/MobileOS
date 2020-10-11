package com.example.instafram;

import android.os.Bundle;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.SearchParameters;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlList = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        urlList.setLayoutManager(layoutManager);
        urlList.setHasFixedSize(true);


//        Thread t = new Thread(() -> loadData(20, 1));
//        t.start();

        imagesAdapter = new ImagesAdapter(20);
        urlList.setAdapter(imagesAdapter);

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