package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Comment;

import java.util.List;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView text_view_result;

    private jsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_view_result = findViewById(R.id.text_view_result);

        Gson gson = new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();


        jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);

//      getPosts();
//      getComments();
//      createPost();
      updatePost();
//      deletePost();
    }


    private void getPosts() {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");

        Call<List<post>> call = jsonPlaceHolderApi.getPost(parameters);

        call.enqueue(new Callback<List<post>>() {
            @Override
            public void onResponse(Call<List<post>> call, Response<List<post>> response) {
                if (!response.isSuccessful())
                {
                    text_view_result.setText("Code: " + response.code());
                    return;
                }

                List<post> posts = response.body();

                for (post post : posts)
                {
                    String content="";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() +"\n\n";

                    text_view_result.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<post>> call, Throwable t) {
                text_view_result.setText(t.getMessage());
            }
        });

    }


    private void getComments() {

        Call<List<comment>> call = jsonPlaceHolderApi
                .getComments("posts/3/comments");

        call.enqueue(new Callback<List<comment>>() {
            @Override
            public void onResponse(Call<List<comment>> call, Response<List<comment>> response) {

                if (!response.isSuccessful())
                {
                    text_view_result.setText("Code: " + response.code());
                    return;
                }


                List<comment> comments = response.body();

                for(comment comment : comments)
                    {
                        String content="";
                        content += "ID: " + comment.getId() + "\n";
                        content += "Post ID: " + comment.getPostId() + "\n";
                        content += "Name: " + comment.getName() + "\n";
                        content += "E-mail: " + comment.getName() + "\n";
                        content += "Text: " + comment.getText() +"\n\n";

                        text_view_result.append(content);
                    }

            }

            @Override
            public void onFailure(Call<List<comment>> call, Throwable t) {
                text_view_result.setText(t.getMessage());
            }
        });

    }


    private void createPost() {

        Map<String, String> fields = new HashMap<>();
        fields.put("userId","25");
        fields.put("title","Testing");
        fields.put("body", "Hello There");
//        post post = new post(23, "New Title", "New Text");
        Call<post> call = jsonPlaceHolderApi.createPost(fields);


        call.enqueue(new Callback<com.example.retrofit.post>() {
            @Override
            public void onResponse(Call<com.example.retrofit.post> call, Response<com.example.retrofit.post> response) {

                if (!response.isSuccessful())
                {
                    text_view_result.setText("Code: " + response.code());
                    return;
                }


                post postResponse = response.body();

                String content= "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() +"\n\n";

                text_view_result.setText(content);
            }

            @Override
            public void onFailure(Call<com.example.retrofit.post> call, Throwable t) {
                text_view_result.setText(t.getMessage());
            }
        });
    }


    private void updatePost() {
        post post = new post(12, null, "testing");
        Call<post> call = jsonPlaceHolderApi.putPost(5, post);
        call.enqueue(new Callback<com.example.retrofit.post>() {
            @Override
            public void onResponse(Call<com.example.retrofit.post> call, Response<com.example.retrofit.post> response) {
                if (!response.isSuccessful())
                {
                    text_view_result.setText("Code: " + response.code());
                    return;
                }


                post postResponse = response.body();

                String content= "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() +"\n\n";

                text_view_result.setText(content);
            }

            @Override
            public void onFailure(Call<com.example.retrofit.post> call, Throwable t) {
                text_view_result.setText(t.getMessage());
            }
        });

    }


    private void deletePost() {
        Call<Void> call = jsonPlaceHolderApi.deletePost(5);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                text_view_result.setText("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                text_view_result.setText(t.getMessage());
            }
        });
    }
}