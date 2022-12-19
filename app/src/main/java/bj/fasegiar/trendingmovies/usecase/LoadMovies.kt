package bj.fasegiar.trendingmovies.usecase

import androidx.lifecycle.asLiveData
import bj.fasegiar.trendingmovies.repository.MoviesLoading
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@ViewModelScoped
class LoadMovies @Inject constructor(private val moviesLoading: MoviesLoading) {
    operator fun invoke() =
        moviesLoading.loadMovies().asLiveData(Dispatchers.Main)
}