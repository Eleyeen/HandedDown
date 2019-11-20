package com.example.handeddown.Network;

import com.example.handeddown.Models.RegistrationResponseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @Multipart
    @POST("post")

    Call<RegistrationResponseModel> registration(@Part("title") RequestBody name,
                                                 @Part("description") RequestBody email,
                                                 @Part("author") RequestBody password,
                                                 @Part MultipartBody.Part photo,
                                                 @Part("file") RequestBody fileName);

}
