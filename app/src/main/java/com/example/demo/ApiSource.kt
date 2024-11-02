package com.example.demo

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiSource {

    private val gson = Gson()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val request = it.request().newBuilder().addHeader(
                "Authorization",
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiODJjMTcyZTdiZjY2NjA1MTY4ODFjNmExZWQ2MTZkZCIsIm5iZiI6MTczMDU0MjY1MS41NDc5MDIzLCJzdWIiOiI1ZjkxYjEzYTU1YzFmNDAwMzkyNzk5YTIiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.JQN6zlqcT5VT4E8HxJz_sytmEU9lQELBsRU5s4SEFXk"
            ).build()

            it.proceed(request)
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val client = retrofit.create(MovieApi::class.java)

}