package hello.tech.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//dd86fa88c89f4bb9bbb4a95a8638db44
public class MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryClickInterface {
    private ProgressBar pb;
Button btnOut;
FirebaseAuth auth;
private ArrayList<Articles> articlesArrayList;
private ArrayList<CategoriesModal> categoriesModals;
private CategoryAdapter categoryAdapter;
private NewsAdapter newsAdapter;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvNews = findViewById(R.id.news);
        RecyclerView rvCategory = findViewById(R.id.categories);

        btnOut=findViewById(R.id.btnOut);
        pb=findViewById(R.id.progress);

        articlesArrayList=new ArrayList<>();
        categoriesModals=new ArrayList<>();

        newsAdapter=new NewsAdapter(articlesArrayList,this);
        categoryAdapter=new CategoryAdapter(categoriesModals, this);
//        For Logout
        btnOut.setOnClickListener(v -> auth.signOut());

        rvNews.setLayoutManager(new LinearLayoutManager(this));
        rvNews.setAdapter(newsAdapter);
        rvCategory.setAdapter(categoryAdapter);
        getCategories();
        getNews("All");
        newsAdapter.notifyDataSetChanged();
    }
@SuppressLint("NotifyDataSetChanged")
private void getCategories()
{
    categoriesModals.add(new CategoriesModal("All","https://images.unsplash.com/photo-1588773846628-13fce0a32105?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8bmV3c3BhcGVyfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
    categoriesModals.add(new CategoriesModal("Technology","https://images.unsplash.com/photo-1550751827-4bd374c3f58b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OXx8dGVjaG5vbG9neXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
    categoriesModals.add(new CategoriesModal("Science","https://images.unsplash.com/photo-1564325724739-bae0bd08762c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8c2NpZW5jZXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
    categoriesModals.add(new CategoriesModal("Health","https://images.unsplash.com/photo-1532938911079-1b06ac7ceec7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80"));
    categoriesModals.add(new CategoriesModal("Entertainment","https://images.unsplash.com/photo-1499364615650-ec38552f4f34?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8ZW50ZXJ0YWlubWVudHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
    categoriesModals.add(new CategoriesModal("Sports","https://images.unsplash.com/photo-1517649763962-0c623066013b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fHNwb3J0fGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
    categoriesModals.add(new CategoriesModal("Business","https://images.unsplash.com/photo-1579532537598-459ecdaf39cc?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjN8fGJ1c2luZXNzfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
    categoriesModals.add(new CategoriesModal("General","https://images.unsplash.com/photo-1494059980473-813e73ee784b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8Z2VuZXJhbHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
    categoryAdapter.notifyDataSetChanged();
}
 private void getNews(String category)
 {
     pb.setVisibility(View.VISIBLE);
     articlesArrayList.clear();
     String categoryUrl="https://newsapi.org/v2/top-headlines?country=in&category="+category+"&apikey=dd86fa88c89f4bb9bbb4a95a8638db44";
     String Url="https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=dd86fa88c89f4bb9bbb4a95a8638db44";
     String BaseUrl="https://newsapi.org/";
     Retrofit retrofit=new Retrofit.Builder().baseUrl(BaseUrl).addConverterFactory(GsonConverterFactory.create()).build();
     RetrofitAPI retrofitAPI=retrofit.create(RetrofitAPI.class);
     Call<NewsModal> call;
     if(category.equals("All"))
     {
         call=retrofitAPI.getAllNews(Url);
     }
     else
     {
         call=retrofitAPI.getNewsByCategory(categoryUrl);
     }
     call.enqueue(new Callback<NewsModal>() {
         @SuppressLint("NotifyDataSetChanged")
         @Override
         public void onResponse(@NonNull Call<NewsModal> call, @NonNull Response<NewsModal> response) {
             NewsModal newsModal= response.body();
             pb.setVisibility(View.GONE);
             assert newsModal != null;
             ArrayList<Articles> articles=newsModal.getArticles();
            for (int i=0;i<articles.size();i++)
            {
                articlesArrayList.add(new Articles
                        (articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage(),articles.get(i).getUrl(),articles.get(i).getContent(),
                                articles.get(i).getAuthor(),articles.get(i).getPublishedAt()));
            }
            newsAdapter.notifyDataSetChanged();
         }

         @Override
         public void onFailure(@NonNull Call<NewsModal> call, @NonNull Throwable t) {
             Toast.makeText(MainActivity.this,"Failed To Get News",Toast.LENGTH_SHORT).show();
         }
     });
 }
    @Override
    public void onCategoryClick(int position) {
        String category;
        category=categoriesModals.get(position).getCategory();
getNews(category);
    }
}