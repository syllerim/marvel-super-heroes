package com.costular.marvelheroes.data.repository.datasource

import com.costular.marvelheroes.data.model.MarvelHeroEntity
import io.reactivex.Flowable

/**
 * Created by costular on 05/06/2018.
 */
class FakeMarvelHeroesDataSource : MarvelHeroesDataSource {

    override fun getMarvelHeroesList(): Flowable<List<MarvelHeroEntity>> {
        return Flowable.just(
                arrayListOf(
                        MarvelHeroEntity("Fake", "https://i.blogs.es/30cb7a/blackpanther5/450_1000.jpg", "Real Name1", "190cm", "Volar", "Ability1", arrayOf("1", "2")),
                        MarvelHeroEntity("Fake2", "https://i.blogs.es/30cb7a/blackpanther5/450_1000.jpg", "Real Name2", "180cm", "Ilumunar", "Ability2", arrayOf("3", "4"))
                )
        )
    }

}