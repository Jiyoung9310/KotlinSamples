package jiyoung.example.kotlin.com.kotlinsamples.db;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiyoung on 2017-06-22.
 */

public class SearchKeyItem implements Parcelable {

    private int key;
    private CharSequence text;
    private int viewType;

    public SearchKeyItem() {
    }

    public SearchKeyItem(int key, CharSequence text) {
        this.key = key;
        this.text = text;
    }

    public SearchKeyItem(int key, CharSequence text, int viewType) {
        this.key = key;
        this.text = text;
        this.viewType = viewType;
    }

    public SearchKeyItem(Parcel in) {
        this.key = in.readInt();
        this.text = in.readParcelable(CharSequence.class.getClassLoader());
        this.viewType = in.readInt();
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public CharSequence getText() {
        return text;
    }

    public void setText(CharSequence text) {
        this.text = text;
    }


    public static final Creator<SearchKeyItem> CREATOR = new Creator<SearchKeyItem>() {
        @Override
        public SearchKeyItem createFromParcel(Parcel in) {
            return new SearchKeyItem(in);
        }

        @Override
        public SearchKeyItem[] newArray(int size) {
            return new SearchKeyItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
