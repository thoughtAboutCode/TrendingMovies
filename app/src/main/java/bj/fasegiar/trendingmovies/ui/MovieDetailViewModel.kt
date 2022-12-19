package bj.fasegiar.trendingmovies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import bj.fasegiar.trendingmovies.model.domain.MovieDetail
import bj.fasegiar.trendingmovies.repository.Error
import bj.fasegiar.trendingmovies.repository.Loading
import bj.fasegiar.trendingmovies.repository.LoadingState
import bj.fasegiar.trendingmovies.repository.Success
import bj.fasegiar.trendingmovies.usecase.DetailMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val detailMovie: DetailMovie
) : ViewModel() {

    private val movieId = MutableLiveData<Long?>()

    private val movieDataLoadingState: LiveData<LoadingState<MovieDetail>> =
        Transformations.switchMap(movieId) {
            it?.let { id ->
                detailMovie(id)
            }
        }

    val movieData = Transformations.map(movieDataLoadingState) {
        it?.let { state ->
            when(state){
                is Error -> state.cachedData
                is Loading -> state.data
                is Success -> state.data
            }
        }
    }

    val isDataLoading = Transformations.map(movieDataLoadingState){
        it?.let {
            it is Loading
        }
    }

    val isDataError = Transformations.map(movieDataLoadingState){
        it?.let {
            it is Error
        }
    }

    fun setMovieId(id: Long) {
        movieId.value = null
        movieId.value = id
    }
}