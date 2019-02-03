package me.ahmedashour.newsreaderapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ArticlesList {

    @SerializedName("articles")
    private List<Article> articles;

    public ArticlesList() {
        articles = new ArrayList<>();
    }

    public List<Article> getArticles() {
        return articles;
    }
}
