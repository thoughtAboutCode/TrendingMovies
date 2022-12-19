package bj.fasegiar.trendingmovies.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val releaseDate: String,
    val posterPath: String,
    val overview: String
)
