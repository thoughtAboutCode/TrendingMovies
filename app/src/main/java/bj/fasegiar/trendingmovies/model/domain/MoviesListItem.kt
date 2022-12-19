package bj.fasegiar.trendingmovies.model.domain

data class MoviesListItem(
    val id: Long,
    val title: String,
    val releaseDate: String,
    val posterPath: String,
)
