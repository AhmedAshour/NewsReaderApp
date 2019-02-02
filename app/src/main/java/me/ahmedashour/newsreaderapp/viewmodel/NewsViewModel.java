package me.ahmedashour.newsreaderapp.viewmodel;

import android.util.Log;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import me.ahmedashour.newsreaderapp.datamanager.NewsDataManager;
import me.ahmedashour.newsreaderapp.model.Article;

public class NewsViewModel extends ViewModel {

    private String TAG = NewsViewModel.class.getSimpleName();

    private NewsDataManager newsDataManager;

    public MediatorLiveData<List<Article>> newsLiveData;


    public LiveData<List<Article>> getNewsLiveData() {

        newsLiveData = new MediatorLiveData<>();
        newsDataManager = new NewsDataManager();

        if (newsLiveData.getValue() == null) {
            newsLiveData.addSource(newsDataManager.getsNewsFromSource(), articleList -> {
                newsLiveData.setValue(articleList);
                Log.d(TAG, "ARTICLES: " + articleList + "");
            });
        }

        return newsLiveData;
    }
}
