package com.radiatus.instaFram;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity
public class FlickrPhoto extends BaseObservable implements Parcelable {

    @PrimaryKey
    private long id;
    private int isLike;

    @Ignore
    private String imageUrl;

    public FlickrPhoto(long id, int isLike) {
        this.id = id;
        this.isLike = isLike;
    }

    public FlickrPhoto(String imageUrl, long id, int isLike) {
        this.imageUrl = imageUrl;
        this.isLike = isLike;
        this.id = id;
    }

    protected FlickrPhoto(Parcel in) {
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

    public static final Creator<FlickrPhoto> CREATOR = new Creator<FlickrPhoto>() {
        @Override
        public FlickrPhoto createFromParcel(Parcel in) {
            return new FlickrPhoto(in);
        }

        @Override
        public FlickrPhoto[] newArray(int size) {
            return new FlickrPhoto[size];
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
    
    public FlickrPhoto( FlickrPhoto other)
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
}
