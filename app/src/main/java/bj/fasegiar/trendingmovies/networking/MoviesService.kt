package bj.fasegiar.trendingmovies.networking

import bj.fasegiar.trendingmovies.model.network_data_transfert.MovieDetails
import bj.fasegiar.trendingmovies.model.network_data_transfert.MoviePage
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {
    @GET("discover/movie")
    suspend fun movies(
        @Query("api_key") key: String = "c9856d0cb57c3f14bf75bdc6c063b8f3"
    ): MoviePage

    @GET("movie/{id}")
    suspend fun movieDetail(
        @Path("id") id: Long,
        @Query("api_key") key: String = "c9856d0cb57c3f14bf75bdc6c063b8f3"
    ): MovieDetails
}