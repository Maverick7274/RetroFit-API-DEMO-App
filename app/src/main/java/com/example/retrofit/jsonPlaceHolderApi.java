package com.example.retrofit;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface jsonPlaceHolderApi {

    @GET("posts")
    Call<List<post>> getPost(
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    @GET("posts")
    Call<List<post>> getPost(

            @QueryMap Map<String, String> parameters);


    @GET("post/{id}/comments")
    Call<List<comment>> getComments(@Path("id") int postId);


    @GET
    Call<List<comment>> getComments(@Url String url);


    @POST("posts")
    Call<post> createPost(@Body post post);

    @FormUrlEncoded
    @POST("posts")
    Call<post> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );

    @FormUrlEncoded
    @POST("posts")
    Call<post> createPost(@FieldMap Map<String, String> fields);


    @PUT("posts/{id}")
    Call<post> putPost(@Path("id") int id, @Body post post);

    @PATCH("posts/{id}")
    Call<post> patchPost(@Path("id") int id, @Body post post);


    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);
}
