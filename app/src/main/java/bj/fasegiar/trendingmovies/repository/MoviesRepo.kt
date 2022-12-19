package bj.fasegiar.trendingmovies.repository

import bj.fasegiar.trendingmovies.db.dao.MovieDao
import bj.fasegiar.trendingmovies.model.domain.MovieDetail
import bj.fasegiar.trendingmovies.model.domain.MoviesListItem
import bj.fasegiar.trendingmovies.model.network_data_transfert.MovieOverview
import bj.fasegiar.trendingmovies.model.network_data_transfert.asEntity
import bj.fasegiar.trendingmovies.networking.MoviesService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

sealed interface LoadingState<D>

data class Loading<D>(val data: D) : LoadingState<D>

data class Success<D>(val data: D) : LoadingState<D>

data class Error<D>(val throwable: Throwable, val cachedData: D? = null) : LoadingState<D>

fun interface MoviesLoading {
    fun loadMovies(): Flow<LoadingState<List<MoviesListItem>>>
}

fun interface DetailOfMovie {
    fun getDetailFor(id: Long): Flow<LoadingState<MovieDetail>>
}

@Singleton
class MoviesRepo @Inject constructor(
    private val movieDao: MovieDao,
    private val moviesService: MoviesService
) : MoviesLoading, DetailOfMovie {
    override fun loadMovies(): Flow<LoadingState<List<MoviesListItem>>> = flow {
        emit(Loading(movieDao.getAll()))
        val response = moviesService.movies()
        movieDao.updateAllData(response.results.map(MovieOverview::asEntity))
        emit(Success(movieDao.getAll()))
    }.catch { ex ->
        emit(Error(ex))
    }.flowOn(Dispatchers.IO)

    override fun getDetailFor(id: Long): Flow<LoadingState<MovieDetail>> =
        flow {
            emit(Loading(movieDao.getMovieWithId(id)))
            val response = moviesService.movieDetail(id = id)
            movieDao.updateMovieInfo(response)
            emit(Success(movieDao.getMovieWithId(id)))
        }.catch { ex ->
            emit(Error(ex, cachedData = movieDao.getMovieWithId(id)))
        }.flowOn(Dispatchers.IO)
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class MoviesRepoBinding {
    @Binds
    abstract fun provideMoviesLoading(moviesRepo: MoviesRepo): MoviesLoading

    @Binds
    abstract fun provideMovieDetail(moviesRepo: MoviesRepo): DetailOfMovie
}