package com.costular.marvelheroes.presentation.heroeslist

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.costular.marvelheroes.data.model.MarvelHeroEntity
import com.costular.marvelheroes.data.repository.MarvelHeroesRepository
import com.costular.marvelheroes.util.util.mvvm.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HeroesListViewModel@Inject constructor(private val marvelHeroesRepository: MarvelHeroesRepository)
    : BaseViewModel()  {

    val marvelHeroesListState: MutableLiveData<List<MarvelHeroEntity>> = MutableLiveData()
    val isLoadingState: MutableLiveData<Boolean> = MutableLiveData()

    fun loadMarvelHeroesList() {
        marvelHeroesRepository.getMarvelHeroesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoadingState.postValue(true) }
                .doOnTerminate { isLoadingState.postValue(false) }
                .subscribeBy(
                        onNext = {
                            marvelHeroesListState.value = it
                        },
                        onError = {
                            Log.d("HeroesViewModel", it.toString())
                        }
                )
                .addTo(compositeDisposable)
    }
}