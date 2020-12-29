package com.radiatus.instaFram;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.radiatus.instaFram.databinding.FlicrLentaActivityBinding;


public class FlickrLentaActivity extends AppCompatActivity {
    private FlickrViewModel viewModel;

    protected void init() {
        viewModel = ViewModelProviders.of(this).get(FlickrViewModel.class);
        String latitube = getIntent().getData().getSchemeSpecificPart().split(",")[0];
        String longitube = getIntent().getData().getSchemeSpecificPart().split(",")[1];
        viewModel.initRepository(latitube, longitube);

        ImageScrollAdapter imageScrollAdapter = new ImageScrollAdapter(viewModel, new FlickrImageCompareCallback());


        viewModel.getPagedListLiveData().observe(this, flickrPhotos -> {
                    imageScrollAdapter.submitList(flickrPhotos);
                }
        );

        FlicrLentaActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.flicr_lenta_activity);

        binding.imageScrollView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL));
        binding.imageScrollView.setAdapter(imageScrollAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flicr_lenta_activity);

        init();
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle arguments = data.getExtras();

        if(arguments == null)
            return;

        viewModel.updateOrInsert(arguments.getParcelable("photo"));
    }
}