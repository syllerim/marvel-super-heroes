package com.costular.marvelheroes.data.repository

import com.costular.marvelheroes.data.repository.datasource.RemoteMarvelHeroesDataSource
import com.costular.marvelheroes.data.model.MarvelHeroEntity
import com.costular.marvelheroes.data.repository.datasource.LocalMarvelHeroesDataSource
import com.costular.marvelheroes.util.SettingsManager
import io.reactivex.Flowable

/**
 * Created by costular on 17/03/2018.
 */
class MarvelHeroesRepository(private val localMarvelHeroesDataSource: LocalMarvelHeroesDataSource,
                             private val remoteMarvelHeroesDataSource: RemoteMarvelHeroesDataSource,
                             private val settingsManager: SettingsManager) {

     fun getMarvelHeroesList(): Flowable<List<MarvelHeroEntity>> =
             if (settingsManager.firstLoad)
                 getMarvelHeroesListFromRemote()
             else
                 getMarvelHeroesListFromLocal()


     private fun getMarvelHeroesListFromLocal(): Flowable<List<MarvelHeroEntity>> =
             localMarvelHeroesDataSource.getMarvelHeroesList()

     private fun getMarvelHeroesListFromRemote(): Flowable<List<MarvelHeroEntity>> =
             remoteMarvelHeroesDataSource.getMarvelHeroesList()
                     .doOnNext {
                         localMarvelHeroesDataSource.saveMarvelHeroes(it)
                     }

     fun getFavoritesMarvelHeroesList(): Flowable<List<MarvelHeroEntity>>? =
             localMarvelHeroesDataSource.getFavoritesMarvelHeroesList()

     fun setFavorite(marvelHeroEntity: MarvelHeroEntity): Flowable<MarvelHeroEntity>? =
             Flowable.just(marvelHeroEntity)
                     .doOnNext {
                         localMarvelHeroesDataSource.setAsFavorite(it)
                     }
                     .doFinally {
                         localMarvelHeroesDataSource.getFavoritesMarvelHeroesList()
                     }

}