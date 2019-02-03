package me.ahmedashour.newsreaderapp.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ahmedashour.newsreaderapp.utils.Constants;
import me.ahmedashour.newsreaderapp.R;
import me.ahmedashour.newsreaderapp.adapter.NewsRecyclerAdapter;
import me.ahmedashour.newsreaderapp.viewmodel.NewsViewModel;


public class NewsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private String TAG = NewsActivity.class.getSimpleName();

    private NewsViewModel newsViewModel;
    private NewsRecyclerAdapter newsRecyclerAdapter;

    private boolean mTwoPane;
    @BindView(R.id.news_list)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        initRecyclerView();
        initViewModel();

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    private void initViewModel() {
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        newsViewModel.getNewsLiveData().observe(this, articleList -> {
            Log.d(TAG, "ARTICLES: " + articleList);

            recyclerView.setAdapter(new NewsRecyclerAdapter());
            newsRecyclerAdapter = new NewsRecyclerAdapter(NewsActivity.this, articleList, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String position = String.valueOf(recyclerView.getChildLayoutPosition(v));
                    Toast.makeText(NewsActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();

                    if (mTwoPane) {
                        Bundle args = new Bundle();
                        args.putString(Constants.ARG_ARTICLE_POSITION, position);
                        NewsDetailsFragment fragment = new NewsDetailsFragment();
                        fragment.setArguments(args);
                        new NewsActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Intent intent = new Intent(v.getContext(), NewsDetailsActivity.class);
                        intent.putExtra(Constants.ARG_ARTICLE_POSITION, position);
                        intent.putExtra(Constants.ARG_URL, articleList.get(Integer.valueOf(position)).getUrl());
                        NewsActivity.this.startActivity(intent);
                    }
                }
            });
            recyclerView.setAdapter(newsRecyclerAdapter);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem search = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) search.getActionView();

        searchView.setOnQueryTextListener(this);

        return true;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        newsRecyclerAdapter.filterArticles(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newsRecyclerAdapter.filterArticles(newText);
        return true;
    }
}
