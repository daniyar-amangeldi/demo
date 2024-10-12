package com.example.demo

object DataSource {

    var movieList = arrayListOf(
        Movie(
            title = "Venom Let There Be Carange",
            rating = 6.4,
            duration = 107
        ),
        Movie(
            title = "The King's Man",
            rating = 5.4,
            duration = 95
        ),
        Movie(
            title = "Harry Potter",
            rating = 8.4,
            duration = 120
        ),
        Movie(
            title = "Lord of Rings",
            rating = 9.0,
            duration = 130
        ),
        Movie(
            title = "Avengers",
            rating = 7.4,
            duration = 124
        ),
        Movie(
            title = "Avengers",
            rating = 7.4,
            duration = 124
        ),
        Movie(
            title = "Avengers",
            rating = 7.4,
            duration = 124
        ),
        Movie(
            title = "Avengers",
            rating = 7.4,
            duration = 124
        ),
        Movie(
            title = "Avengers",
            rating = 7.4,
            duration = 124
        ),
        Movie(
            title = "Avengers",
            rating = 7.4,
            duration = 124
        ),
        Movie(
            title = "Avengers",
            rating = 7.4,
            duration = 124
        ),
        Movie(
            title = "Avengers",
            rating = 7.4,
            duration = 124
        ),
        Movie(
            title = "Avengers",
            rating = 7.4,
            duration = 124
        ),
        Movie(
            title = "Avengers",
            rating = 7.4,
            duration = 124
        ),
    )

    fun setFavourite(movieId: String): ArrayList<Movie> {
        movieList = ArrayList(
            movieList.map {
                if (it.id == movieId) {
                    it.copy(isFavourite = true)
                } else {
                    it
                }
            }
        )

        return movieList
    }

    fun unsetFavourite(movieId: String): ArrayList<Movie> {
        movieList = ArrayList(
            movieList.map {
                if (it.id == movieId) {
                    it.copy(isFavourite = false)
                } else {
                    it
                }
            }
        )

        return movieList
    }
}