package me.ahmedashour.newsreaderapp.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ahmedashour.newsreaderapp.R;
import me.ahmedashour.newsreaderapp.utils.Utils;
import me.ahmedashour.newsreaderapp.model.Article;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private List<Article> articleList;
    private List<Article> articleListCopy;
    private Context context;
    private View.OnClickListener mListener;

    public NewsRecyclerAdapter() {
    }

    public NewsRecyclerAdapter(Context context, List<Article> articleList, View.OnClickListener mListener) {
        this.context = context;
        this.articleList = articleList;
        this.mListener = mListener;
        articleListCopy = new ArrayList<>();
        articleListCopy.addAll(articleList);

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item_content, viewGroup, false);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int i) {
        Article article = articleList.get(i);

        try {
            holder.itemView.setOnClickListener(mListener);
            holder.setImage(article.getUrlToImage());
            holder.setTitle(article.getTitle());
            holder.setDesc(article.getDescription());
            holder.setDate(Utils.dateFormater(article.getPublishedAt()));

            holder.setSource(article.getSource().getName());

        } catch (Exception e) {
        }
    }


    @Override
    public int getItemCount() {
        if (articleList != null) return articleList.size();
        else return 0;
    }

    public void filterArticles(String text) {
        articleList.clear();
        if(text.isEmpty()){
            articleList.addAll(articleListCopy);
        } else if(text.length() >= 3){
            text = text.toLowerCase();
            for(Article article: articleListCopy){
                if(article.getTitle().toLowerCase().contains(text)){
                    articleList.add(article);
                }
            }
        }
        notifyDataSetChanged();
    }
}

class NewsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_source)
    TextView tvSource;
    @BindView(R.id.tv_date)
    TextView tvDate;

    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setImage(String image) {
        Picasso.get().load(image).into(ivImage);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setDesc(String desc) {
        tvDesc.setText(desc);
    }

    public void setSource(String source) {
        tvSource.setText(source);
    }

    public void setDate(String date) {
        tvDate.setText(date);
    }


}