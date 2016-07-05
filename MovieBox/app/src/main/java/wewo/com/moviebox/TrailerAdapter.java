package wewo.com.moviebox;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    public static final String DEVELOPER_KEY = "your youtube api key";
    private List<Trailer> trailerList;
    private TrailerClickListener listener;

    public void setListener(TrailerClickListener listener) {
        this.listener = listener;
    }

    public TrailerAdapter(List<Trailer> trailerList) {
        this.trailerList = trailerList;
    }

    public interface TrailerClickListener {
        void trailerClicked(String videoKey);
    }

    public static class ViewHolder extends CardsAdapter.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardView;
        YouTubeThumbnailView youTubeThumbnailView = (YouTubeThumbnailView) cardView.findViewById(R.id.trailer_thumbnail);
        youTubeThumbnailView.initialize(DEVELOPER_KEY, new InitializeListener(position));
        TextView textView = (TextView) cardView.findViewById(R.id.trailer_title);
        textView.setText(trailerList.get(position).name);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.trailerClicked(trailerList.get(position).key);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    private class InitializeListener implements YouTubeThumbnailView.OnInitializedListener {
        private int idx;

        public InitializeListener(int idx) {
            this.idx = idx;
        }

        @Override
        public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
            youTubeThumbnailLoader.setOnThumbnailLoadedListener(new ThumbnailListener());
            youTubeThumbnailLoader.setVideo(trailerList.get(idx).key);
            Log.v("trailers", trailerList.get(idx).key);
        }

        @Override
        public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

        }
    }

    private final class ThumbnailListener implements
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView thumbnail, String videoId) {
        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView thumbnail,
                                     YouTubeThumbnailLoader.ErrorReason reason) {
        }
    }

}
