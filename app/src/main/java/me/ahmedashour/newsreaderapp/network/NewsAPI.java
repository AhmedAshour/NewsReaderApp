package me.ahmedashour.newsreaderapp.network;

import me.ahmedashour.newsreaderapp.utils.Constants;
import me.ahmedashour.newsreaderapp.model.ArticlesList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsAPI {

    @GET(Constants.EndPoints.TOP_HEADLINES+Constants.EndPoints.COUNTRY_EGYPT + Constants.API_KEY)
    Call<ArticlesList> getTopHeadlinesEG();

    @GET(Constants.EndPoints.TOP_HEADLINES+Constants.EndPoints.COUNTRY_US + Constants.API_KEY)
    Call<ArticlesList> getTopHeadlinesUS();

}
