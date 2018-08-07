package com.costular.marvelheroes.data.repository

import com.costular.marvelheroes.data.repository.datasource.RemoteMarvelHeroesDataSource
import com.costular.marvelheroes.data.model.MarvelHeroEntity
import com.costular.marvelheroes.data.repository.datasource.LocalMarvelHeroesDataSource
import io.reactivex.Flowable

/**
 * Created by costular on 17/03/2018.
 */
class MarvelHeroesRepository(private val localMarvelHeroesDataSource: LocalMarvelHeroesDataSource,
                             private val remoteMarvelHeroesDataSource: RemoteMarvelHeroesDataSource) {

     fun getMarvelHeroesList(): Flowable<List<MarvelHeroEntity>> =
             getMarvelHeroesListFromDb().concatWith(getMarvelHeroesListFromRemote())

     private fun getMarvelHeroesListFromDb(): Flowable<List<MarvelHeroEntity>> =
             localMarvelHeroesDataSource.getMarvelHeroesList()

     private fun getMarvelHeroesListFromRemote(): Flowable<List<MarvelHeroEntity>> =
             remoteMarvelHeroesDataSource.getMarvelHeroesList()
                     .doOnNext {
                          localMarvelHeroesDataSource.saveMarvelHeroes(it)
                     }
     fun getFavoritesMarvelHeroesList(): Flowable<List<MarvelHeroEntity>>? =
             localMarvelHeroesDataSource.getFavoritesMarvelHeroesList()
}