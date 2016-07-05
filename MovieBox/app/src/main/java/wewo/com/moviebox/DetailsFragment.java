package wewo.com.moviebox;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    public static final String ID = "id";
    private int movieId;


    public DetailsFragment() {
        // Required empty public constructor
    }

    public void setMovieId(int id) {
        movieId = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        MovieDatabaseAPI service = MovieDatabaseAPI.retrofit.create(MovieDatabaseAPI.class);
        Call<DetailMovie> data = service.getMovieData(Integer.toString(movieId));
        data.enqueue(new Callback<DetailMovie>() {
            @Override
            public void onResponse(Call<DetailMovie> call, Response<DetailMovie> response) {
                setUpData(response.body());
            }

            @Override
            public void onFailure(Call<DetailMovie> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });

        Call<TrailerModel> trailerModelCall = service.getTrailers(Integer.toString(movieId));
        trailerModelCall.enqueue(new Callback<TrailerModel>() {
            @Override
            public void onResponse(Call<TrailerModel> call, Response<TrailerModel> response) {
                TrailerAdapter trailerAdapter = new TrailerAdapter(response.body().results);
                Log.v("trailerAda", response.body().results.get(0).key);
                RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.trailers_recycler_view);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                trailerAdapter.setListener(new TrailerAdapter.TrailerClickListener() {
                    @Override
                    public void trailerClicked(String videoKey) {
                        Intent intent = new Intent(getActivity(), YoutubeActivity.class);
                        intent.putExtra(Intent.EXTRA_TEXT, videoKey);
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(trailerAdapter);
            }

            @Override
            public void onFailure(Call<TrailerModel> call, Throwable t) {
                Log.v("wtfisthat", "fail");
            }
        });

    }

    private void setUpData(DetailMovie movie) {
        setUpTitle(movie.original_title, movie.backdrop_path);
        setUpPoster(movie.poster_path);
        setUpYear(movie.release_date);
        setUpLength(movie.runtime);
        setUpVote(movie.vote_average);
        setUpOverview(movie.overview);
    }

    private void setUpPoster(String poster) {
        ImageView imageView = (ImageView) getActivity().findViewById(R.id.movie_image);
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w500" + poster).into(imageView);
    }

    private void setUpYear(String year) {
        TextView yearTxt = (TextView) getActivity().findViewById(R.id.movie_year);
        yearTxt.setText("Year:" + year);
    }

    private void setUpVote(double vote) {
        TextView rate = (TextView) getActivity().findViewById(R.id.movie_rate);
        rate.setText(Double.toString(vote) + "/10");
    }

    private void setUpOverview(String overview) {
        TextView description = (TextView) getActivity().findViewById(R.id.movie_description);
        description.setText(overview);
    }

    private void setUpLength(int length) {
        TextView lengthTxt = (TextView) getActivity().findViewById(R.id.movie_length);
        lengthTxt.setText("Length: " + Integer.toString(length));
    }

    private void setUpTitle(String titleData, String imgUrl) {
        ImageView imageView = (ImageView) getActivity().findViewById(R.id.movie_background);
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w500" + imgUrl).into(imageView);
        TextView textView = (TextView) getActivity().findViewById(R.id.movie_title);
        textView.setText(titleData);
    }
}
