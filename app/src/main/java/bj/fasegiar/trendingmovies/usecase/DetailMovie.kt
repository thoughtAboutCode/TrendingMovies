package bj.fasegiar.trendingmovies.usecase

import androidx.lifecycle.asLiveData
import bj.fasegiar.trendingmovies.repository.DetailOfMovie
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@ViewModelScoped
class DetailMovie @Inject constructor(private val detailOfMovie: DetailOfMovie) {
    operator fun invoke(id: Long) =
        detailOfMovie.getDetailFor(id).asLiveData(Dispatchers.Main)
}