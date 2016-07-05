package wewo.com.moviebox;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieDatabaseAPI {
    String END_CALL = "http://api.themoviedb.org/";
    String API_KEY = "your movie database api key";

    enum RequestType {
        NOW_PLAYING, UPCOMING, TOP_RATED, POPULAR
    }

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(END_CALL)
            .build();


    @GET("/3/movie/{list}?api_key=" + API_KEY)
    Call<RequestMovieList> getData(@Path("list") String list);

    @GET("/3/movie/{id}?api_key=" + API_KEY)
    Call<DetailMovie> getMovieData(@Path("id") String id);

    @GET("/3/movie/{id}/videos?api_key=" + API_KEY)
    Call<TrailerModel> getTrailers(@Path("id") String id);

}

class TrailerModel {
    public List<Trailer> results;
}
