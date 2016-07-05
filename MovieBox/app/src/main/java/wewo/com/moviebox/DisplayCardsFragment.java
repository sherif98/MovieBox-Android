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
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayCardsFragment extends Fragment {
    public static final String REQUEST_TYPE = "request_type";
    //    private MoviesData moviesData;
    private RequestMovieList requestMovieList;
    private final static String SAVED_DATA = "saved_data";
//    private static final String ID_ARRAY = "id";
//    private static final String POSTER_ARRAY = "poster";

    public DisplayCardsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_display_cards, container, false);
        Bundle bundle = getArguments();
        String s = bundle.getString(REQUEST_TYPE);
        Log.v("fraginstall", s);
//        if (savedInstanceState != null) {
//            requestMovieList.results = savedInstanceState.getParcelableArrayList(SAVED_DATA);
//            moviesData = new MoviesData(savedInstanceState.getIntArray(ID_ARRAY),
//                    savedInstanceState.getStringArray(POSTER_ARRAY));
//        } else {
//            MainActivity.FragmentTitles title = MainActivity.FragmentTitles.valueOf(s);
//            FragmentStateController fragmentStateController = new FragmentStateController(title);
        MovieDatabaseAPI service = MovieDatabaseAPI.retrofit.create(MovieDatabaseAPI.class);
        Call<RequestMovieList> data = service.getData(s);
        data.enqueue(new Callback<RequestMovieList>() {
            @Override
            public void onResponse(Call<RequestMovieList> call, final Response<RequestMovieList> response) {
                CardsAdapter cardsAdapter = new CardsAdapter(response.body().results);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recyclerView.setAdapter(cardsAdapter);
                cardsAdapter.setListener(new CardsAdapter.Listener() {
                    @Override
                    public void onCardClick(int position) {
                        //TODO display detail activity with chosen position id
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra(DetailsFragment.ID, response.body().results.get(position).id);
                        startActivity(intent);
                    }
                });
//                requestMovieList = response.body();
                Log.v("success", "here");
            }

            @Override
            public void onFailure(Call<RequestMovieList> call, Throwable t) {
                Log.v("failure", "here");
                Toast.makeText(getActivity(), "Internet Unavailable", Toast.LENGTH_SHORT).show();

            }
        });

//        }

        return recyclerView;
    }
//TODO
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putIntArray(ID_ARRAY, moviesData.getId());
//        outState.putStringArray(POSTER_ARRAY, moviesData.getPosterPath());
//    }
}

