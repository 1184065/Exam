package com.example.projectexam.presentation.adapter

import android.annotation.SuppressLint
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectexam.R
import com.example.projectexam.domain.entity.SearchGameEntity
import com.example.projectexam.presentation.state.Type
import kotlinx.android.synthetic.main.item_search_game.view.*

class SearchGameAdapter(private var results: MutableList<SearchGameEntity.Result?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Type.DATA.ordinal -> {
                SearchGameViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(
                            R.layout.item_search_game,
                            parent,
                            false
                        )
                )
            }
            Type.LOADING.ordinal -> {
                LoadingViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(
                            R.layout.item_loading,
                            parent,
                            false
                        )
                )
            }
            else -> throw RuntimeException("Illegal View Type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchGameAdapter.SearchGameViewHolder -> {
                holder.bind(results[holder.adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = results.count()

    override fun getItemViewType(position: Int): Int {
        return when {
            results[position] == null -> Type.LOADING.ordinal
            else -> Type.DATA.ordinal
        }
    }

    fun showLoading() {
        results.add(null)
        Handler().post { notifyItemInserted(results.count().minus(1)) }
    }

    fun hideLoading() {
        results.removeAt(results.count().minus(1))
        Handler().post { notifyItemRemoved(results.count()) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun loadMore(results: MutableList<SearchGameEntity.Result?>) {
        this.results.addAll(results)
        Handler().post { notifyDataSetChanged() }
    }

    inner class SearchGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(result: SearchGameEntity.Result?) {
            with(itemView) {
                Glide.with(itemView).load(result?.backgroundImage).into(img_search_game)
                tv_search_game_name.text = result?.name
                tv_search_game_rating.text = result?.rating
            }
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}