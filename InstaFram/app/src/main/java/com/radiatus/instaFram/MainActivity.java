package com.radiatus.instaFram;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.radiatus.instaFram.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private FlickrViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flicr_lenta_activity);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.setSearchClick(this::onClickSearch);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle arguments = data.getExtras();

        if(arguments == null)
            return;

        viewModel.updateOrInsert(arguments.getParcelable("photo"));
    }

    public void onClickSearch(View v) {
        Intent intent = new Intent(MainActivity.this, FlickrLentaActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:51.660608, 39.201574"));
        startActivity(intent);
    }
}