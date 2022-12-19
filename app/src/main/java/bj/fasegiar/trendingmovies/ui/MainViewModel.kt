package bj.fasegiar.trendingmovies.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import bj.fasegiar.trendingmovies.usecase.LoadMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    loadMovies: LoadMovies
) : ViewModel() {
    private val reloadAction = MutableLiveData(true)
    val movies = Transformations.switchMap(reloadAction) {
        loadMovies()
    }

    fun reload() {
        reloadAction.postValue(!reloadAction.value!!)
    }
}