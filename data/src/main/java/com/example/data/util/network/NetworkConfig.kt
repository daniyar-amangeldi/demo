package com.example.data.util.network
import okhttp3.Interceptor

data class NetworkConfig(
    val baseUrl: String,
    val timeout: Long,
    val headers: HashMap<String, String>,
    val interceptors: List<Interceptor>
)