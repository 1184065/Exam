package com.example.projectexam.presentation.activity

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectexam.R
import com.example.projectexam.databinding.ActivitySearchBinding
import com.example.projectexam.domain.entity.SearchGameEntity
import com.example.projectexam.presentation.SearchGameHomeView
import com.example.projectexam.presentation.adapter.SearchGameAdapter
import com.example.projectexam.presentation.presenter.SearchGamePresenter
import com.example.projectexam.presentation.state.SearchGameState
import com.example.projectexam.presentation.viewmodel.SearchGameViewModel
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.android.support.DaggerAppCompatActivity
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class SearchActivity : DaggerAppCompatActivity(), SearchGameHomeView {

    @Inject
    lateinit var searchGameViewModel: SearchGameViewModel

    //view binding
    private lateinit var binding: ActivitySearchBinding

    private lateinit var getQuery: String

    private var searchGameAdapter: SearchGameAdapter? = null
    private var isLoading: Boolean = false
    private var currentPage: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getQuery = intent.getStringExtra(QUERY).toString()

        searchGameViewModel.getApiSearch(getQuery)
//        Log.d("SearchActivity", getQuery)

        searchGame(binding.svGame)
    }

    override fun onDestroy() {
        super.onDestroy()
        searchGameViewModel.onDetach()
    }

    override fun onShowLoading() {
    }

    override fun onHideLoading() {
    }

    companion object {
        const val QUERY = "query"
    }

    override fun onSuccess(entity: SearchGameEntity) {
        searchGameAdapter = SearchGameAdapter(entity.results.toMutableList())
        binding.rvSearch.adapter = searchGameAdapter
        binding.rvSearch.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                if (visibleItemCount.plus(firstVisibleItemPosition) > totalItemCount) {
                    searchGameAdapter?.showLoading()
                    isLoading = true
                    currentPage++
                }
            }
        })
        Log.d("SearchActivity", "Sukses")
    }
//        Log.d("SearchActivity", "Data berhasil didapatkan : ${entity.results.toString()}")

    override fun onError(error: Throwable) {
    }

    override fun onPaginationSuccess(entity: SearchGameEntity) {
    }

    override fun onPaginationError(error: Throwable) {
    }

    override fun getApiSearch(keyword: String) {
    }

    override val searchGameState: LiveData<SearchGameState>
        get() = TODO("Not yet implemented")

    private fun searchGame(searchView: EditText) {
        searchView.textChanges()
            .filter { it.length > 1 }
            .map { input -> input.toString() }
            .debounce(300, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ input ->
                searchGameViewModel.getApiSearch(input)
                Log.d("SearchActivity", input.toString())
            }, { error ->
                Log.d("SearchActivity", error.message.toString())
            })
    }
}
