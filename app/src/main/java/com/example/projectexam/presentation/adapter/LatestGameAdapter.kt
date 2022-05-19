package com.example.projectexam.presentation.adapter

import android.annotation.SuppressLint
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectexam.R
import com.example.projectexam.domain.entity.LatestGameEntity
import com.example.projectexam.presentation.state.Type
import kotlinx.android.synthetic.main.item_latest_game.view.*

class LatestGameAdapter(private var results: MutableList<LatestGameEntity.Result?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Type.DATA.ordinal -> {
                LatestGameViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(
                            R.layout.item_latest_game,
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
            is LatestGameViewHolder -> {
                holder.bind(results[holder.adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int {
        return results.count()
    }

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
    fun loadMore(results: MutableList<LatestGameEntity.Result?>) {
        this.results.addAll(results)
        Handler().post { notifyDataSetChanged() }
    }

    inner class LatestGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(result: LatestGameEntity.Result?) {
            with(itemView) {
                Glide.with(itemView).load(result?.backgroundImage).into(img_latest_game_background)
                tv_name.text = result?.namelatest ?: " "
                tv_release.text = result?.released ?: " "
            }
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
