package me.ahmedashour.newsreaderapp.views;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.ahmedashour.newsreaderapp.utils.Constants;
import me.ahmedashour.newsreaderapp.R;
import me.ahmedashour.newsreaderapp.utils.Utils;
import me.ahmedashour.newsreaderapp.model.Article;
import me.ahmedashour.newsreaderapp.viewmodel.NewsViewModel;

public class NewsDetailsFragment extends Fragment {

    private String TAG = NewsDetailsFragment.class.getSimpleName();
    private NewsViewModel newsViewModel;
    private Article article;
    private String position;

    @BindView(R.id.tv_date_details)
    TextView tvDateDetails;
    @BindView(R.id.tv_desc_details)
    TextView tvDescDetails;
    @BindView(R.id.tv_source_details)
    TextView tvSourceDetails;

    public NewsDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments().containsKey(Constants.ARG_ARTICLE_POSITION)) {
            position = getArguments().getString(Constants.ARG_ARTICLE_POSITION);
            initViewModel(position);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.news_details, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    public void initViewModel(String position) {
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        newsViewModel.getNewsLiveData().observe(this, articleList -> {
            article = articleList.get(Integer.valueOf(position));
            Log.d(TAG, article.toString());

            tvDateDetails.setText(Utils.dateFormater(article.getPublishedAt()));
            tvDescDetails.setText(article.getDescription());
            tvSourceDetails.setText(article.getSource().getName());

            CollapsingToolbarLayout appBarLayout = getActivity().findViewById(R.id.toolbar_layout);
            ImageView ivAppBar = getActivity().findViewById(R.id.iv_app_bar);
            Picasso.get().load(article.getUrlToImage()).into(ivAppBar);

            if (appBarLayout != null) {
                appBarLayout.setTitle(article.getTitle());
            }

        });
    }

}
