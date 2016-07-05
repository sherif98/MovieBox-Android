package wewo.com.moviebox;

import java.util.List;

public class RequestMovieList /*implements Parcelable*/ {
    public List<Movie> results;

//    public RequestMovieList(Parcel in) {
//        results = in.readParcelable(RequestMovieList.class.getClassLoader());
//    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//
//    }
//
//    public static final Creator CREATOR = new Creator() {
//        @Override
//        public Object createFromParcel(Parcel parcel) {
//            return new RequestMovieList(parcel);
//        }
//
//        @Override
//        public Object[] newArray(int i) {
//            return new Object[i];
//        }
//    };
}
