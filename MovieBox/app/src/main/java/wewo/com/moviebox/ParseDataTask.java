//package wewo.com.moviebox;
//
//import android.os.AsyncTask;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class ParseDataTask extends AsyncTask<String, Void, MoviesData> {
//    private static final String RESULTS = "results";
//    private static final String POSTER = "poster_path";
//    private static final String ID = "id";
//
//    @Override
//    protected MoviesData doInBackground(String... strings) {
//        String[] posters = null;
//        int[] ids = null;
//        try {
//            JSONObject data = new JSONObject(strings[0]);
//            JSONArray allMovies = data.getJSONArray(RESULTS);
//            posters = new String[allMovies.length()];
//            ids = new int[allMovies.length()];
//            for (int i = 0; i < allMovies.length(); i++) {
//                JSONObject movie = allMovies.getJSONObject(i);
//                posters[i] = "http://image.tmdb.org/t/p/w342" + movie.getString(POSTER);
//                ids[i] = movie.getInt(ID);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return new MoviesData(ids, posters);
//    }
//}