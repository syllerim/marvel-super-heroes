package com.costular.marvelheroes.domain.usecase

import com.costular.marvelheroes.data.repository.MarvelHeroesRepository
import com.costular.marvelheroes.data.model.MarvelHeroEntity
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by costular on 17/03/2018.
 */
class GetMarvelHeroesList @Inject constructor(val marvelHeroesRepository: MarvelHeroesRepository)
    : UseCase<List<MarvelHeroEntity>>() {

    override fun buildCase(): Flowable<List<MarvelHeroEntity>> =
            marvelHeroesRepository.getMarvelHeroesList()

}