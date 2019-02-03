package me.ahmedashour.newsreaderapp.views;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.widget.Toolbar;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import me.ahmedashour.newsreaderapp.utils.Constants;
import me.ahmedashour.newsreaderapp.R;

import android.view.MenuItem;

public class NewsDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            String url = getIntent().getStringExtra(Constants.ARG_URL);
            Intent intent = new Intent(NewsDetailsActivity.this, WebView.class);
            intent.putExtra(Constants.ARG_URL, url);
            startActivity(intent);
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(Constants.ARG_ARTICLE_POSITION,
                    getIntent().getStringExtra(Constants.ARG_ARTICLE_POSITION));
            NewsDetailsFragment fragment = new NewsDetailsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, NewsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
