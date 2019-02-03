package me.ahmedashour.newsreaderapp.datamanager;

import android.util.Log;

import java.util.List;
import java.util.Locale;

import androidx.lifecycle.MutableLiveData;
import me.ahmedashour.newsreaderapp.utils.Constants;
import me.ahmedashour.newsreaderapp.model.Article;
import me.ahmedashour.newsreaderapp.model.ArticlesList;
import me.ahmedashour.newsreaderapp.network.NewsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsDataManager {

    private String TAG = NewsDataManager.class.getSimpleName();
    private String LANGUAGE;
    Call<ArticlesList> call;

    MutableLiveData<List<Article>> newsLiveData;

    public MutableLiveData<List<Article>> getsNewsFromSource() {

        newsLiveData = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsAPI newsAPI = retrofit.create(NewsAPI.class);

        LANGUAGE = Locale.getDefault().getLanguage();
        if (LANGUAGE.equals("ar"))
            call = newsAPI.getTopHeadlinesEG();
        else
            call = newsAPI.getTopHeadlinesUS();

        call.enqueue(new Callback<ArticlesList>() {
            @Override
            public void onResponse(Call<ArticlesList> call, Response<ArticlesList> response) {
                Log.d(TAG, "ARTICLES: " + response.body());
                newsLiveData.setValue(response.body().getArticles());
            }

            @Override
            public void onFailure(Call<ArticlesList> call, Throwable t) {
                Log.d(TAG, "FAILURE:" + t.toString());
            }
        });
        return newsLiveData;
    }
}
