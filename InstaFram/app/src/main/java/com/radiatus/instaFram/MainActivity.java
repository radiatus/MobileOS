package com.radiatus.instaFram;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.radiatus.instaFram.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private FlickrViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(FlickrViewModel.class);

        ImageScrollAdapter imageScrollAdapter = new ImageScrollAdapter(viewModel, new FlickrPhotoCompareCallback());


        viewModel.getPagedListLiveData().observe(this, flickrPhotos -> {
                imageScrollAdapter.submitList(flickrPhotos);
            }
        );

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.imageScrollView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL));
        binding.imageScrollView.setAdapter(imageScrollAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle arguments = data.getExtras();

        if(arguments != null)
            viewModel.updateOrInsert(arguments.getParcelable("photo"));
    }
}