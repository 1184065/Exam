package com.example.projectexam.presentation.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectexam.BuildConfig
import com.example.projectexam.data.source.HomeDatasource
import com.example.projectexam.domain.entity.TopRatingEntity
import com.example.projectexam.presentation.TopRatingHomeView
import com.example.projectexam.presentation.state.TopRatingState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class TopRatingViewModel @Inject constructor(
    private val datasource: HomeDatasource
) : ViewModel(), TopRatingHomeView {

    companion object{
        private const val TAG = "RawGames"
    }

    private val disposables = CompositeDisposable()
    private val observer = MutableLiveData<TopRatingState>()

    override val states: LiveData<TopRatingState>
        get() = observer

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

    override fun onSuccess(entity: TopRatingEntity) {
        TODO("Not yet implemented")
    }

    override fun onError(error: Throwable) {
        TODO("Not yet implemented")
    }

    override fun onPaginationSuccess(entity: TopRatingEntity) {
        TODO("Not yet implemented")
    }

    override fun onPaginationError(error: Throwable) {
        TODO("Not yet implemented")
    }

    override fun getApiTopRating() {
        datasource.getApiTopRating(BuildConfig.API_KEY, 1)
            .map<TopRatingState>(TopRatingState::Success)
            .onErrorReturn(TopRatingState::Error)
            .toFlowable()
            .startWith(TopRatingState.Loading)
            .subscribe(observer::postValue)
            .let(disposables::add)
    }

}