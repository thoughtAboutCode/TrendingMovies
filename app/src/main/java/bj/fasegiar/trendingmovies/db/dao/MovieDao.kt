package bj.fasegiar.trendingmovies.db.dao

import androidx.room.*
import bj.fasegiar.trendingmovies.db.entity.MovieEntity
import bj.fasegiar.trendingmovies.model.domain.MovieDetail
import bj.fasegiar.trendingmovies.model.domain.MoviesListItem
import bj.fasegiar.trendingmovies.model.network_data_transfert.MovieDetails

@Dao
abstract class MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(movies: List<MovieEntity>)

    @Query("SELECT id, title, releaseDate, posterPath FROM MovieEntity")
    abstract suspend fun getAll(): List<MoviesListItem>

    @Query("SELECT title, releaseDate, posterPath, overview FROM MovieEntity WHERE id = :id")
    abstract suspend fun getMovieWithId(id: Long): MovieDetail

    @Update(entity = MovieEntity::class)
    abstract suspend fun updateMovieInfo(movieDetail: MovieDetails)

    @Query("DELETE FROM MovieEntity")
    abstract suspend fun clearAll()

    @Transaction
    open suspend fun updateAllData(movies: List<MovieEntity>) {
        clearAll()
        insertAll(movies)
    }
}