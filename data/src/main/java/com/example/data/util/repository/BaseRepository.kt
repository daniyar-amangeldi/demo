package com.example.data.util.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import kotlin.reflect.KClass

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(
        call: suspend () -> T,
        exceptionClass: KClass<out Exception>? = null
    ): Result<T> {
        return runCatching { call() }
            .recoverCatching { e ->
                when (e) {
                    is HttpException -> handleHttpException(e, exceptionClass)
                    else -> throw e
                }
            }
    }

    private fun handleHttpException(
        e: HttpException,
        kClass: KClass<out Exception>?
    ): Nothing {
        val source = e.response()?.errorBody()?.string()
        if (source != null) {
            val apiException = parseException(source, kClass ?: HttpException::class)
            throw apiException
        }
        throw e
    }

    private fun parseException(json: String, kClass: KClass<out Exception>): Exception {
        return try {
            Gson().fromJson(json, TypeToken.getParameterized(kClass.java).type)
        } catch (e: Exception) {
            e
        }
    }
}