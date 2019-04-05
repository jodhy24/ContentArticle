package com.example.contentarticle.network;
import com.example.contentarticle.model.retrofit.ResponseLogin;
import com.example.contentarticle.model.retrofit.ResponseRegister;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseLogin> loginUser (
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseRegister> registerUser (
            @Field("username") String username,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password
    );
}
