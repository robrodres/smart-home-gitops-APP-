package com.example.smarthomedevops.data.remote

import com.example.smarthomedevops.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitClient {
    private const val BASE_URL="https://api.github.com/"


    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val client=OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request=chain.request().newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer ${BuildConfig.token}"
                )
                .addHeader(
                    "Accept",
                    "application/vnd.github+json"
                )
                .addHeader(
                    "X-GitHub-Api-Version",
                    "2026-03-10"
                )
                .build()

            chain.proceed(request)
        }
        .addInterceptor(loggingInterceptor)
        .build()

    val api:GitHubApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApi::class.java)
    }
}