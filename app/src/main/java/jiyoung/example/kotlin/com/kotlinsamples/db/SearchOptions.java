package jiyoung.example.kotlin.com.kotlinsamples.db;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 검색 옵션 정보를 담고있는 클래스 <br><br>
 *
 * Created by jongsic.kim on 2017-04-17.
 */
public class SearchOptions implements Parcelable {

    public String searchKey;
    // VOD search options
    public int searchCategory;
    public String selectedTooth;
    public String vodWriter;

    public SearchOptions() {
        searchKey = selectedTooth = vodWriter = "";
        searchCategory = 0;
    }

    protected SearchOptions(Parcel in) {
        searchKey = in.readString();
        searchCategory = in.readInt();
        selectedTooth = in.readString();
        vodWriter = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(searchKey);
        dest.writeInt(searchCategory);
        dest.writeString(selectedTooth);
        dest.writeString(vodWriter);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchOptions> CREATOR = new Creator<SearchOptions>() {
        @Override
        public SearchOptions createFromParcel(Parcel in) {
            return new SearchOptions(in);
        }

        @Override
        public SearchOptions[] newArray(int size) {
            return new SearchOptions[size];
        }
    };

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("[SearchKey : " + searchKey + ", searchCategory : " + searchCategory + ", selectedTooth : " + selectedTooth + ", vodWriter : " + vodWriter + "]");

        return buf.toString();
    }
}
