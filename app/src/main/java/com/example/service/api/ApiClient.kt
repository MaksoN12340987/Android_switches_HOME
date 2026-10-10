package com.example.service.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val AUTH_TOKEN = "django-insecure-kw&o6na0bg6=!=deyi%q\$+#9my14*wr45h#v8f_cifxje23*tf"

object ApiClient {
    private const val BASE_URL = "http://192.168.0.149:8000/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Token $AUTH_TOKEN")
                .build()
            chain.proceed(request)
        }
        .build()

    val api: SmartHomeApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SmartHomeApi::class.java)
    }
}