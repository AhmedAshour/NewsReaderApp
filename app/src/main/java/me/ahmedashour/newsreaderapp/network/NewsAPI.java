package me.ahmedashour.newsreaderapp.network;

import me.ahmedashour.newsreaderapp.utils.Constants;
import me.ahmedashour.newsreaderapp.model.ArticlesList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsAPI {

    @GET(Constants.EndPoints.EVERYTHING+"?q=bitcoin"+ Constants.API_KEY)
    Call<ArticlesList> getEverything();

}
