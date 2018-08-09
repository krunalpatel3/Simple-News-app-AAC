package krunal.com.example.newsapp;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 05-03-2018.
 */

class Repository {

    private static final String TAB1 = Repository.class.getSimpleName();
    private static final String BASE_URL = "http://content.guardianapis.com/search";
    static List<News> getNewsListFromApi = new ArrayList<>();
    static MutableLiveData<List<News>> mListOfNews;
    public static String newssearch;

    Repository() {
        Log.i(TAB1, "Reposittory call");
        mListOfNews = new MutableLiveData<>();
        new GetDataAsyncTask().execute();
    }

    LiveData<List<News>> getNews(){
        Log.i(TAB1, "getNews Call");
        return mListOfNews;
    }

    static class GetDataAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Log.i(TAB1,"doInBackground call");
            Uri baseUri = Uri.parse(BASE_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();
            uriBuilder.appendQueryParameter("api-key", "4f3854fb-02f1-4da2-81bf-d2194aa7ebdc");
            if(newssearch!=null)
                uriBuilder.appendQueryParameter("q", newssearch);
            getNewsListFromApi = Utility.fetchNewsData(uriBuilder.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i(TAB1,String.valueOf(getNewsListFromApi.size()));
            mListOfNews.postValue(getNewsListFromApi);
            super.onPostExecute(aVoid);
        }
    }

    void fetch(){
        Log.i(TAB1,"fetch call");
        if (getNewsListFromApi!=null){
            getNewsListFromApi.clear();
            if (getNewsListFromApi.size()==0){
                Log.i(TAB1,"getNewsListFromApi is clear");
            }
            new GetDataAsyncTask().execute();
            Log.i(TAB1,"Data post to LiveData again");
            mListOfNews.postValue(getNewsListFromApi);
        }
    }




}
