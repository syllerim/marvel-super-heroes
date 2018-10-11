package com.costular.marvelheroes.data.repository.datasource

import com.costular.marvelheroes.data.db.MarvelHeroesDatabase
import com.costular.marvelheroes.data.model.MarvelHeroEntity
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class LocalMarvelHeroesDataSource(private val marvelHeroesDatabase: MarvelHeroesDatabase) : MarvelHeroesDataSource {

    override fun getMarvelHeroesList(): Flowable<List<MarvelHeroEntity>> {
        return marvelHeroesDatabase
                .getMarvelHeroDao()
                .getAllMarvelHeroes()
                .toFlowable()
    }

    fun saveMarvelHeroes(marvelHeroes: List<MarvelHeroEntity>) {
        Observable.fromCallable {
            marvelHeroesDatabase
                    .getMarvelHeroDao()
                    .removeInsertMarvelHeroes(marvelHeroes)
        }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun setAsFavorite(marvelHero: MarvelHeroEntity) {
        Observable.fromCallable {
            marvelHeroesDatabase
                .getMarvelHeroDao()
                .update(marvelHero)
        }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun getFavoritesMarvelHeroesList(): Flowable<List<MarvelHeroEntity>>? {
        return marvelHeroesDatabase
                .getMarvelHeroDao()
                .getAllFavoritesMarvelHeroes()
                .toFlowable()
    }
}