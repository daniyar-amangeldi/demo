package com.example.demo

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiSource {

    private val gson = Gson()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://my-json-server.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val client = retrofit.create(MovieApi::class.java)

}