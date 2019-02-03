package me.ahmedashour.newsreaderapp.views;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.ahmedashour.newsreaderapp.Constants;
import me.ahmedashour.newsreaderapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

public class WebView extends AppCompatActivity {

    @BindView(R.id.web_view)
    android.webkit.WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        String url = getIntent().getStringExtra(Constants.ARG_URL);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
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
