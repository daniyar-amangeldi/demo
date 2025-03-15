package com.example.domain.util

abstract class UseCase<out Type : Any, in Params : Any> {
    abstract suspend fun run(params: Params): Result<Type>
}