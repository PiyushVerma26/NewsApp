package hello.tech.news;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.viewHolder> {
    private ArrayList<Articles> articlesArrayList;
    private Context context;

    public NewsAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.new_rv,parent,false);
        return new NewsAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.viewHolder holder, int position) {
        Articles articles=articlesArrayList.get(position);
        holder.tvTime.setText(articles.getPublishedAt());
        holder.tvChannel.setText(articles.getAuthor());
        holder.tvDescription.setText(articles.getDescription());
        holder.tvTitle.setText(articles.getTitle());
        Picasso.get().load(articles.getUrlToImage()).into(holder.ivNews);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,NewsDetailed.class);
                intent.putExtra("title",articles.getTitle());
                intent.putExtra("description",articles.getDescription());
                intent.putExtra("content",articles.getContent());
                intent.putExtra("time",articles.getPublishedAt());
                intent.putExtra("author",articles.getAuthor());
                intent.putExtra("image",articles.getUrlToImage());
                intent.putExtra("url",articles.getUrl());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private TextView tvTime,tvChannel,tvTitle,tvDescription;
       private ImageView ivNews;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
             tvTime=itemView.findViewById(R.id.tvTime);
            tvChannel=itemView.findViewById(R.id.tvChannel);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvDescription=itemView.findViewById(R.id.tvDescription);
            ivNews=itemView.findViewById(R.id.ivNews);
        }
    }
}
