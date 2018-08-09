package krunal.com.example.newsapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import java.util.List;

/**
 * Created by acer on 05-03-2018.
 */

public class MainActivityViewModel extends AndroidViewModel {

    private static final String TAB2 = MainActivityViewModel.class.getSimpleName();
    private Repository mRepository;

    private LiveData<List<News>> mNewList;

    public MainActivityViewModel(@NonNull Application application)  {
        super(application);
        Log.i(TAB2,"MainActivityViewModel call");
        this.mRepository = new Repository();
        this.mNewList = mRepository.getNews();
    }

     LiveData<List<News>> getNews(){
        Log.i(TAB2,"getNews call");
        return mNewList;
    }

    void setSearch(String search){
         Log.i(TAB2,"setSearch call");
         Repository.newssearch = search;
         mRepository.fetch();
    }





}
