package bj.fasegiar.trendingmovies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import bj.fasegiar.trendingmovies.R
import bj.fasegiar.trendingmovies.databinding.FragmentMovieDetailBinding
import bj.fasegiar.trendingmovies.ui.binding.configureImageView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val viewModel: MovieDetailViewModel by viewModels()

    private val args: MovieDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentMovieDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.movieData.observe(viewLifecycleOwner) {
            it?.let { detail ->
                with(binding) {
                    configureImageView(moviePoster, detail.posterPath)
                    movieTitle.text = detail.title
                    movieReleaseDate.text = detail.releaseDate
                    movieOverview.text = detail.overview
                }
            }
        }

        viewModel.isDataLoading.observe(viewLifecycleOwner) {
            binding.loadingIndicator.isVisible = it == true
        }

        viewModel.isDataError.observe(viewLifecycleOwner) {
            if (it == true){
                Snackbar.make(
                    binding.root,
                    getString(R.string.loading_error_message),
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(getString(R.string.reload_litteral)) {
                    viewModel.setMovieId(args.movieId)
                }.show()
            }
        }
        viewModel.setMovieId(args.movieId)
    }

}