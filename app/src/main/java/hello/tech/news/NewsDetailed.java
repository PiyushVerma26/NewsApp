package hello.tech.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class NewsDetailed extends AppCompatActivity {
  String title,description,content,imageUrl,url,time,author;
  TextView tvTitle,tvDescription,tvContent,tvAuthor;
  Button btnMore;
  ImageView ivImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detailed);
        title=getIntent().getStringExtra("title");
        description=getIntent().getStringExtra("description");
        content=getIntent().getStringExtra("content");
        imageUrl=getIntent().getStringExtra("image");
        url=getIntent().getStringExtra("url");
        time=getIntent().getStringExtra("time");
        author=getIntent().getStringExtra("author");


        tvTitle=findViewById(R.id.tvTitle);
        tvDescription=findViewById(R.id.tvDescription);
        tvContent=findViewById(R.id.tvContent);
        ivImage=findViewById(R.id.ivNews);
        btnMore=findViewById(R.id.btnRead);
        tvAuthor=findViewById(R.id.author);


        tvAuthor.setText(author);
        tvTitle.setText(title);
        tvDescription.setText(description);
        tvContent.setText(content);
        Picasso.get().load(imageUrl).into(ivImage);


        btnMore.setOnClickListener(v -> {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);

        });
    }
}