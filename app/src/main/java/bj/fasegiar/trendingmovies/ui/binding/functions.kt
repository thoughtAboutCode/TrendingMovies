package bj.fasegiar.trendingmovies.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("posterPath")
fun configureImageView(imageView: ImageView, posterPath: String) {
    Picasso.get().load("https://image.tmdb.org/t/p/w500/$posterPath").into(imageView)
}