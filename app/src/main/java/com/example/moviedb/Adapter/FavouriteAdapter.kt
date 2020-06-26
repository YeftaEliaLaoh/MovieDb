package com.example.moviedb.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.Activity.DetailActivity
import com.example.moviedb.Model.MovieSearch
import com.example.moviedb.R

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_1.view.*

class FavouriteAdapter(
    val context: Context,
    val namelist: ArrayList<MovieSearch>,
    val check: Boolean
) : RecyclerView.Adapter<FavouriteAdapter.myviewholder>() {

    val baseURL = "https://image.tmdb.org/t/p/w342/"


    override fun getItemCount(): Int {
        if (check == false)
            if (namelist != null) {
                return namelist.size

            }

        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {

        var li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(R.layout.layout_1, parent, false)
        return myviewholder(itemView)

    }

    override fun onBindViewHolder(holder: myviewholder, position: Int) {
        if (check == false) {
            val item1 = this.namelist
            holder.itemView.ltView.text = item1[position].original_title
            val target = item1[position].poster_path
            Picasso.get().load(baseURL + target).into(holder.itemView.liView)

            holder.itemView.parentLayout.setOnClickListener {

                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("id", item1[position].id)
                intent.putExtra("type", "Movie")
                ContextCompat.startActivity(context, intent, null)
            }
        }
    }

    inner class myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView)
}