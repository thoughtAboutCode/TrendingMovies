package bj.fasegiar.trendingmovies.model.network_data_transfert

import bj.fasegiar.trendingmovies.db.entity.MovieEntity
import com.squareup.moshi.Json

data class MovieOverview(
    val id: Long,
    @field:Json(name = "original_title")
    val originalTitle: String,
    @field:Json(name = "release_date")
    val releaseDate: String,
    @field:Json(name = "poster_path")
    val posterPath: String
)

fun MovieOverview.asEntity(): MovieEntity = MovieEntity(
    id = id,
    title = originalTitle,
    releaseDate = releaseDate,
    posterPath = posterPath,
    overview = "",
)
