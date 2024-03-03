package com.enokoo.newske;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategorClickInterface {
    //api key
    private RecyclerView newsRV, categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsAVAdapter newsAVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRV = findViewById(R.id.RvNews);
        categoryRV = findViewById(R.id.RvCategories);
        loadingPB = findViewById(R.id.PbLoading);

articlesArrayList=new ArrayList<>();
categoryRVModalArrayList =new ArrayList<>();
newsAVAdapter=new NewsAVAdapter(articlesArrayList,this);
categoryRVAdapter=new CategoryRVAdapter(categoryRVModalArrayList,this,this::onCategoryClick);
newsRV.setLayoutManager(new LinearLayoutManager(this));
newsRV.setAdapter(newsAVAdapter);
categoryRV.setAdapter(categoryRVAdapter);
getCategories();
getNews("All");
newsAVAdapter.notifyDataSetChanged();
    }
    private void getCategories(){
categoryRVModalArrayList.add(new CategoryRVModal("All","https://images.unsplash.com/photo-1572949645841-094f3a9c4c94?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8bmV3c3xlbnwwfHwwfHx8MA%3D%3D"));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology","https://images.unsplash.com/photo-1485827404703-89b55fcc595e?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MjB8fHRlY2hub2xvZ3l8ZW58MHx8MHx8fDA%3D"));
        categoryRVModalArrayList.add(new CategoryRVModal("Science","https://images.unsplash.com/photo-1576086213369-97a306d36557?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8c2NpZW5jZXxlbnwwfHwwfHx8MA%3D%3D"));
        categoryRVModalArrayList.add(new CategoryRVModal("Sports","https://images.unsplash.com/photo-1579952363873-27f3bade9f55?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8c3BvcnRzfGVufDB8fDB8fHww"));
        categoryRVModalArrayList.add(new CategoryRVModal("General","https://images.unsplash.com/photo-1493612276216-ee3925520721?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8Z2VuZXJhbHxlbnwwfHwwfHx8MA%3D%3D"));
        categoryRVModalArrayList.add(new CategoryRVModal("Business","https://images.unsplash.com/photo-1665686304355-0b09b1e3b03c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTF8fGJ1c2luZXNzfGVufDB8fDB8fHww"));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment","https://images.unsplash.com/photo-1499364615650-ec38552f4f34?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8ZW50ZXJ0YWlubWVudHxlbnwwfHwwfHx8MA%3D%3D"));
        categoryRVModalArrayList.add(new CategoryRVModal("Health","https://images.unsplash.com/photo-1532938911079-1b06ac7ceec7?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTB8fGhlYWx0aHxlbnwwfHwwfHx8MA%3D%3D"));
    categoryRVAdapter.notifyDataSetChanged();
    }
private void getNews(String category){
loadingPB.setVisibility(View.VISIBLE);
articlesArrayList.clear();
String categoryURL="https://newsapi.org/v2/top-headlines?country=in&category="+category+"&apiKey=";
    String url="https://newsapi.org/v2/sources?excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=";
    String  BASE_URL="https://newsapi.org/";
    Retrofit retrofit=new Retrofit.Builder()
    .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
RetrofitAPI retrofitAPI=retrofit.create(RetrofitAPI.class);
    Call<NewsModal>call;
    if(category.equals("All")){
        call=retrofitAPI.getAllNews(url);
    }else{
        call=retrofitAPI.getNewsByCategory(categoryURL);
    }
    call.enqueue(new Callback<NewsModal>() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NewsModal newsModal = response.body();
                    if (newsModal.getArticles() != null) {
                        articlesArrayList.clear();
                        articlesArrayList.addAll(newsModal.getArticles());
                        newsAVAdapter.notifyDataSetChanged();
                    } else {
                        // Handle case where articles list is null
                        Log.e("MainActivity", "Articles list is null");
                    }
                } else {
                    // Handle unsuccessful response
                    Log.e("MainActivity", "Response not successful");
                }
            loadingPB.setVisibility(View.GONE);
            }



        @Override
        public void onFailure(Call<NewsModal> call, Throwable t) {
            Toast.makeText(MainActivity.this, "Fail to get news!", Toast.LENGTH_SHORT).show();
            loadingPB.setVisibility(View.GONE);
        }
    });
    }
    @Override
    public void onCategoryClick(int position) {
        String category=categoryRVModalArrayList.get(position).getCategory();
getNews(category);
    }
}

