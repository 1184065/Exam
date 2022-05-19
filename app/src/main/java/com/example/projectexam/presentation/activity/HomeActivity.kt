package com.example.projectexam.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.projectexam.R
import com.example.projectexam.databinding.ActivityHomeBinding
import com.example.projectexam.domain.entity.LatestGameEntity
import com.example.projectexam.domain.entity.TopRatingEntity
import com.example.projectexam.presentation.LatestGameHomeView
import com.example.projectexam.presentation.TopRatingAdapter
import com.example.projectexam.presentation.TopRatingHomeView
import com.example.projectexam.presentation.adapter.LatestGameAdapter
import com.example.projectexam.presentation.adapter.SearchGameAdapter
import com.example.projectexam.presentation.presenter.LatestGamePresenter
import com.example.projectexam.presentation.presenter.TopRatingPresenter
import com.example.projectexam.presentation.state.LatestGameState
import com.example.projectexam.presentation.state.TopRatingState
import com.example.projectexam.presentation.viewmodel.LatestGameViewModel
import com.example.projectexam.presentation.viewmodel.TopRatingViewModel
import dagger.hilt.android.HiltAndroidApp
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject


@HiltAndroidApp
class HomeActivity : TopRatingHomeView, LatestGameHomeView {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    @Inject
    lateinit var topRatingViewModel: TopRatingViewModel

    @Inject
    lateinit var latestGameViewModel: LatestGameViewModel

    private lateinit var binding: ActivityHomeBinding

    private var topRatingAdapter: TopRatingAdapter? = null
    private var latestGameAdapter: LatestGameAdapter? = null
    private var searchGameAdapter: SearchGameAdapter? = null
    private var isLoading: Boolean = false
    private var currentPage: Long = -1L

    private val viewModel: TopRatingHomeView by lazy {
        viewModelProviders
            .of(this, viewModelFactory)
            .get(TopRatingViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        viewModel.states.observe(this, Observer { state ->
//            when (state) {
//                isLoading -> pb_home.visibility = View.VISIBLE
//                is Success -> {
//                    pb_home.visibility = View.GONE
//                    rv_top_rating.addItemDecoration(DividerItemDecoration(this@HomeActivity, DividerItemDecoration))
//                }
//            }
//        })
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        topRatingViewModel.getApiTopRating()
        latestGameViewModel.getApiLatest()

        getQuery()
    }

    override fun onDestroy() {
        super.onDestroy()
        topRatingViewModel.onDetach()
        latestGameViewModel.onDetach()
    }

    override fun onShowLoading() {
        binding.pbHome.visibility = View.VISIBLE
    }

    override fun onHideLoading() {
        binding.pbHome.visibility = View.GONE
        binding.rvTopRating.visibility = View.VISIBLE
    }


    override fun onSuccess(entity: LatestGameEntity) {
        latestGameAdapter = LatestGameAdapter(entity.results.toMutableList())
        binding.rvLatestGame.addItemDecoration(
            DividerItemDecoration(
                this@HomeActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rvLatestGame.adapter = latestGameAdapter
        binding.rvLatestGame.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rv_latest_game.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                if (visibleItemCount.plus(firstVisibleItemPosition) > totalItemCount) {
                    latestGameAdapter?.showLoading()
                    isLoading = true
                    currentPage++
                    latestGameViewModel.loadMore(currentPage)
                }
            }
        })
    }

    override fun onSuccess(entity: TopRatingEntity) {
        topRatingAdapter = TopRatingAdapter(entity.results.toMutableList())
        binding.rvTopRating.addItemDecoration(
            DividerItemDecoration(
                this@HomeActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rvTopRating.adapter = topRatingAdapter
        binding.rvTopRating.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.rvTopRating.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                if (visibleItemCount.plus(firstVisibleItemPosition) > totalItemCount) {
                    topRatingAdapter?.showLoading()
                    isLoading = true
                    currentPage++
                    topRatingViewModel.loadMore(currentPage)
                }
            }
        })
    }

    override fun onError(error: Throwable) {
        Log.e(HomeActivity::class.java.simpleName, "${error.printStackTrace()}")
    }


    override fun onPaginationSuccess(entity: LatestGameEntity) {
        hideLoading()
        latestGameAdapter?.loadMore(entity.results.toMutableList())
    }

    override fun onPaginationSuccess(entity: TopRatingEntity) {
        hideLoading()
        topRatingAdapter?.loadMore(entity.results.toMutableList())
    }

    override fun onPaginationError(error: Throwable) {
        Log.e(HomeActivity::class.java.simpleName, "${error.printStackTrace()}")
        currentPage--
        hideLoading()
    }

    override fun getApiTopRating() {
        TODO("Not yet implemented")
    }

    override val states: LiveData<TopRatingState>
        get() = TODO("Not yet implemented")

    override fun getApiLatest() {
        TODO("Not yet implemented")
    }

    override val latestGameState: LiveData<LatestGameState>
        get() = TODO("Not yet implemented")

    private fun hideLoading() {
        topRatingAdapter?.hideLoading()
        isLoading = false
    }

    private fun hideLoadingLatestGame() {
        latestGameAdapter?.hideLoading()
        isLoading = false
    }

    private fun getQuery() {
//        sv_home.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                sv_home.clearFocus()
//                if (query.isEmpty()) return false
//                goToSearchFragment(query = query)
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//        })
        binding.svHome.setOnClickListener {
            val query = binding.svHome.text.toString()
            binding.svHome.clearFocus()
            if (query.isEmpty()) return@setOnClickListener
            goToSearchFragment(query = query)
        }
    }

    private fun goToSearchFragment(query: String) {
        val intent = Intent(this, SearchActivity::class.java)
        intent.putExtra(SearchActivity.QUERY, query)
        startActivity(intent)
    }
}