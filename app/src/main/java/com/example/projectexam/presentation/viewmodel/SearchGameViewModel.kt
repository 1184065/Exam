package com.example.projectexam.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectexam.BuildConfig
import com.example.projectexam.data.source.HomeDatasource
import com.example.projectexam.domain.entity.SearchGameEntity
import com.example.projectexam.presentation.SearchGameHomeView
import com.example.projectexam.presentation.state.SearchGameState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class SearchGameViewModel @Inject constructor(
    private val datasource: HomeDatasource) : ViewModel(),
    SearchGameHomeView {

    companion object{
        private const val TAG = "RawGames"
    }

    private val disposables = CompositeDisposable()
    private val observer = MutableLiveData<SearchGameState>()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    override fun onShowLoading() {
        TODO("Not yet implemented")
    }

    override fun onHideLoading() {
        TODO("Not yet implemented")
    }

    override fun onSuccess(entity: SearchGameEntity) {
        TODO("Not yet implemented")
    }

    override fun onError(error: Throwable) {
        TODO("Not yet implemented")
    }

    override fun onPaginationSuccess(entity: SearchGameEntity) {
        TODO("Not yet implemented")
    }

    override fun onPaginationError(error: Throwable) {
        TODO("Not yet implemented")
    }

    override fun getApiSearch(keyword: String) {
        datasource.getApiSearch(BuildConfig.API_KEY, keyword)
            .map<SearchGameState>(SearchGameState::Success)
            .onErrorReturn(SearchGameState::Error)
            .toFlowable()
            .startWith(SearchGameState.Loading)
            .subscribe(observer::postValue)
            .let(disposables::add)
    }

    override val searchGameState: LiveData<SearchGameState>
        get() = TODO("Not yet implemented")
}