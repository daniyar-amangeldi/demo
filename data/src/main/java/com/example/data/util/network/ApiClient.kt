package com.example.data.util.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    fun <T> createApiService(
        config: NetworkConfig,
        serviceClass: Class<T>
    ): T {
        return Retrofit.Builder()
            .baseUrl(config.baseUrl)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(config.timeout, TimeUnit.SECONDS)
                    .readTimeout(config.timeout, TimeUnit.SECONDS)
                    .writeTimeout(config.timeout, TimeUnit.SECONDS)
                    .apply {
                        config.interceptors.forEach {
                            addInterceptor(it)
                        }
                        config.headers.forEach { header ->
                            addInterceptor(
                                Interceptor {
                                    val original = it.request()

                                    val newRequest = original.newBuilder()
                                        .header(header.key, header.value)
                                        .build()

                                    return@Interceptor it.proceed(newRequest)
                                }
                            )
                        }
                    }
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(serviceClass)
    }
}