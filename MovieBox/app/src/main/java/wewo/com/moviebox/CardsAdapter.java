package wewo.com.moviebox;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {
    private Listener listener;
    private List<Movie> movies;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public CardsAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    public interface Listener {
        void onCardClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.card_image);
        Picasso.with(holder.cardView.getContext()).load(getPicturePath(movies.get(position).poster_path))
                .into(imageView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCardClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    private String getPicturePath(String end) {
        return "http://image.tmdb.org/t/p/w500/" + end;
    }
}
