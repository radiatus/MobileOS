package com.radiatus.instaFram;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.squareup.picasso.Picasso;


@Entity
public class FlickrImage extends BaseObservable implements Parcelable {

    @PrimaryKey
    private long id;
    private int isLike;

    @Ignore
    private String imageUrl;

    public FlickrImage(long id, int isLike) {
        this.id = id;
        this.isLike = isLike;
    }

    public FlickrImage(String imageUrl, long id, int isLike) {
        this.imageUrl = imageUrl;
        this.isLike = isLike;
        this.id = id;
    }

    protected FlickrImage(Parcel in) {
        id = in.readLong();
        isLike = in.readInt();
        imageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(isLike);
        dest.writeString(imageUrl);
    }

    public static final Creator<FlickrImage> CREATOR = new Creator<FlickrImage>() {
        @Override
        public FlickrImage createFromParcel(Parcel in) {
            return new FlickrImage(in);
        }

        @Override
        public FlickrImage[] newArray(int size) {
            return new FlickrImage[size];
        }
    };

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    @Bindable
    public long getId() {
        return id;
    }

    @Bindable
    public int getIsLike() {
        return isLike;
    }
    
    public FlickrImage(FlickrImage other)
    {
        this(other.getImageUrl(), other.getId(), other.getIsLike());
    }
    
    public boolean hasLike() {
        return isLike > 0;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void clickLike() {
        isLike = (isLike == 1) ? 0 : 1;
        notifyPropertyChanged(BR._all);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
            Picasso.get()
                    .load(imageUrl)
                    .placeholder( R.drawable.ic_placeholder)
                    .into(view);
    }
}
