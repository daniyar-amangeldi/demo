package com.example.data.util.network

object NetworkManager {
    val apiClient = ApiClient()

    inline fun <reified T> createService(
        config: NetworkConfig
    ) = apiClient.createApiService(config, T::class.java)
}