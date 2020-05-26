package com.example.gojek.networking.retrofit

import com.example.gojek.networking.urls.RequestUrl.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    fun createRetrofitService(): RetrofitServiceAnnotator {
        val interceptor: HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client1 = OkHttpClient.Builder()
            .addInterceptor(interceptor)

        val retrofitClient: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client1.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofitClient.create(
            RetrofitServiceAnnotator::
            class.java
        )
    }
}