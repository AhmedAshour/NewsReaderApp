package me.ahmedashour.newsreaderapp.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ahmedashour.newsreaderapp.Constants;
import me.ahmedashour.newsreaderapp.R;
import me.ahmedashour.newsreaderapp.adapter.NewsRecyclerAdapter;
import me.ahmedashour.newsreaderapp.model.Article;
import me.ahmedashour.newsreaderapp.viewmodel.NewsViewModel;

import java.util.List;


public class NewsActivity extends AppCompatActivity {

    private String TAG = NewsActivity.class.getSimpleName();

    private NewsViewModel newsViewModel;
    private NewsRecyclerAdapter newsRecyclerAdapter;
    private List<Article> articleList;

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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new NewsRecyclerAdapter());
    }

    private void initViewModel() {
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        newsViewModel.getNewsLiveData().observe(this, articleList -> {
            Log.d(TAG, "ARTICLES: " + articleList);

            recyclerView.setAdapter(new NewsRecyclerAdapter(NewsActivity.this, articleList, v -> {

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
                    startActivity(intent);
                }
            }));
        });
    }
}
