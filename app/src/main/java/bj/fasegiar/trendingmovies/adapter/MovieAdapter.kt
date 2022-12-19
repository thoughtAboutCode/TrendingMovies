package bj.fasegiar.trendingmovies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import bj.fasegiar.trendingmovies.databinding.MovieItemLayoutBinding
import bj.fasegiar.trendingmovies.model.domain.MoviesListItem
import bj.fasegiar.trendingmovies.ui.MainFragmentDirections

class MovieAdapter :
    ListAdapter<MoviesListItem, MovieViewHolder>(MovieComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        return MovieViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

object MovieComparator : DiffUtil.ItemCallback<MoviesListItem>() {
    override fun areItemsTheSame(oldItem: MoviesListItem, newItem: MoviesListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MoviesListItem, newItem: MoviesListItem): Boolean {
        return oldItem == newItem
    }
}

class MovieViewHolder(
    private val parent: ViewGroup,
    private val binding: MovieItemLayoutBinding = MovieItemLayoutBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
) : ViewHolder(binding.root) {

    fun bind(moviesListItem: MoviesListItem) {
        binding.item = moviesListItem
        if (binding.hasPendingBindings()) {
            binding.executePendingBindings()
        }
        itemView.setOnClickListener {
            Navigation.findNavController(parent).navigate(
                MainFragmentDirections.actionMainFragmentToMovieDetailFragment(moviesListItem.id)
            )
        }
    }
}