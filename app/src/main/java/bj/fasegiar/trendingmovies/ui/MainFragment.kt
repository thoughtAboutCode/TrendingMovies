package bj.fasegiar.trendingmovies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import bj.fasegiar.trendingmovies.R
import bj.fasegiar.trendingmovies.adapter.MovieAdapter
import bj.fasegiar.trendingmovies.databinding.FragmentMainBinding
import bj.fasegiar.trendingmovies.repository.Error
import bj.fasegiar.trendingmovies.repository.Loading
import bj.fasegiar.trendingmovies.repository.Success
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

interface MainUI {
    val adapter: MovieAdapter
}

@AndroidEntryPoint
class MainFragment : Fragment(), MainUI {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.ui = this@MainFragment
        }
        return binding.root
    }

    override val adapter: MovieAdapter = MovieAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.movies.observe(viewLifecycleOwner) {
            when (it) {
                is Error -> {
                    binding.loadingIndicator.isVisible = false
                    Snackbar.make(
                        binding.root,
                        getString(R.string.loading_error_message),
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction(getString(R.string.reload_litteral)) {
                            viewModel.reload()
                        }.show()
                }
                is Loading -> {
                    binding.loadingIndicator.isVisible = true
                    adapter.submitList(it.data)
                }
                is Success -> {
                    binding.loadingIndicator.isVisible = false
                    adapter.submitList(it.data)
                }
            }
        }
    }
}