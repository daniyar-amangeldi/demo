package com.example.demo

data class Person(
    val name: String,
    val surname: String,
    val educationLevel: EducationLevel,
    val disciplines: MutableList<Discipline>
)

enum class EducationLevel {
    MASTER, BACHELOR;

    companion object {
        fun create(value: String) = if (value == "Master") {
            MASTER
        } else {
            BACHELOR
        }
    }
}

enum class Discipline {
    ANDROID, IOS
}
