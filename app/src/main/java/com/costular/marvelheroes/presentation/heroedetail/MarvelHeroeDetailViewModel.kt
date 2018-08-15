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

class MarvelHeroDetailViewModel@Inject constructor(private val marvelHeroesRepository: MarvelHeroesRepository)
    : BaseViewModel() {

    val marvelHeroState: MutableLiveData<MarvelHeroEntity> = MutableLiveData()

    fun saveFavorite(marvelHeroEntity: MarvelHeroEntity) {
        marvelHeroesRepository.setFavorite(marvelHeroEntity)?.let {
            it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                            onNext = {
                                marvelHeroState.value = it
                            },
                            onError = {
                                Log.d("HeroesViewModel", it.toString())
                            }
                    )
                    .addTo(compositeDisposable)
        }

    }
}