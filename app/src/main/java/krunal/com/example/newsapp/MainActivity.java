package krunal.com.example.newsapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAB = MainActivity.class.getSimpleName();

    private RecycleViewAdapter mAdapter;
    private RecyclerView mRecycleView;
    private TextView mInternetTextView;
    private ProgressBar mProgressBar;
    private MainActivityViewModel mMainActivityViewModel;
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mInternetTextView = findViewById(R.id.NoInternetConnetion);
        mRecycleView = findViewById(R.id.recyclerView);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = findViewById(R.id.progressBar);

        mInternetTextView.setVisibility(View.GONE);

        if (CheckInternetConnection(this)) {
            mAdapter = new RecycleViewAdapter(this, new OnItemClickListener() {
                @Override
                public void OnItemClick(News news) {
                    String url = news.getmUrl();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            });

            mRecycleView.setAdapter(mAdapter);
            mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

            mMainActivityViewModel.getNews().observe(this, new Observer<List<News>>() {
                @Override
                public void onChanged(@Nullable List<News> list) {
                    Log.i(TAB, "onChanged call");
                    Log.i(TAB, String.valueOf(list.size()));
                    if (list.size()==0){
                        mProgressBar.setVisibility(View.VISIBLE);
                    }else {
                        mProgressBar.setVisibility(View.GONE);
                    }
                    mAdapter.add(list);
                }
            });
        }else {
            mInternetTextView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
            mMainActivityViewModel = null;
            mRecycleView = null;
            mAdapter = null;
            mProgressBar = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);

        return super.onCreateOptionsMenu(menu);
    }

    private void search(SearchView searchView){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.i(TAB,"onQueryTextChange call");
                if (CheckInternetConnection(getApplicationContext())) {
                    if (!newText.equals("")) {
                        Log.i(TAB, "newText not null");

                        mMainActivityViewModel.setSearch(newText);
                    }
                }
                return false;
            }
        });
    }

    private boolean CheckInternetConnection(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
